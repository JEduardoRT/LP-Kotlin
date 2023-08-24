package espol.edu.ec.bikeobjeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import espol.edu.ec.bikeobjeto.model.SecuenciaPrimaria;

public interface SecuenciaPrimariaRepository extends JpaRepository<SecuenciaPrimaria, Long>  {
	
}