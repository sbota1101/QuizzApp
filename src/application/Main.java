
package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

// This is the Main file where the scene is initialized
// UI settings about the scene in general is done in here
// Not much else happens in here

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // This loads the FXML files for the GUI
        Parent root = FXMLLoader.load(getClass().getResource("quizWindow.fxml"));
        // Adds the icon
        // primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        // Loads the CSS for the FXML
        //  root.getStylesheets().add("application/lightTheme.css");
        primaryStage.setResizable(false);  // Did this to preserve "nice-lookingness" of the GUI
        primaryStage.setTitle("Grupa 2 Quiz Game!");
        primaryStage.setOnCloseRequest((event -> {  // Terminates the scheduled TimerTask to display next question
            // Platform.exit();
            System.exit(0);
        }));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args); // Launches a standalone application
    }
}
