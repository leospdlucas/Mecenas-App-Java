package app.mecenas.server.api;

import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
public class HealthController {

  @GetMapping("/api/health")
  public Map<String, Object> h() {
    return Map.of("ok", true);
  }
}
