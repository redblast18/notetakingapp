package ci.pigier.controllers.ui;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import ci.pigier.controllers.BaseController;
import ci.pigier.dao.NoteDAO;
import ci.pigier.model.Note;
import ci.pigier.ui.FXMLPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddEditUIController extends BaseController implements Initializable {

    @FXML
    private TextArea descriptionTxtArea;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField titleTxtFld;

    private NoteDAO noteDAO = new NoteDAO();

    @FXML
    void doBack(ActionEvent event) throws IOException {
        navigate(event, FXMLPage.LIST.getPage());
    }

    @FXML
    void doClear(ActionEvent event) {
        titleTxtFld.clear();
        descriptionTxtArea.clear();
    }

    @FXML
    void doSave(ActionEvent event) throws IOException {
        if (titleTxtFld.getText().trim().isEmpty() || descriptionTxtArea.getText().trim().isEmpty()) {
            alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Invalid data to save or update!");
            alert.setContentText("Note title or description cannot be empty!");
            alert.showAndWait();
            return;
        }

        Note note = new Note(titleTxtFld.getText(), descriptionTxtArea.getText());

        if (editNote != null) {
            noteDAO.updateNote(note);
            data.remove(editNote);
        } else {
            noteDAO.addNote(note);
        }

        data.add(note);
        navigate(event, FXMLPage.LIST.getPage());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (editNote != null) {
            titleTxtFld.setText(editNote.getTitle());
            descriptionTxtArea.setText(editNote.getDescription());
            saveBtn.setText("Update");
        }
    }
}
