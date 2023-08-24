package espol.edu.ec.bikeusuario.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import espol.edu.ec.bikeusuario.model.SecuenciaPrimaria;
import espol.edu.ec.bikeusuario.repository.SecuenciaPrimariaRepository;


@Service
public class FuncionesGenerales {
	
	@Autowired
	private SecuenciaPrimariaRepository repository;
	
	public Long obtenerSecuenciaPrimaria(Long codigoSecuencia) {
		if(codigoSecuencia== null ||codigoSecuencia==0) {
			return 0l;
		}
		if(!repository.findById(codigoSecuencia).isPresent()) {
			return 0l;
		}
		SecuenciaPrimaria secPrim=repository.findById(codigoSecuencia).get();
		
		if (secPrim == null ) {
			return 0l;
		}
		
		Long id = secPrim.getValorActual() + secPrim.getIncrementaEn();
		secPrim.setValorActual(id);
		repository.save(secPrim);
		return id;
	}
	
	public void validar(Object objeto, Class<?> group) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Object>> violations = validator.validate(objeto, group);
		if (!violations.isEmpty()) {
			List<String> errores = new ArrayList<String>();
			for (ConstraintViolation<Object> violation : violations) {
				errores.add(violation.getMessage());
			}
			throw new DataException(errores.toString(), null);
		}
	}
}
