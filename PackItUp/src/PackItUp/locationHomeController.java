/*
* Authors:
*      Vincent Sanchez
*/

package PackItUp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class locationHomeController implements HomeController<Location>{ 

    private ObservableList<Location> locationList = FXCollections.observableArrayList(); // List to store locations
    private Stage stage;
    private Scene scene;
    private Parent root;
    public static String selectedUser;

    @FXML
    private TableView<Location> tableView;
    @FXML
    private TableColumn<Location, String> locationColumn;


    @FXML
    public void initialize() {
        loadData();

        // Set up each column to display the correct property
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    
        // Initially populate the table with data from locationList
        displayLocations(selectedUser);
    
        // Load data
        loadData();
        
        // Handle row click to select location
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double click to edit
                
                Location selectedLocation = tableView.getSelectionModel().getSelectedItem();
                if (selectedLocation != null) {
                    // Call edit method to open the location creation screen for editing
                    edit(selectedLocation);
                    saveData();
                } // end of if
            } // end of if
        }); // end of tableView

        saveData();
        // debug code
        System.out.println("locationHomeController initialized with selectedUser: " + selectedUser);
    } // end of initialize

    public void openLocation(ActionEvent event) throws IOException {
        Location selectedLocation = tableView.getSelectionModel().getSelectedItem();

        if (selectedLocation != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("box.fxml"));
            Parent root = loader.load();

            boxHomeController controller = loader.getController();
            String location = selectedLocation.getName(); 
            controller.setSelectedLocation(location);
            controller.displayBoxes(location);
        
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } // end of if
    } // end of openLocation


    // Button that opens Home Screen
    public void goBack(ActionEvent event) throws IOException {
        saveData();
        root = FXMLLoader.load(getClass().getResource("user.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end of goBack


    // Button that opens the location creation screen
    public void create(ActionEvent event) throws IOException {
        saveData();

        System.out.println("Navigating to location creation screen...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addLocation.fxml"));
        root = loader.load();
    
        locationController controller = loader.getController();
        controller.setList(locationList);
        controller.setUser(selectedUser);
    
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end of createLocation


    // Button that allows the user to delete the selected list location
    @FXML
    public void delete(ActionEvent event) throws IOException {
        // Get the selected location from the TableView
        Location selectedLocation = tableView.getSelectionModel().getSelectedItem();

        if (selectedLocation != null) {
            // Show a confirmation dialog before deletion
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Are you sure you want to delete this location?");
            alert.setContentText(selectedLocation.getName());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Remove the selected location from the location list
                locationList.remove(selectedLocation);
                displayLocations(selectedUser);
                // Refresh the table
                tableView.refresh();
                saveData();
            } // end of if
        } // end of if
        else {
            // Show a message if no location was selected
            System.out.println("No location selected for deletion.");
        } // end of else
        saveData();
    } // end of deleteLocation
    

    // Open the editing view when an location is double-clicked
    public void edit(Location selected) {
        try {
            // Navigate to location creation screen for editing
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addLocation.fxml"));
            Parent root = loader.load();

            locationController controller = loader.getController();
            
            // Pass the selected location to the locationController for editing
            controller.setLocation(selected);
            controller.setList(locationList); // Pass the location list to the controller
        
            Stage stage = (Stage) tableView.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } // end of try
        
        catch (IOException e) {
            e.printStackTrace();
        } // end of catch
    } // end of editLocation


    public void setList(ObservableList<Location> locationList) {
        this.locationList = locationList;
        tableView.setItems(locationList);  // Update the table with the new list
        tableView.refresh(); // Ensure the table view is refreshed to reflect changes
    
        saveData();
    } // end of setLocationList

    
    @FXML
    private void handleEditLocation(ActionEvent event) throws IOException {
        Location selectedLocation = tableView.getSelectionModel().getSelectedItem();
    
        if (selectedLocation != null) {
            // Navigate to location creation screen for editing
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addLocation.fxml"));
            Parent root = loader.load();
    
            // Get the controller of locationCreation.fxml
            locationController controller = loader.getController();
            
            // Set the current location to the selected location for editing
            controller.setLocation(selectedLocation);
            controller.setList(locationList); 
    
            // Show the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } // end of if
        else {
            // Handle case when no location is selected
            System.out.println("No location selected for editing.");
        } // end of else
        saveData();
    } // end of handleEditLocation


    // Saves the locations to the csv file
    public void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("locations.csv"))) {
            // Write the header (optional)
            writer.write("Location,User");
            writer.newLine();
            
            // Write each location in the list to the CSV file
            for (Location location : locationList) {
                StringBuilder sb = new StringBuilder();
                sb.append(location.getName()).append(",");
                sb.append(location.getSelectedUser());
                writer.write(sb.toString());
                writer.newLine(); // Ensure each location is written on a new line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }  // end of saveData

    // Loads the locations from the csv
    public void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("locations.csv"))) {
            String line;
            ArrayList<Location> loadedList = new ArrayList<>();
            
            // Skip header line
            reader.readLine();
            
            // Read each line and create a Box object
            while ((line = reader.readLine()) != null) {
                String[] data =line.split(",");

                String location = data[0];
                String user = data[1];
                // Create a new Location object and add it to the list
                Location newLocation = new Location();
                newLocation.setLocationName(location);
                newLocation.setUser(user);

                loadedList.add(newLocation);
            }
            
            // Update the locationList and refresh the table
            locationList.setAll(loadedList);
            tableView.setItems(locationList);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSelectedUser(String user) {
        this.selectedUser = user;  // Or store the whole user object if needed
        loadData();
        displayLocations(selectedUser);
    }

    public String getSelectedUser(){
        return selectedUser;
    }

    public void displayLocations(String user){
        ObservableList<Location> filteredList = locationList.filtered(location -> location.getSelectedUser().equals(user));
        tableView.setItems(filteredList);
        tableView.refresh();
        saveData();
    }

} // end of locationHomeController