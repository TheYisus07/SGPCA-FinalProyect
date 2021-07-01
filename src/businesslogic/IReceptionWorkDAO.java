package businesslogic;

import domain.ReceptionWork;

/**
 *
 * @author Javier Blas
 */
public interface IReceptionWorkDAO {

    /**
     * Method of adding a ReceptionWork
     * @param receptionwork ReceptionWork to add
     * @return int 1 if the ReceptionWork was added successfully
     * int 0 if it could not be added
     * @throws businesslogic.BusinessException
     */
    public int addReceptionWork(ReceptionWork receptionwork) throws BusinessException;
    
    /**
     * Method of modifying a ReceptionWork
     * @param receptionwork ReceptionWork to modify
     * @param idEvidence int to identify
     * @return int 1 if the ReceptionWork was modified successfully
     * int 0 if it could not be modified
     * @throws businesslogic.BusinessException
     */
    public int modifyReceptionWork(ReceptionWork receptionwork, int idEvidence) throws BusinessException;
    
    /**
     * Method to consult a ReceptionWork according to the idEvidence that corresponds to it
     * @param idEvidence int to check
     * @return ReceptionWork consulted according to the idEvidence
     * @throws businesslogic.BusinessException
     */
    public ReceptionWork consultReceptionWork(int idEvidence) throws BusinessException;
}
