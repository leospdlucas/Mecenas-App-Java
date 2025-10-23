package app.mecenas.server.service;

import app.mecenas.server.payments.StripeService;
import app.mecenas.server.security.Auth;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SubscriptionService {
  private final Auth auth;
  private final StripeService stripe;
  private final String base;
  private final String pw;
  private final String pm;
  private final String pa;

  public SubscriptionService(Auth auth, StripeService stripe, String base, String pw, String pm, String pa){
    this.auth=auth; this.stripe=stripe; this.base=base; this.pw=pw; this.pm=pm; this.pa=pa;
  }

  public ResponseEntity<?> checkout(String plan) throws StripeException {
    var me = auth.currentUser().orElse(null); if(me==null) return ResponseEntity.status(401).body(Map.of("error","unauthorized"));
    if(me.getRoles()==null || !me.getRoles().contains(app.mecenas.server.security.Role.PARTNER)) return ResponseEntity.status(403).body(Map.of("error","forbidden"));
    String price = "weekly".equals(plan)?pw:("annual".equals(plan)?pa:pm);
    String url = stripe.subscriptionCheckout(base+"/dashboard/payment/success", base+"/dashboard/payment/cancel",
            price, Map.of("orgUserId", String.valueOf(me.getId())));
    return ResponseEntity.ok(Map.of("url", url));
  }
}
