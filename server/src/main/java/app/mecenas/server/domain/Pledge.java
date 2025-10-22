
package app.mecenas.server.domain;
import jakarta.persistence.*; import lombok.Data;
@Entity @Table(name="pledges") @Data
public class Pledge { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @ManyToOne @JoinColumn(name="campaign_id") private Campaign campaign;
  @ManyToOne @JoinColumn(name="user_id") private User user;
  private Integer valorBrl; private String status = "PENDING";
}
