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

public class TEmpleado {

	private int id_empleado;
	private String nombre;
	private String DNI;
	private boolean tiempo_completo;
	private boolean activo;

	public TEmpleado(){
	}

	public TEmpleado(int id_empleado , String DNI, String nombre, boolean activo, boolean tiempo_completo) {
		this.id_empleado = id_empleado;
		this.nombre = nombre;
		this.DNI = DNI;
		this.activo=activo;
		this.tiempo_completo = tiempo_completo;
	}

	public int getId_empleado() {
		return id_empleado;
	}

	public void setId_empleado(int id_empleado) {
		this.id_empleado = id_empleado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String DNI) {
		this.DNI = DNI;
	}

	public boolean isTiempo_completo() {
		return tiempo_completo;
	}

	public void setTiempo_completo(boolean tiempo_completo) {
		this.tiempo_completo = tiempo_completo;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String toString() {
		return "******Empleado*******\n"+
				"ID: "+this.getId_empleado() + "\n"+
				"DNI: "+this.getDNI() + "\n"+
				"Nombre: " +this.getNombre() + "\n"+
				"TiempoCompleto: "+this.isTiempo_completo() + "\n"+
				"Activo: " +this.isActivo()+"\n";
	}
}
