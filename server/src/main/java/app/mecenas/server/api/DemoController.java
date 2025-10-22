
package app.mecenas.server.api;
import app.mecenas.server.storage.S3Service; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController @RequestMapping("/api/demos")
public class DemoController {
  private final S3Service s3; public DemoController(S3Service s3){ this.s3=s3; }
  public record PresignReq(String key,String contentType){}
  @PostMapping("/presign") public ResponseEntity<?> presign(@RequestBody PresignReq r){
    if(r.key()==null||r.contentType()==null) return ResponseEntity.badRequest().body(Map.of("error","missing params"));
    var url = s3.presignPut(r.key(), r.contentType()); return ResponseEntity.ok(Map.of("url", url.toString(), "key", r.key()));
  }
}
