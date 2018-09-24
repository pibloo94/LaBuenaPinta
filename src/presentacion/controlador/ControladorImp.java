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

package presentacion.controlador;

import negocio.factoria.FactoriaSA;
import negocio.cerveza.TCerveza;
import negocio.empleado.TEmpleado;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
import negocio.marca.TMarca;
import presentacion.util.Util;

import java.util.List;

public class ControladorImp extends Controlador {

	@Override
	public void accion(Eventos evento, Object datos) {
		switch (evento) {
			case insertar_Marca:
				if (FactoriaSA.getInstancia().generarSAMarca().insertar_marca((TMarca) datos)) {
					Util.informar("Marca registrada con exito");
				} else {
					Util.informar("Marca no registrada");
				}
				break;
			case mostrar_Marca:
				TMarca marcaMostrar = FactoriaSA.getInstancia().generarSAMarca().mostrar_marca((TMarca) datos);
				if (marcaMostrar != null) {
					Util.informar(marcaMostrar.toString());
				} else {
					Util.informar("El ID de la marca no existe");
				}
				break;
			case mostraTodos_Marca:
				List listaMarca = (List) datos;
				listaMarca.addAll(FactoriaSA.getInstancia().generarSAMarca().mostrar_todos_marca());
				break;
			case modificar_Marca:
				if (FactoriaSA.getInstancia().generarSAMarca().modificar_marca((TMarca) datos)) {
					Util.informar("Marca modificada con exito");
				} else {
					Util.informar("Marca no modificada");
				}
				break;
			case eliminar_Marca:
				if (FactoriaSA.getInstancia().generarSAMarca().baja_marca((TMarca) datos)) {
					Util.informar("Marca eliminada con exito");
				} else {
					Util.informar("Marca no eliminada");
				}
				break;
			case insertar_Cerveza:
				if (FactoriaSA.getInstancia().generarSACerveza().insertar_cerveza((TCerveza) datos)) {
					Util.informar("Cerveza registrada con exito");
				} else {
					Util.informar("Cerveza no registrada");
				}
				break;
			case mostrar_Cerveza:
				TCerveza cervezaMostrar = FactoriaSA.getInstancia().generarSACerveza().mostrar_cerveza((TCerveza) datos);
				if (cervezaMostrar != null) {
					Util.informar(cervezaMostrar.toString());
				} else {
					Util.informar("El ID de la cerveza no existe");
				}
				break;
			case mostraTodos_Cerveza:
				List listCerveza = (List) datos;
				listCerveza.addAll(FactoriaSA.getInstancia().generarSACerveza().mostrar_todos_cerveza());
				break;
			case modificar_Cerveza:
				if (FactoriaSA.getInstancia().generarSACerveza().modificar_cerveza((TCerveza) datos)) {
					Util.informar("Cerveza modificada con exito");
				} else {
					Util.informar("Cerveza no modificada");
				}
				break;
			case eliminar_Cerveza:
				if (FactoriaSA.getInstancia().generarSACerveza().baja_cerveza((TCerveza) datos)) {
					Util.informar("Cerveza eliminada con exito");
				} else {
					Util.informar("Cerveza no eliminada");
				}
				break;
			case insertar_Empleado:
				if (FactoriaSA.getInstancia().generarSAEmpleado().insertar_emplado((TEmpleado) datos)) {
					Util.informar("Empleado registrado con exito");
				} else {
					Util.informar("Empleado no creado");
				}
				break;
			case mostrar_Empleado:
				TEmpleado empleadoMostrar = FactoriaSA.getInstancia().generarSAEmpleado().mostrar_empleado((TEmpleado) datos);
				if (empleadoMostrar != null) {
					Util.informar(empleadoMostrar.toString());
				} else {
					Util.informar("El ID del empleado no existe");
				}
				break;
			case mostraTodos_Empleado:
				List listaEmpleado = (List) datos;
				listaEmpleado.addAll(FactoriaSA.getInstancia().generarSAEmpleado().mostrar_todos_empleado());
				break;
			case modificar_Empleado:
				if (FactoriaSA.getInstancia().generarSAEmpleado().modificar_empleado((TEmpleado) datos)) {
					Util.informar("Empleado modificado con exito");
				} else {
					Util.informar("Empleado no modificado");
				}
				break;
			case eliminar_Empleado:
				if (FactoriaSA.getInstancia().generarSAEmpleado().baja_empleado((TEmpleado) datos)) {
					Util.informar("Empleado eliminado con exito");
				} else {
					Util.informar("Empleado no eliminado");
				}
				break;
			case insertar_Factura:
				if (FactoriaSA.getInstancia().generarSAFactura().insertar_factura((TFactura) datos)) {
					Util.informar("Factura generada con exito");
				} else {
					Util.informar("Factura no generada (empleado no activo)");
				}
				break;
			case mostrar_Factura:
				TFactura facturaMostrar = FactoriaSA.getInstancia().generarSAFactura().mostrar_factura((TFactura) datos);
				if (facturaMostrar != null) {
					Util.informar(facturaMostrar.toString());
				} else {
					Util.informar("El ID de la marca no existe");
				}
				break;
			case mostraTodos_Factura:
				List listaFactura = (List) datos;
				listaFactura.addAll(FactoriaSA.getInstancia().generarSAFactura().mostrar_todos_factura());
				break;
			case eliminar_Factura:
				if (FactoriaSA.getInstancia().generarSAFactura().baja_factura((TFactura) datos)) {
					Util.informar("Factura eliminada con exito");
				} else {
					Util.informar("Factura no eliminada");
				}
				break;

			case anadir_producto:
				if (FactoriaSA.getInstancia().generarSAFactura().anadir_producto((TLineaFactura) datos)) {
					Util.informar("Producto anadido con exito");
				} else {
					Util.informar("Producto no anadido");
				}
				break;

			case cerrar_factura:
				if (FactoriaSA.getInstancia().generarSAFactura().cerrar_factura((TFactura) datos)) {
					Util.informar("Factura cerrada con exito");
				} else {
					Util.informar("Factura no cerrada");
				}
				break;
		}
	}
}
