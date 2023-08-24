package espol.edu.ec.bikeobjeto.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import espol.edu.ec.bikeobjeto.model.Objeto;

public interface ObjetoRepository extends JpaRepository<Objeto,Integer>{
	@Query(" SELECT e FROM Objeto e "
			+ "WHERE (:codigo is null OR e.codigo=:codigo) "
			+ "AND (:descripcion is null OR UPPER(e.descripcion) LIKE %:descripcion%) "
			+ "AND (:precio is null OR e.precio = :precio) "
			+ "AND (:ciudad is null OR UPPER(e.ciudad) LIKE %:ciudad%) "
			+ "AND (:provincia is null OR UPPER(e.provincia) LIKE %:provincia%) "
			)
	Page<Objeto> buscarPorParametro(@Param("codigo") Integer codigo,
												  @Param("descripcion") String descripcion,
												  @Param("precio") Double precio,
												  @Param("ciudad") String ciudad,
												  @Param("provincia") String provincia,
												  Pageable pageable);
}
