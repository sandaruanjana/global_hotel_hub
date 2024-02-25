package ac.uk.bolton.globalhotelhub.service;

import ac.uk.bolton.globalhotelhub.dto.BookingHotelFilterRequestDTO;
import ac.uk.bolton.globalhotelhub.util.AjaxResponse;

public interface HotelService {
    AjaxResponse<Object> getHotels(BookingHotelFilterRequestDTO bookingHotelFilterRequestDTO);

    AjaxResponse<Object> getById(Long id);
}
