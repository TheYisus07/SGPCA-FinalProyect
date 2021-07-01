package domain;

import java.util.Date;

/**
 *
 * @author Javier Blas
 */
public class ChapterBook extends Evidence{
    private int yearPublication;
    private String author;
    private int endPage;
    private int homePage;

    public ChapterBook(int yearPublication, String author, int endPage, int homePage, int idEvidence, Date registrationDate, String actualStatus, String impactOnCA, String title, String memberFullName, String keycodeAcademicGroup, String projectTitle) {
        super(idEvidence, registrationDate, actualStatus, impactOnCA, title, memberFullName, keycodeAcademicGroup, projectTitle);
        this.yearPublication = yearPublication;
        this.author = author;
        this.endPage = endPage;
        this.homePage = homePage;
    }

    public ChapterBook() {}

    public int getYearPublication() {
        return yearPublication;
    }

    public String getAuthor() {
        return author;
    }

    public int getEndPage() {
        return endPage;
    }

    public int getHomePage() {
        return homePage;
    }

    public void setYearPublication(int yearPublication) {
        this.yearPublication = yearPublication;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public void setHomePage(int homePage) {
        this.homePage = homePage;
    }

    @Override
    public String toString() {
        return "ChapterBook{" + "yearPublication=" + yearPublication + ", author=" + author + ", endPage=" + endPage + ", homePage=" + homePage + '}';
    }
}
