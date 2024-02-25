package ac.uk.bolton.globalhotelhub.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

import static jakarta.persistence.GenerationType.AUTO;

@Data
@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(unique = false, nullable = true)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String url;

    @Column(unique = false, nullable = true)
    private String image_url;

    @Column(unique = false, nullable = true)
    private String address;

    @Column(unique = false, nullable = true)
    private String price;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String description;

    @Column(unique = false, nullable = true)
    private String source;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp created_at;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updated_at;



}
