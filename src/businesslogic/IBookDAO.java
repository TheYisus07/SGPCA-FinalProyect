package businesslogic;

import domain.Book;

/**
 *
 * @author daniCV
 */

public interface IBookDAO {
    
    /**
     * Method of adding a Book 
     * @param book Book to add
     * @return int 1 if the Book was added successfully
     * int 0 if it could not be added
     * @throws businesslogic.BusinessException
     */
    public int addNewBook(Book book) throws BusinessException;    
    
    /**
     * Method of modifying a Book
     * @param book Book to modify
     * @param idEvidence int to identify Evidence
     * @return int 1 if the article was modified successfully
     * int 0 if it could not be modified
     * @throws businesslogic.BusinessException
     */
    public int modifyBook(Book book, int idEvidence) throws BusinessException;
    
    /**
     * Method to consult a Book according to the id that corresponds to it
     * @param idEvidence int to check
     * @return Article consulted according to the id
     * @throws businesslogic.BusinessException
     */
    public Book consultBook(int idEvidence) throws BusinessException;
}