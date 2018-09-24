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

import negocio.empleado.TEmpleadoCompleto;
import negocio.empleado.TEmpleadoParcial;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.util.TipoTurno;
import presentacion.util.Util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormModificarEmpleado extends  JDialog{

	private JTextField textNombre;
	private JTextField textID;
	private JTextField textDNI;
	private JTextField textHExtras;
	private JComboBox comboTCompleto;
	private JComboBox comboTParcial;

	public FormModificarEmpleado() {
		setTitle("Modificar Empleado");
		setResizable(false);
		Util.addEscapeListener(this);
		initGUI();
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

		JPanel panelCampos = new JPanel(new GridLayout(6,2,0,7));
		Border border = panelCampos.getBorder();
		Border margin = new EmptyBorder(10,10,10,10);
		panelCampos.setBorder(new CompoundBorder(border, margin));

		//ID
		JLabel panelID = new JLabel("ID Buscado");
		panelCampos.add(panelID);

		textID = new JTextField(10);
		panelCampos.add(textID);

		//Nombre
		JLabel panelNombre = new JLabel("Nombre");
		panelCampos.add(panelNombre);

		textNombre = new JTextField(10);
		panelCampos.add(textNombre);

		//DNI
		JLabel panelDNI = new JLabel("DNI");
		panelCampos.add(panelDNI);

		textDNI = new JTextField(10);
		panelCampos.add(textDNI);

		//Tiempo Completo
		JLabel panelTCompleto= new JLabel("Tiempo completo");
		panelCampos.add(panelTCompleto);

		comboTCompleto = seleccionarTCompleto();
		panelCampos.add(comboTCompleto);

		//Horas Extras
		JLabel panelHorasExtras = new JLabel("Horas extras");
		panelCampos.add(panelHorasExtras);

		textHExtras = new JTextField(10);
		panelCampos.add(textHExtras);

		//Tiemplo Parcial
		JLabel panelTParcial = new JLabel("Tiempo parcial");
		panelCampos.add(panelTParcial);

		comboTParcial = seleccionarTParcial();
		panelCampos.add(comboTParcial);


		return panelCampos;
	}

	private  JPanel botonesFormulario(){
		//Botones
		JPanel panelBotones = new JPanel(new FlowLayout());

		JButton modificar = new JButton("MODIFICAR");
		modificar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(comboTCompleto.getSelectedItem().equals("true")){
						TEmpleadoCompleto empleado = new TEmpleadoCompleto();
						empleado.setId_empleado(Util.parseaIntNoNegativo(textID.getText()));
						empleado.setNombre(Util.parseaString(textNombre.getText()));
						empleado.setDNI(Util.parseaString(textDNI.getText()));
						empleado.setHoras_extra(Util.parseaIntNoNegativo(textHExtras.getText()));
						empleado.setTiempo_completo(true);
						dispose();
						Controlador.getInstancia().accion(Eventos.modificar_Empleado, empleado);
					}else{
						TEmpleadoParcial empleado = new TEmpleadoParcial();
						empleado.setId_empleado(Util.parseaIntNoNegativo(textID.getText()));
						empleado.setNombre(Util.parseaString(textNombre.getText()));
						empleado.setDNI(Util.parseaString(textDNI.getText()));
						if (comboTParcial.getSelectedItem().equals("t")){
							empleado.setTurno(TipoTurno.t);
						}else {
							empleado.setTurno(TipoTurno.m);
						}
						empleado.setTiempo_completo(false);
						dispose();
						Controlador.getInstancia().accion(Eventos.modificar_Empleado, empleado);
					}
					dispose();
				}catch (Exception ex){
					JOptionPane.showMessageDialog(getRootPane(), ex.getMessage(), "Error Modificar Empleado", JOptionPane.ERROR_MESSAGE);
				}
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
		panelBotones.add(modificar);

		return panelBotones;
	}

	private JComboBox seleccionarTParcial() {
		comboTParcial = new JComboBox();
		comboTParcial.setEnabled(false);
		comboTParcial.addItem("m");
		comboTParcial.addItem("t");

		return comboTParcial;
	}

	private JComboBox seleccionarTCompleto() {
		comboTCompleto = new JComboBox();
		comboTCompleto.addItem("true");
		comboTCompleto.addItem("false");

		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (comboTCompleto.getSelectedItem().equals("false")){
					textHExtras.setEnabled(false);
					comboTParcial.setEnabled(true);
				}else{
					comboTParcial.setEnabled(false);
					textHExtras.setEnabled(true);
				}
			}
		};
		comboTCompleto.addActionListener(actionListener);

		return comboTCompleto;
	}
}
