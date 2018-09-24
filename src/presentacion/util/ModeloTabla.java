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

package presentacion.util;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public abstract class ModeloTabla<T> extends DefaultTableModel {
	protected String[] columnIds;
	protected List<T> lista;

	public ModeloTabla(String[] columnIds) {
		lista = null;
		this.columnIds = columnIds;
	}

	@Override
	public String getColumnName(int col) {
		return columnIds[col];
	}

	@Override
	public int getColumnCount() {
		return columnIds.length;
	}

	@Override
	public int getRowCount() {
		return lista == null ? 0 : lista.size();
	}
	
	@Override
	public boolean isCellEditable(int row, int colum) {
		return false;
	}

	public void setLista(List<T> lista) {
		this.lista = lista;
		fireTableStructureChanged();
	}
}