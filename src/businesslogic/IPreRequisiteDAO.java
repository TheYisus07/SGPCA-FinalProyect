package businesslogic;

import domain.PreRequisite;
import java.util.ArrayList;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public interface IPreRequisiteDAO {
    
    /**
     * Method of register a PreRequisite
     * @param preRequisite
     * @return int 1 if the PreRequisite was registered
     * int 0 if it could not be registered
     * @throws businesslogic.BusinessException
     */
    public int registerPreRequisite(PreRequisite preRequisite) throws BusinessException;
    
    /**
     * Method of consult all PreRequisites of a meeting
     * @param meeting_idMeeting
     * @return ArrayList of PreRequisites
     * @throws businesslogic.BusinessException
     */
    public ArrayList<PreRequisite> consultPreRequisites(int meeting_idMeeting) throws BusinessException;
    
    /**
     * Method of delete aall PreRequisites of a meeting
     * @param meeting_idMeeting
     * @return int 1 if the PreRequisites was deleted
     * int 0 if it could not be deleted
     * @throws businesslogic.BusinessException
     */
    public int deletePrerequisites(int meeting_idMeeting) throws BusinessException;
}
