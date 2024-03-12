package eigen.oefening2;
import be.kuleuven.cs.som.annotate.*;

public class Stock {

    private float price;
    private final String companyCode;

    /**
     * @param   price
     *          The price of the stock
     * @param   companyCode
     *          The company code of the stock
     * @pre     The price must be a positive number
     *          | price > 0
     */
    public Stock(float price, String companyCode) {
        this.companyCode = companyCode;
        this.price = price;
    }

    /**
     * Setter for the price.
     * @param   price
     *          The price to be set
     * @pre     The price must be a positive number
     *          | price > 0
     */
    public void setPrice(float price) {
        this.price = price;
    }

    @Basic
    public float getPrice() {
        return price;
    }

}
