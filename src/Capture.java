import java.util.Arrays;

public abstract class Capture{
    protected double multiplier;
    protected Card[] captureCards;
    private static Capture[] allPossibleCapture = {new Run(), new Straight(), new Combo(), new Triple(), new Pair()};

    // for subclass to implement
    /* return capture object of its own type for valid capture, null otherwise */
    public abstract Capture formCapture(Card handCard, Card[] poolCards);
    public abstract double getScore();
    public abstract String getCaptureName();

    // protect the array
    public Card[] getCaptureCards(){
        Card[] returnCards = new Card[captureCards.length];
        for(int i = 0; i < captureCards.length; i++)
            returnCards[i] = captureCards[i];
        Arrays.sort(returnCards);
        return returnCards;
    };

    // return highest possible scored capture, null if cannot form capture
    public static Capture formCaptures(Card handCard, Card[] poolCards){
        // try form higher score capture first
        // allPossibleCapture must sorted from highest possible score to lowest
        for(Capture c: allPossibleCapture)
            if(c.formCapture(handCard, poolCards) != null)
                return c.formCapture(handCard, poolCards);

        return null;
    }
}