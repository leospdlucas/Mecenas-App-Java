package app.mecenas.server.service;

import app.mecenas.server.domain.Campaign;
import app.mecenas.server.domain.User;
import app.mecenas.server.repo.CampaignRepo;
import app.mecenas.server.repo.PledgeRepo;
import app.mecenas.server.security.Auth;
import app.mecenas.server.payments.StripeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class PledgeServiceTest {

  @Test
  void rejectsBelowMinimum() {
    CampaignRepo campaigns = Mockito.mock(CampaignRepo.class);
    PledgeRepo pledges = Mockito.mock(PledgeRepo.class);
    Auth auth = Mockito.mock(Auth.class);
    StripeService stripe = Mockito.mock(StripeService.class);

    Campaign c = new Campaign();
    c.setMinPledgeBrl(500); // 5 reais
    when(campaigns.findById(1L)).thenReturn(Optional.of(c));

    PledgeService svc = new PledgeService(campaigns, pledges, auth, stripe, "https://base");

    ResponseEntity<?> resp = svc.checkout(1L, 100);
    assertEquals(400, resp.getStatusCode().value());
  }

  @Test
  void happyPathReturnsUrl() throws Exception {
    CampaignRepo campaigns = Mockito.mock(CampaignRepo.class);
    PledgeRepo pledges = Mockito.mock(PledgeRepo.class);
    Auth auth = Mockito.mock(Auth.class);
    StripeService stripe = Mockito.mock(StripeService.class);

    Campaign c = new Campaign();
    c.setMinPledgeBrl(500);
    c.setWork(new app.mecenas.server.domain.Work()); c.getWork().setId(99L);
    when(campaigns.findById(1L)).thenReturn(Optional.of(c));

    User u = new User(); u.setId(7L); u.setEmail("u@u.com");
    when(auth.currentUser()).thenReturn(Optional.of(u));

    when(stripe.donationCheckout(anyString(), anyString(), anyString(), anyLong(), anyMap())).thenReturn("ok");

    PledgeService svc = new PledgeService(campaigns, pledges, auth, stripe, "https://base");
    ResponseEntity<?> resp = svc.checkout(1L, 500);
    assertEquals(200, resp.getStatusCode().value());
    assertTrue(resp.getBody().toString().contains("ok"));
  }
}
