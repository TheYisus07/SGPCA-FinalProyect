package businesslogic;

import domain.Assistant;
import java.util.ArrayList;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public interface IAssistantDAO {
    
    /**
     * Method of register an Assistant
     * @param assistant Assistant to register
     * @return int 1 if the Assistant was registered
     * int 0 if it could not be registered
     * @throws businesslogic.BusinessException
     */
    public int registerAssistantByRol(Assistant assistant) throws BusinessException;
    
    /**
     * Method of delete an Assistants
     * @param meeting_idMeeting identifier used to consult all the Assistants to this meeting
     * @return ArrayList of Assistants
     * @throws businesslogic.BusinessException
     */
    public ArrayList<Assistant> getAssitants(int meeting_idMeeting) throws BusinessException;
    
    /**
     * Method of consult all the Assistants
     * @param meeting_idMeeting identifier used to delete all the Assistants to this meeting
     * @return int 1 if the Assistants were deleted
     * int 0 if it could not be deleted
     * @throws businesslogic.BusinessException
     */
    public int deleteAssistants(int meeting_idMeeting) throws BusinessException;
    
}
