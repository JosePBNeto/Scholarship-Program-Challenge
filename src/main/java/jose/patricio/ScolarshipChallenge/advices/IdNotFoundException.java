package jose.patricio.ScolarshipChallenge.advices;

public class IdNotFoundException extends RuntimeException{
    public IdNotFoundException(String message) {
        super(message);
    }
}
