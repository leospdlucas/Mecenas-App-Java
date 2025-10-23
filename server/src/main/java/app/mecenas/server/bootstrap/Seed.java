
package app.mecenas.server.bootstrap;
import app.mecenas.server.domain.*; import app.mecenas.server.repo.*; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.Bean; import org.springframework.context.annotation.Configuration;
import java.time.Instant;
@Configuration public class Seed {

  @Bean CommandLineRunner seed(UserRepo users, WorkRepo works, DemoRepo demos, CampaignRepo campaigns){
    return args -> {
      var admin = users.findByEmail("admin@mecenas.app").orElseGet(()->{ var u=new User(); u.setEmail("admin@mecenas.app"); u.setRoles("ADMIN"); return users.save(u); });
      var author = users.findByEmail("autor@mecenas.app").orElseGet(()->{ var u=new User(); u.setEmail("autor@mecenas.app"); u.setRoles("AUTHOR"); return users.save(u); });
      var partner = users.findByEmail("parceiro1@cnpj.app").orElseGet(()->{ var u=new User(); u.setEmail("parceiro1@cnpj.app"); u.setRoles("PARTNER"); return users.save(u); });
      var w = works.findAll().stream().findFirst().orElseGet(()->{ var ww=new Work(); ww.setAuthor(author); ww.setTitulo("Romance das Estações"); ww.setCategoria("narrativa"); ww.setDescricao("Uma história sobre mudanças, perdas e novos começos."); return works.save(ww); });
      if (demos.findAll().isEmpty()){ var d=new Demo(); d.setWork(w); d.setMimeType("image/svg+xml"); d.setStorageUrl("https://upload.wikimedia.org/wikipedia/commons/3/3f/Placeholder_view_vector.svg"); demos.save(d); }
      campaigns.findByWorkId(w.getId()).orElseGet(()->{ var c=new Campaign(); c.setWork(w); c.setMetaBrl(200000); c.setMinPledgeBrl(500); c.setDeadlineAt(Instant.now().plusSeconds(2592000)); return campaigns.save(c); });
    };
  }
}
