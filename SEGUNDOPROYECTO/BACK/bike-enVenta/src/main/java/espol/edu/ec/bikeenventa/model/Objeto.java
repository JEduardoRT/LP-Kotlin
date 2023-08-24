package espol.edu.ec.bikeenventa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="objeto")
public class Objeto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public interface ObjetoCreation{
		
	}
	
	public interface ObjetoUpdate{
		
	}
	
	@Id
	@NotNull(groups = ObjetoUpdate.class, message="Código no puede ser nulo")
	private Integer codigo;
	
	@Column(length=200)
	@NotNull(groups = ObjetoCreation.class, message="Descripción no puede ser nulo")
	@NotBlank(groups = ObjetoCreation.class, message="Descripción no puede ser vacío")
	private String descripcion;

	@Column
	@NotNull(groups = ObjetoCreation.class, message="Precio no puede ser nulo")
	private Double precio;
	
	@Column(length=200)
	@NotNull(groups = ObjetoCreation.class, message="Imagen no puede ser nulo")
	@NotBlank(groups = ObjetoCreation.class, message="Imagen no puede ser vacío")
	private String imagen;
	
	@Column(length=200)
	@NotNull(groups = ObjetoCreation.class, message="Ciudad no puede ser nulo")
	@NotBlank(groups = ObjetoCreation.class, message="Ciudad no puede ser vacío")
	private String ciudad;
	
	@Column(length=200)
	@NotNull(groups = ObjetoCreation.class, message="Provincia no puede ser nulo")
	@NotBlank(groups = ObjetoCreation.class, message="Provincia no puede ser vacío")
	private String provincia;
	
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
	
	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public Objeto() {
		super();
	}

	public Objeto(@NotNull(message="El código no puede ser nulo") Integer codigo) {
		super();
		this.codigo = codigo;
	}
}