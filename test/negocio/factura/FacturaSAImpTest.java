package negocio.factura;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import negocio.cerveza.CervezaSAImp;
import negocio.cerveza.TCerveza;
import negocio.empleado.EmpleadoSAImp;
import negocio.empleado.TEmpleadoCompleto;
import negocio.marca.MarcaSAImp;
import negocio.marca.TMarca;

class FacturaSAImpTest {

	private static Connection conn;
	private FacturaSAImp facturaSAImp;
	private TFactura factura;
	private TEmpleadoCompleto empleadoAsociado;

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
			st.execute("DELETE FROM empleado_tparcial");
			st.execute("DELETE FROM empleado_tcompleto");
			st.execute("DELETE FROM factura");
			st.execute("DELETE FROM empleado");
			st.execute("DELETE FROM cerveza");
			st.execute("DELETE FROM marca");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		facturaSAImp = new FacturaSAImp();
		factura = new TFactura();

		empleadoAsociado = new TEmpleadoCompleto();
		empleadoAsociado.setActivo(true);
		empleadoAsociado.setDNI("N123");
		empleadoAsociado.setNombre("Employer");
		empleadoAsociado.setTiempo_completo(true);
		empleadoAsociado.setHoras_extra(2);

		EmpleadoSAImp empleadoSAImp = new EmpleadoSAImp();
		empleadoSAImp.insertar_emplado(empleadoAsociado);

	}

	@Test
	void insertarFacturaEmpleadoExistente() {
		factura.setEmpleado(empleadoAsociado.getId_empleado());
		assertTrue(facturaSAImp.insertar_factura(factura));
	}

	@Test
	void insertarFacturaEmpleadoInexistente() {
		assertFalse(facturaSAImp.insertar_factura(factura));
	}

	@Test
	void insertarFacturaEmpleadoInactivo() {
		EmpleadoSAImp empleadoSAImp = new EmpleadoSAImp();
		empleadoSAImp.baja_empleado(empleadoAsociado); // activo = false

		factura.setEmpleado(empleadoAsociado.getId_empleado());

		assertFalse(facturaSAImp.insertar_factura(factura));
	}

	@Test
	void cerrarFacturaInexistente() {
		assertFalse(facturaSAImp.cerrar_factura(factura));
	}

	@Test
	void cerrarFacturaExistente() {
		factura.setEmpleado(empleadoAsociado.getId_empleado());
		facturaSAImp.insertar_factura(factura);

		assertTrue(facturaSAImp.cerrar_factura(factura));
	}

	@Test
	void bajaFactura() {
		factura.setEmpleado(empleadoAsociado.getId_empleado());
		facturaSAImp.insertar_factura(factura);
		facturaSAImp.cerrar_factura(factura);

		assertTrue(facturaSAImp.baja_factura(factura));
	}

	@Test
	void bajaFacturaInexistente() {
		assertFalse(facturaSAImp.baja_factura(factura));
	}

	@Test
	void bajaFacturaAbierta() {

		EmpleadoSAImp empleadoSAImp = new EmpleadoSAImp();
		empleadoSAImp.insertar_emplado(empleadoAsociado);
		factura.setEmpleado(empleadoAsociado.getId_empleado());
		factura.setAbierta(true);
		facturaSAImp.insertar_factura(factura);

		assertFalse(facturaSAImp.baja_factura(factura));
	}

	@Test
	void anadirProducto() {
		// Producto
		TLineaFactura producto = new TLineaFactura();

		// Factura asociada a producto
		factura.setEmpleado(empleadoAsociado.getId_empleado());
		facturaSAImp.insertar_factura(factura);
		producto.setId_factura(factura.getId_factura());

		// Cerveza Asociada a producto
		TCerveza cerveza = new TCerveza();
		cerveza.setGraduacion(30.2);
		cerveza.setNombre("5estrellas");
		cerveza.setPrecio(10);
		cerveza.setActiva(true);
		cerveza.setStock(5);

		// Cantidad
		producto.setCantidad(3);

		// Marca asociada a las cerveza
		MarcaSAImp marcaSA = new MarcaSAImp();
		TMarca marcaAsociada = new TMarca();
		marcaAsociada.setNombre("nuevaaMarca");
		marcaAsociada.setPais("Spain");
		marcaAsociada.setSede("Barcelona");
		marcaAsociada.setActiva(true);
		marcaSA.insertar_marca(marcaAsociada);

		// Cerveza asociada a la marca
		CervezaSAImp cervezaSA = new CervezaSAImp();
		cerveza.set_marca(marcaAsociada.getId_marca());
		cervezaSA.insertar_cerveza(cerveza);
		producto.setId_cerveza(cerveza.getId_cerveza());

		assertTrue(facturaSAImp.anadir_producto(producto));
	}

	@Test
	void anadirProductoCantidadMayorAStock() {

		// Producto
		TLineaFactura producto = new TLineaFactura();

		// Factura asociada a producto
		factura.setEmpleado(empleadoAsociado.getId_empleado());
		facturaSAImp.insertar_factura(factura);
		producto.setId_factura(factura.getId_factura());

		// Cerveza Asociada a producto
		TCerveza cerveza = new TCerveza();
		cerveza.setGraduacion(30.2);
		cerveza.setNombre("5estrellas");
		cerveza.setPrecio(10);
		cerveza.setActiva(true);
		cerveza.setStock(5);

		// Cantidad
		producto.setCantidad(6);

		// Marca asociada a las cerveza
		MarcaSAImp marcaSA = new MarcaSAImp();
		TMarca marcaAsociada = new TMarca();
		marcaAsociada.setNombre("nuevaaMarca");
		marcaAsociada.setPais("Spain");
		marcaAsociada.setSede("Barcelona");
		marcaAsociada.setActiva(true);
		marcaSA.insertar_marca(marcaAsociada);

		// Cerveza asociada a la marca
		CervezaSAImp cervezaSA = new CervezaSAImp();
		cerveza.set_marca(marcaAsociada.getId_marca());
		cervezaSA.insertar_cerveza(cerveza);
		producto.setId_cerveza(cerveza.getId_cerveza());

		assertFalse(facturaSAImp.anadir_producto(producto));

	}

	@Test
	void anadirProductoCervezaInactiva() {

		// Producto
		TLineaFactura producto = new TLineaFactura();

		// Factura asociada a producto
		factura.setEmpleado(empleadoAsociado.getId_empleado());
		facturaSAImp.insertar_factura(factura);
		producto.setId_factura(factura.getId_factura());

		// Cerveza Asociada a producto
		TCerveza cerveza = new TCerveza();
		cerveza.setGraduacion(30.2);
		cerveza.setNombre("5estrellas");
		cerveza.setPrecio(10);
		cerveza.setActiva(true);
		cerveza.setStock(0);

		// Cantidad
		producto.setCantidad(3);

		// Marca asociada a las cerveza
		MarcaSAImp marcaSA = new MarcaSAImp();
		TMarca marcaAsociada = new TMarca();
		marcaAsociada.setNombre("nuevaaMarca");
		marcaAsociada.setPais("Spain");
		marcaAsociada.setSede("Barcelona");
		marcaAsociada.setActiva(true);
		marcaSA.insertar_marca(marcaAsociada);

		// Cerveza asociada a la marca
		CervezaSAImp cervezaSA = new CervezaSAImp();
		cerveza.set_marca(marcaAsociada.getId_marca());
		cervezaSA.insertar_cerveza(cerveza);
		// Inactivar cerveza
		cervezaSA.baja_cerveza(cerveza); // activa = 0
		producto.setId_cerveza(cerveza.getId_cerveza());

		assertFalse(facturaSAImp.anadir_producto(producto));
	}

	@Test
	void anadirProductoCervezaInexistente() {

		// Producto
		TLineaFactura producto = new TLineaFactura();

		// Factura asociada a producto
		factura.setEmpleado(empleadoAsociado.getId_empleado());
		facturaSAImp.insertar_factura(factura);
		producto.setId_factura(factura.getId_factura());

		// Cerveza Asociada a producto
		TCerveza cerveza = new TCerveza();
		cerveza.setGraduacion(30.2);
		cerveza.setNombre("5estrellas");
		cerveza.setPrecio(10);
		cerveza.setActiva(true);
		cerveza.setStock(5);

		// Cantidad
		producto.setCantidad(3);

		assertFalse(facturaSAImp.anadir_producto(producto));
	}

}
