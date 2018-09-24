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

package presentacion.util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;

public class Util {

	public static void addEscapeListener(final JDialog dialog) {
		ActionListener escListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		};

		dialog.getRootPane().registerKeyboardAction(escListener,
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
	}

	public static String parseaString(String s){
		if (s == null || s.isEmpty()){
			throw new NullPointerException("Campo vacio");
		}
		return s;
	}

	public static int parseaIntNoNegativo(String s) throws ParseException, NumberFormatException, NullPointerException {
		if (s == null || s.isEmpty()){
			throw new NullPointerException("El valor no es valido.");
		}

		int x = Integer.parseInt(s);
		if (x < 0){
			throw new ParseException("El valor no es valido.", 0);
		}
		return x;
	}

	public static float parseaFloatNoNegativo(String s)throws ParseException, NumberFormatException, NullPointerException{
		if (s == null || s.isEmpty()){
			throw new NullPointerException("El valor no es valido.");
		}

		float x = Float.parseFloat(s);
		if (x < 0){
			throw new ParseException("El valor no es valido.", 0);
		}
		return x;
	}

	public static boolean parseaActiva(String s){
		return s.equals("true");
	}

	public static void informar(String s) {
		JOptionPane.showMessageDialog(null, s);
	}
}
