package domain;

import java.util.Date;

/**
 *
 * @author Javier Blas
 */

public class ReceptionWork extends Evidence {
    private String direction;
    private Date finalDate;
    private Date startDate;
    private String grade;
    private int numberStudents; 
    private String kind;
    private String blueprintTitle;

    public ReceptionWork(String direction, Date finalDate, Date startDate, String grade, int numberStudents, String kind, String blueprintTitle, int idEvidence, Date registrationDate, String actualStatus, String impactOnCA, String title, String memberFullName, String keycodeAcademicGroup, String projectTitle) {
        super(idEvidence, registrationDate, actualStatus, impactOnCA, title, memberFullName, keycodeAcademicGroup, projectTitle);
        this.direction = direction;
        this.finalDate = finalDate;
        this.startDate = startDate;
        this.grade = grade;
        this.numberStudents = numberStudents;
        this.kind = kind;
        this.blueprintTitle = blueprintTitle;
    }

    public ReceptionWork() {}

    public String getDirection() {
        return direction;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getGrade() {
        return grade;
    }

    public int getNumberStudents() {
        return numberStudents;
    }

    public String getKind() {
        return kind;
    }

    public String getBlueprintTitle() {
        return blueprintTitle;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setNumberStudents(int numberStudents) {
        this.numberStudents = numberStudents;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setBlueprintTitle(String blueprintTitle) {
        this.blueprintTitle = blueprintTitle;
    }

    @Override
    public String toString() {
        return "ReceptionWork{" + "direction=" + direction + ", finalDate=" + finalDate + ", startDate=" + startDate + ", grade=" + grade + ", numberStudents=" + numberStudents + ", kind=" + kind + ", blueprintTitle=" + blueprintTitle + '}';
    }
}