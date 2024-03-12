package eigen.oefening1;

public class IllegalPartnerException extends RuntimeException {

    private Person partner;

    public IllegalPartnerException(Person partner) {
        this.partner = partner;
    }

}
