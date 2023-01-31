package espol.edu.ec.lpbike.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import espol.edu.ec.lpbike.model.Ejemplo;

public interface EjemploRepository extends JpaRepository<Ejemplo,Integer>{
	@Query(" SELECT e FROM Ejemplo e "
			+ "WHERE (:codigo=null OR e.codigo=:codigo) "
			+ "AND (:descripcion=null OR UPPER(e.descripcion) LIKE CONCAT('%',:descripcion,'%'))"
			)
	Page<Ejemplo> buscarPorParametro(@Param("codigo") Integer codigo,
												  @Param("descripcion") String descripcion,
												  Pageable pageable);
}
