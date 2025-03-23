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

public class itemHomeController {

    private ObservableList<Item> itemList = FXCollections.observableArrayList(); // List to store items
    private Stage stage;
    private Scene scene;
    private Parent root;
    private static int selectedBoxID;
    private DataManager dataManager = DataManager.getInstance();

    @FXML
    private TableView<Item> tableView;
    @FXML
    private TableColumn<Item, String> nameColumn;
    @FXML
    private TableColumn<Item, String> dateColumn;
    @FXML
    private TableColumn<Item, String> statusColumn;
    @FXML
    private TableColumn<Item, Integer> amountColumn;
    @FXML
    private TableColumn<Item, String> ownerColumn;


    @FXML
    private void initialize() throws IOException{
        
        // Set up each column to display the correct property
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("packStatus"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));
    
        // Handle row click to select item
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double click to edit
                
                Item selectedItem = tableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    // Call edit method to open the Item creation screen for editing
                    try {
                        editItem(selectedItem);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } // end of if
            } // end of if
        }); // end of tableView


        //debug code
        System.out.println("itemHomeController initialized with selectedBoxID: " + selectedBoxID);
    } // end of initialize


    // Button that opens Boxes Screen
    public void goBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("box.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end of goMain


    // Button that opens the item creation screen
    public void createItem(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemCreation.fxml"));
        root = loader.load();
        
        itemController controller = loader.getController();
        controller.setItemList(itemList); // Pass item list to the creation controller
        controller.setBoxID(selectedBoxID);
    
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end of createItem


    // Button that allows the user to delete the selected list item
    @FXML
    void deleteItem(ActionEvent event) throws IOException {
        // Get the selected item from the TableView
        Item selectedItem = tableView.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            // Show a confirmation dialog before deletion
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Are you sure you want to delete this item?");
            alert.setContentText(selectedItem.getName());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Remove the selected item from the item list
                itemList.remove(selectedItem);
                // Refresh the table
                tableView.refresh();
            } // end of if
        } // end of if
        else {
            // Show a message if no item was selected
            System.out.println("No item selected for deletion.");
        } // end of else
    } // end of delete item
    

    // Open the editing view when an item is double-clicked
    private void editItem(Item selectedItem) throws IOException {
        // Navigate to item creation screen for editing
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemCreation.fxml"));
        Parent root = loader.load();
        itemController controller = loader.getController();
        
        // Pass the selected item to the itemController for editing
        controller.setItem(selectedItem);
        controller.setItemList(itemList); // Pass the item list to the controller
    
        Stage stage = (Stage) tableView.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end of editItem
    
    
    public void setItemList(ObservableList<Item> itemList) {
        this.itemList = itemList;
        tableView.setItems(itemList);  // Update the table with the new list
        tableView.refresh(); // Ensure the table view is refreshed to reflect changes
    } // end of setItemList

    
    @FXML
    private void handleEditItem(ActionEvent event) throws IOException {
        Item selectedItem = tableView.getSelectionModel().getSelectedItem();
    
        if (selectedItem != null) {
            // Navigate to item creation screen for editing
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemCreation.fxml"));
            Parent root = loader.load();
    
            // Get the controller of ItemCreation.fxml
            itemController controller = loader.getController();
            
            // Set the current item to the selected item for editing
            controller.setItem(selectedItem);
            controller.setItemList(itemList); // Pass the item list for saving new items
    
            // Show the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } // end of if
        
        else {
            // Handle case when no item is selected
            System.out.println("No item selected for editing.");
        } // end of else
    } // end of handleEditItem


    public void setSelectedBoxID(int id){
        this.selectedBoxID = id;
    }
    
    
    public int getSelectedBoxID(){
        return selectedBoxID;
    }


    public void displayItems(int boxID) {
        //debug code
        System.out.println("Displaying items for boxID: " + selectedBoxID);
        tableView.setItems(dataManager.getItemList().filtered(item -> item.getBoxID() == selectedBoxID));
        tableView.refresh();
    }
} // end of displayItems