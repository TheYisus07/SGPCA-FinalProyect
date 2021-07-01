package bussisslogic;

import businesslogic.ArticleDAO;
import businesslogic.BusinessException;
import org.junit.Test;
import domain.Article;
import java.sql.Date;
import org.junit.Assert;

/**
 *
 * @author Javier Blas
 */
public class ArticleDAOTest {
    @Test
    public void testAddNeWArticleSuccessfully() throws BusinessException {
        ArticleDAO articleDAO = new ArticleDAO();
        int articleExpected;
        Date publicationDate = new Date((2021-1900), (4-1), 8);
        Date registrrationDate = new Date((2010-1900), (8-1), 14);
        Article articleObject = new Article("Javier Alberto Calderón Blas", "zs19014035@estudiantes.uv.mx", publicationDate, "1144-875X", "Future", 14, "CDMX", 1, 104, registrrationDate, "Concluido", "Sí", "Inteligencia Artificial", "Javier Alberto Calderón Blas", "LIS", null);
        articleExpected = articleDAO.addNewArticle(articleObject);
        
        Assert.assertEquals("Registro exitoso", 1, articleExpected);
    }
    
    @Test 
    public void testModifyArticleByIdEvidence() throws BusinessException {
        ArticleDAO articleDAO =  new ArticleDAO();
        int articleResult;
        
        Date publicationDate = new Date((2021-1900), (4-1), 15);
        Date registrrationDate = new Date((2010-1900), (8-1), 14);
        Article articleObjectNew = new Article("Javier Alberto Calderón Blas", "zs19014035@estudiantes.uv.mx", publicationDate, "1144-880X", "Future", 14, "CDMX", 1, 104, registrrationDate, "Concluido", "Sí", "Inteligencia Artificial", "Javier Alberto Calderón Blas", "LIS", null);
        int idEvidence = 104;
        articleResult = articleDAO.modifyArticle(articleObjectNew, idEvidence);
        
        Assert.assertEquals("Prueba modificar articulo", 1, articleResult);
    }
    
    @Test
    public void testConsultArticleByIdEvidence() throws BusinessException {
        ArticleDAO articleDAO = new ArticleDAO();
        Article articleResult;
        int idEvidence = 104;
        articleResult = articleDAO.consultArticle(idEvidence);
        int ideEvidenceActual = articleResult.getIdEvidence();
        Assert.assertEquals("Prueba obtener otro artículo", idEvidence, ideEvidenceActual);
    }
}
