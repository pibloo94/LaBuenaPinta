package negocio.marca;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import integracion.cerveza.CervezaDAOImp;
import negocio.cerveza.TCerveza;

class MarcaSAImpTest {

	private MarcaSAImp marcaSAImp;
	private static Connection conn;
	private TMarca marca1;

	@BeforeAll
	static void beforeAll() {

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LBP", "empleado", "password");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@BeforeEach
	void beforeEach() {

		try (Statement st = conn.createStatement()) {
			st.execute("DELETE FROM cerveza");
			st.execute("DELETE FROM marca");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		marcaSAImp = new MarcaSAImp();

		// Creo Marca
		marca1 = new TMarca();
		marca1.setNombre("Amstel");
		marca1.setPais("pais1");
		marca1.setSede("sede1");
		marca1.setActiva(true);

	}

	@Test
	void insertarMarca() {
		assertTrue(marcaSAImp.insertar_marca(marca1));
	}

	@Test
	void insertarMarcasMismoNombre() {

		// Inserto la primera marca con nombre Amstel
		assertTrue(marcaSAImp.insertar_marca(marca1));

		// Creo otra marca con el mismo nombre
		TMarca marca2 = new TMarca();
		marca2.setNombre("Amstel");
		marca2.setPais("pais2");
		marca2.setSede("sede2");
		marca2.setActiva(true);

		assertFalse(marcaSAImp.insertar_marca(marca1));
	}

	@Test
	void modificarMarca() {

		// Inserto la marca (precondicion)
		marcaSAImp.insertar_marca(marca1);

		// Modifico la marca insertada
		marca1.setId_marca(marca1.getId_marca()); // id buscado
		marca1.setPais("otro");
		marca1.setSede("otra");;

		assertTrue(marcaSAImp.modificar_marca(marca1));
	}
	
	@Test
	void modificarMarcaInexistente() {
		
		//marcaSAImp.insertar_marca(marca1);
		
		marca1.setId_marca(marca1.getId_marca()); // id buscado
		marca1.setPais("otro");
		marca1.setSede("otra");

		assertFalse(marcaSAImp.modificar_marca(marca1));
	}
	
	
	@Test
	void modificarMarcaConMismoNombre() {
		
		marcaSAImp.insertar_marca(marca1);
		
		marca1.setId_marca(47847398); // id buscado(diferente al que tiene marca1)
		marca1.setNombre("Amstel"); // mismo nombre
		marca1.setPais("otro");
		marca1.setSede("otra");

		// Marca con el mismo nombre y diferente ID no se puede modificar
		assertFalse(marcaSAImp.modificar_marca(marca1));	
	}
	
	@Test
	void bajaMarcaConCervezasActivas() {
		marcaSAImp.insertar_marca(marca1);
		
		TCerveza cerv1 = new TCerveza();
		TCerveza cerv2 = new TCerveza();
		// Cerveza 1
		cerv1.setGraduacion(30.2);
		cerv1.setNombre("5estrellas");
		cerv1.setPrecio(10);
		cerv1.setActiva(true);
		cerv1.setStock(10);
		// Cerveza 2
		cerv2.setGraduacion(40.2);
		cerv2.setNombre("Clasica");
		cerv2.setPrecio(20);
		cerv2.setActiva(true);
		cerv2.setStock(5);
		
		cerv1.set_marca(marca1.getId_marca());
		cerv2.set_marca(marca1.getId_marca());
		
		CervezaDAOImp cervezaDAO = new CervezaDAOImp();
		cervezaDAO.insertar(cerv1);
		cervezaDAO.insertar(cerv2);
		
		assertFalse(marcaSAImp.baja_marca(marca1));	
	}
	
	void bajaMarcaSinCervezasActiva() {
		marcaSAImp.insertar_marca(marca1);
	
		assertTrue(marcaSAImp.baja_marca(marca1));	
	}
	
	@Test
	void bajaMarcaInexistente() {
		//marcaSAImp.insertar_marca(marca1);
		assertFalse(marcaSAImp.modificar_marca(marca1));	
	}
	
}
