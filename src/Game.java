import java.util.*;
import java.text.DecimalFormat;
import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.event.*;
import javafx.scene.paint.Color;

public class Game extends Application{
    final static int POOLCARD = 8, PLAYERCARD = 4, WINNING_SCORE = 21;
    static int currentPlayerIdx, numberOfPlayer;
    static Player currentPlayer;
    static Player[] players;
    static Card[] deck;
    static ArrayList<Card> poolCards;
    static CardPane[][] playerCardsImage;
    static CardPane[] poolCardsImage;
    static GridPane poolCardsPane;

    public static void setNextPlayer(int nextPlayer){
        for(int i = 0; i < players.length; i++){
            Card[] playerHandCard = players[i].getHandCard();
            for(int j = 0; j < playerCardsImage[i].length; j++){
                if(j < playerHandCard.length){
                    playerCardsImage[i][j].setCard(playerHandCard[j]);
                }
                else{
                    playerCardsImage[i][j].setCard(null);

                }
                playerCardsImage[i][j].setCurrentPlayer(players[nextPlayer]);
                if(i != nextPlayer || j >= playerHandCard.length)
                    playerCardsImage[i][j].setActive(false);
                else
                    playerCardsImage[i][j].setActive(true);
            }
        }

        Collections.sort(poolCards);
        for(int i = 0; i < poolCardsImage.length; i++){
            if(i < poolCards.size()){
                poolCardsImage[i].setCard(poolCards.get(i));
                poolCardsImage[i].setCurrentPlayer(players[nextPlayer]);
                poolCardsImage[i].setActive(true);
            }
            else{
                poolCardsImage[i].setCard(null);
                poolCardsImage[i].setActive(false);
            }
        }

        poolCardsPane.getChildren().clear();
        if(poolCards.size() <= 12){
            // 4 col
            for(int i = 0; i < poolCardsImage.length; i++)
                poolCardsPane.add(poolCardsImage[i], i%4, i/4);
        }
        else if(poolCards.size() <= 18){
            // 6 col
            for(int i = 0; i < poolCardsImage.length; i++)
                poolCardsPane.add(poolCardsImage[i], i%6, i/6);
        }
        else{
            // 8 col
            for(int i = 0; i < poolCardsImage.length; i++)
                poolCardsPane.add(poolCardsImage[i], i%8, i/8);
        }
    }

    public static void initializeRound(int p){
        deck = Card.newDeck();
        deck = Card.shuffleDeck(deck);

        // select pool card
        poolCards = new ArrayList<Card>();
        for(int i = 0; i < POOLCARD; i++)
            poolCards.add(deck[i]);

        // distribute card to player
        for(int i = 0, idx = POOLCARD; i < PLAYERCARD*p; i++, idx++)
            players[i%p].addHandCard(deck[idx]);
    }

    public HBox formCapturesImage(Capture capture){
        HBox capturePane = new HBox(3);
        Text t = new Text(capture.getCaptureName() + ": ");
        t.setFill(Color.WHITE);
        capturePane.getChildren().add(t);

        for(Card c: capture.getCaptureCards()){
            ImageView iv = new ImageView();
            iv.setFitWidth(20);
            iv.setPreserveRatio(true);
            iv.setSmooth(true);
            iv.setCache(true);

            iv.setImage(c.getImage());

            capturePane.getChildren().add(iv);
        }

        capturePane.setAlignment(Pos.CENTER_LEFT);
        return capturePane;
    }

    public void start(Stage primaryStage) {
        /* GUI Design begin */
        playerCardsImage = new CardPane[4][PLAYERCARD];
        for(int i = 0; i < playerCardsImage.length; i++){
            for(int j = 0; j < playerCardsImage[i].length; j++){
                playerCardsImage[i][j] = new CardPane(75);
            }
        }

        poolCardsImage = new CardPane[PLAYERCARD*4 + POOLCARD];
        for(int i = 0; i < poolCardsImage.length; i++){
            poolCardsImage[i] = new CardPane(60);
        }

        HBox player1CardsPane = new HBox(5), player3CardsPane = new HBox(5);
        player1CardsPane.setPrefHeight(115); player3CardsPane.setPrefHeight(115);
        for(int i = 0; i < 4; i++){
            player1CardsPane.getChildren().add(playerCardsImage[0][i]);
            player3CardsPane.getChildren().add(playerCardsImage[2][i]);
        }

        GridPane player2CardsPane = new GridPane(), player4CardsPane = new GridPane();
        player2CardsPane.setVgap(5);    player4CardsPane.setVgap(5);
        player2CardsPane.setHgap(5);    player4CardsPane.setHgap(5);
        player2CardsPane.setPrefHeight(235); player4CardsPane.setPrefHeight(235);
        for(int i = 0; i < 4; i++){
            player2CardsPane.add(playerCardsImage[1][i], i%2, i/2);
            player4CardsPane.add(playerCardsImage[3][i], i%2, i/2);
        }

        poolCardsPane = new GridPane();
        poolCardsPane.setVgap(3);
        poolCardsPane.setHgap(3);

        Text[] playerText = new Text[4];
        Text[] playerScoreText = new Text[4];
        Button[] playerCaptureButton = new Button[4];
        for(int i = 0; i < 4; i++){
            playerText[i] = new Text("Player " + (i+1));
            playerScoreText[i] = new Text("Score: 0.0");
            playerCaptureButton[i] = new Button("Capture");

            playerText[i].setFill(Color.WHITE);
            playerText[i].setStyle("-fx-font: 22 arial");
            playerScoreText[i].setFill(Color.WHITE);
            playerScoreText[i].setStyle("-fx-font: 15 arial");


        }

        VBox player2Pane = new VBox(10), player4Pane = new VBox(10);
        player2Pane.getChildren().addAll(playerText[1], playerScoreText[1], player2CardsPane, playerCaptureButton[1]);
        player4Pane.getChildren().addAll(playerText[3], playerScoreText[3], player4CardsPane, playerCaptureButton[3]);

        HBox player1Pane = new HBox(10), player3Pane = new HBox(10);
        VBox player1TextPane = new VBox(10), player3TextPane = new VBox(10);
        player1TextPane.getChildren().addAll(playerText[0], playerScoreText[0]);
        player3TextPane.getChildren().addAll(playerText[2], playerScoreText[2]);
        player1Pane.getChildren().addAll(player1TextPane, player1CardsPane, playerCaptureButton[0]);
        player3Pane.getChildren().addAll(player3TextPane, player3CardsPane, playerCaptureButton[2]);

        Pane[] playerPane = new Pane[4];
        playerPane[0] = player1Pane;
        playerPane[1] = player2Pane;
        playerPane[2] = player3Pane;
        playerPane[3] = player4Pane;

        BorderPane mainPane = new BorderPane();
        mainPane.setStyle("-fx-background-color: green");
        mainPane.setPadding(new Insets(23));
        mainPane.setBottom(player1Pane);
        mainPane.setLeft(player2Pane);
        mainPane.setTop(player3Pane);
        mainPane.setRight(player4Pane);
        mainPane.setCenter(poolCardsPane);
        mainPane.setPrefSize(1000, 700);
        BorderPane.setAlignment(poolCardsPane, Pos.CENTER);
        player1Pane.setAlignment(Pos.CENTER);
        player3Pane.setAlignment(Pos.CENTER);
        poolCardsPane.setAlignment(Pos.CENTER);
        for(int i = 0; i < 4; i++){
            BorderPane.setAlignment(playerPane[i], Pos.CENTER);
            playerPane[i].setVisible(false);
        }

        VBox capturesBoard = new VBox(20);
        Text captureBoardTitle = new Text("Capturing Status"), captureStatus = new Text("No Capture");
        captureBoardTitle.setFill(Color.WHITE);
        captureBoardTitle.setStyle("-fx-font: 25 arial");
        captureStatus.setStyle("-fx-font: 25 arial; -fx-fill: #ea8f79;");
        capturesBoard.setAlignment(Pos.TOP_CENTER);
        capturesBoard.getChildren().addAll(captureBoardTitle, captureStatus);
        capturesBoard.setPadding(new Insets(20));
        capturesBoard.setPrefWidth(250);
        capturesBoard.setStyle("-fx-padding: 10; -fx-background-color: green;" +
                      "-fx-border-style: solid inside;" +
                      "-fx-border-width: 2;" +
                      "-fx-border-insets: 35;" +
                      "-fx-border-radius: 5;" +
                      "-fx-border-color: white;");

        VBox[] playerCaptures = new VBox[4];
        for(int i = 0; i < 4; i++){
            playerCaptures[i] = new VBox(5);
            capturesBoard.getChildren().add(playerCaptures[i]);
        }


        HBox mainWrapper = new HBox();
        mainWrapper.getChildren().addAll(mainPane, capturesBoard);
        // main menu design begin
        VBox mainMenu = new VBox(30);

        Text mainMenuText = new Text("Please Select Number of Player");
        mainMenuText.setStyle("-fx-font: 25 arial");
        mainMenuText.setFill(Color.WHITE);

        HBox mainMenuSelection = new HBox(20);
        Button[] playerSelectionButton = new Button[3];
        for(int i = 0; i < playerSelectionButton.length; i++){
            playerSelectionButton[i] = new Button(i+2+"");
            mainMenuSelection.getChildren().add(playerSelectionButton[i]);
        }

        mainMenu.getChildren().addAll(mainMenuText, mainMenuSelection);

        mainPane.setCenter(mainMenu);
        BorderPane.setAlignment(mainMenu, Pos.CENTER);
        mainMenu.setAlignment(Pos.CENTER);
        mainMenuSelection.setAlignment(Pos.CENTER);
        // main menu design end

        // winning menu design begin
        VBox winningMenu = new VBox(30);

        Text winningText = new Text("");
        winningText.setStyle("-fx-font: 25 arial");
        winningText.setFill(Color.WHITE);

        HBox winningSelection = new HBox(20);
        Button winningContinue = new Button("New Game");
        Button winningExit = new Button("Exit Game");

        winningSelection.getChildren().addAll(winningContinue, winningExit);
        winningMenu.getChildren().addAll(winningText, winningSelection);

        BorderPane.setAlignment(winningMenu, Pos.CENTER);
        winningMenu.setAlignment(Pos.CENTER);
        winningSelection.setAlignment(Pos.CENTER);

        winningExit.setOnAction(e -> {
            Platform.exit();
        });

        winningContinue.setOnAction(e -> {
            mainPane.setCenter(mainMenu);
        });

        // winning menu design end


        Scene scene = new Scene(mainWrapper);
        primaryStage.setTitle("An Interesting Card Game");
        primaryStage.setScene(scene);
        primaryStage.show();
        /* GUI Design end */


        /* Game Logic begin */
        for(int i = 0; i < playerSelectionButton.length; i++){
            int p = i+2;
            playerSelectionButton[i].setOnAction(e -> {
                numberOfPlayer = p;
                players = new Player[numberOfPlayer];
                for(int j = 0; j < players.length; j++)
                    players[j] = new Player();

                initializeRound(numberOfPlayer);

                currentPlayerIdx = 0;
                currentPlayer = players[currentPlayerIdx];

                setNextPlayer(currentPlayerIdx);

                mainPane.setCenter(poolCardsPane);

                for(int j = 0; j < 4; j++)
                    playerCaptureButton[j].setDisable(true);
                playerCaptureButton[currentPlayerIdx].setDisable(false);

                for(int j = 0; j < 4; j++)
                    if(j < numberOfPlayer)
                        playerPane[j].setVisible(true);
                    else
                        playerPane[j].setVisible(false);

                for(int j = 0; j < 4; j++)
                    playerScoreText[j].setText("Score: 0.0");

                // reset capture board
                captureStatus.setText("No Capture");
                for(int j = 0; j < 4; j++)
                    playerCaptures[j].getChildren().clear();
                for(int j = 0; j < numberOfPlayer; j++){
                    Text t = new Text("Player " + (j+1) + " Captures:");
                    t.setStyle("-fx-font: 15 arial; -fx-fill: white;");
                    playerCaptures[j].getChildren().add(t);
                }

            });
        }

        class CaptureHandler implements EventHandler<ActionEvent> {
            public void handle(ActionEvent e) {
                Button currentControl = (Button)e.getSource();

                Card playedCard = currentPlayer.playChoosenCard();
                if(playedCard != null){
                    // trailing
                    if(playedCard != Card.getEmptyCard()){
                        poolCards.add(playedCard);
                        captureStatus.setText("Trailing");
                    }
                    // capture
                    else{
                        Capture currentCapture = currentPlayer.getLatestCapture();
                        for(Card c: currentCapture.getCaptureCards())
                            poolCards.remove(c);
                        captureStatus.setText("Captured " + currentCapture.getCaptureName());
                        playerCaptures[currentPlayerIdx].getChildren().add(formCapturesImage(currentCapture));
                    }

                    // successful capture, update player score
                    DecimalFormat df = new DecimalFormat("#0.0");
                    playerScoreText[currentPlayerIdx].setText("Score: " + df.format(currentPlayer.getScore()));

                    // this player won
                    if(currentPlayer.getScore() >= WINNING_SCORE){
                        // game end
                        mainPane.setCenter(winningMenu);
                        winningText.setText("Player " + (currentPlayerIdx+1) + " wins !!!!");
                        playerCaptureButton[currentPlayerIdx].setDisable(true);
                        return;
                    }
                    // successful capture, next player
                    playerCaptureButton[currentPlayerIdx].setDisable(true);
                    currentPlayerIdx = (currentPlayerIdx + 1)%numberOfPlayer;
                    currentPlayer = players[currentPlayerIdx];

                    // if current player has no hand card, need new round
                    if(currentPlayer.getHandCard().length == 0){
                        initializeRound(numberOfPlayer);
                        // reset player capture board
                        for(int j = 0; j < numberOfPlayer; j++)
                            playerCaptures[j].getChildren().clear();
                        for(int j = 0; j < numberOfPlayer; j++){
                            Text t = new Text("Player " + (j+1) + " Captures:");
                            t.setStyle("-fx-font: 15 arial; -fx-fill: white;");
                            playerCaptures[j].getChildren().add(t);
                        }
                    }

                    setNextPlayer(currentPlayerIdx);
                    playerCaptureButton[currentPlayerIdx].setDisable(false);

                }
                else{
                    captureStatus.setText("Invalid Capture");
                }
            }
        }

        for(int i = 0; i < 4; i++){
            playerCaptureButton[i].setOnAction(new CaptureHandler());
        }

        /* Game Logic end */
    }

    public static void main(String[] args){
        launch(args);
    }

}