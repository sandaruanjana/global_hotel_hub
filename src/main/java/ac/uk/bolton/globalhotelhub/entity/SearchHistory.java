package ac.uk.bolton.globalhotelhub.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

import static jakarta.persistence.GenerationType.AUTO;

@Data
@Entity
@Table(name = "search_history")
public class SearchHistory {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(unique = false, nullable = true)
    private String location;

    @Column(unique = false, nullable = true)
    private String check_in;

    @Column(unique = false, nullable = true)
    private String checkout;

    @Column(unique = false, nullable = true)
    private String rooms;

    @Column(unique = false, nullable = true)
    private String adults;

    @Column(unique = false, nullable = true)
    private String child;

    @Column(unique = false, nullable = true)
    private String source;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp created_at;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updated_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
