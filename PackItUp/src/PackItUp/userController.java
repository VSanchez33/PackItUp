/*
* Authors: 
*      Vincent Sanchez
*      Tabatha Valverde
*/

package PackItUp;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
 
public class userController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private ObservableList<User> userList; // This will store the users from userHomeController
    private User currentUser; // Store the user currently being edited
    private DataManager dataManager = DataManager.getInstance();
    
    @FXML
    private TextField userNameField;
    

    // Method to initialize the controller
    @FXML 
    public void initialize() {
        if (currentUser != null) {
            // Load the user data into the fields if there's an user to edit
            userNameField.setText(currentUser.getName());
        } // end of if
    } // end of initialize
 

    // Set the user to the list
    public void setUser(User user) {
        this.currentUser = user;
        // Populate fields with the selected user data for editing
        userNameField.setText(currentUser.getName());
    } // end of setUser


    // Cancels user creation or edit 
    @FXML
    public void cancel (ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("user.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end of cancel    


    // Saves the user created or edited
    @FXML
    void saveUser(ActionEvent event) throws IOException {
        if (currentUser != null) {
            // If we're updating an existing user, apply changes
            currentUser.setName(userNameField.getText());
        } 
        else {
            // If currentBox is null, create a new user
            User newUser = new User();
            newUser.setName(userNameField.getText());
    
            // Add the new user to the list
            userList.add(newUser);
            dataManager.getUserList().add(newUser);
        } // end of else
    
        // Navigate back to home
        FXMLLoader loader = new FXMLLoader(getClass().getResource("user.fxml"));
        Parent root = loader.load();
    
        // Pass the updated user list to userHomeController
        userHomeController controller = loader.getController();
        controller.setUserList(userList); 
    
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } // end of saveUser


    // Setter to receive the list of users from homeController
    public void setUserList(ObservableList<User> userList) {
        this.userList = userList;
    } // end of setUserList
} // end of userController 
