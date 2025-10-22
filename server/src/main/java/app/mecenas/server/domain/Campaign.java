
package app.mecenas.server.domain;
import jakarta.persistence.*; import lombok.Data; import java.time.Instant;
@Entity @Table(name="campaigns") @Data
public class Campaign { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @OneToOne @JoinColumn(name="work_id", unique=true) private Work work;
  private Integer metaBrl; private Integer minPledgeBrl = 500; private Instant deadlineAt;
}
