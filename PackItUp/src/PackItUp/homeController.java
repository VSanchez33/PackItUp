/*
 * Authors: 
 *      Vincent Sanchez
 */

package PackItUp;

import javafx.fxml.FXML;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

// Interface for homeController Classes 
public interface homeController<E> {

    @FXML
    public void initialize() throws IOException;

    public void goBack(ActionEvent event) throws IOException;

    public void create(ActionEvent event) throws IOException;

    @FXML
    public void delete(ActionEvent event) throws IOException;

    public void edit(ActionEvent event) throws IOException;

    public void setList(ObservableList<E> list);

    @FXML
    public void handleEdit(ActionEvent event) throws IOException;

    public void saveData();

    public void loadData();

    public void setSelectedID(int id);

    public int getSelectedID();

    public void display(int id);
}
