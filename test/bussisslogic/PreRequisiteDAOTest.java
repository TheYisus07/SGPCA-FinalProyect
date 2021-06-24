package bussisslogic;
        
import businesslogic.PreRequisiteDAO;
import domain.PreRequisite;
import java.util.ArrayList;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public class PreRequisiteDAOTest {

    
    @Test
    public void testMeetingByRegisterPreRequisite() {

        PreRequisiteDAO preRequisiteDAO = new PreRequisiteDAO();
        int preRequisiteResult;        
        PreRequisite preRequisiteObject = new PreRequisite("Terminar DAO's", "Antonio de Jesús Domínguez García", 1);
        preRequisiteResult = preRequisiteDAO.registerPreRequisite(preRequisiteObject);
        org.junit.Assert.assertEquals(1, preRequisiteResult);
        
    }
    
    @Test
    public void testMeetingByConsultPreRequisitesy() {
        
        PreRequisiteDAO preRequisiteDAO = new PreRequisiteDAO();
        ArrayList<PreRequisite> arrayListPreRequisites;
        arrayListPreRequisites = preRequisiteDAO.consultPreRequisites(1);
        Assert.assertFalse("Prueba regresa lista de pre-requisitos", arrayListPreRequisites.isEmpty());
        
    }
    
    
    @Test
    public void testMeetingByDeletePrerequisites() {

        PreRequisiteDAO preRequisiteDAO = new PreRequisiteDAO();
        int preRequisiteResult;
        preRequisiteResult = preRequisiteDAO.deletePrerequisites(1);
        org.junit.Assert.assertEquals(1, preRequisiteResult);
    }
}
