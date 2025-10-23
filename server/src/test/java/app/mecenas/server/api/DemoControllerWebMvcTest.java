package app.mecenas.server.api;

import app.mecenas.server.service.DemoService;
import app.mecenas.server.storage.S3Service;
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

@WebMvcTest(controllers = DemoController.class)
class DemoControllerWebMvcTest {

  @Autowired MockMvc mvc;
  @Autowired ObjectMapper om;

  @MockBean S3Service s3;
  @MockBean DemoService service;

  @Test
  void presignDelegatesService() throws Exception {
    when(service.presign(any())).thenReturn(org.springframework.http.ResponseEntity.ok(Map.of("url","http://s3/presigned","key","x")));
    var body = Map.of("key","x", "contentType","audio/mpeg");
    mvc.perform(post("/api/demos/presign").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(body)))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$.url").value("http://s3/presigned"))
       .andExpect(jsonPath("$.key").value("x"));
  }
}
