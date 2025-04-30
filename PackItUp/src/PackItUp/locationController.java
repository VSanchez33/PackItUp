/*
* Authors:
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

public class locationController {
    
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
            // Load the location data into the fields if there's an location to edit
            locationNameField.setText(currentLocation.getName());
        } // end of if
    } // end of initialize


    // Set the location to the list
    public void setLocation(Location location) {
        this.currentLocation = location;
        // Populate fields with the selected location data for editing
        locationNameField.setText(currentLocation.getName());
    } // end of setLocation


    // Cancels location creation or edit 
    @FXML
    public void cancel (ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("location.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end of cancel    


    // Saves the location created or edited
    @FXML
    void saveLocation(ActionEvent event) throws IOException {

        if (currentLocation != null) {
            // If we're updating an existing location, apply changes
            currentLocation.setLocationName(locationNameField.getText());
        } 
        else {
            // If currentBox is null, create a new location
            Location newLocation = new Location();
            newLocation.setLocationName(locationNameField.getText());
            newLocation.setUserID(userID);
            // Add the new location to the list
            locationList.add(newLocation);
            dataManager.getLocationList().add(newLocation);
        } // end of else
    
        // Navigate back to home
        FXMLLoader loader = new FXMLLoader(getClass().getResource("location.fxml"));
        Parent root = loader.load();
    
        // Pass the updated location list to locationHomeController
        locationHomeController controller = loader.getController();
        controller.setLocationList(locationList); 
        controller.setSelectedUser(userID);
    
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end of saveLocation
 

    // Setter to receive the list of locations from homeController
    public void setLocationList(ObservableList<Location> locationList) {
        this.locationList = locationList;
    } // end of setLocationList

    public void setUserID(int userID) {
        this.userID = userID;
    }

} // end of locationController 
