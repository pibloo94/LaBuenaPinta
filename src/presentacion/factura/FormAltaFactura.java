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
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import negocio.empleado.TEmpleado;
import presentacion.util.Util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class FormAltaFactura extends JDialog {

	private JTextField textCantidadTotal;
	private JComboBox comboEmpleado;

	public FormAltaFactura() {
		setTitle("Alta Factura");
		setResizable(false);
		Util.addEscapeListener(this);
		initGUI();
	}

	public int getComboEmpleado() {
		return (int) comboEmpleado.getSelectedItem();
	}

	private void initGUI() {
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal,BoxLayout.Y_AXIS));

		panelPrincipal.add(camposFormulario());
		panelPrincipal.add(botonesFormulario());

		add(panelPrincipal);
		pack();
		setLocationRelativeTo(null);
	}

	private JPanel camposFormulario(){

		JPanel panelCampos = new JPanel(new GridLayout(1,2,0,7));
		Border border = panelCampos.getBorder();
		Border margin = new EmptyBorder(10,10,10,10);
		panelCampos.setBorder(new CompoundBorder(border, margin));

		//Empleado
		JLabel panelActiva= new JLabel("Empleado");
		panelCampos.add(panelActiva);

		comboEmpleado = seleccionarEmpleado();
		panelCampos.add(comboEmpleado);

		return panelCampos;
	}

	private  JPanel botonesFormulario(){
		//Botones
		JPanel panelBotones = new JPanel(new FlowLayout());

		JButton crear = new JButton("GENERAR");
		crear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				TFactura factura = new TFactura();
				factura.setEmpleado(getComboEmpleado());
				dispose();
				Controlador.getInstancia().accion(Eventos.insertar_Factura, factura);
			}
		});

		JButton cancelar = new JButton("CANCELAR");
		cancelar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		panelBotones.add(cancelar);
		panelBotones.add(crear);

		return panelBotones;
	}

	private JComboBox seleccionarEmpleado() {
		comboEmpleado = new JComboBox();

		List<TEmpleado> listaEmpleados = new ArrayList<TEmpleado>();
		Controlador.getInstancia().accion(Eventos.mostraTodos_Empleado, listaEmpleados);
		for (TEmpleado empleado : listaEmpleados) {
				comboEmpleado.addItem(empleado.getId_empleado());
		}
		return comboEmpleado;
	}
}
