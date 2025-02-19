import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PackItUp extends Application {
    @Override
    public void start(Stage stage) {

        Label l = new Label("Hello, welcome to Pack It Up!\n\n       Meet the team: \n\n       Aaron Escalera\n       Bryson Young\n       Vincent Sanchez\n       Austin Matthys\n       Tabatha Valverde");
        Scene scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}