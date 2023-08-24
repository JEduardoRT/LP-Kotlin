package espol.edu.ec.bikeusuario.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import espol.edu.ec.bikeusuario.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{
	@Query(" SELECT e FROM Usuario e "
			+ "WHERE (:codigo is null OR e.codigo=:codigo) "
			+ "AND (:nombre is null OR UPPER(e.nombre) LIKE %:nombre%) "
			+ "AND (:apellido is null OR UPPER(e.apellido) LIKE %:apellido%) "
			+ "AND (:usuario is null OR UPPER(e.usuario) LIKE %:usuario%)"
			)
	Page<Usuario> buscarPorParametro(@Param("codigo") Integer codigo,
												  @Param("nombre") String nombre,
												  @Param("apellido") String apellido,
												  @Param("usuario") String usuario,
												  Pageable pageable);
}
