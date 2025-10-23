
package app.mecenas.server.api;
import app.mecenas.server.domain.*; import app.mecenas.server.repo.*; import app.mecenas.server.security.Auth; import app.mecenas.server.service.WorkService; import jakarta.validation.Valid; import org.springframework.validation.annotation.Validated; import app.mecenas.server.mapper.WorkMapper; import app.mecenas.server.dto.WorkDto;
import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*; import java.util.Map; import java.util.Optional;
@Validated
@RestController @RequestMapping("/api/works")
public class WorkController {
  private final WorkRepo works; private final Auth auth; private final WorkService workService; private final WorkMapper mapper;
  public WorkController(WorkRepo works, Auth auth, WorkService workService, WorkMapper mapper){ this.works=works; this.auth=auth; this.workService=workService; this.mapper=mapper; }
  public record WorkCreate(String titulo,String categoria,String descricao){}
  @PostMapping public ResponseEntity<?> create(@Valid @RequestBody WorkService.WorkCreate b){ return workService.create(b); }
  @GetMapping("/{id}") public ResponseEntity<?> get(@PathVariable Long id){ return works.findById(id).map(mapper::toDto).<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(()->ResponseEntity.status(404).body(Map.of("error","not_found"))); }
}
