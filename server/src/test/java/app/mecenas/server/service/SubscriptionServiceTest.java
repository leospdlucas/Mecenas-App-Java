package app.mecenas.server.service;

import app.mecenas.server.domain.User;
import app.mecenas.server.payments.StripeService;
import app.mecenas.server.security.Auth;
import app.mecenas.server.security.Role;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SubscriptionServiceTest {

  @Test
  void picksRightPriceByPlan() throws StripeException {
    Auth auth = Mockito.mock(Auth.class);
    StripeService stripe = Mockito.mock(StripeService.class);

    User me = new User(); me.setId(99L); me.setEmail("p@p.com"); me.setRoles(Set.of(Role.PARTNER));
    when(auth.currentUser()).thenReturn(Optional.of(me));
    when(stripe.subscriptionCheckout(anyString(), anyString(), eq("price_weekly"), anyMap())).thenReturn("ok-weekly");
    when(stripe.subscriptionCheckout(anyString(), anyString(), eq("price_monthly"), anyMap())).thenReturn("ok-monthly");
    when(stripe.subscriptionCheckout(anyString(), anyString(), eq("price_annual"), anyMap())).thenReturn("ok-annual");

    SubscriptionService svc = new SubscriptionService(auth, stripe, "https://base", "price_weekly", "price_monthly", "price_annual");

    ResponseEntity<?> w = svc.checkout("weekly");
    ResponseEntity<?> m = svc.checkout("monthly");
    ResponseEntity<?> a = svc.checkout("annual");

    assertTrue(w.getBody().toString().contains("ok-weekly"));
    assertTrue(m.getBody().toString().contains("ok-monthly"));
    assertTrue(a.getBody().toString().contains("ok-annual"));
  }
}
