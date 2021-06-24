package domain;


/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public class PreRequisite {
    private String description;
    private String nameParticipant;
    private int idMeeting;

    public PreRequisite(String description, String nameParticipant, int idMeeting) {
        this.description = description;
        this.nameParticipant = nameParticipant;
        this.idMeeting = idMeeting;
    }
    
    public PreRequisite(String description, String nameParticipant) {
        this.description = description;
        this.nameParticipant = nameParticipant;
    }
    
    public PreRequisite() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameParticipant() {
        return nameParticipant;
    }

    public void setNameParticipant(String nameParticipant) {
        this.nameParticipant = nameParticipant;
    }

    public int getIdMeeting() {
        return idMeeting;
    }

    public void setIdMeeting(int idMeeting) {
        this.idMeeting = idMeeting;
    }

    @Override
    public String toString() {
        return "PreRequisite{" + "description=" + description + ", nameParticipant=" + nameParticipant + ", idMeeting=" + idMeeting + '}';
    }
    
    
}
