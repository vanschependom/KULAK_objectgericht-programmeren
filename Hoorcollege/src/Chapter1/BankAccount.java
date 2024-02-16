package Chapter1;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

class BankAccount {
    private final int number;
    private static final int bankCode = 123;
    private long balance;
    private static long creditLimit = 0L;
    private static final long balanceLimit = Long.MAX_VALUE;
    private boolean isBlocked;

    public BankAccount(int number, long balance, boolean isBlocked) {
        this.balance = 0L;
        this.isBlocked = false;
        if (number < 0) {
            number = 0;
        }

        this.number = number;
        this.setBalance(balance);
        this.setBlocked(isBlocked);
    }

    public BankAccount(int number, long balance) {
        this(number, balance, false);
    }

    public BankAccount(int number) {
        this(number, 0L);
    }

    public int getNumber() {
        return this.number;
    }

    public static int getBankCode() {
        return 123;
    }

    public long getBalance() {
        return this.balance;
    }

    public boolean hasHigherBalanceThan(long amount) {
        return this.getBalance() > amount;
    }

    public boolean hasHigherBalanceThan(BankAccount other) {
        return other != null && this.hasHigherBalanceThan(other.getBalance());
    }

    public void deposit(long amount) {
        if (amount > 0L && this.getBalance() <= getBalanceLimit() - amount) {
            this.setBalance(this.getBalance() + amount);
        }

    }

    public void withdraw(long amount) {
        if (amount > 0L && !this.isBlocked() && this.getBalance() >= getCreditLimit() + amount) {
            this.setBalance(this.getBalance() - amount);
        }

    }

    public void transferTo(long amount, BankAccount destination) {
        if (amount > 0L && destination != null && !this.isBlocked() && this.getBalance() >= getCreditLimit() + amount && destination.getBalance() <= getBalanceLimit() - amount) {
            this.withdraw(amount);
            destination.deposit(amount);
        }

    }

    private void setBalance(long balance) {
        if (balance >= getCreditLimit() && balance <= getBalanceLimit()) {
            this.balance = balance;
        }

    }

    public static long getCreditLimit() {
        return creditLimit;
    }

    public static void setCreditLimit(long creditLimit) {
        if (creditLimit < getCreditLimit()) {
            BankAccount.creditLimit = creditLimit;
        }

    }

    public static long getBalanceLimit() {
        return Long.MAX_VALUE;
    }

    public boolean isBlocked() {
        return this.isBlocked;
    }

    public void setBlocked(boolean flag) {
        this.isBlocked = flag;
    }

    public void block() {
        this.setBlocked(true);
    }

    public void unblock() {
        this.setBlocked(false);
    }
}
