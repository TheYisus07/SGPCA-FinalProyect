package domain;

import java.util.Date;

/**
 * 
 * @author daniCV
 */

public class Blueprint {
    private String title;
    private Date startDate;
    private String associatedLGAC;
    private String status;
    private String modality;
    private String student;
    private String description;
    private String codirectors;
    private String projectTitle; 

    public Blueprint(String title, Date startDate, String associatedLGAC, String status, String modality, String student, String description, String codirectors, String projectTitle) {
        this.title = title;
        this.startDate = startDate;
        this.associatedLGAC = associatedLGAC;
        this.status = status;
        this.modality = modality;
        this.student = student;
        this.description = description;
        this.codirectors = codirectors;
        this.projectTitle = projectTitle;
    }

    public Blueprint(String title, Date startDate, String associatedLGAC, String status, String modality, String student, String description, String codirectors) {
        this.title = title;
        this.startDate = startDate;
        this.associatedLGAC = associatedLGAC;
        this.status = status;
        this.modality = modality;
        this.student = student;
        this.description = description;
        this.codirectors = codirectors;
    }    

    public Blueprint() {}

    public String getTitle() {
        return title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getAssociatedLGAC() {
        return associatedLGAC;
    }

    public String getStatus() {
        return status;
    }

    public String getModality() {
        return modality;
    }

    public String getStudent() {
        return student;
    }

    public String getDescription() {
        return description;
    }

    public String getCodirectors() {
        return codirectors;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setAssociatedLGAC(String associatedLGAC) {
        this.associatedLGAC = associatedLGAC;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCodirectors(String codirectors) {
        this.codirectors = codirectors;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }
}