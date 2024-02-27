package kulak_ogp_oz2.eigen;

public class IllegalAmountException extends RuntimeException {

    private final String type;

    public IllegalAmountException(String type) {
        super();
        this.type = type;
    }

    @Override
    public String getMessage() {
        return "Illegal amount for " + type;
    }

}
