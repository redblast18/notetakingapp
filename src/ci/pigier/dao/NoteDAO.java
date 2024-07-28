package ci.pigier.dao;

import ci.pigier.model.Note;
import ci.pigier.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDAO {

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT * FROM notes ORDER BY REGEXP_REPLACE(title, '[0-9]', '') ASC, CAST(REGEXP_REPLACE(title, '[^0-9]', '') AS UNSIGNED) ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Note note = new Note(
                        rs.getString("title"),
                        rs.getString("description")
                );
                notes.add(note);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

    public void addNote(Note note) {
        String query = "INSERT INTO notes (title, description) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, note.getTitle());
            pstmt.setString(2, note.getDescription());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateNote(Note note) {
        String query = "UPDATE notes SET description = ? WHERE title = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, note.getDescription());
            pstmt.setString(2, note.getTitle());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteNote(String title) {
        String query = "DELETE FROM notes WHERE title = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, title);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
