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

import java.util.List;

public interface EmpleadoSA {

	boolean insertar_emplado(TEmpleado tEmpleado);

	TEmpleado mostrar_empleado(TEmpleado tEmpleado);

	List<TEmpleado> mostrar_todos_empleado();

	boolean modificar_empleado(TEmpleado tEmpleado);

	boolean baja_empleado(TEmpleado tEmpleado);

}