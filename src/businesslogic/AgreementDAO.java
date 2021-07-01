package businesslogic;

import dataaccess.Conexion;
import domain.Agreement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public class AgreementDAO implements IAgreementDAO{
    private final Conexion CONNECTION = new Conexion();

    @Override
    public int registerAgreement(Agreement agreement) throws BusinessException{
        int result;
        result = 0;
        try {
            String query = "INSERT INTO agreement (Number, Description, LeaderName, Date, meeting_idMeeting) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, agreement.getNumber());
            preparedStatement.setString(2, agreement.getDescription());
            preparedStatement.setString(3, agreement.getManager());
            preparedStatement.setString(4, agreement.getDate());
            preparedStatement.setInt(5, agreement.getIdMeeting());
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }

    @Override  
    public int deleteAgreement(int idMeeting) throws BusinessException{
        String query = "DELETE FROM agreement WHERE meeting_idMeeting = ?";
        int result = 0;
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idMeeting);
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        }
        return result;
    }

    @Override
    public ArrayList<Agreement> consultAgreement(int idMeeting) throws BusinessException{
        ArrayList<Agreement> agreementConsulted = new ArrayList<>();
        
        try {
            CONNECTION.connect();
            String query = "SELECT Number, Description, LeaderName, Date, meeting_idMeeting FROM agreement WHERE meeting_idMeeting = ?";
            PreparedStatement  preparedStatement  = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idMeeting);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                agreementConsulted.add(new Agreement(resultSet.getString("Description"), resultSet.getString("LeaderName"), resultSet.getString("Date"), resultSet.getInt("Number"), resultSet.getInt("meeting_idMeeting"))); 
            }
        } catch (SQLException sQLException) {
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return agreementConsulted;
    }
    
}
