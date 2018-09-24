package negocio.empleado;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import integracion.empleado.EmpleadoDAOImp;
import presentacion.util.TipoTurno;

class EmpleadoSAImpTest {

	private static Connection conn;
	private EmpleadoSAImp empleadoSAImp;
	private TEmpleadoCompleto empCompleto;
	private TEmpleadoParcial empParcial;

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
			st.execute("DELETE FROM factura"); // Por si hay algun empleado asociado a factura
			st.execute("DELETE FROM empleado");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		empleadoSAImp = new EmpleadoSAImp();
		empCompleto = new TEmpleadoCompleto();
		empParcial = new TEmpleadoParcial();
		
		// Tiempo completo
		empCompleto.setActivo(true);
		empCompleto.setDNI("xvz123");
		empCompleto.setNombre("EmpComp");
		empCompleto.setTiempo_completo(true);
		empCompleto.setHoras_extra(2);

		// Tiempo Parcial
		empParcial.setActivo(true);
		empParcial.setDNI("tsfsv54");
		empParcial.setNombre("EmpParcial");
		empParcial.setTiempo_completo(false);
		empParcial.setTurno(TipoTurno.m);
	}
	
	@Test
	void insertarEmpleadoAtiempoCompleto() {
		assertTrue(empleadoSAImp.insertar_emplado(empCompleto));
	}
	
	@Test
	void insertarEmpleadoAtiempoParcial() {
		assertTrue(empleadoSAImp.insertar_emplado(empParcial));
	}
	
	@Test
	void insertarEmpleadoConDniYaExistente() {
		
		empCompleto.setDNI("xvz123");
		empleadoSAImp.insertar_emplado(empCompleto);
		
		empParcial.setDNI("xvz123"); // mismo DNI
		assertFalse(empleadoSAImp.insertar_emplado(empParcial));
	}
	
	// Tiene que existir
	@Test
	void modificarEmpleadoATiempoCompleto() {
		
		empleadoSAImp.insertar_emplado(empCompleto);
		empCompleto.setHoras_extra(3);
		empCompleto.setNombre("Pepe");
		
		assertTrue(empleadoSAImp.modificar_empleado(empCompleto));
	}
	
	@Test
	void modificarEmpleadoATiempoParcial() {
	
		empleadoSAImp.insertar_emplado(empParcial);
		empParcial.setTurno(TipoTurno.t);
		empParcial.setNombre("Nuevo");
		
		assertTrue(empleadoSAImp.modificar_empleado(empParcial));
	}
	
	@Test
	void modificarEmpleadoIdInexistente() {
		
		empleadoSAImp.insertar_emplado(empCompleto);
		empCompleto.setId_empleado(12138237); // id inexistente
		empCompleto.setHoras_extra(5);
		
		
		assertFalse(empleadoSAImp.modificar_empleado(empCompleto));
	}
	
	@Test // No puede haber 2 empleados con el mismo DNI y distinto Id
	void modificarEmpleadoMismoDni() {
		
		// Insertamos un empleado
		empleadoSAImp.insertar_emplado(empCompleto);
		
		empParcial.setId_empleado(empParcial.getId_empleado()); // id buscado
		empParcial.setDNI(empCompleto.getDNI()); // le asignamos un dni ya existente
		empParcial.setTurno(TipoTurno.t);
		
		assertFalse(empleadoSAImp.modificar_empleado(empParcial));
	}
	
	@Test
	void eliminarEmpleadoTiempoCompleto() {
		empleadoSAImp.insertar_emplado(empCompleto);
		assertTrue(empleadoSAImp.baja_empleado(empCompleto));
		
	}
	
	@Test
	void eliminarEmpleadoTiempoParcial() {
		empleadoSAImp.insertar_emplado(empParcial);
		assertTrue(empleadoSAImp.baja_empleado(empParcial));
	
	}
	
	@Test
	void eliminarEmpleadoINexistente() {
		// empleadoSAImp.insertar_emplado(empParcial);
		assertFalse(empleadoSAImp.baja_empleado(empParcial));
	}

}
