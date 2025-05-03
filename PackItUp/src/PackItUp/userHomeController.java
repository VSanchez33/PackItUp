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

public class userHomeController implements homeController<User> {

    // Variable declaration
    private ObservableList<User> userList = FXCollections.observableArrayList(); // List to store boxes
    private Stage stage;
    private Scene scene;
    private Parent root;
    private DataManager dataManager = DataManager.getInstance();

    @FXML
    private TableView<User> tableView;

    @FXML
    private TableColumn<User, String> userColumn;

    // Method to initialize the controller
    @FXML
    public void initialize() throws IOException {

        // Set up each column to display the correct property
        userColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Initially populate the table with data from boxList
        tableView.setItems(dataManager.getUserList());
        tableView.refresh();

        loadData(); // Load data

        // Handle row click to select box
        tableView.setOnMouseClicked(event -> {

            if (event.getClickCount() == 2) { // Double click to edit

                User selectedUser = tableView.getSelectionModel().getSelectedItem();

                if (selectedUser != null) {

                    // Call edit method to open the user creation screen for editing
                    openUser(selectedUser);
                    saveData();

                } // end of if

            } // end of if

        }); // end of tableView

        saveData(); // Save data

    } // end of initialize

    // Set the user to the list
    public void setList(ObservableList<User> userList) {

        this.userList = userList;
        tableView.setItems(userList); // Update the table with the new list
        tableView.refresh(); // Ensure the table view is refreshed to reflect changes

        saveData(); // Save data

    } // end of setUserList

    // Button that opens Home Screen
    public void goBack(ActionEvent event) throws IOException {

        saveData();
        root = FXMLLoader.load(getClass().getResource("splash.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    } // end of goBack

    // Button that opens the user creation screen
    public void create(ActionEvent event) throws IOException {

        saveData();

        System.out.println("Navigating to user creation screen...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addUser.fxml"));
        root = loader.load();

        userController controller = loader.getController();
        controller.setList(userList);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    } // end of createUser

    // Button that allows the user to delete the selected list box
    @FXML
    public void delete(ActionEvent event) throws IOException {

        User selectedUser = tableView.getSelectionModel().getSelectedItem(); // Get the selected box from the TableView

        if (selectedUser != null) {

            // Show a confirmation dialog before deletion
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Are you sure you want to delete this user?");
            alert.setContentText(selectedUser.getName());

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                userList.remove(selectedUser); // Remove the selected box from the box list
                tableView.refresh(); // Refresh the table
                saveData();

            } // end of if

        } // end of if

        else {

            System.out.println("No user selected for deletion."); // Show a message if no box was selected

        } // end of else

        saveData();

    } // end of deleteUser

    // Edit user by selecting the desired user and clicking the Edit button
    public void edit(ActionEvent event) throws IOException {

        User selectedUser = tableView.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("addUser.fxml"));
            Parent root = loader.load();

            userController controller = loader.getController();

            // Pass the selected user to the userController for editing
            controller.setUser(selectedUser);
            controller.setList(userList); // Pass the user list to the controller

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } // end of if

    } // end of editUser

    // Open the editing user when an box is double-clicked
    private void openUser(User selectedUser) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("location.fxml"));
            Parent root = loader.load();

            locationHomeController controller = loader.getController();

            int userID = selectedUser.getID();
            System.out.println("User Passed ID: " + userID);

            controller.setSelectedID(userID);
            controller.display(userID);

            Stage stage = (Stage) tableView.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {

            e.printStackTrace();

        } // end of try/catch

    } // end of openUser

    @FXML
    public void handleEdit(ActionEvent event) throws IOException {

        User selectedUser = tableView.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {

            // Navigate to box creation screen for editing
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addUser.fxml"));
            Parent root = loader.load();

            // Get the controller of userCreation.fxml
            userController controller = loader.getController();

            // Set the current box to the selected box for editing
            controller.setUser(selectedUser);
            controller.setList(userList);

            // Show the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            saveData();

        } // end of if

        else {

            System.out.println("No user selected for editing."); // Handle case when no box is selected

        } // end of else

        saveData();

    } // end of handleEditUser

    // Saves the users to the csv file
    public void saveData() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.csv"))) {
            // Write the header (optional)
            writer.write("ID, UserName");
            writer.newLine();

            // Write each box in the list to the CSV file
            for (User user : userList) {

                writer.write(user.getID() + "," + user.getName());
                writer.newLine(); // Ensure each users is written on a new line

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    } // end of saveData

    // Loads the users from the csv
    public void loadData() {

        // User.resetIDCounter();

        try (BufferedReader reader = new BufferedReader(new FileReader("users.csv"))) {

            String line;
            ArrayList<User> loadedList = new ArrayList<>();

            // Skip header line
            reader.readLine();

            // Read each line and create a Box object
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                if (parts.length == 2) {

                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    User newUser = new User(id, name); // This should set name & update ID counter
                    loadedList.add(newUser);

                } // end of if

            } // end of while

            userList.setAll(loadedList);
            tableView.setItems(userList);
            tableView.refresh();

        } catch (IOException e) {

            e.printStackTrace();

        } // end of try/catch

    } // end of loadData

    public void setSelectedID(int id) {
        ;
    };

    public int getSelectedID() {
        return 0;
    };

    public void display(int id) {
        ;
    };

} // end of userHomeController