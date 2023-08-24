package espol.edu.ec.bikeusuario.controller;

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

import espol.edu.ec.bikeusuario.model.Usuario;
import espol.edu.ec.bikeusuario.repository.UsuarioRepository;
import espol.edu.ec.bikeusuario.utils.FuncionesGenerales;
import espol.edu.ec.bikeusuario.utils.Globales;
import espol.edu.ec.bikeusuario.utils.ManejadorExcepciones;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@Autowired
	private FuncionesGenerales fg;
	
	@GetMapping()
	public Page<?> consultarTodos(@RequestParam(name="codigo",required=false) Integer codigo, 
								  @RequestParam(name="nombre",required=false) String nombre,
								  @RequestParam(name="apellido",required=false) String apellido,
								  @RequestParam(name="usuario",required=false) String usuario,
								  @PageableDefault(page=0, size=Globales.CANTIDAD_ELEMENTOS_PAGINA) Pageable pageable) {
		Page<Usuario> result = usuarioRepository.buscarPorParametro(codigo, nombre, apellido, usuario, pageable);
		if(result.isEmpty()) {
			throw new NoResultException("No hay coincidencias");
		}
		return result;
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> consultar(@PathVariable(name="codigo") Integer codigo) {
		Optional<Usuario> result = usuarioRepository.findById(codigo);
		if(!result.isPresent()) {
			throw new NoResultException("Usuario con código "+codigo+" no existe");
		}
		return new ResponseEntity<Usuario>(result.get(), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<?> ingresar(@RequestBody Usuario transaccion,
			HttpServletRequest request){
		fg.validar(transaccion, Usuario.UsuarioCreation.class);
		
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				Integer codigo = fg.obtenerSecuenciaPrimaria(Globales.CODIGO_SECUENCIA_USUARIO).intValue();
				if (codigo == 0) {
					throw new DataIntegrityViolationException("Secuencia " + Globales.CODIGO_SECUENCIA_USUARIO
							+ " no existe");
				}
				transaccion.setCodigo(codigo);
				if(usuarioRepository.existsById(transaccion.getCodigo())){
					throw new DataIntegrityViolationException(
							"Usuario con código " + codigo + " ya existe");
				}
				usuarioRepository.save(transaccion);
			}
		});
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}")
				.buildAndExpand(transaccion.getCodigo()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PostMapping("/varios")
	public ResponseEntity<?> ingresarVarios(@RequestBody List<Usuario> transacciones,
			HttpServletRequest request){

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				for(Usuario transaccion : transacciones) {
					fg.validar(transaccion, Usuario.UsuarioCreation.class);
					Integer codigo = fg.obtenerSecuenciaPrimaria(Globales.CODIGO_SECUENCIA_USUARIO).intValue();
					if (codigo == 0) {
						throw new DataIntegrityViolationException("Secuencia " + Globales.CODIGO_SECUENCIA_USUARIO
								+ " no existe");
					}
					transaccion.setCodigo(codigo);
					if(usuarioRepository.existsById(transaccion.getCodigo())){
						throw new DataIntegrityViolationException(
								"Usuario con código " + codigo + " ya existe");
					}
					usuarioRepository.save(transaccion);
				}
			}
		});
		return new ResponseEntity<List<Usuario>>(transacciones, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<?> actualizar(@RequestBody Usuario transaccion, HttpServletRequest request) {
		fg.validar(transaccion, Usuario.UsuarioUpdate.class);
		if (!usuarioRepository.existsById(transaccion.getCodigo())) {
			return ManejadorExcepciones.error(HttpStatus.NOT_FOUND, null,
					"Usuario con código " + transaccion.getCodigo() + " no encontrado");
		}
		usuarioRepository.save(transaccion);

		return new ResponseEntity<String>("", HttpStatus.OK);
	}
	
	@PutMapping("/varios")
	public ResponseEntity<?> actualizarVarios(@RequestBody List<Usuario> transacciones,
			HttpServletRequest request) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				for (Usuario transaccion : transacciones) {
					fg.validar(transaccion, Usuario.UsuarioUpdate.class);
					if (!usuarioRepository.existsById(transaccion.getCodigo())) {
						throw new NoResultException("Usuario con código " + transaccion.getCodigo() + " no encontrado");
					} 
					usuarioRepository.save(transaccion);
				}
			}
		});
		return new ResponseEntity<String>("", HttpStatus.OK);
	}
}
