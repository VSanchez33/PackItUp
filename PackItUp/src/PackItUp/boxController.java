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

public class boxController implements Controller<Box>{
    private Stage stage;
    private Scene scene;
    private Parent root;

    private ObservableList<Box> boxList; // This will store the boxes from boxHomeController
    private Box currentBox; // Store the box currently being edited
    private DataManager dataManager = DataManager.getInstance();
    private static String location;
    
    @FXML
    private TextField idField;

    @FXML
    private TextField ownerField;

    @FXML
    private TextField nameField;
    

    // Author: Tabatha Valverde
    // Method to initialize the controller
    @FXML 
    public void initialize() {
        if (currentBox != null) {
            // Load the box data into the fields if there's an box to edit
            idField.setText("" + currentBox.getBoxID());
            ownerField.setText(currentBox.getBoxOwner()); 
            nameField.setText(currentBox.getBoxName()); 
        } // end of if
    } // end of initialize


    // Author: Tabatha Valverde
    // Set the box to the list
    public void setBox(Box box) {
        this.currentBox = box;
        // Populate fields with the selected box data for editing
        idField.setText("" + currentBox.getBoxID());
        ownerField.setText(currentBox.getBoxOwner());
        nameField.setText(currentBox.getBoxName());
    } // end of setBox


    // Author: Tabatha Valverde
    // Cancels item creation or edit 
    @FXML
    public void cancel (ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("box.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end of cancel    


    // Author: Tabatha Valverde and Vincent Sanchez
    // Saves the box created or edited
    @FXML
    public void save(ActionEvent event) throws IOException {
        if (currentBox != null) {
            // If we're updating an existing box, apply changes
            currentBox.setBoxID(Integer.parseInt(idField.getText()));
            currentBox.setBoxOwner(ownerField.getText());
            currentBox.setBoxName(nameField.getText());
        } 
        else {
            // If currentBox is null, create a new box
            Box newBox = new Box();
            newBox.setBoxID(Integer.parseInt(idField.getText()));
            newBox.setBoxOwner(ownerField.getText());
            newBox.setBoxName(nameField.getText());
             // Sets the location for the location the item is stored in 
            newBox.setLocation(location);
            // Add the new box to the list
            boxList.add(newBox);
            dataManager.getBoxList().add(newBox);
        } // end of else
    
        // Navigate back to home
        FXMLLoader loader = new FXMLLoader(getClass().getResource("box.fxml"));
        Parent root = loader.load();
    
        // Pass the updated box list to boxHomeController
        boxHomeController controller = loader.getController();
        controller.setList(boxList); 
        controller.setSelectedLocation(location);
    
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end of saveBox
 

    // Author: Tabatha Valverde
    // Setter to receive the list of boxess from homeController
    public void setList(ObservableList<Box> boxList) {
        this.boxList = boxList;
    } // end of setBoxList

    public void setLocation(String location){
        this.location = location;
    }
} // end of boxController 
