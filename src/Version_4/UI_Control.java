//***************************************//
// Catherine Wright                      //
//                                       //
// UI_Control class controls the         //
// interface, and instantiates Board     //
// class and Coordinate class            //
//                                       //
// Contains main : runs application      //
//***************************************//

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javafx.util.Duration;
import java.util.ArrayList;

public class UI_Control extends Application implements EventHandler<ActionEvent>
{
  //Board size, score, padding of vboxes
  private int B_S;
  private int score = 0;
  private int pad = 10;
  private int blockSize;
  private String foundText; //Text displayed when found
  private String message = ""; //Current word
  private Board board;
  private Stage window;
  //

  //LABELS
  private Label foundWords = new Label(); //Words found on the board by player
  private Label words; //All possible words on the board, displayed after game over
  private Label scoreTitle = new Label(); //Label holding the current score
  //

  //Array lists
  private ArrayList<Coordinate> coords = new ArrayList<>(); //Coordinates of clicked letters
  private ArrayList<Coordinate> possible = new ArrayList<>(); //surrounding squares around current coordinate
  private ArrayList<String> foundList = new ArrayList<>(); //list of words found to avoid duplicates
  //

  //MUSIC
  private String musicFile;
  private Media sound;
  private MediaPlayer mediaPlayer;
  //

  //SCENES
  private Scene scene; //Main play
  private Scene list; //Scene to show all possible words
  //

  //BUTTONS
  private Button playWord = new Button("Play Word");
  private Button clear = new Button("Clear");
  private Button rapMusic = new Button("I'm more of a rap fan myself... [EXPLICIT]");
  private Button playButton_4;
  private Button playButton_5;
  private Button showFullList;
  private Button endGame;
  //

  //FINAL VALUES
  private final int FONT_SIZE = 20;
  private final int FONT_SIZE_BOARD = 50;
  private final int CANVAS_SIZE = 400;
  private final int WORDS_SIZE = 300;
  private final int WIDTH = 500 + 2*pad + WORDS_SIZE;
  //

  //TIMER
  private final int START_TIME = 59;
  private final int START_MIN = 2;
  private Timeline timeline;
  private Label timerLabel = new Label();
  private Integer timeSeconds = START_TIME;
  private Integer minutes = START_MIN;
  //

  //GRAPHICS
  private GraphicsContext gct;
  private Color bkgd = Color.AQUAMARINE;
  private Color pink = Color.DEEPPINK;
  private String font = "Krungthep";
  //

  //BACKGROUNDS
  private Background background = new Background(new BackgroundFill(bkgd, CornerRadii.EMPTY, Insets.EMPTY));
  private Background backgroundOpener = new Background(new BackgroundFill(pink, CornerRadii.EMPTY, Insets.EMPTY));
  private Background backgroundBlk = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
  //

  private boolean gamePlay = false; //game is currently not started

  public static void main(String[] args)
  {
    launch(args);
  }

  /**
   * start() method:
   * @param primaryStage : stage of javafx to be displayed
   *
   * start method implements the GUI of the game.  Broken into chunks:
   *
   *  - Sets constants, and names the game
   *  - MUSIC:
   *          creates a mediaPlayer to play background music
   *  - TIMER:
   *          If game is not in play, disables endGame button
   *          If startGame button is pushed, timeline is created
   *            Board is displayed, redrawn, and timer begins counting down
   *  - End SCENE:
   *          If showFullList button is pushed, scene changes to end scene and
   *            full word list is displayed
   *  - Opener SCENE:
   *          Original scene with buttons to chose music and board size
   *  - Main SCENE:
   *          Scene containing timer, board, foundList, score, and remaining buttons
   */
  public void start(Stage primaryStage)
  {
    final int LINE_LENGTH = 180;
    final int HEIGHT = 650;
    window = primaryStage;
    window.setTitle("BOGGLE");

    //MUSIC
    musicFile = UI_Control.class.getResource("ThomasTheTank.mp3").toString();
    sound = new Media(musicFile);
    mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.play();
    //

    //TIMER
    timerLabel.setText(buildTime());
    timerLabel.setTextFill(Color.BLACK);
    timerLabel.setFont(Font.font(font, FontWeight.SEMI_BOLD, FONT_SIZE_BOARD));
    endGame = new Button("End Game");
    formatButton(endGame);
    if(!gamePlay) endGame.setDisable(true);

    Button timerButton = new Button("Start Game");
    timerButton.setFont(Font.font(font, 16));
    timerButton.setTextFill(bkgd);
    timerButton.setBackground(backgroundBlk);
    timerButton.setOnAction(e -> {
      if(!gamePlay)
      {
        gamePlay = true;
        endGame.setDisable(false);
        score = 0;
        scoreTitle.setText("Score: " + score);
        foundText = null;
        words.setText(board.outputFinalList());
        foundWords.setText("");
        timerLabel.setTextFill(Color.BLACK);
        minutes = START_MIN;
        showFullList.setDisable(true);
        setGCT(B_S);
        if (timeline != null) timeline.stop();
        timeSeconds = START_TIME;
        timerLabel.setText(buildTime());
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event ->
        {
          timeSeconds--;
          if (minutes == 0 && timeSeconds <= 15) timerLabel.setTextFill(Color.RED);
          timerLabel.setText(buildTime());
          if (timeSeconds <= 0 && minutes >= 0)
          {
            timeSeconds = START_TIME + 1;
            minutes--;
          }
          if (timeSeconds < 0 || minutes < 0) setEndGame();
        }
        ));
        timeline.playFromStart();
      }
    });
    //

    //Ending scene with list of words
    Button hideFullList = new Button("Hide List");
    formatButton(hideFullList);
    hideFullList.setOnAction(event -> window.setScene(scene));

    showFullList = new Button("Show All Words");
    if(!gamePlay) showFullList.setDisable(true);
    formatButton(showFullList);
    showFullList.setOnAction(event -> window.setScene(list));

    Label popupTitle = new Label("All Possible Words");
    popupTitle.setFont(Font.font(font, FONT_SIZE));
    popupTitle.setTextFill(bkgd);

    Line wordLine = new Line(0,0,LINE_LENGTH,0);

    words = new Label();
    words.setFont(Font.font(font, FONT_SIZE));
    words.setTextFill(Color.BLUEVIOLET);

    VBox popupBox = new VBox();
    popupBox.setPadding(new Insets(pad, pad, pad, pad));
    popupBox.setAlignment(Pos.CENTER);
    popupBox.setBackground(backgroundOpener);
    popupBox.getChildren().addAll(popupTitle, wordLine, words, hideFullList);
    ScrollPane scrollPane = new ScrollPane(popupBox);
    list = new Scene(scrollPane);
    //

    //OPENER scene
    Label title = new Label("BOGGLE");
    title.setTextFill(bkgd);
    title.setFont(Font.font(font, 100));
    VBox openingVbox = new VBox();
    Scene opener = new Scene(openingVbox);

    HBox openingButtons = new HBox();
    openingButtons.setPadding(new Insets(pad, pad, pad, pad));
    openingButtons.setSpacing(15);
    openingButtons.setAlignment(Pos.CENTER);

    openingVbox.setPadding(new Insets(100, 100, 100, 100));
    openingVbox.backgroundProperty().set(backgroundOpener);
    openingVbox.getChildren().addAll(title, openingButtons, rapMusic);

    playButton_4 = new Button("4 TILES");
    playButton_5 = new Button("5 TILES");
    formatButton(playButton_4);
    formatButton(playButton_5);
    formatButton(rapMusic);
    rapMusic.setBackground(backgroundOpener);
    rapMusic.setTextFill(Color.BLACK);
    rapMusic.setAlignment(Pos.BOTTOM_CENTER);
    rapMusic.setOnAction(event -> {
      mediaPlayer.stop();
      musicFile = UI_Control.class.getResource("RAPMusic.m4a").toString();
      sound = new Media(musicFile);
      mediaPlayer = new MediaPlayer(sound);
      mediaPlayer.play();
    });
    openingButtons.getChildren().addAll(playButton_4, playButton_5);

    //Main Scene
    VBox vbox = new VBox();
    scene = new Scene(vbox, WIDTH, HEIGHT);

    formatButton(playWord);
    formatButton(clear);
    vbox.setPadding(new Insets(pad, pad, pad, pad));
    vbox.setSpacing(15);
    vbox.setAlignment(Pos.BASELINE_CENTER);
    vbox.backgroundProperty().setValue(background);

    Canvas canvas = new Canvas(CANVAS_SIZE, CANVAS_SIZE);
    gct = canvas.getGraphicsContext2D();
    canvas.setOnMouseClicked(event -> {
      if(gamePlay) doAction(event);
    });

    Label foundTitle = new Label("Valid Words: ");

    HBox scoreBox = new HBox();
    scoreBox.setSpacing(120);
    scoreTitle.setText("Score: " + score);
    scoreTitle.setFont(Font.font(font, FONT_SIZE));
    scoreBox.getChildren().addAll(foundTitle, scoreTitle);
    foundTitle.setFont(Font.font(font, FONT_SIZE));

    Line horizLine = new Line(0, 0, LINE_LENGTH, 0);

    VBox found = new VBox();
    found.getChildren().addAll(scoreBox, horizLine, foundWords);

    HBox canvasAndScore = new HBox();
    canvasAndScore.getChildren().addAll(canvas, found);
    HBox buttons = new HBox();
    buttons.setSpacing(15);
    buttons.setAlignment(Pos.CENTER);
    buttons.getChildren().addAll(timerButton, endGame, showFullList);

    HBox bottomButtons = new HBox(playWord, clear);
    bottomButtons.setAlignment(Pos.CENTER);
    bottomButtons.setSpacing(15);

    vbox.getChildren().addAll(timerLabel, canvasAndScore, buttons, bottomButtons);
    //

    window.setScene(opener);
    window.show();
  }

  /**
   * Overridden handle() method:
   * @param event : ActionEvent of button clicked
   * No output
   *
   * Finds source (Button name) clicked.  Depending on which button, performs
   * a different action.
   *
   * Buttons:
   *      playWord: checks if selected word is in the finalList of acceptable words
   *                if it is - changes score, adds word to foundWords label,
   *                clears selection and redraws board
   *      clear: Clears current selection and redraws board
   *      playButton_4 or playButton_5: Sets board size accordingly and generates starting
   *                gameboard.
   *      endGame: runs setEndGame() function to end a game
   *      rapMusic: changes background music to rap
   */
  @Override
  public void handle(ActionEvent event)
  {
    Object source = event.getSource();
    if(source == playWord)
    {
      foundWords.setFont(Font.font(font, FONT_SIZE));
      if(board.returnFinalList().contains(message) && !foundList.contains(message))
      {
        foundWords.setText(updateFoundWords(message));
        score = updateScore(message);
        scoreTitle.setText("Score: " + score);
        foundList.add(message);
      }
      message = "";
      coords.clear();
      setGCT(B_S);
      possible.clear();
    }
    else if(source == clear)
    {
      message = "";
      coords.clear();
      setGCT(B_S);
      possible.clear();
    }
    else if(source == playButton_4 || source == playButton_5)
    {
      B_S = (source == playButton_4) ? 4 : 5;
      board = new Board(B_S);
      blockSize = CANVAS_SIZE / B_S;
      setGCT(B_S);
      window.setScene(scene);
    }
    else if(source == endGame) setEndGame();
  }

  /**
   * doAction() method:
   * @param event : mouseEvent that takes place on the board
   * No output
   *
   * Used on canvas that displays the board.  Finds board coordinates of x and y
   * values clicked, and if click is valid, adds coordinate to coords array list.
   *              Coords array list is used in repainting the board with purple
   */
  private void doAction(MouseEvent event)
  {
    int yval = (int) Math.floor(event.getX() / blockSize);
    int xval = (int) Math.floor(event.getY() / blockSize);
    Coordinate boardCoord = new Coordinate(xval, yval);
    if(!coords.contains(boardCoord))
    {
      if (possible.isEmpty() || possible.contains(boardCoord))
      {
        possible = boardCoord.buildPossibles();
        coords.add(boardCoord);
        message += board.getC(boardCoord);
        setGCT(B_S);
      }
    }
  }

  /**
   * setEndGame() method:
   * No parameters or outputs
   *
   * sets gamePlay to false, stops the timer, redraws the board black, makes
   * showFullList button available and  endGame button not available, clears
   * current selection and generates a new board
   */
  private void setEndGame()
  {
    gamePlay = false;
    timeline.stop();
    setGCT(B_S);
    showFullList.setDisable(false);
    endGame.setDisable(true);
    coords.clear();
    board = new Board(B_S);
  }

  /**
   * updateScore() method:
   * @param word : String found on board that is in dictionary
   * @return new score as an int
   *
   * When a player finds a valid word, the score is raised by 2 less than the
   * length of the word
   */
  private int updateScore(String word)
  {
    return score + word.length()-2;
  }

  /**
   * updateFoundWords() method:
   * @param message : word to be added to foundText label
   * @return new foundText label
   *
   * updates foundText label if a valid word is found on board
   */
  private String updateFoundWords(String message)
  {
    if(foundText == null) foundText = message;
    else foundText += "\n" + message;
    return foundText;
  }

  /**
   * setGCT() method:
   * @param B_S : current board size (4 or 5)
   * No output
   *
   * Redraws the board on the scene.
   *            If gamePlay is false, redraws all tiles as black
   *            If board is showing, draws every other tile as pink and displays
   *                      characters
   *            If board has a selection, draws selected tiles as purple
   */
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
          Coordinate c = new Coordinate(j, i);
          if(coords.contains(c)) gct.setFill(Color.BLUEVIOLET);
          else if ((i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0)) gct.setFill(Color.BLACK);
          else gct.setFill(pink);

          gct.fillRoundRect(blockSize *i + buf, blockSize *j + buf, blockSize - (2 * buf), blockSize - (2 * buf), 15, 15);
          gct.setFill(bkgd);
          gct.setFont(Font.font(font, FontWeight.BLACK, FONT_SIZE_BOARD));
          //INDEXING WTF
          char text = (char) ((int) board.getC(c) - 32);
          gct.fillText("" + text, blockSize * i + blockSize / 3, blockSize * j + blockSize / 1.5);
        }
        else
        {
          gct.setFill(Color.BLACK);
          gct.fillRoundRect(blockSize *i + buf, blockSize *j + buf, blockSize - (2 * buf), blockSize - (2 * buf), 15, 15);
        }
      }
    }
  }

  /**
   * buildTime() method:
   * @return String of current time remaining
   *
   * Used with the timer in start() method.  Makes remaining time look like
   * a clock
   */
  private String buildTime()
  {
    String text;
    if(timeSeconds < 10) text = "0" + minutes + ":0" + timeSeconds.toString();
    else text = "0" + minutes + ":" + timeSeconds.toString();
    return text;
  }

  /**
   * formatButton() method:
   * @param b : button to be formatted
   *
   * Sets font, background color, text color, and action of a button b
   */
  private void formatButton(Button b)
  {
    if(b == playWord || b == clear)
    {
      b.setFont(Font.font(font, FONT_SIZE));
      b.setBackground(backgroundBlk);
      b.setTextFill(pink);
      b.setOnAction(this);
    }
    else
    {
      b.setFont(Font.font(font, 16));
      b.setTextFill(bkgd);
      b.setBackground(backgroundBlk);
      b.setOnAction(this);
    }
  }
}
