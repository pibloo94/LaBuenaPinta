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

package negocio.empleado;

import integracion.factoriaDAO.FactoriaDAO;

import java.util.List;

public class EmpleadoSAImp implements EmpleadoSA {
	@Override
	public boolean insertar_emplado(TEmpleado tEmpleado) {
		TEmpleado tl = FactoriaDAO.getInstancia().getEmpleadoDAO().mostrarPorDNI(tEmpleado.getDNI());

		if (tl == null) {
			FactoriaDAO.getInstancia().getEmpleadoDAO().insertar(tEmpleado);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public TEmpleado mostrar_empleado(TEmpleado tEmpleado) {
		return FactoriaDAO.getInstancia().getEmpleadoDAO().mostrar(tEmpleado.getId_empleado());
	}

	@Override
	public List<TEmpleado> mostrar_todos_empleado() {
		return FactoriaDAO.getInstancia().getEmpleadoDAO().mostrarTodos();

	}

	@Override
	public boolean modificar_empleado(TEmpleado tEmpleado) {
		TEmpleado tl = FactoriaDAO.getInstancia().getEmpleadoDAO().mostrar(tEmpleado.getId_empleado());
		TEmpleado tr = FactoriaDAO.getInstancia().getEmpleadoDAO().mostrarPorDNI(tEmpleado.getDNI());
		if (tl == null) {
			return false; 
		} else if (tr != null && tl.getId_empleado() != tr.getId_empleado()) {
			return false;
		} else {
			FactoriaDAO.getInstancia().getEmpleadoDAO().modificar(tEmpleado);
			return true;
		}
	}

	@Override
	public boolean baja_empleado(TEmpleado tEmpleado) {
		TEmpleado tl = FactoriaDAO.getInstancia().getEmpleadoDAO().mostrar(tEmpleado.getId_empleado());

		if (tl == null) {
			return false;
		} else {
			FactoriaDAO.getInstancia().getEmpleadoDAO().eliminar(tEmpleado.getId_empleado());
			return true;
		}
	}
}
