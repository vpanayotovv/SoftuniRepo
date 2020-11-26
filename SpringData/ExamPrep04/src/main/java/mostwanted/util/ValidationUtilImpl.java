package mostwanted.util;

import javax.validation.Validation;
import javax.validation.Validator;

public class ValidationUtilImpl implements ValidationUtil {
    private Validator validator;


    public ValidationUtilImpl() {
        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }

    @Override
    public <T> boolean isValid(T entity)     {
        return this.validator.validate(entity).isEmpty();
    }
}
