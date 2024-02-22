import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of exceptions signaling that a user is not authorized to perform a certain operation.
 *   Each not authorized exception involves the file for which the user is not authorized.
 *
 * @version 1.0
 * @author  Vincent Van Schependom
 * @author  Flor De Meulemeester
 * @author  Arne Claerhout
 */
public class NotAuthorizedException extends RuntimeException {

    /**
     * Initialize this new not authorized exception with given file and given permission.
     *
     * @param  file
     *         The file for this new not authorized exception.
     * @post   The file of this new not authorized exception
     *         is the same as the given file.
     *       | new.getFile() == file
     * @effect This new not authorized exception is further
     *         initialized as a new runtime exception involving
     *         no diagnostic message and no cause.
     *       | super()
     */
    public NotAuthorizedException(File file) {
        this.file = file;
    }

    /**
     * Variable registering the file of this not authorized exception.
     */
    private final File file;

    /**
     * Return the file for this NotAuthorizedException.
     */
    @Basic
    @Raw
    @Immutable
    public File getFile() {
        return file;
    }

}
