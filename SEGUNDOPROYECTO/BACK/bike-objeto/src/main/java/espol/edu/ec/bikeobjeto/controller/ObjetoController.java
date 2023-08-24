package espol.edu.ec.bikeobjeto.controller;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import espol.edu.ec.bikeobjeto.model.Objeto;
import espol.edu.ec.bikeobjeto.repository.ObjetoRepository;
import espol.edu.ec.bikeobjeto.utils.FuncionesGenerales;
import espol.edu.ec.bikeobjeto.utils.Globales;
import espol.edu.ec.bikeobjeto.utils.ManejadorExcepciones;

@RestController
@RequestMapping("/objeto")
public class ObjetoController {
	@Autowired
	private ObjetoRepository objetoRepository;

	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@Autowired
	private FuncionesGenerales fg;
	
	@GetMapping()
	public Page<?> consultarTodos(@RequestParam(name="codigo",required=false) Integer codigo, 
								  @RequestParam(name="descripcion",required=false) String descripcion,
								  @RequestParam(name="precio",required=false) Double precio,
								  @RequestParam(name="ciudad",required=false) String ciudad,
								  @RequestParam(name="provincia",required=false) String provincia,
								  @PageableDefault(page=0, size=Globales.CANTIDAD_ELEMENTOS_PAGINA) Pageable pageable) {
		if(descripcion!=null) {
			descripcion.toUpperCase();
		}else {
			descripcion="";
		}
		Page<Objeto> result = objetoRepository.buscarPorParametro(codigo, descripcion, precio, ciudad, provincia, pageable);
		if(result.isEmpty()) {
			throw new NoResultException("No hay coincidencias");
		}
		return result;
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> consultar(@PathVariable(name="codigo") Integer codigo) {
		Optional<Objeto> result = objetoRepository.findById(codigo);
		if(!result.isPresent()) {
			throw new NoResultException("Objeto con código "+codigo+" no existe");
		}
		return new ResponseEntity<Objeto>(result.get(), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<?> ingresar(@RequestBody Objeto transaccion,
			HttpServletRequest request){
		fg.validar(transaccion, Objeto.ObjetoCreation.class);
		
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				Integer codigo = fg.obtenerSecuenciaPrimaria(Globales.CODIGO_SECUENCIA_OBJETO).intValue();
				if (codigo == 0) {
					throw new DataIntegrityViolationException("Secuencia " + Globales.CODIGO_SECUENCIA_OBJETO
							+ " no existe");
				}
				transaccion.setCodigo(codigo);
				if(objetoRepository.existsById(transaccion.getCodigo())){
					throw new DataIntegrityViolationException(
							"Objeto con código " + codigo + " ya existe");
				}
				objetoRepository.save(transaccion);
			}
		});
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}")
				.buildAndExpand(transaccion.getCodigo()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PostMapping("/varios")
	public ResponseEntity<?> ingresarVarios(@RequestBody List<Objeto> transacciones,
			HttpServletRequest request){

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				for(Objeto transaccion : transacciones) {
					fg.validar(transaccion, Objeto.ObjetoCreation.class);
					Integer codigo = fg.obtenerSecuenciaPrimaria(Globales.CODIGO_SECUENCIA_OBJETO).intValue();
					if (codigo == 0) {
						throw new DataIntegrityViolationException("Secuencia " + Globales.CODIGO_SECUENCIA_OBJETO
								+ " no existe");
					}
					transaccion.setCodigo(codigo);
					if(objetoRepository.existsById(transaccion.getCodigo())){
						throw new DataIntegrityViolationException(
								"Objeto con código " + codigo + " ya existe");
					}
					objetoRepository.save(transaccion);
				}
			}
		});
		return new ResponseEntity<List<Objeto>>(transacciones, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<?> actualizar(@RequestBody Objeto transaccion, HttpServletRequest request) {
		fg.validar(transaccion, Objeto.ObjetoUpdate.class);
		if (!objetoRepository.existsById(transaccion.getCodigo())) {
			return ManejadorExcepciones.error(HttpStatus.NOT_FOUND, null,
					"Objeto con código " + transaccion.getCodigo() + " no encontrado");
		}
		objetoRepository.save(transaccion);

		return new ResponseEntity<String>("", HttpStatus.OK);
	}
	
	@PutMapping("/varios")
	public ResponseEntity<?> actualizarVarios(@RequestBody List<Objeto> transacciones,
			HttpServletRequest request) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				for (Objeto transaccion : transacciones) {
					fg.validar(transaccion, Objeto.ObjetoUpdate.class);
					if (!objetoRepository.existsById(transaccion.getCodigo())) {
						throw new NoResultException("Objeto con código " + transaccion.getCodigo() + " no encontrado");
					} 
					objetoRepository.save(transaccion);
				}
			}
		});
		return new ResponseEntity<String>("", HttpStatus.OK);
	}
}
