package app.mecenas.server.service;

import app.mecenas.server.storage.S3Service;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Service
@Validated
public class DemoService {
  private final S3Service s3;
  public DemoService(S3Service s3){ this.s3=s3; }

  public record Presign(@NotBlank String key, @NotBlank String contentType) {}

  public ResponseEntity<?> presign(@Valid Presign r){
    var url = s3.presignPut(r.key(), r.contentType());
    return ResponseEntity.ok(Map.of("url", url.toString(), "key", r.key()));
  }
}
