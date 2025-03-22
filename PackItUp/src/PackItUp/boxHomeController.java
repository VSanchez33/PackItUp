package PackItUp;

import java.io.IOException;
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

    private ObservableList<Box> boxList = FXCollections.observableArrayList(); // List to store boxes
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableView<Box> tableView;
    @FXML
    private TableColumn<Item, String> boxNameColumn;
    @FXML
    private TableColumn<Item, String> reasonColumn;
    @FXML
    private TableColumn<Item, Integer> idColumn;


    @FXML
    private void initialize() {
        
        // Set up each column to display the correct property
        idColumn.setCellValueFactory(new PropertyValueFactory<>("boxID"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        boxNameColumn.setCellValueFactory(new PropertyValueFactory<>("boxName"));
    
        // Initially populate the table with data from boxList

        tableView.setItems(boxList);
    
        // Handle row click to select box
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double click to edit
                
                Box selectedItem = tableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    // Call edit method to open the Item creation screen for editing
                    editBox(selectedItem);
                } // end of if
            } // end of if
        }); // end of tableView
    } // end of initialize


    // Button that opens Home Screen
    public void goBack(ActionEvent event) throws IOException {
        
        root = FXMLLoader.load(getClass().getResource("splash.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    
    } // end of goMain


    // Button that opens the box creation screen
    public void createBox(ActionEvent event) throws IOException {
       
        System.out.println("Navigating to box creation screen...");
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemCreation.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("boxCreation.fxml"));
        root = loader.load();
    
        boxController controller = loader.getController();
        controller.setBoxList(boxList); // Pass box list to the creation controller
    
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    
    } // end of createItem


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
            alert.setContentText(selectedBox.getName());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Remove the selected box from the box list
                boxList.remove(selectedBox);
                // Refresh the table
                tableView.refresh();
            } // end of if
        } // end of if
        
        else {
            // Show a message if no box was selected
            System.out.println("No box selected for deletion.");
        } // end of else
    } // end of delete box
    

    // Open the editing view when an box is double-clicked
    private void editBox(Box selectedBox) {
        try {
            // Navigate to box creation screen for editing
            FXMLLoader loader = new FXMLLoader(getClass().getResource("boxCreation.fxml"));
            Parent root = loader.load();
            
            boxController controller = loader.getController();
            
            // Pass the selected box to the boxController for editing
            controller.setBox(selectedBox);
            controller.setBoxList(boxList); // Pass the box list to the controller
        
            Stage stage = (Stage) tableView.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } // end of try
        
        catch (IOException e) {
            e.printStackTrace();
        } // end of catch
    } // end of editItem

    public void setBoxList(ObservableList<Box> boxList) {
       
        this.boxList = boxList;
        tableView.setItems(boxList);  // Update the table with the new list
        tableView.refresh(); // Ensure the table view is refreshed to reflect changes
   
    } // end of setBoxList

    
    @FXML
    private void handleEditItem(ActionEvent event) throws IOException {
        Box selectedBox = tableView.getSelectionModel().getSelectedItem();
    
        if (selectedBox != null) {

            // Navigate to box creation screen for editing
            FXMLLoader loader = new FXMLLoader(getClass().getResource("box.fxml"));
            Parent root = loader.load();
    
            // Get the controller of ItemCreation.fxml
            boxController controller = loader.getController();
            
            // Set the current box to the selected box for editing
            controller.setBox(selectedBox);
            controller.setBoxList(boxList); // Pass the box list for saving new boxes
    
            // Show the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } // end of if
        
        else {
            // Handle case when no box is selected
            System.out.println("No box selected for editing.");
        } // end of else

    } // end of handleEditItem

} // end of homeController