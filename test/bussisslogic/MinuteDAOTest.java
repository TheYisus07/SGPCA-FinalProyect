package bussisslogic;

import businesslogic.MinuteDAO;
import domain.Minute;
import org.junit.Test;

/**
 *
 * @author Antonio de Jesús Dominguez García
 *
 */
public class MinuteDAOTest {
    
    @Test
    public void testMinuteByGenerateMinute() {

        MinuteDAO minuteDAO = new MinuteDAO();
        int minuteResult;
        
        Minute meetingObject = new Minute("Notas", "Pendientes", 1);
        minuteResult = minuteDAO.generateMinute(meetingObject);
        org.junit.Assert.assertEquals(1, minuteResult);
        
    }
    
    @Test
    public void testMeetingByConsultMinute() {

        MinuteDAO minuteDAO = new MinuteDAO();
        Minute minuteResult;
        int minuteExpected = 1;
        minuteResult = minuteDAO.consultMinute(minuteExpected);
        int minuteActual = minuteResult.getIdMeeting();
        org.junit.Assert.assertEquals(minuteExpected, minuteActual);
        
    }
    
}
