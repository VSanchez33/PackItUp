package PackItUp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        
        Parent root = FXMLLoader.load(getClass().getResource("splash.fxml"));
        primaryStage.setTitle("Pack It Up");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    } // end of start

    public static void main(String[] args) throws Exception {
        launch(args);
    } // end of main
} // end of Application
