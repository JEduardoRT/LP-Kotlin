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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import espol.edu.ec.lpbike.model.EnVenta;
import espol.edu.ec.lpbike.repository.EnVentaRepository;
import espol.edu.ec.lpbike.utils.FuncionesGenerales;
import espol.edu.ec.lpbike.utils.Globales;
import espol.edu.ec.lpbike.utils.ManejadorExcepciones;

@RestController
@RequestMapping("/enVenta")
public class EnVentaController {
	@Autowired
	private EnVentaRepository enVentaRepository;

	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@Autowired
	private FuncionesGenerales fg;
	
	@GetMapping()
	public Page<?> consultarTodos(@RequestParam(name="codigo",required=false) Integer codigo, 
								  @RequestParam(name="vendido",required=false) String vendido,
								  @PageableDefault(page=0, size=Globales.CANTIDAD_ELEMENTOS_PAGINA) Pageable pageable) {
		Page<EnVenta> result = enVentaRepository.buscarPorParametro(codigo, vendido, pageable);
		if(result.isEmpty()) {
			throw new NoResultException("No hay coincidencias");
		}
		return result;
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> consultar(@PathVariable(name="codigo") Integer codigo) {
		Optional<EnVenta> result = enVentaRepository.findById(codigo);
		if(!result.isPresent()) {
			throw new NoResultException("EnVenta con código "+codigo+" no existe");
		}
		return new ResponseEntity<EnVenta>(result.get(), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<?> ingresar(@RequestBody EnVenta transaccion,
			HttpServletRequest request){
		fg.validar(transaccion, EnVenta.EnVentaCreation.class);
		
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				Integer codigo = fg.obtenerSecuenciaPrimaria(Globales.CODIGO_SECUENCIA_EN_VENTA).intValue();
				if (codigo == 0) {
					throw new DataIntegrityViolationException("Secuencia " + Globales.CODIGO_SECUENCIA_EN_VENTA
							+ " no existe");
				}
				transaccion.setCodigo(codigo);
				if(enVentaRepository.existsById(transaccion.getCodigo())){
					throw new DataIntegrityViolationException(
							"EnVenta con código " + codigo + " ya existe");
				}
				enVentaRepository.save(transaccion);
			}
		});
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}")
				.buildAndExpand(transaccion.getCodigo()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PostMapping("/varios")
	public ResponseEntity<?> ingresarVarios(@RequestBody List<EnVenta> transacciones,
			HttpServletRequest request){

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				for(EnVenta transaccion : transacciones) {
					fg.validar(transaccion, EnVenta.EnVentaCreation.class);
					Integer codigo = fg.obtenerSecuenciaPrimaria(Globales.CODIGO_SECUENCIA_EN_VENTA).intValue();
					if (codigo == 0) {
						throw new DataIntegrityViolationException("Secuencia " + Globales.CODIGO_SECUENCIA_EN_VENTA
								+ " no existe");
					}
					transaccion.setCodigo(codigo);
					if(enVentaRepository.existsById(transaccion.getCodigo())){
						throw new DataIntegrityViolationException(
								"EnVenta con código " + codigo + " ya existe");
					}
					enVentaRepository.save(transaccion);
				}
			}
		});
		return new ResponseEntity<List<EnVenta>>(transacciones, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<?> actualizar(@RequestBody EnVenta transaccion, HttpServletRequest request) {
		fg.validar(transaccion, EnVenta.EnVentaUpdate.class);
		if (!enVentaRepository.existsById(transaccion.getCodigo())) {
			return ManejadorExcepciones.error(HttpStatus.NOT_FOUND, null,
					"EnVenta con código " + transaccion.getCodigo() + " no encontrado");
		}
		enVentaRepository.save(transaccion);

		return new ResponseEntity<String>("", HttpStatus.OK);
	}
	
	@PutMapping("/varios")
	public ResponseEntity<?> actualizarVarios(@RequestBody List<EnVenta> transacciones,
			HttpServletRequest request) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				for (EnVenta transaccion : transacciones) {
					fg.validar(transaccion, EnVenta.EnVentaUpdate.class);
					if (!enVentaRepository.existsById(transaccion.getCodigo())) {
						throw new NoResultException("EnVenta con código " + transaccion.getCodigo() + " no encontrado");
					} 
					enVentaRepository.save(transaccion);
				}
			}
		});
		return new ResponseEntity<String>("", HttpStatus.OK);
	}
}
