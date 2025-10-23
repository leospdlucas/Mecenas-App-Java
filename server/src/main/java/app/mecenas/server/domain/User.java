
package app.mecenas.server.domain;
<<<<<<< HEAD
import jakarta.persistence.*; import lombok.Data;
@Entity
@Table(name="users")
@Data
public class User {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;

  @Column(nullable=false, unique=true) private String email;

  @Column(nullable=false) private String roles = "READER";
=======
import app.mecenas.server.security.Role;
import java.util.Set;
import jakarta.persistence.*; import lombok.Getter; import lombok.Setter; import lombok.EqualsAndHashCode;
@Entity @Table(name="users") @Getter @Setter @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User { @EqualsAndHashCode.Include @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @Column(nullable=false, unique=true) private String email;
  @jakarta.persistence.ElementCollection(fetch = jakarta.persistence.FetchType.EAGER) @jakarta.persistence.CollectionTable(name="user_roles", joinColumns=@jakarta.persistence.JoinColumn(name="user_id")) @jakarta.persistence.Enumerated(jakarta.persistence.EnumType.STRING) @jakarta.persistence.Column(name="role", nullable=false) private java.util.Set<Role> roles = java.util.Set.of(Role.READER);
>>>>>>> 1f6bccc1ecdc206a9380947cbc827c5f8dfabbd1
}
