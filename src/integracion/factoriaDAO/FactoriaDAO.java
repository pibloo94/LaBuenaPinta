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

package integracion.factoriaDAO;

import integracion.marca.MarcaDAO;
import integracion.cerveza.CervezaDAO;
import integracion.empleado.EmpleadoDAO;
import integracion.factura.FacturaDAO;

public abstract class FactoriaDAO {
	private static FactoriaDAO instancia = null;

	public abstract CervezaDAO getCervezaDAO();

	public abstract EmpleadoDAO getEmpleadoDAO();

	public abstract FacturaDAO getFacturaDAO();

	public abstract MarcaDAO getMarcaDAO();

	public static FactoriaDAO getInstancia() {
		if (instancia == null) {
			instancia = new FactoriaDAOImp();
		}
		return instancia;
	}
}
