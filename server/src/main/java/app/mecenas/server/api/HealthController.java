package app.mecenas.server.api; import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController public class HealthController{
  @GetMapping("/api/health") public Map<String,Object> h(){ return Map.of("ok",true);} }