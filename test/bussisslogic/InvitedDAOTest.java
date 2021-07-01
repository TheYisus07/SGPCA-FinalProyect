package bussisslogic;

import businesslogic.BusinessException;
import businesslogic.InvitedDAO;
import domain.Invited;
import java.util.ArrayList;
import org.junit.Test;

/**
 *
 * @author Antonio de Jesús Dominguez García
 *
 */
public class InvitedDAOTest {
    
    @Test
    public void testMeetingByConfirmAssistance() throws BusinessException {

        InvitedDAO invitedDAO = new InvitedDAO();
        int invitedResult;
        
        Invited invitedObject = new Invited("Antonio de Jesús Domínguez García", 1);
        invitedResult = invitedDAO.confirmAssistance(invitedObject);
        org.junit.Assert.assertEquals(1, invitedResult);
    }
    
    @Test
    public void testMeetingByDeleteAssistance() throws BusinessException {
        
        InvitedDAO invitedDAO = new InvitedDAO();
        int invitedResult;
        invitedResult = invitedDAO.deleteAssistance("Antonio de Jesús Domínguez García", 1);
        org.junit.Assert.assertEquals(1, invitedResult);
    }
    
    @Test
    public void testMeetingByGetAssitantsName() throws BusinessException {
        
        InvitedDAO invitedDAO = new InvitedDAO();
        ArrayList<Invited> arrayListInvited;
        arrayListInvited = invitedDAO.getAssitantsName(1);
        org.junit.Assert.assertFalse("Prueba regresa lista de invitados", arrayListInvited.isEmpty());
        
    }
}
