import java.util.Random;
import java.util.Comparator;
import javafx.scene.image.*;
import java.net.URL;

public class Card implements Comparable<Card>{
    private int suit, rank;
    private final String[] verbose_suit = {"Diamond", "Club", "Heart", "Spade"};
    private final String[] verbose_rank = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private final String[] suit_symbol = {"\u2662", "\u2663", "\u2661", "\u2660"};
    private Image cardImage;
    private static Card emptyCard = new Card(-1);
    // private static Image backImage = new Image("file:../img/back.png");
    private static Image backImage = new Image(Card.class.getResourceAsStream("img/back.png"));

    // value should be 0-51, -1 for empty card
    public Card(int value){
        if(0 <= value && value <= 51){
            suit = value/13;
            rank = value%13;
        }
    }

    public String getSuit(){
        return verbose_suit[suit];
    }

    public int getSuitValue(){
        return suit;
    }

    public String getRank(){
        return verbose_rank[rank];
    }

    public int getRankValue(){
        return rank+1;
    }

    public String toString(){
        return suit_symbol[suit]+verbose_rank[rank];
    }

    public static Card[] newDeck(){
        Card[] deck = new Card[51];
        for(int i = 0; i < deck.length; i++){
            deck[i] = new Card(i);
        }
        return deck;
    }

    public static Card[] shuffleDeck(Card[] deck){
        Card[] shuffled = new Card[deck.length];

        Random rand = new Random();
        for(int i = 0; i < deck.length; i++){
            int nextCard = rand.nextInt(deck.length-i);
            for(int j = 0; j < deck.length; j++){
                // currect card is the one selected
                if(nextCard == 0 && deck[j] != null){
                    shuffled[i] = deck[j];
                    deck[j] = null;
                    break;
                }

                if(deck[j] != null)
                    nextCard--;
            }
        }

        return shuffled;
    }

    public int compareTo(Card c){
        if(this.suit != c.suit)
            return this.suit - c.suit;
        return this.rank - c.rank;
    }

    public static Card getEmptyCard(){
        return emptyCard;
    }

    public Image getImage(){
        // load card image only when needed
        if(cardImage == null)
            cardImage = new Image(
                // ("file:../img/" + verbose_rank[rank]+ verbose_suit[suit] + ".png").toLowerCase());
                Card.class.getResourceAsStream(("img/" + verbose_rank[rank]+ verbose_suit[suit] + ".png").toLowerCase()));
        return cardImage;
    }

    public static Image getBackImage(){
        return backImage;
    }

}

