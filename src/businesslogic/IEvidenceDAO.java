package businesslogic;

import domain.Evidence;
import java.util.ArrayList;

/**
 *
 * @author daniCV
 */

public interface IEvidenceDAO {
    
    /**
     * Method of consulting the list of all registered Evidences
     * @return Arraylist of Evidences
     * @throws businesslogic.BusinessException
     */
    public ArrayList<Evidence> consultEvidenceList() throws BusinessException;
    
    /**
     * Method to get the id of the last inserted Evidence
     * @return  evidenceId int
     * @throws businesslogic.BusinessException
     */
    public int getLastInsertEvidenceId() throws BusinessException;
    
    /**
     * Method of adding a Evidence without an associated project
     * @param evidence Evidence to add
     * @return int 1 if the Evidence was added successfully
     * int 0 if it could not be added
     * @throws businesslogic.BusinessException
     */
    public int addNewEvidenceWithoutProject(Evidence evidence) throws BusinessException;
        
    /**
     * Method of adding a Evidence with an associated project
     * @param evidence Evidence to add
     * @return int 1 if the Evidence was added successfully
     * int 0 if it could not be added
     * @throws businesslogic.BusinessException
     */
    public int addNewEvidenceWithProject(Evidence evidence) throws BusinessException;
    
    /**
     * Method to consult a Evidence according to the ID that corresponds to it
     * @param idEvidence int to check
     * @return Evidence consulted according to the id
     * @throws businesslogic.BusinessException
     */
    public Evidence consultEvidenceById(int idEvidence) throws BusinessException;
    
    /**
     * Method to delete a Evidence
     * @param idEvidence int to remove 
     * @return int 1 if the Evidence was eliminated successfully
     * int 0 if it could not be eliminated
     * @throws businesslogic.BusinessException
     */   
    public int deleteEvidence(int idEvidence) throws BusinessException;
    
    /**
     * Method of modifying a Evidence
     * @param evidence Evidence to modify
     * @param idEvidence int to identify
     * @return int 1 if the Evidence was modified successfully
     * int 0 if it could not be modified
     * @throws businesslogic.BusinessException
     */
    public int modifyEvidence(Evidence evidence, int idEvidence) throws BusinessException;
}
