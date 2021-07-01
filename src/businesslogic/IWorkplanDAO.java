package businesslogic;

import domain.Workplan;
import domain.Objective;
import domain.Strategy;
import java.util.ArrayList;

/**
 * 
 * @author daniCV
 */

public interface IWorkplanDAO {
    
    /**
     * Method of adding a AcademicGroup WorkPlan
     * @param workplan Workplan to add
     * @return int 1 if the WorkPlan was added successfully
     * int 0 if it could not be added
     * @throws businesslogic.BusinessException
     */
    public int addWorkPlan(Workplan workplan) throws BusinessException;
    
    /**
     * Method to consult a Workplan according to the keycode that corresponds to it
     * @param keyCode String to check
     * @return Workplan consulted according to the keyCode
     * @throws businesslogic.BusinessException
     */
    public Workplan consultWorkplanByKeyCode(String keyCode) throws BusinessException;
    
    /**
     * Method of managing a WorkPlan
     * @param workplan Workplan to manage
     * @param newWorkplanKeycode String to identify
     * @return  int 1 if the WorkPlan was modified successfully
     * int 0 if it could not be modified
     * @throws businesslogic.BusinessException
     */
    public int manageWorkPlan(Workplan workplan, String newWorkplanKeycode) throws BusinessException;
    
    /**
     * Method of adding a Objective
     * @param objective Objective to add
     * @return int 1 if the Objective was added successfully
     * int 0 if it could not be added
     * @throws businesslogic.BusinessException
     */
    public int addObjetive(Objective objective) throws BusinessException;
    
    /**
     * Method of adding a Strategy
     * @param strategy Strategy to add
     * @return int 1 if the Strategy was added successfully
     * int 0 if it could not be added
     * @throws businesslogic.BusinessException
     */
    public int addStrategy(Strategy strategy) throws BusinessException;
    
    /**
     * Method of modifying a Objective
     * @param objective Objective to modify
     * @param objectiveId int to identify
     * @return int 1 if the Objective was modified successfully
     * int 0 if it could not be modified
     * @throws businesslogic.BusinessException
     */
    public int modifyObjective(Objective objective, int objectiveId) throws BusinessException;
    
    /**
     * Method of consulting the list of all registered WorkPlans
     * @return ArrayList of Workplan 
     * @throws businesslogic.BusinessException 
     */
    public ArrayList<Workplan> consultListOfWorkplans() throws BusinessException;
    
    /**
     * Method to delete a Strategy
     * @param strategyId int to remove 
     * @return int 1 if the Strategy was eliminated successfully
     * int 0 if it could not be eliminated
     * @throws businesslogic.BusinessException
     */
    public int deleteStrategyByObjectiveId(int strategyId) throws BusinessException;
    
    /**
     * Method of consulting the list of all registered Objectives
     * @return ArrayList of Objective
     * @throws businesslogic.BusinessException
     */
    public ArrayList<Objective> consultListOfObjectives() throws BusinessException;
    
    /**
     * Method of consulting the list of all registered Strategies
     * @return ArrayList of Strategy
     * @throws businesslogic.BusinessException
     */
    public ArrayList<Strategy> consulListOfStrategys() throws BusinessException;
    
    /**
     * Method to get the id of the last inserted Objective
     * @return objectiveId int
     * @throws businesslogic.BusinessException
     */
    public int getLastInsertedObjectiveId() throws BusinessException;
}