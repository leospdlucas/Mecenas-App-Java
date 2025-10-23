package app.mecenas.server.api;

import app.mecenas.server.service.PledgeService;
import app.mecenas.server.repo.CampaignRepo;
import app.mecenas.server.repo.PledgeRepo;
import app.mecenas.server.security.Auth;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CampaignController.class)
class CampaignControllerWebMvcTest {

  @Autowired MockMvc mvc;
  @Autowired ObjectMapper om;

  @MockBean CampaignRepo campaigns;
  @MockBean PledgeRepo pledges;
  @MockBean Auth auth;
  @MockBean PledgeService pledgeService;

  @Test
  void checkoutReturnsUrl() throws Exception {
    when(pledgeService.checkout(anyLong(), org.mockito.ArgumentMatchers.any())).thenReturn(org.springframework.http.ResponseEntity.ok(Map.of("url","https://stripe/ok")));
    var body = Map.of("amountBrl", 500);
    mvc.perform(post("/api/campaigns/1/pledges/checkout")
        .contentType(MediaType.APPLICATION_JSON)
        .content(om.writeValueAsBytes(body)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.url").value("https://stripe/ok"));
  }
}
