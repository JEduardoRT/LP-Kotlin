package espol.edu.ec.lpbike.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import espol.edu.ec.lpbike.model.Objeto;

public interface ObjetoRepository extends JpaRepository<Objeto,Integer>{
	@Query(" SELECT e FROM Objeto e "
			+ "WHERE (:codigo=null OR e.codigo=:codigo) "
			+ "AND (:descripcion=null OR UPPER(e.descripcion) LIKE CONCAT('%',:descripcion,'%')) "
			+ "AND (:precio=null OR e.precio = :precio) "
			+ "AND (:ciudad=null OR UPPER(e.ciudad) LIKE CONCAT('%',:ciudad,'%')) "
			+ "AND (:provincia=null OR UPPER(e.provincia) LIKE CONCAT('%',:provincia,'%')) "
			)
	Page<Objeto> buscarPorParametro(@Param("codigo") Integer codigo,
												  @Param("descripcion") String descripcion,
												  @Param("precio") Double precio,
												  @Param("ciudad") String ciudad,
												  @Param("provincia") String provincia,
												  Pageable pageable);
}
