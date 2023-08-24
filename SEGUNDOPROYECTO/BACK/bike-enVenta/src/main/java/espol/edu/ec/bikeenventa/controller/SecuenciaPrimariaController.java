package espol.edu.ec.bikeenventa.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import espol.edu.ec.bikeenventa.model.SecuenciaPrimaria;
import espol.edu.ec.bikeenventa.repository.SecuenciaPrimariaRepository;
import espol.edu.ec.bikeenventa.utils.FuncionesGenerales;
import espol.edu.ec.bikeenventa.utils.ManejadorExcepciones;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;


@RestController
@RequestMapping("/secuenciaPrimaria")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SecuenciaPrimariaController {
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@Autowired
	private FuncionesGenerales fg;
	
	@Autowired
	private SecuenciaPrimariaRepository repository;
	
	public List<?> consultar() {
		return repository.findAll();
	}
	
	@GetMapping(path = "/{codigo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> consultarPorCodigo(@PathVariable Long codigo) {
		Optional<SecuenciaPrimaria> secuenciaPrimaria = repository.findById(codigo);
		if (!secuenciaPrimaria.isPresent()) {
			return ManejadorExcepciones.error(HttpStatus.NOT_FOUND, null, 
					"Secuencia con código "+codigo+" no existe");
		}
		return new ResponseEntity<SecuenciaPrimaria>(secuenciaPrimaria.get(),HttpStatus.OK) ;
		
	}
	
	@PostMapping
	public ResponseEntity<?> ingresar(@RequestBody SecuenciaPrimaria transaccion,
			HttpServletRequest request) {

		fg.validar(transaccion, SecuenciaPrimaria.SecuenciaPrimariaCreation.class);
		
		if(repository.existsById(transaccion.getCodigo())) {
			return ManejadorExcepciones.error(HttpStatus.NOT_FOUND, null,
					"Secuencia con código "+ transaccion.getCodigo()+" existe");
		}
		
		repository.save(transaccion);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}")
				.buildAndExpand(transaccion.getCodigo()).toUri();
		return ResponseEntity.created(location).build();

	}
	
	@PostMapping("/varios")
	public ResponseEntity<?> ingresarVarios(@RequestBody List<SecuenciaPrimaria> transacciones, 
			HttpServletRequest request) {

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				for (SecuenciaPrimaria transaccion : transacciones) {
					fg.validar(transaccion, SecuenciaPrimaria.SecuenciaPrimariaCreation.class);
					if(repository.existsById(transaccion.getCodigo())) {
						throw new DataIntegrityViolationException(
								"Secuencia con código "+ transaccion.getCodigo()+" existe.");
					}
					
					repository.save(transaccion);
				}
			}
		});
		return new ResponseEntity<List<SecuenciaPrimaria>>(transacciones, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<?> actualizar(/* @Validated(AgePerfiles.PerfilUpdate.class) */
			@RequestBody SecuenciaPrimaria transaccion, HttpServletRequest request) {
		fg.validar(transaccion, SecuenciaPrimaria.SecuenciaPrimariaUpdate.class);
		Optional<SecuenciaPrimaria> hagLicenAplicaSecu= repository.findById(transaccion.getCodigo());
		
		if (!hagLicenAplicaSecu.isPresent()) {
			return ManejadorExcepciones.error(HttpStatus.NOT_FOUND, null,
					"Secuencia con código "+ transaccion.getCodigo()+" no encontrado");
		}
		if (transaccion.getCiclica()==null || transaccion.getCiclica().equals("")) {
			transaccion.setCiclica(hagLicenAplicaSecu.get().getCiclica());
		}
		if (transaccion.getDescripcion()==null || transaccion.getDescripcion().equals("")) {
			transaccion.setDescripcion(hagLicenAplicaSecu.get().getDescripcion());
		}
		if (transaccion.getIncrementaEn()==null || transaccion.getIncrementaEn()<=0) {
			transaccion.setIncrementaEn(hagLicenAplicaSecu.get().getIncrementaEn());
		}
		if (transaccion.getValorActual()==null || transaccion.getValorActual()<0) {
			transaccion.setValorActual(hagLicenAplicaSecu.get().getValorActual());
		}
		if (transaccion.getValorInicial()==null || transaccion.getValorInicial()<0) {
			transaccion.setValorInicial(hagLicenAplicaSecu.get().getValorInicial());
		}
		
		repository.save(transaccion);

		return new ResponseEntity<String>("", HttpStatus.OK);
	}
	
	@ApiOperation(value = "", authorizations = { @Authorization(value = "Autorización token módulo hag") })
	@PutMapping("/varios")
	public ResponseEntity<?> actualizarVarios(@RequestBody List<SecuenciaPrimaria> transacciones,
			HttpServletRequest request) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				for (SecuenciaPrimaria transaccion : transacciones) {
					fg.validar(transaccion, SecuenciaPrimaria.SecuenciaPrimariaUpdate.class);
					
					Optional<SecuenciaPrimaria> hagLicenAplicaSecu= repository.findById(transaccion.getCodigo());
					
					if (!hagLicenAplicaSecu.isPresent() ) {
						throw new DataIntegrityViolationException(
								"Secuencia con código "+ transaccion.getCodigo()+" no encontrado");
					}
					if (transaccion.getCiclica()==null || transaccion.getCiclica().equals("")) {
						transaccion.setCiclica(hagLicenAplicaSecu.get().getCiclica());
					}
					if (transaccion.getDescripcion()==null || transaccion.getDescripcion().equals("")) {
						transaccion.setDescripcion(hagLicenAplicaSecu.get().getDescripcion());
					}
					if (transaccion.getIncrementaEn()==null || transaccion.getIncrementaEn()<=0) {
						transaccion.setIncrementaEn(hagLicenAplicaSecu.get().getIncrementaEn());
					}
					if (transaccion.getValorActual()==null || transaccion.getValorActual()<0) {
						transaccion.setValorActual(hagLicenAplicaSecu.get().getValorActual());
					}
					if (transaccion.getValorInicial()==null || transaccion.getValorInicial()<0) {
						transaccion.setValorInicial(hagLicenAplicaSecu.get().getValorInicial());
					}
					
					repository.save(transaccion);

				}
			}
		});
		return new ResponseEntity<List<SecuenciaPrimaria>>(transacciones, HttpStatus.CREATED);
	}

}
