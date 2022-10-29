package ar.edu.davinci.dvds20221cg2.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tiendas")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Tienda implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5871595048816646337L;

		// Configurar por JPA cual el PK de la tabla prendas
		@Id
		// Configurar la estragia de generación de los ids por JPA
		@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
		@GenericGenerator(name = "native", strategy = "native")
		// Configuramos por JPA el nombre de la columna
		@Column(name = "tienda_id")
		private Long id;
		
		@Column(name = "tienda_nombre", nullable = false)
		private String nombre;
		
		@OneToMany(mappedBy="tienda", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, orphanRemoval = true)
		@JsonManagedReference
		private List<Venta> ventas;
		
		
	

}
