package PackItUp;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class homeController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void goMain(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("splash.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void createItem(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("itemCreation.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}