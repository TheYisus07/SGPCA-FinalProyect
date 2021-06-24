package businesslogic;

import domain.Constancy;
import java.util.ArrayList;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public interface IConstancyDAO {
    
    /**
     * Method of consult all Constancys
     * @return ArrayList of Constancy
     */
    public ArrayList<Constancy> consultConstancyList();
    
    /**
     * Method of consult a Constancy
     * @param idConstancy identifier used to consult the Constancy
     * @return a Constancy
     */
    public Constancy checkConstancy(int idConstancy);
    
    /**
     * Method of register a Constancy
     * @param constancy Constancy to generate
     * @return int 1 if the Agreement was registered
     * int 0 if it could not be registered
     */
    public int generateConstancy(Constancy constancy);
}
