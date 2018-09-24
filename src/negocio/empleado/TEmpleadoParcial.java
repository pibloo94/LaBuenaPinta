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

import negocio.empleado.TEmpleado;
import presentacion.util.TipoTurno;

public class TEmpleadoParcial extends TEmpleado {
	private TipoTurno turno;

	public TEmpleadoParcial() {

	}

	public TEmpleadoParcial(int id_empleado, String nombre, String DNI, boolean activo, TipoTurno turno) {
		super(id_empleado, DNI, nombre, activo, false);
		this.turno = turno;
	}

	public TipoTurno getTurno() {
		return turno;
	}

	public void setTurno(TipoTurno turno) {
		this.turno = turno;
	}

	public String toString() {
		return "******Empleado*******\n" +
				"ID: " + this.getId_empleado() + "\n" +
				"DNI: " + this.getDNI() + "\n" +
				"Nombre: " + this.getNombre() + "\n" +
				"TiempoCompleto: " + this.isTiempo_completo() + "\n" +
				"Activo: " + this.isActivo() + "\n" +
				"Turno: " + this.getTurno() + "\n";
	}
}
