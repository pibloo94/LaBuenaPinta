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

package presentacion;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame{

	public VentanaPrincipal(){
		setTitle("La Buena Pinta");
		initGUI();
	}

	private void initGUI() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("resources/images/logo-64.png"));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

		PanelTabs tabbedPane = new PanelTabs();
		setContentPane(tabbedPane);
		tabbedPane.actualizarMarca();

		pack();
		setVisible(true);
	}
}
