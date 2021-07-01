package bussisslogic;

import businesslogic.BusinessException;
import businesslogic.EventDAO;
import domain.Event;
import java.sql.Date;
import java.util.ArrayList;
import junit.framework.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Antonio de Jesús Dominguez García
 *
 */
public class EventDAOTest {
    
   
    
    @Test
    public void testEventByScheduleEvent() throws BusinessException {

        EventDAO event = new EventDAO();
        int eventResult;
        
        Date registrationDate = new Date((2021-1900), (3-1), 27);
        Date eventDate = new Date((2021-1900), (4-1), 03);
        Event eventObject = new Event("Hackaton", "Hackaton", registrationDate, "FEI", eventDate, "Estudiantes", "Antonio de Jesús Domínguez García");
        eventResult = event.scheduleEvent(eventObject);
        
        org.junit.Assert.assertEquals("prueba ingresar un evento", 1, eventResult);
        
    }
    
    @Test
    public void testEventByCheckEventHistory() throws BusinessException {
        
        EventDAO event = new EventDAO();
        ArrayList<Event> arrayListEvents;
        arrayListEvents = event.checkEventHistory();
        
        Assert.assertFalse("Prueba regresa lista de eventos", arrayListEvents.isEmpty());
        
    }
    
    @Test
    public void testEventByConsultEvent() throws BusinessException {
        
        EventDAO event = new EventDAO();
        Event eventResult;
        String eventExpected = "Hackaton";
        eventResult = event.consultEvent(eventExpected);
        String eventActual = eventResult.getTittle();
        assertEquals("Prueba obtener evento", eventExpected, eventActual);
    }
    
    @Test
    public void testEventByModifyEvent() throws BusinessException {
        
        EventDAO event = new EventDAO();
        int eventResult;
        
        Date registrationDate = new Date((2021-1900), (3-1), 27);
        Date eventDate = new Date((2021-1900), (4-1), 03);
        Event eventObjectNew = new Event("Seminario profesores", "Seminario", registrationDate, "FEI", eventDate, "Docentes", "Antonio de Jesús Domínguez García");
        String TitleOfTheEvent = "Hackaton";
        eventResult = event.modifyEvent(eventObjectNew,TitleOfTheEvent);
        
        org.junit.Assert.assertEquals("prueba modificar evento", 1, eventResult);
        
    }
    
}
