package espol.edu.ec.lpbike.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import espol.edu.ec.lpbike.model.EnVenta;

public interface EnVentaRepository extends JpaRepository<EnVenta,Integer>{
	@Query(" SELECT e FROM EnVenta e "
			+ "WHERE (:codigo=null OR e.codigo=:codigo) "
			+ "AND (:vendido=null OR e.vendido = :vendido)"
			)
	Page<EnVenta> buscarPorParametro(@Param("codigo") Integer codigo,
												  @Param("vendido") String vendido,
												  Pageable pageable);
}
