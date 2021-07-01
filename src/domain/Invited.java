package domain;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public class Invited {
    private String fullName;
    private int idMeeting;

    public Invited(String fullName, int idMeeting) {
        this.fullName = fullName;
        this.idMeeting = idMeeting;
    }
    
    public Invited () {}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getIdMeeting() {
        return idMeeting;
    }

    public void setIdMeeting(int idMeeting) {
        this.idMeeting = idMeeting;
    }

    @Override
    public String toString() {
        return "Invited{" + "fullName=" + fullName + ", meeting_idMeeting=" + idMeeting + '}';
    }
}
