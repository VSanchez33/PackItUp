package PackItUp;

public class Item 
{
    private String itemName;    // Name of the item
    private String date;        // Date the item was last updated
    private boolean packStatus; // Status of the items packing (packed/unpacked)
    private int amount;         // Amount of said item is in the box
    private String owner;       // Name of the last person to update the item

    // Default values
    public Item()
    {
        itemName = "Item";
        date = "01/01/2000";
        packStatus = false;
        amount = 1;
    }

    // Setters
    public void setName(String newName)
    {
        itemName = newName;
    }
    public void setDate(int newDay, int newMonth, int newYear)
    {
        date = "" + newDay + "/" + newMonth + "/" + newYear;
    }
    public void setStatus(boolean newStatus)
    {
        packStatus = newStatus;
    }
    public void setQuantity(int newAmount)
    {
        amount = newAmount;
    }
    public void setOwner(String newOwner)
    {
        owner = newOwner;
    }

    // Getters
    public String getName()
    {
        return itemName;
    }
    public String getDate()
    {
        return date;
    }
    public boolean getStatus()
    {
        return packStatus;
    }
    public int getQuantity()
    {
        return amount;
    }
    public String getOwner()
    {
        return owner;
    }
}
