package businesslogic;

import dataaccess.Conexion;
import domain.ChapterBook;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author daniCV
 */

public class ChapterBookDAO implements IChapterBookDAO {
    
    private final Conexion CONNECTION = new Conexion();

    @Override
    public int addNewChapterBook(ChapterBook chapterBook) throws BusinessException {
        String query = "INSERT INTO chapterbook (evidence_idEvidence, YearPublication, Author, EndPage, HomePage) VALUES (?, ?, ?, ?, ?)";        
        int result = 0;
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, chapterBook.getIdEvidence());
            preparedStatement.setInt(2, chapterBook.getYearPublication());
            preparedStatement.setString(3, chapterBook.getAuthor());
            preparedStatement.setInt(4, chapterBook.getEndPage());
            preparedStatement.setInt(5, chapterBook.getHomePage());                   
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }

    @Override
    public int modifyArticle(ChapterBook chapterBook, int idEvidence) throws BusinessException {
        String query = "UPDATE chapterbook SET YearPublication = ?, Author = ?, EndPage = ?, HomePage = ? WHERE evidence_idEvidence = ?";
        int result = 0;
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, chapterBook.getYearPublication());
            preparedStatement.setString(2, chapterBook.getAuthor());
            preparedStatement.setInt(3, chapterBook.getEndPage());
            preparedStatement.setInt(4, chapterBook.getHomePage());
            preparedStatement.setInt(5, idEvidence);
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }

    @Override
    public ChapterBook consultChapterBook(int idEvidence) throws BusinessException {
        String query = "SELECT evidence_idEvidence, YearPublication, Author, EndPage, HomePage FROM chapterbook WHERE evidence_idEvidence = ?";
        ChapterBook chapterBook = new ChapterBook();
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idEvidence);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                chapterBook.setIdEvidence(resultSet.getInt("evidence_idEvidence"));
                chapterBook.setYearPublication(resultSet.getInt("YearPublication"));
                chapterBook.setAuthor(resultSet.getString("Author"));
                chapterBook.setEndPage(resultSet.getInt("EndPage"));
                chapterBook.setHomePage(resultSet.getInt("HomePage"));
            }  
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return chapterBook;
    }
}