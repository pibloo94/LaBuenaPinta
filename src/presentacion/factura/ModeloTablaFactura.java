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

package presentacion.factura;

import negocio.factura.TFactura;
import presentacion.util.ModeloTabla;

public class ModeloTablaFactura extends ModeloTabla<TFactura> {
	public ModeloTablaFactura(String[] columnIds) {
		super(columnIds);
	}

	@Override
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch (indiceCol) {
			case 0:
				s = lista.get(indiceFil).getId_factura();
				break;
			case 1:
				s = lista.get(indiceFil).getPrecio_total();
				break;
			case 2:
				s = lista.get(indiceFil).getEmpleado();
				break;
			case 3:
				s = lista.get(indiceFil).isAbierta();
				break;
			default:
				assert (false);
		}
		return s;
	}
}
