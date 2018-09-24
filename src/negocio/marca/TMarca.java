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

public class TMarca {
	private int id_marca;
	private String nombre;
	private String sede;
	private String pais;
	private boolean activa;

	public  TMarca() {
	}

	public TMarca(int id_marca, String nombre, String sede, String pais, boolean activa) {
		this.id_marca = id_marca;
		this.nombre = nombre;
		this.sede = sede;
		this.pais = pais;
		this.activa = activa;
	}

	public int getId_marca() {
		return id_marca;
	}

	public void setId_marca(int id_marca) {
		this.id_marca = id_marca;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	@Override
	public String toString() {
		return "******Marca*******\n"+
				"ID: "+this.getId_marca() + "\n"+
				"Nombre: "+this.getNombre() + "\n"+
				"Sede: "+this.getSede() + "\n"+
				"Pais: " +this.getPais();
	}
}
