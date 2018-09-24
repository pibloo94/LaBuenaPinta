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

import integracion.gestor.GestorConnexiones;
import negocio.cerveza.TCerveza;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAOImp implements FacturaDAO{
	private Connection conn;

	private final String INSERT = "INSERT INTO factura(empleado) VALUES(?)";
	private final String READALL = "SELECT * FROM factura";
	private final String READ = READALL+ " WHERE id_factura = ?";
	private final String READCERVEZASDEFACTURA = "SELECT * FROM asociada WHERE factura = ?";
	private final String READASOCIADA = "SELECT * FROM asociada  WHERE cerveza = ? AND factura = ?";
	private final String UPDATE = "UPDATE factura SET precio_total = ?, empleado = ? WHERE id_factura = ?";
	private final String CLOSE = "UPDATE factura  SET abierta = 0 WHERE id_factura = ?";
	private final String DELETE = "DELETE FROM factura WHERE id_factura = ?";
	private final String NEW = "INSERT INTO asociada VALUES (?,?,?,?)";
	private final String UPDATECantidad = "UPDATE asociada SET cantidad = ? WHERE factura = ? AND cerveza = ?";
	private final String UPDATECerveza = "UPDATE cerveza SET stock = ? WHERE id_cerveza = ?";



	public FacturaDAOImp() {
		this.conn = GestorConnexiones.getInstancia().getConnection();
	}


	@Override
	public void insertar(TFactura e) {
		try (PreparedStatement st = conn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
			st.setInt(1, e.getEmpleado());
			st.executeUpdate();
			try(ResultSet rs = st.getGeneratedKeys()) {
				if (rs.next()) {
					e.setId_factura(rs.getInt(1));
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public TFactura mostrar(int id) {
		TFactura f = null;
		List<TLineaFactura> lista = new ArrayList<>();
		try (PreparedStatement st = conn.prepareStatement(READ)) {
			st.setInt(1, id);
			try(ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					f = new TFactura(id,rs.getDouble("precio_total"),rs.getInt("empleado"),rs.getBoolean("abierta"),lista);
				}
				try (PreparedStatement ste = conn.prepareStatement(READCERVEZASDEFACTURA)) {
					ste.setInt(1, id);
					try(ResultSet rse = ste.executeQuery()) {
						while (rse.next()) {
							lista.add(new TLineaFactura(rse.getInt("factura"), rse.getInt("cerveza"), rse.getInt("cantidad")));
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return f;
	}

	@Override
	public List<TFactura> mostrarTodos() {
		ArrayList<TFactura> lista = new ArrayList<TFactura>();
		try (PreparedStatement st = conn.prepareStatement(READALL); ResultSet rs = st.executeQuery()) {
			while (rs.next()){
				lista.add(new TFactura(rs.getInt("id_factura"), rs.getInt("precio_total"),
						rs.getInt("empleado"), rs.getBoolean("abierta"),new ArrayList<>()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public void modificar(TFactura e) {
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

	@Override
	public void anadirProducto(TLineaFactura linea, TCerveza c) {
		try (PreparedStatement st = conn.prepareStatement(READASOCIADA)) {
			st.setInt(1,linea.getId_cerveza());
			st.setInt(2,linea.getId_factura());
			try(ResultSet rs = st.executeQuery()) {
				//Si no esta se crea la fila
				if (!rs.next()) {
					try (PreparedStatement ste = conn.prepareStatement(NEW)) {
						ste.setInt(1, linea.getId_factura());
						ste.setInt(2, linea.getId_cerveza());
						ste.setInt(3, linea.getCantidad());
						ste.setDouble(4, c.getPrecio());
						ste.executeUpdate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				//Si esta se añade la cantidad
				} else {
					int parcial = rs.getInt("cantidad");
					try (PreparedStatement ste = conn.prepareStatement(UPDATECantidad)) {
						ste.setInt(1,linea.getCantidad()+parcial);
						ste.setInt(2, linea.getId_factura());
						ste.setInt(3, linea.getId_cerveza());
						ste.executeUpdate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				//actualizamos stock cerveza
				try (PreparedStatement ste = conn.prepareStatement(UPDATECerveza)) {
					ste.setInt(1, c.getStock()-linea.getCantidad());
					ste.setInt(2, linea.getId_cerveza());
					ste.executeUpdate();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				//actualizamos precio
				try (PreparedStatement ste = conn.prepareStatement(READ)) {
						ste.setInt(1,linea.getId_factura());
					try(ResultSet rse = ste.executeQuery()) {
						if (rse.next()) {
							TFactura f = new TFactura(rse.getInt("id_factura"), rse.getDouble("precio_total"),
									rse.getInt("empleado"), rse.getBoolean("abierta"), null);
							try (PreparedStatement ste2 = conn.prepareStatement(UPDATE)) {
								ste2.setDouble(1, f.getPrecio_total()+ (linea.getCantidad()*c.getPrecio()));
								ste2.setInt(2, f.getEmpleado());
								ste2.setInt(3,f.getId_factura());
								ste2.executeUpdate();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}


	}

	@Override
	public void cerrar(int id) {
		try (PreparedStatement st = conn.prepareStatement(CLOSE)) {
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
