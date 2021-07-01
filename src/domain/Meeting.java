package domain;

import java.util.Date;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public class Meeting {
    private int idMeeting;
    private Date date;
    private String titleOfProyect;
    private String place;
    private String affair;
    private String fullName;
    
    public Meeting(int idMeeting, Date date, String titleOfProyect, String place, String affair, String fullName) {
        this.idMeeting = idMeeting;
        this.date = date;
        this.titleOfProyect = titleOfProyect;
        this.place = place;
        this.affair = affair;
        this.fullName = fullName;
    }
    public Meeting(Date date, String titleOfProyect, String place, String affair, String fullName) {
        this.date = date;
        this.titleOfProyect = titleOfProyect;
        this.place = place;
        this.affair = affair;
        this.fullName = fullName;
    }

    public Meeting() {}

    public int getIdMeeting() {
        return idMeeting;
    }

    public void setIdMeeting(int idMeeting) {
        this.idMeeting = idMeeting;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitleOfProyect() {
        return titleOfProyect;
    }

    public void setTitleOfProyect(String titleOfProyect) {
        this.titleOfProyect = titleOfProyect;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getAffair() {
        return affair;
    }

    public void setAffair(String affair) {
        this.affair = affair;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    
}
