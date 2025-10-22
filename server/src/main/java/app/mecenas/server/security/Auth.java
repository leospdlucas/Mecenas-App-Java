
package app.mecenas.server.security;
import app.mecenas.server.domain.User; import app.mecenas.server.repo.UserRepo; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.stereotype.Component; import java.util.Optional;
@Component public class Auth { private final UserRepo users; public Auth(UserRepo users){ this.users=users; }
  public Optional<User> currentUser(){ var a=SecurityContextHolder.getContext().getAuthentication(); if(a==null||a.getName()==null) return Optional.empty(); return users.findByEmail(a.getName()); }
  public boolean hasRole(User u, String role){ if(u==null||u.getRoles()==null) return false; for(String r:u.getRoles().split(",")) if(r.trim().equalsIgnoreCase(role)) return true; return false; }
}
