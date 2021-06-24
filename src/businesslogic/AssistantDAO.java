package businesslogic;

import dataaccess.Conexion;
import domain.Assistant;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public class AssistantDAO implements IAssistantDAO{
    private final Conexion CONNECTION = new Conexion();


    @Override
    public int registerAssistantByRol(Assistant assistant) {
        int result;
        result = 0;
        try {
            String query = "INSERT INTO assistant (Rol, member_FullName, meeting_idMeeting) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, assistant.getRol());
            preparedStatement.setString(2, assistant.getFullName());
            preparedStatement.setInt(3, assistant.getIdMeeting());
            result = preparedStatement.executeUpdate();
        } catch (SQLException ex ) {
            Logger.getLogger(AssistantDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }

    @Override
    public ArrayList<Assistant> getAssitants(int meeting_idMeeting) {
        ArrayList<Assistant> assistantConsulted = new ArrayList<>();
        
        try {
            String query = "SELECT Rol, member_FullName, meeting_idMeeting FROM assistant WHERE meeting_idMeeting = ?";
 
            PreparedStatement  preparedStatement  = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, meeting_idMeeting);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                assistantConsulted.add(new Assistant(resultSet.getString("Rol"), resultSet.getString("member_FullName"), resultSet.getInt("meeting_idMeeting"))); 
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AssistantDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CONNECTION.disconnect();
        }
        return assistantConsulted;
    }

    @Override
    public int deleteAssistants(int meeting_idMeeting) {
        String query = "DELETE FROM assistant WHERE meeting_idMeeting = ?";
        int result = 0;
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, meeting_idMeeting);
            result = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AssistantDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
}
