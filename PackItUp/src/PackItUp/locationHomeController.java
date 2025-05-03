/*
* Authors:
*      Vincent Sanchez
*      Tabatha Valverde
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

public class locationHomeController implements homeController<Location> {

    // Variable declaration
    private ObservableList<Location> locationList = FXCollections.observableArrayList(); // List to store locations
    private Stage stage;
    private Scene scene;
    private Parent root;
    private static int selectedUserID;

    @FXML
    private TableView<Location> tableView;

    @FXML
    private TableColumn<Location, String> locationColumn;

    // Method to initialize the controller
    @FXML
    public void initialize() throws IOException {

        // Set up each column to display the correct property
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Initially populate the table with data from locationList
        display(selectedUserID);

        // Load data
        loadData();

        // Handle row click to select location
        tableView.setOnMouseClicked(event -> {

            if (event.getClickCount() == 2) { // Double click to edit

                Location selectedLocation = tableView.getSelectionModel().getSelectedItem();

                if (selectedLocation != null) {

                    // Call edit method to open the location creation screen for editing
                    openLocation(selectedLocation);
                    saveData();

                } // end of if

            } // end of if

        }); // end of tableView

        saveData();

        System.out.println("locationHomeController initialized with selectedUser: " + selectedUserID);

    } // end of initialize

    // Open the box when an box is double-clicked
    private void openLocation(Location selectedLocation) {

        try {

            // Navigate to box creation screen for editing
            FXMLLoader loader = new FXMLLoader(getClass().getResource("box.fxml"));
            Parent root = loader.load();

            // Get the controller of the box creation screen
            boxHomeController controller = loader.getController();

            int locationID = selectedLocation.getLocationID();
            System.out.println("Location Passed ID: " + locationID);

            // Pass the location's ID to the boxHomeController
            controller.setSelectedID(locationID);
            controller.display(locationID); // Filter boxes by locationID

            // Set up the stage and scene for the box creation screen
            Stage stage = (Stage) tableView.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {

            e.printStackTrace();

        } // end of try/catch

    } // end of openLocation

    // Button that opens Home Screen
    public void goBack(ActionEvent event) throws IOException {

        saveData();
        root = FXMLLoader.load(getClass().getResource("user.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
        controller.setID(selectedUserID);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    } // end of createLocation

    // Button that allows the user to delete the selected list location
    @FXML
    public void delete(ActionEvent event) throws IOException {

        Location selectedLocation = tableView.getSelectionModel().getSelectedItem(); // Get the selected location from
                                                                                     // the TableView

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
                tableView.setItems(locationList);
                tableView.refresh(); // Refresh the table
                saveData();

            } // end of if

        } // end of if

        else {

            System.out.println("No location selected for deletion."); // Show a message if no location was selected

        } // end of else

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

        saveData();

    } // end of deleteLocation

    // Edit location by selecting the desired location and clicking the Edit button
    public void edit(ActionEvent event) throws IOException {

        Location selectedLocation = tableView.getSelectionModel().getSelectedItem();

        if (selectedLocation != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("addLocation.fxml"));
            Parent root = loader.load();

            locationController controller = loader.getController();

            // Pass the selected location to the locationController for editing
            controller.setLocation(selectedLocation);
            controller.setList(locationList); // Pass the location list to the controller
            controller.setID(selectedUserID); // Get user ID to load correct location

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } // end of if

    } // end of editLocation

    public void setList(ObservableList<Location> locationList) {

        this.locationList = locationList;
        tableView.setItems(locationList); // Update the table with the new list
        tableView.refresh(); // Ensure the table view is refreshed to reflect changes

        saveData();

    } // end of setLocationList

    @FXML
    public void handleEdit(ActionEvent event) throws IOException {

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

            System.out.println("No location selected for editing."); // Handle case when no location is selected

        } // end of else

        saveData();

    } // end of handleEditLocation

    // Saves the locations to the csv file
    public void saveData() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("locations.csv"))) {

            writer.write("ID,Name,UserID");
            writer.newLine();

            for (Location location : locationList) {

                writer.write(location.getLocationID() + "," + location.getName() + "," + location.getUserID());
                writer.newLine();

            } // end of for

        } catch (IOException e) {

            e.printStackTrace();

        } // end of try/catch

    } // end of saveData

    // Loads the locations from the csv
    public void loadData() {

        Location.resetIDCounter();

        try (BufferedReader reader = new BufferedReader(new FileReader("locations.csv"))) {

            String line;
            ArrayList<Location> loadedList = new ArrayList<>();

            reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length == 3) {

                    int id = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    int userID = Integer.parseInt(data[2].trim());

                    Location newLocation = new Location(id, name, userID);
                    loadedList.add(newLocation);

                } // end of if

            } // end of while

            locationList.setAll(loadedList);
            tableView.setItems(locationList);
            tableView.refresh();

        } catch (IOException e) {

            e.printStackTrace();

        } // end of try/catch

    } // end of loadData

    public void setSelectedID(int userID) {

        selectedUserID = userID;
        loadData();
        display(userID);

    } // setSelectedUser

    public int getSelectedID() {
        return selectedUserID;
    }

    public void display(int userID) {

        ObservableList<Location> filteredList = locationList.filtered(location -> location.getUserID() == userID);
        tableView.setItems(filteredList);
        tableView.refresh();

    } // end of displayLocations

} // end of locationHomeController