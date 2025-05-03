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

public class userController implements Controller<User> {

    // Variable declaration
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

            userNameField.setText(currentUser.getName()); // Load the user data into the fields if there's an user to
                                                          // edit

        } // end of if

    } // end of initialize

    // Set the user to the list
    public void setUser(User user) {

        this.currentUser = user;
        userNameField.setText(currentUser.getName()); // Populate fields with the selected user data for editing

    } // end of setUser

    // Cancels user creation or edit
    @FXML
    public void cancel(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("user.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    } // end of cancel

    // Saves the user created or edited
    @FXML
    public void save(ActionEvent event) throws IOException {

        if (currentUser != null) {

            currentUser.setName(userNameField.getText()); // If we're updating an existing user, apply changes

        } // end of if

        else {

            // If currentBox is null, create a new user
            User newUser = new User(userNameField.getText());

            // Add the new user to the list
            userList.add(newUser);
            dataManager.getUserList().add(newUser);

        } // end of else

        // Navigate back to User screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("user.fxml"));
        Parent root = loader.load();

        // Pass the updated user list to userHomeController
        userHomeController controller = loader.getController();
        controller.setList(userList);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    } // end of saveUser

    // Setter to receive the list of users from homeController
    public void setList(ObservableList<User> userList) {

        this.userList = userList;

    } // end of setUserList

    public void setID(int id) {
        ;
    }

} // end of userController
