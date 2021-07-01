package businesslogic;

import domain.Invited;
import java.util.ArrayList;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public interface IInvitedDAO {
    
    /**
     * Method of register an Invited
     * @param invited Invited to register
     * @return int 1 if the Invited was registered
     * int 0 if it could not be registered
     * @throws businesslogic.BusinessException
     */
    public int confirmAssistance(Invited invited) throws BusinessException;
    
    /**
     * Method of delete an Invited
     * @param fullName
     * @param idMeeting
     * @return int 1 if the Invited was deleted
     * int 0 if it could not be deleted
     * @throws businesslogic.BusinessException
     */
    public int deleteAssistance(String fullName, int idMeeting) throws BusinessException;
    
    /**
     * Method of consult all the Invited
     * @param idMeeting identifier used to consult all the Inviteds to this meeting
     * @return ArrayList of Invited
     * @throws businesslogic.BusinessException
     */
    public ArrayList<Invited> getAssitantsName(int idMeeting) throws BusinessException;
}
