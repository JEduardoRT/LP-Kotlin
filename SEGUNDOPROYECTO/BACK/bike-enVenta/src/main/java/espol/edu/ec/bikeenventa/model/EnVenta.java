package espol.edu.ec.bikeenventa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import espol.edu.ec.bikeenventa.utils.Globales;

@Entity
@Table(name="en_venta")
public class EnVenta implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public interface EnVentaCreation{
		
	}
	
	public interface EnVentaUpdate{
		
	}
	
	
	@Id
	@NotNull(groups = EnVentaUpdate.class, message="Código no puede ser nulo")
	private Integer codigo;
	
	@Column(name="codigo_usuario")
	@NotNull(groups = EnVentaCreation.class, message="Código de usuario no puede ser nulo")
	private Integer codigoUsuario;
	
	@Column(name="codigo_objeto")
	@NotNull(groups = EnVentaCreation.class, message="Código de objeto no puede ser nulo")
	private Integer codigoObjeto;

	@Column(length=1)
	@Pattern(regexp="["+Globales.SI+Globales.NO+"]")
	@NotNull(groups = EnVentaCreation.class, message="Vendido no puede ser nulo")
	@NotBlank(groups = EnVentaCreation.class, message="Vendido no puede ser vacío")
	private String vendido;
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(Integer codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public Integer getCodigoObjeto() {
		return codigoObjeto;
	}

	public void setCodigoObjeto(Integer codigoObjeto) {
		this.codigoObjeto = codigoObjeto;
	}

	public String getVendido() {
		return vendido;
	}

	public void setVendido(String vendido) {
		this.vendido = vendido;
	}

	public EnVenta() {
		super();
	}

	public EnVenta(@NotNull(message="El código no puede ser nulo") Integer codigo) {
		super();
		this.codigo = codigo;
	}
}