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

package presentacion.cerveza;

import negocio.cerveza.TCerveza;
import presentacion.util.ModeloTabla;

public class ModeloTablaCerveza extends ModeloTabla<TCerveza> {
	public ModeloTablaCerveza(String[] columnIds) {
		super(columnIds);
	}

	@Override
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch (indiceCol) {
			case 0:
				s = lista.get(indiceFil).getId_cerveza();
				break;
			case 1:
				s = lista.get(indiceFil).getNombre();
				break;
			case 2:
				s = lista.get(indiceFil).getStock();
				break;
			case 3:
				s = lista.get(indiceFil).getGraduacion();
				break;
			case 4:
				s = lista.get(indiceFil).getPrecio();
				break;
			case 5:
				s = lista.get(indiceFil).get_marca();
				break;
			case 6:
				s = lista.get(indiceFil).isActiva();
				break;
			default:
				assert (false);
		}
		return s;
	}
}
