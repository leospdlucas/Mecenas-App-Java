package app.mecenas.server.service;

import app.mecenas.server.domain.User;
import app.mecenas.server.domain.Work;
import app.mecenas.server.repo.WorkRepo;
import app.mecenas.server.security.Auth;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Map;
import java.util.Optional;

@Service
@Validated
public class WorkService {
  private final WorkRepo works;
  private final Auth auth;

  public WorkService(WorkRepo works, Auth auth){
    this.works=works; this.auth=auth;
  }

  public record WorkCreate(@NotBlank String titulo, @NotBlank String categoria, @NotBlank String descricao) {}

  public ResponseEntity<?> create(WorkCreate b){
    Optional<User> me = auth.currentUser(); if (me.isEmpty()) return ResponseEntity.status(401).body(Map.of("error","unauthorized"));
    var w = new Work();
    w.setAuthor(me.get());
    w.setTitulo(b.titulo());
    w.setCategoria(b.categoria());
    w.setDescricao(b.descricao());
    works.save(w);
    return ResponseEntity.ok(Map.of("id", w.getId()));
  }

  public Optional<Work> findById(Long id){
    return works.findById(id);
  }
}
