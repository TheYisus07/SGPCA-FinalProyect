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
     * @throws businesslogic.BusinessException
     */
    public ArrayList<Meeting> checkMeetingHistory() throws BusinessException;
    
    /**
     * Method of schedule a Meeting
     * @param meeting Meeting to schedule
     * @return int 1 if the Meeting was schedule
     * int 0 if it could not be schedule
     * @throws businesslogic.BusinessException
     */
    public int scheduleMeeting(Meeting meeting) throws BusinessException;
    
    /**
     * Method to consult a Meeting
     * @param idMeeting identifier of the Meeting to consult
     * @return a Meeting
     * @throws businesslogic.BusinessException
     */
    public Meeting searchMeeting(int idMeeting) throws BusinessException;
    
    /**
     * Method to get the las id inserted of the Meetings
     * @return int of the last Meeting inserted
     * @throws businesslogic.BusinessException
     */
    public int  getLastInsertedMeetingId() throws BusinessException;
    
    /**
     * Method of update a Meeting
     * @param newMeeting new Meeting to update
     * @param idMeeting id of Meeting to update
     * @return int 1 if the Meeting was updated
     * int 0 if it could not be updated
     * @throws businesslogic.BusinessException
     */
    public int updateMeeting(Meeting newMeeting, int idMeeting) throws BusinessException;
}
