package businesslogic;

import dataaccess.Conexion;
import domain.Member;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javier Blas
 */

public class MemberDAO implements IMemberDAO{
    
    private final Conexion CONNECTION = new Conexion();
    
    @Override
    public ArrayList<Member> consultMemberList() throws BusinessException {
       String query = "SELECT fullName, dateOfBirth, curp, phoneNumber, institutionalMail, discipline, studyGrade, studyArea, typeOfTeaching, lgac, ies, prodepParticipation, position, academicGroup_Keycode, password FROM member";
        ArrayList<Member> arrayListMembers = new ArrayList<>();     
        try {
            Statement statement = CONNECTION.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                 arrayListMembers.add(new Member(resultSet.getString("FullName"), resultSet.getDate("DateOfBirth"), resultSet.getString("CURP"), resultSet.getString("PhoneNumber"), resultSet.getString("InstitutionalMail"), resultSet.getString("Discipline"), resultSet.getString("StudyGrade"), resultSet.getString("StudyArea"),resultSet.getString("TypeOfTeaching"), resultSet.getString("LGAC"), resultSet.getString("IES"), resultSet.getString("ProdepParticipation"), resultSet.getString("Position"), resultSet.getString("academicGroup_Keycode"), resultSet.getString("password"))); 
            }
        } catch (SQLException ex) {            
            throw new BusinessException("DatabaseConnectionError", ex);
        } finally {
            CONNECTION.disconnect();
        }
        return arrayListMembers;
    }
    
    @Override
    public Member consultMember(String memberFullName) throws BusinessException {
        Member member = new Member();
        try {
            String query = "SELECT fullName, dateOfBirth, curp, phoneNumber, institutionalMail, discipline, studyGrade, studyArea, typeOfTeaching, lgac, ies, prodepParticipation, position, academicGroup_Keycode, password FROM member WHERE FullName = ?";
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, memberFullName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                member = new Member();
                member.setFullName(resultSet.getString("FullName"));
                member.setDateOfBirth(resultSet.getDate("DateOfBirth"));
                member.setCurp(resultSet.getString("CURP"));
                member.setPhoneNumber(resultSet.getString("PhoneNumber"));
                member.setInstitutionalMail(resultSet.getString("InstitutionalMail"));
                member.setDiscipline(resultSet.getString("Discipline"));
                member.setStudyGrade(resultSet.getString("StudyGrade"));
                member.setStudyArea(resultSet.getString("StudyArea"));
                member.setTypeOfTeaching(resultSet.getString("TypeOfTeaching"));
                member.setLgac(resultSet.getString("Lgac"));
                member.setIes(resultSet.getString("Ies"));
                member.setProdepParticipation(resultSet.getString("ProdepParticipation"));
                member.setPosition(resultSet.getString("Position"));
                member.setKeycodeAcademicGroup(resultSet.getString("academicGroup_Keycode"));
                member.setPassword(resultSet.getString("password"));
            }  
        } catch (SQLException ex) {            
            throw new BusinessException("DatabaseConnectionError", ex);       
        } finally {
            CONNECTION.disconnect();
        }
        return member;
    }

    @Override
    public int registerMember(Member member) throws BusinessException {
        int result = 0;
        String dateOfBirth = (new SimpleDateFormat("yyyy-MM-dd").format(member.getDateOfBirth()));        
        try {
            String query = ("INSERT INTO member (fullName, dateOfBirth, curp, phoneNumber, institutionalMail, discipline, studyGrade, studyArea, typeOfTeaching, lgac, ies, prodepParticipation, position, academicGroup_Keycode) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, member.getFullName());
            preparedStatement.setString(2, dateOfBirth);
            preparedStatement.setString(3, member.getCurp());
            preparedStatement.setString(4, member.getPhoneNumber());
            preparedStatement.setString(5, member.getInstitutionalMail());
            preparedStatement.setString(6, member.getDiscipline());
            preparedStatement.setString(7, member.getStudyGrade());
            preparedStatement.setString(8, member.getStudyArea());
            preparedStatement.setString(9, member.getTypeOfTeaching());
            preparedStatement.setString(10, member.getLgac());
            preparedStatement.setString(11, member.getIes());
            preparedStatement.setString(12, member.getProdepParticipation());
            preparedStatement.setString(13, member.getPosition());
            preparedStatement.setString(14, member.getKeycodeAcademicGroup());
            result = preparedStatement.executeUpdate();
        } catch (SQLException ex) {            
            throw new BusinessException("DatabaseConnectionError", ex);       
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }

    @Override
    public int removeMember(String fullName) throws BusinessException {
        String query = "DELETE FROM member WHERE fullName = ?";
        int result = 0;
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, fullName);
            result = preparedStatement.executeUpdate();
        } catch (SQLException ex) {            
            throw new BusinessException("DatabaseConnectionError", ex);       
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }

    @Override
    public int modifyMember(Member newMember, String fullName) throws BusinessException {        
        String dateOfBirth = (new SimpleDateFormat("yyyy-MM-dd").format(newMember.getDateOfBirth()));
        int result = 0;
        try {
            String query = "UPDATE member SET fullName = ?, dateOfBirth = ?, curp = ?, phoneNumber = ?, institutionalMail = ?, discipline = ?, studyGrade = ?, studyArea = ?, typeOfTeaching = ?, lgac = ?, ies = ?, prodepParticipation = ?, position = ?, academicGroup_Keycode = ? WHERE fullName = ?";
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, newMember.getFullName());
            preparedStatement.setString(2, dateOfBirth);
            preparedStatement.setString(3, newMember.getCurp());
            preparedStatement.setString(4, newMember.getPhoneNumber());
            preparedStatement.setString(5, newMember.getInstitutionalMail());
            preparedStatement.setString(6, newMember.getDiscipline());
            preparedStatement.setString(7, newMember.getStudyGrade());
            preparedStatement.setString(8, newMember.getStudyArea());
            preparedStatement.setString(9, newMember.getTypeOfTeaching());
            preparedStatement.setString(10, newMember.getLgac());
            preparedStatement.setString(11, newMember.getIes());
            preparedStatement.setString(12, newMember.getProdepParticipation());
            preparedStatement.setString(13, newMember.getPosition());
            preparedStatement.setString(14, newMember.getKeycodeAcademicGroup());
            preparedStatement.setString(15, fullName);
            result = preparedStatement.executeUpdate();
        } catch (SQLException ex) {            
            throw new BusinessException("DatabaseConnectionError", ex);       
        } finally {
            CONNECTION.disconnect();
        }      
        return result;
    }
    
    @Override
    public int updatePasswordOfMember(String password, String fullName) throws BusinessException {
        String query = "UPDATE member SET password = HEX(AES_ENCRYPT(?, 'AES')) WHERE FullName = ?";
        int result = 0; 
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, fullName);            
            result = preparedStatement.executeUpdate();
        } catch (SQLException ex) {            
            throw new BusinessException("DatabaseConnectionError", ex);       
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }
    
    @Override
    public String decryptMemberPassword(String password) throws BusinessException {
        String query = "SELECT AES_DECRYPT(UNHEX(?), 'AES')";
        String result = null;
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getString(1);
            }  
        } catch (SQLException ex) {            
            throw new BusinessException("DatabaseConnectionError", ex);       
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }
}