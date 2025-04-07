/*
 * Authors: 
 *      Tabatha Valverde
 *      Vincent Sanchez
 */

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
import javafx.scene.control.TableView;

    
public class itemController {
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    private ObservableList<Item> itemList; // This will store the items from homeController
    private Item currentItem; // Store the item currently being edited
    private DataManager dataManager = DataManager.getInstance(); // Data Manager to store items
    private static int boxID; // Box ID for the selected item

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


    // Author: Tabatha Valverde
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


    // Author: Tabatha Valverde
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


    // Author: Tabatha Valverde
    @FXML
    public void cancel (ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("items.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end of cancel


    // Author: Tabatha Valverde and Vincent Sanchez
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
        else{
            Item newItem = new Item();
            newItem.setName(nameField.getText());
            newItem.setDate(datePicker.getValue().toString());
            newItem.setStatus(Boolean.parseBoolean(statusField.getText()));
            newItem.setQuantity(Integer.parseInt(amountField.getText()));
            newItem.setOwner(ownerField.getText());
            // Sets the ID for the box the item is stored in 
            newItem.setBoxID(boxID);
            System.out.println("BOX ID IS THIS: " + boxID);
            // Add the new item to the list
            itemList.add(newItem);
            // Add the new item to the list universally
            dataManager.getItemList().add(newItem);
        } // end of else

        // Navigate back to the home screen with updated items
        FXMLLoader loader = new FXMLLoader(getClass().getResource("items.fxml"));
        Parent root = loader.load();

        itemHomeController controller = loader.getController();
        controller.setItemList(itemList); // Pass the updated list back
        controller.setSelectedBoxID(boxID);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end of saveItem


    // Author: Tabatha Valverde
    // Setter to receive the list of items from homeController
    public void setItemList(ObservableList<Item> itemList) {
        this.itemList = itemList;
    } // end of setItemList


    // Author: Tabatha Valverde
    // Sets the ID for the item of the current box
    public void setBoxID(int boxID) { // Add this setter
        this.boxID = boxID;
    } // end of setBoxID

} // end of itemController
