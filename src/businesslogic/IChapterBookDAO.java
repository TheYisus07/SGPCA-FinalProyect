package businesslogic;

import domain.ChapterBook;

/**
 * 
 * @author daniCV
 */

public interface IChapterBookDAO {
    
    /**
     * Method of adding a ChapterBook
     * @param chapterBook ChapterBook to add
     * @return int 1 if the ChapterBook was added successfully
     * int 0 if it could not be added
     * @throws businesslogic.BusinessException
     */
    public int addNewChapterBook(ChapterBook chapterBook) throws BusinessException;    
    
    /**
     * Method of modifying a Article
     * @param chapterBook ChapterBook to modify
     * @param idEvidence int to identify Evidence
     * @return int 1 if the chapterBook was modified successfully
     * int 0 if it could not be modified
     * @throws businesslogic.BusinessException
     */
    public int modifyChapterBook(ChapterBook chapterBook, int idEvidence) throws BusinessException;
    
    /**
     * Method to consult a ChapterBook according to the id that corresponds to it
     * @param idEvidence int to check
     * @return ChapterBook consulted according to the id
     * @throws businesslogic.BusinessException
     */
    public ChapterBook consultChapterBook(int idEvidence) throws BusinessException;
}
