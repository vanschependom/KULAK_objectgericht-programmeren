import be.kuleuven.cs.som.annotate.*;

import java.sql.Array;
import java.util.Arrays;
import java.util.Date;

/**
 * A class representing a single file that has a name, a size, a creation time, a modification time and a writeability.
 *
 * @version 1.0
 * @author  Vincent Van Schependom
 * @author  Flor De Meulemeester
 * @author  Arne Claerhout
 * @invar   The size of a file is always greater than or equal to 0 and less than or equal to the defined maximum size.
 *        | isValidSize(getSize())
 */
public class File {

    /**
     * The size of the file, represented in bytes. A file can be empty, meaning the size equals 0.
     */
    private int size;

    /**
     * For now, the max size of all files is equal to the biggest possible value of the class Integer.
     */
    private static final int MAX_SIZE = Integer.MAX_VALUE;

    /**
     * The name must only contain letters, digits, periods (.), dashes (-) and underscores (_). The name cannot
     * be empty and is case-sensitive.
     */
    private String name;

    /**
     * The creation time is initially set to be NULL and is instantiated when the file is created, in the constructor.
     */
    private final Date creationTime;
    /**
     * The modification time is initially set to be NULL and is instantiated when the file is modified for the first time.
     */
    private Date modificationTime;

    /**
     * Describes if the file is writeable.
     */
    private boolean writeable;

    /**
     * A method for checking if the file is writeable.
     * @return True if the file is writeable.
     * @return False if the file is not writeable.
     */
    public boolean isWriteable() {
        return writeable;
    }

    /**
     * A method for setting the writeability of the file.
     * @param   writeable
     *          The writeability to be set, either true or false.
     * @post    The writeability of the file is set to the parameter writeable.
     *        | new.isWriteable() == writeable
     */
    public void setWriteable(boolean writeable) {
        this.writeable = writeable;
    }

    /**
     * Constructs an object with a given (legal) name, size and writeability.
     *
     * @param   name
     *          The name of the file.
     * @param   size
     *          The size of the file.
     * @param   writeable
     *          The writeability of the file.
     * @pre     The size must be a legal size, i.e. a positive number, smaller than or equal to the maximum size.
     *        | canHaveAsSize(size)
     * @post    The name of the file is set to the parameter name.
     *        | this.getName() == name
     * @post    The size of the file is set to the parameter size.
     *        | this.getSize() == size
     * @post    The creation time of the file is set to the current time.
     *        | this.getCreationTime() == new Date()
     * @post    The writeability of the file is set to the parameter writeable.
     *        | this.isWriteable() == writeable
     */
    public File(String name, int size, boolean writeable) throws IllegalArgumentException {
        assert canHaveAsSize(size) : "Illegal size";
        this.setName(name);
        this.setSize(size);
        this.creationTime = new Date();
        this.writeable = writeable;
    }

    /**
     * Constructs an object with a given name.
     * @param   name
     *          The name of the file.
     * @effect  A new object of the class File is created with the given parameter name,
     *          the size is set to 0 and the writeability is set to true.
     *         | this(name, 0, true)
     */
    public File(String name) {
        this(name, 0, true);
    }

    /**
     * A method for getting the file size.
     * @return The size of the file.
     */
    @Basic
    public int getSize() {
        return this.size;
    }

    /**
     * A method for setting the file size equal to a given positive number.
     * @param   size
     *          The size to be set.
     * @pre     The size must be a legal size, i.e. a positive number.
     *        | canHaveAsSize(getSize()
     * @post    If the size is legal, the file size is changed to equal the parameter size.
     *        | new.getSize() == size
     */
    private void setSize(int size) {
        assert canHaveAsSize(size) : "Illegal size";
        this.size = size;
    }

    /**
     * Check whether this file can have a given number as its size.
     * @param   size
     *          The size to check.
     * @return  True if and only if the size is greater than or equal to 0,
     *          and if the size is less than or equal to the maximum size.
     *        | result == (size >= 0) && (size <= MAX_SIZE)
     */
    @Raw
    public boolean canHaveAsSize(int size) {
        return size >= 0 && size <= MAX_SIZE;
    }

    /**
     * A method for increasing the file size with a given positive amount of bits.
     * @param   amountOfBits
     *          The amount of bits we want to increase the file size with.
     * @pre     The amountOfBits must be a positive number.
     *        | amountOfBits > 0
     * @pre     The current size of the file, increased with the positive amountOfBits, must not cause overflow.
     *        | getSize() <= Integer.MAX_VALUE - amountOfBits
     * @pre     The writeability of the file must be true.
     *       | isWriteable()
     * @pre     The current size of the file, increased with the positive amountOfBits, must be a legal size.
     *        | canHaveAsSize(getSize() + amountOfBits)
     * @effect  The new size of the file is set to the old size of the file, incremented with the parameter amountOfBits.
     *        | new.getSize() == this.getSize() + amountOfBits
     * @throws  NotAuthorizedException
     *          The file is not writeable.
     */
    public void enlarge(int amountOfBits) throws NotAuthorizedException {
        if (!isWriteable()) {
            throw new NotAuthorizedException(this);
        }
        setSize(this.getSize() + amountOfBits);
    }

    /**
     * A method for decreasing the file size with a given positive amount of bits.
     * @param   amountOfBits
     *          The amount of bits we want to decrease the file size with.
     * @pre     The writeability of the file must be true.
     *        | isWriteable()
     * @pre     The amountOfBits must be a positive number.
     *        | amountOfBits > 0
     * @pre     The current size of the file, decreased with the positive amountOfBits parameter must be a legal size.
     *        | canHaveAsSize(getSize() - amountOfBits)
     * @effect  The new size of the file is set to the old size of the file, decremented with the parameter amountOfBits.
     *        | new.getSize() == this.getSize() - amountOfBits
     * @throws  NotAuthorizedException
     *          The file is not writeable.
     */
    public void shorten(int amountOfBits) throws NotAuthorizedException{
        if (!isWriteable()) {
            throw new NotAuthorizedException(this);
        }
        setSize(this.getSize() - amountOfBits);
    }

    /**
     * A method for getting the name of the file.
     * @return The name of the file.
     */
    @Basic
    public String getName() {
        return this.name;
    }

    /**
     * A method for setting the name of the file.
     * @param   name
     *          The name to be set.
     * @post    The name of the file is changed to equal the parameter name, if the name follows the guidelines.
     *          The name cannot be empty and must only contain letters, digits, periods (.), dashes (-) and underscores (_).
     *          (because of the inertia axioma, the name is not changed if the name is not valid)
     */
    private void setName(String name) {
        // check the validity of the string with a regex
        if (name.matches("[a-zA-Z0-9._-]+"))
            this.name = name;
    }

    /**
     * A method for changing the name of the file.
     * @param   newName
     *          The new name for the file.
     * @pre     The writeability of the file must be true.
     *        | isWriteable()
     * @post    The name of the file is changed to the parameter newName, only if it follows the guidelines.
     *          The name cannot be empty and must only contain letters, digits, periods (.), dashes (-) and underscores (_).
     * @post    The modification time of the file is set to the current time, if the name is changed.
     * @throws  NotAuthorizedException
     *          The file is not writeable.
     */
    public void changeName(String newName) throws NotAuthorizedException {
        if (!isWriteable()) {
            throw new NotAuthorizedException(this);
        }
        // the name is different from the current one
        if (!newName.equals(this.name)) {
            this.setName(newName);
            // the name has been changed
            if (this.getName().equals(newName)) {
                this.setModificationTime(new Date());
            }
        }
    }

    /**
     * A method for getting the creation time of the file.
     * @return The creation time of the file.
     */
    @Basic @Immutable
    public Date getCreationTime() {
        return this.creationTime;
    }

    /**
     * A method for getting the modification time of the file.
     * @return The modification time of the file is returned, possibly null.
     */
    @Basic
    public Date getModificationTime() {
        return this.modificationTime;
    }

    /**
     * A method for setting the modification time of the file.
     * The modification time shouldn't be changed when the file is created, or when the write authorization is changed.
     *
     * @param modificationTime The modification time to be set.
     * @post The modification time of the file is set to the parameter modificationTime.
     */
    private void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }

    /**
     * A method for checking if the file has an overlapping usage period with another file.
     * @param other The file to be checked for overlapping usage period.
     * @return False if either one of the files doesn't have a modification time.
     * @return True if the usage periods of the two files overlap.
     */
    public boolean hasOverlappingUsagePeriod(File other) {
        // Unchanged files don't have overlapping usage periods.
        if (this.modificationTime == null) {
            return false;
        } else {
            if (this.creationTime.before(other.creationTime)) {
                // The file was created before the other file
                return this.modificationTime.after(other.creationTime);
            } else {
                // The other file was created before this file
                return other.creationTime.before(this.modificationTime);
            }
        }
    }

}
