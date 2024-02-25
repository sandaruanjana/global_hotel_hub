package ac.uk.bolton.globalhotelhub.dto;

import lombok.Data;

@Data
public class BookingResponseDTO {
    String title;
    String url;
    String image_url;
    String address;
    String price;
    String description;
    String source;

}
