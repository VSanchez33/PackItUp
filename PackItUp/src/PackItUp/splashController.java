/*
 * Authors: 
 *      Vincent Sanchez
 */

package PackItUp;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class splashController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void goHome(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("user.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
   
    } // end of goHome
} // end of splashController


