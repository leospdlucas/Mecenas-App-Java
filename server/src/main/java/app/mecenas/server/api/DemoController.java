
package app.mecenas.server.api;
<<<<<<< HEAD
import app.mecenas.server.storage.S3Service; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController
@RequestMapping("/api/demos")
=======
import app.mecenas.server.storage.S3Service; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*; import java.util.Map; import app.mecenas.server.service.DemoService; import org.springframework.validation.annotation.Validated; import jakarta.validation.Valid;
@Validated
@RestController @RequestMapping("/api/demos")
>>>>>>> 1f6bccc1ecdc206a9380947cbc827c5f8dfabbd1
public class DemoController {
  private final S3Service s3; private final DemoService service; public DemoController(S3Service s3, DemoService service){ this.s3=s3; this.service=service; }
  public record PresignReq(String key,String contentType){}
<<<<<<< HEAD

  @PostMapping("/presign") public ResponseEntity<?> presign(@RequestBody PresignReq r){
    if(r.key()==null||r.contentType()==null) return ResponseEntity.badRequest().body(Map.of("error","missing params"));
    var url = s3.presignPut(r.key(), r.contentType()); return ResponseEntity.ok(Map.of("url", url.toString(), "key", r.key()));
  }
=======
  @PostMapping("/presign") public ResponseEntity<?> presign(@Valid @RequestBody DemoService.Presign r){ return service.presign(r); }
>>>>>>> 1f6bccc1ecdc206a9380947cbc827c5f8dfabbd1
}
