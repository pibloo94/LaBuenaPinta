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

package negocio.cerveza;

import integracion.factoriaDAO.FactoriaDAO;
import negocio.marca.TMarca;

import java.util.List;

public class CervezaSAImp implements CervezaSA{

	@Override
	public boolean insertar_cerveza(TCerveza tCerveza) {
		TCerveza tl = FactoriaDAO.getInstancia().getCervezaDAO().mostrarPorNombre(tCerveza.getNombre());

		//Comprobar que la marca existe
		TMarca tlmarca = FactoriaDAO.getInstancia().getMarcaDAO().mostrar(tCerveza.get_marca());

		if (tl == null && tlmarca != null && tlmarca.isActiva()){
			FactoriaDAO.getInstancia().getCervezaDAO().insertar(tCerveza);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public TCerveza mostrar_cerveza(TCerveza tCerveza) {
		return FactoriaDAO.getInstancia().getCervezaDAO().mostrar(tCerveza.getId_cerveza());
	}

	@Override
	public List<TCerveza> mostrar_todos_cerveza() {
		return FactoriaDAO.getInstancia().getCervezaDAO().mostrarTodos();
	}

	@Override
	public boolean modificar_cerveza(TCerveza tCerveza) {
		TCerveza tl = FactoriaDAO.getInstancia().getCervezaDAO().mostrar(tCerveza.getId_cerveza());
		TCerveza c = FactoriaDAO.getInstancia().getCervezaDAO().mostrarPorNombre(tCerveza.getNombre());

		if (tl == null) {
			return false;
		} else if ( c != null && c.getId_cerveza() != tl.getId_cerveza()) {
			return false;
		} else {
			FactoriaDAO.getInstancia().getCervezaDAO().modificar(tCerveza);
			return true;
		}
	}

	@Override
	public boolean baja_cerveza(TCerveza tCerveza) {
		TCerveza tl = FactoriaDAO.getInstancia().getCervezaDAO().mostrar(tCerveza.getId_cerveza());

		if (tl == null || tl.getStock() != 0){
			return false;
		} else {
			FactoriaDAO.getInstancia().getCervezaDAO().eliminar(tCerveza.getId_cerveza());
			return true;
		}
	}
}
