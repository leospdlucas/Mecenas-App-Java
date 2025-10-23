package app.mecenas.server.api;

import app.mecenas.server.repo.ContractOfferRepo;
import app.mecenas.server.repo.WorkRepo;
import app.mecenas.server.security.Auth;
import app.mecenas.server.service.OfferService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebMvcTest(controllers = OfferController.class)
class OfferControllerWebMvcTest {

  @Autowired MockMvc mvc;
  @Autowired ObjectMapper om;

  @MockBean ContractOfferRepo offers;
  @MockBean WorkRepo works;
  @MockBean Auth auth;
  @MockBean OfferService service;

  @Test
  void createOfferDelegatesService() throws Exception {
    when(service.create(any())).thenReturn(org.springframework.http.ResponseEntity.ok(Map.of("id", 1, "status", "SENT")));
    var body = Map.of("workId", 7, "termos", Map.of("a","b"), "expiraEm", null);
    mvc.perform(post("/api/offers").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(body)))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$.id").value(1))
       .andExpect(jsonPath("$.status").value("SENT"));
  }
}
