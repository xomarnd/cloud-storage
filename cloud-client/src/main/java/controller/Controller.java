package src.main.java.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Controller {

    @FXML
    HBox connectPanel;

    @FXML
    VBox leftPanel, rightPanel;

    public void btnExitAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void copyBtnAction(ActionEvent actionEvent) {
        LocalPanelController leftPC = (LocalPanelController) leftPanel.getProperties().get("ctrl");
        CloudPanelController rightPC = (CloudPanelController) rightPanel.getProperties().get("ctrl");

        if (leftPC.getSelectedFilename() == null && rightPC.getSelectedFilename() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ни один файл не был выбран", ButtonType.OK);
            alert.showAndWait();
            return;
        }


        if (leftPC.getSelectedFilename() != null) {
            Path srcPath = Paths.get(leftPC.getCurrentPath(), leftPC.getSelectedFilename());
            Path dstPath = Paths.get(rightPC.getCurrentPath()).resolve(srcPath.getFileName().toString());

            try {
                Files.copy(srcPath, dstPath);
                leftPC.updateFilesList(Paths.get(leftPC.getCurrentPath()));
            } catch (IOException e) {
                System.out.println (e);
                Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось выгрузить файлы на облако", ButtonType.OK);
                alert.showAndWait();
            }
        }
        if (rightPC.getSelectedFilename() != null) {
            Path srcPath = Paths.get(rightPC.getCurrentPath(), rightPC.getSelectedFilename());
            Path dstPath = Paths.get(leftPC.getCurrentPath()).resolve(srcPath.getFileName().toString());

            try {
                Files.copy(srcPath, dstPath);
                rightPC.updateFilesList (Paths.get(rightPC.getCurrentPath()));
            } catch (IOException e) {
                System.out.println (e);
                Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось загрузить файл с облака", ButtonType.OK);
                alert.showAndWait();
            }
        }


    }
}