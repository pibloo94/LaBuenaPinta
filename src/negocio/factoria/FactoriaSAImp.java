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

package negocio.factoria;

import negocio.cerveza.CervezaSA;
import negocio.empleado.EmpleadoSA;
import negocio.factura.FacturaSA;
import negocio.marca.MarcaSA;
import negocio.cerveza.CervezaSAImp;
import negocio.empleado.EmpleadoSAImp;
import negocio.factura.FacturaSAImp;
import negocio.marca.MarcaSAImp;

public class FactoriaSAImp extends FactoriaSA{

	@Override
	public MarcaSA generarSAMarca() {
		return new MarcaSAImp();
	}

	@Override
	public CervezaSA generarSACerveza() {
		return new CervezaSAImp();
	}

	@Override
	public EmpleadoSA generarSAEmpleado() {
		return new EmpleadoSAImp();
	}

	@Override
	public FacturaSA generarSAFactura() {
		return new FacturaSAImp();
	}
}
