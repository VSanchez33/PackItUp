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

public class User {

    private StringProperty userName;
    private IntegerProperty userID;
    private ArrayList<Location> locations;

    public User() {
        userName = new SimpleStringProperty("UserName");
        userID = new SimpleIntegerProperty(1);
        locations = new ArrayList<>();
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
    
}
