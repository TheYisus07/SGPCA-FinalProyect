package businesslogic;

import dataaccess.Conexion;
import domain.Invited;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public class InvitedDAO implements IInvitedDAO{
    private final Conexion CONNECTION = new Conexion();
    
    @Override
    public int confirmAssistance(Invited invited) throws BusinessException{
        int result;
        result = 0;
        try {
            String query = "INSERT INTO invited (member_FullName, meeting_idMeeting) VALUES (?, ?)";
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, invited.getFullName());
            preparedStatement.setInt(2, invited.getIdMeeting());
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
        
    }
    
    @Override
    public int deleteAssistance(String fullName, int idMeeting) throws BusinessException{
        String query = "DELETE FROM invited WHERE member_FullName = ? and meeting_idMeeting = ?";
        int result = 0;
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, fullName);
            preparedStatement.setInt(2, idMeeting);
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        }
        return result;
    }
    
    @Override
    public ArrayList<Invited> getAssitantsName(int idMeeting) throws BusinessException{
        ArrayList<Invited> invitedConsultedName = new ArrayList<>();
        
        try {
            String query = "SELECT member_FullName, meeting_idMeeting FROM invited WHERE meeting_idMeeting = ?";
 
            PreparedStatement  preparedStatement  = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idMeeting);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                invitedConsultedName.add(new Invited(resultSet.getString("member_FullName"), resultSet.getInt("meeting_idMeeting"))); 
                
            }
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return invitedConsultedName;
    }
}
