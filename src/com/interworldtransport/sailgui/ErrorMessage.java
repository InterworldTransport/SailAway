/* 
<h2>Copyright</h2>
Copyright (c) 1998-2001 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailgui.ErrorMessage---------------------------------
<p>
Interworld Transport grants you ("Licensee") a license to this software 
under the terms of the GNU General Public License.<br>
A full copy of the license can be found bundled with this package or code file.
<p>
If the license file has become separated from the package, code file, or binary 
executable, the Licensee is still expected to read about the license at the 
following URL before accepting this material.
<blockquote><code>http://www.opensource.org/gpl-license.html</code></blockquote>
<p>
Use of this code or executable objects derived from it by the Licensee states 
their willingness to accept the terms of the license.
<p>
A prospective Licensee unable to find a copy of the license terms should contact
Interworld Transport for a free copy.
<p>
---com.interworldtransport.sailgui.ErrorMessage---------------------------------
 */

/**
 * Derived from original code for ThreeSpace made available under GPL by... 
 * ThreeSpace (c) Copyright 2001 Adam Chodorowski, John Nilsson
 * Created during spring of 2001 for the PROST01 course
 */

package com.interworldtransport.sailgui;

import javax.swing.JOptionPane;

/**
 * A class used by the GUI to show error messages
 */
public class ErrorMessage {
	/**
	 * Shows an error message.
	 * 
	 * @param mess  The Message
	 * @param title The Title
	 */
	public final static void show(String mess, String title) {
		JOptionPane.showMessageDialog(null, mess, "Error - " + title, JOptionPane.ERROR_MESSAGE);
	}
}
