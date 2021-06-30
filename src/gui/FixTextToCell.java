package gui;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Text;

/**
 * 
 * @author daniCV
 * @param <T>
 */

public class FixTextToCell<T> {
    public void fitTextToCell(TableColumn<T, String> tableColum) {
        tableColum.setCellFactory(param -> {
            return new TableCell<T, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        Text text = new Text(item);
                        text.setStyle("-fx-text-alignment: left;");                      
                        text.wrappingWidthProperty().bind(getTableColumn().widthProperty().subtract(30));
                        setGraphic(text);
                    }
                }
            };
        });
    }
}
