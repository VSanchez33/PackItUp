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
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class itemHomeController implements homeController<Item> {

    // Variable declaration
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
    private TextField searchField;

    @FXML
    public void initialize() throws IOException {

        // Set up each column to display the correct property
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("packStatus"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        display(selectedBoxID);

        // Load data
        loadData();

        // Setup search functionality
        FilteredList<Item> filteredData = new FilteredList<>(itemList, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {

            filteredData.setPredicate(item -> {

                // If search is empty, show all
                if (newValue == null || newValue.isEmpty()) {

                    return item.getBoxID() == selectedBoxID;

                } // end of if

                String lowerCaseFilter = newValue.toLowerCase();

                return item.getBoxID() == selectedBoxID &&
                        (item.getName().toLowerCase().contains(lowerCaseFilter) ||
                                item.getDate().toLowerCase().contains(lowerCaseFilter) ||
                                String.valueOf(item.getQuantity()).contains(lowerCaseFilter));
            });

            tableView.setItems(filteredData);
            tableView.refresh();

        });

        // Default display
        tableView.setItems(filteredData);
        tableView.refresh();

        saveData();

        // debug code
        System.out.println("itemHomeController initialized with selectedBoxID: " + selectedBoxID);

    } // end of initialize

    // Open the editing view when an item is double-clicked
    public void edit(ActionEvent event) throws IOException {

        Item selectedItem = tableView.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("itemCreation.fxml"));
            Parent root = loader.load();

            itemController controller = loader.getController();

            // Pass the selected item to the boxController for editing
            controller.setItem(selectedItem);
            controller.setList(itemList); // Pass the item list to the controller
            controller.setID(selectedBoxID);

            Stage stage = (Stage) tableView.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } // end of if

    } // end of editItem

    // Button that opens Boxes Screen
    public void goBack(ActionEvent event) throws IOException {

        saveData(); // Save any changes made to the list of items before going back

        // Load the previous screen (e.g., locationHomeController)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("box.fxml"));
        root = loader.load();

        // Pass the updated box list to locationHomeController
        boxHomeController boxController = loader.getController();
        boxController.display(boxController.getSelectedID()); // Pass the list of Box objects

        // Pass the current user (if needed)
        boxController.setSelectedID(boxController.getSelectedID());

        // Navigate to the locationHome screen
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    } // end of goBack

    // Button that opens the item creation screen
    public void create(ActionEvent event) throws IOException { 

        saveData();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("itemCreation.fxml"));
        root = loader.load();

        itemController controller = loader.getController();
        controller.setList(itemList); // Pass item list to the creation controller
        controller.setID(selectedBoxID);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    } // end of createItem

    // Button that allows the user to delete the selected list item
    @FXML
    public void delete(ActionEvent event) throws IOException {

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

            } // end of if

        } // end of if

        else {

            System.out.println("No item selected for deletion."); // Show a message if no item was selected

        } // end of else

        FXMLLoader loader = new FXMLLoader(getClass().getResource("items.fxml"));
        Parent root = loader.load();

        itemHomeController controller = loader.getController();
        controller.setList(itemList); // Pass the updated list back
        controller.setSelectedID(controller.getSelectedID());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        saveData();

    } // end of deleteItem

    public void setList(ObservableList<Item> itemList) {

        this.itemList = itemList;
        tableView.setItems(itemList); // Update the table with the new list
        tableView.refresh(); // Ensure the table view is refreshed to reflect changes

        saveData();

    } // end of setItemList

    @FXML
    public void handleEdit(ActionEvent event) throws IOException {

        Item selectedItem = tableView.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {

            // Navigate to item creation screen for editing
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemCreation.fxml"));
            Parent root = loader.load();

            // Get the controller of ItemCreation.fxml
            itemController controller = loader.getController();

            // Set the current item to the selected item for editing
            controller.setItem(selectedItem);
            controller.setList(itemList); // Pass the item list for saving new items

            // Show the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            saveData();

        } // end of if

        else {

            System.out.println("No item selected for editing."); // Handle case when no item is selected

        } // end of else

        saveData();

    } // end of handleEditItem

    public void saveData() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("items.csv"))) {

            // Write the header
            writer.write("ItemName,Date,PackStatus,Amount,BoxID");
            writer.newLine();

            // Write each item in the list to the CSV file
            for (Item item : itemList) {

                StringBuilder sb = new StringBuilder();
                sb.append(item.getName()).append(",");
                sb.append(item.getDate()).append(",");
                sb.append(item.getStatus()).append(",");
                sb.append(item.getQuantity()).append(",");
                sb.append(item.getBoxID());
                writer.write(sb.toString());
                writer.newLine();

            } // end of for

        } catch (IOException e) {

            e.printStackTrace();

        } // end of try/catch

    } // end of saveItems

    // Loads the items from the csv
    public void loadData() {

        try (BufferedReader reader = new BufferedReader(new FileReader("items.csv"))) {

            String line;
            ArrayList<Item> loadedList = new ArrayList<>();

            // Skip header line
            reader.readLine();

            // Read each line and create an Item object
            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                // Assuming the CSV has columns in the order: ItemName, Date, PackStatus,
                // Amount, ID
                String itemName = data[0];
                String date = data[1];
                String packStatus = data[2];
                int amount = Integer.parseInt(data[3]);
                int ID = Integer.parseInt(data[4]);

                // Create a new Item object and add it to the list
                Item item = new Item();
                item.setName(itemName);
                item.setDate(date);
                item.setStatus(packStatus);
                item.setQuantity(amount);
                item.setBoxID(ID);

                loadedList.add(item);

            } // end of while

            // Update the itemList and refresh the table
            itemList.setAll(loadedList);
            tableView.setItems(itemList);

        } catch (IOException e) {

            e.printStackTrace();

        } // end of try/catch

    } // end of loadData

    // Set the boxId to get the correct items
    public void setSelectedID(int id) {

        selectedBoxID = id;
        loadData(); // Reload data when the selected box changes
        display(selectedBoxID);

    } // end of setSelectedBoxID

    // get the box id to display the correct items
    public int getSelectedID() {

        return selectedBoxID;

    } // end of getSeletcedBoxID

    // Displays the items from correct box
    public void display(int boxID) {

        ObservableList<Item> filteredList = itemList.filtered(item -> item.getBoxID() == boxID);
        tableView.setItems(filteredList);
        tableView.refresh();

    } // end of displayItems

} // end of itemHomeController