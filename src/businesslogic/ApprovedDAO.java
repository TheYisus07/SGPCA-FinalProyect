package businesslogic;

import dataaccess.Conexion;
import domain.Approved;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public class ApprovedDAO implements IApprovedDAO{
    
    private final Conexion CONNECTION = new Conexion();

    @Override
    public int approvedMeeting(Approved Approved) throws BusinessException{
        int result;
        result = 0;
        try {
            String query = "INSERT INTO approved (member_FullName, meeting_idMeeting) VALUES (?, ?)";
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, Approved.getMemberApprove());
            preparedStatement.setInt(2, Approved.getIdMeeting());
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
        
    }

    @Override
    public int deleteApproved(String member_FullName, int meeting_idMeeting) throws BusinessException{
        String query = "DELETE FROM approved WHERE member_FullName = ? and meeting_idMeeting = ?";
        int result = 0;
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, member_FullName);
            preparedStatement.setInt(2, meeting_idMeeting);
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        }
        return result;
    }

    @Override
    public ArrayList<Approved> getMembersApprove(int meeting_idMeeting) throws BusinessException{
        ArrayList<Approved> approvedConsultedName = new ArrayList<>();
        
        try {
            String query = "SELECT member_FullName, meeting_idMeeting FROM approved WHERE meeting_idMeeting = ?";
 
            PreparedStatement  preparedStatement  = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, meeting_idMeeting);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                approvedConsultedName.add(new Approved(resultSet.getInt("meeting_idMeeting"), resultSet.getString("member_FullName"))); 
                
            }
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return approvedConsultedName;
    }
    
}
