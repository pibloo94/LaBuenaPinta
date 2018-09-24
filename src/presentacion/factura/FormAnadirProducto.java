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

import negocio.cerveza.TCerveza;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FormAnadirProducto extends  JDialog{

	private JTextField textCantidad;
	private JComboBox comboID;
	private JComboBox comboProd;

	public FormAnadirProducto() {
		setTitle("Anadir Producto");
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
		setVisible(false);
		pack();
		setLocationRelativeTo(null);
	}

	private JPanel camposFormulario(){

		JPanel panelCampos = new JPanel(new GridLayout(3,2,0,7));
		Border border = panelCampos.getBorder();
		Border margin = new EmptyBorder(10,10,10,10);
		panelCampos.setBorder(new CompoundBorder(border, margin));

		//IDFactura
		JLabel panelFacturaID= new JLabel("IDfactura");
		panelCampos.add(panelFacturaID);

		comboID = comboIDfact();
		panelCampos.add(comboID);

		//Productos
		JLabel panelDevProd = new JLabel("Productos");
		panelCampos.add(panelDevProd);

		comboProd = comboProductos();
		panelCampos.add(comboProd);

		//Cantidad
		JLabel panelCantidad = new JLabel("Cantidad");
		panelCampos.add(panelCantidad);

		textCantidad = new JTextField(10);
		panelCampos.add(textCantidad);

		return panelCampos;
	}

	private  JPanel botonesFormulario(){
		//Botones
		JPanel panelBotones = new JPanel(new FlowLayout());

		JButton crear = new JButton("AÑADIR PRODUCTO");
		crear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				TLineaFactura lineaFactura = new TLineaFactura();
				try {
					lineaFactura.setId_factura(Util.parseaIntNoNegativo(comboID.getSelectedItem().toString()));
					lineaFactura.setId_cerveza(Util.parseaIntNoNegativo(comboProd.getSelectedItem().toString()));
					lineaFactura.setCantidad(Util.parseaIntNoNegativo(textCantidad.getText()));
					dispose();
					Controlador.getInstancia().accion(Eventos.anadir_producto, lineaFactura);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(getRootPane(), ex.getMessage(), "Error añadir producto", JOptionPane.ERROR_MESSAGE);
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

	private JComboBox comboProductos(){
		comboProd = new JComboBox();

		List<TCerveza> listaCervezas = new ArrayList<TCerveza>();
		Controlador.getInstancia().accion(Eventos.mostraTodos_Cerveza, listaCervezas);
		for (TCerveza cerveza : listaCervezas) {
				comboProd.addItem(cerveza.getId_cerveza());
		}
		return comboProd;
	}

	private JComboBox comboIDfact(){
		comboID = new JComboBox();

		List<TFactura> listaFacturas = new ArrayList<TFactura>();
		Controlador.getInstancia().accion(Eventos.mostraTodos_Factura, listaFacturas);
		for (TFactura factura : listaFacturas) {
			if (factura.isAbierta()) {
				comboID.addItem(factura.getId_factura());
			}
		}
		return comboID;
	}
}
