package businesslogic;

import dataaccess.Conexion;
import domain.ReceptionWork;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 *
 * @author daniCV
 */

public class ReceptionWorkDAO implements IReceptionWorkDAO {

    private final Conexion CONNECTION = new Conexion();

    @Override
    public int addReceptionWork(ReceptionWork receptionwork) throws BusinessException {
        String finalDate = (new SimpleDateFormat("yyyy-MM-dd").format(receptionwork.getFinalDate()));
        String startDate = (new SimpleDateFormat("yyyy-MM-dd").format(receptionwork.getStartDate()));        
        String query = "INSERT INTO receptionwork (evidence_idEvidence, Direction, FinalDate, StartDate, Grade, NumberStudents, Kind, blueprint_Title) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int result = 0;
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, receptionwork.getIdEvidence());
            preparedStatement.setString(2, receptionwork.getDirection());              
            preparedStatement.setString(3, finalDate);
            preparedStatement.setString(4, startDate);
            preparedStatement.setString(5, receptionwork.getGrade());
            preparedStatement.setInt(6, receptionwork.getNumberStudents());
            preparedStatement.setString(7, receptionwork.getKind());
            preparedStatement.setString(8, receptionwork.getBlueprintTitle());
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }
   
    @Override
    public int modifyReceptionWork(ReceptionWork receptionwork, int idEvidence) throws BusinessException {
        String finalDate = (new SimpleDateFormat("yyyy-MM-dd").format(receptionwork.getFinalDate()));
        String startDate = (new SimpleDateFormat("yyyy-MM-dd").format(receptionwork.getStartDate()));
        
        String query = "UPDATE receptionwork SET Direction = ?, FinalDate = ?, StartDate = ?, Grade = ?, NumberStudents = ?, Kind = ?, blueprint_Title = ? WHERE evidence_idEvidence = ?";
        int result = 0;
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, receptionwork.getDirection());              
            preparedStatement.setString(2, finalDate);
            preparedStatement.setString(3, startDate);
            preparedStatement.setString(4, receptionwork.getGrade());
            preparedStatement.setInt(5, receptionwork.getNumberStudents());
            preparedStatement.setString(6, receptionwork.getKind());
            preparedStatement.setString(7, receptionwork.getBlueprintTitle());
            preparedStatement.setInt(8, idEvidence);
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }

    @Override
    public ReceptionWork consultReceptionWork(int idEvidence) throws BusinessException {
        String query = "SELECT evidence_idEvidence, Direction, FinalDate, StartDate, Grade, NumberStudents, Kind, blueprint_Title FROM receptionwork WHERE evidence_idEvidence = ?";
        ReceptionWork receptionwork = new ReceptionWork();
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idEvidence);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                receptionwork.setIdEvidence(resultSet.getInt("evidence_idEvidence"));  
                receptionwork.setDirection(resultSet.getString("Direction"));
                receptionwork.setFinalDate(resultSet.getDate("FinalDate"));
                receptionwork.setStartDate(resultSet.getDate("StartDate"));
                receptionwork.setGrade(resultSet.getString("Grade"));
                receptionwork.setNumberStudents(resultSet.getInt("NumberStudents"));
                receptionwork.setKind(resultSet.getString("Kind"));
                receptionwork.setBlueprintTitle(resultSet.getString("blueprint_Title"));
            }  
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return receptionwork; 
    }
}