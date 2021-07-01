package businesslogic;

import domain.PersonalCurriculum;

/**
 *
 * @author Javier Blas
 */
public interface IPersonalCurriculumDAO {
    /**
     * Method to consult a personal resume according to the corresponding name
     * @param fullName String  to check
     * @return PersonalCurriculum consulted according to the fullname
     * @throws bussinesslogic.BusinessException
     */
    public PersonalCurriculum consultPersonalCurriculum (String fullName) throws BusinessException;
    /**
     * Method of modifying a PersonalCurriculum
     * @param personalCurriculum PersonalCurriculum to modify
     * @param fullName String to identifier
     * @return int 1 if the personalCurriculum was modified successfully
     * int 0 if the member was not modified successfully
     * @throws bussinesslogic.BusinessException
     */
    public int modifyPersonalCurriculum (PersonalCurriculum personalCurriculum, String fullName) throws BusinessException;
}