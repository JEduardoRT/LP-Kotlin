package espol.edu.ec.bikeusuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import espol.edu.ec.bikeusuario.model.SecuenciaPrimaria;

public interface SecuenciaPrimariaRepository extends JpaRepository<SecuenciaPrimaria, Long>  {
	
}