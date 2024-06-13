package App.exception;

public class NoTravelFoundException extends Exception{
    public NoTravelFoundException(){
        super();
    }

    public NoTravelFoundException(String msg){
        super(msg);
    }
}
