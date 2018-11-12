/*
<h2>Copyright</h2>
Copyright (c) 1998-2002 Interworld Transport.  All rights reserved.<br>
--------------------------------------------------------------------------------
<br>
---com.interworldtransport.sailevent.WinCloseEvents<br>
--------------------------------------------------------------------------------
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
--------------------------------------------------------------------------------
<br>
---com.interworldtransport.sailevent.WinCloseEvents<br>
--------------------------------------------------------------------------------
 */
 
package com.interworldtransport.sailevent;

import com.interworldtransport.sailgui.*;
import com.interworldtransport.sailmodel.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class WinCloseEvents implements ActionListener {

    protected WinEvents		Parent;
    protected JMenuItem		ControlIt;

/** This is the default constructor.
 */
    public WinCloseEvents(JMenuItem pWin, WinEvents pParent) {
	this.ControlIt=pWin;
	this.Parent=pParent;
	this.ControlIt.addActionListener(this);

	}//end of WinCloseEvents Menu constructor

/** This is the actual action to be performed by this menu item.
 */
    public void actionPerformed(ActionEvent evt) {
	//Show the Close feature;
	System.out.println("Closing the GUI now...");
	}//end of action performed method.

    }//end of WinCloseEvents class
