package domain;
import java.util.Date;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public class Event {
    private String tittle;
    private String responsable;
    private String type;
    private String place;
    private String privacy;
    private Date eventDate;
    private Date registrationDate;

    public Event(String tittle, String type, Date registrationDate, String place, Date eventDate, String privacy, String responsable) {
        this.tittle = tittle;
        this.responsable = responsable;
        this.type = type;
        this.place = place;
        this.privacy = privacy;
        this.registrationDate = registrationDate;
        this.eventDate = eventDate;
    }
    
    public Event() {}

    public String getTittle() {
        return tittle;
    }

    public String getResponsable() {
        return responsable;
    }

    public String getType() {
        return type;
    }

    public String getPlace() {
        return place;
    }

    public String getPrivacy() {
        return privacy;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public Date getEventDate() {
        return eventDate;
    }
    
    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public String toString() {
        return "Event{" + "tittle=" + tittle + ", responsable=" + responsable + ", type=" + type + ", place=" + place + ", privacy=" + privacy + ", registrationDate=" + registrationDate + ", eventDate=" + eventDate + '}';
    }
    
}
