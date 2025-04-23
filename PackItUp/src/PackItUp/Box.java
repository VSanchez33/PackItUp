/*
 * Authors: 
 *      Bryson Young
 */


package PackItUp;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Box {

    private StringProperty boxName;
    private IntegerProperty boxID;
    private String location;
 
    public Box() {
        boxName = new SimpleStringProperty("Box");
        boxID = new SimpleIntegerProperty(1);
    } // End of constructor
    
    public StringProperty boxNameProperty(){
        return boxName;
    }

    public IntegerProperty boxIDProperty(){
        return boxID;
    }

    public void setBoxName(String name) {
        this.boxName.set(name);
    }

    public void setBoxID(int ID) {
        this.boxID.set(ID);
    }

    public String getBoxName() {
        return boxName.get();
    }

    public int getBoxID() {
        return boxID.get();
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }
}