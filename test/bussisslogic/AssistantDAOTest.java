package bussisslogic;

import businesslogic.AssistantDAO;
import businesslogic.BusinessException;
import domain.Assistant;
import java.util.ArrayList;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author Antonio de Jesús Dominguez García
 *
 */
public class AssistantDAOTest {
    
    @Test
    public void testMeetingByRegisterAssitnat() throws BusinessException {

        AssistantDAO assistantDAO = new AssistantDAO();
        int assistantResult;
        Assistant asisstantObject = new Assistant("Lider", "Antonio de Jesús Domínguez García", 1);
        assistantResult = assistantDAO.registerAssistantByRol(asisstantObject);
        Assert.assertEquals(1, assistantResult);
        
    }
    
    @Test
    public void testMeetingByGetAssistants() throws BusinessException {
        
        AssistantDAO assistantDAO = new AssistantDAO();
        ArrayList<Assistant> arrayListAssitants;
        arrayListAssitants = assistantDAO.getAssitants(1);
        Assert.assertFalse("Prueba regresa lista de asistentes", arrayListAssitants.isEmpty());
        
    }
    
    
    @Test
    public void testMeetingBydeleteAssistants() throws BusinessException {

        AssistantDAO assistantDAO = new AssistantDAO();
        int assistantResult;
        assistantResult = assistantDAO.deleteAssistants(1);
        org.junit.Assert.assertEquals(1, assistantResult);
    }
}
