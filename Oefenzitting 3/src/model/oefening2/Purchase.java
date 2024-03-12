package model.oefening2;

import be.kuleuven.cs.som.annotate.Raw;


/**
 * A class of purchases of a number of items of a share. 
 * 
 * @invar   Each purchase must be able to have its number of items
 *          as its number of items.
 *          | model.oefening2.Purchase.canHaveAsNbItems(getNbItems())
 * @invar   Each purchase must be able to have its highest price
 *          as its highest price.
 *          | model.oefening2.Purchase.canHaveAsHighestPrice(getHighestPrice())
 * @invar   Each purchase must have a proper wallet.
 *          | hasProperWallet()
 * @invar   Each purchase must have a proper share.
 *          | hasProperShare()
 */
public class Purchase {

    /**
     * Variable referencing the wallet to which this purchase belongs.
     * 
     * @invar   This purchase must be able to have the referenced wallet
     *          as its wallet.
     *          | model.oefening2.Purchase.canHaveAsWallet(wallet)
     */
    private final Wallet wallet;


    /**
     * Variable referencing the share involved in this purchase.
     * 
     * @invar   This purchase must be able to have the referenced
     *          share as its share.
     *          | model.oefening2.Purchase.canHaveAsShare(share)
     */
    private final Share share;



    /**
     * Variable referening the highest price at which this purchase may
     * be granted.
     * 
     * @invar   This purchase must be able to have the registered highest
     *          price of its items.
     *          | model.oefening2.Purchase.canHaveAsHighestPrice(highestPrice)
     * 
     */
    private final MoneyAmount highestPrice = new MoneyAmount();


    /**
     * Variable registering the number of items for this purchase.
     * 
     * @invar   This purchase must be able to have the registered number
     *          of items as its number of items.
     *          | model.oefening2.Purchase.canHaveAsNbItems(nbItems)
     */
    private int nbItems;


    /**
     * Variable registering whether this purchase has been granted.
     */
    private boolean isGranted;


    /**
     * Variable registering whether this purchase has been terminated.
     */
    private boolean isTerminated;


    /***************
     * CONSTRUCTOR *
     ***************/

    /**
     * Initialize this new purchase for the given wallet of the given
     * number of items of the given share at the given highest price.
     * 
     * @param   wallet
     *          The wallet for this new purchase.
     * @param   nbItems
     *          The number of items for this new purchase.
     * @param   share
     *          The share involved in this new purchase.
     * @param   highestPrice
     *          The highest price at which the given share may be
     *          granted.
     * @post    The wallet to which this new purchase belongs is set
     *          to the given wallet.
     *          | new.getWallet() == wallet
     * @post    The given wallet has this new purchase as one of its
     *          purchases.
     *          | (new wallet).hasAsPurchase(this)
     * @post    The number of items for this new purchase is set to
     *          the given number of items.
     *          | new.getNbItems() == nbItems
     * @post    The highest price at which this new purchase may be granted
     *          is set to equal to the given highest price.
     *          | new.getHighestPrice().isEqualTo(highestPrice)
     * @post    The share involved in this new purchase is set
     *          to the given share.
     *          | new.getShare() == share
     * @post    The given share has this new purchase as one of its
     *          purchases.
     *          | (new share).hasAsPurchase(this)
     * @post    This new purchase is not yet granted nor terminated.
     *          | (! new.isGranted()) && (! new.isTerminated())
     * @throws IllegalArgumentException
     *          This new purchase cannot have the given wallet as its
     *          wallet.
     *          ! model.oefening2.Purchase.canHaveAsWallet(wallet)
     * @throws  IllegalArgumentException
     *          This purchase cannot have the given number of items
     *          as its number of items.
     *          | ! model.oefening2.Purchase.canHaveAsNbItems(nbItems)
     * @throws IllegalArgumentException
     *          This new purchase cannot have the given share as its
     *          share.
     *          ! model.oefening2.Purchase.canHaveAsShare(share)
     * @throws  IllegalArgumentException
     *          This new purchase cannot have the given highest price as
     *          its highest price.
     *          | ! model.oefening2.Purchase.canHaveAsHighestPrice(highestPrice)
     */
    public Purchase(Wallet wallet, int nbItems, Share share,
            MoneyAmount highestPrice) throws IllegalArgumentException {
        if (!Purchase.canHaveAsWallet(wallet)) {
            throw new IllegalArgumentException("Non-appropriate wallet!");
        }
        if (!Purchase.canHaveAsShare(share)) {
            throw new IllegalArgumentException("Non-appropriate share!");
        }
        // Order of initialization is important.
        this.share = share;
        share.addPurchase(this);
        this.wallet = wallet;
        wallet.addPurchase(this);
        changeNbItems(nbItems);
        changeHighestPrice(highestPrice);
    }

    /**
     * Check whether this purchase is terminated.
     */
    @Raw
    public boolean isTerminated() {
        return this.isTerminated;
    }

    /**
     * Terminate this purchase.
     * 
     * @post    This purchase is terminated.
     *          | new.isTerminated()
     * @post    The number of purchases for the wallet to which this
     *          purchase belonged is decremented by 1.
     *          | (new getWallet()).getNbPurchases() ==
     *          |   getWallet().getNbPurchases() - 1
     * @post    This purchase is no longer one of the purchases for
     *          the wallet to which this purchase belonged.
     *          | ! (new getWallet()).hasAsPurchase(this))
     * @post    This purchase is no longer one of the purchases for
     *          the share involved in this purchase.
     *          | ! (new getShare()).hasAsPurchase(this))
     */
    public void terminate() {
        if (!this.isTerminated()) {
            this.getWallet().removePurchase(this);
            this.getShare().removePurchase(this);
            this.setIsTerminated(true);
        }
    }

    /**
     * Set the terminated-state for this purchase according to the
     * given flag.
     * 
     * @param   flag  The flag to be registered.
     * @post    The terminated-state of this purchase is set according
     *          to the given flag.
     *          | new.isTerminated() == flag
     */
    private void setIsTerminated(boolean flag) {
        this.isTerminated = flag;
    }


    public MoneyAmount getHighestPrice() {
        // A copy must be returned, because money amounts are mutable
        // objects, and users must not have the ability to change the
        // highest price for a purchase without appealing to methods
        // offered by the class of purchases.
        return this.highestPrice.getCopy();
    }


    /**
     * Change the highest price at which this purchase may be
     * granted to the given highest price.
     * 
     * @param   highestPrice
     *          The highest price at which the given shares may be
     *          granted.
     * @post    The highest price at which this purchase may be granted
     *          is set to equal to the given highest price.
     *          | new.getHighestPrice().isEqualTo(highestPrice)
     * @throws  IllegalArgumentException
     *          This purchase cannot have the given highest price as
     *          its highest price.
     *          | ! model.oefening2.Purchase.canHaveAsHighestPrice(highestPrice)
     * @throws  IllegalStateException
     *          This purchase has already been granted, or is already
     *          terminated.
     *          | isGranted() || isTerminated()
     */
    public void changeHighestPrice(MoneyAmount highestPrice)
            throws IllegalArgumentException, IllegalStateException {
        // Class invariants satisfied at this point?
        if (!Purchase.canHaveAsHighestPrice(highestPrice))
            throw new IllegalArgumentException("Invalid highest price!");
        if (isGranted() || isTerminated())
            throw new IllegalStateException(
                    "model.oefening2.Purchase already granted or terminated!");
        this.highestPrice.changeValue(highestPrice);
    }

    /*******************
     * NUMBER OF ITEMS *
     *******************/

    /**
     * Return the number of items involved in this purchase.
     */
    @Raw
    public int getNbItems() {
        return this.nbItems;
    }


    /**
     * Change the number of items involved in this purchase with
     * to the given number of items.
     * 
     * @param   nbItems
     *          The number of items to be registered.
     * @post    The number of items involved in this purchase is set
     *          to the given number of items.
     *          | new.getNbItems() == nbItems
     * @throws  IllegalArgumentException
     *          This purchase cannot have the given number of items
     *          as its number of items.
     *          | ! model.oefening2.Purchase.canHaveAsNbItems(nbItems)
     * @throws  IllegalStateException
     *          This purchase has already been granted, or is already
     *          terminated.
     *          | isGranted() || isTerminated()
     */
    public void changeNbItems(int nbItems) {
        if (!Purchase.canHaveAsNbItems(nbItems)) {
            throw new IllegalArgumentException("Invalid number of items!");
        }
        if (isGranted() || isTerminated()) {
            throw new IllegalStateException(
                    "model.oefening2.Purchase already granted or terminated!");
        }
        this.setNbItems(nbItems);
    }


    /**
     * Set the number of items of this purchase to the given number
     * of items
     * 
     * @param   nbItems  The new number of items for this purchase.
     * @post    The number of items of this purchase is set to the
     *          given number of items.
     *          | new.getNbItems() == nbItems  
     */
    private void setNbItems(int nbItems) {
        this.nbItems = nbItems;
    }


    public boolean isGranted() {
        return isGranted;
    }


    /**
     * Register that this purchase has been granted.
     * 
     * @post    This purchase is changed into a granted purchase.
     *          | new.isGranted()
     * @throws  IllegalStateException
     *          This purchase has already been granted or is already
     *          terminated
     *          | isGranted() || isTerminated()
     */
    public void grant() {
        if (isGranted() || isTerminated()) {
            throw new IllegalStateException(
                    "model.oefening2.Purchase already granted or terminated!");
        }
        this.setIsGranted(true);
    }


    /**
     * Sell the given number of items of the share involved
     * in this purchase.
     * 
     * @param   nbItems  The number of items to sell.
     * @post    The number of items involved in this purchase is
     *          decremented with the given number of items.
     *          | new.getNbItems() == getNbItems() - nbItems
     * @throws  IllegalArgumentException
     *          The given number of items is not positive, or exceeds
     *          the number of items involved in this purchase.
     *          | (nbItems <= 0) || (nbItems > getNbItems())
     * @throws  IllegalStateException
     *          This purchase has not been granted yet.
     *          | ! isGranted()
     */
    public void sell(int nbItems) {
        if ((nbItems <= 0) || (nbItems >= getNbItems())) {
            throw new IllegalArgumentException("Invalid number of items!");
        }
        if (!isGranted()) {
            throw new IllegalStateException(
                    "model.oefening2.Purchase has not been granted yet!");
        }
        this.setNbItems(this.getNbItems() - nbItems);
    }


    /**
     * Set the granted-state for this purchase according to the
     * given flag.
     * 
     * @param   flag  The flag to be registered.
     * @post    The granted-state of this purchase is set according
     *          to the given flag.
     *          | new.isGranted() == flag
     */
    private void setIsGranted(boolean flag) {
        this.isGranted = flag;
    }



    /**
     * Return the wallet to which this purchase applies. 
     */
    @Raw
    public Wallet getWallet() {
        return this.wallet;
    }



    /**
     * Check whether this purchase has a proper wallet.
     *
     * @return  True if this purchase can have its wallet as wallet,
     *          and if that wallet has this purchase as one of its
     *          purchases; false otherwise.
     *          | result ==
     *          |   model.oefening2.Purchase.canHaveAsWallet(getWallet()) &&
     *          |   wallet.hasAsPurchase(this) 
     */
    public boolean hasProperWallet() {
        return Purchase.canHaveAsWallet(this.getWallet()) && 
                this.getWallet().hasAsPurchase(this);
    }


    /**
     * Return the share involved in this purchase. 
     */
    @Raw
    public Share getShare() {
        return this.share;
    }


    /**
     * Check whether this purchase has a proper share.
     *
     * @return  True if this purchase can have its share as share,
     *          and if that share has this purchase as one of its
     *          purchases; false otherwise.
     *          | result ==
     *          |   model.oefening2.Purchase.canHaveAsShare(getShare()) &&
     *          |   share.hasAsPurchase(this) 
     */
    public boolean hasProperShare() {
        return Purchase.canHaveAsShare(this.getShare()) && 
                this.getShare().hasAsPurchase(this);
    }



    /**
     * Check whether this purchase can have the given number of
     * items as its number of items.
     * 
     * @param   nbItems  The number of items to check.
     * @return  True of the given number of items is positive;
     *          false otherwise.
     *          | result == (nbItems > 0)
     */
    public static boolean canHaveAsNbItems(int nbItems) {
        return nbItems > 0;
    }


    /**
     * Check whether this purchase can have the given highest price
     * as its highest price.
     * 
     * @param   highestPrice  The highest price to check.
     * @return  True of the given highest price is effective;
     *          false otherwise.
     *          | result == (highestPrice != null)
     */
    public static boolean canHaveAsHighestPrice(MoneyAmount highestPrice) {
        return highestPrice != null;
    }

    /**
     * Check whether this purchase can have the given wallet as
     * its wallet.
     * 
     * @param   wallet  The wallet to check.
     * @return  True if the given wallet is effective and not yet
     *          terminated; false otherwise.
     *          | result == (wallet != null) && (! wallet.isTerminated())
     */
    public static boolean canHaveAsWallet(Wallet wallet) {
        try {
            return !wallet.isTerminated();
        } catch (NullPointerException exc) {
            // The given wallet is not effective.
            return false;
        }
    }

    /**
     * Check whether this purchase can have the given share as
     * its share.
     * 
     * @param   share  The share to check.
     * @return  True if the given share is effective and not yet
     *          terminated; false otherwise.
     *          | result == (share != null) && (! share.isTerminated())
     */
    public static boolean canHaveAsShare(Share share) {
        try {
            return !share.isTerminated();
        } catch (NullPointerException exc) {
            // The given share is not effective.
            return false;
        }
    }
}
