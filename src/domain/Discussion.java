package domain;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public class Discussion {
    private int estimatedTime;
    private int number;
    private int idMeeting;
    private String description;
    private String LeaderName;
    private String startHour;
    private String endHour;

    public Discussion(int estimatedTime, int number, int idMeeting, String description, String LeaderName, String startHour, String endHour) {
        this.estimatedTime = estimatedTime;
        this.number = number;
        this.idMeeting = idMeeting;
        this.description = description;
        this.LeaderName = LeaderName;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public Discussion(int estimatedTime, int number, int idMeeting, String description, String LeaderName) {
        this.estimatedTime = estimatedTime;
        this.number = number;
        this.idMeeting = idMeeting;
        this.description = description;
        this.LeaderName = LeaderName;
    }
    
    
    
    public Discussion(int estimatedTime, int number, String description, String LeaderName) {
        this.estimatedTime = estimatedTime;
        this.number = number;
        this.description = description;
        this.LeaderName = LeaderName;
    }
    

    public Discussion(int estimatedTime, String description, String LeaderName) {
        this.estimatedTime = estimatedTime;
        this.description = description;
        this.LeaderName = LeaderName;
        
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLeaderName() {
        return LeaderName;
    }

    public void setLeaderName(String LeaderName) {
        this.LeaderName = LeaderName;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    @Override
    public String toString() {
        return "Discussion{" + "estimatedTime=" + estimatedTime + ", number=" + number + ", meeting_idMeeting=" + idMeeting + ", description=" + description + ", LeaderName=" + LeaderName + ", startHour=" + startHour + ", endHour=" + endHour + '}';
    }
    
    
    
}
