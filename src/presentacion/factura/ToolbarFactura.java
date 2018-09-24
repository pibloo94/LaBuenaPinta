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

import presentacion.PanelTabs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//NOTA: EL FORMULARIO DE DEVOLVER PRODUCTO NO ES CORRECTO

public class ToolbarFactura extends JToolBar {

	public ToolbarFactura(PanelTabs panelTabs) {
		initGUI(panelTabs);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBackground(new Color(240,240,240));
	}

	private void initGUI(PanelTabs panelTabs) {
		// crear factura
		JButton botonCrear = new JButton("Crear Factura",new ImageIcon("resources/images/add-32.png"));
		botonCrear.setToolTipText("Crear Factura");
		botonCrear.setVerticalTextPosition(SwingConstants.BOTTOM);
		botonCrear.setHorizontalTextPosition(SwingConstants.CENTER);
		botonCrear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FormAltaFactura formAltaFactura = new FormAltaFactura();
				formAltaFactura.setVisible(true);
			}
		});

		// eliminar factura
		JButton botonElimiar = new JButton("Eliminar Factura", new ImageIcon("resources/images/remove-32.png"));
		botonElimiar.setToolTipText("Eliminar Factura");
		botonElimiar.setVerticalTextPosition(SwingConstants.BOTTOM);
		botonElimiar.setHorizontalTextPosition(SwingConstants.CENTER);
		botonElimiar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FormBajaFactura formBajaFactura = new FormBajaFactura();
				formBajaFactura.setVisible(true);
			}
		});

		// mostrar factura
		JButton botonMostrar = new JButton("Mostrar Factura",new ImageIcon( "resources/images/eye-32.png"));
		botonMostrar.setToolTipText("Mostrar Factura");
		botonMostrar.setVerticalTextPosition(SwingConstants.BOTTOM);
		botonMostrar.setHorizontalTextPosition(SwingConstants.CENTER);
		botonMostrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FormMostrarFactura formMostrarFactura = new FormMostrarFactura();
				formMostrarFactura.setVisible(true);
			}
		});

		// modificar factura
		JButton botonModificar = new JButton("Cerrar Factura", new ImageIcon("resources/images/edit-32.png"));
		botonModificar.setToolTipText("Cerrar Factura");
		botonModificar.setVerticalTextPosition(SwingConstants.BOTTOM);
		botonModificar.setHorizontalTextPosition(SwingConstants.CENTER);
		botonModificar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FormCerrarFactura formCerrarFactura = new FormCerrarFactura();
				formCerrarFactura.setVisible(true);
			}
		});

		// anadir producto
		JButton botonDevolverProd= new JButton("Anadir Producto", new ImageIcon("resources/images/return-32.png"));
		botonDevolverProd.setToolTipText("Anadir Producto");
		botonDevolverProd.setVerticalTextPosition(SwingConstants.BOTTOM);
		botonDevolverProd.setHorizontalTextPosition(SwingConstants.CENTER);
		botonDevolverProd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FormAnadirProducto formAnadirProducto = new FormAnadirProducto();
				formAnadirProducto.setVisible(true);
			}
		});

		// actualizar factura
		JButton botonactualizar = new JButton("Actualizar factura", new ImageIcon("resources/images/refresh-32.png"));
		botonactualizar.setToolTipText("Actualizar factura");
		botonactualizar.setVerticalTextPosition(SwingConstants.BOTTOM);
		botonactualizar.setHorizontalTextPosition(SwingConstants.CENTER);
		botonactualizar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panelTabs.actualizarFactura();
			}
		});

		add(botonCrear);
		add(botonModificar);
		add(botonMostrar);
		add(botonElimiar);
		add(botonDevolverProd);
		add(botonactualizar);
	}
}
