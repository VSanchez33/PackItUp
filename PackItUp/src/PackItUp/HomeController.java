/*
 * Authors: 
 *      Vincent Sanchez
 */

 package PackItUp;

 import java.io.IOException;
 
 import javafx.event.ActionEvent;
 
 import javafx.collections.ObservableList;
 import javafx.fxml.FXML;
 
 public interface HomeController<E>{
    @FXML
    public void initialize();
    public void goBack (ActionEvent event) throws IOException;
    public void create(ActionEvent event) throws IOException;
    public void delete(ActionEvent event) throws IOException;
    public void edit (E selected) throws IOException;
    public void setList(ObservableList<E> list);
    public void saveData();
    public void loadData();
 }
