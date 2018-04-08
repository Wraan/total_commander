package sample.Service;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControllerServiceImpl implements ControllerService {

    @Override
    public void addDoubleClickListenerListView(ListView<String> fileList, TextField textField) {
        fileList.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                openDirectory(fileList, textField);
            }
        });
    }

    @Override
    public void loadPathList(String path, ListView<String> listView) {
        listView.getItems().clear();
        File file = new File(path);
        File[] files = FileSystemView.getFileSystemView().getFiles(file, false);
        List<String> onlyDirs = new ArrayList<>();
        List<String> onlyFiles = new ArrayList<>();
        for(File f : files){
            if(f.isDirectory())
                onlyDirs.add(f.getName());
            else
                onlyFiles.add(f.getName());
        }
        Collections.sort(onlyDirs, String.CASE_INSENSITIVE_ORDER);
        Collections.sort(onlyFiles, String.CASE_INSENSITIVE_ORDER);
        onlyDirs.addAll(onlyFiles);
        for(String name: onlyDirs){
            listView.getItems().add(name);
        }
    }

    @Override
    public void openDirectory(ListView<String> fileList, TextField textField) {
        String dirName = fileList.getSelectionModel().getSelectedItem();
        File file = new File(textField.getText() + dirName);
        if (file.isDirectory()){
            textField.setText(textField.getText() + dirName + "\\");
            loadPathList(textField.getText(), fileList);
        }
    }
}
