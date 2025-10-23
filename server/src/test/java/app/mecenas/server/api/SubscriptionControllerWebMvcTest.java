package app.mecenas.server.api;

import app.mecenas.server.security.Auth;
import app.mecenas.server.service.SubscriptionService;
import app.mecenas.server.payments.StripeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SubscriptionController.class)
class SubscriptionControllerWebMvcTest {

  @Autowired MockMvc mvc;
  @Autowired ObjectMapper om;

  @MockBean Auth auth;
  @MockBean StripeService stripe;
  @MockBean SubscriptionService service;

  @Test
  void checkoutDelegatesService() throws Exception {
    when(service.checkout(any())).thenReturn(org.springframework.http.ResponseEntity.ok(Map.of("url","ok")));
    var body = Map.of("plan", "monthly");
    mvc.perform(post("/api/subscriptions/checkout").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(body)))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$.url").value("ok"));
  }
}
