package businesslogic;

import domain.Article;

/**
 * 
 * @author daniCV
 */

public interface IArticleDAO {
    
    /**
     * Method of adding a Article 
     * @param article Article to add
     * @return int 1 if the Article was added successfully
     * int 0 if it could not be added
     * @throws businesslogic.BusinessException
     */
    public int addNewArticle(Article article) throws BusinessException;    
    
    /**
     * Method of modifying a Article
     * @param article Article to modify
     * @param idEvidence int to identify Evidence
     * @return int 1 if the article was modified successfully
     * int 0 if it could not be modified
     * @throws businesslogic.BusinessException
     */
    public int modifyArticle(Article article, int idEvidence) throws BusinessException;
    
    /**
     * Method to consult a Article according to the id that corresponds to it
     * @param idEvidence int to check
     * @return Article consulted according to the id
     * @throws businesslogic.BusinessException
     */
    public Article consultArticle(int idEvidence) throws BusinessException;
}
