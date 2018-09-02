import java.util.Arrays;

public class Straight extends Capture{
    public Straight(){};

    private Straight(Card handCard, Card[] poolCards){
        multiplier = 1.3;
        captureCards = new Card[poolCards.length+1];
        captureCards[0] = handCard;
        for(int i = 0; i < poolCards.length; i++){
            captureCards[i+1] = poolCards[i];
        }
    }

    public Capture formCapture(Card handCard, Card[] poolCards){
        if(poolCards.length < 2)
            return null;

        int[] allCardRank = new int[poolCards.length+1];
        allCardRank[0] = handCard.getRankValue();
        for(int i = 0; i < poolCards.length; i++)
            allCardRank[i+1] = poolCards[i].getRankValue();

        Arrays.sort(allCardRank);
        for(int i = 1; i < allCardRank.length; i++)
            if(allCardRank[i] != allCardRank[i-1]+1)
                return null;

        return new Straight(handCard, poolCards);
    }
    public double getScore(){
        return multiplier*captureCards.length;
    }

    public String getCaptureName(){
        return "Straight";
    }
}