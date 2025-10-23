
package app.mecenas.server.domain;
import app.mecenas.server.security.Role;
import java.util.Set;
import jakarta.persistence.*; import lombok.Getter; import lombok.Setter; import lombok.EqualsAndHashCode;
@Entity @Table(name="users") @Getter @Setter @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User { @EqualsAndHashCode.Include @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false, unique=true) private String email;
  @jakarta.persistence.ElementCollection(fetch = jakarta.persistence.FetchType.EAGER) @jakarta.persistence.CollectionTable(name="user_roles", joinColumns=@jakarta.persistence.JoinColumn(name="user_id")) @jakarta.persistence.Enumerated(jakarta.persistence.EnumType.STRING) @jakarta.persistence.Column(name="role", nullable=false) private java.util.Set<Role> roles = java.util.Set.of(Role.READER);
}
