package model.oefening2;

import java.util.Set;
import java.util.HashSet;

import be.kuleuven.cs.som.annotate.Raw;


/**
 * A class a shares involving a code, a value and a set of
 * purchases in which they are involved.
 * 
 * @invar   Each share must be able to have its code as
 *          its code.
 *          | canHaveAsCode(getCode())
 * @invar   Each share must be able to have its value as
 *          its value.
 *          | canHaveAsValue(getValue())
 */
public class Share {

    /**
     * Variable referencing the code ascribed to this share.
     * 
     * @invar   A share must be able to have the referenced code
     *          as its code.
     *          | canHaveAsCode(code)
     */
    private final String code;
    

    /**
     * Variable referencing the value of this share.
     * 
     * @invar   The referenced value must be an acceptable value
     *          for a share.
     *          | canHaveAsValue(value) 
     */
    private MoneyAmount value;
    

    /**
     * Variable referencing a set collecting all the purchases in
     * which this share is involved.
     * 
     * @invar   The referenced set of purchases is effective.
     *          | purchases != null
     * @invar   No effective purchase registered in the set of
     *          purchases is already terminated.
     *          | for each purchase in purchases:
     *          |   if (purchase != null)
     *          |       then (! purchase.isTerminated()) 
     */
    private Set<Purchase> purchases = new HashSet<>();
    
    
    
    /****************
     * CONSTRUCTORS *
     ****************/
    
    /**
     * Initialize this new share with given code and given initial
     * value.
     * 
     * @param   code  The code for this new share.
     * @param   value The value for this new share.
     * @post    This new purchase is not yet terminated.
     *          | ! new.isTerminated()
     * @post    The code for this new share is set to the given code.
     *          | new.getCode() == code
     * @post    The value for this new share is set to a value equal
     *          to the given value.
     *          | new.getValue().isEqualTo(value)
     * @post    This new share is not yet involved in any purchases.
     *          | new.getNbPurchases() == 0
     * @throws  IllegalArgumentException
     *          A share cannot have the given code as its code.
     *          | ! canHaveAsCode(code)
     * @throws  IllegalArgumentException
     *          A share cannot have the given value as its value.
     *          | ! canHaveAsValue(value)
     */
    public Share(String code, MoneyAmount value) throws IllegalArgumentException {
        if (!canHaveAsCode(code)) {
            throw new IllegalArgumentException("Illegal code for share!");
        }
        if (!canHaveAsValue(value)) {
            throw new IllegalArgumentException("Illegal vaue for share!");
        }
        this.code = code;
        this.value = value;
    }
    
    
    /***************
     * TERMINATION *
     ***************/
    
    /**
     * Check whether this share has been terminated.
     */
    @Raw
    public boolean isTerminated() {
        return purchases == null;
    }
    
    /**
     * Terminate this share.
     *
     * @post    This share is terminated.
     *          | new.isTerminated()
     * @post    No non-terminated purchase exists that still
     *          belongs refers to this share.
     *          | for each purchase in model.oefening2.Purchase:
     *          |   if (! (new purchase).isTerminated())
     *          |       then (purchase.getShare() != this)
     */
    public void terminate() {
        if (! isTerminated()) {
            for (Purchase purchase : purchases) {
                if (purchase != null) {
                    purchase.terminate();
                }
            }
            
            // In order to avoid an extra instance variable, the
            // instance variable 'purchases' is set to the null
            // reference as soon as a share is terminated.
            purchases = null;
        }
    }

    /**
     * Get the code of this share.
     */
    public String getCode() {
        return code;
    }

    
    /**
     * Check whether a share can have the given code as
     * its code.
     * 
     * @param   code  The code to check.
     * @return  True if the given code is an effective sequence
     *          of 4 letters or digits; false otherwise.
     *          | result == (code != null) && code.matches("\\w{4}")
     */
    public static boolean canHaveAsCode(String code) {
        try {
            return code.matches("\\w{4}");
        }
        catch (NullPointerException exc) {
            // The given code is not effective.
            return false;
        }
    }


 
    
    /*********
     * VALUE *
     *********/

    /**
     * Return the current value of this share. 
     */
    public MoneyAmount getValue() {
        // A copy of the instance variable must be returned
        // in order to avoid undisciplined changes to that
        // value outside the scope of this class.
        return value.getCopy();
    }

    /**
     * Check whether a share can have the given value as its
     * share.
     * 
     * @param   value  The value to check.
     * @return  True if the given value is effective and not
     *          negative; false otherwise.
     *          | result == (value != null) && (! value.isNegative())
     */
    public static boolean canHaveAsValue(MoneyAmount value) {
        try {
            return !value.isNegative();
        }
        catch (NullPointerException exc) {
            // The given value is not effective.
            return false;
        }
    }

    /**
     * Change the value of this share to the given value.
     * 
     * @param   value  The new value for this share.
     * @post    The value of this share is set to a value that is
     *          equal to the given value.
     *          | new.getValue().isEqualTo(value)
     * @throws  IllegalArgumentException
     *          The given value is not an acceptable value for a share.
     *          | ! canHaveAsValue(value)
     */
    public void changeValue(MoneyAmount value) throws IllegalArgumentException {
        if (!canHaveAsValue(value)) {
            throw new IllegalArgumentException("Inappropriate value!");
        }
        setValue(value);
    }
    
    /**
     * Set the value of this share to the given value.
     * 
     * @param   value  The new value for this share.
     * @post    The value of this share is set to a value that is
     *          equal to the given value.
     *          | new.getValue().isEqualTo(value)
     */
    private void setValue(MoneyAmount value) {
        // A copy of the given value must be registered in order
        // to avoid changes to that value outside the scope of
        // this class.
        this.value = value.getCopy();
    }
    
    
    /*************
     * PURCHASES *
     *************/
    
    /**
     * Check whether this share has the given purchase as one of its
     * purchases.
     * 
     * @param purchase  The purchase to check.
     */
    @Raw
    public boolean hasAsPurchase(Purchase purchase) {
        // Because the method is qualified raw, it is possible that
        // the array of purchases has not been constructed yet.
        if (this.purchases == null) {
            return false;
        }
        if (purchase == null) {
            return false;
        }

        return this.purchases.contains(purchase);
    }
    
    /**
     * Check whether this share can have the given purchase
     * as one of its purchases.
     * 
     * @param purchase  The purchase to check.
     * @return  True if the given purchase is effective, not already
     *          terminated and references this share as its share;
     *          false otherwise.
     *          | result ==
     *          |   (purchase != null) &&
     *          |   (! purchase.isTerminated()) &&
     *          |   (purchase.getShare() == this)
     */
    @Raw
    public boolean canHaveAsPurchase(Purchase purchase) {
        try {
            return (!purchase.isTerminated()) &&
                    (purchase.getShare() == this);
        } catch (NullPointerException exc) {
            // The given purchase is not effective.
            return false;
        }
    }

    /**
     * Check whether all the purchases registered for this share are 
     * proper purchases for this share.
     * 
     * @return  True if this share can have each of its purchases as
     *          one of its purchases; false otherwise.
     *          | result ==
     *          |   for each purchase in model.oefening2.Purchase:
     *          |       if (hasAsPurchase(purchase))
     *          |           then canHaveAsPurchase(purchase)
     */
    public boolean hasProperPurchases() {
        for (Purchase purchase : this.purchases) {
            if (( purchase != null) && (!canHaveAsPurchase(purchase)) ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return the number of purchases registered for this share.
     * 
     * @return  The number of all the purchases that are registered for
     *          this share.
     *          | result ==
     *          |   card ( { purchase in model.oefening2.Purchase: hasAsPurchase(purchase) } )
     */
    @Raw
    public int getNbPurchases() {
        // Because the method is qualified raw, it is possible that
        // the array of purchases has not been constructed yet.
        if (purchases == null) {
            return 0;
        }
        
        return this.purchases.size();
    }
    
    /**
     * Add the given purchase to the purchases registered for
     * this share.
     * 
     * @param   purchase  The purchase to be added.
     * @pre     This share must be able to accept the given purchase
     *          as one of its purchases.
     *          | canHaveAsPurchase(purchase)
     * @pre     The given purchase may not have been registered yet
     *          for this share.
     *          | (! hasAsPurchase(purchase))
     * @post    The given purchase is registered as one of the
     *          purchases for this wallet.
     *          | new.hasAsPurchase(purchase)
     */
    void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
    }
    
    /**
     * Remove the given purchase from the purchases registered for
     * this share.
     * 
     * @param   purchase  The purchase to be removed.
     * @pre     The given purchase is registered as one of the purchases
     *          for this share.
     *          | hasAsPurchase(purchase)
     * @post    This share no longer has the given purchase as one
     *          of its purchases.
     *          | ! new.hasAsPurchase(this)
     */
    void removePurchase(Purchase purchase) {
        this.purchases.remove(purchase);
    }
}
