/*
 * Authors: 
 *      Bryson Young
 *      Tabatha Valverde
 */

package PackItUp;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {

    // Tabatha: updated the elemets to e properties to work with the table
    private StringProperty itemName;
    private StringProperty date;
    private BooleanProperty packStatus;
    private IntegerProperty amount;
    // remove owner
    private StringProperty owner;
    private int boxID;

    // Everything else Bryson implemented 
    public Item() {
        itemName = new SimpleStringProperty("Item");
        date = new SimpleStringProperty("01/01/2000");
        packStatus = new SimpleBooleanProperty(false);
        amount = new SimpleIntegerProperty(1);
        owner = new SimpleStringProperty("Unknown");
    } // end of constructor
    

    public String getName() {
        return itemName.get();
    }

    public void setName(String name) {
        itemName.set(name);
    }

    public StringProperty itemNameProperty() {
        return itemName;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public StringProperty dateProperty() {
        return date;
    }

    public boolean getStatus() {
        return packStatus.get();
    }

    public void setStatus(boolean status) {
        packStatus.set(status);
    }

    public BooleanProperty packStatusProperty() {
        return packStatus;
    }

    public int getQuantity() {
        return amount.get();
    }

    public void setQuantity(int amount) {
        this.amount.set(amount);
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public String getOwner() {
        return owner.get();
    }

    public void setOwner(String owner) {
        this.owner.set(owner);
    }

    public StringProperty ownerProperty() {
        return owner;
    }

    public int getBoxID() {
        return boxID;
    }

    public void setBoxID(int boxID) {
        this.boxID = boxID;
    }
} // end of Item