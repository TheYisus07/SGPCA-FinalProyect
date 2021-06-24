package domain;


/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public class Agreement {
    private String description;
    private String manager;
    private String date;
    private int number;
    private int idMeeting;

    public Agreement(String description, String manager, String date, int number, int idMeeting) {
        this.description = description;
        this.manager = manager;
        this.date = date;
        this.number = number;
        this.idMeeting = idMeeting;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getIdMeeting() {
        return idMeeting;
    }

    public void setIdMeeting(int idMeeting) {
        this.idMeeting = idMeeting;
    }
}
