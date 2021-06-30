package gui;

import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.StringLengthValidator;
import javafx.scene.image.ImageView;

/**
 * 
 * @author daniCV
 */

public class TextFieldValidation {
    private final RequiredFieldValidator REQUIRED_FIELD_VALIDATOR = new RequiredFieldValidator();
    private final NumberValidator NUMBER_VALIDATOR = new NumberValidator();
    private final RegexValidator EMAIL_VALIDATOR = new RegexValidator();
    private final StringLengthValidator STRING_LENGTH_VALIDATOR_TITLE = new StringLengthValidator(50);
    private final StringLengthValidator STRING_LENGTH_VALIDATOR_CURP = new StringLengthValidator(20);
    private final StringLengthValidator STRING_LENGTH_VALIDATOR_PHONE = new StringLengthValidator(10);
    private final StringLengthValidator STRING_LENGTH_VALIDATOR_INT = new StringLengthValidator(4);
    private final StringLengthValidator STRING_LENGTH_VALIDATOR_DESCRIPTION = new StringLengthValidator(200);
    
    public TextFieldValidation() {
        EMAIL_VALIDATOR.setRegexPattern("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
        EMAIL_VALIDATOR.setMessage("Esto no parece ser un correo");
        EMAIL_VALIDATOR.setIcon(new ImageView("images\\exclamationMark.png"));  
        
        STRING_LENGTH_VALIDATOR_TITLE.setMessage("Longitud excedida 30 carácteres");
        STRING_LENGTH_VALIDATOR_TITLE.setIcon(new ImageView("images\\exclamationMark.png"));
        
        STRING_LENGTH_VALIDATOR_CURP.setMessage("Longitud excedida 20 carácteres");
        STRING_LENGTH_VALIDATOR_CURP.setIcon(new ImageView("images\\exclamationMark.png"));
        
        STRING_LENGTH_VALIDATOR_PHONE.setMessage("Longitud excedida 10 carácteres");
        STRING_LENGTH_VALIDATOR_PHONE.setIcon(new ImageView("images\\exclamationMark.png"));
        
        STRING_LENGTH_VALIDATOR_INT.setMessage("Longitud excedida 4 carácteres");
        STRING_LENGTH_VALIDATOR_INT.setIcon(new ImageView("images\\exclamationMark.png"));
        
        STRING_LENGTH_VALIDATOR_DESCRIPTION.setMessage("Longitud excedida 200 carácteres");
        STRING_LENGTH_VALIDATOR_DESCRIPTION.setIcon(new ImageView("images\\exclamationMark.png"));
        
        REQUIRED_FIELD_VALIDATOR.setMessage("Campo obligatorio");
        REQUIRED_FIELD_VALIDATOR.setIcon(new ImageView("images\\exclamationMark.png"));
        
        NUMBER_VALIDATOR.setMessage("Únicamente valores numéricos");
        NUMBER_VALIDATOR.setIcon(new ImageView("images\\exclamationMark.png"));                    
    }

    public RequiredFieldValidator getREQUIRED_FIELD_VALIDATOR() {
        return REQUIRED_FIELD_VALIDATOR;
    }

    public NumberValidator getNUMBER_VALIDATOR() {
        return NUMBER_VALIDATOR;
    }

    public RegexValidator getEMAIL_VALIDATOR() {
        return EMAIL_VALIDATOR;
    } 
    
    public StringLengthValidator getSTRING_LENGTH_VALIDATOR_TITLE() {
        return STRING_LENGTH_VALIDATOR_TITLE;
    }
    
    public StringLengthValidator getSTRING_LENGTH_VALIDATOR_CURP() {
        return STRING_LENGTH_VALIDATOR_CURP;
    }
    
    public StringLengthValidator getSTRING_LENGTH_VALIDATOR_PHONE() {
        return STRING_LENGTH_VALIDATOR_PHONE;
    }
    
    public StringLengthValidator getSTRING_LENGTH_VALIDATOR_INT() {
        return STRING_LENGTH_VALIDATOR_INT;
    }
    
    public StringLengthValidator getSTRING_LENGTH_VALIDATOR_DESCRIPTION() {
        return STRING_LENGTH_VALIDATOR_DESCRIPTION;
    }
}