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

import presentacion.cerveza.PanelCerveza;
import negocio.cerveza.TCerveza;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.empleado.PanelEmpleado;
import negocio.empleado.TEmpleado;
import presentacion.factura.PanelFactura;
import negocio.factura.TFactura;
import presentacion.marca.PanelMarca;
import negocio.marca.TMarca;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class PanelTabs extends JTabbedPane {

	private PanelCerveza panelCerveza;
	private PanelEmpleado panelEmpleado;
	private PanelMarca panelMarca;
	private PanelFactura panelFactura;

	PanelTabs() {
		//vista marca
		JComponent tabMarca = new JPanel(new GridLayout(1, 1));
		addTab("Marcas", new ImageIcon("resources/images/company-16.png"), tabMarca, "Vista marca");
		setMnemonicAt(0, KeyEvent.VK_1);
		panelMarca = new PanelMarca(this);
		tabMarca.add(panelMarca);

		//vista cerveza
		JComponent tabCerveza = new JPanel(new GridLayout(1, 1));
		addTab("Cervezas", new ImageIcon("resources/images/beer-16.png"), tabCerveza, "Vista cerveza");
		setMnemonicAt(1, KeyEvent.VK_2);
		panelCerveza = new PanelCerveza(this);
		tabCerveza.add(panelCerveza);

		//vista empleado
		JComponent tabEmpleado = new JPanel(new GridLayout(1, 1));
		addTab("Empleados", new ImageIcon("resources/images/employee-16.png"), tabEmpleado, "Vista empleado");
		setMnemonicAt(2, KeyEvent.VK_3);
		panelEmpleado = new PanelEmpleado(this);
		tabEmpleado.add(panelEmpleado);

		//vista facturathis
		JComponent tabFactura = new JPanel(new GridLayout(1, 1));
		addTab("Facturas", new ImageIcon("resources/images/bill-16.png"), tabFactura, "Vista factura");
		setMnemonicAt(3, KeyEvent.VK_4);
		panelFactura = new PanelFactura(this);
		tabFactura.add(panelFactura);

		//The following line enables to use scrolling tabs.
		setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent changeEvent) {
				switch (getSelectedIndex()) {
					case 0:
						actualizarMarca();
						break;
					case 1:
						actualizarCerveza();
						break;
					case 2:
						actualizarEmpleado();
						break;
					case 3:
						actualizarFactura();
						break;
				}
			}
		});
	}

	public void actualizarMarca(){
		ArrayList listaMarca = new ArrayList<TMarca>();
		Controlador.getInstancia().accion(Eventos.mostraTodos_Marca, listaMarca);
		panelMarca.getModelo().setLista(listaMarca);
	}

	public void actualizarCerveza(){
		ArrayList listaCerveza = new ArrayList<TCerveza>();
		Controlador.getInstancia().accion(Eventos.mostraTodos_Cerveza, listaCerveza);
		panelCerveza.getModelo().setLista(listaCerveza);
	}

	public void actualizarEmpleado(){
		ArrayList listaEmpleado = new ArrayList<TEmpleado>();
		Controlador.getInstancia().accion(Eventos.mostraTodos_Empleado, listaEmpleado);
		panelEmpleado.getModelo().setLista(listaEmpleado);
	}

	public void actualizarFactura(){
		ArrayList listaListaFactura = new ArrayList<TFactura>();
		Controlador.getInstancia().accion(Eventos.mostraTodos_Factura, listaListaFactura);
		panelFactura.getModelo().setLista(listaListaFactura);
	}
}