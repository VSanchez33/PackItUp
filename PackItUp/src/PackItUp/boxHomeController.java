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

public class boxHomeController implements HomeController<Box>{ 

    private ObservableList<Box> boxList = FXCollections.observableArrayList(); // List to store boxes
    private Stage stage;
    private Scene scene;
    private Parent root;
    public static String selectedLocation;

    @FXML
    private TableView<Box> tableView;
    @FXML
    private TableColumn<Box, String> boxNameColumn;
    @FXML
    private TableColumn<Box, Integer> idColumn;


    // Author: Tabatha Valverde and Vincent Sanchez
    @FXML
    public void initialize() {
        loadData();

        // Set up each column to display the correct property
        idColumn.setCellValueFactory(new PropertyValueFactory<>("boxID"));
        boxNameColumn.setCellValueFactory(new PropertyValueFactory<>("boxName"));
    
        // Initially populate the table with data from boxList
        displayBoxes(selectedLocation);

    
        // Load data
        loadData();
        
        // Handle row click to select box
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double click to edit
                
                Box selectedBox = tableView.getSelectionModel().getSelectedItem();
                if (selectedBox != null) {
                    // Call edit method to open the Item creation screen for editing
                    edit(selectedBox);
                    saveData();
                } // end of if
            } // end of if
        }); // end of tableView

        saveData();
        //debug code
        System.out.println("boxHomeController initialized with selectedLocation: " + selectedLocation);
    } // end of initialize
 
    // Author: Tabatha Valverde and Vincent Sanchez
    public void openBox(ActionEvent event) throws IOException {
        Box selectedBox = tableView.getSelectionModel().getSelectedItem();

        if (selectedBox != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("items.fxml"));
            Parent root = loader.load();

            itemHomeController controller = loader.getController();
            int boxID = selectedBox.getBoxID(); 
            controller.setSelectedBoxID(boxID);
            controller.displayItems(boxID);
        
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } // end of if
    } // end of opeBox


    // Author: Tabatha Valverde and Vincent Sanchez
    // Button that opens Home Screen
    public void goBack(ActionEvent event) throws IOException {
        saveData();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("location.fxml"));
        Parent root = loader.load();

        locationHomeController controller = loader.getController();
        String user = locationHomeController.selectedUser;
        System.out.println("User Passed: " + user);
        controller.setSelectedUser(user);
        controller.displayLocations(user);
    
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end of goMain


    // Author: Tabatha Valverde 
    // Button that opens the box creation screen
    public void create(ActionEvent event) throws IOException {
        saveData();

        System.out.println("Navigating to box creation screen...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("boxCreation.fxml"));
        root = loader.load();
    
        boxController controller = loader.getController();
        controller.setList(boxList);
        controller.setLocation(selectedLocation);
    
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end of createBox


    // Author: Tabatha Valverde
    // Button that allows the user to delete the selected list box
    @FXML
    public void delete(ActionEvent event) throws IOException {
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
                displayBoxes(selectedLocation);
                // Refresh the table
                tableView.refresh();
                saveData();
            } // end of if
        } // end of if
        else {
            // Show a message if no box was selected
            System.out.println("No box selected for deletion.");
        } // end of else
        saveData();
    } // end of deleteBox
    

    // Author: Tabatha Valverde
    // Open the editing view when an box is double-clicked
    public void edit(Box selected) {
        try {
            // Navigate to box creation screen for editing
            FXMLLoader loader = new FXMLLoader(getClass().getResource("boxCreation.fxml"));
            Parent root = loader.load();

            boxController controller = loader.getController();
            
            // Pass the selected box to the boxController for editing
            controller.setBox(selected);
            controller.setList(boxList); // Pass the box list to the controller
        
            Stage stage = (Stage) tableView.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } // end of try
        
        catch (IOException e) {
            e.printStackTrace();
        } // end of catch
    } // end of editBox


    // Author: Tabatha Valverde
    public void setList(ObservableList<Box> boxList) {
        this.boxList = boxList;
        tableView.setItems(boxList);  // Update the table with the new list
        tableView.refresh(); // Ensure the table view is refreshed to reflect changes
    
        saveData();
    } // end of setBoxList

    
    // Author: Tabatha Valverde and Vincent Sanchez
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
            // Handle case when no box is selected
            System.out.println("No box selected for editing.");
        } // end of else
        saveData();
    } // end of EditBox


    // Author: Tabatha Valverde
    // Saves the boxes to the csv file
    public void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("boxes.csv"))) {
            // Write the header (optional)
            writer.write("BoxID,BoxName,Location");
            writer.newLine();
            
            // Write each box in the list to the CSV file
            for (Box box : boxList) {
                StringBuilder sb = new StringBuilder();
                sb.append(box.getBoxID()).append(",");
                sb.append(box.getBoxName()).append(",");
                sb.append(box.getLocation());
                writer.write(sb.toString());
                writer.newLine(); // Ensure each box is written on a new line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Loads the boxes from the csv
    public void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("boxes.csv"))) {
            String line;
            ArrayList<Box> loadedList = new ArrayList<>();
            
            // Skip header line
            reader.readLine();
            
            // Read each line and create a Box object
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                
                // Assuming the CSV has columns in the order: BoxID, BoxName
                int boxID = Integer.parseInt(data[0]);
                String boxName = data[1];
                String location = data[2];
    
                // Create a new Box object and add it to the list
                Box box = new Box();
                box.setBoxID(boxID);
                box.setBoxName(boxName);
                box.setLocation(location);

                loadedList.add(box);
            }
            
            // Update the boxList and refresh the table
            boxList.setAll(loadedList);
            tableView.setItems(boxList);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Author: Vincent Sanchez
    public void setSelectedLocation(String location) {
        System.out.println("selectedLocation from setSelectedLocation: " + location);
        boxHomeController.selectedLocation = location;
        loadData();
        displayBoxes(selectedLocation);
    }

    // Author: Vincent Sanchez
    public String getSelectedLocation(){
        return selectedLocation;
    }

    // Author: Vincent Sanchez
    public void displayBoxes(String location) {
        ObservableList<Box> filteredList = boxList.filtered(box -> box.getLocation().equals(location));
        tableView.setItems(filteredList);
        tableView.refresh();
        saveData();
    }
 
} // end of boxHomeController