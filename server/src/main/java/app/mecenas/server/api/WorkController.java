
package app.mecenas.server.api;
import app.mecenas.server.domain.*; import app.mecenas.server.repo.*; import app.mecenas.server.security.Auth; import app.mecenas.server.service.WorkService; import jakarta.validation.Valid; import org.springframework.validation.annotation.Validated; import app.mecenas.server.mapper.WorkMapper; import app.mecenas.server.dto.WorkDto;
import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*; import java.util.Map; import java.util.Optional;
<<<<<<< HEAD
@RestController
@RequestMapping("/api/works")
=======
<<<<<<< HEAD
@RestController
@RequestMapping("/api/works")
=======
@Validated
@RestController @RequestMapping("/api/works")
>>>>>>> 1f6bccc1ecdc206a9380947cbc827c5f8dfabbd1
>>>>>>> f509e3d3f75bbcdd62127a120ad1d2be3e866c81
public class WorkController {
  private final WorkRepo works; private final Auth auth; private final WorkService workService; private final WorkMapper mapper;
  public WorkController(WorkRepo works, Auth auth, WorkService workService, WorkMapper mapper){ this.works=works; this.auth=auth; this.workService=workService; this.mapper=mapper; }
  public record WorkCreate(String titulo,String categoria,String descricao){}
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> f509e3d3f75bbcdd62127a120ad1d2be3e866c81

  @PostMapping public ResponseEntity<?> create(@RequestBody WorkCreate b){
    Optional<User> me = auth.currentUser(); if(me.isEmpty()) return ResponseEntity.status(401).body(Map.of("error","unauthorized"));
    var w=new Work(); w.setAuthor(me.get()); w.setTitulo(b.titulo()); w.setCategoria(b.categoria()); w.setDescricao(b.descricao()); works.save(w);
    return ResponseEntity.ok(Map.of("id", w.getId()));
  }

  @GetMapping("/{id}") public ResponseEntity<?> get(@PathVariable Long id){ return works.findById(id).<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(()->ResponseEntity.status(404).body(Map.of("error","not_found"))); }
=======
  @PostMapping public ResponseEntity<?> create(@Valid @RequestBody WorkService.WorkCreate b){ return workService.create(b); }
  @GetMapping("/{id}") public ResponseEntity<?> get(@PathVariable Long id){ return works.findById(id).map(mapper::toDto).<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(()->ResponseEntity.status(404).body(Map.of("error","not_found"))); }
>>>>>>> 1f6bccc1ecdc206a9380947cbc827c5f8dfabbd1
}
