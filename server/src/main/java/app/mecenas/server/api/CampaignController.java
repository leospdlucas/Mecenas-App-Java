
package app.mecenas.server.api;
import app.mecenas.server.domain.*; import app.mecenas.server.repo.*; import app.mecenas.server.security.Auth; import app.mecenas.server.service.PledgeService;
import com.stripe.exception.StripeException; import org.springframework.beans.factory.annotation.Value; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {
<<<<<<< HEAD
  private final CampaignRepo campaigns; private final PledgeRepo pledges; private final Auth auth; private final StripeService stripe; private final String base;
  public CampaignController(CampaignRepo c, PledgeRepo p, Auth a, StripeService s,
  @Value("${app.baseUrl}") String base){ this.campaigns=c; this.pledges=p; this.auth=a; this.stripe=s; this.base=base; }
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
=======
  private final CampaignRepo campaigns; private final PledgeRepo pledges; private final Auth auth; private final String base; private final PledgeService pledgeService;
  public CampaignController(CampaignRepo c, PledgeRepo p, Auth a, StripeService s, @Value("${app.baseUrl}") String base){ this.campaigns=c; this.pledges=p; this.auth=a; this.stripe=s; this.base=base; }
>>>>>>> 1f6bccc1ecdc206a9380947cbc827c5f8dfabbd1
>>>>>>> f509e3d3f75bbcdd62127a120ad1d2be3e866c81
>>>>>>> 2585984bf8114c646374a98c2248cc799e3d6a01
  public record CheckoutReq(Integer amountBrl){}

  @PostMapping("/{id}/pledges/checkout") public ResponseEntity<?> checkout(@PathVariable Long id,
  @RequestBody CheckoutReq r) throws StripeException {
    var c = campaigns.findById(id).orElse(null); if(c==null) return ResponseEntity.status(404).body(Map.of("error","not_found"));
    int amount = r.amountBrl()==null?0:r.amountBrl(); if(amount < c.getMinPledgeBrl()) return ResponseEntity.badRequest().body(Map.of("error","min"));
    var me = auth.currentUser().orElse(null); if(me==null) return ResponseEntity.status(401).body(Map.of("error","unauthorized"));
    var p = new Pledge(); p.setCampaign(c); p.setUser(me); p.setValorBrl(amount); pledges.save(p);
    String success = base + "/works/" + c.getWork().getId() + "?paid=1";
    String cancel  = base + "/works/" + c.getWork().getId() + "?canceled=1";
    String checkoutUrl = stripe.donationCheckout(success, cancel, "Doação: "+c.getWork().getTitulo(), amount*100L, Map.of("pledgeId", String.valueOf(p.getId())));
    return ResponseEntity.ok(Map.of("url", checkoutUrl));
  }
}
