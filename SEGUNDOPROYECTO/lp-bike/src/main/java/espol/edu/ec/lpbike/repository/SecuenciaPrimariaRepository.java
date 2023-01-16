package espol.edu.ec.lpbike.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import espol.edu.ec.lpbike.model.SecuenciaPrimaria;

public interface SecuenciaPrimariaRepository extends JpaRepository<SecuenciaPrimaria, Long>  {
	
}