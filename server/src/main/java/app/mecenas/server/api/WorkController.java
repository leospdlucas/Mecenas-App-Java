
package app.mecenas.server.api;
import app.mecenas.server.domain.*; import app.mecenas.server.repo.*; import app.mecenas.server.security.Auth;
import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*; import java.util.Map; import java.util.Optional;
@RestController
@RequestMapping("/api/works")
public class WorkController {
  private final WorkRepo works; private final Auth auth;
  public WorkController(WorkRepo works, Auth auth){ this.works=works; this.auth=auth; }
  public record WorkCreate(String titulo,String categoria,String descricao){}

  @PostMapping public ResponseEntity<?> create(@RequestBody WorkCreate b){
    Optional<User> me = auth.currentUser(); if(me.isEmpty()) return ResponseEntity.status(401).body(Map.of("error","unauthorized"));
    var w=new Work(); w.setAuthor(me.get()); w.setTitulo(b.titulo()); w.setCategoria(b.categoria()); w.setDescricao(b.descricao()); works.save(w);
    return ResponseEntity.ok(Map.of("id", w.getId()));
  }

  @GetMapping("/{id}") public ResponseEntity<?> get(@PathVariable Long id){ return works.findById(id).<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(()->ResponseEntity.status(404).body(Map.of("error","not_found"))); }
}
