package exception;

public class PlayerWithThisIdDontExist extends Exception{
    public PlayerWithThisIdDontExist(String message) {
        super(message);
    }
}
