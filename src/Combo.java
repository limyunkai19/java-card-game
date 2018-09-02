public class Combo extends Capture{
    public Combo(){};

    private Combo(Card handCard, Card[] poolCards){
        multiplier = 1.2;
        captureCards = new Card[poolCards.length+1];
        captureCards[0] = handCard;
        for(int i = 0; i < poolCards.length; i++){
            captureCards[i+1] = poolCards[i];
        }
    }

    public Capture formCapture(Card handCard, Card[] poolCards){
        if(poolCards.length < 2)
            return null;

        int sum = 0;

        for(Card c: poolCards)
            if(c.getRankValue() > 10)
                return null;
            else
                sum += c.getRankValue();

        if(handCard.getRankValue() > 10)
            return null;

        if(handCard.getRankValue() != sum)
            return null;

        return new Combo(handCard, poolCards);

    }
    public double getScore(){
        return multiplier*captureCards.length;
    }

    public String getCaptureName(){
        return "Combo";
    }

}