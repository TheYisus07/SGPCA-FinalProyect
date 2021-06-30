package businesslogic;

import domain.Member;
import java.util.ArrayList;

/**
 *
 * @author Javier Blas
 */
public interface IMemberDAO {
    /**
     * Method of consulting the list of all registered Members
     * @return Arraylist of member
     * @throws businesslogic.BusinessException
     */
    public ArrayList<Member> consultMemberList() throws BusinessException;
    
    /**
     * Method to consult a member according to his name
     * @param fullName String to check
     * @return Member consulted according to his name
     * @throws businesslogic.BusinessException
     */
    public Member consultMember(String fullName) throws BusinessException;
    
    /**
     * Method to add a member to the academic group
     * @param member
     * @return int 1 if the member was registered successfully
     * int 0 if the member was not registered correctly
     * @throws businesslogic.BusinessException
     */ 
    public int registerMember(Member member) throws BusinessException; 
    
    /**
     * Method of modifying a Member
     * @param member Member to modify
     * @param fullName String to identify
     * @return int 1 if the member was modified successfully
     * int 0 if the member was not modified correctly
     * @throws businesslogic.BusinessException
     */
    public int modifyMember(Member member, String fullName) throws BusinessException;
    
    /**
     * Method to delete a Member
     * @param fullName String to remove identifier
     * @return int 1 if the member was removed successfully
     * int 0 if the member was not removed correctly
     * @throws businesslogic.BusinessException
     */
    public int removeMember (String fullName) throws BusinessException;
    
    /**
     * Method to change password
     * @param password String to change
     * @param fullName String to identifier
     * @return int 1 if the password was modified successfully
     * int 0 if the password was not modified successfully
     * @throws businesslogic.BusinessException
     */
    public int updatePasswordOfMember(String password, String fullName) throws BusinessException;
    
    /**
     * Method to encrypt password
     * @param password String to encrypt
     * @return String to compare
     */
    public String decryptMemberPassword(String password) throws BusinessException;
}

