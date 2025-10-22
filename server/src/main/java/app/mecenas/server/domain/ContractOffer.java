
package app.mecenas.server.domain;
import jakarta.persistence.*; import lombok.Data; import java.time.Instant;
@Entity @Table(name="contract_offers") @Data
public class ContractOffer { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @ManyToOne @JoinColumn(name="work_id") private Work work;
  @ManyToOne @JoinColumn(name="partner_id") private User partner;
  @ManyToOne @JoinColumn(name="author_id") private User author;
  @Column(columnDefinition="text") private String termosJson = "{}"; private String status = "DRAFT"; private Instant expiraEm;
}
