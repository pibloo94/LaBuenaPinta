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

package negocio.factura;

import integracion.factoriaDAO.FactoriaDAO;
import negocio.cerveza.TCerveza;
import negocio.empleado.TEmpleado;

import java.util.List;

public class FacturaSAImp implements FacturaSA{
	@Override
	public boolean insertar_factura(TFactura tFactura) {
		TEmpleado empleado = FactoriaDAO.getInstancia().getEmpleadoDAO().mostrar(tFactura.getEmpleado());
		if(empleado != null && empleado.isActivo()) {
			FactoriaDAO.getInstancia().getFacturaDAO().insertar(tFactura);
			return true;
		}
		else return false;
	}

	@Override
	public TFactura mostrar_factura(TFactura tFactura) {
		return FactoriaDAO.getInstancia().getFacturaDAO().mostrar(tFactura.getId_factura());
	}

	@Override
	public List<TFactura> mostrar_todos_factura() {
		return FactoriaDAO.getInstancia().getFacturaDAO().mostrarTodos();
	}

	@Override
	public boolean baja_factura(TFactura tFactura) {
		TFactura tl = FactoriaDAO.getInstancia().getFacturaDAO().mostrar(tFactura.getId_factura());

		if (tl == null){
			return false;
		} else if (tl.isAbierta()){
			return false;
		}
		else{
			FactoriaDAO.getInstancia().getFacturaDAO().eliminar(tFactura.getId_factura());
			return true;
		}
	}
	@Override
	public boolean anadir_producto(TLineaFactura lineaFactura){
		TFactura f = FactoriaDAO.getInstancia().getFacturaDAO().mostrar(lineaFactura.getId_factura());
		TCerveza c = FactoriaDAO.getInstancia().getCervezaDAO().mostrar(lineaFactura.getId_cerveza());
		if (f == null || !f.isAbierta() || c == null || !c.isActiva() || lineaFactura.getCantidad() > c.getStock() || lineaFactura.getCantidad() <= 0){
			return false;
		}
		else {
			FactoriaDAO.getInstancia().getFacturaDAO().anadirProducto(lineaFactura, c);
			return true;
		}
	}

	@Override
	public boolean cerrar_factura(TFactura tFactura) {
		TFactura tl = FactoriaDAO.getInstancia().getFacturaDAO().mostrar(tFactura.getId_factura());

		if (tl == null){
			return false;
		} else {
			FactoriaDAO.getInstancia().getFacturaDAO().cerrar(tFactura.getId_factura());
			return true;
		}
	}
}
