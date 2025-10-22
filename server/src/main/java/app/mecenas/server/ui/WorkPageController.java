
package app.mecenas.server.ui;
import app.mecenas.server.repo.*; import app.mecenas.server.security.Auth; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.*;
@Controller public class WorkPageController {
  private final WorkRepo works; private final DemoRepo demos; private final Auth auth;
  public WorkPageController(WorkRepo w, DemoRepo d, Auth a){ this.works=w; this.demos=d; this.auth=a; }
  @GetMapping("/") public String home(){ return "home"; }
  @GetMapping("/works/{id}") public String view(@PathVariable Long id, Model model){
    var w = works.findById(id).orElse(null);
    var d = demos.findAll().stream().filter(x->x.getWork().getId().equals(id)).findFirst().orElse(null);
    var u = auth.currentUser().orElse(null);
    model.addAttribute("work", w); model.addAttribute("demo", d);
    model.addAttribute("watermark", (u!=null?("user#"+u.getId()):"guest")+" • "+java.time.Instant.now().toString());
    return "work";
  }
}
