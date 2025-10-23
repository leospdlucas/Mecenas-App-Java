package app.mecenas.server.service;

import app.mecenas.server.domain.ContractOffer;
import app.mecenas.server.domain.User;
import app.mecenas.server.domain.Work;
import app.mecenas.server.repo.ContractOfferRepo;
import app.mecenas.server.repo.WorkRepo;
import app.mecenas.server.security.Auth;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Service
@Validated
public class OfferService {
  private final ContractOfferRepo offers;
  private final WorkRepo works;
  private final Auth auth;

  public OfferService(ContractOfferRepo offers, WorkRepo works, Auth auth){
    this.offers = offers; this.works = works; this.auth = auth;
  }

  public record OfferCreate(@NotNull Long workId, Map<String,Object> termos, String expiraEm) {}

  public ResponseEntity<?> create(@Valid OfferCreate r){
    Optional<User> meOpt = auth.currentUser(); if (meOpt.isEmpty()) return ResponseEntity.status(401).body(Map.of("error","unauthorized"));
    User me = meOpt.get();
    // roles in original code are String list inside a single field
    if (me.getRoles()==null || !me.getRoles().contains(app.mecenas.server.security.Role.PARTNER)) return ResponseEntity.status(403).body(Map.of("error","forbidden"));
    Work w = works.findById(r.workId()).orElse(null); if (w==null) return ResponseEntity.status(404).body(Map.of("error","work_not_found"));
    ContractOffer co = new ContractOffer();
    co.setWork(w);
    co.setPartner(me);
    co.setTermos(r.termos()==null? "{}" : r.termos().toString());
    co.setStatus("SENT");
    co.setExpiraEm(r.expiraEm()==null? Instant.now().plusSeconds(60L*60*24*14) : Instant.parse(r.expiraEm()));
    offers.save(co);
    return ResponseEntity.ok(Map.of("id", co.getId(), "status", co.getStatus()));
  }
}
