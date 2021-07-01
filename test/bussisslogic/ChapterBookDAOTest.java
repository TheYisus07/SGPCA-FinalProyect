package bussisslogic;

import businesslogic.BusinessException;
import businesslogic.ChapterBookDAO;
import domain.ChapterBook;
import java.sql.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Javier Blas
 */
public class ChapterBookDAOTest {
    @Test
    public void testAddNeWChapterBookSuccessfully() throws BusinessException {
        ChapterBookDAO chapterBookDAO = new ChapterBookDAO();
        int chapterBookExpected;
        Date registrationDate = new Date((2010-1900), (8-1), 14);
        ChapterBook chapterBookObject = new ChapterBook(2019, "Javier Alberto Calderón Blas", 140, 90, 104, registrationDate, "En proceso", "Sí", "Inteligencia Artificial", "Javier Alberto Calderón Blas", "LIS", null);
        chapterBookExpected = chapterBookDAO.addNewChapterBook(chapterBookObject);
        
        Assert.assertEquals("Registro exitoso", 1, chapterBookExpected);
    }
    
    @Test 
    public void testModifyChapterBookByIdEvidence() throws BusinessException {
        ChapterBookDAO chapterBookDAO =  new ChapterBookDAO();
        int chapterBookResult;
 
        Date registrationDate = new Date((2010-1900), (8-1), 14);
        ChapterBook chapterBookObject = new ChapterBook(2019, "Javier Alberto Calderón Blas", 145, 100, 104, registrationDate, "En proceso", "Sí", "Inteligencia Artificial", "Javier Alberto Calderón Blas", "LIS", null);
        int idEvidence = 104;
        chapterBookResult = chapterBookDAO.modifyChapterBook(chapterBookObject, idEvidence);
        
        Assert.assertEquals("Prueba modificar capítulo de libro", 1, chapterBookResult);
    }
    
    @Test
    public void testConsultChapterBookByIdEvidence() throws BusinessException {
        ChapterBookDAO chapterBookDAO = new ChapterBookDAO();
        ChapterBook chapterBookResult;
        int idEvidence = 104;
        chapterBookResult = chapterBookDAO.consultChapterBook(idEvidence);
        int ideEvidenceActual = chapterBookResult.getIdEvidence();
        Assert.assertEquals("Prueba obtener otro capítulo de libro", idEvidence, ideEvidenceActual);
    } 
}
