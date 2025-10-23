package app.mecenas.server.service;

import app.mecenas.server.domain.Campaign;
import app.mecenas.server.domain.Pledge;
import app.mecenas.server.domain.User;
import app.mecenas.server.payments.StripeService;
import app.mecenas.server.repo.CampaignRepo;
import app.mecenas.server.repo.PledgeRepo;
import app.mecenas.server.security.Auth;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PledgeService {
  private final CampaignRepo campaigns;
  private final PledgeRepo pledges;
  private final Auth auth;
  private final StripeService stripe;
  private final String base;

  public PledgeService(CampaignRepo campaigns, PledgeRepo pledges, Auth auth, StripeService stripe, String base){
    this.campaigns=campaigns; this.pledges=pledges; this.auth=auth; this.stripe=stripe; this.base=base;
  }

  public ResponseEntity<?> checkout(Long id, Integer amountBrl) {
    Campaign c = campaigns.findById(id).orElse(null); if(c==null) return ResponseEntity.status(404).body(Map.of("error","not_found"));
    int amount = amountBrl==null?0:amountBrl; if(amount < c.getMinPledgeBrl()) return ResponseEntity.badRequest().body(Map.of("error","min"));
    var me = auth.currentUser().orElse(null); if(me==null) return ResponseEntity.status(401).body(Map.of("error","unauthorized"));
    var p = new Pledge(); p.setCampaign(c); p.setUser(me); p.setValorBrl(amount); pledges.save(p);
    String success = base + "/works/" + c.getWork().getId() + "?paid=1";
    String cancel  = base + "/works/" + c.getWork().getId() + "?canceled=1";
    String url = stripe.donationCheckout(success, cancel, me.getEmail(), amount*100L, Map.of("pledgeId", String.valueOf(p.getId())));
    return ResponseEntity.ok(Map.of("url", url));
  }
}
