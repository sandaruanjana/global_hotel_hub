package ac.uk.bolton.globalhotelhub.controller;

import ac.uk.bolton.globalhotelhub.dto.BookingHotelFilterRequestDTO;
import ac.uk.bolton.globalhotelhub.service.HotelService;
import ac.uk.bolton.globalhotelhub.util.AjaxResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping("/search")
    public AjaxResponse<Object> search(@RequestBody(required = false) BookingHotelFilterRequestDTO bookingHotelFilterRequestDTO) {
        return hotelService.getHotels(bookingHotelFilterRequestDTO);
    }

    @GetMapping("/{id}")
    public AjaxResponse<Object> getById(@PathVariable Long id) {
        return hotelService.getById(id);
    }

}
