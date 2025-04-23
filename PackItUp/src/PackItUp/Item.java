/*
 * Authors: 
 *      Bryson Young
 *      Tabatha Valverde
 */

package PackItUp;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {

    // Tabatha: updated the elemets to e properties to work with the table
    private StringProperty itemName;
    private StringProperty date;
    private StringProperty packStatus;
    private StringProperty amount;
    // remove owner
    private int boxID;

    // Everything else Bryson implemented 
    public Item() {
        itemName = new SimpleStringProperty("Item");
        date = new SimpleStringProperty("01/01/2000");
        packStatus = new SimpleStringProperty("unpacked");
        amount = new SimpleStringProperty("1");
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

    public String getStatus() {
        return packStatus.get();
    }

    public void setStatus(String status) {
        packStatus.set(status);
    }

    public StringProperty packStatusProperty() {
        return packStatus;
    }

    public String getQuantity() {
        return amount.get();
    }

    public void setQuantity(String amount) {
        this.amount.set(amount);
    }

    public StringProperty amountProperty() {
        return amount;
    }

    public int getBoxID() {
        return boxID;
    }

    public void setBoxID(int boxID) {
        this.boxID = boxID;
    }
} // end of Item