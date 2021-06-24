package businesslogic;


import dataaccess.Conexion;
import domain.Constancy;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Antonio de Jesús Dominguez García
 */
public class ConstancyDAO implements IConstancyDAO {
    
    private final Conexion CONNECTION = new Conexion();
    @Override
    public ArrayList<Constancy> consultConstancyList() {
        ArrayList<Constancy> arrayListConstancys = new ArrayList<>();
        
        try {
            CONNECTION.connect();
            String query = "SELECT ID_Constancy, RecognitionType, Description, InstitutionalMailOfReceivers, IntitucionalMailValidator, InstitutionalNailRedpient, RegulatoryNotes, event_Title FROM constancy";
            Statement statement = CONNECTION.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                arrayListConstancys.add(new Constancy(resultSet.getInt("ID_Constancy"), resultSet.getString("RecognitionType"), resultSet.getString("Description"), resultSet.getString("InstitutionalMailOfReceivers"), resultSet.getString("IntitucionalMailValidator"), resultSet.getString("InstitutionalNailRedpient"), resultSet.getString("RegulatoryNotes"), resultSet.getString("event_Title"))); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(Constancy.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CONNECTION.disconnect();
        }
        return arrayListConstancys;
    }

    @Override
    public Constancy checkConstancy(int idConstancy) {        
        
        Constancy constancyChecked = new Constancy();
        
        try {
            CONNECTION.connect();
            String query = "SELECT ID_Constancy, RecognitionType, Description, InstitutionalMailOfReceivers, IntitucionalMailValidator, InstitutionalNailRedpient, RegulatoryNotes, event_Title FROM constancy WHERE ID_Constancy = ?";
            PreparedStatement  preparedStatement  = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idConstancy);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                constancyChecked.setRecognitionType(resultSet.getString("ID_Constancy"));
                constancyChecked.setRecognitionType(resultSet.getString("RecognitionType"));
                constancyChecked.setDescription(resultSet.getString("Description"));
                constancyChecked.setInstitutionalMailReceivers(resultSet.getString("InstitutionalMailOfReceivers"));
                constancyChecked.setInstitutionalMailValidator(resultSet.getString("IntitucionalMailValidator"));
                constancyChecked.setInstitutionalMailRedPient(resultSet.getString("InstitutionalNailRedpient"));
                constancyChecked.setRegulatoryNote(resultSet.getString("RegulatoryNotes"));
                constancyChecked.setEventTitle(resultSet.getString("event_Title"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Constancy.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CONNECTION.disconnect();
        }
        return constancyChecked;
    }

    @Override
    public int generateConstancy(Constancy constancy) {        
        int result = 0;
        try {
            String query = "INSERT INTO constancy (RecognitionType, Description, InstitutionalMailOfReceivers, IntitucionalMailValidator, InstitutionalNailRedpient, RegulatoryNotes, event_Title) VALUES (?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, constancy.getRecognitionType());
            preparedStatement.setString(2, constancy.getDescription());
            preparedStatement.setString(3,  constancy.getInstitutionalMailReceivers());
            preparedStatement.setString(4, constancy.getInstitutionalMailValidator());
            preparedStatement.setString(5,  constancy.getInstitutionalMailRedPient());
            preparedStatement.setString(6, constancy.getRegulatoryNote());
            preparedStatement.setString(7, constancy.getEventTitle());
            result = preparedStatement.executeUpdate();
        } catch (SQLException ex ) {
            Logger.getLogger(ConstancyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CONNECTION.disconnect();
        }
        
        return result;
    }

}
