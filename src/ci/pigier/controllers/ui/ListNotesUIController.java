package ci.pigier.controllers.ui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import ci.pigier.controllers.BaseController;
import ci.pigier.dao.NoteDAO;
import ci.pigier.model.Note;
import ci.pigier.ui.FXMLPage;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListNotesUIController extends BaseController implements Initializable {

    @FXML
    private TableColumn<Note, String> descriptionTc;

    @FXML
    private Label notesCount;

    @FXML
    private TableView<Note> notesListTable;

    @FXML
    private TextField searchNotes;

    @FXML
    private TableColumn<Note, String> titleTc;

    private NoteDAO noteDAO = new NoteDAO();

    @FXML
    void doDelete(ActionEvent event) {
        Note selectedNote = notesListTable.getSelectionModel().getSelectedItem();
        if (selectedNote != null) {
            noteDAO.deleteNote(selectedNote.getTitle());
            data.remove(selectedNote);
        } else {
            alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("No Note Selected");
            alert.setContentText("Please select a note to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    void doEdit(ActionEvent event) throws IOException {
        Note selectedNote = notesListTable.getSelectionModel().getSelectedItem();
        if (selectedNote != null) {
            editNote = selectedNote;
            navigate(event, FXMLPage.ADD.getPage());
        } else {
            alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("No Note Selected");
            alert.setContentText("Please select a note to edit.");
            alert.showAndWait();
        }
    }

    @FXML
    void newNote(ActionEvent event) throws IOException {
        editNote = null;
        navigate(event, FXMLPage.ADD.getPage());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	List<Note> notesFromDB = noteDAO.getAllNotes();
        data.addAll(notesFromDB);
        
        FilteredList<Note> filteredData = new FilteredList<>(data, n -> true);
        notesListTable.setItems(filteredData);
        
        titleTc.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionTc.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        searchNotes.setOnKeyReleased(e -> {
            filteredData.setPredicate(n -> {
                if (searchNotes.getText() == null || searchNotes.getText().isEmpty())
                    return true;
                return n.getTitle().contains(searchNotes.getText()) ||
                       n.getDescription().contains(searchNotes.getText());
            });
        });
        
        updateNotesCount();
    }

    private void updateNotesCount() {
        int count = data.size();
        notesCount.setText(count + " Notes");
    }
}
