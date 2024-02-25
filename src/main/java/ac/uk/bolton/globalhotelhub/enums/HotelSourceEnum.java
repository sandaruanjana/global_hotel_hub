package ac.uk.bolton.globalhotelhub.enums;

public enum HotelSourceEnum {
    BOOKINGCOM("Booking.com");

    private String name;

    HotelSourceEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}