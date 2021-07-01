package businesslogic;

import dataaccess.Conexion;
import domain.GeneralCurriculum;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Javier Blas
 */
public class GeneralCurriculumDAO implements IGeneralCurriculumDAO {

    private final Conexion CONNECTION = new Conexion();

    @Override
    public GeneralCurriculum consultGeneralCurriculum(String keycode) throws BusinessException {
        String query = "SELECT KeyCode, Title, DependencyInstitute, FacultyOfTheInstitution, DegreeOfConsolidation, NumberOfParticipants, NumberOfColaborators, RegistrationDate, LGAC, GeneralPurpose, Mission, Vision  FROM academicgroup WHERE keycode = ?";
        GeneralCurriculum generalCurriculum = null; 
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, keycode);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                generalCurriculum = new GeneralCurriculum();
                generalCurriculum.setKeyCode(resultSet.getString("KeyCode"));
                generalCurriculum.setTitle(resultSet.getString("Title"));
                generalCurriculum.setDependencyInstitute(resultSet.getString("DependencyInstitute"));
                generalCurriculum.setFacultyOfTheInstitution(resultSet.getString("FacultyOfTheInstitution"));
                generalCurriculum.setDegreeOfConsolidation(resultSet.getString("DegreeOfConsolidation"));
                generalCurriculum.setNumberOfParticipants(resultSet.getInt("NumberOfParticipants"));
                generalCurriculum.setNumberOfColaborators(resultSet.getInt("NumberOfColaborators"));
                generalCurriculum.setRegistrationDate(resultSet.getDate("RegistrationDate"));
                generalCurriculum.setLgac(resultSet.getString("LGAC"));
                generalCurriculum.setGeneralPurpose(resultSet.getString("GeneralPurpose"));
                generalCurriculum.setMission(resultSet.getString("Mission"));
                generalCurriculum.setVision(resultSet.getString("Vision"));
            }  
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return generalCurriculum;
    }
    
    @Override
    public int modifyGeneralCurriculum(GeneralCurriculum generalCurriculum, String keycode) throws BusinessException {
        int result = 0;
        String registrationDate = (new SimpleDateFormat("yyyy-MM-dd").format(generalCurriculum.getRegistrationDate()));
        try {
            String query = "UPDATE academicgroup SET KeyCode = ?, Title = ?, DependencyInstitute = ?, FacultyOfTheInstitution = ?, DegreeOfConsolidation = ?, NumberOfParticipants = ?, NumberOfColaborators = ?, RegistrationDate = ?, LGAC = ?, GeneralPurpose = ?, Mission = ?, Vision = ? WHERE KeyCode = ?";
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, generalCurriculum.getKeyCode());
            preparedStatement.setString(2, generalCurriculum.getTitle());
            preparedStatement.setString(3, generalCurriculum.getDependencyInstitute());
            preparedStatement.setString(4, generalCurriculum.getFacultyOfTheInstitution());
            preparedStatement.setString(5, generalCurriculum.getDegreeOfConsolidation());
            preparedStatement.setInt(6, generalCurriculum.getNumberOfParticipants());
            preparedStatement.setInt(7, generalCurriculum.getNumberOfColaborators());
            preparedStatement.setString(8, registrationDate);
            preparedStatement.setString(9, generalCurriculum.getLgac());
            preparedStatement.setString(10, generalCurriculum.getGeneralPurpose());
            preparedStatement.setString(11, generalCurriculum.getMission());
            preparedStatement.setString(12, generalCurriculum.getVision());
            preparedStatement.setString(13, keycode);
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }
}