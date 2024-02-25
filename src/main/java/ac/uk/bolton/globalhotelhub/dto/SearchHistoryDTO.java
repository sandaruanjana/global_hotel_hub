package ac.uk.bolton.globalhotelhub.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchHistoryDTO {

    private Long id;

    private String location;

    private String check_in;

    private String checkout;

    private String rooms;

    private String adults;

    private String child;

    private String source;

    private Timestamp created_at;

    private Timestamp updated_at;
}
