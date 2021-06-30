package domain;

import java.util.Date;

/**
 * 
 * @author daniCV
 */

public class Project {
    private String title;
    private Date estimedFinishDate;
    private Date startDate;
    private String associatesIGAC;
    private String descripcion;
    private String participants;
    private String memberFullName;

    public Project(String title, Date estimedFinishDate, Date startDate, String associatesIGAC, String descripcion, String participants, String memberFullName) {
        this.title = title;
        this.estimedFinishDate = estimedFinishDate;
        this.startDate = startDate;
        this.associatesIGAC = associatesIGAC;
        this.descripcion = descripcion;
        this.participants = participants;
        this.memberFullName = memberFullName;
    }

    public Project() {}

    public String getTitle() {
        return title;
    }

    public Date getEstimedFinishDate() {
        return estimedFinishDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getAssociatesIGAC() {
        return associatesIGAC;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getParticipants() {
        return participants;
    }

    public String getMemberFullName() {
        return memberFullName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEstimedFinishDate(Date estimedFinishDate) {
        this.estimedFinishDate = estimedFinishDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setAssociatesIGAC(String associatesIGAC) {
        this.associatesIGAC = associatesIGAC;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public void setMemberFullName(String memberFullName) {
        this.memberFullName = memberFullName;
    }

    @Override
    public String toString() {
        return "Project{" + "title=" + title + ", estimedFinishDate=" + estimedFinishDate + ", startDate=" + startDate + ", associatesIGAC=" + associatesIGAC + ", descripcion=" + descripcion + ", participants=" + participants + ", memberFullName=" + memberFullName + '}';
    }
}
