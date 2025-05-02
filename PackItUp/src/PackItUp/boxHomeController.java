/*
 * Authors: 
 *      Tabatha Valverde
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

public class boxHomeController {

    // Variable declaration
    private ObservableList<Box> boxList = FXCollections.observableArrayList(); // List to store boxes
    private Stage stage;
    private Scene scene;
    private Parent root;
    private static int selectedLocationID;

    @FXML
    private TableView<Box> tableView;

    @FXML
    private TableColumn<Box, String> boxNameColumn;

    @FXML
    private TableColumn<Box, Integer> idColumn;

    @FXML
    private void initialize() {

        // Set up each column to display the correct property
        idColumn.setCellValueFactory(new PropertyValueFactory<>("boxID"));
        boxNameColumn.setCellValueFactory(new PropertyValueFactory<>("boxName"));

        // Initially populate the table with data from boxList
        displayBoxes(selectedLocationID);

        // Load data
        loadData();

        // Handle row click to select box
        tableView.setOnMouseClicked(event -> {

            if (event.getClickCount() == 2) { // Double click to edit

                Box selectedBox = tableView.getSelectionModel().getSelectedItem();

                if (selectedBox != null) {

                    // Call edit method to open the Item creation screen for editing
                    openBox(selectedBox);
                    saveData();

                } // end of if

            } // end of if

        }); // end of tableView

        saveData();

        // debug code
        System.out.println("boxHomeController initialized with selectedLocation: " + selectedLocationID);

    } // end of initialize

    // Open the editing view when an box is double-clicked
    private void openBox(Box selectedBox) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("items.fxml"));
            root = loader.load();

            itemHomeController controller = loader.getController();

            int boxID = selectedBox.getBoxID();
            System.out.println("Box Passed ID: " + boxID);

            controller.setSelectedBoxID(boxID);
            controller.displayItems(boxID);

            Stage stage = (Stage) tableView.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {

            e.printStackTrace();

        } // end of try/catch

    } // end of openBox

    // Button that opens Home Screen
    public void goBack(ActionEvent event) throws IOException {

        saveData();
        root = FXMLLoader.load(getClass().getResource("location.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    } // end of goBack

    // Button that opens the box creation screen
    public void createBox(ActionEvent event) throws IOException {

        saveData();

        System.out.println("Navigating to box creation screen...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("boxCreation.fxml"));
        root = loader.load();

        boxController controller = loader.getController();
        controller.setList(boxList);
        controller.setID(selectedLocationID);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    } // end of createBox

    // Button that allows the user to delete the selected list box
    @FXML
    void deleteBox(ActionEvent event) throws IOException {

        // Get the selected box from the TableView
        Box selectedBox = tableView.getSelectionModel().getSelectedItem();

        if (selectedBox != null) {

            // Show a confirmation dialog before deletion
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Are you sure you want to delete this box?");
            alert.setContentText(selectedBox.getBoxName());

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                // Remove the selected box from the box list
                boxList.remove(selectedBox);
                tableView.setItems(boxList);

                // Refresh the table
                tableView.refresh();
                saveData();

            } // end of if

        } // end of if

        else {

            System.out.println("No box selected for deletion."); // Show a message if no box was selected

        } // end of else

        saveData();

    } // end of deleteBox

    public void editBox(ActionEvent event) throws IOException {

        Box selectedBox = tableView.getSelectionModel().getSelectedItem();

        if (selectedBox != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("boxCreation.fxml"));
            Parent root = loader.load();

            boxController controller = loader.getController();

            // Pass the selected box to the boxController for editing
            controller.setBox(selectedBox);
            controller.setList(boxList); // Pass the box list to the controller
            controller.setID(selectedLocationID);

            Stage stage = (Stage) tableView.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } // end of if

    } // end of editBox

    public void setBoxList(ObservableList<Box> boxList) {

        this.boxList = boxList;
        tableView.setItems(boxList); // Update the table with the new list
        tableView.refresh(); // Ensure the table view is refreshed to reflect changes

        saveData();

    } // end of setBoxList

    @FXML
    private void handleEditBox(ActionEvent event) throws IOException {

        Box selectedBox = tableView.getSelectionModel().getSelectedItem();

        if (selectedBox != null) {

            // Navigate to box creation screen for editing
            FXMLLoader loader = new FXMLLoader(getClass().getResource("boxCreation.fxml"));
            Parent root = loader.load();

            // Get the controller of boxCreation.fxml
            boxController controller = loader.getController();

            // Set the current box to the selected box for editing
            controller.setBox(selectedBox);
            controller.setList(boxList);

            // Show the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            saveData();

        } // end of if

        else {

            System.out.println("No box selected for editing."); // Handle case when no box is selected

        } // end of else

        saveData();

    } // end of handleEditBox

    // Saves the boxes to the csv file
    public void saveData() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("boxes.csv"))) {

            // Write the header
            writer.write("BoxID,BoxName,BoxInput,LocationID");
            writer.newLine();

            // Write each box in the list to the CSV file
            for (Box box : boxList) {

                StringBuilder sb = new StringBuilder();
                sb.append(box.getBoxID()).append(",");
                sb.append(box.getBoxName()).append(",");
                sb.append(box.getBoxInput()).append(",");
                sb.append(box.getLocationID());
                writer.write(sb.toString());
                writer.newLine();

            } // end of for loop

        } catch (IOException e) {

            e.printStackTrace();

        } // end of try/catch

    } // end of saveData

    // Loads the boxes from the csv
    public void loadData() {

        Box.resetIDCounter();

        try (BufferedReader reader = new BufferedReader(new FileReader("boxes.csv"))) {

            String line;
            ArrayList<Box> loadedList = new ArrayList<>();

            // Skip header line
            reader.readLine();

            // Read each line and create a Box object
            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length == 4) {

                    // Assuming the CSV has columns in the order: BoxID, BoxName, BoxDate, BoxOwner
                    int boxID = Integer.parseInt(data[0]);
                    String boxName = data[1];
                    String boxInput = data[2];
                    int locationID = Integer.parseInt(data[3]);

                    // Create a new Box object and add it to the list
                    Box newBox = new Box(boxID, boxName, boxInput, locationID);
                    loadedList.add(newBox);

                } // end of if

            } // end of while loop

            // Update the boxList and refresh the table
            boxList.setAll(loadedList);
            tableView.setItems(boxList);
            tableView.refresh();

        } catch (IOException e) {

            e.printStackTrace();

        } // end of try/catch

    } // end of loadData

    public void setSelectedLocation(int locationID) {

        selectedLocationID = locationID;
        loadData();
        displayBoxes(locationID);

    } // end of setSelectedLocationID

    public int getSelectedLocationID() {
        return selectedLocationID;
    } // end of getSelectedLocationID

    public void displayBoxes(int locationID) {

        ObservableList<Box> filteredList = boxList.filtered(box -> box.getLocationID() == locationID);
        tableView.setItems(filteredList);
        tableView.refresh();

    } // end of displayBoxes

} // end of boxHomeController