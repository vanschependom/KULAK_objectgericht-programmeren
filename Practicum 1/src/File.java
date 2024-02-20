import be.kuleuven.cs.som.annotate.*;

import java.sql.Array;
import java.util.Arrays;
import java.util.Date;

/**
 * A class representing a single file.
 *
 * @version 1.0
 * @author Vincent Van Schependom
 * @author Flor De Meulemeester
 * @author Arne Claerhout
 */
public class File {

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
     * The size of the file, represented in bytes. A file can be empty, meaning the size equals 0.
     */
    private int size;

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
     * Constructs an object with a given name (that follows certain guidelines), size and writeability.
     * @post An object of the class File is created with given parameters name, size and writeability.
     * @param name The name of the file.
     * @param size The size of the file.
     * @param writeable The writeability of the file.
     */
    public File(String name, int size, boolean writeable) {
        this.setName(name);
        this.setSize(size);
        this.creationTime = new Date();
        this.writeable = writeable;
    }

    /**
     * Constructs an object with a given name
     * @post An object of the class File is created with given parameter name.
     * @param name The name of the file.
     */
    public File(String name) {
        this(name, 0, true);
    }

    /**
     * A method for setting the file size equal to a given positive number.
     * @param size The size to be set.
     * @post If the size is less than or equal to the maximum file size, the file size is changed to equal the parameter size.
     */
    @Basic
    private void setSize(int size) {
        this.size = Integer.min(Integer.max(0, size), MAX_SIZE);
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
     * A method for setting the name of the file.
     * @param name The name to be set.
     * @post The name of the file is changed to equal the parameter name, if the name follows the guidelines.
     */
    private void setName(String name) {
        boolean valid = !name.isEmpty();
        for(char c : name.toCharArray()){
            if(!Character.isLetterOrDigit(c) && c != '.' && c != '-' && c != '_') {
                valid = false;
            }
        }
        if (valid) {
            this.name = name;
        }
    }

    /**
     * A method for getting the creation time of the file.
     * @return The creation time of the file.
     */
    @Basic
    public Date getCreationTime() {
        return this.creationTime;
    }

    /**
     * A method for getting the modification time of the file.
     * @return The modification time of the file.
     */
    @Basic
    public Date getModificationTime() {
        return modificationTime;
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
     * A method for setting the modification time of the file.
     * The modification time shouldn't be changed when the file is created, or when the write authorization is changed.
     * @param modificationTime The modification time to be set.
     */
    private void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }

    /**
     * A method for increasing the file size with a given positive amount of bits.
     * @param amountOfBits The amount of bits we want to increase the file size with.
     * @post If the current size of the file, increased with the positive amountOfBits parameter doesn't exceed the MAX_SIZE
     * constant, and amountOfBits is strictly positive, the filesize is increased with the parameter amountOfBits.
     */
    public void enlarge(int amountOfBits) {
        if (this.size <= MAX_SIZE - amountOfBits && amountOfBits > 0) {
            setSize(this.getSize() + amountOfBits);
        }
    }

    /**
     * A method for decreasing the file size with a given positive amount of bits.
     * @param amountOfBits The amount of bits we want to decrease the file size with.
     * @post If the current size of the file, decreased with the positive amountOfBits parameter is greater than or equal to 0,
     * and amountOfBits is strictly positive, the filesize is decreased with the parameter amountOfBits.
     */
    public void shorten(int amountOfBits) {
        if (this.size >= amountOfBits && amountOfBits > 0) {
            setSize(this.getSize() - amountOfBits);
        }
    }

    /**
     * A method for changing the name of the file.
     * @param newName The new name for the file.
     * @post The name of the file is changed to the parameter newName.
     * @post The modification time of the file is set to the current time.
     */
    public void changeName(String newName) {
        this.setName(newName);
        this.setModificationTime(new Date());
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
