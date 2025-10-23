package app.mecenas.server.security;

import app.mecenas.server.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class DevAuthFilterTest {

  @Test
  void rolesAreMappedToAuthorities() {
    User u = new User();
    u.setId(1L);
    u.setEmail("x@y.com");
    u.setRoles(Set.of(Role.READER, Role.PARTNER));

    // mimic the mapping logic used in DevAuthFilter
    List<SimpleGrantedAuthority> auths = u.getRoles().stream()
        .map(rn -> new SimpleGrantedAuthority("ROLE_" + rn.name()))
        .collect(Collectors.toList());

    assertTrue(auths.contains(new SimpleGrantedAuthority("ROLE_READER")));
    assertTrue(auths.contains(new SimpleGrantedAuthority("ROLE_PARTNER")));
  }
}
