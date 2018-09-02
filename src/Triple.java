public class Triple extends Capture{
    public Triple(){};

    private Triple(Card handCard, Card[] poolCards){
        multiplier = 1;
        captureCards = new Card[poolCards.length+1];
        captureCards[0] = handCard;
        for(int i = 0; i < poolCards.length; i++){
            captureCards[i+1] = poolCards[i];
        }
    }

    public Capture formCapture(Card handCard, Card[] poolCards){
        if(poolCards.length != 2)
            return null;
        if(handCard.getRankValue() != poolCards[0].getRankValue()
            || handCard.getRankValue() != poolCards[1].getRankValue() )
            return null;

        return new Triple(handCard, poolCards);

    }
    public double getScore(){
        return multiplier*captureCards.length;
    }

    public String getCaptureName(){
        return "Triple";
    }

}