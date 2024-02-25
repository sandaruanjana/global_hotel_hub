package ac.uk.bolton.globalhotelhub.exception;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
public class InternalServerErrorException extends RuntimeException{
    public InternalServerErrorException(String message) {
        super(message);
    }
}
