package espol.edu.ec.bikeenventa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import espol.edu.ec.bikeenventa.model.SecuenciaPrimaria;

public interface SecuenciaPrimariaRepository extends JpaRepository<SecuenciaPrimaria, Long>  {
	
}