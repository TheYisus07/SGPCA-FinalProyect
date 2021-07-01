package businesslogic;

import dataaccess.Conexion;
import domain.PreRequisite;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public class PreRequisiteDAO implements IPreRequisiteDAO{
    private final Conexion CONNECTION = new Conexion();
    
    @Override
    public int registerPreRequisite(PreRequisite preRequisite) throws BusinessException{
        int result;
        result = 0;
        try {
            String query = "INSERT INTO prerequisite (Description, NameParticipant, meeting_idMeeting) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, preRequisite.getDescription());
            preparedStatement.setString(2, preRequisite.getNameParticipant());
            preparedStatement.setInt(3, preRequisite.getIdMeeting());
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }

    @Override
    public ArrayList<PreRequisite> consultPreRequisites(int meeting_idMeeting) throws BusinessException{
        ArrayList<PreRequisite> preRequisiteConsulted = new ArrayList<>();
        
        try {
            CONNECTION.connect();
            String query = "SELECT Description, NameParticipant, meeting_idMeeting FROM prerequisite WHERE meeting_idMeeting = ?";
            PreparedStatement  preparedStatement  = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, meeting_idMeeting);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                preRequisiteConsulted.add(new PreRequisite(resultSet.getString("Description"), resultSet.getString("NameParticipant"), resultSet.getInt("meeting_idMeeting"))); 
            }
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return preRequisiteConsulted;
    }

    @Override
    public int deletePrerequisites(int meeting_idMeeting) throws BusinessException{
        String query = "DELETE FROM prerequisite WHERE meeting_idMeeting = ?";
        int result = 0;
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, meeting_idMeeting);
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        }
        return result;
    }
}
