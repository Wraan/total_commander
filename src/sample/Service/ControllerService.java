package sample.Service;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public interface ControllerService {

    void addDoubleClickListenerListView(ListView<String> fileList, TextField textField);
    void loadPathList(String path, ListView<String> listView);
    void openDirectory(ListView<String> fileList, TextField textField);

}
