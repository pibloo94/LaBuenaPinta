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

package integracion.cerveza;

import integracion.gestor.GestorConnexiones;
import negocio.cerveza.TCerveza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CervezaDAOImp implements CervezaDAO {
	private Connection conn;

	private final String INSERT = "INSERT INTO cerveza(nombre, stock, graduacion, precio, activa, marca) VALUES(?, ?, ?, ?, ?, ?)";
	private final String READALL = "SELECT * FROM cerveza";
	private final String READ = READALL + " WHERE id_cerveza = ?";
	private final String READBYNAME = READALL + " WHERE nombre = ?";
	private final String READBYBRAND = READALL + " WHERE marca = ?";
	private final String UPDATE = "UPDATE cerveza SET nombre = ?, stock = ?, graduacion = ?, precio = ? WHERE id_cerveza = ?";
	private final String DELETE = "UPDATE cerveza SET activa = 0 WHERE id_cerveza = ?";

	public CervezaDAOImp() {
		this.conn = GestorConnexiones.getInstancia().getConnection();
	}

	@Override
	public void insertar(TCerveza e) {
		try (PreparedStatement st = conn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
			st.setString(1, e.getNombre());
			st.setInt(2, e.getStock());
			st.setDouble(3, e.getGraduacion());
			st.setDouble(4, e.getPrecio());
			st.setBoolean(5, e.isActiva());
			st.setInt(6, e.get_marca());

			st.executeUpdate();
			try(ResultSet rs = st.getGeneratedKeys()) {
				if (rs.next()) {
					e.setId_cerveza(rs.getInt(1));
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public TCerveza mostrar(int id) {
		TCerveza c = null;
		try (PreparedStatement st = conn.prepareStatement(READ)) {
			st.setInt(1, id);
			try(ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					c = new TCerveza(id, rs.getString("nombre"), rs.getInt("stock"),
							rs.getDouble("graduacion"), rs.getDouble("precio"), rs.getBoolean("activa"), rs.getInt("marca"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	@Override
	public TCerveza mostrarPorNombre(String nombre) {
		TCerveza c = null;
		try (PreparedStatement st = conn.prepareStatement(READBYNAME)) {
			st.setString(1, nombre);
			try(ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					c = new TCerveza(rs.getInt("id_cerveza"), rs.getString("nombre"), rs.getInt("stock"),
							rs.getDouble("graduacion"), rs.getDouble("precio"), rs.getBoolean("activa"), rs.getInt("marca"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	@Override
	public List<TCerveza> mostrarPorMarca(int id){
		ArrayList<TCerveza> lista = new ArrayList<TCerveza>();
		try (PreparedStatement st = conn.prepareStatement(READBYBRAND)) {
			st.setInt(1, id);
				try (ResultSet rs = st.executeQuery()) {
					while (rs.next()) {
						lista.add(new TCerveza(rs.getInt("id_cerveza"), rs.getString("nombre"), rs.getInt("stock"),
								rs.getDouble("graduacion"), rs.getDouble("precio"), rs.getBoolean("activa"), rs.getInt("marca")));
					}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public List<TCerveza> mostrarTodos() {
		ArrayList<TCerveza> lista = new ArrayList<TCerveza>();
		try (PreparedStatement st = conn.prepareStatement(READALL); ResultSet rs = st.executeQuery()) {
			while (rs.next()){
				lista.add(new TCerveza(rs.getInt("id_cerveza"), rs.getString("nombre"), rs.getInt("stock"),
						rs.getDouble("graduacion"),rs.getDouble("precio"), rs.getBoolean("activa"), rs.getInt("marca")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public void modificar(TCerveza e) {
		try (PreparedStatement st = conn.prepareStatement(UPDATE)) {
			st.setString(1, e.getNombre());
			st.setInt(2, e.getStock());
			st.setDouble(3, e.getGraduacion());
			st.setDouble(4, e.getPrecio());
			st.setInt(5, e.getId_cerveza());
			st.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void eliminar(int id) {
		try (PreparedStatement st = conn.prepareStatement(DELETE)) {
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
