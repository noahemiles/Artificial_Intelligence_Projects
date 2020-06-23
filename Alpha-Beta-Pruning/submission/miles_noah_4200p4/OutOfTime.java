
/**
 *
 * @author Noah
 */
//Exception to handle when the user runs out of time to make their move
class OutOfTimeException extends Exception {

    public OutOfTimeException(String s) {
        super(s);
    }
}
