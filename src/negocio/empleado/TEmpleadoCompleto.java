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

public class TEmpleadoCompleto extends TEmpleado {
	private int horas_extra;

	public TEmpleadoCompleto() {

	}

	public TEmpleadoCompleto(int id_empleado, String nombre, String DNI, boolean activo, int horas_extra) {
		super(id_empleado, DNI, nombre, activo, true);
		this.horas_extra = horas_extra;
	}

	public int getHoras_extra() {
		return horas_extra;
	}

	public void setHoras_extra(int horas_extra) {
		this.horas_extra = horas_extra;
	}

	public String toString() {
		return "******Empleado*******\n" +
				"ID: " + this.getId_empleado() + "\n" +
				"DNI: " + this.getDNI() + "\n" +
				"Nombre: " + this.getNombre() + "\n" +
				"TiempoCompleto: " + this.isTiempo_completo() + "\n" +
				"Activo: " + this.isActivo() + "\n" +
				"HorasExtra: " + this.getHoras_extra() + "\n";
	}
}
