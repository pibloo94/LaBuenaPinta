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

package presentacion.empleado;

import presentacion.PanelTabs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBarEmpleado extends JToolBar{

	public ToolBarEmpleado(PanelTabs panelTabs){
		initGUI(panelTabs);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBackground(new Color(240,240,240));
	}

	private void initGUI(PanelTabs panelTabs){

		// crear empleado
		JButton botonCrear = new JButton("Crear Empleado", new ImageIcon("resources/images/add-32.png"));
		botonCrear.setToolTipText("Crear empleado");
		botonCrear.setVerticalTextPosition(SwingConstants.BOTTOM);
		botonCrear.setHorizontalTextPosition(SwingConstants.CENTER);
		botonCrear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FormAltaEmpleado formAltaEmpleado = new FormAltaEmpleado();
				formAltaEmpleado.setVisible(true);
			}
		});

		// eliminar empleado
		JButton botonElimiar = new JButton("Eliminar Empleado", new ImageIcon("resources/images/remove-32.png"));
		botonElimiar.setToolTipText("Eliminar empleado");
		botonElimiar.setVerticalTextPosition(SwingConstants.BOTTOM);
		botonElimiar.setHorizontalTextPosition(SwingConstants.CENTER);
		botonElimiar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FormBajaEmpleado formBajaEmpleado = new FormBajaEmpleado();
				formBajaEmpleado.setVisible(true);
			}
		});

		// mostrar empleado
		JButton botonMostrar = new JButton("Mostrar Empleado", new ImageIcon("resources/images/eye-32.png"));
		botonMostrar.setToolTipText("Mostrar empleado");
		botonMostrar.setVerticalTextPosition(SwingConstants.BOTTOM);
		botonMostrar.setHorizontalTextPosition(SwingConstants.CENTER);
		botonMostrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FormMostrarEmpleado formMostrarEmpleado = new FormMostrarEmpleado();
				formMostrarEmpleado.setVisible(true);
			}
		});

		// modificar empleado
		JButton botonModificar = new JButton("Modificar Empleado", new ImageIcon("resources/images/edit-32.png"));
		botonModificar.setToolTipText("Modificar empleado");
		botonModificar.setVerticalTextPosition(SwingConstants.BOTTOM);
		botonModificar.setHorizontalTextPosition(SwingConstants.CENTER);
		botonModificar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FormModificarEmpleado formModificarEmpleado = new FormModificarEmpleado();
				formModificarEmpleado.setVisible(true);
			}
		});

		// actualizar empleado
		JButton botonactualizar = new JButton("Actualizar empleado", new ImageIcon("resources/images/refresh-32.png"));
		botonactualizar.setToolTipText("Actualizar empleado");
		botonactualizar.setVerticalTextPosition(SwingConstants.BOTTOM);
		botonactualizar.setHorizontalTextPosition(SwingConstants.CENTER);
		botonactualizar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panelTabs.actualizarEmpleado();
			}
		});

		add(botonCrear);
		add(botonModificar);
		add(botonMostrar);
		add(botonElimiar);
		add(botonactualizar);
	}
}
