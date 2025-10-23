
package app.mecenas.server.domain;
import jakarta.persistence.*; import lombok.Data; import java.time.Instant;
@Entity
@Table(name="subscriptions")
@Data
public class Subscription {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;

  @ManyToOne
  @JoinColumn(name="org_user_id") private User orgUser;
  private String plano; private Integer precoBrl; private Instant currentPeriodEnd; private String status = "ACTIVE";
}
