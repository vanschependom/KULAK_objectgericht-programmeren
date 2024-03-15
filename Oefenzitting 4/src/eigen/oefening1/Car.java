package eigen.oefening1;
import java.time.LocalDate;

public class Car extends Property {

    private LocalDate purchaseDate;
    private float cc;

    public Car(float value, float cc) {
        super(value);
        setCc(cc);
    }

    private void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setCc(float cc) {
        this.cc = cc;
    }

    public float getCc() {
        return cc;
    }

    /**
     * The value of a car lowers from 100% to 50% over 5 years.
     * @return
     */
    @Override
    public float getValue() {
        float v = super.getValue();
        if (purchaseDate == null) {
            return v;
        } else {
            int years = LocalDate.now().getYear() - purchaseDate.getYear();
            if (years > 5) {
                return v / 2;
            } else {
                return v * (1 - years / 10);
            }
        }
    }

}
