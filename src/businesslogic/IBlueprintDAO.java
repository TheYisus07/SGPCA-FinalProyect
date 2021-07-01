package businesslogic;

import domain.Blueprint;
import java.util.ArrayList;

/**
 * 
 * @author daniCV
 */

public interface IBlueprintDAO {
    /**
     * Method of adding a Blueprint with a related project
     * @param blueprint Blueprint to add
     * @return int 1 if the Blueprint was added successfully
     * int 0 if it could not be added
     * @throws businesslogic.BusinessException
     */
    public int addBlueprint(Blueprint blueprint) throws BusinessException;
    
    /**
     * Method of adding a blueprint without a related project
     * @param blueprint Blueprint to add
     * @return int 1 if the Blueprint was added successfully
     * int 0 if it could not be added
     * @throws businesslogic.BusinessException
     */
    public int addBlueprintWithoutProject(Blueprint blueprint) throws BusinessException;
    
    /**
     * Method of modifying a Blueprint
     * @param blueprint Blueprint to modify 
     * @param blueprintTitle String to identify
     * @return int 1 if the Blueprint was modified successfully
     * int 0 if it could not be modified
     * @throws businesslogic.BusinessException
     */
    public int modifyBlueprint(Blueprint blueprint, String blueprintTitle) throws BusinessException;
    
    /**
     * Method to consult a Blueprint according to the title that corresponds to it
     * @param blueprintTitle
     * @return Blueprint consulted according to the title
     * @throws businesslogic.BusinessException
     */
    public Blueprint consultBlueprintByTitle(String blueprintTitle) throws BusinessException;
    
    /**
     * Method to consult a Blueprint according to the date that corresponds to it
     * @param blueprintDate
     * @return Blueprint consulted according to the date
     * @throws businesslogic.BusinessException
     */
    public Blueprint consultBlueprintByDate(String blueprintDate) throws BusinessException;
    
    /**
     * Method to delete a Bluprint
     * @param blueprintTitle
     * @return int 1 if the Blueprint was eliminated successfully
     * int 0 if it could not be eliminated
     * @throws businesslogic.BusinessException
     */
    public int deleteBlueprintByTitle(String blueprintTitle) throws BusinessException;
    
    /**
     * Method of consulting the list of all registered Blueprints
     * @return ArrayList of Blueprint
     */
    public ArrayList<Blueprint> consultListOfBlueprint() throws BusinessException;
}