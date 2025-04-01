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
}