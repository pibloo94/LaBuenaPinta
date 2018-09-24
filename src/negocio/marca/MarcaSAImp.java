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

package negocio.marca;

import integracion.factoriaDAO.FactoriaDAO;
import negocio.cerveza.TCerveza;

import java.util.List;

public class MarcaSAImp implements MarcaSA {

	@Override
	public boolean insertar_marca(TMarca tMarca) {
		TMarca tl = FactoriaDAO.getInstancia().getMarcaDAO().mostrarPorNombre(tMarca.getNombre());

		if (tl == null){
			FactoriaDAO.getInstancia().getMarcaDAO().insertar(tMarca);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public TMarca mostrar_marca(TMarca tMarca) {
		return FactoriaDAO.getInstancia().getMarcaDAO().mostrar(tMarca.getId_marca());
	}

	@Override
	public List<TMarca> mostrar_todos_marca() {
		return FactoriaDAO.getInstancia().getMarcaDAO().mostrarTodos();
	}

	@Override
	public boolean modificar_marca(TMarca tMarca) {
		TMarca tl = FactoriaDAO.getInstancia().getMarcaDAO().mostrar(tMarca.getId_marca());
		TMarca m = FactoriaDAO.getInstancia().getMarcaDAO().mostrarPorNombre(tMarca.getNombre());
		if (tl == null) {
			return false;
		} else if ( m != null  &&   m.getId_marca() != tl.getId_marca()) {
			return false;
		} else {
			FactoriaDAO.getInstancia().getMarcaDAO().modificar(tMarca);
			return true;
		}
	}

	@Override
	public boolean baja_marca(TMarca tMarca) {
		TMarca tl = FactoriaDAO.getInstancia().getMarcaDAO().mostrar(tMarca.getId_marca());
		List<TCerveza> tlCerveza = FactoriaDAO.getInstancia().getCervezaDAO().mostrarPorMarca(tMarca.getId_marca());

		if (tl == null){
			return false;
		}

		for (TCerveza tCerveza : tlCerveza) {
			if (tCerveza.isActiva()) {
				return false;
			}
		}

		FactoriaDAO.getInstancia().getMarcaDAO().eliminar(tMarca.getId_marca());
		return true;
	}
}
