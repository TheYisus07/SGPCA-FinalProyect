package domain;

import java.util.Date;

/**
 *
 * @author Javier Blas
 */
public class GeneralCurriculum {
    private String title;
    private String dependencyInstitute;
    private String facultyOfTheInstitution;
    private String degreeOfConsolidation;
    private int numberOfParticipants;
    private int numberOfColaborators;
    private Date registrationDate;
    private String lgac;
    private String generalPurpose;
    private String mission;
    private String vision;
    private String keyCode;

    public GeneralCurriculum(String title, String dependencyInstitute, String facultyOfTheInstitution, String degreeOfConsolidation, int numberOfParticipants, int numberOfColaborators, Date registrationDate, String lgac, String generalPurpose, String mission, String vision, String keyCode) {
        this.title = title;
        this.dependencyInstitute = dependencyInstitute;
        this.facultyOfTheInstitution = facultyOfTheInstitution;
        this.degreeOfConsolidation = degreeOfConsolidation;
        this.numberOfParticipants = numberOfParticipants;
        this.numberOfColaborators = numberOfColaborators;
        this.registrationDate = registrationDate;
        this.lgac = lgac;
        this.generalPurpose = generalPurpose;
        this.mission = mission;
        this.vision = vision;
        this.keyCode = keyCode;
    }

    public GeneralCurriculum() {
    }

    public String getTitle() {
        return title;
    }

    public String getDependencyInstitute() {
        return dependencyInstitute;
    }

    public String getFacultyOfTheInstitution() {
        return facultyOfTheInstitution;
    }

    public String getDegreeOfConsolidation() {
        return degreeOfConsolidation;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public int getNumberOfColaborators() {
        return numberOfColaborators;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public String getLgac() {
        return lgac;
    }

    public String getGeneralPurpose() {
        return generalPurpose;
    }

    public String getMission() {
        return mission;
    }

    public String getVision() {
        return vision;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDependencyInstitute(String dependencyInstitute) {
        this.dependencyInstitute = dependencyInstitute;
    }

    public void setFacultyOfTheInstitution(String facultyOfTheInstitution) {
        this.facultyOfTheInstitution = facultyOfTheInstitution;
    }

    public void setDegreeOfConsolidation(String degreeOfConsolidation) {
        this.degreeOfConsolidation = degreeOfConsolidation;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public void setNumberOfColaborators(int numberOfColaborators) {
        this.numberOfColaborators = numberOfColaborators;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setLgac(String lgac) {
        this.lgac = lgac;
    }

    public void setGeneralPurpose(String generalPurpose) {
        this.generalPurpose = generalPurpose;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    @Override
    public String toString() {
        return "GeneralCurriculum{" + "title=" + title + ", dependencyInstitute=" + dependencyInstitute + ", facultyOfTheInstitution=" + facultyOfTheInstitution + ", degreeOfConsolidation=" + degreeOfConsolidation + ", numberOfParticipants=" + numberOfParticipants + ", numberOfColaborators=" + numberOfColaborators + ", registrationDate=" + registrationDate + ", lgac=" + lgac + ", generalPurpose=" + generalPurpose + ", mission=" + mission + ", vision=" + vision + ", keyCode=" + keyCode + '}';
    }
}