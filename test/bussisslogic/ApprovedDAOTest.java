package bussisslogic;

import businesslogic.ApprovedDAO;
import businesslogic.BusinessException;
import domain.Approved;
import java.util.ArrayList;
import org.junit.Test;

/**
 *
 * @author Antonio de Jesús Dominguez García
 *
 */
public class ApprovedDAOTest {
    
    @Test
    public void testMinuteByApprovedMinute() throws BusinessException {

        ApprovedDAO approvedDAO = new ApprovedDAO();
        int approvedResult;
        
        Approved approvedObject = new Approved(1, "Antonio de Jesús Domínguez García");
        approvedResult = approvedDAO.approvedMeeting(approvedObject);
        org.junit.Assert.assertEquals(1, approvedResult);
    }
    
    @Test
    public void testMinuteByDeleteApproveMinute() throws BusinessException {
        
        ApprovedDAO approvedDAO = new ApprovedDAO();
        int approvedResult;
        approvedResult = approvedDAO.deleteApproved("Antonio de Jesús Domínguez García", 1);
        org.junit.Assert.assertEquals(1, approvedResult);
    }
    
    @Test
    public void testMinuteByGetMembersApprove() throws BusinessException {
        
        ApprovedDAO approvedDAO = new ApprovedDAO();
        ArrayList<Approved> arrayListApproved;
        arrayListApproved = approvedDAO.getMembersApprove(1);
        org.junit.Assert.assertFalse("Prueba minuta provada", arrayListApproved.isEmpty());
        
    }
}
