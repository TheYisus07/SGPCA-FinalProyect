package domain;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public class Minute {
    private String note;
    private String pending;
    private int idMeeting;

    public Minute(String note, String pending, int idMeeting) {
        this.note = note;
        this.pending = pending;
        this.idMeeting = idMeeting;
    }

    public Minute() {}

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPending() {
        return pending;
    }

    public void setPending(String pending) {
        this.pending = pending;
    }

    public int getIdMeeting() {
        return idMeeting;
    }

    public void setIdMeeting(int idMeeting) {
        this.idMeeting = idMeeting;
    }
}
