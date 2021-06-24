package bussisslogic;

import businesslogic.MeetingDAO;
import domain.Meeting;
import java.sql.Date;
import java.util.ArrayList;
import org.junit.Test;

/**
 *
 * @author Antonio de Jesús Dominguez García
 *
 */
public class MeetingDAOTest {
    
    @Test
    public void testMeetingByScheduleMeeting() {

        MeetingDAO meeting = new MeetingDAO();
        int meetingResult;
        
        Date meetingDate = new Date((2021-1900), (7-1), 03);
        Meeting meetingObject = new Meeting(meetingDate, "Reunión tripleJ", "FEI", "Resolver dudas del proyecto", "Antonio de Jesús Domínguez García" );
        meetingResult = meeting.scheduleMeeting(meetingObject);
        org.junit.Assert.assertEquals(1, meetingResult);
        
    }
    
    @Test
    public void testMeetingByCheckMeetingHistory() {
        
        MeetingDAO meeting = new MeetingDAO();
        ArrayList<Meeting> arrayListMeetings;
        arrayListMeetings = meeting.checkMeetingHistory();
        org.junit.Assert.assertFalse("Prueba regresa lista de reuniónes", arrayListMeetings.isEmpty());
        
    }
    
    @Test
    public void testMeetingByConsultMeeting() {
        
        MeetingDAO meeting = new MeetingDAO();
        Meeting meetingResult;
        int meetingExpected = 1;
        meetingResult = meeting.searchMeeting(meetingExpected);
        int meetingActual = meetingResult.getIdMeeting();
        org.junit.Assert.assertEquals(meetingExpected, meetingActual);
    }
    
    @Test
    public void testMeetingByGetLastInsertedId() {
        
        MeetingDAO meetingDAO = new MeetingDAO();
        
        int lastIdInserted = meetingDAO.getLastInsertedMeetingId();
        
        org.junit.Assert.assertEquals(1, lastIdInserted);
    }
}
