
package app.mecenas.server.api;
import app.mecenas.server.security.Auth; import app.mecenas.server.payments.StripeService; import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Value; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController @RequestMapping("/api/subscriptions")
public class SubscriptionController {
  private final Auth auth; private final StripeService stripe; private final String base; private final String pw, pm, pa; private final SubscriptionService service;
  public SubscriptionController(Auth a, StripeService s, @Value("${app.baseUrl}") String base, @Value("${stripe.price.weekly}") String pw, @Value("${stripe.price.monthly}") String pm, @Value("${stripe.price.annual}") String pa){ this.auth=a; this.stripe=s; this.base=base; this.pw=pw; this.pm=pm; this.pa=pa; }
  public record SubReq(String plan){}
  @PostMapping("/checkout") public ResponseEntity<?> checkout(@RequestBody SubReq r) throws StripeException { return service.checkout(r.plan()); }
}
