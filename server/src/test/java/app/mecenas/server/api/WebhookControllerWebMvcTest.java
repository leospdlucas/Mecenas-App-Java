package app.mecenas.server.api;

import app.mecenas.server.repo.PledgeRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WebhookController.class)
class WebhookControllerWebMvcTest {

  @Autowired MockMvc mvc;
  @Autowired ObjectMapper om;

  @MockBean PledgeRepo pledges;

  @Test
  void badPayloadReturns400() throws Exception {
    mvc.perform(post("/api/webhook")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{not-json"))
      .andExpect(status().is4xxClientError());
  }
}
