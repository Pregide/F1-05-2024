package App.exception;

public class NoTravelFoundException extends Exception{
    public NoTravelFoundException(){
        System.out.println("No Travel Possible.");
    }
}
