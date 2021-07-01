package bussisslogic;

import businesslogic.BusinessException;
import businesslogic.MemberDAO;
import domain.Member;
import java.sql.Date;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Javier Blas
 */
public class MemberDAOTest {
    @Test
    public void testRegisterMember() throws BusinessException {

        MemberDAO member = new MemberDAO();
        int memberExpectedt;
        Date memberDate = new Date((1995-1900), (5-1), 8);

        Member memberObject = new Member("Antonio Domínguez García", memberDate, "DOGA011207HVZMNRA08", "2281272550", "zs19014049@estudiante.uv.mx", "Maestria", "Cuarto grado", "Innovación", "Completo", "Sí", "Sí", "Sí", "Integrante", "LIS");
        memberExpectedt = member.registerMember(memberObject);
        
        org.junit.Assert.assertEquals("Registrar miembro exitosamente",1 , memberExpectedt);

    }
    
    @Test
    public void testRemoveMember() throws BusinessException {
        MemberDAO member = new MemberDAO();
        int deleteResult = member.removeMember("Antonio Domínguez García");
        Assert.assertEquals(1, deleteResult);
    }
    
    @Test
    public void testConsultMemberList() throws BusinessException {
        ArrayList<Member> listMembers = new ArrayList<>();
        MemberDAO member = new MemberDAO();
        listMembers = member.consultMemberList();
        Assert.assertTrue(!listMembers.isEmpty());
    }
    
    @Test
    public void testConsultMember() throws BusinessException {
        MemberDAO member = new MemberDAO();
        Member memberResult;
        String memberFullNameExpected = "Antonio Domínguez García";
        memberResult = member.consultMember(memberFullNameExpected);
        String memberFullNameActual = memberResult.getFullName();
        Assert.assertEquals("Prueba obtener miembro", memberFullNameExpected, memberFullNameActual);
    }
    
    @Test 
    public void testModifyMember() throws BusinessException {
        MemberDAO member = new MemberDAO();
        int memberResult;
       
        Date memberDate = new Date((2021-1900), (4-1), 03);
        Member memberObjectNew = new Member("Antonio Domínguez García", memberDate, "DOGA011207HVZMNRA08", "2281272550", "zs19014049@estudiante.uv.mx", "Maestria", "Cuarto grado", "Innovación", "Completo", "Sí", "Sí", "Sí", "Integrante", "LIS");
        String fullNameOfTheMember = "Antonio Domínguez García";
        memberResult = member.modifyMember(memberObjectNew, fullNameOfTheMember);
        
        org.junit.Assert.assertEquals("prueba modificar miembro", 1, memberResult);
    }
    
    @Test
    public void testUpdatePasswordOfMember() throws BusinessException {
        MemberDAO memberDAO = new MemberDAO();
        int resultUpdatePasswordOfMember = memberDAO.updatePasswordOfMember("123", "Javier Alberto Calderón Blas");
        Assert.assertEquals("La contraseña no fue asignada", 1, resultUpdatePasswordOfMember);
    }
    
    @Test
    public void testDecryptMemberPassword() throws BusinessException {
        MemberDAO memberDAO = new MemberDAO();
        Member memberConsulted = memberDAO.consultMember("Javier Alberto Calderón Blas");
        String memberPassword = memberDAO.decryptMemberPassword(memberConsulted.getPassword());
        String memberPasswordDecrypt = "123";
        Assert.assertEquals("La contraseña no fue desencriptada", memberPasswordDecrypt, memberPassword);
    }
    
}
