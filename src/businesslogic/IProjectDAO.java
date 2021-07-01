package businesslogic;

import domain.Project;
import java.util.ArrayList;

public interface IProjectDAO {
    
    /**
     * Method of consulting the list of all registered Projects
     * @return ArrayList of Project
     * @throws businesslogic.BusinessException
     */
    public ArrayList<Project> consultListOfProjects() throws BusinessException;
    
    /**
     * Method of adding a Project
     * @param project
     * @return int 1 if the Project was added successfully
     * int 0 if it could not be added
     * @throws businesslogic.BusinessException
     */
    public int addProject(Project project) throws BusinessException;
    
    /**
     * Method of modifying a Project
     * @param project
     * @param projecTitle
     * @return int 1 if the Objective was modified successfully
     * int 0 if it could not be modified
     * @throws businesslogic.BusinessException
     */
    public int modifyProject(Project project, String projecTitle) throws BusinessException;
    
    /**
     * Method to consult a Project according to the Title that corresponds to it
     * @param projectTitle
     * @return Project consulted according to the Title
     * @throws businesslogic.BusinessException
     */
    public Project consultProjectByTitle(String projectTitle) throws BusinessException; 
}