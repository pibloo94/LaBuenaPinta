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
import presentacion.util.Util;
import presentacion.util.TipoTurno;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormAltaEmpleado extends JDialog{

	private JTextField textNombre;
	private JTextField textDNI;
	private JTextField textHExtras;
	private JComboBox comboActiva;
	private JComboBox comboTCompleto;
	private JComboBox comboTParcial;

	public FormAltaEmpleado() {
		setTitle("Alta Empleado");
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

		//Activa
		JLabel panelActiva= new JLabel("Activo");
		panelCampos.add(panelActiva);

		comboActiva = seleccionarActiva();
		panelCampos.add(comboActiva);

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

	private JPanel botonesFormulario(){
		//Botones
		JPanel panelBotones = new JPanel(new FlowLayout());

		JButton crear = new JButton("CREAR");
		crear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(comboTCompleto.getSelectedItem().equals("true")){
						TEmpleadoCompleto empleado = new TEmpleadoCompleto();
						empleado.setNombre(Util.parseaString(textNombre.getText()));
						empleado.setDNI(Util.parseaString(textDNI.getText()));
						empleado.setActivo(Util.parseaActiva(comboActiva.getSelectedItem().toString()));
						empleado.setHoras_extra(Util.parseaIntNoNegativo(textHExtras.getText()));
						empleado.setTiempo_completo(true);
						dispose();
						Controlador.getInstancia().accion(Eventos.insertar_Empleado, empleado);
					}else{
						TEmpleadoParcial empleado = new TEmpleadoParcial();
						empleado.setNombre(Util.parseaString(textNombre.getText()));
						empleado.setDNI(Util.parseaString(textDNI.getText()));
						empleado.setActivo(Util.parseaActiva(comboActiva.getSelectedItem().toString()));
						if (comboTParcial.getSelectedItem().equals("t")){
							empleado.setTurno(TipoTurno.t);
						}else {
							empleado.setTurno(TipoTurno.m);
						}
						empleado.setTiempo_completo(false);
						dispose();
						Controlador.getInstancia().accion(Eventos.insertar_Empleado, empleado);
					}
				}catch (Exception ex){
					JOptionPane.showMessageDialog(getRootPane(), ex.getMessage(), "Error Alta Empleado", JOptionPane.ERROR_MESSAGE);
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
		panelBotones.add(crear);

		return panelBotones;
	}

	private JComboBox seleccionarActiva() {
		comboActiva = new JComboBox();
		comboActiva.addItem("true");
		comboActiva.addItem("false");

		return comboActiva;
	}

	private JComboBox seleccionarTCompleto() {
		comboTCompleto = new JComboBox();
		comboTCompleto.addItem("true");
		comboTCompleto.addItem("false");

		comboTCompleto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (comboTCompleto.getSelectedItem().equals("false")){
					comboTParcial.setEnabled(true);
					textHExtras.setEnabled(false);
				}else{
					comboTParcial.setEnabled(false);
					textHExtras.setEnabled(true);
				}
			}
		});

		return comboTCompleto;
	}

	private JComboBox seleccionarTParcial() {
		comboTParcial = new JComboBox();
		comboTParcial.addItem("m");
		comboTParcial.addItem("t");
		comboTParcial.setEnabled(false);

		return comboTParcial;
	}
}
