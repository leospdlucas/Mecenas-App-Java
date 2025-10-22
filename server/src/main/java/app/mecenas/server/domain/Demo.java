
package app.mecenas.server.domain;
import jakarta.persistence.*; import lombok.Data;
@Entity @Table(name="demos") @Data
public class Demo { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @ManyToOne @JoinColumn(name="work_id") private Work work;
  private String storageUrl; private String mimeType;
}
