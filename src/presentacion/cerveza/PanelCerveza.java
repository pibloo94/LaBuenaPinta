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
import presentacion.PanelTabs;
import presentacion.util.ModeloTabla;
import presentacion.util.PanelTabla;

import javax.swing.*;
import java.awt.*;

public class PanelCerveza extends JPanel {
	private final String[] columnId = {"Id" , "Nombre", "Stock", "Graduacion", "Precio", "Marca", "Activa"};
	private ModeloTabla modelo;

	public PanelCerveza(PanelTabs panelTabs){
		setLayout(new BorderLayout());
		add(new ToolBarCerveza(panelTabs), BorderLayout.NORTH);
		modelo = new ModeloTablaCerveza(columnId);
		add(new PanelTabla<TCerveza>(modelo), BorderLayout.CENTER);
	}

	public ModeloTabla getModelo() {
		return modelo;
	}
}
