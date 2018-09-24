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

package integracion.marca;

import org.junit.jupiter.api.*;
import negocio.marca.TMarca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class MarcaDAOImpTest {

	private MarcaDAOImp marcaDAOImp;
	private static Connection conn;
	private TMarca marca1;
	private TMarca marca2;

	@BeforeAll
	static void beforeAll() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LBP", "empleado", "password");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try (Statement st = conn.createStatement()) {
			st.execute("DELETE FROM cerveza");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@BeforeEach
	void beforeEach() {
		try (Statement st = conn.createStatement()) {
			st.execute("DELETE FROM marca");
		} catch (SQLException e) {
			e.printStackTrace();
		}


		marcaDAOImp = new MarcaDAOImp();

		marca1 = new TMarca();
		marca2 = new TMarca();
		marca1.setNombre("nombre1");
		marca1.setPais("pais1");
		marca1.setSede("sede1");
		marca1.setActiva(true);
		marca2.setNombre("nombre2");
		marca2.setPais("pais2");
		marca2.setSede("sede2");
		marca2.setActiva(true);
	}

	@Test
	void insertar() {
		marcaDAOImp.insertar(marca1);

		if (marca1.getId_marca() != 0) {
			assertTrue(iguales(marca1, marcaDAOImp.mostrar(marca1.getId_marca())));
		} else {
			fail("la marca no se inserto");
		}
	}

	@Test
	void mostrarPorNombre() {
		marcaDAOImp.insertar(marca1);

		assertTrue(iguales(marca1, marcaDAOImp.mostrarPorNombre(marca1.getNombre())));
	}

	@Test
	void mostrarTodos() {
		List<TMarca> original = new ArrayList<TMarca>();
		original.add(marca1);
		original.add(marca2);

		for (TMarca tMarca : original) {
			marcaDAOImp.insertar(tMarca);
		}

		List<TMarca> tl = marcaDAOImp.mostrarTodos();

		for (int i = 0; i < original.size(); i++) {
			if (!iguales(original.get(i), tl.get(i))) {
				fail("La marca leida no se corresponde con la insertada");
			}
		}

	}

	@Test
	void modificar() {
		marcaDAOImp.insertar(marca1);

		marca1.setNombre("nuevoNombre1");
		marca1.setPais("nuevopais1");
		marca1.setSede("nuevosede1");

		marcaDAOImp.modificar(marca1);

		assertTrue(iguales(marca1, marcaDAOImp.mostrar(marca1.getId_marca())));
	}

	@Test
	void eliminar() {
		marcaDAOImp.insertar(marca1);

		marcaDAOImp.eliminar(marca1.getId_marca());


		assertFalse(marcaDAOImp.mostrar(marca1.getId_marca()).isActiva());
	}

	@AfterAll
	static void afterAll() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean iguales(TMarca expected, TMarca actual) {
		return expected.getId_marca() == actual.getId_marca() &&
				expected.getNombre().equals(actual.getNombre()) &&
				expected.getPais().equals(actual.getPais()) &&
				expected.getSede().equals(actual.getSede()) &&
				expected.isActiva() == actual.isActiva();
	}
}