package espol.edu.ec.lpbike.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import espol.edu.ec.lpbike.model.Ejemplo;
import espol.edu.ec.lpbike.repository.EjemploRepository;
import espol.edu.ec.lpbike.utils.FuncionesGenerales;
import espol.edu.ec.lpbike.utils.Globales;
import espol.edu.ec.lpbike.utils.ManejadorExcepciones;

@RestController
@RequestMapping("/ejemplo")
public class EjemploController {
	@Autowired
	private EjemploRepository ejemploRepository;

	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@Autowired
	private FuncionesGenerales fg;
	
	RestTemplate restTemplate = new RestTemplate();
	
	@GetMapping()
	public Page<?> consultarTodos(@RequestParam(name="cod",required=false) Integer codigo, 
								  @RequestParam(name="sol",required=false) Integer codigoInstitucionControl,
								  @RequestParam(name="desc",required=false) String descripcion,
								  @PageableDefault(page=0, size=Globales.CANTIDAD_ELEMENTOS_PAGINA) Pageable pageable) {
		if(descripcion!=null) {
			descripcion.toUpperCase();
		}else {
			descripcion="";
		}
		Page<Ejemplo> result = ejemploRepository.buscarPorParametro(codigo, descripcion, pageable);
		if(result.isEmpty()) {
			throw new NoResultException("No hay coincidencias");
		}
		return result;
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> consultar(@PathVariable(name="codigo") Integer codigo) {
		Optional<Ejemplo> result = ejemploRepository.findById(codigo);
		if(!result.isPresent()) {
			throw new NoResultException("Ejemplo con código "+codigo+" no existe");
		}
		return new ResponseEntity<Ejemplo>(result.get(), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<?> ingresar(@RequestBody Ejemplo transaccion,
			HttpServletRequest request){
		fg.validar(transaccion, Ejemplo.EjemploCreation.class);
		
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				Integer codigo = fg.obtenerSecuenciaPrimaria(Globales.CODIGO_SECUENCIA_EJEMPLO).intValue();
				if (codigo == 0) {
					throw new DataIntegrityViolationException("Secuencia " + Globales.CODIGO_SECUENCIA_EJEMPLO
							+ " no existe");
				}
				transaccion.setCodigo(codigo);
				if(ejemploRepository.existsById(transaccion.getCodigo())){
					throw new DataIntegrityViolationException(
							"Ejemplo con código " + codigo + " ya existe");
				}
				ejemploRepository.save(transaccion);
			}
		});
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}")
				.buildAndExpand(transaccion.getCodigo()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PostMapping("/varios")
	public ResponseEntity<?> ingresarVarios(@RequestBody List<Ejemplo> transacciones,
			HttpServletRequest request){

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				for(Ejemplo transaccion : transacciones) {
					fg.validar(transaccion, Ejemplo.EjemploCreation.class);
					Integer codigo = fg.obtenerSecuenciaPrimaria(Globales.CODIGO_SECUENCIA_EJEMPLO).intValue();
					if (codigo == 0) {
						throw new DataIntegrityViolationException("Secuencia " + Globales.CODIGO_SECUENCIA_EJEMPLO
								+ " no existe");
					}
					transaccion.setCodigo(codigo);
					if(ejemploRepository.existsById(transaccion.getCodigo())){
						throw new DataIntegrityViolationException(
								"Ejemplo con código " + codigo + " ya existe");
					}
					ejemploRepository.save(transaccion);
				}
			}
		});
		return new ResponseEntity<List<Ejemplo>>(transacciones, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<?> actualizar(@RequestBody Ejemplo transaccion, HttpServletRequest request) {
		fg.validar(transaccion, Ejemplo.EjemploUpdate.class);
		if (!ejemploRepository.existsById(transaccion.getCodigo())) {
			return ManejadorExcepciones.error(HttpStatus.NOT_FOUND, null,
					"Ejemplo con código " + transaccion.getCodigo() + " no encontrado");
		}
		transaccion.validarActualizar(ejemploRepository.findById(transaccion.getCodigo()).get());
		ejemploRepository.save(transaccion);

		return new ResponseEntity<String>("", HttpStatus.OK);
	}
	
	@PutMapping("/varios")
	public ResponseEntity<?> actualizarVarios(@RequestBody List<Ejemplo> transacciones,
			HttpServletRequest request) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				for (Ejemplo transaccion : transacciones) {
					fg.validar(transaccion, Ejemplo.EjemploUpdate.class);
					if (!ejemploRepository.existsById(transaccion.getCodigo())) {
						throw new NoResultException("Ejemplo con código " + transaccion.getCodigo() + " no encontrado");
					} 
					transaccion.validarActualizar(ejemploRepository.findById(transaccion.getCodigo()).get());
					ejemploRepository.save(transaccion);
				}
			}
		});
		return new ResponseEntity<String>("", HttpStatus.OK);
	}
}
