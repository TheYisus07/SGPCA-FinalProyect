package businesslogic;


import dataaccess.Conexion;
import domain.Event;
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

public class EventDAO implements IEventDAO{
    private final Conexion CONNECTION = new Conexion();

    @Override
    public Event consultEvent(String tittle) throws BusinessException{
        Event eventConsulted = new Event();
        
        try {
            CONNECTION.connect();
            String query = "SELECT Title, Type, RegistrationDate, Place, EventDate, Privacy, member_FullName FROM event WHERE Title = ?";
            PreparedStatement  preparedStatement  = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, tittle);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                eventConsulted.setTittle(resultSet.getString("Title"));
                eventConsulted.setResponsable(resultSet.getString("member_FullName"));
                eventConsulted.setType(resultSet.getString("Type"));
                eventConsulted.setPlace(resultSet.getString("Place"));
                eventConsulted.setPrivacy(resultSet.getString("Privacy"));
                eventConsulted.setRegistrationDate(resultSet.getDate("RegistrationDate"));
                eventConsulted.setEventDate(resultSet.getDate("EventDate"));
            }
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return eventConsulted;
    }

    @Override
    public int scheduleEvent(Event event) throws BusinessException{
        String registrationDate = (new SimpleDateFormat("yyyy-MM-dd").format(event.getRegistrationDate()));
        String eventDate = (new SimpleDateFormat("yyyy-MM-dd").format(event.getEventDate()));
        int result = 0;
        try {
            String query = "INSERT INTO event (Title, Type, RegistrationDate, Place, EventDate, Privacy, member_FullName) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, event.getTittle());
            preparedStatement.setString(2, event.getType());
            preparedStatement.setString(3,  registrationDate);
            preparedStatement.setString(4, event.getPlace());
            preparedStatement.setString(5,  eventDate);
            preparedStatement.setString(6, event.getPrivacy());
            preparedStatement.setString(7, event.getResponsable());
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException ) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }
    

    @Override
    public int modifyEvent(Event newEvent, String tittle) throws BusinessException{
        int result = 0;
        String registrationDate = (new SimpleDateFormat("yyyy-MM-dd").format(newEvent.getRegistrationDate()));
        String eventDate = (new SimpleDateFormat("yyyy-MM-dd").format(newEvent.getEventDate()));
        try {
            String query = "UPDATE event SET Title = ?, Type = ?, RegistrationDate = ?, Place = ?, EventDate = ?, Privacy = ?, member_FullName = ? WHERE Title = ?";
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, newEvent.getTittle());
            preparedStatement.setString(2, newEvent.getType());
            preparedStatement.setString(3,  registrationDate);
            preparedStatement.setString(4, newEvent.getPlace());
            preparedStatement.setString(5,  eventDate);
            preparedStatement.setString(6, newEvent.getPrivacy());
            preparedStatement.setString(7, newEvent.getResponsable());
            preparedStatement.setString(8, tittle);
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }  
        
        return result;
    }
    
    @Override
    public ArrayList<Event> checkEventHistory() throws BusinessException{
        ArrayList<Event> arrayListEvents = new ArrayList<>();
        
        try {
            CONNECTION.connect();
            String query = "SELECT Title, Type, RegistrationDate, Place, EventDate, Privacy, member_FullName FROM event";
            Statement statement = CONNECTION.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                arrayListEvents.add(new Event(resultSet.getString("Title"), resultSet.getString("Type"), resultSet.getDate("RegistrationDate"), resultSet.getString("Place"), resultSet.getDate("EventDate"), resultSet.getString("Privacy"), resultSet.getString("member_FullName"))); 
            }
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return arrayListEvents;
    }
}
