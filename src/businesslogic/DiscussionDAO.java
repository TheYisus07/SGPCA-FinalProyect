package businesslogic;

import dataaccess.Conexion;
import domain.Discussion;
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
public class DiscussionDAO implements IDiscussionDAO{
    
    private final Conexion CONNECTION = new Conexion();

    @Override
    public int registerDiscussion(Discussion discussion) {
        int result;
        result = 0;
        try {
            String query = "INSERT INTO discussion (EstimatedTime, Number, Description, LeaderName, meeting_idMeeting) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, discussion.getEstimatedTime());
            preparedStatement.setInt(2, discussion.getNumber());
            preparedStatement.setString(3, discussion.getDescription());
            preparedStatement.setString(4, discussion.getLeaderName());
            preparedStatement.setInt(5, discussion.getIdMeeting());
            result = preparedStatement.executeUpdate();
        } catch (SQLException ex ) {
            Logger.getLogger(DiscussionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }
    
    @Override
    public int updateDiscussion(Discussion discussion) {
        int result;
        result = 0;
        try {
            String query = "INSERT INTO discussion (StartHour, EndHour, EstimatedTime, Number, Description, LeaderName, meeting_idMeeting) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, discussion.getStartHour());
            preparedStatement.setString(2, discussion.getEndHour());
            preparedStatement.setInt(3, discussion.getEstimatedTime());
            preparedStatement.setInt(4, discussion.getNumber());
            preparedStatement.setString(5, discussion.getDescription());
            preparedStatement.setString(6, discussion.getLeaderName());
            preparedStatement.setInt(7, discussion.getIdMeeting());
            result = preparedStatement.executeUpdate();
        } catch (SQLException ex ) {
            Logger.getLogger(DiscussionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }

    @Override
    public ArrayList<Discussion> consultDiscussions(int meeting_idMeeting) {
        ArrayList<Discussion> discussionConsulted = new ArrayList<>();
        
        try {
            CONNECTION.connect();
            String query = "SELECT EstimatedTime, Number, Description, LeaderName, meeting_idMeeting FROM discussion WHERE meeting_idMeeting = ?";
            PreparedStatement  preparedStatement  = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, meeting_idMeeting);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                discussionConsulted.add(new Discussion(resultSet.getInt("EstimatedTime"), resultSet.getInt("Number"), resultSet.getInt("meeting_idMeeting"), resultSet.getString("Description"), resultSet.getString("LeaderName"))); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiscussionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CONNECTION.disconnect();
        }
        return discussionConsulted;
    }
    
    @Override
    public ArrayList<Discussion> consultDiscussionsUpdated(int meeting_idMeeting) {
        ArrayList<Discussion> discussionConsulted = new ArrayList<>();
        
        try {
            CONNECTION.connect();
            String query = "SELECT StartHour, EndHour, EstimatedTime, Number, Description, LeaderName, meeting_idMeeting FROM discussion WHERE meeting_idMeeting = ?";
            PreparedStatement  preparedStatement  = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, meeting_idMeeting);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                discussionConsulted.add(new Discussion(resultSet.getInt("EstimatedTime"), resultSet.getInt("Number"), resultSet.getInt("meeting_idMeeting"), resultSet.getString("Description"), resultSet.getString("LeaderName"), resultSet.getString("StartHour"), resultSet.getString("EndHour"))); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiscussionDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CONNECTION.disconnect();
        }
        return discussionConsulted;
    }

    @Override
    public int deleteDiscussions(int meeting_idMeeting) {
        String query = "DELETE FROM discussion WHERE meeting_idMeeting = ?";
        int result = 0;
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, meeting_idMeeting);
            result = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DiscussionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    
    
}
