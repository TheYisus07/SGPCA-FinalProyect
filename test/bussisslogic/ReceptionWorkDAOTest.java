package bussisslogic;

import businesslogic.BusinessException;
import businesslogic.ReceptionWorkDAO;
import domain.ReceptionWork;
import java.sql.Date;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Javier Blas
 */
public class ReceptionWorkDAOTest {
//    @Test
    public void testAddNeWReceptionWorkSuccessfully() throws BusinessException {
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        int receptionWorkExpected;
        
        Date finalDate = new Date((2021-1900), (4-1), 5);
        Date startDate = new Date((2020-1900), (4-1), 10);
        Date registrationDate = new Date((2010-1900), (8-1), 14);
        ReceptionWork receptionWorkObject = new ReceptionWork("Avenida Xalapa #220", finalDate, startDate, "Maestría", 2, "Desarrollo", "Inteligencia artificial", 104, registrationDate, "En proceso", "Sí", "Inteligencia Artificial", "Javier Alberto Calderón Blas", "LIS", null);
        receptionWorkExpected = receptionWorkDAO.addReceptionWork(receptionWorkObject);
        
        Assert.assertEquals("Registro exitoso", 1, receptionWorkExpected);
    }
    
    @Test 
    public void testModifyReceptionWorkByIdEvidence() throws BusinessException {
        ReceptionWorkDAO receptionWorkDAO =  new ReceptionWorkDAO();
        int receptionWorkResult;
 
        Date finalDate = new Date((2021-1900), (4-1), 5);
        Date startDate = new Date((2020-1900), (4-1), 10);
        Date registrationDate = new Date((2010-1900), (8-1), 14);
        ReceptionWork receptionWorkObject = new ReceptionWork("Avenida Xalapa #220", finalDate, startDate, "Maestría", 2, "Desarrollo", "Inteligencia artificial", 104, registrationDate, "Concluido", "Sí", "Inteligencia Artificial", "Javier Alberto Calderón Blas", "LIS", null);
        int idEvidence = 104;
        receptionWorkResult = receptionWorkDAO.modifyReceptionWork(receptionWorkObject, idEvidence);
        
        Assert.assertEquals("Prueba modificar trabajo recepcional", 1, receptionWorkResult);
    }
    
//    @Test
    public void testConsultReceptionWorkByIdEvidence() throws BusinessException {
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        ReceptionWork receptionWorkResult;
        int idEvidence = 104;
        receptionWorkResult = receptionWorkDAO.consultReceptionWork(idEvidence);
        int ideEvidenceActual = receptionWorkResult.getIdEvidence();
        Assert.assertEquals("Prueba obtener otro trabajo recepcional", idEvidence, ideEvidenceActual);
    } 
}
