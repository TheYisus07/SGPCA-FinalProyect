package businesslogic;

import domain.Discussion;
import java.util.ArrayList;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public interface IDiscussionDAO {
    
    /**
     * Method of register a Discussion
     * @param discussion
     * @return int 1 if the Agreement was registered
     * int 0 if it could not be registered
     */
    public int registerDiscussion(Discussion discussion) throws BusinessException;
    
    /**
     * Method of update a Discussion
     * @param discussion
     * @return int 1 if the Agreement was updated
     * int 0 if it could not be updated
     * @throws businesslogic.BusinessException
     */
    public int updateDiscussion(Discussion discussion) throws BusinessException;
    
    /**
     * Method of consult all Discussions
     * @param meeting_idMeeting
     * @return ArrayList of Discussions
     * @throws businesslogic.BusinessException
     */
    public ArrayList<Discussion> consultDiscussions(int meeting_idMeeting) throws BusinessException;
    
    /**
     * Method of consult all Discussions who have all te data
     * @param meeting_idMeeting
     * @return ArrayList of Discussions
     * @throws businesslogic.BusinessException
     */
    public ArrayList<Discussion> consultDiscussionsUpdated(int meeting_idMeeting) throws BusinessException;
    
    /**
     * Method of delete all Discussions
     * @param meeting_idMeeting
     * @return int 1 if the Agreement was deleted
     * int 0 if it could not be deleted
     * @throws businesslogic.BusinessException
     */
    public int deleteDiscussions(int meeting_idMeeting) throws BusinessException;
}
