package PackItUp;

import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Box {
    
    private StringProperty boxName;
    private StringProperty boxDate;
    private StringProperty packingReason;
    private static ArrayList<Item> items;
    private IntegerProperty boxID;
    private IntegerProperty totalBoxes;

    public Box()
    {
        boxName = new SimpleStringProperty("Box");
        boxDate = new SimpleStringProperty("01/01/2000");
        packingReason = new SimpleStringProperty("Miscellaneous");
        items = new ArrayList<>();              
        boxID = new SimpleIntegerProperty(1);
        totalBoxes = new SimpleIntegerProperty(1);
    } // End of contstructor

    // Setters and Getters using JavaFX properties
    public void setName(String name)
    {
        this.boxName.set(name);
    }

    public void setDate(String date)
    {
        this.boxDate.set(date);
    }

    public void setReason(String reason)
    {
        this.packingReason.set(reason);
    }
    
    // Probably will not work with javafx properties
    public void addItems(Item item)
    {
        items.add(item);
    }

    public void setBoxID(int ID)
    {
        this.boxID.set(ID);
    }

    public void setTotalBoxes(int boxes)
    {
        this.totalBoxes.set(boxes);
    }

    public String getName() 
    {
        return boxName.get();
    }
    
    public String getDate()
    {
        return boxDate.get();
    }

    public String getReason()
    {
        return packingReason.get();
    }

    // Probably will not work with javafx properties
    public ArrayList<Item> getPackedItems()
    {
        return items;
    }

    public int getBoxID() 
    {
        return boxID.get();
    }

    public int getTotal()
    {
        return totalBoxes.get();
    }



}
