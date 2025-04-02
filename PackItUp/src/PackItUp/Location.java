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
 
 public class Location {
 
     private IntegerProperty locationID;
     private StringProperty name;
     private ObservableList<Box> boxList;
     private IntegerProperty totalBoxes;
     // UserID

     public Location() {
        locationID = new SimpleIntegerProperty(1);
        name = new SimpleStringProperty("Location");
        // boxes
        totalBoxes = new SimpleIntegerProperty(0);
        // UserID
     }

     // Getters and Setters

     public void setLocationName(String newName) {
        name.set(newName);
     }
     public String getLocationName() {
        return name.get();
     }
     // get and set boxes
     // get and set users
     
 }
 