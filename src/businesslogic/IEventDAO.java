package businesslogic;
import domain.Event;
import java.util.ArrayList;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public interface IEventDAO {
    
    /**
     * Method to consult an Event
     * @param tittle title of the Event to consult
     * @return Event consulted
     * @throws businesslogic.BusinessException
     */
    public Event consultEvent(String tittle) throws BusinessException;
    
    /**
     * Method to schedule an Event
     * @param event Event to schedule
     * @return int 1 if the Event was scheduled
     * int 0 if it could not be scheduled
     * @throws businesslogic.BusinessException
     */
    public int scheduleEvent(Event event) throws BusinessException;
    
    /**
     * Method to modify an Event
     * @param newEvent new Event to modify
     * @param tittle title of the Event to modify
     * @return int 1 if the Event was scheduled
     * int 0 if it could not be scheduled
     * @throws businesslogic.BusinessException
     */
    public int modifyEvent(Event newEvent, String tittle) throws BusinessException;
    
    /**
     * Method to check all Events
     * @return ArrayList of Events
     * @throws businesslogic.BusinessException
     */
    public ArrayList<Event> checkEventHistory() throws BusinessException;
}
