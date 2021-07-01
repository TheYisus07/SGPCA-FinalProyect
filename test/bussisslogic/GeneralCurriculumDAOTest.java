package bussisslogic;

import businesslogic.BusinessException;
import businesslogic.GeneralCurriculumDAO;
import domain.GeneralCurriculum;
import java.sql.Date;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Javier Blas
 */
public class GeneralCurriculumDAOTest {
    @Test
    public void testConsultGeneralCurriculumByKeyCode() throws BusinessException {
        GeneralCurriculumDAO generalCurriculum = new GeneralCurriculumDAO();
        GeneralCurriculum generalCurriculumResult;
        String generalCurriculumKeycodeExpected = "LIS";
        generalCurriculumResult = generalCurriculum.consultGeneralCurriculum(generalCurriculumKeycodeExpected);
        String KeycodeActual = generalCurriculumResult.getKeyCode();
        Assert.assertEquals("Prueba obtener otro curriculum general", generalCurriculumKeycodeExpected, KeycodeActual);
    }
    
    @Test
    public void testUpdateGeneralCurriculumSuccessfully() throws BusinessException {
        Date registrationDate = new Date((2004-1900), (2-1), 15);
        GeneralCurriculumDAO generalCurriculum = new GeneralCurriculumDAO();
        GeneralCurriculum updatedGeneralCurriculum = new GeneralCurriculum("Tecnologías de la informática", "Universidad Veracruzana", "Estadística e informática", "En consolidación", 5, 5, registrationDate, "Sí","Desarrrolar métodos, técnicas y herramientas para el desarrollo de software", "Generar cnocimiento y formar recursos humanos en Ingeniería de Software", "Generar conocimiento y formar recursos humanos en Ingeniería de software", "JDOEIJ804");
        int updatedSuccessful = generalCurriculum.modifyGeneralCurriculum(updatedGeneralCurriculum, "LIS");
        Assert.assertEquals(1, updatedSuccessful);
    }
}
