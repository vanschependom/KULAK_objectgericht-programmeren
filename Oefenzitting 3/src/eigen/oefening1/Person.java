package eigen.oefening1;
import be.kuleuven.cs.som.annotate.*;

import java.lang.*;

/**
 * @author  Vincent Van Schependom
 * @version 1.0
 *
 * @invar   The partner must be a good partner
 *          | canHaveAsMarriagePartner()
 */
public class Person {

    private String name;
    private Person marriagePartner;
    private boolean isTerminated;

    /**
     * @pre     Partner must be a good partner
     *          | canHaveAsMarriagePartner()
     * @param   name
     * @param   partner
     * @post    | this.getName() == name
     * @post    | if ( canHaveAsMarriagePartner() ) then
     *          |   ( this.getMarriagePartner() == partner )
     */
    public Person(String name, Person partner) throws IllegalPartnerException {
        if ( canHaveAsMarriagePartner(partner) ) {
            setName(name);
            setMarriagePartner(partner);
        }
    }

    /**
     * @param   name
     * @effect  | this(name, null)
     */
    public Person(String name) {
        this(name, null);
    }

    /**
     * Setter for the marriage partner.
     * @param partner
     */
    private void setMarriagePartner(Person partner) {
        if ( canHaveAsMarriagePartner(partner) )
            marriagePartner = partner;
    }

    /**
     * Setter for the name.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the marriage partner.
     * @return marriagePartner, if there is one
     * @return null, if there is no marriage partner
     */
    @Basic
    public Person getMarriagePartner() {
        return marriagePartner;
    }

    /**
     * @post | this.getMarriagePartner() == null
     * @post | marriagePartner.getMarriagePartner() == null
     */
    public void divorce() {
        if(canHaveAsMarriagePartner(marriagePartner)) {
            marriagePartner.setMarriagePartner(null);
            marriagePartner = null;
        }
    }

    /**
     * @param   person
     * @pre     | canHaveAsPartner(person)
     * @post    | this.getMarriagePartner() == person
     * @post    | person.getMarriagePartner() == this
     * @throws  IllegalPartnerException
     *          | ! canHaveAsPartner(person)
     */
    public void marry(Person person) throws IllegalPartnerException {
        if(!canHaveAsMarriagePartner(person))
            throw new IllegalPartnerException(person);
        setMarriagePartner(person);
        person.setMarriagePartner(this);
    }

    /**
     * @param   person
     * @return  | result == (marriagePartner != null)
     *                 && (marriagePartner.getMarriagePartner() == null)
     *                 && (marriagePartner != this);
     */
    public boolean canHaveAsMarriagePartner(Person person) {
        return (person != null)
                && (person.getMarriagePartner() == null)
                && (person != this);
    }

    /**
     * Terminate a person instance.
     * @post | this.getMarriagePartner() == null
     * @post | this.isTerminated() == true
     */
    public void terminate() {
        divorce();
        isTerminated = true;
    }

}

