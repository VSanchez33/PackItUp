/*
 * Authors: 
 *      Bryson Young
 *      Tabatha Valverde
 */

package PackItUp;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {

    // Variable declaration
    private StringProperty itemName;
    private StringProperty date;
    private StringProperty packStatus;
    private IntegerProperty amount;
    private int boxID;

    // Constructors
    public Item() {

        itemName = new SimpleStringProperty("Item");
        date = new SimpleStringProperty("01/01/2000");
        packStatus = new SimpleStringProperty("Packed");
        amount = new SimpleIntegerProperty(1);
    
    } 

    public Item(int boxID) {

        this(); // Call the default constructor
        this.boxID = boxID;

    }
    
    // Getter and setters

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

    public int getQuantity() {
        return amount.get();
    }

    public void setQuantity(int amount) {
        this.amount.set(amount);
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public int getBoxID() {
        return boxID;
    }

    public void setBoxID(int boxID) {
        this.boxID = boxID;
    }

} // end of Item