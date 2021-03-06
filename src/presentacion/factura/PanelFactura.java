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
import presentacion.PanelTabs;
import presentacion.util.ModeloTabla;
import presentacion.util.PanelTabla;

import javax.swing.*;
import java.awt.*;

public class PanelFactura extends JPanel {
	private final String[] columnId = {"Id", "Precio Total", "Empleado", "Abierta"};
	private ModeloTabla modelo;

	public PanelFactura(PanelTabs panelTabs) {
		setLayout(new BorderLayout());
		add(new ToolbarFactura(panelTabs), BorderLayout.NORTH);
		modelo = new ModeloTablaFactura(columnId);
		add(new PanelTabla<TFactura>(modelo), BorderLayout.CENTER);
	}

	public ModeloTabla getModelo() {
		return modelo;
	}
}

