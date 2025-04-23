/*
 * Author:
 *      Bryson Young
 */

package PackItUp;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

    private StringProperty userName;
    // maybe user ID
    private ArrayList<Location> locations;

    public User() {
        userName = new SimpleStringProperty("UserName");
        locations = new ArrayList<>();
    }

    // Setters and getters
    public void setName(String newName) {
        userName.set(newName);
    }

    public String getName() {
        return userName.get();
    }

    public void addLocations(Location location) {
        locations.add(location);
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }
    
}
