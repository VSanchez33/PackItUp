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

   private static int idCounter = 1;
   private IntegerProperty locationID;
   private StringProperty name;
   private ArrayList<Box> boxList;
   private IntegerProperty totalBoxes;
   private int userID;

   public Location() {
      this.locationID = new SimpleIntegerProperty(idCounter++);
      this.name = new SimpleStringProperty("Location");
      this.totalBoxes = new SimpleIntegerProperty(0);
      this.boxList = new ArrayList<>();
   }

   public Location(int id, String name, int userID) {
      this.locationID = new SimpleIntegerProperty(id);
      this.name = new SimpleStringProperty(name);
      this.totalBoxes = new SimpleIntegerProperty(0);
      this.userID = userID;
      this.boxList = new ArrayList<>();

      if (id >= idCounter) {
         idCounter = id + 1;
      }
   }

   // Getters and Setters

   public int getLocationID() {
      return locationID.get();
   }

   public void setLocationID(int id) {
      locationID.set(id);
   }

   public void setLocationName(String newName) {
      name.set(newName);
   }

   public String getName() {
      return name.get();
   }

   public ArrayList<Box> getBoxes() {
   return boxList;
   }

   public void setBoxes(ArrayList<Box> boxes) {
      this.boxList = boxes;
   }

  public int getUserID() {
      return userID;
   }

  public void setUserID(int id) {
      this.userID = id;
   }

  public static void resetIDCounter() {
      idCounter = 1;
   }
   
}
