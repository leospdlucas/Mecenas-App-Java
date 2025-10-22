
package app.mecenas.server.api;
import app.mecenas.server.repo.PledgeRepo; import com.stripe.exception.SignatureVerificationException; import com.stripe.model.Event; import com.stripe.model.checkout.Session; import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController @RequestMapping("/api/webhooks/stripe")
public class WebhookController {
  private final PledgeRepo pledges; private final String wh;
  public WebhookController(PledgeRepo pledges, @Value("${stripe.webhookSecret}") String wh){ this.pledges=pledges; this.wh=wh; }
  @PostMapping public ResponseEntity<?> handle(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sig) throws Exception {
    Event evt;
    try { evt = Webhook.constructEvent(payload, sig, wh); } catch (SignatureVerificationException e){ return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); }
    if ("checkout.session.completed".equals(evt.getType())) {
      Session s = (Session) evt.getDataObjectDeserializer().getObject().orElse(null);
      if (s != null && "payment".equals(s.getMode())) {
        String pledgeId = s.getMetadata().get("pledgeId");
        if (pledgeId != null) pledges.findById(Long.parseLong(pledgeId)).ifPresent(p -> { p.setStatus("PAID"); pledges.save(p); });
      }
    }
    return ResponseEntity.ok(Map.of("received", true));
  }
}
