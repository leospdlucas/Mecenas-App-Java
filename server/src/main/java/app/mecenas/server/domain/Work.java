
package app.mecenas.server.domain;
import jakarta.persistence.*; import lombok.Data;
@Entity
@Table(name="works")
@Data
public class Work {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;

  @ManyToOne
  @JoinColumn(name="author_id") private User author;
  private String titulo; private String categoria; private String descricao; private String status = "ACTIVE";
}
