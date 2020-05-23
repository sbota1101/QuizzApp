package application;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;


public class Controller {

    // Declaring all UI objects
    @FXML
    private Label questionLabel;

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Label questionNum;

    @FXML
    private Label scoreLabel;

    // variables
    static ArrayList<Question> questions;
    static int percent;
    static int pauseValue = 1500;


    @FXML
    void initialize() {  // This gets run once to initialize the event handlers and such

        // Loads questions from 'questions.txt'
        questions = Question.loadQuestions("resources/questions.txt");

        Question.setButtons(button1, button2, button3, button4);

        // Displays the first question in the GUI
        questions.get(Question.getQuestionIndex()).displayQuestion(questionLabel, questionNum);

        // Event handlers for the buttons calls method
        button1.setOnAction(this::handleButtonAction);
        button2.setOnAction(this::handleButtonAction);
        button3.setOnAction(this::handleButtonAction);
        button4.setOnAction(this::handleButtonAction);
    }


    // Called when a button is clicked
    private void handleButtonAction(ActionEvent event) {

        button1.setDisable(true);
        button2.setDisable(true);
        button3.setDisable(true);
        button4.setDisable(true);

        // Checks to see if user pressed the correct button; changes score
        // Also checks if all questions exhausted; exits if yes
        // Uses getTarget() to get the button that was clicked
        questions.get(Question.getQuestionIndex()).checkCorrect((Button) event.getTarget(), questions, scoreLabel);

        questions.get(Question.getQuestionIndex()).displayQuestion(questionLabel, questionNum);

        button1.setDisable(false);
        button2.setDisable(false);
        button3.setDisable(false);
        button4.setDisable(false);

    }


    // This method is run when all questions have been answered and displays score
    public static void finished(int score, int questionsCorrect) {
        // Calculates percent value
        if (questionsCorrect == 0) { percent = 0; }
        else { percent = (int) ((double)questionsCorrect/(double)questions.size() * 100); }

        // Creates alert; tells score and questions correct
        Alert finish = new Alert(Alert.AlertType.INFORMATION);
        finish.setTitle("You Win!");
        finish.setHeaderText("Score: " + score);
        finish.setContentText("Questions Correct: " + questionsCorrect + " out of " + questions.size() + " (" + percent + "%)");
        //  finish.getDialogPane().getStylesheets().add(Question.class.getResource("lightTheme.css").toExternalForm());
        finish.showAndWait();

        // Exits
        Platform.exit();
        System.exit(0);
    }
}

