
package app.mecenas.server.api;
import app.mecenas.server.domain.*; import app.mecenas.server.repo.*; import app.mecenas.server.security.Auth; import app.mecenas.server.service.OfferService; import org.springframework.validation.annotation.Validated; import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*; import java.time.Instant; import java.util.Map;
@Validated
@RestController @RequestMapping("/api/offers")
public class OfferController {
  private final ContractOfferRepo offers; private final WorkRepo works; private final Auth auth; private final OfferService service;
  public OfferController(ContractOfferRepo o, WorkRepo w, Auth a, OfferService s){ this.offers=o; this.works=w; this.auth=a; this.service=s; }
  public record OfferReq(Long workId, java.util.Map<String,Object> termos, String expiraEm){}
  @PostMapping public ResponseEntity<?> create(@Valid @RequestBody OfferReq r){ return service.create(new OfferService.OfferCreate(r.workId(), r.termos(), r.expiraEm())); }
  @PostMapping("/{id}/accept") public ResponseEntity<?> accept(@PathVariable Long id){
    var me = auth.currentUser().orElse(null); if(me==null) return ResponseEntity.status(401).body(Map.of("error","unauthorized"));
    var co = offers.findById(id).orElse(null); if(co==null) return ResponseEntity.status(404).body(Map.of("error","not_found"));
    if(!co.getAuthor().getId().equals(me.getId())) return ResponseEntity.status(403).body(Map.of("error","forbidden"));
    co.setStatus("ACCEPTED"); offers.save(co); return ResponseEntity.ok(Map.of("ok",true));
  }
  @PostMapping("/{id}/reject") public ResponseEntity<?> reject(@PathVariable Long id){
    var me = auth.currentUser().orElse(null); if(me==null) return ResponseEntity.status(401).body(Map.of("error","unauthorized"));
    var co = offers.findById(id).orElse(null); if(co==null) return ResponseEntity.status(404).body(Map.of("error","not_found"));
    if(!co.getAuthor().getId().equals(me.getId())) return ResponseEntity.status(403).body(Map.of("error","forbidden"));
    co.setStatus("REJECTED"); offers.save(co); return ResponseEntity.ok(Map.of("ok",true));
  }
}
