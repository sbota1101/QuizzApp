package application;

// This class handles questions and all their information
// All questions are instances of this class

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


// This class holds the correct answer and wrong answers for each question
public class Question {

    // static variables (available to any instance)
    static Random rand = new Random();
    static int score = 0;
    static int questionCount = 1;
    static int questionIndex = 0;
    static int questionsCorrect = 0;
    static ArrayList<Button> buttons;

    // instance specific variables
    String question;
    String correct;
    ArrayList<String> wrongs;
    Button randButton;


    // Constructor; creates "wrongs" ArrayList which stores the wrong/decoy answers
    public Question(String question, String correct, String wrong1, String wrong2, String wrong3) {
        this.question = question;
        this.correct = correct;
        this.wrongs = new ArrayList<>();
        this.wrongs.add(wrong1);
        this.wrongs.add(wrong2);
        this.wrongs.add(wrong3);
    }


    // Loads questions from a file and returns an ArrayList of Question objects
    public static ArrayList<Question> loadQuestions(String filename) {
        ArrayList<Question> questions = new ArrayList<>();

        try {
            Path filePath = Paths.get("C:\\Users\\Sorina\\IdeaProjects\\QuizzApp\\src\\resources\\questions.txt");
            // Reads lines from file
            Files.lines(filePath).forEach(line -> {
                if (line.startsWith("//") || line.isEmpty()) { // Ignores lines starting with '//' (comments)
                    return;
                }
                String[] tokens = line.split(";" ); // Splits line into array using delimiter
                // Adds Question object to ArrayList using split line and trims of excessive spaces
                questions.add(new Question(tokens[0].trim(), tokens[1].trim(), tokens[2].trim(), tokens[3].trim(), tokens[4].trim()));
            });
        } catch (IOException e) {
            // Displays an alert about question file not found
            Alert notFound = new Alert(Alert.AlertType.ERROR);
            notFound.setTitle("Questions File Error");
            notFound.setHeaderText("Questions file not found!");
            notFound.setContentText("Make sure there is a questions.txt file in the same directory.");
            // notFound.getDialogPane().getStylesheets().add(Question.class.getResource("lightTheme.css").toExternalForm());
            notFound.showAndWait();
            // Exits
            Platform.exit();
            System.exit(0);
        }

        // Checks to make sure there are actually questions in the ArrayList; otherwise alerts and exits
        if (questions.isEmpty()) {
            Alert emptyQuestion = new Alert(Alert.AlertType.WARNING);
            emptyQuestion.setTitle("No Questions found");
//            emptyQuestion.setHeaderText("No Questions were found in " + filename);
//            emptyQuestion.setContentText("Make sure there are questions in " + filename + ".");
//            //emptyQuestion.getDialogPane().getStylesheets().add(Question.class.getResource("lightTheme.css").toExternalForm());
//            emptyQuestion.showAndWait();

            //Platform.exit();
            System.exit(0);
        }
        return questions;
    }


    // Groups the 4 buttons in an ArrayList for easy access
    public static void setButtons(Button...buttonsArray) {
        buttons = new ArrayList<>(Arrays.asList(buttonsArray));
    }


    // questionIndex getter
    public static int getQuestionIndex() { return questionIndex; }


    // Takes input of label and buttons and displays the instance question and answers in the GUI
    public void displayQuestion(Label lbl, Label correctLabel) {

        ArrayList<Button> buttonsFxml = new ArrayList<>(buttons);

        // Sets question label to instance variable "question"
        lbl.setText(this.question);
        correctLabel.setText("Question " + questionCount);

        // Generates random integer from 0 to 3
        int randInt = rand.nextInt(4);
        randButton = buttonsFxml.get(randInt);

        // Sets correct button to correct answer
        buttonsFxml.get(randInt).setText(this.correct);
        buttonsFxml.remove(randInt);
        // The correct button is removed so it is easier to set other buttons to wrong answers without checking
        // whether it is the right answer button or not

        // Shuffles "wrongs" ArrayList so buttons aren't in predictable order
        Collections.shuffle(this.wrongs);
        for (Button b : buttonsFxml) {
            // Sets button to wrong of the same index (not a problem due to the shuffle)
            b.setText(this.wrongs.get(buttonsFxml.indexOf(b)));
        }
    }


    // Checks whether the clicked button is the correct answer or not
    public void checkCorrect(Button b, ArrayList<Question> questions, Label scoreLabel) {

        // Checks if the button clicked in correct
        // If correct, increments "score" and "questionsCorrect" and changes Score label
        if (this.randButton == b) {
            b.getStyleClass().add("correct"); // Makes button green
            score += 10;
            scoreLabel.setText("Score: " + score);
            questionsCorrect += 1;
        } else {
            b.getStyleClass().add("wrong"); // Makes button red
            this.randButton.getStyleClass().add("correct");
        }

        // Checks if all questions have ben used; if yes, calls "finished" to show score and exit
        if (questions.size() == questionCount) {
            Controller.finished(score, questionsCorrect);
        }
        // If the program has reached this far, means that there more questions
        // Increments "questionCount" and "questionIndex" to keep track of the current question
        // Changes current question label
        questionCount += 1;
        questionIndex += 1;
    }
}
