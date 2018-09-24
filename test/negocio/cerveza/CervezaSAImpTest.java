package negocio.cerveza;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import negocio.marca.MarcaSAImp;
import negocio.marca.TMarca;

class CervezaSAImpTest {

	private static Connection conn;
	private CervezaSAImp cervezaSAImp;
	private TCerveza cerveza1;
	private TMarca marcaAsociada;

	@BeforeAll
	static void beforeAll() {

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LBP", "empleado", "password");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@BeforeEach
	void BeforeEach() {

		try (Statement st = conn.createStatement()) {
			st.execute("DELETE FROM cerveza");
			st.execute("DELETE FROM marca");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// SA
		cervezaSAImp = new CervezaSAImp();

		// Cerveza 1
		cerveza1 = new TCerveza();
		cerveza1.setGraduacion(30.2);
		cerveza1.setNombre("5estrellas");
		cerveza1.setPrecio(10);
		cerveza1.setActiva(true);
		cerveza1.setStock(10);

		// Marca asociada a las cervezas
		marcaAsociada = new TMarca();
		marcaAsociada.setNombre("nuevaaMarca");
		marcaAsociada.setPais("Spain");
		marcaAsociada.setSede("Barcelona");
		marcaAsociada.setActiva(true);

		MarcaSAImp marcaSA = new MarcaSAImp();
		marcaSA.insertar_marca(marcaAsociada);

	}

	@Test
	void insertarCervezaConMarcaAsociada() {
		cerveza1.set_marca(marcaAsociada.getId_marca()); // Asocio Marca a cerveza
		assertTrue(cervezaSAImp.insertar_cerveza(cerveza1));
	}

	@Test
	void insertarCervezaSinMarcaAsociada() {
		assertFalse(cervezaSAImp.insertar_cerveza(cerveza1));
	}

	@Test
	void insertarCervezaConNombreRepetido() {

		// Cerveza1 inizicalizada con nombre "5eestrellas"
		cerveza1.set_marca(marcaAsociada.getId_marca()); 
		cervezaSAImp.insertar_cerveza(cerveza1);
		
		// Cerveza 2 -> mismo nombre atributos distintos
		TCerveza cerveza2 = new TCerveza();
		cerveza2.setGraduacion(20);
		cerveza2.setNombre("5estrellas"); 
		cerveza2.setPrecio(25);
		cerveza2.setActiva(true);
		cerveza2.setStock(30);
		cerveza2.set_marca(marcaAsociada.getId_marca());

		assertFalse(cervezaSAImp.insertar_cerveza(cerveza2));

	}

	@Test
	void bajaCervezaInexistente() {
		assertFalse(cervezaSAImp.baja_cerveza(cerveza1));
	}

	@Test
	void bajaCervezaConStock() {
		cerveza1.set_marca(marcaAsociada.getId_marca());
		cerveza1.setStock(10);
		cervezaSAImp.insertar_cerveza(cerveza1);
		
		assertFalse(cervezaSAImp.baja_cerveza(cerveza1));
	}
	
	@Test
	void bajaCervezaExistenteYsinStock() {
		cerveza1.set_marca(marcaAsociada.getId_marca());
		cerveza1.setStock(0);
		cervezaSAImp.insertar_cerveza(cerveza1);
		
		assertTrue(cervezaSAImp.baja_cerveza(cerveza1));
	}
	

	@Test
	void modificarCerveza() {

		cerveza1.set_marca(marcaAsociada.getId_marca()); // Asocio Marca a cerveza
		cervezaSAImp.insertar_cerveza(cerveza1);

		// Modifico la cerveza insertada
		cerveza1.setId_cerveza(cerveza1.getId_cerveza()); // idBuscado
		cerveza1.setGraduacion(20.4);
		cerveza1.setPrecio(23);
		cerveza1.setStock(55);
		
		assertTrue(cervezaSAImp.modificar_cerveza(cerveza1));
	}
	
	@Test
	void modificarCervezaInexistente() {
		
		// Modifico la cerveza sin insertar
		cerveza1.setId_cerveza(cerveza1.getId_cerveza()); // idBuscado
		cerveza1.setGraduacion(20.4);
		cerveza1.setPrecio(23);
		cerveza1.setStock(55);

		assertFalse(cervezaSAImp.modificar_cerveza(cerveza1));
	}
	
	
	@Test
	void modificarCervezaConMismoNombre() {
		cerveza1.set_marca(marcaAsociada.getId_marca()); // Asocio Marca a cerveza
		cervezaSAImp.insertar_cerveza(cerveza1);

		// Modifico la cerveza insertada
		cerveza1.setId_cerveza(12321312); // id buscado diferente
		cerveza1.setNombre("5estrellas");
		cerveza1.setGraduacion(20.4);
		cerveza1.setPrecio(23);
		cerveza1.setStock(55);

		// Cerveza con el mismo nombre y diferente ID no se puede modificar
		assertFalse(cervezaSAImp.modificar_cerveza(cerveza1));	
	}

}
