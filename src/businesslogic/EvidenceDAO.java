package businesslogic;

import dataaccess.Conexion;
import domain.Evidence;
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
 * @author daniCV
 */

public class EvidenceDAO implements IEvidenceDAO {
    private final Conexion CONNECTION = new Conexion();

    @Override
    public ArrayList<Evidence> consultEvidenceList() throws BusinessException {
        String query = "SELECT idEvidence, RegistrationDate, ActualStatus, ImpactOnCA, Title, member_FullName, AcademicGroup_KeyCode, project_Title FROM evidence";
        ArrayList<Evidence> arrayListEvidence = new ArrayList<>();     
        try {
            Statement statement = CONNECTION.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                arrayListEvidence.add(new Evidence(resultSet.getInt("idEvidence"), resultSet.getDate("RegistrationDate"), resultSet.getString("ActualStatus"), resultSet.getString("ImpactOnCA"), resultSet.getString("Title"), resultSet.getString("member_FullName"), resultSet.getString("AcademicGroup_KeyCode"), resultSet.getString("project_Title"))); 
            }
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return arrayListEvidence;
    } 

    @Override
    public int getLastInsertEvidenceId() throws BusinessException {
        String query = "SELECT MAX(idEvidence) AS idEvidence FROM Evidence";
        int lastEvidenceId = 0;
        try {
            Statement statement = CONNECTION.getConnection().createStatement();            
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {                
                lastEvidenceId = resultSet.getInt(1);
            }            
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return lastEvidenceId;
    }
    
    @Override
    public int addNewEvidenceWithoutProject(Evidence evidence) throws BusinessException {
        String registrationDate = (new SimpleDateFormat("yyyy-MM-dd").format(evidence.getRegistrationDate()));
        String query = "INSERT INTO evidence (RegistrationDate, ActualStatus, ImpactOnCA, Title, member_FullName, AcademicGroup_KeyCode) VALUES (?, ?, ?, ?, ?, ?)";
        int result = 0;
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, registrationDate);
            preparedStatement.setString(2, evidence.getActualStatus());
            preparedStatement.setString(3, evidence.getImpactOnCA());
            preparedStatement.setString(4, evidence.getTitle());
            preparedStatement.setString(5, evidence.getMemberFullName());
            preparedStatement.setString(6, evidence.getKeycodeAcademicGroup());
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }

    @Override
    public int addNewEvidenceWithProject(Evidence evidence) throws BusinessException {
        String registrationDate = (new SimpleDateFormat("yyyy-MM-dd").format(evidence.getRegistrationDate()));
        String query = "INSERT INTO evidence (RegistrationDate, ActualStatus, ImpactOnCA, Title, member_FullName, AcademicGroup_KeyCode, project_Title) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int result = 0;
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, registrationDate);
            preparedStatement.setString(2, evidence.getActualStatus());
            preparedStatement.setString(3, evidence.getImpactOnCA());
            preparedStatement.setString(4, evidence.getTitle());
            preparedStatement.setString(5, evidence.getMemberFullName());
            preparedStatement.setString(6, evidence.getKeycodeAcademicGroup());
            preparedStatement.setString(7, evidence.getProjectTitle());
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }
    
    @Override
    public Evidence consultEvidenceById(int idEvidence) throws BusinessException {
        String query = "SELECT idEvidence, RegistrationDate, ActualStatus, ImpactOnCA, Title, member_FullName, AcademicGroup_KeyCode, project_Title FROM evidence WHERE idEvidence = ?";
        Evidence evidence = new Evidence(); 
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idEvidence);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                evidence.setIdEvidence(resultSet.getInt("idEvidence"));
                evidence.setRegistrationDate(resultSet.getDate("RegistrationDate"));
                evidence.setActualStatus(resultSet.getString("ActualStatus"));
                evidence.setImpactOnCA(resultSet.getString("ImpactOnCA"));
                evidence.setTitle(resultSet.getString("Title"));
                evidence.setMemberFullName(resultSet.getString("member_FullName"));
                evidence.setKeycodeAcademicGroup(resultSet.getString("AcademicGroup_KeyCode"));
                evidence.setProjectTitle(resultSet.getString("project_Title"));
            }  
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return evidence;
    }

    @Override
    public int deleteEvidence(int idEvidence) throws BusinessException {
        String query = "DELETE FROM evidence WHERE idEvidence = ?";
        int result = 0;
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idEvidence);
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }
    
    @Override
    public int modifyEvidence(Evidence evidence, int idEvidence) throws BusinessException {
        String query = "UPDATE evidence SET RegistrationDate = ?, ActualStatus = ?, ImpactOnCA = ?, Title = ?, project_Title = ? WHERE idEvidence = ?";
        String registrationDate = (new SimpleDateFormat("yyyy-MM-dd").format(evidence.getRegistrationDate()));
        int result = 0;
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, registrationDate);
            preparedStatement.setString(2, evidence.getActualStatus());
            preparedStatement.setString(3, evidence.getImpactOnCA());
            preparedStatement.setString(4, evidence.getTitle());
            preparedStatement.setString(5, evidence.getProjectTitle());
            preparedStatement.setInt(6, idEvidence);
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }
}