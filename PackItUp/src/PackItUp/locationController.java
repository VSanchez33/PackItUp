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

public class locationController implements Controller<Location>{
    
    private ObservableList<Location> locationList; // This will store the locations from locationHomeController
    private Location currentLocation; // Store the location currently being edited
    private DataManager dataManager = DataManager.getInstance();
    private static String user;
    
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
        // Navigate back to home
        FXMLLoader loader = new FXMLLoader(getClass().getResource("location.fxml"));
        Parent root = loader.load();
    
        // Pass the updated location list to locationHomeController
        locationHomeController controller = loader.getController();
        controller.setList(locationList); 
        controller.setSelectedUser(locationHomeController.selectedUser);
    
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end of cancel    


    // Saves the location created or edited
    @FXML
    public void save(ActionEvent event) throws IOException {
        if (currentLocation != null) {
            // If we're updating an existing location, apply changes
            currentLocation.setLocationName(locationNameField.getText());
        } 
        else {
            // If currentBox is null, create a new location
            Location newLocation = new Location();
            newLocation.setLocationName(locationNameField.getText());
            newLocation.setUser(user);
            // Add the new location to the list
            locationList.add(newLocation);
            dataManager.getLocationList().add(newLocation);
        } // end of else
    
        // Navigate back to home
        FXMLLoader loader = new FXMLLoader(getClass().getResource("location.fxml"));
        Parent root = loader.load();
    
        // Pass the updated location list to locationHomeController
        locationHomeController controller = loader.getController();
        controller.setList(locationList); 
        controller.setSelectedUser(locationHomeController.selectedUser);
    
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end of saveLocation
 

    // Setter to receive the list of locations from homeController
    public void setList(ObservableList<Location> locationList) {
        this.locationList = locationList;
    } // end of setLocationList

    public void setUser(String user){
        locationController.user = user;
    }
} // end of locationController 
