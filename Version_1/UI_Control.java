import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UI_Control extends Application implements EventHandler<ActionEvent>
{
  Dictionary dictionary = new Dictionary();
  private Text labelDesc = new Text("Find a valid word");
  private Label labelText = new Label("Your Word is: ");
  private Label labelMsg = new Label();
  private TextField word = new TextField();

  private Button playWord = new Button("Check Word");
  private final int MIN_WORD_LENGTH = 3;
  private final int FONT_SIZE = 30;
  private final int HEIGHT = 300;
  private final int WIDTH = 500;

  public void start(Stage stage)
  {
    stage.setTitle("Dictionary");
    playWord.setOnAction(this);

    labelDesc.setFont(Font.font("Verdana", FONT_SIZE));
    labelText.setFont(Font.font("Verdana", FONT_SIZE));
    labelDesc.setWrappingWidth(WIDTH-20);
    labelMsg.setFont(Font.font("Verdana", FONT_SIZE));
    word.setFont(Font.font("Verdana", FONT_SIZE));

    Tooltip msgToolTip = new Tooltip("Must be at least " + MIN_WORD_LENGTH + " letters long");
    Tooltip.install(word, msgToolTip);

    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    VBox vbox = new VBox();
    vbox.setPadding(new Insets(10, 10, 10, 10));
    vbox.setSpacing(15);

    vbox.getChildren().addAll(labelDesc, word, labelText, labelMsg, playWord, canvas);

    Scene scene = new Scene(vbox, WIDTH, HEIGHT);
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void handle(ActionEvent event)
  {
    Object source = event.getSource();
    if(source == playWord)
    {
      if(checkText())
      {
        labelMsg.setTextFill(Color.GREEN);
        labelMsg.setText("VALID");
      }
      else
      {
        labelMsg.setTextFill(Color.RED);
        labelMsg.setText("INVALID");
      }
    }
  }

  private boolean checkText()
  {
    String message = word.getText();
    if(message.length() > MIN_WORD_LENGTH) if(dictionary.contains(message)) return true;
    return false;
  }

  public static void main(String[] args)
  {
    launch(args);
  }
}
