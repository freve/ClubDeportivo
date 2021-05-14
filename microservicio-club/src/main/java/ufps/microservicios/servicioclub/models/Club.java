package ufps.microservicios.servicioclub.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "club")
@NamedQuery(name="Club.findAll", query="SELECT c FROM Club c")
public class Club implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_club")
	private int id;
	
	@NotBlank(message = "El nombre no debe ser vacio")
	private String nombre;
	
	@NotBlank(message = "La direccion no debe ser vacio")
	private String direccion;
	
	@NotBlank(message = "seleccione un pais")
	private String pais;
	
	@NotBlank(message = "el telefono no debe ser vacio")
	private String telefono;
	
	private String foto;
	
	@NotBlank(message = "la ciudad no debe ser vacia")
	private String ciudad;
	
	private String logo;
	
	@NotBlank(message = "El estadio no debe ser vacia")
	private String estadio;
	

	public Club(int id, String nombre, String direccion, String pais, String telefono, String foto, String ciudad,
			String logo, String estadio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.direccion = direccion;
		this.pais = pais;
		this.telefono = telefono;
		this.foto = foto;
		this.ciudad = ciudad;
		this.logo = logo;
		this.estadio = estadio;
	}

	public Club() {
		
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getPais() {
		return pais;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getFoto() {
		return foto;
	}

	public String getCiudad() {
		return ciudad;
	}

	public String getLogo() {
		return logo;
	}

	public String getEstadio() {
		return estadio;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public void setEstadio(String estadio) {
		this.estadio = estadio;
	}
	
	
	
	

}
