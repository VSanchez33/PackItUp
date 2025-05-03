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


public class Location {

   // Variable declaration
   private static int idLocCounter = 1;
   private IntegerProperty locationID;
   private StringProperty name;
   private ArrayList<Box> boxList;
   private int userID;

   // Constructors
   public Location() {
      
      this.locationID = new SimpleIntegerProperty(idLocCounter++);
      this.name = new SimpleStringProperty("Location");
      this.boxList = new ArrayList<>();
   }

   public Location(int id, String name, int userID) {
      
      this.locationID = new SimpleIntegerProperty(id);
      this.name = new SimpleStringProperty(name);
      this.userID = userID;
      this.boxList = new ArrayList<>();

      if (id >= idLocCounter) {
         idLocCounter = id + 1;
      }
   }

   // Setters and getters

   public void setLocationID(int id) {
      locationID.set(id);
   }

   public int getLocationID() {
      return locationID.get();
   }

   public void setLocationName(String newName) {
      name.set(newName);
   }

   public String getName() {
      return name.get();
   }

   public void setBoxes(ArrayList<Box> boxes) {
      this.boxList = boxes;
   }

   public ArrayList<Box> getBoxes() {
   return boxList;
   }

   public void setUserID(int id) {
      this.userID = id;
   }

   public int getUserID() {
      return userID;
   }

   public static void resetIDCounter() {
      idLocCounter = 1;
   }
   
} // end of Location class
