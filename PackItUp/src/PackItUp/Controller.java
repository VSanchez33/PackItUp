/*
 * Authors: 
 *      Vincent Sanchez
 */

package PackItUp;

import javafx.fxml.FXML;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

// Interface for Controller Classes 
public interface Controller<E> {
    @FXML
    public void initialize();
    @FXML
    public void cancel (ActionEvent event) throws IOException;
    @FXML
    public void save (ActionEvent event) throws IOException;
    public void setList(ObservableList<E> list);
}
