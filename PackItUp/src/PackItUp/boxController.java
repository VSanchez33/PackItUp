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

public class boxController implements Controller<Box> {

    // Variable declaration
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
    private TextField nameField;

    // Initialize the form with data for editing
    @FXML
    public void initialize() {

        if (currentBox != null) {

            idField.setText(currentBox.getBoxInput());
            nameField.setText(currentBox.getBoxName());

        } // end of if

    } // end of initialize

    // Set the box being edited or created
    public void setBox(Box box) {

        this.currentBox = box;
        idField.setText(currentBox.getBoxInput());
        nameField.setText(currentBox.getBoxName());

    } // end of setBox

    @FXML
    public void cancel(ActionEvent event) throws IOException {

        // Navigate back to home
        FXMLLoader loader = new FXMLLoader(getClass().getResource("box.fxml"));
        Parent root = loader.load();

        // Pass the updated location list to locationHomeController
        boxHomeController controller = loader.getController();
        controller.setBoxList(boxList);
        controller.setSelectedLocation(locationID);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    } // end of cancel

    // Saves the box created or edited
    @FXML
    public void save(ActionEvent event) throws IOException {

        if (currentBox != null) { // If we're updating an existing location, apply changes

            idField.setText(currentBox.getBoxInput());
            currentBox.setBoxName(nameField.getText());

        } // end of if

        else {

            // If currentBox is null, create a new location
            Box newBox = new Box();
            newBox.setBoxName(nameField.getText());
            newBox.setBoxInput(idField.getText());
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
        controller.setSelectedLocation(locationID);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    } // end of saveLocation

    public void setList(ObservableList<Box> boxList) {

        this.boxList = boxList;

    } // end of setBoxList

    // Set the location ID for this box
    public void setID(int locationID) {

        this.locationID = locationID;

    } // end of setLocationID

} // end of boxController
