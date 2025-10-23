
package app.mecenas.server.api;
import app.mecenas.server.security.Auth; import app.mecenas.server.payments.StripeService; import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Value; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
<<<<<<< HEAD
  private final Auth auth; private final StripeService stripe; private final String base; private final String pw, pm, pa;
  public SubscriptionController(Auth a, StripeService s,
  @Value("${app.baseUrl}") String base,
  @Value("${stripe.price.weekly}") String pw,
  @Value("${stripe.price.monthly}") String pm,
  @Value("${stripe.price.annual}") String pa){ this.auth=a; this.stripe=s; this.base=base; this.pw=pw; this.pm=pm; this.pa=pa; }
  public record SubReq(String plan){}

  @PostMapping("/checkout") public ResponseEntity<?> checkout(@RequestBody SubReq r) throws StripeException {
    var me = auth.currentUser().orElse(null); if(me==null) return ResponseEntity.status(401).body(Map.of("error","unauthorized"));
    if(me.getRoles()==null || !me.getRoles().contains("PARTNER")) return ResponseEntity.status(403).body(Map.of("error","forbidden"));
    String price = "weekly".equals(r.plan())?pw:("annual".equals(r.plan())?pa:pm);
    String url = stripe.subscriptionCheckout(base+"/dashboard/partner?sub=ok", base+"/dashboard/partner?sub=cancel", price, Map.of("orgUserId", String.valueOf(me.getId())));
    return ResponseEntity.ok(Map.of("url", url));
  }
=======
  private final Auth auth; private final StripeService stripe; private final String base; private final String pw, pm, pa; private final SubscriptionService service;
  public SubscriptionController(Auth a, StripeService s, @Value("${app.baseUrl}") String base, @Value("${stripe.price.weekly}") String pw, @Value("${stripe.price.monthly}") String pm, @Value("${stripe.price.annual}") String pa){ this.auth=a; this.stripe=s; this.base=base; this.pw=pw; this.pm=pm; this.pa=pa; }
  public record SubReq(String plan){}
  @PostMapping("/checkout") public ResponseEntity<?> checkout(@RequestBody SubReq r) throws StripeException { return service.checkout(r.plan()); }
>>>>>>> 1f6bccc1ecdc206a9380947cbc827c5f8dfabbd1
}
