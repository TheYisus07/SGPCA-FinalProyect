package businesslogic;

import domain.Agreement;
import java.util.ArrayList;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public interface IAgreementDAO {
    
    /**
     * Method of register an Agreement
     * @param agreement Agreement to register
     * @return int 1 if the Agreement was registered
     * int 0 if it could not be registered
     * @throws businesslogic.BusinessException
     */
    public int registerAgreement(Agreement agreement) throws BusinessException;
    
    /**
     * Method of delete an Agreement
     * @param idMeeting identifier used to delete all the agreements to this meeting
     * @return int 1 if the Agreement was deleted
     * int 0 if it could not be deleted
     * @throws businesslogic.BusinessException
     */
    public int deleteAgreement(int idMeeting) throws BusinessException;
    
    /**
     * Method of consult all the Agreement
     * @param idMeeting identifier used to consult all the agreements to this meeting
     * @return ArrayList of Agreements
     * @throws businesslogic.BusinessException
     */
    public ArrayList<Agreement> consultAgreement(int idMeeting) throws BusinessException;
}
