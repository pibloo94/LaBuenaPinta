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

public class TCerveza {
	private int id_cerveza;
	private String nombre;
	private int stock;
	private double graduacion;
	private double precio;
	private boolean activa;
	private int marca;

	public TCerveza() {
	}

	public TCerveza(int id_cerveza, String nombre, int stock, double graduacion, double precio, boolean activa, int id_marca) {
		this.id_cerveza = id_cerveza;
		this.nombre = nombre;
		this.stock = stock;
		this.graduacion = graduacion;
		this.precio = precio;
		this.activa = activa;
		this.marca = id_marca;
	}

	public int getId_cerveza() {
		return id_cerveza;
	}

	public void setId_cerveza(int id_cerveza) {
		this.id_cerveza = id_cerveza;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public double getGraduacion() {
		return graduacion;
	}

	public void setGraduacion(double graduacion) {
		this.graduacion = graduacion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	public int get_marca() {
		return marca;
	}

	public void set_marca(int id_marca) {
		this.marca = id_marca;
	}


	@Override
	public String toString() {
		return "******Cerveza*******\n"+
				"ID: "+this.getId_cerveza() + "\n"+
				"Nombre: "+this.getNombre() + "\n"+
				"Stock: "+this.getStock() + "\n"+
				"Graduacion: " +this.getGraduacion()+"\n" +
				"Precio: "+this.getPrecio() + "\n"+
				"Activa: "+ this.isActiva();
	}
}
