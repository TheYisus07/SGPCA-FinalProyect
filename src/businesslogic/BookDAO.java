package businesslogic;

import dataaccess.Conexion;
import domain.Book;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * 
 * @author daniCV
 */

public class BookDAO implements IBookDAO {
    
    private final Conexion CONNECTION = new Conexion();
    
    @Override
    public int addNewBook(Book book) throws BusinessException {
        String query = "INSERT INTO book (evidence_idEvidence, Editorial, EndDate, StartDate, PublicationDate, EditionNumber) VALUES (?, ?, ?, ?, ?, ?)";
        String endDate = (new SimpleDateFormat("yyyy-MM-dd").format(book.getEndDate()));
        String StartDate = (new SimpleDateFormat("yyyy-MM-dd").format(book.getStartDate()));
        int result = 0;
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, book.getIdEvidence());
            preparedStatement.setString(2, book.getEditorial());
            preparedStatement.setString(3, endDate);
            preparedStatement.setString(4, StartDate);
            preparedStatement.setInt(5, book.getPublicationYear());
            preparedStatement.setInt(6, book.getEditionNumber());         
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }

    @Override
    public int modifyBook(Book book, int idEvidence) throws BusinessException {
        String query = "UPDATE book SET Editorial = ?, EndDate = ?, StartDate = ?, PublicationDate = ?, EditionNumber = ? WHERE evidence_idEvidence = ?";
        String endDate = (new SimpleDateFormat("yyyy-MM-dd").format(book.getEndDate()));
        String startDate = (new SimpleDateFormat("yyyy-MM-dd").format(book.getStartDate()));
        int result = 0;
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, book.getEditorial());
            preparedStatement.setString(2, endDate);
            preparedStatement.setString(3, startDate);
            preparedStatement.setInt(4, book.getPublicationYear());
            preparedStatement.setInt(5, book.getEditionNumber());
            preparedStatement.setInt(6, idEvidence);
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }

    @Override
    public Book consultBook(int idEvidence) throws BusinessException {
        String query = "SELECT evidence_idEvidence, Editorial, EndDate, StartDate, PublicationDate, EditionNumber FROM book WHERE evidence_idEvidence = ?";
        Book book = new Book();
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idEvidence);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                book.setIdEvidence(resultSet.getInt("evidence_idEvidence"));
                book.setEditorial(resultSet.getString("Editorial"));
                book.setEndDate(resultSet.getDate("EndDate"));
                book.setStartDate(resultSet.getDate("StartDate"));
                book.setPublicationYear(resultSet.getInt("PublicationDate"));
                book.setEditionNumber(resultSet.getInt("EditionNumber"));
            }  
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return book;
    }
}