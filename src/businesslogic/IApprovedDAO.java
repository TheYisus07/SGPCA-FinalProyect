package businesslogic;

import domain.Approved;
import java.util.ArrayList;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public interface IApprovedDAO {
    
    /**
     * Method of insert a member who Approved a minute
     * @param Approved member who has approved a minute
     * @return int 1 if the Approved was registered
     * int 0 if it could not be registered
     * @throws businesslogic.BusinessException
     */
    public int approvedMeeting(Approved Approved) throws BusinessException;
    
    /**
     * Method of delete a member who Approved a minute
     * @param member_FullName name of the member to delete
     * @param meeting_idMeeting identifier of the minute where is the member Approved
     * @return int 1 if the Approved was deleted
     * int 0 if it could not be deleted
     * @throws businesslogic.BusinessException
     */
    public int deleteApproved(String member_FullName, int meeting_idMeeting) throws BusinessException;
    
    /**
     * Method to get all the member who has approved this minute
     * @param meeting_idMeeting identifier of the minute
     * @return ArrayList of Approved
     * @throws businesslogic.BusinessException
     */
    public ArrayList<Approved> getMembersApprove(int meeting_idMeeting) throws BusinessException;
}
