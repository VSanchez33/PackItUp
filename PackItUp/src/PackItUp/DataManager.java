/*
 * Authors: 
 *      Vincent Sanchez
 */

package PackItUp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataManager {

    private static DataManager instance;
    private ObservableList<Item> itemList = FXCollections.observableArrayList();
    private ObservableList<Box> boxList = FXCollections.observableArrayList();
    private ObservableList<Location> locationList = FXCollections.observableArrayList();
    private ObservableList<User> userList = FXCollections.observableArrayList();

    private DataManager(){
        System.out.println("DataManager Initialize");
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    } // end of getInstance

    public ObservableList<Item> getItemList() {
        return itemList;
    } // end of getItemList

    public ObservableList<Box> getBoxList() {
        return boxList;
    } // end of getBoxList
    
    public ObservableList<Location> getLocationList() {
        return locationList;
    } // end of getBoxList

    public ObservableList<User> getUserList() {
        return userList;
    } // end of getBoxList
}