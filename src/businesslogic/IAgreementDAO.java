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
     */
    public int registerAgreement(Agreement agreement);
    
    /**
     * Method of delete an Agreement
     * @param idMeeting identifier used to delete all the agreements to this meeting
     * @return int 1 if the Agreement was deleted
     * int 0 if it could not be deleted
     */
    public int deleteAgreement(int idMeeting);
    
    /**
     * Method of consult all the Agreement
     * @param idMeeting identifier used to consult all the agreements to this meeting
     * @return ArrayList of Agreements
     */
    public ArrayList<Agreement> consultAgreement(int idMeeting);
}
