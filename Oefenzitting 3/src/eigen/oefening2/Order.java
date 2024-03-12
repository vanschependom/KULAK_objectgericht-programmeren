package eigen.oefening2;
import be.kuleuven.cs.som.annotate.*;
import java.util.List;
import java.util.ArrayList;

public class Order {

    private final int amountOfStocks;
    private float maxPrice;
    private List<Stock> stocks = new ArrayList<>();
    private boolean isComplete = false;

    public Order(int amountOfStocks, float maxPrice) {
        setMaxPrice(maxPrice);
        this.amountOfStocks = amountOfStocks;
    }

    /**
     * @pre     The maxprice must be a positive number.
     *          | maxPrice > 0
     * @param   maxPrice
     *          The max price to be set
     */
    public void setMaxPrice(float maxPrice) {
        if (!isComplete) {
            this.maxPrice = maxPrice;
        }
    }

    public void complete() {
        isComplete = true;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    /**
     * Add a stock to the order.
     * @param   stock
     * @post    The stock is added to the order if it is a legal stock.
     *          | if (canHaveAsStock(stock))
     *          |     then new.contains(stock) == true
     */
    public void addStock(Stock stock) {
        if (canHaveAsStock(stock)) {
            stocks.add(stock);
        }
    }

    public boolean canHaveAsStock(Stock stock) {
        return !isComplete
                && stock != null
                && stock.getPrice() <= maxPrice
                && !stocks.contains(stock);
    }

}
