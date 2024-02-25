package ac.uk.bolton.globalhotelhub.service.impl;

import ac.uk.bolton.globalhotelhub.constant.BookingConstant;
import ac.uk.bolton.globalhotelhub.dto.BookingHotelFilterRequestDTO;
import ac.uk.bolton.globalhotelhub.dto.BookingResponseDTO;
import ac.uk.bolton.globalhotelhub.entity.Hotel;
import ac.uk.bolton.globalhotelhub.entity.SearchHistory;
import ac.uk.bolton.globalhotelhub.entity.User;
import ac.uk.bolton.globalhotelhub.enums.HotelSourceEnum;
import ac.uk.bolton.globalhotelhub.repository.HotelRepository;
import ac.uk.bolton.globalhotelhub.repository.SearchHistoryRepository;
import ac.uk.bolton.globalhotelhub.service.HotelService;
import ac.uk.bolton.globalhotelhub.service.UserService;
import ac.uk.bolton.globalhotelhub.util.AjaxResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Log4j2
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    private final UserService userService;

    private final SearchHistoryRepository searchHistoryRepository;

    List<BookingResponseDTO> bookingResponseList = new ArrayList<>();

    @Override
    public AjaxResponse<Object> getHotels(BookingHotelFilterRequestDTO bookingHotelFilterRequestDTO) {

        boolean isUserLogged = false;
        boolean isSearch = true;


        try {
            userService.getCurrentUser();
            isUserLogged = true;
        } catch (Exception e) {
            isUserLogged = false;
            e.printStackTrace();
        }


        BookingHotelFilterRequestDTO defaultRequest = new BookingHotelFilterRequestDTO();

        if (bookingHotelFilterRequestDTO == null) {
            defaultRequest.setLocation("Sri+Lanka");
            defaultRequest.setCheck_in("2024-02-29");
            defaultRequest.setCheckout("2024-03-01");
            defaultRequest.setRooms("1");
            defaultRequest.setAdults("1");
            defaultRequest.setChild("0");
            defaultRequest.setMax_price("5000000");
            defaultRequest.setMin_price("0");

            bookingHotelFilterRequestDTO = defaultRequest;
            isSearch = false;
        }


        if (isUserLogged && isSearch) {

            User user = new User();
            user.setId(userService.getCurrentUser().getId());

            SearchHistory searchHistory = new SearchHistory();
            searchHistory.setUser(user);
            searchHistory.setLocation(bookingHotelFilterRequestDTO.getLocation());
            searchHistory.setCheck_in(bookingHotelFilterRequestDTO.getCheck_in());
            searchHistory.setCheckout(bookingHotelFilterRequestDTO.getCheckout());
            searchHistory.setRooms(bookingHotelFilterRequestDTO.getRooms());
            searchHistory.setAdults(bookingHotelFilterRequestDTO.getAdults());
            searchHistory.setChild(bookingHotelFilterRequestDTO.getChild());
            searchHistory.setCreated_at(Timestamp.valueOf(java.time.LocalDateTime.now()));
            searchHistory.setUpdated_at(Timestamp.valueOf(java.time.LocalDateTime.now()));
            searchHistory.setSource(HotelSourceEnum.BOOKINGCOM.getName());
            searchHistoryRepository.save(searchHistory);

        }

        bookingHotelFilterRequestDTO.setLocation(bookingHotelFilterRequestDTO.getLocation() == null ? "Sri+Lanka" : bookingHotelFilterRequestDTO.getLocation());
        bookingHotelFilterRequestDTO.setCheck_in(bookingHotelFilterRequestDTO.getCheck_in() == null ? "2024-02-29" : bookingHotelFilterRequestDTO.getCheck_in());
        bookingHotelFilterRequestDTO.setCheckout(bookingHotelFilterRequestDTO.getCheckout() == null ? "2024-03-01" : bookingHotelFilterRequestDTO.getCheckout());
        bookingHotelFilterRequestDTO.setRooms(bookingHotelFilterRequestDTO.getRooms() == null ? "1" : bookingHotelFilterRequestDTO.getRooms());
        bookingHotelFilterRequestDTO.setAdults(bookingHotelFilterRequestDTO.getAdults() == null ? "0" : bookingHotelFilterRequestDTO.getAdults());
        bookingHotelFilterRequestDTO.setChild(bookingHotelFilterRequestDTO.getChild() == null ? "0" : bookingHotelFilterRequestDTO.getChild());
        bookingHotelFilterRequestDTO.setMax_price(bookingHotelFilterRequestDTO.getMax_price() == null ? "50000000" : bookingHotelFilterRequestDTO.getMax_price());
        bookingHotelFilterRequestDTO.setMin_price(bookingHotelFilterRequestDTO.getMin_price() == null ? "0" : bookingHotelFilterRequestDTO.getMin_price());


        String bookingComUrl = String.format(BookingConstant.BOOKING_COM_SEARCH_URL, bookingHotelFilterRequestDTO.getLocation(), bookingHotelFilterRequestDTO.getCheck_in(), bookingHotelFilterRequestDTO.getCheckout(), bookingHotelFilterRequestDTO.getAdults(), bookingHotelFilterRequestDTO.getRooms(), bookingHotelFilterRequestDTO.getChild(), bookingHotelFilterRequestDTO.getMin_price(), bookingHotelFilterRequestDTO.getMax_price());

        parseBookingComSearchResults(bookingComUrl);

        if (isSearch && bookingHotelFilterRequestDTO.getLocation() != null) {


            return AjaxResponse.success(hotelRepository.findByAddress(bookingHotelFilterRequestDTO.getLocation()));
        }

        return AjaxResponse.success(hotelRepository.findAll());

    }

    @Override
    public AjaxResponse<Object> getById(Long id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);

        if (hotel.isPresent()) {
            return AjaxResponse.success(hotel);
        }

        return AjaxResponse.error("Hotel not found");
    }

    private void parseBookingComSearchResults(String bookingComUrl) {
        try {
            Document bookingPageDocument = Jsoup.connect(bookingComUrl).get();
            Elements accommodationItems = bookingPageDocument.select("div[data-testid=property-card]"); // Assuming this is the relevant class

            if (accommodationItems.isEmpty()) {
                log.info("No Accommodation Found");
                return;
            }

            for (Element accommodationItem : accommodationItems) {
                String accommodationTitle = accommodationItem.select("[data-testid=title]").text();
                if (!accommodationTitle.isEmpty()) {
                    BookingResponseDTO bookingResponse = new BookingResponseDTO();

                    bookingResponse.setTitle(accommodationTitle);

                    String url = accommodationItem.select("[data-testid=title-link]").first().attr("href");
                    String image_url = accommodationItem.select("[data-testid=image]").first().attr("src").replace("square200", "square600");
                    String address = accommodationItem.select("[data-testid=address]").first().text();

                    String price = "0.00";

                    if (accommodationItem.select("[data-testid=price-and-discounted-price]").first() != null) {
                        price = accommodationItem.select("[data-testid=price-and-discounted-price]").first().text();

                    }

                    String description = accommodationItem.select(".abf093bdfe").text();
                    String source = HotelSourceEnum.BOOKINGCOM.getName();

                    bookingResponse.setUrl(url);
                    bookingResponse.setImage_url(image_url);
                    bookingResponse.setAddress(address);
                    bookingResponse.setPrice(price);

                    bookingResponse.setDescription(description);
                    bookingResponse.setSource(source);

                    Hotel existingHotel = hotelRepository.findByTitleAndSource(accommodationTitle, source);

                    if (existingHotel == null) {

                        Hotel hotel = new Hotel();
                        BeanUtils.copyProperties(bookingResponse, hotel);
                        hotel.setCreated_at(new java.sql.Timestamp(System.currentTimeMillis()));
                        hotel.setUpdated_at(new java.sql.Timestamp(System.currentTimeMillis()));
                        hotelRepository.save(hotel);
                    } else {

                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String extractPriceFromText(String priceText) {
        Pattern pricePattern = Pattern.compile("LKR\\s(\\d{1,3}(,\\d{3})*)(\\.\\d{1,2})?");
        Matcher matcher = pricePattern.matcher(priceText);
        return matcher.find() ? "LKR " + matcher.group(1) : "LKR " + priceText;
    }
}
