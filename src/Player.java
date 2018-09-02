import java.util.Arrays;
import java.util.ArrayList;

public class Player{
    double score = 0;
    ArrayList<Card> handCards = new ArrayList<Card>();
    ArrayList<Card> choosenCards = new ArrayList<Card>();
    ArrayList<Card> choosenHandCards = new ArrayList<Card>();
    ArrayList<Capture> captures = new ArrayList<Capture>();

    public void addHandCard(Card[] cards){
        for(int i = 0; i < cards.length; i++){
            handCards.add(cards[i]);
        }
    }
    public void removeHandCard(Card card){
        handCards.remove(card);
    }
    public void addHandCard(Card card){
        handCards.add(card);
    }
    public Card[] getHandCard(){
        Card[] returnCard = new Card[handCards.size()];
        for(int i = 0; i < returnCard.length; i++)
            returnCard[i] = handCards.get(i);
        Arrays.sort(returnCard);
        return returnCard;
    }
    public void addChoosenCard(Card card){
        if(handCards.contains(card))
            choosenHandCards.add(card);
        else
            choosenCards.add(card);
    }

    public boolean removeChoosenCard(Card card){
        if(handCards.contains(card)){
            return choosenHandCards.remove(card);
        }
        return choosenCards.remove(card);
    }

    // return card if trailling, empty card if capture, null otherwise
    public Card playChoosenCard(){
        if(choosenHandCards.size() != 1)
            return null;

        if(choosenCards.size() == 0){
            // play trailling
            removeHandCard(choosenHandCards.get(0));
            Card returnCard = choosenHandCards.get(0);
            choosenHandCards.clear();
            return returnCard;
        }

        if(formCaptureChoosenCard() != null){
            Capture currentCapture = formCaptureChoosenCard();
            score += currentCapture.getScore();
            captures.add(currentCapture);

            removeHandCard(choosenHandCards.get(0));
            choosenCards.clear();
            choosenHandCards.clear();

            return Card.getEmptyCard();
        }

        return null;
    }
    private Capture formCaptureChoosenCard(){
        if(choosenHandCards.size() != 1 || choosenCards.size() == 0)
            return null;

        Card[] capturingCard = new Card[choosenCards.size()];
        for(int i = 0; i < choosenCards.size(); i++)
            capturingCard[i] = choosenCards.get(i);

        return Capture.formCaptures(choosenHandCards.get(0), capturingCard);
    }

    // to help main remove card from pool
    public Capture getLatestCapture(){
        if(captures.size() == 0)
            return null;
        return captures.get(captures.size()-1);
    }
    public double getScore(){
        return score;
    }

}