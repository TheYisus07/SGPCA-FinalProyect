package bussisslogic;
import businesslogic.BusinessException;
import businesslogic.ProjectDAO;
import domain.Project;
import java.sql.Date;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author daniCV
 */

public class ProjectDAOTest {
    
    //@Test
    public void testAddProjectSuccessfully() throws BusinessException {
        Date estimatedFinishDate = new Date((2022-1900), (4-1), 12);
        Date startDate = new Date((2023-1900), (4-1), 12);
        Project project = new Project("Machine Learning 2021", estimatedFinishDate, startDate, "LGCA1, LGCA2", "Proyecto de investigación dedicado a la intervención de los protocolos de seguridad para microservicios en los que se tiene relación con la ingeniería de software.", "José Daniel Camarillo Villa", "Antonio de Jesús Domínguez García");
        ProjectDAO projectDAO = new ProjectDAO();
        int successfulSave = projectDAO.addProject(project);
        Assert.assertEquals(1, successfulSave);
    }
    
    //@Test
    public void testUpdateProjectSuccessfully() throws BusinessException {
        Date estimatedFinishDate = new Date((2023-1900), (10-1), 1);
        Date startDate = new Date((2024-1900), (10-1), 1);
        ProjectDAO project = new ProjectDAO();
        Project updatedProject = new Project("Machine Learning en Python", estimatedFinishDate, startDate, "LGCA1, LGCA2", "Proyecto de investigación dedicado a la intervención de los protocolos de seguridad para microservicios en IS.", "Antonio de Jesús Domínguez García", "José Daniel Camarillo Villa");
        int updatedSuccessful = project.modifyProject(updatedProject, "Machine Learning 2021");
        Assert.assertEquals(1, updatedSuccessful);
    }
    
    //@Test
    public void testConsultProjectByTitle() throws BusinessException {
        ProjectDAO project = new ProjectDAO();
        Project projectResult;
        String projectTitleExpected = "Machine Learning en Python";
        projectResult = project.consultProjectByTitle(projectTitleExpected);
        String projectTitleActual = projectResult.getTitle();
        Assert.assertEquals("Prueba obtener otro Projecto", projectTitleExpected, projectTitleActual);
    }
    
    @Test
    public void testConsultListOfProjects() throws BusinessException {
        ArrayList<Project> listProject = new ArrayList<>();
        ProjectDAO project = new ProjectDAO();
        listProject = project.consultListOfProjects();
        Assert.assertTrue(!listProject.isEmpty());
    }
}
