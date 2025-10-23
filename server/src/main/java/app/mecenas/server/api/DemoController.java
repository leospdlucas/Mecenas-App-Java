
package app.mecenas.server.api;
import app.mecenas.server.storage.S3Service; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*; import java.util.Map; import app.mecenas.server.service.DemoService; import org.springframework.validation.annotation.Validated; import jakarta.validation.Valid;
@Validated
@RestController @RequestMapping("/api/demos")
public class DemoController {
  private final S3Service s3; private final DemoService service; public DemoController(S3Service s3, DemoService service){ this.s3=s3; this.service=service; }
  public record PresignReq(String key,String contentType){}
  @PostMapping("/presign") public ResponseEntity<?> presign(@Valid @RequestBody DemoService.Presign r){ return service.presign(r); }
}
