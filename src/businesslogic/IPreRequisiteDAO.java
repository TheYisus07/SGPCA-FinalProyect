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
     */
    public int registerPreRequisite(PreRequisite preRequisite);
    
    /**
     * Method of consult all PreRequisites of a meeting
     * @param meeting_idMeeting
     * @return ArrayList of PreRequisites
     */
    public ArrayList<PreRequisite> consultPreRequisites(int meeting_idMeeting);
    
    /**
     * Method of delete aall PreRequisites of a meeting
     * @param meeting_idMeeting
     * @return int 1 if the PreRequisites was deleted
     * int 0 if it could not be deleted
     */
    public int deletePrerequisites(int meeting_idMeeting);
}
