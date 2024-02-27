package kulak_ogp_oz2.eigen;

public class InequivalentClocksException extends RuntimeException {

    public InequivalentClocksException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Inequivalent clocks: min and max are not equal.";
    }

}
