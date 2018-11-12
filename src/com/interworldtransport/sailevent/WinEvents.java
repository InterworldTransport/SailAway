/*
<h2>Copyright</h2>
Copyright (c) 1998-2002 Interworld Transport.  All rights reserved.<br>
--------------------------------------------------------------------------------
<br>
---com.interworldtransport.sailevent.WinEvents<br>
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
---com.interworldtransport.sailevent.WinEvents<br>
--------------------------------------------------------------------------------
 */

package com.interworldtransport.sailevent;

import com.interworldtransport.sailgui.*;
import com.interworldtransport.sailmodel.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class WinEvents implements ActionListener {
    protected WinPackEvents	pk;
    protected WinCloseEvents	cl;
    protected WinIconifyEvents	ic;

    protected SailAway 		ParentGUI;

/** This is the default constructor.  The event structure of the Window
 *  menu starts here and finishes with the child menu items.
 */
    public WinEvents(SailAway pTheGUI) {

	this.ParentGUI=pTheGUI;

	this.pk = new WinPackEvents(pTheGUI.TheMenuBar.mniPack, this);
	this.cl = new WinCloseEvents(pTheGUI.TheMenuBar.mniClose, this);
	this.ic = new WinIconifyEvents(pTheGUI.TheMenuBar.mniIconify, this);

	}//end of WinEvents Menu constructor

/** This is the default action to be performed by all members of the Window menu.
 *  It will be overridden by specific members of the menu.
 */
    public void actionPerformed(ActionEvent evt) {
	;
	}//end of action performed method.


    }//end of WinEvents class
