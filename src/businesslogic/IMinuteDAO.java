package businesslogic;

import domain.Minute;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public interface IMinuteDAO {
    
    /**
     * Method of generate a Minute
     * @param minute Minute to generate
     * @return int 1 if the Minute was generated
     * int 0 if it could not be generated
     */
    public int generateMinute(Minute minute);
    
    /**
     * Method of consult a Minute
     * @param idMeeting identifier used to consult a minute
     * @return a Minute
     */
    public Minute consultMinute(int idMeeting);
    
    /**
     * Method of update a Minute
     * @param idMeeting identifier used to update a minute
     * @param minute new data Minute to update
     * @return int 1 if the Minute was updated
     * int 0 if it could not be updated
     */
    public int updateMinute(int idMeeting, Minute minute);
}
