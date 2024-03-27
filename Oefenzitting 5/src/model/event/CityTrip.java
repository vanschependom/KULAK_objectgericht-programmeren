package model.event;

import java.math.BigDecimal;


public class CityTrip extends Event {
    // The price of the city trip
    private final BigDecimal price;

    // The mode of transportation to reach the city
    private TransportationMode transportationMode;


    /**
     * Initialize a new city trip event.
     * @param category        The event category this city trip belongs to.
     * @param location        The city that will be visited.
     * @param duration        The duration of the city visit, expressed in number of minutes.
     * @param price           The price of the city trip.
     * @param transportation  The mode of transportation to reach the city.
     */
    public CityTrip(EventCategory category, String location, int duration,
                    BigDecimal price, TransportationMode transportation) {
        super(category, location, duration);
        this.price = price;
        this.setTransportationMode(transportation);
    }


    /**
     * Get the price of this city trip.
     */
    public BigDecimal getPrice() {
        return this.price;
    }

    /**
     * Get the mode of transportation for this city trip.
     */
    public TransportationMode getTransportationMode() {
        return this.transportationMode;
    }


    /**
     * Change the mode of transportation for this city trip.
     * @param mode  The new transportation mode.
     */
    public void changeTransportationMode(TransportationMode mode) {
        this.setTransportationMode(mode);
    }


    /**
     * Set the transportation mode for this city trip.
     */
    private void setTransportationMode(TransportationMode mode) {
        this.transportationMode = mode;
    }


    @Override
    public String getSummary() {
        return String.format("City trip %s with price %s and transportation mode %s.",
                super.getSummary(), this.getPrice().toString(), this.getTransportationMode().toString().toLowerCase());
    }

    @Override
    public void sendNotification() {
        System.out.println(this.getSummary());
    }


    public enum TransportationMode {BIKE, TRAIN, CAR}
}
