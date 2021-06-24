package bussisslogic;

import businesslogic.AgreementDAO;
import domain.Agreement;
import java.util.ArrayList;
import org.junit.Test;

/**
 *
 * @author Antonio de Jesús Dominguez García
 *
 */   
public class AgreementDAOTest {
    
    
    @Test
    public void testAgreementByRegisterAgreement() {

        AgreementDAO agreementDAO = new AgreementDAO();
        int agreementResult;
        
        Agreement agreementObject = new Agreement("Se revisaran problemas a cordes a la tesis de Javier", "Antonio Domínguez", "Lunes 25 de abril", 1, 1);
        agreementResult = agreementDAO.registerAgreement(agreementObject);
        org.junit.Assert.assertEquals(1, agreementResult);
    }
    
    @Test
    public void testAgreementByDeleteAgreement() {
        
        AgreementDAO agreementDAO = new AgreementDAO();
        int agreementResult;
        agreementResult = agreementDAO.deleteAgreement(1);
        org.junit.Assert.assertEquals(1, agreementResult);
    }
    
    @Test
    public void testAgreementByConsultAgreement() {
        
        AgreementDAO agreementDAO = new AgreementDAO();
        ArrayList<Agreement> arrayListAgreement;
        arrayListAgreement = agreementDAO.consultAgreement(1);
        org.junit.Assert.assertFalse("Prueba regresa lista de acuerdos", arrayListAgreement.isEmpty());
        
    }
    
}
