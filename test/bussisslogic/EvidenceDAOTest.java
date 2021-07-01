package bussisslogic;
        
import businesslogic.BusinessException;
import businesslogic.EvidenceDAO;
import domain.Evidence;
import java.util.ArrayList;
import java.util.Date;
import org.junit.Test;
        
        
/**
 *
 * @author Antonio de Jesús Domínguez García
 */

public class EvidenceDAOTest {
    
    @Test
    public void testEvidenceByAddNewEvidenceWithoutProject() throws BusinessException {
        EvidenceDAO evidenceDAO = new EvidenceDAO();
        Date evidenceDate = new Date((2021-1900), (7-1), 03);
        Evidence evidenceObject = new Evidence(1, evidenceDate, "Avalada", "Alto", "Investigación de inteligencia artificial", "José Daniel Camarillo Villa", "LIS");
        int evidenceResult = evidenceDAO.addNewEvidenceWithoutProject(evidenceObject);
        org.junit.Assert.assertEquals(1, evidenceResult);
        
    }
    
    @Test
    public void testEvidenceByAddNewEvidenceWithProject() throws BusinessException {
        EvidenceDAO evidenceDAO = new EvidenceDAO();
        int evidenceResult;
        Date evidenceDate = new Date((2021-1900), (7-1), 03);
        Evidence evidenceObject = new Evidence(2, evidenceDate, "Avalada", "Alto", "Investigación de inteligencia artificial", "José Daniel Camarillo Villa", "LIS","Python lenguaje máquina");
        evidenceResult = evidenceDAO.addNewEvidenceWithProject(evidenceObject);
        org.junit.Assert.assertEquals(1, evidenceResult);
        
    }
    
    @Test
    public void testEvidenceByConsultEvidenceList() throws BusinessException {        
        EvidenceDAO evidenceDAO = new EvidenceDAO();
        ArrayList<Evidence> arrayListEvidence;
        arrayListEvidence = evidenceDAO.consultEvidenceList();
        org.junit.Assert.assertFalse(arrayListEvidence.isEmpty());        
    }
    
    @Test
    public void testEvidenceByConsultEvidenceById() throws BusinessException {        
        EvidenceDAO evidenceDAO = new EvidenceDAO();
        Evidence evidenceResult;
        int evidenceExpected = 2;
        evidenceResult = evidenceDAO.consultEvidenceById(evidenceExpected);
        int evidenceActual = evidenceResult.getIdEvidence();
        org.junit.Assert.assertEquals(evidenceExpected, evidenceActual);
    }
    
    @Test
    public void testEvidenceByGetLastInsertedEvidenceId() throws BusinessException { 
        EvidenceDAO evidenceDAO = new EvidenceDAO();
        int lastIdInserted = evidenceDAO.getLastInsertEvidenceId();
        org.junit.Assert.assertEquals(2, lastIdInserted);
    }
    
    
    @Test
    public void testEvidenceByDeleteEvidence() throws BusinessException {
        EvidenceDAO evidenceDAO = new EvidenceDAO();
        int evidenceResult;
        evidenceResult = evidenceDAO.deleteEvidence(1);
        org.junit.Assert.assertEquals(1, evidenceResult);
    }
    
    @Test
    public void testEventByModifyEvent() throws BusinessException {        
        EvidenceDAO evidenceDAO = new EvidenceDAO();                 
        Date evidenceDate = new Date((2021-1900), (3-1), 27);
        Evidence evidenceObject = new Evidence(2, evidenceDate, "Asignada", "Alto", "Investigación de LIS", "José Daniel Camarillo Villa", "LIS", "Python lenguaje máquina");
        int evidenceResult = evidenceDAO.modifyEvidence(evidenceObject, 2);        
        org.junit.Assert.assertEquals(1, evidenceResult);        
    }
}