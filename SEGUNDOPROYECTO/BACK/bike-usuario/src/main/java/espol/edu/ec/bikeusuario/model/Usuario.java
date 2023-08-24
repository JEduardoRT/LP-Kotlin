package espol.edu.ec.bikeusuario.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public interface UsuarioCreation{
		
	}
	
	public interface UsuarioUpdate{
		
	}
	
	@Id
	@NotNull(groups = UsuarioUpdate.class, message="Código no puede ser nulo")
	private Integer codigo;
	
	@Column(length=20)
	@NotNull(groups = UsuarioCreation.class, message="Nombre no puede ser nulo")
	@NotBlank(groups = UsuarioCreation.class, message="Nombre no puede ser vacío")
	private String nombre;
	
	@Column(length=20)
	@NotNull(groups = UsuarioCreation.class, message="Apellido no puede ser nulo")
	@NotBlank(groups = UsuarioCreation.class, message="Apellido no puede ser vacío")
	private String apellido;
	
	@Column(length=16)
	@NotNull(groups = UsuarioCreation.class, message="Usuario no puede ser nulo")
	@NotBlank(groups = UsuarioCreation.class, message="Usuario no puede ser vacío")
	private String usuario;
	
	@Column(length=16)
	@NotNull(groups = UsuarioCreation.class, message="Contraseña no puede ser nulo")
	@NotBlank(groups = UsuarioCreation.class, message="Contraseña no puede ser vacío")
	private String contrasena;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Usuario() {
		super();
	}

	public Usuario(@NotNull(message="El código no puede ser nulo") Integer codigo) {
		super();
		this.codigo = codigo;
	}
	
}