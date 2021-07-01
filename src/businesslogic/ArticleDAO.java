package businesslogic;

import dataaccess.Conexion;
import domain.Article;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * 
 * @author daniCV
 */

public class ArticleDAO implements IArticleDAO {
    private final Conexion CONNECTION = new Conexion();
    
    @Override
    public int addNewArticle(Article article) throws BusinessException {
        String query = "INSERT INTO article (evidence_idEvidence, Author, Mail, PublicationDate, ISSN, MegazineName, EndPage, Country, Volumen) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String publicationDate = (new SimpleDateFormat("yyyy-MM-dd").format(article.getPublicationDate()));
        int result = 0;
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, article.getIdEvidence());
            preparedStatement.setString(2, article.getAuthor());
            preparedStatement.setString(3, article.getMail());
            preparedStatement.setString(4, publicationDate);
            preparedStatement.setString(5, article.getIssn());
            preparedStatement.setString(6, article.getMegazineName());
            preparedStatement.setInt(7, article.getEndPage());
            preparedStatement.setString(8, article.getCountry());
            preparedStatement.setInt(9, article.getVolumen());            
            result = preparedStatement.executeUpdate();        
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }

    @Override
    public int modifyArticle(Article article, int idEvidence) throws BusinessException {
        String query = "UPDATE article SET Author = ?, Mail = ?, PublicationDate = ?, ISSN = ?, MegazineName = ?, EndPage = ?, Country = ?, Volumen = ? WHERE evidence_idEvidence = ?";
        String publicationDate = (new SimpleDateFormat("yyyy-MM-dd").format(article.getPublicationDate()));
        int result = 0;
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setString(1, article.getAuthor());
            preparedStatement.setString(2, article.getMail());
            preparedStatement.setString(3, publicationDate);
            preparedStatement.setString(4, article.getIssn());
            preparedStatement.setString(5, article.getMegazineName());
            preparedStatement.setInt(6, article.getEndPage());
            preparedStatement.setString(7, article.getCountry());
            preparedStatement.setInt(8, article.getVolumen());
            preparedStatement.setInt(9, idEvidence);
            result = preparedStatement.executeUpdate();
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return result;
    }

    @Override
    public Article consultArticle(int idEvidence) throws BusinessException {
        String query = "SELECT evidence_idEvidence, Author, Mail, PublicationDate, ISSN, MegazineName, EndPage, Country, Volumen FROM article WHERE evidence_idEvidence = ?";
        Article article = new Article();
        try {
            PreparedStatement preparedStatement = CONNECTION.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idEvidence);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                article.setIdEvidence(resultSet.getInt("evidence_idEvidence"));
                article.setAuthor(resultSet.getString("Author"));
                article.setMail(resultSet.getString("Mail"));
                article.setPublicationDate(resultSet.getDate("PublicationDate"));
                article.setIssn(resultSet.getString("ISSN"));
                article.setMegazineName(resultSet.getString("MegazineName"));
                article.setCountry(resultSet.getString("Country"));
                article.setVolumen(resultSet.getInt("Volumen"));
            }  
        } catch (SQLException sQLException) {            
            throw new BusinessException("DatabaseConnectionError", sQLException);
        } finally {
            CONNECTION.disconnect();
        }
        return article;
    }    
}
