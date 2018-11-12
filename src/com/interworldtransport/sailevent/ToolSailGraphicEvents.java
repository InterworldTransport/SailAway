/*
<h2>Copyright</h2>
Copyright (c) 1998-2002 Interworld Transport.  All rights reserved.<br>
--------------------------------------------------------------------------------
<br>
---com.interworldtransport.sailevent.ToolSailGraphicEvents<br>
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
---com.interworldtransport.sailevent.ToolSailGraphicEvents<br>
--------------------------------------------------------------------------------
 */

package com.interworldtransport.sailevent;

import com.interworldtransport.sailgui.*;
import com.interworldtransport.sailmodel.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/** com.interworldtransport.sailevent.ToolSailGraphicEvents  
 * This class groups the actions associated with the Tools|Show Orbit Graphic menu.
 * 
 * @version 0.02, $Date: 2002/02/15 08:55:12 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailgui.SailAway
 * @see com.interworldtransport.sailmodel.Sail
 * @see com.interworldtransport.sailmodel
 */
public class ToolSailGraphicEvents implements ActionListener {

    protected	JCheckBoxMenuItem	ControlIt;
    protected	SailAway			ParentGUI;
    protected	ToolEvents			Parent;

/** This is the default constructor.
 */
    public ToolSailGraphicEvents(SailAway pGUI, JCheckBoxMenuItem pSailShower, ToolEvents pParent) {
	this.ParentGUI=pGUI;
	this.Parent=pParent;
	this.ControlIt=pSailShower;
	this.ControlIt.addActionListener(this);

	}//end of ToolSailGraphicEvents Menu constructor

/** This is the default action to be performed by the Tools|Show Sail Graphic menu.
 *  The Sail Attitude Graphic frame should be called into being.
 */
    public void actionPerformed(ActionEvent evt) {
/*
	if (!ControlIt.isSelected()) {
		if (this.ParentGUI.SailDrawing!=null) this.ParentGUI.SailDrawing.dispose();
		}
	else {
		this.ParentGUI.SailDrawing=new SailGraphic("Graphical Sail for: "+ParentGUI.TestSail.getSailName(), ParentGUI);
		this.ParentGUI.SailDrawing.setVisible(true);
		}
*/
	}//end of action performed method.

    }//end of ToolSailGraphicEvents class
