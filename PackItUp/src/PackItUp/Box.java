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

    // remove owner
    private StringProperty boxOwner;
    private StringProperty boxName;
    // remove date
    private StringProperty boxDate;
    private StringProperty packingReason;
    // maybe boxID 
    private IntegerProperty boxID;
    // remove total boxes
    private IntegerProperty totalBoxes;
    private String location;
 
    public Box() {
        boxName = new SimpleStringProperty("Box");
        boxDate = new SimpleStringProperty("01/01/2000");
        packingReason = new SimpleStringProperty("Miscellaneous");
        boxOwner = new SimpleStringProperty("No one");
        // items = new ArrayList<>();
        boxID = new SimpleIntegerProperty(1);
        totalBoxes = new SimpleIntegerProperty(1);
    } // End of constructor
    
    public StringProperty boxNameProperty(){
        return boxName;
    }

    public StringProperty boxDateProperty(){
        return boxDate;
    }

    public StringProperty packingReasonProperty(){
        return packingReason;
    }

    public IntegerProperty boxIDProperty(){
        return boxID;
    }

    public IntegerProperty totalBoxesProperty(){
        return totalBoxes;
    }

    public void setBoxName(String name) {
        this.boxName.set(name);
    }

    public void setBoxDate(String date) {
        this.boxDate.set(date);
    }

    public void setReason(String reason) {
        this.packingReason.set(reason);
    }

    // public void addItems(Item item) {
    //     items.add(item);
    // }

    public void setBoxID(int ID) {
        this.boxID.set(ID);
    }

    public void setTotalBoxes(int boxes) {
        this.totalBoxes.set(boxes);
    }

    public String getBoxName() {
        return boxName.get();
    }

    public String getBoxDate() {
        return boxDate.get();
    }

    public String getReason() {
        return packingReason.get();
    }

    // public ArrayList<Item> getPackedItems() {
    //     return items;
    // }

    public int getBoxID() {
        return boxID.get();
    }

    public int getTotalBoxes() {
        return totalBoxes.get();
    }

    public void setBoxOwner(String owner){
        this.boxOwner.set(owner);
    }

    public String getBoxOwner(){
        return boxOwner.get();
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }
}