package businesslogic;

import domain.Meeting;
import java.util.ArrayList;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public interface IMeetingDAO {
    
    /**
     * Method of consult all Meetings
     * @return ArrayList of Meetings
     */
    public ArrayList<Meeting> checkMeetingHistory();
    
    /**
     * Method of schedule a Meeting
     * @param meeting Meeting to schedule
     * @return int 1 if the Meeting was schedule
     * int 0 if it could not be schedule
     */
    public int scheduleMeeting(Meeting meeting);
    
    /**
     * Method to consult a Meeting
     * @param idMeeting identifier of the Meeting to consult
     * @return a Meeting
     */
    public Meeting searchMeeting(int idMeeting);
    
    /**
     * Method to get the las id inserted of the Meetings
     * @return int of the last Meeting inserted
     */
    public int  getLastInsertedMeetingId();
    
    /**
     * Method of update a Meeting
     * @param newMeeting new Meeting to update
     * @param idMeeting id of Meeting to update
     * @return int 1 if the Meeting was updated
     * int 0 if it could not be updated
     */
    public int updateMeeting(Meeting newMeeting, int idMeeting);
}
