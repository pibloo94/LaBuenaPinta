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
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.util.Util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormModificarCerveza extends JDialog{

	private JTextField textNombre;
	private JTextField textStock;
	private JTextField textGraduacion;
	private JTextField textPrecio;
	private JTextField textID;

	public FormModificarCerveza() {
		setTitle("Modificar Cerveza");
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

		JPanel panelCampos = new JPanel(new GridLayout(5,2,0,7));
		Border border = panelCampos.getBorder();
		Border margin = new EmptyBorder(10, 10, 10, 10);
		panelCampos.setBorder(new CompoundBorder(border, margin));
		
		//ID
		JLabel panelID = new JLabel("ID buscado");
		panelCampos.add(panelID);
		textID = new JTextField(10);
		panelCampos.add(textID);

		//Nombre
		JLabel panelNombre = new JLabel("Nombre");
		panelCampos.add(panelNombre);

		textNombre = new JTextField(10);
		panelCampos.add(textNombre);

		//Stock
		JLabel panelStock = new JLabel("Stock");
		panelCampos.add(panelStock);

		textStock = new JTextField(10);
		panelCampos.add(textStock);

		//Graduacion
		JLabel panelGraduacion = new JLabel("Graduacion");
		panelCampos.add(panelGraduacion);

		textGraduacion = new JTextField(10);
		panelCampos.add(textGraduacion);

		//Graduacion
		JLabel panelPrecio = new JLabel("Precio");
		panelCampos.add(panelPrecio);

		textPrecio = new JTextField(10);
		panelCampos.add(textPrecio);

		return panelCampos;
	}

	private JPanel botonesFormulario(){
		//Botones
		JPanel panelBotones = new JPanel(new FlowLayout());

		JButton modificar = new JButton("MODIFICAR");
		modificar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				TCerveza cerveza = new TCerveza();
				try {
					cerveza.setId_cerveza(Util.parseaIntNoNegativo(textID.getText()));
					cerveza.setNombre(Util.parseaString(textNombre.getText()));
					cerveza.setStock((Util.parseaIntNoNegativo(textStock.getText())));
					cerveza.setGraduacion(Util.parseaFloatNoNegativo(textGraduacion.getText()));
					cerveza.setPrecio(Util.parseaFloatNoNegativo(textPrecio.getText()));
					dispose();
					Controlador.getInstancia().accion(Eventos.modificar_Cerveza,cerveza);
				}catch (Exception ex){
					JOptionPane.showMessageDialog(getRootPane(), ex.getMessage(), "Error Modificar Cerveza", JOptionPane.ERROR_MESSAGE);
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
}
