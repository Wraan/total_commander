package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Service.ControllerService;
import sample.Service.ControllerServiceImpl;
import sample.Service.FileService;
import sample.Service.FileServiceImpl;

import java.io.File;

public class Controller {

    @FXML
    private ListView<String> leftFileList;

    @FXML
    private TextField rightPathTextF;

    @FXML
    private ListView<String> rightFileList;

    @FXML
    private TextField leftPathTextF;

    private FileService fileService = new FileServiceImpl();

    private ControllerService controllerService = new ControllerServiceImpl();

    @FXML
    public void initialize() {
        controllerService.addDoubleClickListenerListView(leftFileList, leftPathTextF);
        controllerService.addDoubleClickListenerListView(rightFileList, rightPathTextF);
    }

    @FXML
    void backDirectory(ActionEvent event) {
        Button btn = (Button) event.getSource();
        TextField tf;
        ListView<String> lv;
        if(btn.getId().equals("leftBackButton")) {
            tf = leftPathTextF;
            lv = leftFileList;
        }
        else {
            tf = rightPathTextF;
            lv = rightFileList;
        }

        File file = new File(tf.getText());
        tf.setText(fileService.getParent(file));

        controllerService.loadPathList(tf.getText(), lv);
    }

    @FXML
    public void loadRoots(Event event){
        ComboBox cb = (ComboBox) event.getSource();
        cb.getItems().clear();

        File[] paths = File.listRoots();

        for(File path:paths){
            cb.getItems().add(path.toString());
        }
    }

    @FXML
    public void putRootToPath(ActionEvent event){
        ComboBox cb = (ComboBox) event.getSource();
        if(cb.getSelectionModel().getSelectedItem() != null){
            if(cb.getId().equals("leftRootComboBox")) {
                leftPathTextF.setText(cb.getSelectionModel().getSelectedItem().toString());
                controllerService.loadPathList(leftPathTextF.getText(), leftFileList);
            }
            else {
                rightPathTextF.setText(cb.getSelectionModel().getSelectedItem().toString());
                controllerService.loadPathList(rightPathTextF.getText(), rightFileList);
            }
        }
    }

    @FXML
    public void loadPath(ActionEvent event){
        TextField tf = (TextField) event.getSource();
        ListView<String> lv;
        File file = new File(tf.getText());
        if(file.exists()){
            if(tf.getId().equals("leftPathTextF"))
                lv = leftFileList;
            else
                lv = rightFileList;

            if(tf.getText().charAt(tf.getText().length()-1) != '\\')
                tf.appendText("\\");

            controllerService.loadPathList(file.getPath(), lv);
        }
    }
    @FXML
    public void deleteFile(ActionEvent event) {
        if(leftFileList.isFocused()) {
            fileService.deleteFile(leftPathTextF.getText() + leftFileList.getSelectionModel().getSelectedItem());
            controllerService.loadPathList(leftPathTextF.getText(), leftFileList);
        }

        if(rightFileList.isFocused()) {
            fileService.deleteFile(rightPathTextF.getText() + rightFileList.getSelectionModel().getSelectedItem());
            controllerService.loadPathList(rightPathTextF.getText(), rightFileList);
        }
    }

    @FXML
    public void copyToRight() {
        String path = leftPathTextF.getText() + leftFileList.getSelectionModel().getSelectedItem();
        String dest = rightPathTextF.getText() + leftFileList.getSelectionModel().getSelectedItem();
        fileService.copyFile(path, dest);
        controllerService.loadPathList(rightPathTextF.getText(), rightFileList);
    }

    @FXML
    public void moveToRight() {
        String path = leftPathTextF.getText() + leftFileList.getSelectionModel().getSelectedItem();
        String dest = rightPathTextF.getText() + leftFileList.getSelectionModel().getSelectedItem();
        fileService.moveFile(path, dest);
        loadBothPathLists();

    }

    @FXML
    public void copyToLeft() {
        String path = rightPathTextF.getText() + rightFileList.getSelectionModel().getSelectedItem();
        String dest = leftPathTextF.getText() + rightFileList.getSelectionModel().getSelectedItem();
        fileService.copyFile(path, dest);
        controllerService.loadPathList(leftPathTextF.getText(), leftFileList);
    }

    @FXML
    public void moveToLeft() {
        String path = rightPathTextF.getText() + rightFileList.getSelectionModel().getSelectedItem();
        String dest = leftPathTextF.getText() + rightFileList.getSelectionModel().getSelectedItem();
        fileService.moveFile(path, dest);
        loadBothPathLists();

    }

    private void loadBothPathLists(){
        controllerService.loadPathList(rightPathTextF.getText(), rightFileList);
        controllerService.loadPathList(leftPathTextF.getText(), leftFileList);
    }




}
