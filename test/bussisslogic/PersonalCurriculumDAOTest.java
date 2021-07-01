package bussisslogic;

import businesslogic.BusinessException;
import businesslogic.PersonalCurriculumDAO;
import domain.PersonalCurriculum;
import java.sql.Date;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Javier Blas
 */
public class PersonalCurriculumDAOTest {
    
    @Test
    public void testConsultPersonalCurriculumByFullName() throws BusinessException {
        PersonalCurriculumDAO personalCurriculum = new PersonalCurriculumDAO();
        PersonalCurriculum personalCurriculumResult;
        String personalFullNameExpected = "Antonio Domínguez García";
        personalCurriculumResult = personalCurriculum.consultPersonalCurriculum(personalFullNameExpected);
        String workplanlanKeycodeActual = personalCurriculumResult.getFullName();
        Assert.assertEquals("Prueba obtener otro curriculum", personalFullNameExpected, workplanlanKeycodeActual);
    }
    
    @Test
    public void testUpdatePersonalCurriculumSuccessfully() throws BusinessException {
        Date dateOfBirth = new Date((2001-1900), (7-1), 21);
        PersonalCurriculumDAO personalCurriculum = new PersonalCurriculumDAO();
        PersonalCurriculum updatedPersonalCurriculum = new PersonalCurriculum("Antonio Domínguez García", dateOfBirth, "DOGA011207HVZMNRA08", "2288539801", "zs19014035@estudiantes.uv.mx", "Maestría", "Doctorado", "Innovación", "Completo", "Sí", "Sí", "Sí", "Integrante", "LIS");
        int updatedSuccessful = personalCurriculum.modifyPersonalCurriculum(updatedPersonalCurriculum, "Antonio Domínguez García");
        Assert.assertEquals(1, updatedSuccessful);
    }
}