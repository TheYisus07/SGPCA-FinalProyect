package businesslogic;

import dataaccess.Conexion;
import domain.Minute;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public class MinuteDAO implements IMinuteDAO{
    
    private final Conexion CONNECTION = new Conexion();

    @Override
    public int generateMinute(Minute minute) throws BusinessException{
        int result;
        result = 0;
        try {
            String query = "INSERT INTO minute (Note, Pending, meeting_idMeeting) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, minute.getNote());
            preparedStatement.setString(2, minute.getPending());
            preparedStatement.setInt(3, minute.getIdMeeting());
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }
    
    @Override
    public Minute consultMinute(int idMeeting) throws BusinessException{
        Minute minuteConsulted = new Minute();
        
        try {
            CONNECTION.connect();
            String query = "SELECT meeting_idMeeting, Note, Pending FROM minute WHERE meeting_idMeeting = ?";
            PreparedStatement  preparedStatement  = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idMeeting);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                minuteConsulted.setIdMeeting(resultSet.getInt("meeting_idMeeting"));   
                minuteConsulted.setNote(resultSet.getString("Note"));    
                minuteConsulted.setPending(resultSet.getString("Pending"));          
            }
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return minuteConsulted;

    }

    @Override
    public int updateMinute(int idMeeting, Minute minute) throws BusinessException{
        int result;
        
        result = 0;
        try {
            String query = "UPDATE minute SET meeting_idMeeting = ?, Note = ?, Pending = ? WHERE meeting_idMeeting = ?";
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, minute.getIdMeeting());
            preparedStatement.setString(2, minute.getNote());
            preparedStatement.setString(3, minute.getPending());
            preparedStatement.setInt(4, idMeeting);
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        
        return result;
    }
    
}
