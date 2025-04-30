/*
 * Author:
 *      Bryson Young
 */

package PackItUp;

import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class User {

    private static int idCounter = 1;
    private StringProperty userName;
    private IntegerProperty userID;
    private ArrayList<Location> locations;

    public User() {
        this.userID = new SimpleIntegerProperty(idCounter++);
        this.userName = new SimpleStringProperty("UserName");
        this.locations = new ArrayList<>();
    }

    public User(String name) {
        this.userID = new SimpleIntegerProperty(idCounter++);
        this.userName = new SimpleStringProperty(name);
        this.locations = new ArrayList<>();
    }

    public User(int id, String name) {
        this.userID = new SimpleIntegerProperty(id);
        this.userName = new SimpleStringProperty(name);
        this.locations = new ArrayList<>();
    
        if (id >= idCounter) {
            idCounter = id + 1;
        }
    }

    // Setters and getters
    public void setName(String newName) {
        userName.set(newName);
    }
    public String getName() {
        return userName.get();
    }
    public void setID(int newID) {
        userID.set(newID);
    }
    public int getID() {
        return userID.get();
    }

    public void addLocations(Location location) {
        locations.add(location);
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }
    
    public static void resetIDCounter() {
        idCounter = 1;
    }
}