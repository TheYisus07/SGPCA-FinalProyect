package businesslogic;

import dataaccess.Conexion;
import domain.PersonalCurriculum;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Javier Blas
 */
public class PersonalCurriculumDAO implements IPersonalCurriculumDAO{
    
    private final Conexion CONNECTION = new Conexion();
    
    @Override
    public PersonalCurriculum consultPersonalCurriculum(String personalfullName) throws BusinessException {
        String query = "SELECT FullName, DateOfBirth, CURP, PhoneNumber, InstitutionalMail, Discipline, StudyGrade, StudyArea, TypeOfTeaching, LGAC, IES, ProdepParticipation, Position, AcademicGroup_KeyCode FROM member WHERE FullName = ?";
        PersonalCurriculum personalCurriculum = null; 
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, personalfullName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                personalCurriculum = new PersonalCurriculum();
                personalCurriculum.setFullName(resultSet.getString("FullName"));                    
                personalCurriculum.setDateOfBirth(resultSet.getDate("DateOfBirth"));
                personalCurriculum.setCurp(resultSet.getString("CURP"));
                personalCurriculum.setPhoneNumber(resultSet.getString("PhoneNumber"));
                personalCurriculum.setInstitutionalMail(resultSet.getString("InstitutionalMail"));
                personalCurriculum.setDiscipline(resultSet.getString("Discipline"));
                personalCurriculum.setStudyGrade(resultSet.getString("StudyGrade"));
                personalCurriculum.setStudyArea(resultSet.getString("StudyArea"));
                personalCurriculum.setTypeOfTeaching(resultSet.getString("TypeOfTeaching"));
                personalCurriculum.setLgac(resultSet.getString("LGAC"));
                personalCurriculum.setIes(resultSet.getString("IES"));
                personalCurriculum.setProdepParticipation(resultSet.getString("ProdepParticipation"));
                personalCurriculum.setPositionCA(resultSet.getString("position"));
                personalCurriculum.setKeycode(resultSet.getString("AcademicGroup_KeyCode"));
            }  
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return personalCurriculum;
    }
    
    @Override
    public int modifyPersonalCurriculum(PersonalCurriculum personalCurriculum, String fullName) throws BusinessException {
        int result = 0;
        try {
            String query = "UPDATE member SET fullName = ?, dateOfBirth = ?, curp = ?, phoneNumber = ?, institutionalMail = ?, discipline = ?, studyGrade = ?, studyArea = ?, typeOfTeaching = ?, lgac = ?, ies = ?, prodepParticipation = ?, position = ?, AcademicGroup_KeyCode = ? WHERE fullName = ?";
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, personalCurriculum.getFullName());
            preparedStatement.setDate(2, (Date) personalCurriculum.getDateOfBirth());
            preparedStatement.setString(3, personalCurriculum.getCurp());
            preparedStatement.setString(4, personalCurriculum.getPhoneNumber());
            preparedStatement.setString(5, personalCurriculum.getInstitutionalMail());
            preparedStatement.setString(6, personalCurriculum.getDiscipline());
            preparedStatement.setString(7, personalCurriculum.getStudyGrade());
            preparedStatement.setString(8, personalCurriculum.getStudyArea());
            preparedStatement.setString(9, personalCurriculum.getTypeOfTeaching());
            preparedStatement.setString(10, personalCurriculum.getLgac());
            preparedStatement.setString(11, personalCurriculum.getIes());
            preparedStatement.setString(12, personalCurriculum.getProdepParticipation());
            preparedStatement.setString(13, personalCurriculum.getPositionCA());
            preparedStatement.setString(14, personalCurriculum.getKeycode());
            preparedStatement.setString(15, fullName);
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }
}