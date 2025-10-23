package app.mecenas.server.api;

import app.mecenas.server.domain.User;
import app.mecenas.server.domain.Work;
import app.mecenas.server.dto.WorkDto;
import app.mecenas.server.mapper.WorkMapper;
import app.mecenas.server.repo.WorkRepo;
import app.mecenas.server.security.Auth;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WorkController.class)
class WorkControllerWebMvcTest {

  @Autowired MockMvc mvc;

  @MockBean WorkRepo works;
  @MockBean Auth auth;

  @Configuration
  static class TestConfig {
    @Bean WorkMapper workMapper() {
      return new WorkMapper() {
        @Override public WorkDto toDto(Work w) {
          return new WorkDto(w.getId(), w.getAuthor()==null?null:w.getAuthor().getId(), w.getTitulo(), w.getCategoria(), w.getDescricao(), w.getStatus());
        }
      };
    }
  }

  @Test
  void getWorkReturnsDto() throws Exception {
    Work w = new Work();
    w.setId(7L);
    User u = new User(); u.setId(99L); w.setAuthor(u);
    w.setTitulo("Obra X");
    w.setCategoria("Música");
    w.setDescricao("Desc");
    w.setStatus("DRAFT");

    Mockito.when(works.findById(anyLong())).thenReturn(Optional.of(w));

    mvc.perform(get("/api/works/7").accept(MediaType.APPLICATION_JSON))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$.id").value(7))
       .andExpect(jsonPath("$.authorId").value(99))
       .andExpect(jsonPath("$.titulo").value("Obra X"));
  }
}
