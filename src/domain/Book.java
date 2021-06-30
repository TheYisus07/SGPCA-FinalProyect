package domain;

import java.util.Date;

/**
 *
 * @author daniCV
 */

public class Book extends Evidence{
    private String editorial;
    private Date endDate;
    private Date startDate;
    private int publicationYear;
    private int editionNumber;

    public Book(String editorial, Date endDate, Date startDate, int publicationYear, int editionNumber, int idEvidence, Date registrationDate, String actualStatus, String impactOnCA, String title, String memberFullName, String keycodeAcademicGroup, String projectTitle) {
        super(idEvidence, registrationDate, actualStatus, impactOnCA, title, memberFullName, keycodeAcademicGroup, projectTitle);
        this.editorial = editorial;
        this.endDate = endDate;
        this.startDate = startDate;
        this.publicationYear = publicationYear;
        this.editionNumber = editionNumber;
    }

    public Book() {}

    public String getEditorial() {
        return editorial;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public int getEditionNumber() {
        return editionNumber;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setEditionNumber(int editionNumber) {
        this.editionNumber = editionNumber;
    }

    @Override
    public String toString() {
        return "Book{" + "editorial=" + editorial + ", endDate=" + endDate + ", startDate=" + startDate + ", publicationYear=" + publicationYear + ", editionNumber=" + editionNumber + '}';
    }
}