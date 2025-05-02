/*
 * Authors: 
 *      Bryson Young
 */

package PackItUp;

import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Box {

    // Variable declaration
    private int locationID; // Link to Location by ID
    private static int idBoxCounter = 1;
    private StringProperty boxName;
    private StringProperty boxInputID;
    private IntegerProperty boxID;
    private ArrayList<Item> itemList;

    // Constructors
    public Box() {

        this.boxID = new SimpleIntegerProperty(idBoxCounter++);
        this.boxName = new SimpleStringProperty("Box");
        this.boxInputID = new SimpleStringProperty("1");
        this.itemList = new ArrayList<>();

    }

    public Box(int id, String name, String input, int locationID) {

        this.boxID = new SimpleIntegerProperty(id);
        this.boxName = new SimpleStringProperty(name);
        this.boxInputID = new SimpleStringProperty(input);
        this.locationID = locationID;
        this.itemList = new ArrayList<>();

        if (id >= idBoxCounter) {
            idBoxCounter = id + 1;
        }
    }

    // Setters and getters

    public void setBoxID(int ID) {
        this.boxID.set(ID);
    }

    public int getBoxID() {
        return boxID.get();
    }

    public void setBoxName(String name) {
        this.boxName.set(name);
    }

    public String getBoxName() {
        return boxName.get();
    }

    public void setBoxInput(String input) {
        this.boxInputID.set(input);
    }

    public String getBoxInput() {
        return boxInputID.get();
    }

    public void setItems(ArrayList<Item> items) {
        this.itemList = items;
    }

    public ArrayList<Item> getItems() {
        return itemList;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public int getLocationID() {
        return locationID;
    }

    public static void resetIDCounter() {
        idBoxCounter = 1;
    }

} // end of Box
