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

    private int locationID; // Link to Location by ID
    private static int nextBoxID = 1;
    private StringProperty boxOwner;
    private StringProperty boxName;
    private StringProperty boxDate;
    private StringProperty packingReason;
    private IntegerProperty boxID;
    private IntegerProperty totalBoxes;

    public Box() {
        this.boxID = new SimpleIntegerProperty(nextBoxID++);
        this.boxName = new SimpleStringProperty("Box");
        this.boxDate = new SimpleStringProperty("01/01/2000");
        this.packingReason = new SimpleStringProperty("Miscellaneous");
        this.boxOwner = new SimpleStringProperty("No one");
        this.totalBoxes = new SimpleIntegerProperty(1);
    }

    public Box(int locationID) {
        this(); // Call the default constructor
        this.locationID = locationID;
    }

    public StringProperty boxNameProperty() {
        return boxName;
    }

    public StringProperty boxDateProperty() {
        return boxDate;
    }

    public StringProperty packingReasonProperty() {
        return packingReason;
    }

    public IntegerProperty boxIDProperty() {
        return boxID;
    }

    public IntegerProperty totalBoxesProperty() {
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

    public int getBoxID() {
        return boxID.get();
    }

    public int getTotalBoxes() {
        return totalBoxes.get();
    }

    public void setBoxOwner(String owner) {
        this.boxOwner.set(owner);
    }

    public String getBoxOwner() {
        return boxOwner.get();
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }
}
