package ac.uk.bolton.globalhotelhub.dto;

import lombok.Data;

@Data
public class BookingHotelFilterRequestDTO {
    private Long user_id;
    private String location;
    private String check_in;
    private String checkout;
    private String rooms;
    private String adults;
    private String child;
    private String max_price;
    private String min_price;
}
