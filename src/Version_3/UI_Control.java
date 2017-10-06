import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javafx.util.Duration;

public class UI_Control extends Application implements EventHandler<ActionEvent>
{
  private int B_S;
  private TextField word = new TextField();
  private Label label = new Label();
  private Label foundWords = new Label();
  private Label scoreTitle = new Label();
  private int score = 0;
  private String foundText;
  private Board board;
  private int pad = 10;
  private Stage window;
  private Scene opener;
  private Scene scene;
  private Button playWord = new Button("Check Word");
  private Button playButton_4;
  private Button playButton_5;
  private final int MIN_WORD_LENGTH = 3;
  private final int FONT_SIZE = 20;
  private final int FONT_SIZE_BOARD = 50;
  private final int LINE_LENGTH = 180;
  private final int HEIGHT = 700;
  private final int CANVAS_SIZE = 400;
  private final int WORDS_SIZE = 300;
  private final int WIDTH = 500 + 2*pad + WORDS_SIZE;
  private int BLOCK_SIZE;

  //TIMER

  private final int START_TIME = 59;
  private final int START_MIN = 2;
  private Timeline timeline;
  private Label timerLabel = new Label();
  private Integer timeSeconds = START_TIME;
  private Integer minutes = START_MIN;
  private Button timerButton;
  private Button endGame;

  private boolean gamePlay = false;

  //GRAPHICS

  private Canvas canvas;
  private GraphicsContext gct;
  private Color bkgd = Color.AQUAMARINE;
  private Color pink = Color.HOTPINK;
  private String font = "Krungthep";
  //

  String buildTime()
  {
    String text;
    if(timeSeconds < 10) text = "0" + minutes + ":0" + timeSeconds.toString();
    else text = "0" + minutes + ":" + timeSeconds.toString();
    return text;
  }

  void formatButton(Button b)
  {
    Background backgroundBlk = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
    b.setFont(Font.font(font, 16));
    b.setTextFill(Color.AQUAMARINE);
    b.setBackground(backgroundBlk);
  }

  public void start(Stage primaryStage)
  {
    //TIMER
    timerLabel.setText(buildTime());
    timerLabel.setTextFill(Color.BLACK);
    timerLabel.setFont(Font.font(font, FontWeight.SEMI_BOLD, FONT_SIZE_BOARD));
    timerButton = new Button("Start Game");
    endGame = new Button("End Game");
    formatButton(endGame);
    endGame.setOnAction(this);
    formatButton(timerButton);
    timerButton.setOnAction(e -> {
      if(!gamePlay)
      {
        score = 0;
        scoreTitle.setText("Score: " + score);
        foundText = null;
        foundWords.setText("");
        timerLabel.setTextFill(Color.BLACK);
        minutes = START_MIN;
        gamePlay = true;
        setGCT(B_S);
        if (timeline != null) timeline.stop();
        timeSeconds = START_TIME;
        timerLabel.setText(buildTime());
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event ->
        {
          timeSeconds--;
          if (minutes == 0 && timeSeconds <= 15)
          {
            timerLabel.setTextFill(Color.RED);
          }
          timerLabel.setText(buildTime());
          if (timeSeconds <= 0 && minutes >= 0)
          {
            timeSeconds = START_TIME + 1;
            minutes--;
          }
          if (timeSeconds < 0 || minutes < 0)
          {
            gamePlay = false;
            timeline.stop();
            board = new Board(B_S);
            setGCT(B_S);
          }
        }
        ));
        timeline.playFromStart();
      }
    });
    //

    window = primaryStage;
    VBox vbox = new VBox();
    HBox hbox = new HBox();
    hbox.setPadding(new Insets(pad, pad, pad, pad));
    hbox.setSpacing(15);
    hbox.setAlignment(Pos.CENTER);
    VBox openingVbox = new VBox();
    Background background = new Background(new BackgroundFill(bkgd, CornerRadii.EMPTY, Insets.EMPTY));
    Background backgroundOpener = new Background(new BackgroundFill(pink, CornerRadii.EMPTY, Insets.EMPTY));
    Label title = new Label("BOGGLE");
    title.setTextFill(bkgd);
    title.setFont(Font.font(font, 100));
    openingVbox.setPadding(new Insets(100, 100, 100, 100));
    openingVbox.backgroundProperty().set(backgroundOpener);
    openingVbox.getChildren().addAll(title, hbox);
    opener = new Scene(openingVbox);
    scene = new Scene(vbox, WIDTH, HEIGHT);
    playButton_4 = new Button("4 TILES");
    playButton_5 = new Button("5 TILES");
    formatButton(playButton_4);
    formatButton(playButton_5);
    playButton_4.setOnAction(this);
    playButton_5.setOnAction(this);

    window.setTitle("Boggle Version 3");
    formatButton(playWord);
    playWord.setOnAction(this);
    word.setFont(Font.font(font, FONT_SIZE));

    Tooltip msgToolTip = new Tooltip("Enter lower case words, at least " + MIN_WORD_LENGTH + " letters long");
    msgToolTip.setFont(Font.font(font));
    Tooltip.install(word, msgToolTip);

    canvas = new Canvas(CANVAS_SIZE, CANVAS_SIZE);
    gct = canvas.getGraphicsContext2D();

    vbox.setPadding(new Insets(pad, pad, pad, pad));
    vbox.setSpacing(15);
    vbox.setAlignment(Pos.BASELINE_CENTER);
    vbox.backgroundProperty().setValue(background);

    HBox canvasAndScore = new HBox();
    VBox found = new VBox();
    HBox scoreBox = new HBox();
    Label foundTitle = new Label("Valid Words: ");
    scoreTitle.setText("Score: " + score);
    scoreBox.setSpacing(120);
    scoreTitle.setFont(Font.font(font, FONT_SIZE));
    scoreBox.getChildren().addAll(foundTitle, scoreTitle);
    foundTitle.setFont(Font.font(font, FONT_SIZE));
    Line horizLine = new Line(0, 0, LINE_LENGTH, 0);
    found.getChildren().addAll(scoreBox, horizLine, foundWords);
    canvasAndScore.getChildren().addAll(canvas, found);
    HBox buttons = new HBox();
    buttons.setSpacing(15);
    buttons.setAlignment(Pos.CENTER);
    buttons.getChildren().addAll(timerButton, endGame);
    vbox.getChildren().addAll(timerLabel, canvasAndScore, buttons, word, label, playWord);
    hbox.getChildren().addAll(playButton_4, playButton_5);

    window.setScene(opener);
    window.show();
  }

  //Draws the board
  private void setGCT(int B_S)
  {
    int buf = 5;
    gct.setFill(bkgd);
    gct.fillRect(0,0, CANVAS_SIZE, CANVAS_SIZE);
    for(int i = 0; i < B_S; i++)
    {
      for(int j = 0; j < B_S; j++)
      {
        if (gamePlay)
        {
          if ((i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0)) gct.setFill(Color.BLACK);
          else gct.setFill(pink);
          //gct.fillOval(BLOCK_SIZE * i + buf, BLOCK_SIZE * j + buf, BLOCK_SIZE - (2 * buf), BLOCK_SIZE - (2 * buf));
          gct.fillRoundRect(BLOCK_SIZE*i + buf, BLOCK_SIZE*j + buf, BLOCK_SIZE- (2 * buf), BLOCK_SIZE- (2 * buf), 15, 15);
          gct.setFill(bkgd);
          gct.setFont(Font.font(font, FontWeight.BLACK, FONT_SIZE_BOARD));
          //INDEXING WTF
          char text = (char) ((int) board.get(j, i) - 32);
          gct.fillText("" + text, BLOCK_SIZE * i + BLOCK_SIZE / 3, BLOCK_SIZE * j + BLOCK_SIZE / 1.5);
        }
        else
        {
          gct.setFill(Color.BLACK);
          gct.fillRoundRect(BLOCK_SIZE*i + buf, BLOCK_SIZE*j + buf, BLOCK_SIZE- (2 * buf), BLOCK_SIZE- (2 * buf), 15, 15);

          //gct.fillOval(BLOCK_SIZE * i + buf, BLOCK_SIZE * j + buf, BLOCK_SIZE - (2 * buf), BLOCK_SIZE - (2 * buf));
        }
      }
    }
  }

  @Override
  public void handle(ActionEvent event)
  {
    Object source = event.getSource();
    if(source == playWord)
    {
      label.setFont(Font.font(font, FontWeight.SEMI_BOLD, FONT_SIZE));
      foundWords.setFont(Font.font(font));
      String message = word.getText();
      if(board.returnFinalList().contains(message))
      {
        foundWords.setText(updateFoundWords(message));
        score = updateScore(message);
        label.setText("Yes!");
        scoreTitle.setText("Score: " + score);
      }
      else label.setText("Nope.");
    }
    else if(source == playButton_4)
    {
      B_S = 4;
      board = new Board(4);
      BLOCK_SIZE = CANVAS_SIZE / 4;
      setGCT(4);
      window.setScene(scene);
    }
    else if(source == playButton_5)
    {
      B_S = 5;
      board = new Board(B_S);
      BLOCK_SIZE = CANVAS_SIZE / B_S;
      setGCT(B_S);
      window.setScene(scene);
    }
    else if(source == endGame)
    {
      gamePlay = false;
      setGCT(B_S);
      board = new Board(B_S);
      timeline.stop();
    }
  }

  int updateScore(String word)
  {
    return score + word.length()-2;
  }

  String updateFoundWords(String message)
  {
    if(foundText == null) foundText = message;
    else foundText += "\n" + message;
    return foundText;
  }

  public static void main(String[] args)
  {
    launch(args);
  }
}
