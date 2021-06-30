package businesslogic;

import java.sql.SQLException;

public final class BusinessException extends SQLException {
    public BusinessException(String message, SQLException sQLException){
        super(message);   
    }
    
    public BusinessException(String message){
        super(message);
    }  
}