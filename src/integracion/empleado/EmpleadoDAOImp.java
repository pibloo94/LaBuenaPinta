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

package integracion.empleado;

import integracion.gestor.GestorConnexiones;
import negocio.empleado.TEmpleado;
import negocio.empleado.TEmpleadoCompleto;
import negocio.empleado.TEmpleadoParcial;
import presentacion.util.TipoTurno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAOImp implements EmpleadoDAO {
	private Connection conn;

	private final String INSERT = "INSERT INTO empleado(nombre, DNI, activo) VALUES(?, ?, ?)";
	private final String INSERTCompleto = "INSERT INTO empleado_tcompleto(id_empleado, horas_extra) VALUES(?, ?)";
	private final String INSERTParcial = "INSERT INTO empleado_tparcial(id_empleado, turno) VALUES(?, ?)";

	private final String READALL = "SELECT * FROM empleado LEFT JOIN empleado_tcompleto USING (id_empleado)" +
			" LEFT JOIN empleado_tparcial USING (id_empleado)";
	private final String READ = READALL + " WHERE id_empleado = ?";
	private final String READBYDNI = READALL + " WHERE DNI = ?";

	private final String UPDATE = "UPDATE empleado SET nombre = ?, DNI = ? WHERE id_empleado = ?";
	private final String UPDATECompleto = "UPDATE empleado_tcompleto SET horas_extra = ? WHERE id_empleado = ?";
	private final String UPDATEParcial = "UPDATE empleado_tparcial SET turno = ? WHERE id_empleado = ?";

	private final String DELETE = "UPDATE empleado SET activo = 0 WHERE id_empleado = ?";
	private final String DELETECompleto = "DELETE FROM empleado_tcompleto WHERE id_empleado = ?";
	private final String DELETEParcial = "DELETE FROM empleado_tparcial WHERE id_empleado = ?";


	public EmpleadoDAOImp() {
		this.conn = GestorConnexiones.getInstancia().getConnection();
	}

	@Override
	public void insertar(TEmpleado e) {
		try (PreparedStatement st = conn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
			st.setString(1, e.getNombre());
			st.setString(2, e.getDNI());
			st.setBoolean(3, e.isActivo());
			st.executeUpdate();
			try (ResultSet rs = st.getGeneratedKeys()) {
				if (rs.next()) {
					e.setId_empleado(rs.getInt(1));
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		if (!e.isTiempo_completo()) {
			TEmpleadoParcial empleadoParcial = (TEmpleadoParcial) e;
			try (PreparedStatement ste = conn.prepareStatement(INSERTParcial, PreparedStatement.RETURN_GENERATED_KEYS)) {
				ste.setInt(1, e.getId_empleado());
				ste.setString(2, empleadoParcial.getTurno().toString());
				ste.executeUpdate();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else {
			TEmpleadoCompleto empleadoCompleto = (TEmpleadoCompleto) e;
			try (PreparedStatement ste = conn.prepareStatement(INSERTCompleto, PreparedStatement.RETURN_GENERATED_KEYS)) {
				ste.setInt(1, e.getId_empleado());
				ste.setInt(2, empleadoCompleto.getHoras_extra());
				ste.executeUpdate();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public TEmpleado mostrar(int id) {
		TEmpleado e = null;
		try (PreparedStatement st = conn.prepareStatement(READ)) {
			st.setInt(1, id);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					int horas = rs.getInt("horas_extra");
					if (rs.wasNull()) {
						e = new TEmpleadoParcial(rs.getInt("id_empleado"),
								rs.getString("nombre"),
								rs.getString("DNI"),
								rs.getBoolean("activo"),
								TipoTurno.valueOf(rs.getString("turno")));
					} else {
						e = new TEmpleadoCompleto(rs.getInt("id_empleado"),
								rs.getString("nombre"),
								rs.getString("DNI"),
								rs.getBoolean("activo"),
								horas);
					}
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return e;
	}

	@Override
	public TEmpleado mostrarPorDNI(String DNI) {
		TEmpleado e = null;
		try (PreparedStatement st = conn.prepareStatement(READBYDNI)) {
			st.setString(1, DNI);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					int horas = rs.getInt("horas_extra");
					if (rs.wasNull()) {
						e = new TEmpleadoParcial(rs.getInt("id_empleado"),
								rs.getString("nombre"),
								rs.getString("DNI"),
								rs.getBoolean("activo"),
								TipoTurno.valueOf(rs.getString("turno")));
					} else {
						e = new TEmpleadoCompleto(rs.getInt("id_empleado"),
								rs.getString("nombre"),
								rs.getString("DNI"),
								rs.getBoolean("activo"),
								horas);
					}
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return e;
	}

	@Override
	public List<TEmpleado> mostrarTodos() {
		ArrayList<TEmpleado> lista = new ArrayList<TEmpleado>();
		try (PreparedStatement st = conn.prepareStatement(READALL); ResultSet rs = st.executeQuery()) {
			while (rs.next()) {
				int horas = rs.getInt("horas_extra");
				if (rs.wasNull()) {
					lista.add(new TEmpleadoParcial(rs.getInt("id_empleado"),
							rs.getString("nombre"),
							rs.getString("DNI"),
							rs.getBoolean("activo"),
							TipoTurno.valueOf(rs.getString("turno"))));
				} else {
					lista.add(new TEmpleadoCompleto(rs.getInt("id_empleado"),
							rs.getString("nombre"),
							rs.getString("DNI"),
							rs.getBoolean("activo"),
							horas));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return lista;
	}

	@Override
	public void modificar(TEmpleado e) {

		TEmpleado actual = mostrar(e.getId_empleado());

		//MODIFICAMOS EL EMPLEADO DE LA TABLA PRINCIPAL
		try (PreparedStatement st = conn.prepareStatement(UPDATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
			st.setString(1,e.getNombre());
			st.setString(2, e.getDNI());
			st.setInt(3,e.getId_empleado());
			st.executeUpdate();
			try (ResultSet rs = st.getGeneratedKeys()) {
				if (rs.next()) {
					e.setId_empleado(rs.getInt(1));
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		//COMPROBAMOS SI ES NECESARIO ACTUALIZAR TABLAS
		//-->Si pasamos de completo a parcial
		if( actual.isTiempo_completo() && !e.isTiempo_completo()){
			//borramos tupla en completo
			try (PreparedStatement ste = conn.prepareStatement(DELETECompleto, PreparedStatement.RETURN_GENERATED_KEYS)) {
				ste.setInt(1,e.getId_empleado());
				ste.executeUpdate();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			//insertas tupla en parcial
			TEmpleadoParcial empleadoParcial = (TEmpleadoParcial) e;
			try (PreparedStatement ste = conn.prepareStatement(INSERTParcial, PreparedStatement.RETURN_GENERATED_KEYS)) {
				ste.setInt(1, e.getId_empleado());
				ste.setString(2, empleadoParcial.getTurno().toString());
				ste.executeUpdate();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			//si pasamos de parcial a completo
		}else if ( !actual.isTiempo_completo() && e.isTiempo_completo()){
			//borramos parcial
			try (PreparedStatement ste = conn.prepareStatement(DELETEParcial, PreparedStatement.RETURN_GENERATED_KEYS)) {
				ste.setInt(1,e.getId_empleado());
				ste.executeUpdate();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			//insertas tupla completo
			TEmpleadoCompleto empleadoCompleto = (TEmpleadoCompleto) e;
			try (PreparedStatement ste = conn.prepareStatement(INSERTCompleto, PreparedStatement.RETURN_GENERATED_KEYS)) {
				ste.setInt(1, e.getId_empleado());
				ste.setInt(2, empleadoCompleto.getHoras_extra());
				ste.executeUpdate();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		//sino modificamos tablas
		else if (!e.isTiempo_completo() && !actual.isTiempo_completo()){
			TEmpleadoParcial empleadoParcial = (TEmpleadoParcial) e;
			try (PreparedStatement ste = conn.prepareStatement(UPDATEParcial, PreparedStatement.RETURN_GENERATED_KEYS)) {
				ste.setString(1, empleadoParcial.getTurno().toString());
				ste.setInt(2, e.getId_empleado());
				ste.executeUpdate();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else if (e.isTiempo_completo() && actual.isTiempo_completo()) {
			TEmpleadoCompleto empleadoCompleto = (TEmpleadoCompleto) e;
			try (PreparedStatement ste = conn.prepareStatement(UPDATECompleto, PreparedStatement.RETURN_GENERATED_KEYS)) {
				ste.setInt(1,empleadoCompleto.getHoras_extra());
				ste.setInt(2,e.getId_empleado());
				ste.executeUpdate();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

	}

	@Override
	public void eliminar(int id) {
		try (PreparedStatement st = conn.prepareStatement(DELETE)) {
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
