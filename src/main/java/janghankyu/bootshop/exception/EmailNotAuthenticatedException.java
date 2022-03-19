package janghankyu.bootshop.exception;

public class EmailNotAuthenticatedException extends RuntimeException{
    public EmailNotAuthenticatedException(String message) {
        super(message);
    }
}
