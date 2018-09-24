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

import presentacion.PanelTabs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBarCerveza extends  JToolBar{

	public ToolBarCerveza(PanelTabs panelTabs){
		initGUI(panelTabs);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBackground(new Color(240,240,240));
	}

	private void initGUI(PanelTabs panelTabs){
		// crear cerveza
		JButton botonCrear = new JButton("Crear Cerveza",new ImageIcon("resources/images/add-32.png"));
		botonCrear.setToolTipText("Crear Cerveza");
		botonCrear.setVerticalTextPosition(SwingConstants.BOTTOM);
		botonCrear.setHorizontalTextPosition(SwingConstants.CENTER);
		botonCrear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FormAltaCerveza formAltaCerveza = new FormAltaCerveza();
				formAltaCerveza.setVisible(true);
			}
		});

		// eliminar cerveza
		JButton botonElimiar = new JButton("Eliminar Cerveza", new ImageIcon("resources/images/remove-32.png"));
		botonElimiar.setToolTipText("Eliminar Cerveza");
		botonElimiar.setVerticalTextPosition(SwingConstants.BOTTOM);
		botonElimiar.setHorizontalTextPosition(SwingConstants.CENTER);
		botonElimiar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FormBajaCerveza formBajaCerveza = new FormBajaCerveza();
				formBajaCerveza.setVisible(true);
			}
		});

		// mostrar cerveza
		JButton botonMostrar = new JButton("Mostrar Cerveza",new ImageIcon( "resources/images/eye-32.png"));
		botonMostrar.setToolTipText("Mostrar Cerveza");
		botonMostrar.setVerticalTextPosition(SwingConstants.BOTTOM);
		botonMostrar.setHorizontalTextPosition(SwingConstants.CENTER);
		botonMostrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FormMostrarCerveza formMostrarCerveza = new FormMostrarCerveza();
				formMostrarCerveza.setVisible(true);
			}
		});

		// modificar cerveza
		JButton botonModificar = new JButton("Modificar Cerveza", new ImageIcon("resources/images/edit-32.png"));
		botonModificar.setToolTipText("Modificar Cerveza");
		botonModificar.setVerticalTextPosition(SwingConstants.BOTTOM);
		botonModificar.setHorizontalTextPosition(SwingConstants.CENTER);
		botonModificar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FormModificarCerveza formModificarCerveza = new FormModificarCerveza();
				formModificarCerveza.setVisible(true);
			}
		});

		// actualizar cerveza
		JButton botonactualizar = new JButton("Actualizar cerveza", new ImageIcon("resources/images/refresh-32.png"));
		botonactualizar.setToolTipText("Actualizar cerveza");
		botonactualizar.setVerticalTextPosition(SwingConstants.BOTTOM);
		botonactualizar.setHorizontalTextPosition(SwingConstants.CENTER);
		botonactualizar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panelTabs.actualizarCerveza();
			}
		});

		add(botonCrear);
		add(botonModificar);
		add(botonMostrar);
		add(botonElimiar);
		add(botonactualizar);
	}
}
