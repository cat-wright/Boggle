//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
//import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javafx.util.Duration;

public class UI_Control extends Application implements EventHandler<ActionEvent>
{
  private int B_S;
  Dictionary dictionary = new Dictionary();
  private TextField word = new TextField();
  private Label label = new Label();
  private Board board;
  private int pad = 10;
  Stage window;
  Scene opener;
  Scene scene;
  private Button playWord = new Button("Check Word");
  private Button playButton_4;
  private Button playButton_5;
  private final int MIN_WORD_LENGTH = 3;
  private final int FONT_SIZE = 20;
  private final int HEIGHT = 660;
  private final int CANVAS_SIZE = 500;
  private int BLOCK_SIZE;
  //private final int WORDS_SIZE = 300;
  private final int WIDTH = 500 + 2*pad;

  //TIMER
  /*
  private final int START_TIME = 59;
  private int minutes = 2;
  private Timeline timeline;
  private Label timerLabel = new Label();
  private Label minuteLabel = new Label();
  private Integer timeSeconds = START_TIME;
  private Button timerButton;
  */

  private Canvas canvas;
  private GraphicsContext gct;

  /*
  String buildTime()
  {
    String text;
    if(timeSeconds < 10) text = "" + minutes + " : 0" + timeSeconds.toString();
    else text = "" + minutes + " : " + timeSeconds.toString();
    return text;
  }
  */

  public void start(Stage primaryStage)
  {
    //TIMER
    /*
    Group root = new Group();
    HBox timerBox = new HBox();
    timerLabel.setText(buildTime());
    //minuteLabel.setText("" + minutes);
    timerLabel.setTextFill(Color.BLACK);
    //minuteLabel.setTextFill(Color.BLACK);
    timerLabel.setFont(Font.font("Verdana", FONT_SIZE));
    minuteLabel.setFont(Font.font("Verdana", FONT_SIZE));
    timerButton = new Button("Start Game");
    timerButton.setOnAction(e -> {
      if(timeline != null) timeline.stop();
      timeSeconds = START_TIME;
      timerLabel.setText(buildTime());
      //minuteLabel.setText("" + minutes);
      timeline = new Timeline();
      timeline.setCycleCount(Timeline.INDEFINITE);
      timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event ->
        {
          timeSeconds--;
          if(minutes == 0 && timeSeconds <= 15)
          {
            timerLabel.setTextFill(Color.RED);
          }
          timerLabel.setText(buildTime());
          if(timeSeconds <=0 && minutes >= 0)
          {
            timeSeconds = START_TIME + 1;
            minutes--;
            //minuteLabel.setText("" + minutes);
          }
          if(timeSeconds < 0 || minutes < 0) timeline.stop();
        }
      ));
      timeline.playFromStart();
    });
    timerBox.getChildren().addAll(timerButton, timerLabel);
    root.getChildren().add(timerBox);
    //
    */

    window = primaryStage;
    VBox vbox = new VBox();
    HBox hbox = new HBox();
    opener = new Scene(hbox);
    scene = new Scene(vbox, WIDTH, HEIGHT);
    playButton_4 = new Button("Play with 4");
    playButton_5 = new Button("Play with 5");
    playButton_4.setOnAction(this);
    playButton_5.setOnAction(this);

    window.setTitle("Boggle Version 2");
    playWord.setOnAction(this);
    word.setFont(Font.font("Verdana", FONT_SIZE));

    Tooltip msgToolTip = new Tooltip("Enter lower case words, at least " + MIN_WORD_LENGTH + " letters long");
    Tooltip.install(word, msgToolTip);

    canvas = new Canvas(CANVAS_SIZE, CANVAS_SIZE);
    gct = canvas.getGraphicsContext2D();

    vbox.setPadding(new Insets(pad, pad, pad, pad));
    vbox.setSpacing(15);

    HBox canvasAndScore = new HBox();
    canvasAndScore.getChildren().add(canvas);
    //TIMER canvasAndScore.getChildren().addAll(canvas, root);
    vbox.getChildren().addAll(canvasAndScore, word, label, playWord);
    hbox.getChildren().addAll(playButton_4, playButton_5);

    window.setScene(opener);
    window.show();
  }

  //Draws the board
  private void setGCT(int B_S)
  {
    for(int i = 0; i < B_S; i++)
    {
      for(int j = 0; j < B_S; j++)
      {
        if((i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0)) gct.setFill(Color.WHITE);
        else gct.setFill(Color.RED);
        gct.fillRect(BLOCK_SIZE*i, BLOCK_SIZE*j, BLOCK_SIZE, BLOCK_SIZE);
        gct.setFill(Color.BLACK);
        gct.setFont(Font.font("Verdana", 60));
        //INDEXING WTF
        char text = (char)((int)board.get(j,i) - 32);
        gct.fillText("" + text, BLOCK_SIZE*i + BLOCK_SIZE/3, BLOCK_SIZE*j + BLOCK_SIZE/1.5);
      }
    }
  }

  @Override
  public void handle(ActionEvent event)
  {
    Object source = event.getSource();
    if(source == playWord)
    {
      String message = word.getText();
      board.findWord(message);
      if(board.returnTruth())
      {
        if(checkText(message)) label.setText("Yes!");
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
  }

  private boolean checkText(String message)
  {
    if(message.length() >= MIN_WORD_LENGTH) if(dictionary.contains(message)) return true;
    return false;
  }

  public static void main(String[] args)
  {
    launch(args);
  }
}