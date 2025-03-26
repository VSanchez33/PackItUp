package PackItUp;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;

    
public class itemController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private ObservableList<Item> itemList; // This will store the items from homeController
    private Item currentItem; // Store the item currently being edited
    
    @FXML
    private TextField nameField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField statusField;

    @FXML
    private TextField amountField;

    @FXML
    private TextField ownerField;


    // Method to initialize the controller
    @FXML
    public void initialize() {
        
        if (currentItem != null) {
            // Load the item data into the fields if there's an item to edit
            nameField.setText(currentItem.getName());
            datePicker.setValue(java.time.LocalDate.parse(currentItem.getDate())); // Assuming date format is ISO (yyyy-MM-dd)
            statusField.setText(Boolean.toString(currentItem.getStatus()));
            amountField.setText(Integer.toString(currentItem.getQuantity()));
            ownerField.setText(currentItem.getOwner());

        } // end of if
    } // end of initialize


    // Set the item to the list
    public void setItem(Item item) {

        this.currentItem = item;
        // Populate fields with the selected item data for editing
        nameField.setText(currentItem.getName());
        datePicker.setValue(java.time.LocalDate.parse(currentItem.getDate()));  // Assuming the date is in ISO format
        statusField.setText(Boolean.toString(currentItem.getStatus()));
        amountField.setText(Integer.toString(currentItem.getQuantity()));
        ownerField.setText(currentItem.getOwner());

    } // end of setItem

    @FXML
    public void cancel (ActionEvent event) throws IOException {
        
        root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    
    } // end of goMain

    // Save the changes made to the item
    @FXML
    void goHome(ActionEvent event) throws IOException {
        
        if (currentItem != null) {
            // If we're updating an existing item, apply changes
            currentItem.setName(nameField.getText());
            currentItem.setDate(datePicker.getValue().toString());
            currentItem.setStatus(Boolean.parseBoolean(statusField.getText()));
            currentItem.setQuantity(Integer.parseInt(amountField.getText()));
            currentItem.setOwner(ownerField.getText());

        } // end of if 
        
        else {
            // If currentItem is null, create a new item
            Item newItem = new Item();
            newItem.setName(nameField.getText());
            newItem.setDate(datePicker.getValue().toString());
            newItem.setStatus(Boolean.parseBoolean(statusField.getText()));
            newItem.setQuantity(Integer.parseInt(amountField.getText()));
            newItem.setOwner(ownerField.getText());
    
            // Add the new item to the list
            itemList.add(newItem);

        } // end of else

        // Navigate back to home
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        
        // Pass the updated item list to homeController
        homeController controller = loader.getController();
        controller.setItemList(itemList);  // Ensure the updated list is passed back to homeController
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    } // end of goHome


    @FXML
    private void saveItem(ActionEvent event) throws IOException {
        
        if (currentItem != null) {
            // Update the selected item with the new values
            currentItem.setName(nameField.getText());
            currentItem.setDate(datePicker.getValue().toString());
            currentItem.setStatus(Boolean.parseBoolean(statusField.getText()));
            currentItem.setQuantity(Integer.parseInt(amountField.getText()));
            currentItem.setOwner(ownerField.getText());

        } // end of if
        
        // Navigate back to the home screen with updated items
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        homeController controller = loader.getController();
        controller.setItemList(itemList); // Pass the updated list back
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    } // end of saveItem


    // Setter to receive the list of items from homeController
    public void setItemList(ObservableList<Item> itemList) {
        this.itemList = itemList;
    } // end of setItemList

} // end of itemController
