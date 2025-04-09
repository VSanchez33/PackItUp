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
   // maybe box list
   private ArrayList<Box> boxList;
   private IntegerProperty totalBoxes;
   private String user;

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

   public String getName() {
      return name.get();
   }


   // get and set boxes
   public ArrayList<Box> getBoxes() {
   return boxList;
   }

   public String getSelectedUser(){
      return user;
   }

   public void setUser(String user){
      this.user = user;
   }
}
