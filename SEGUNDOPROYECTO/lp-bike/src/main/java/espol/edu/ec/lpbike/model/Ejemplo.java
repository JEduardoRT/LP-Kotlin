package espol.edu.ec.lpbike.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="ejemplo")
public class Ejemplo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public interface EjemploCreation{
		
	}
	
	public interface EjemploUpdate{
		
	}
	
	@Id
	@NotNull(groups = EjemploCreation.class, message="Código no puede ser nulo")
	private Integer codigo;
	
	@Column(length=200)
	@NotNull(groups = EjemploCreation.class, message="Descripción no puede ser nulo")
	@NotBlank(groups = EjemploCreation.class, message="Descripción no puede ser vacío")
	private String descripcion;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Ejemplo() {
		super();
	}

	public Ejemplo(@NotNull(message="El código no puede ser nulo") Integer codigo) {
		super();
		this.codigo = codigo;
	}
	
	public void validarActualizar(Ejemplo actualizable) {
		if(this.descripcion==null) {
			this.descripcion=actualizable.getDescripcion();
		}
	}
}