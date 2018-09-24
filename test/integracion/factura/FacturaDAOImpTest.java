/*
 *     This file is part of LaBuenaPinta.
 *
 *     LaBuenaPinta is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     LaBuenaPinta is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with LaBuenaPinta.  If not, see <http://www.gnu.org/licenses/>.
 */

package integracion.factura;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import integracion.cerveza.CervezaDAOImp;
import integracion.empleado.EmpleadoDAOImp;
import integracion.marca.MarcaDAOImp;
import negocio.cerveza.CervezaSAImp;
import negocio.cerveza.TCerveza;
import negocio.empleado.EmpleadoSAImp;
import negocio.empleado.TEmpleado;
import negocio.empleado.TEmpleadoCompleto;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
import negocio.marca.MarcaSAImp;
import negocio.marca.TMarca;

class FacturaDAOImpTest {

	private static Connection conn;
	private FacturaDAOImp facturaDAOImp;
	private TFactura factura1;
	private TFactura factura2;

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

		// Para poder crear una factura tiene que haber
		// algun empleado registrado previamente

		facturaDAOImp = new FacturaDAOImp();
		EmpleadoDAOImp empleadoDAOImp = new EmpleadoDAOImp();

		factura1 = new TFactura();
		factura2 = new TFactura();

		TEmpleadoCompleto empleadoAsociado = new TEmpleadoCompleto();
		empleadoAsociado.setActivo(true);
		empleadoAsociado.setDNI("N123");
		empleadoAsociado.setNombre("Employer");
		empleadoAsociado.setTiempo_completo(true);
		empleadoAsociado.setHoras_extra(2);

		empleadoDAOImp.insertar(empleadoAsociado);
		factura1.setEmpleado(empleadoAsociado.getId_empleado());
		factura2.setEmpleado(empleadoAsociado.getId_empleado());
	}

	@Test
	void insertar() {

		facturaDAOImp.insertar(factura1);

		if (factura1.getId_factura() != 0) {
			TFactura facturaInsertada = facturaDAOImp.mostrar(factura1.getId_factura());
			assertTrue(iguales(factura1, facturaInsertada));
		}

	}

	@Test
	void eliminar() {
		facturaDAOImp.insertar(factura1);
		facturaDAOImp.eliminar(factura1.getId_factura());

		assertNull(facturaDAOImp.mostrar(factura1.getId_factura()));

	}

	@Test
	void anadirPorducto() {

		// Producto
		TLineaFactura producto = new TLineaFactura();
		
		// Añado factura
		facturaDAOImp.insertar(factura1);
		producto.setId_factura(factura1.getId_factura());

		// Cerveza
		TCerveza cerveza = new TCerveza();
		cerveza.setGraduacion(30.2);
		cerveza.setNombre("5estrellas");
		cerveza.setPrecio(10);
		cerveza.setActiva(true);
		cerveza.setStock(5);

		// Marca asociada a las cerveza
		MarcaDAOImp marcaDAO = new MarcaDAOImp();
		TMarca marcaAsociada = new TMarca();
		marcaAsociada.setNombre("nuevaaMarca");
		marcaAsociada.setPais("Spain");
		marcaAsociada.setSede("Barcelona");
		marcaAsociada.setActiva(true);
		marcaDAO.insertar(marcaAsociada);

		// Cerveza asociada a la marca
		CervezaDAOImp cervezaDAO = new CervezaDAOImp();
		cerveza.set_marca(marcaAsociada.getId_marca());
		cervezaDAO.insertar(cerveza);
		
		// Cantidad
		producto.setCantidad(3);
		producto.setId_cerveza(cerveza.getId_cerveza());

		// Añadimos producto
		facturaDAOImp.anadirProducto(producto, cerveza);

		// Leemos
		TFactura facturaInsertada = facturaDAOImp.mostrar(factura1.getId_factura());
		List<TLineaFactura> lineasFacturaInsertada = facturaInsertada.getLineaFacturas();
		TLineaFactura productoInsertado = lineasFacturaInsertada.get(lineasFacturaInsertada.size()-1);
		
		// Comprobamos
		assertTrue(productoInsertado.getId_cerveza() == producto.getId_cerveza() 
				&& productoInsertado.getCantidad() == producto.getCantidad()
				&& productoInsertado.getId_factura() == producto.getId_factura());

	}

	@Test
	void mostrarTodos() {

		List<TFactura> original = new ArrayList<TFactura>();
		original.add(factura1);
		original.add(factura2);

		for (TFactura tFactura : original) {
			facturaDAOImp.insertar(tFactura);
		}

		List<TFactura> tl = facturaDAOImp.mostrarTodos();

		for (int i = 0; i < original.size(); i++) {
			if (!iguales(original.get(i), tl.get(i))) {
				fail("La factura leida no se corresponde con la insertada");
			}
		}

	}

	@AfterAll
	static void afterAll() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean iguales(TFactura esper, TFactura resul) {

		return (esper.getId_factura() == resul.getId_factura() && esper.getEmpleado() == resul.getEmpleado()
				&& esper.getPrecio_total() == resul.getPrecio_total())
				&& esper.getLineaFacturas().containsAll(resul.getLineaFacturas())
				&& resul.getLineaFacturas().containsAll(esper.getLineaFacturas())
				&& resul.getLineaFacturas().size() == esper.getLineaFacturas().size();
	}

}
