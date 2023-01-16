package espol.edu.ec.lpbike.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import espol.edu.ec.lpbike.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{
	@Query(" SELECT e FROM Usuario e "
			+ "WHERE (:codigo=null OR e.codigo=:codigo) "
			+ "AND (:nombre=null OR UPPER(e.nombre) LIKE CONCAT('%',:nombre,'%')) "
			+ "AND (:apellido=null OR UPPER(e.apellido) LIKE CONCAT('%',:apellido,'%')) "
			+ "AND (:usuario=null OR UPPER(e.usuario) LIKE CONCAT('%',:usuario,'%'))"
			)
	Page<Usuario> buscarPorParametro(@Param("codigo") Integer codigo,
												  @Param("nombre") String nombre,
												  @Param("apellido") String apellido,
												  @Param("usuario") String usuario,
												  Pageable pageable);
}
