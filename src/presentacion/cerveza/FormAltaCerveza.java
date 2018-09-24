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
import negocio.marca.TMarca;
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
import java.util.ArrayList;
import java.util.List;

public class FormAltaCerveza extends JDialog{

	private JTextField textNombre;
	private JTextField textStock;
	private JTextField textGraduacion;
	private JTextField textPrecio;
	private JTextField textMarca;
	private JComboBox comboBoxActiva;
	private JComboBox comboBoxMarca;


	public FormAltaCerveza() {
		setTitle("Alta Cerveza");
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

		//Precio
		JLabel panelPrecio = new JLabel("Precio");
		panelCampos.add(panelPrecio);

		textPrecio = new JTextField(10);
		panelCampos.add(textPrecio);

		//Marca
		JLabel panelMarca = new JLabel("Marca(ID)");
		panelCampos.add(panelMarca);

		comboBoxMarca = seleccionarMarca();
		panelCampos.add(comboBoxMarca);

		//Activa
		JLabel panelActiva= new JLabel("Activa");
		panelCampos.add(panelActiva);

		comboBoxActiva = seleccionarActiva();
		panelCampos.add(comboBoxActiva);

		return panelCampos;
	}

	private JPanel botonesFormulario(){
		//Botones
		JPanel panelBotones = new JPanel(new FlowLayout());

		JButton crear = new JButton("CREAR");
		crear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				TCerveza cerveza = new TCerveza();
				try {
					cerveza.setNombre(Util.parseaString(textNombre.getText()));
					cerveza.setGraduacion(Util.parseaFloatNoNegativo(textGraduacion.getText()));
					cerveza.setPrecio(Util.parseaFloatNoNegativo(textPrecio.getText()));
					cerveza.setStock(Util.parseaIntNoNegativo(textStock.getText()));
					cerveza.setActiva(Util.parseaActiva(comboBoxActiva.getSelectedItem().toString()));
					cerveza.set_marca((int)comboBoxMarca.getSelectedItem());
					dispose();
					Controlador.getInstancia().accion(Eventos.insertar_Cerveza, cerveza);
				}catch (Exception ex){
					JOptionPane.showMessageDialog(getRootPane(), ex.getMessage(), "Error Alta Cerveza", JOptionPane.ERROR_MESSAGE);
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
		comboBoxActiva = new JComboBox();
		comboBoxActiva.addItem("true");
		comboBoxActiva.addItem("false");

		return comboBoxActiva;
	}

	private JComboBox seleccionarMarca(){
		comboBoxMarca = new JComboBox();

		List<TMarca> listaMarcas = new ArrayList<TMarca>();
		Controlador.getInstancia().accion(Eventos.mostraTodos_Marca, listaMarcas);
		for (TMarca marca: listaMarcas) {
			comboBoxMarca.addItem(marca.getId_marca());
		}
		return  comboBoxMarca;
	}
}