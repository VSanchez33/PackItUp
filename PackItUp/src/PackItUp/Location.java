/*
* Author:
*      Bryson Young
*/

package PackItUp;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Location {

   private StringProperty name;
   // maybe box list
   private String user;

   public Location() {
      name = new SimpleStringProperty("Location");
   }

   // Getters and Setters

   public void setLocationName(String newName) {
      name.set(newName);
   }

   public String getName() {
      return name.get();
   }

   public String getSelectedUser(){
      return user;
   }

   public void setUser(String user){
      this.user = user;
   }
}
