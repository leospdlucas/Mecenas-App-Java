
package app.mecenas.server.api;
import app.mecenas.server.domain.*; import app.mecenas.server.repo.*; import app.mecenas.server.security.Auth; import app.mecenas.server.service.OfferService; import org.springframework.validation.annotation.Validated; import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*; import java.time.Instant; import java.util.Map;
<<<<<<< HEAD
@RestController
@RequestMapping("/api/offers")
=======
<<<<<<< HEAD
@RestController
@RequestMapping("/api/offers")
=======
<<<<<<< HEAD
@RestController
@RequestMapping("/api/offers")
=======
@Validated
@RestController @RequestMapping("/api/offers")
>>>>>>> 1f6bccc1ecdc206a9380947cbc827c5f8dfabbd1
>>>>>>> f509e3d3f75bbcdd62127a120ad1d2be3e866c81
>>>>>>> 2585984bf8114c646374a98c2248cc799e3d6a01
public class OfferController {
  private final ContractOfferRepo offers; private final WorkRepo works; private final Auth auth; private final OfferService service;
  public OfferController(ContractOfferRepo o, WorkRepo w, Auth a, OfferService s){ this.offers=o; this.works=w; this.auth=a; this.service=s; }
  public record OfferReq(Long workId, java.util.Map<String,Object> termos, String expiraEm){}
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> f509e3d3f75bbcdd62127a120ad1d2be3e866c81
>>>>>>> 2585984bf8114c646374a98c2248cc799e3d6a01

  @PostMapping public ResponseEntity<?> create(@RequestBody OfferReq r){
    var me = auth.currentUser().orElse(null); if(me==null) return ResponseEntity.status(401).body(Map.of("error","unauthorized"));
    if(me.getRoles()==null || !me.getRoles().contains("PARTNER")) return ResponseEntity.status(403).body(Map.of("error","forbidden"));
    var w = works.findById(r.workId()).orElse(null); if(w==null) return ResponseEntity.status(404).body(Map.of("error","work_not_found"));
    var co = new ContractOffer(); co.setWork(w); co.setPartner(me); co.setAuthor(w.getAuthor()); co.setTermosJson(r.termos()==null?"{}":r.termos().toString()); co.setStatus("SENT");
    co.setExpiraEm(r.expiraEm()==null?Instant.now().plusSeconds(1209600):Instant.parse(r.expiraEm())); offers.save(co);
    return ResponseEntity.ok(Map.of("id", co.getId(), "status", co.getStatus()));
  }

<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
=======
  @PostMapping public ResponseEntity<?> create(@Valid @RequestBody OfferReq r){ return service.create(new OfferService.OfferCreate(r.workId(), r.termos(), r.expiraEm())); }
>>>>>>> 1f6bccc1ecdc206a9380947cbc827c5f8dfabbd1
>>>>>>> f509e3d3f75bbcdd62127a120ad1d2be3e866c81
>>>>>>> 2585984bf8114c646374a98c2248cc799e3d6a01
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
