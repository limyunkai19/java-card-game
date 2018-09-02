import javafx.geometry.*;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;

public class CardPane extends StackPane{
    private Player currentPlayer;
    private Card card;
    private ImageView iv;
    private boolean selected = false;
    private boolean active = true;

    public CardPane(int width){
        iv = new ImageView();
        iv.setFitWidth(width);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);

        setMargin(iv, new Insets(3));
        getChildren().add(iv);
    }

    public void setCard(Card card){
        this.card = card;
        selected = false;
        setStyle("-fx-background-color: none");

        if(card == null){
            iv.setImage(null);
            setOnMouseClicked(null);
        }
        else{
            iv.setImage(card.getImage());

            setOnMouseClicked(e ->{
                // dont do anything if not active
                if(!active)
                    return;

                if(!selected){
                    setStyle("-fx-background-color: blue");
                    currentPlayer.addChoosenCard(card);
                }
                else{
                    setStyle("-fx-background-color: none");
                    currentPlayer.removeChoosenCard(card);
                }

                selected = !selected;
            });
        }
    }

    public void setCurrentPlayer(Player player){
        currentPlayer = player;
    }

    public void setActive(boolean active){
        this.active = active;

        if(card == null)
            return;

        if(!active)
            iv.setImage(card.getBackImage());
        else
            iv.setImage(card.getImage());
    }
}
