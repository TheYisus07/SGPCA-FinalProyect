package domain;

import java.util.Date;

/**
 *
 * @author Javier Blas
 */

public class Evidence {
    private int idEvidence;
    private Date registrationDate;
    private String actualStatus;
    private String impactOnCA;
    private String title;
    private String memberFullName;
    private String keycodeAcademicGroup;
    private String projectTitle;

    public Evidence(int idEvidence, Date registrationDate, String actualStatus, String impactOnCA, String title, String memberFullName, String keycodeAcademicGroup, String projectTitle) {
        this.idEvidence = idEvidence;
        this.registrationDate = registrationDate;
        this.actualStatus = actualStatus;
        this.impactOnCA = impactOnCA;
        this.title = title;
        this.memberFullName = memberFullName;
        this.keycodeAcademicGroup = keycodeAcademicGroup;
        this.projectTitle = projectTitle;
    }
    
    public Evidence(int idEvidence, Date registrationDate, String actualStatus, String impactOnCA, String title, String memberFullName, String keycodeAcademicGroup) {
        this.idEvidence = idEvidence;
        this.registrationDate = registrationDate;
        this.actualStatus = actualStatus;
        this.impactOnCA = impactOnCA;
        this.title = title;
        this.memberFullName = memberFullName;
        this.keycodeAcademicGroup = keycodeAcademicGroup;        
    }

    public Evidence() {}

    public int getIdEvidence() {
        return idEvidence;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public String getActualStatus() {
        return actualStatus;
    }

    public String getImpactOnCA() {
        return impactOnCA;
    }

    public String getTitle() {
        return title;
    }

    public String getMemberFullName() {
        return memberFullName;
    }

    public String getKeycodeAcademicGroup() {
        return keycodeAcademicGroup;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setIdEvidence(int idEvidence) {
        this.idEvidence = idEvidence;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setActualStatus(String actualStatus) {
        this.actualStatus = actualStatus;
    }

    public void setImpactOnCA(String impactOnCA) {
        this.impactOnCA = impactOnCA;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMemberFullName(String memberFullName) {
        this.memberFullName = memberFullName;
    }

    public void setKeycodeAcademicGroup(String keycodeAcademicGroup) {
        this.keycodeAcademicGroup = keycodeAcademicGroup;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    @Override
    public String toString() {
        return "Evidence{" + "idEvidence=" + idEvidence + ", registrationDate=" + registrationDate + ", actualStatus=" + actualStatus + ", impactOnCA=" + impactOnCA + ", title=" + title + ", memberFullName=" + memberFullName + ", keycodeAcademicGroup=" + keycodeAcademicGroup + ", projectTitle=" + projectTitle + '}';
    }    
}
