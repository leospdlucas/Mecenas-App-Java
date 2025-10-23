
package app.mecenas.server.security;
import org.springframework.context.annotation.Bean; import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer; import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain; import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration public class SecurityConfig {

  @Bean SecurityFilterChain filter(HttpSecurity http, DevAuthFilter dev) throws Exception {
    http.csrf(csrf->csrf.disable())
    .addFilterBefore(dev, UsernamePasswordAuthenticationFilter.class)
    .authorizeHttpRequests(a->a.requestMatchers("/", "/works/**", "/api/health", "/api/webhooks/**").permitAll().anyRequest().authenticated())
    .formLogin(Customizer.withDefaults()).logout(Customizer.withDefaults());
    return http.build();
  }
}
