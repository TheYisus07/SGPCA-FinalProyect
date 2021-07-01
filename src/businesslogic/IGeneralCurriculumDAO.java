package businesslogic;

import domain.GeneralCurriculum;

/**
 *
 * @author Javier Blas
 */
public interface IGeneralCurriculumDAO {
    /**
     * Method to consult a GeneralCurriculum according to the keycode that corresponds to it
     * @param keycode String to check
     * @return GeneralCurriculum consulted according to the keyCode
     * @throws bussinesslogic.BusinessException
     */
    public GeneralCurriculum consultGeneralCurriculum (String keycode) throws BusinessException;
    /**
     * Method to modifying a GeneralCurriculum
     * @param generalCurriculum GeneralCurriculum to modify
     * @param keycode String to indentifier
     * @return int 1 if the GeneralCurriculum was modified successfully
     * int 0 if the Objective was not modified successfully
     * @throws bussinesslogic.BusinessException
     */
    public int modifyGeneralCurriculum (GeneralCurriculum generalCurriculum, String keycode) throws BusinessException;
}
