package utils;


import com.crux.common.exception.BusinessException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * 实体对象校验
 *
 */
public class ValidatorUtil {

	/**
	 * 使用 JSP-303 的 Validate 来校验数据。
	 * @param value
	 * @throws BusinessException
	 */
	public <T> void validate(T value) throws BusinessException {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = (Validator) factory.usingContext().getValidator();
		Set<ConstraintViolation<T>> violations = validator.validate(value);

		if (violations.size() > 0) {
			StringBuffer message = new StringBuffer();
			for (ConstraintViolation<T> violation : violations) {
				message.append(violation.getMessage() + "<BR>\n");
			}
			throw new BusinessException(message.toString());
		}

	}

}
