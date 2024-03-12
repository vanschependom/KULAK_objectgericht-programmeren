package model.oefening2;

/**
 * A class collecting different currencies used to express
 * amounts of money.
 * 	In its current form, the class only supports Euro and USD.
 * 
 * @note	This class is better split in a hierarchy of classes
 * 			involving a top class of currencies in general, and
 * 			subclasses for each possible currency. This structure
 * 			matches the way enumeration types are supported in
 * 			Java 5.0. Because inheritance has not been covered yet,
 * 			all currencies are collected in a single class.
 */
public class Currency {

    /**
     * Variable referencing the object representing the currency Euro.
     * 
     * @invar	The referenced currency is effective and differs from
     * 			the currency representing the currency USD.
     * 			| (euroInstance != null) && (euroInstance != usdInstance)
     */
    private static final Currency euroInstance = new Currency();

    /**
     * Variable referencing the object representing the currency USD.
     * 
     * @invar	The referenced currency is effective and differs from
     * 			the currency representing the currency Euro.
     * 			| (euroInstance != null) && (euroInstance != euroInstance)
     */
    private static final Currency usdInstance = new Currency();

    /**
     * Variable registering the conversion rate from Euro to USD.
     * 	For the sake of simplicity, the conversion rate between both
     *	currencies is assumed to be constant.
     *
     * @invar	The conversion rate from Euro to USD cannot be negative.
     * 			| Euro2USD >= 0
     */
    public final static int Euro2USD = 12204853;
    
    
    
    /**
     * Initialize this new currency with no characteristics.
     * 
     * @note	The constructor is kept private to prevent ordinary
     * 			users to create their own currencies.
     */
    private Currency() {}
 
    
    /*****************
     * EURO CURRENCY *
     *****************/
    
    /**
     * Return the unique object representing the currency Euro.
     *
     * @return	An effective instance of the class of currencies that
     * 			differs from the currency USD.
     * 			Successive invocations of this method are guaranteed
     * 			to return the same object.
     * 			| (result != null) && (result != getUSD()) 
     */
    public static Currency getEuro() {
        return Currency.euroInstance;
    }
    
    
    
    /****************
     * USD CURRENCY *
     ****************/
    
    /**
     * Return the unique object representing the currency USD.
     *
     * @return	An effective instance of the class of currencies that
     * 			differs from the currency Euro.
     * 			Successive invocations of this method are guaranteed
     * 			to return the same object.
     * 			| (result != null) && (result != getEuro()) 
     */
    public static Currency getUSD() {
        return Currency.usdInstance;
    }
    
    
    /***************
     * CONVERSIONS *
     ***************/

    
    /**
     * Return the conversion rate from USD to Euro.
     * 
     * @return	The inverse of the conversion rate from Euro to USD.
     * 			| result == (int) Math.round((1000000.0/Euro2USD)*1000000)
     */
    public static int USD2Euro() {
        return (int) Math.round((1000000.0 / Currency.Euro2USD) * 10000000);
    }
    
    /**
     * Return the value of 1.000.000 cents of this currency in the other currency.
     * 
     * @return	If this currency is the same as the other currency, 1.000.000 is
     * 			returned.
     * 			| if (this == other) then (result == 1000000)
     * @return	If this currency is the currency Euro and the other is the currency
     * 			USD, the Euro-to-USD rate is returned.
     * 			| if ( (this = getEuro()) && (other == getUSD())
     * 			|	then result = Euro2USD
     * @return	If this currency is the currency USD and the other is the currency
     * 			Euro, the USD-to-Euro rate is returned.
     * 			| if ( (this = getEuro()) && (other == getUSD())
     * 			|	then result = USD2Euro()
     * @throws	IllegalArgumentException
     * 			The given currency is not effective.
     * 			| (other == null)
     */
    public int toCurrency(Currency other) {
        if (other == null) {
            throw new IllegalArgumentException("Non effective currency!");
        }
        if (this == other) {
            return 1000000;
        }

        if (this == Currency.getEuro()) {
            return Currency.Euro2USD;
        }
        else {
            return Currency.USD2Euro();
        }
    }

}
