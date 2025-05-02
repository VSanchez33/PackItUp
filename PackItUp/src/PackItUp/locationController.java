/*
* Authors:
*      Vincent Sanchez
*      Tabatha Valverde
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

public class locationController implements Controller<Location> {

    // Variable declaration
    private Stage stage;
    private Scene scene;
    private Parent root;
    private ObservableList<Location> locationList; // This will store the locations from locationHomeController
    private Location currentLocation; // Store the location currently being edited
    private DataManager dataManager = DataManager.getInstance();
    private static int userID;

    @FXML
    private TextField locationNameField;

    // Method to initialize the controller
    @FXML
    public void initialize() {

        if (currentLocation != null) {

            locationNameField.setText(currentLocation.getName()); // Load the location data into the fields if there's
                                                                  // an location to edit

        } // end of if

    } // end of initialize

    // Set the location to the list
    public void setLocation(Location location) {

        this.currentLocation = location;
        locationNameField.setText(currentLocation.getName()); // Populate fields with the selected location data for
                                                              // editing

    } // end of setLocation

    // Cancels location creation or edit
    @FXML
    public void cancel(ActionEvent event) throws IOException {

        // Load the previous screen (e.g., locationHomeController)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("location.fxml"));
        root = loader.load();

        // Pass the updated box list to locationHomeController
        locationHomeController locationController = loader.getController();
        locationController.display(locationController.getSelectedID()); // Pass the list of Box objects

        // Pass the current user (if needed)
        locationController.setSelectedID(locationController.getSelectedID());

        // Navigate to the locationHome screen
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    } // end of cancel

    // Saves the location created or edited
    @FXML
    public void save(ActionEvent event) throws IOException {

        if (currentLocation != null) {

            currentLocation.setLocationName(locationNameField.getText()); // If we're updating an existing location,
                                                                          // apply changes

        } // end of if

        else {

            // If currentBox is null, create a new location
            Location newLocation = new Location();
            newLocation.setLocationName(locationNameField.getText());
            newLocation.setUserID(userID);

            // Add the new location to the list
            locationList.add(newLocation);
            dataManager.getLocationList().add(newLocation);

        } // end of else

        // Navigate back to Location screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("location.fxml"));
        Parent root = loader.load();

        // Pass the updated location list to locationHomeController
        locationHomeController controller = loader.getController();
        controller.setList(locationList);
        controller.setSelectedID(userID);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    } // end of saveLocation

    // Setter to receive the list of locations from homeController
    public void setList(ObservableList<Location> locationList) {

        this.locationList = locationList;

    } // end of setLocationList

    public void setID(int userID) {

        this.userID = userID;

    } // end of setUserID

} // end of locationController
