package bussisslogic;

import businesslogic.BookDAO;
import businesslogic.BusinessException;
import domain.Book;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Javier Blas
 */
public class BookDAOTest {
    @Test
    public void testAddNeWBookSuccessfully() throws BusinessException {
        BookDAO bookDAO = new BookDAO();
        int bookExpected;
        Date endDate = new Date((2021-1900), (4-1), 8);
        Date startDate = new Date((2020-1900), (4-1), 8);
        Date registrationDate = new Date((2010-1900), (8-1), 14);
        Book bookObject = new Book("Milano", endDate, startDate, 2020, 1, 104, registrationDate, "En proceso", "Sí", "Inteligencia Artificial", "Javier Alberto Calderón Blas", "LIS", null);
        bookExpected = bookDAO.addNewBook(bookObject);
        
        Assert.assertEquals("Registro exitoso", 1, bookExpected);
    }
   
    @Test 
    public void testModifyBookByIdEvidence() throws BusinessException {
        BookDAO bookDAO =  new BookDAO();
        int bookResult;
        
        Date endDate = new Date((2021-1900), (4-1), 8);
        Date startDate = new Date((2020-1900), (4-1), 8);
        Date registrationDate = new Date((2010-1900), (8-1), 14);
        Book bookObjectNew = new Book("Sprice", endDate, startDate, 2020, 1, 104, registrationDate, "En proceso", "Sí", "Inteligencia Artificial", "Javier Alberto Calderón Blas", "LIS", null);
        int idEvidence = 104;
        bookResult = bookDAO.modifyBook(bookObjectNew, idEvidence);
        
        Assert.assertEquals("Prueba modificar libro", 1, bookResult);
    }
    
    @Test
    public void testConsultBookByIdEvidence() throws BusinessException {
        BookDAO bookDAO = new BookDAO();
        Book bookResult;
        int idEvidence = 104;
        bookResult = bookDAO.consultBook(idEvidence);
        int ideEvidenceActual = bookResult.getIdEvidence();
        Assert.assertEquals("Prueba obtener otro libro", idEvidence, ideEvidenceActual);
    } 
}
