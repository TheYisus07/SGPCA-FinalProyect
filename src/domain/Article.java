package domain;

import java.util.Date;

/**
 *
 * @author Javier Blas
 */
public class Article extends Evidence{
    private String author;
    private String mail;
    private Date publicationDate;
    private String issn;
    private String megazineName;
    private int endPage;
    private String country;
    private int volumen;

    public Article(String author, String mail, Date publicationDate, String issn, String megazineName, int endPage, String country, int volumen, int idEvidence, Date registrationDate, String actualStatus, String impactOnCA, String title, String memberFullName, String keycodeAcademicGroup, String projectTitle) {
        super(idEvidence, registrationDate, actualStatus, impactOnCA, title, memberFullName, keycodeAcademicGroup, projectTitle);
        this.author = author;
        this.mail = mail;
        this.publicationDate = publicationDate;
        this.issn = issn;
        this.megazineName = megazineName;
        this.endPage = endPage;
        this.country = country;
        this.volumen = volumen;
    }

    public Article() {}

    public String getAuthor() {
        return author;
    }

    public String getMail() {
        return mail;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public String getIssn() {
        return issn;
    }

    public String getMegazineName() {
        return megazineName;
    }

    public int getEndPage() {
        return endPage;
    }

    public String getCountry() {
        return country;
    }

    public int getVolumen() {
        return volumen;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public void setMegazineName(String megazineName) {
        this.megazineName = megazineName;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setVolumen(int volumen) {
        this.volumen = volumen;
    }

    @Override
    public String toString() {
        return "Article{" + "author=" + author + ", mail=" + mail + ", publicationDate=" + publicationDate + ", issn=" + issn + ", megazineName=" + megazineName + ", endPage=" + endPage + ", country=" + country + ", volumen=" + volumen + '}';
    } 
}