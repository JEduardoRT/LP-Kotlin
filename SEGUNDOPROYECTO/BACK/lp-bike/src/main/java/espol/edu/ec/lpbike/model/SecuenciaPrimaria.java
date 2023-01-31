package espol.edu.ec.lpbike.model;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import espol.edu.ec.lpbike.utils.Globales;

/**
 * The persistent class for the sri_secuencia_primaria database table.
 * 
 */
@Cacheable(false)
@Entity
@Table(name="secuencia_primaria")
@NamedQuery(name="SecuenciaPrimaria.findAll", query="SELECT a FROM SecuenciaPrimaria a")
public class SecuenciaPrimaria implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public interface SecuenciaPrimariaCreation{
		
	}
	public interface SecuenciaPrimariaUpdate{
		
	}
	@NotNull(groups = {SecuenciaPrimariaCreation.class, SecuenciaPrimariaUpdate.class}, message = "El código de la secuencia no puede ser nulo")
	@Id
	private Long codigo;

	@NotNull(groups = SecuenciaPrimariaCreation.class, message = "El campo cíclica no puede ser nulo")
	@Pattern(groups = {SecuenciaPrimariaCreation.class, SecuenciaPrimariaUpdate.class}, regexp = "["+Globales.SI+Globales.NO+"]", message = "El campo cíclica debe ser "+Globales.SI+" o "+Globales.NO)
	private String ciclica;

	@NotNull(groups = SecuenciaPrimariaCreation.class, message = "La descripción no puede ser nula")
	private String descripcion;

	@NotNull(groups = SecuenciaPrimariaCreation.class, message = "El incremento de la secuencia no puede ser nulo")
	@Min(groups = {SecuenciaPrimariaCreation.class, SecuenciaPrimariaUpdate.class}, value = 1, message = "El incremento de la secuencia debe ser mayor a 0")
	@Column(name="incrementa_en")
	private Integer incrementaEn;

	@Column(name="valor_actual")
	private Long valorActual;

	@NotNull(groups = SecuenciaPrimariaCreation.class, message = "El inicio de la secuencia no puede ser nulo")
	@Column(name="valor_inicial")
	private Long valorInicial;

	@PrePersist
	void preInsert() {
		if(this.valorActual == null) {
			this.valorActual = 0l;
		}
	}
	
	
	public SecuenciaPrimaria() {
	}
	
	
	public SecuenciaPrimaria(Long codigo, String ciclica, String descripcion, Integer incrementaEn, Long valorActual, Long valorInicial) {
		super();
		this.codigo = codigo;
		this.ciclica = ciclica;
		this.descripcion = descripcion;
		this.incrementaEn = incrementaEn;
		this.valorActual = valorActual;
		this.valorInicial = valorInicial;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public String getCiclica() {
		return this.ciclica;
	}

	public void setCiclica(String ciclica) {
		this.ciclica = ciclica;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getIncrementaEn() {
		return this.incrementaEn;
	}

	public void setIncrementaEn(Integer incrementaEn) {
		this.incrementaEn = incrementaEn;
	}

	public Long getValorActual() {
		return this.valorActual;
	}

	public void setValorActual(Long valorActual) {
		this.valorActual = valorActual;
	}

	public Long getValorInicial() {
		return this.valorInicial;
	}

	public void setValorInicial(Long valorInicial) {
		this.valorInicial = valorInicial;
	}
	
	public void validarActualizar(SecuenciaPrimaria actualizable){
		if(this.ciclica==null){
			this.ciclica=actualizable.getCiclica();
		}
		if(this.descripcion==null){
			this.descripcion=actualizable.getDescripcion();
		}
	}
}