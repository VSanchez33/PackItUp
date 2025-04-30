/*
 * Authors:
 *      Tabatha Valverde
 *      Vincent Sanchez
 */

package PackItUp;

import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class boxController {

    private Box currentBox;
    private ObservableList<Box> boxList;
    private static int locationID;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private DataManager dataManager = DataManager.getInstance();

    @FXML
    private TextField idField;
    @FXML
    private TextField ownerField;
    @FXML
    private TextField nameField;

    // Initialize the form with data for editing
    @FXML
    public void initialize() {
        if (currentBox != null) {
            idField.setText(String.valueOf(currentBox.getBoxID()));
            ownerField.setText(currentBox.getBoxOwner());
            nameField.setText(currentBox.getBoxName());
        }
    }

    // Set the box being edited or created
    public void setBox(Box box) {
        this.currentBox = box;
        idField.setText(String.valueOf(currentBox.getBoxID()));
        //ownerField.setText(currentBox.getBoxOwner());
        nameField.setText(currentBox.getBoxName());
    }

    // Set the location ID for this box
    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    // @FXML
    // public void saveBox() {
    //     if (currentBox != null) {
    //         currentBox.setBoxOwner(ownerField.getText());
    //         currentBox.setBoxName(nameField.getText());
    //         currentBox.setLocationID(locationID);
    //     }
    // }

    @FXML
    public void cancel (ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("box.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end of cancel 

    public void setBoxList(ObservableList<Box> boxList) {
        this.boxList = boxList;
    } // end of setLocationList

    // Author: Tabatha Valverde and Vincent Sanchez
    // Saves the box created or edited
    @FXML
    void saveBox(ActionEvent event) throws IOException {

        if (currentBox != null) {
            // If we're updating an existing location, apply changes
            currentBox.setBoxName(nameField.getText());
        } 
        else {
            // If currentBox is null, create a new location
            Box newBox = new Box();
            newBox.setBoxName(nameField.getText());
            newBox.setLocationID(locationID);
            // Add the new location to the list
            boxList.add(newBox);
            dataManager.getBoxList().add(newBox);
        } // end of else
    
        // Navigate back to home
        FXMLLoader loader = new FXMLLoader(getClass().getResource("box.fxml"));
        Parent root = loader.load();
    
        // Pass the updated location list to locationHomeController
        boxHomeController controller = loader.getController();
        controller.setBoxList(boxList); 
        controller.setSelectedLocationID(locationID);
    
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end of saveLocation



}
