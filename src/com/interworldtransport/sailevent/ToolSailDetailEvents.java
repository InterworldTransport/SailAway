/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailevent.ToolSailDetailEvents-------------------------
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
Use of this code or executable objects derived from it by the Licensee states their 
willingness to accept the terms of the license.
<p>
A prospective Licensee unable to find a copy of the license terms should contact
Interworld Transport for a free copy.
<p>
---com.interworldtransport.sailevent.ToolSailDetailEvents-------------------------
*/

package com.interworldtransport.sailevent;

import com.interworldtransport.sailgui.*;
import com.interworldtransport.sailmodel.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/** com.interworldtransport.sailevent.ToolSailDetailEvents  
 * This class groups the actions associated with the Tools|Show Sail Details menu.
 * 
 * @version 0.02, $Date: 2002/02/15 08:55:12 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailgui.SailAway
 * @see com.interworldtransport.sailmodel.Sail
 * @see com.interworldtransport.sailmodel
 */
public class ToolSailDetailEvents implements ActionListener {

    protected	JCheckBoxMenuItem	ControlIt;
    protected	SailAway		ParentGUI;
    protected	ToolEvents		Parent;

/** This is the default constructor.
 */
    public ToolSailDetailEvents(SailAway pGUI, JCheckBoxMenuItem pSailDetailer, ToolEvents pParent) {
	this.ParentGUI=pGUI;
	this.Parent=pParent;
	this.ControlIt=pSailDetailer;
	this.ControlIt.addActionListener(this);

	}//end of ToolSailDetailEvents Menu constructor

/** This is the default action to be performed by the Tools|Show Sail Details menu.
 *  The Sail Detail frame should be called into being.
 */
    public void actionPerformed(ActionEvent evt) {

	if (!ControlIt.isSelected()) {
		if (this.ParentGUI.SailParm!=null) {
			this.ParentGUI.SailParm.dispose();
			this.ParentGUI.SailParm=null;
			}
		}
	else {
		this.ParentGUI.EventModel.ActParts.f.nullDetailer2();
		this.ParentGUI.EventModel.ActParts.b.nullDetailer2();
		this.ParentGUI.SailParm=new SailDetailing("Sail Detailer for: "+ParentGUI.TestSail.getSailName(), ParentGUI);
		this.ParentGUI.EventModel.ActParts.f.setDetailers();
		this.ParentGUI.EventModel.ActParts.b.setDetailers();
		this.ParentGUI.SailParm.setVisible(true);
		}

	}//end of action performed method.

    }//end of ToolSailDetailEvents class
