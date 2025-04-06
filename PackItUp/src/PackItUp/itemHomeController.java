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

public class itemHomeController {

    private ObservableList<Item> itemList = FXCollections.observableArrayList(); // List to store items
    private Stage stage;
    private Scene scene;
    private Parent root;
    private static int selectedBoxID;

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


    // Author: Tabatha Valverde
    @FXML
    private void initialize() throws IOException{
        //Load data
        loadData();

        // Set up each column to display the correct property
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("packStatus"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));

        displayItems(selectedBoxID);

        // Load data
        loadData();

        // Handle row click to select item
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double click to edit
                
                Item selectedItem = tableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    // Call edit method to open the Item creation screen for editing
                    try {
                        editItem(selectedItem);
                        saveData();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } // end of if
            } // end of if
        }); // end of tableView

        saveData();
        //debug code
        System.out.println("itemHomeController initialized with selectedBoxID: " + selectedBoxID);
    } // end of initialize


    // Author: Tabatha Valverde
    // Button that opens Boxes Screen
    public void goBack(ActionEvent event) throws IOException {
        saveData();
        root = FXMLLoader.load(getClass().getResource("box.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end of goMain


    // Author: Tabatha Valverde
    // Button that opens the item creation screen
    public void createItem(ActionEvent event) throws IOException {
        saveData();

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


    // Author: Tabatha Valverde
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
                tableView.setItems(itemList); // Re-bind the updated list to the TableView
                tableView.refresh(); // Ensure the table view is refreshed
                // Save the updated list to the CSV file
                saveData();
            }
        } else {
            // Show a message if no item was selected
            System.out.println("No item selected for deletion.");
        }
        saveData();
    }
    

    // Author: Tabatha Valverde
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
    
    
    // Author: Tabatha Valverde
    public void setItemList(ObservableList<Item> itemList) {
        this.itemList = itemList;
        tableView.setItems(itemList);  // Update the table with the new list
        tableView.refresh(); // Ensure the table view is refreshed to reflect changes
    
        saveData();
    } // end of setItemList

    

    // Author: Tabatha Valverde and Vincent Sanchez
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

            saveData();
        } // end of ifs
        else {
            // Handle case when no item is selected
            System.out.println("No item selected for editing.");
        } // end of else
        saveData();
    } // end of handleEditItem


    // Author: Vincent Sanchez
    // Set the boxId to get the correct items
    public void setSelectedBoxID(int id){
        this.selectedBoxID = id;
        loadData(); // Reload data when the selected box changes
        displayItems(selectedBoxID);
    }
    
    
    // Author: Vincent Sanchez
    // get the box id to display the correct items
    public int getSelectedBoxID(){
        return selectedBoxID;
    }


    // Author: Vincent Sanchez
    // Displays the items from correct box
    public void displayItems(int boxID) {
        ObservableList<Item> filteredList = itemList.filtered(item -> item.getBoxID() == boxID);
        saveData();
        tableView.setItems(filteredList);
        tableView.refresh();
    }


    // Author: Tabatha Valverde
    public void saveData() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("items.csv"))) {
        // Write the header (optional)
        writer.write("ItemName,Date,PackStatus,Amount,Owner,BoxID");
        writer.newLine();
        
        // Write each item in the list to the CSV file
        for (Item item : itemList) {
            StringBuilder sb = new StringBuilder();
                sb.append(item.getName()).append(",");
                sb.append(item.getDate()).append(",");
                sb.append(item.getStatus()).append(",");
                sb.append(item.getQuantity()).append(",");
                sb.append(item.getOwner()).append(",");
                sb.append(item.getBoxID());
                writer.write(sb.toString());
                writer.newLine(); // Ensure each item is written on a new line
        }
    } catch (IOException e) {
        e.printStackTrace();
        }
    } // end of saveItems


    // Author: Tabatha Valverde
    public void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("items.csv"))) {
            String line;
            ArrayList<Item> loadedList = new ArrayList<>();
            
            // Skip header line
            reader.readLine();
            
            // Read each line and create an Item object
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                
                // Assuming the CSV has columns in the order: ItemName, Date, PackStatus, Amount, Owner
                String itemName = data[0];
                String date = data[1];
                Boolean packStatus = Boolean.parseBoolean(data[2]);
                int amount = Integer.parseInt(data[3]);
                String owner = data[4];
                int ID = Integer.parseInt(data[5]);

                // Create a new Item object and add it to the list
                Item item = new Item();
                item.setName(itemName);
                item.setDate(date);
                item.setStatus(packStatus);
                item.setQuantity(amount);
                item.setOwner(owner);
                item.setBoxID(ID);

                loadedList.add(item);
            }
            
            // Update the itemList and refresh the table
            itemList.setAll(loadedList);
            tableView.setItems(itemList);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    } //end of loadItems

} // end of itemHomeController