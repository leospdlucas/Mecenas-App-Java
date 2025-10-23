
package app.mecenas.server.security;
import app.mecenas.server.domain.User; import app.mecenas.server.repo.UserRepo;
import jakarta.servlet.*; import jakarta.servlet.http.*; import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority; import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component; import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException; import java.util.stream.Collectors;
@Component public class DevAuthFilter extends OncePerRequestFilter {
  private final UserRepo users; public DevAuthFilter(UserRepo users){ this.users=users; }
  @Override protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
    String email = req.getHeader("X-Dev-Email");
    if (email!=null && SecurityContextHolder.getContext().getAuthentication()==null){
      User u = users.findByEmail(email).orElseGet(()->{ var nu=new User(); nu.setEmail(email); return users.save(nu); });
      var auth = new UsernamePasswordAuthenticationToken(u.getEmail(), "N/A",
        Arrays.stream(u.getRoles().split(",")).map(String::trim).filter(s->!s.isEmpty()).map(r->new SimpleGrantedAuthority("ROLE_"+r)).collect(Collectors.toList()));
      SecurityContextHolder.getContext().setAuthentication(auth);
    }
    chain.doFilter(req,res);
  }
}
