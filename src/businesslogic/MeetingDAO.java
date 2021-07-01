package businesslogic;


import dataaccess.Conexion;
import domain.Meeting;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public class MeetingDAO implements IMeetingDAO {
    
    private final Conexion CONNECTION = new Conexion();

    @Override
    public ArrayList<Meeting> checkMeetingHistory() throws BusinessException{
        ArrayList<Meeting> arrayListMeetings = new ArrayList<>();
        
        try {
            CONNECTION.connect();
            String query = "SELECT idMeeting, Date, TitleOfProyect, Place, Affair, member_FullName FROM meeting";
            Statement statement = CONNECTION.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                arrayListMeetings.add(new Meeting(resultSet.getInt("idMeeting"), resultSet.getDate("Date"), resultSet.getString("TitleOfProyect"), resultSet.getString("Place"), resultSet.getString("Affair"), resultSet.getString("member_FullName"))); 
            }
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return arrayListMeetings;
    }

    @Override
    public int scheduleMeeting(Meeting meeting) throws BusinessException{
        int result;
        
        String date = (new SimpleDateFormat("yyyy-MM-dd").format(meeting.getDate()));
        result = 0;
        try {
            String query = "INSERT INTO meeting (Date, TitleOfProyect, Place, Hour, Affair, member_FullName ) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, meeting.getTitleOfProyect());
            preparedStatement.setString(3, meeting.getPlace());
            preparedStatement.setString(4, meeting.getPlace());
            preparedStatement.setString(5, meeting.getAffair());
            preparedStatement.setString(6, meeting.getFullName());
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }

    @Override
    public Meeting searchMeeting(int idMeeting) throws BusinessException{
        Meeting meetingConsulted = new Meeting();
        
        try {
            CONNECTION.connect();
            String query = "SELECT idMeeting, Date, TitleOfProyect, Place, Affair, member_FullName FROM meeting WHERE idMeeting = ?";
            PreparedStatement  preparedStatement  = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idMeeting);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                meetingConsulted.setIdMeeting(resultSet.getInt("idMeeting"));   
                meetingConsulted.setDate(resultSet.getDate("Date"));
                meetingConsulted.setTitleOfProyect(resultSet.getString("TitleOfProyect"));
                meetingConsulted.setAffair(resultSet.getString("Affair"));
                meetingConsulted.setPlace(resultSet.getString("Place"));
                meetingConsulted.setFullName(resultSet.getString("member_FullName"));            
            }
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return meetingConsulted;

    }
    
    @Override
    public int  getLastInsertedMeetingId() throws BusinessException{
        String query = "SELECT MAX(idMeeting) AS idMeeting FROM meeting";
        int lastMeetingId = 0;
        try {
            Statement statement = CONNECTION.getConnection().createStatement();            
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {                
                lastMeetingId = resultSet.getInt(1);
            }            
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return lastMeetingId;
    }

    @Override
    public int updateMeeting(Meeting newMeeting, int idMeeting) throws BusinessException{
        int result;
        
        String date = (new SimpleDateFormat("yyyy-MM-dd").format(newMeeting.getDate()));
        result = 0;
        try {
            String query = "UPDATE meeting SET Date = ?, TitleOfProyect = ?, Place = ?, Affair = ?, member_FullName = ? WHERE idMeeting = ?";
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, newMeeting.getTitleOfProyect());
            preparedStatement.setString(3, newMeeting.getPlace());
            preparedStatement.setString(4, newMeeting.getAffair());
            preparedStatement.setString(5, newMeeting.getFullName());
            preparedStatement.setInt(6, idMeeting);
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }
}
