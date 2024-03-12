package model.oefening2;

import java.util.*;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of wallets involving a number of purchases of
 * shares.
 * 
 * @invar   Each wallet must have a proper sequence of purchases.
 *          | hasProperPurchases()
 * @invar   Each wallet must have a proper number of purchases.
 *          | canHaveAsNbPurchases(getNbPurchases())
 */
public class Wallet {

    /**
     * Variable referencing a map collecting all the purchases
     * registered in this wallet.
     * 
     * @invar   The referenced map is effective.
     *          | purchases != null
     * @invar   Each key registered in the map is an
     *          acceptable code for a share.
     *          | for each object in Object:
     *          |   if (purchases.containsKey(object))
     *          |       then ( (object instanceof String) &&
     *          |           (model.oefening2.Share.canHaveAsCode((String)object)) )
     * @invar   Each value registered in the hash map is an
     *          effective list of purchases referencing this wallet.
     *          | for each object in Object:
     *          |   if (purchases.containsValue(object))
     *          |       then ( (object instanceof List<model.oefening2.Purchase>) &&
     *          |           for each purchase in object:
     *          |               ((model.oefening2.Purchase)object).getWallet() == this )
     */
    private Map<String, List<Purchase>> purchases = new HashMap<>();
    
    
    /***************
     * CONSTRUCTOR *
     ***************/
    
    /**
     * Initialize this new wallet without any purchases yet.
     *
     * @post    No purchases are registered yet in this new wallet.
     *          | new.getNbPurchases() == 0
     * @post    The new wallet is not yet terminated.
     *          | ! isTerminated()
     */
    public Wallet() {}
    
    
    /***************
     * TERMINATION *
     ***************/
    
    /**
     * Check whether this wallet is already terminated.
     */
    public boolean isTerminated() {
        return purchases == null;
    }
    
    /**
     * Terminate this wallet.
     *
     * @post    This wallet is terminated.
     *          | new.isTerminated()
     * @post    No non-terminated purchase exists that still
     *          belongs to this wallet.
     *          | for each purchase in model.oefening2.Purchase:
     *          |   if (! (new purchase).isTerminated())
     *          |       then (purchase.getWallet() != this)
     */
    public void terminate() {
        if (! isTerminated()) {

            // Terminate all purchases present in this wallet.
            for (List<Purchase> purchaseList : this.purchases.values()) {
                for (Purchase purchase : purchaseList) {
                    if (purchase != null) {
                        purchase.terminate();
                    }
                }
            }

            // In order to avoid an extra instance variable, the
            // instance variable 'purchases' is set to the null
            // reference as soon as a wallet is terminated.
            purchases = null;
        }
    }

    /**
     * Return the number of purchases registered in this wallet.
     * 
     * @return  The number of all the purchases that are registered in
     *          this wallet.
     *          | result ==
     *          |   card ( { purchase in model.oefening2.Purchase: hasAsPurchase(purchase) } )
     */
    @Raw
    public int getNbPurchases() {
        int nbPurchases = 0;

        for (List<Purchase> purchaseList : this.purchases.values()) {
            nbPurchases += purchaseList.size();
        }

        return nbPurchases;
    }

    /**
     * Check whether this wallet can have the given purchase
     * as one of its purchases.
     * 
     * @param   purchase  The purchase to check.
     * @return  True if the given purchase is effective, not already
     *          terminated and references this wallet as its wallet;
     *          false otherwise.
     *          | result ==
     *          |   (purchase != null) &&
     *          |   (! purchase.isTerminated()) &&
     *          |   (purchase.getWallet() == this)
     */
    @Raw
    public boolean canHaveAsPurchase(Purchase purchase) {
        try {
            return (!purchase.isTerminated()) &&
                (purchase.getWallet() == this);
        }
        catch (NullPointerException exc) {
            // The given purchase is not effective.
            return false;
        }
    }

    /**
     * Check whether all the purchases registered in this wallet are 
     * proper purchases for this wallet.
     * 
     * @return  True if this wallet can have each of its purchases as
     *          one of its purchases; false otherwise.
     *          | result ==
     *          |   for each purchase in model.oefening2.Purchase:
     *          |       if (hasAsPurchase(purchase))
     *          |           then canHaveAsPurchase(purchase)
     */
    public boolean hasProperPurchases() {
        for (List<Purchase> purchaseList : this.purchases.values()) {
            for (Purchase purchase : purchaseList) {
                if (!canHaveAsPurchase(purchase)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check whether this wallet has the given purchase as one
     * of its purchases.
     */
    public boolean hasAsPurchase(Purchase purchase) {
        try {
            return this.purchases.containsKey(purchase.getShare().getCode()) &&
                this.purchases.get(purchase.getShare().getCode()).contains(purchase);
        }
        catch (NullPointerException exc) {
            // The given share is not effective.
            return false;
        }
    }

    /**
     * Add the given purchase to the purchases registered in
     * this wallet.
     * 
     * @param   purchase  The purchase to be added.
     * @pre     This wallet must be able to accept the given purchase
     *          as one of its purchases.
     *          | canHaveAsPurchase(purchase)
     * @pre     This wallet has no purchases of the share involved
     *          in the given purchase.
     *          | (! hasItemsOf(purchase.getShare())
     * @post    The given purchase is registered as one of the
     *          purchases for this wallet.
     *          | new.hasAsPurchase(purchase)
     */
    void addPurchase(Purchase purchase) {
        String code = purchase.getShare().getCode();

        if (!this.purchases.containsKey(code)) {
            // Initialize a new list of purchases for this specific share
            this.purchases.put(code, new ArrayList<>());
        }
        this.purchases.get(code).add(purchase);
    }

    /**
     * Remove the given purchase from the purchases registered in
     * this wallet.
     * 
     * @param   purchase
     *          The purchase to be removed.
     * @pre     The given purchase is registered as one of the purchases
     *          for this wallet.
     *          | hasAsPurchase(purchase)
     * @post    This wallet no longer has the given purchase as one
     *          of its purchases.
     *          | ! new.hasAsPurchase(this)
     */
    void removePurchase(Purchase purchase) {
        String code = purchase.getShare().getCode();
        this.purchases.get(code).remove(purchase);

        // Remove the list from our purchases map if it does
        // not contain purchases anymore
        if (this.purchases.get(code).isEmpty()) {
            this.purchases.remove(code);
        }
    }
    

 
    public boolean hasItemsOf(Share share) {
        return false;
    }

}