/*
<h2>Copyright</h2>
Copyright (c) 1998-2002 Interworld Transport.  All rights reserved.<br>
--------------------------------------------------------------------------------
<br>
---com.interworldtransport.sailevent.ToolSailMomentsEvents<br>
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
---com.interworldtransport.sailevent.ToolSailMomentsEvents<br>
--------------------------------------------------------------------------------
 */
 
package com.interworldtransport.sailevent;

import com.interworldtransport.sailgui.*;
import com.interworldtransport.sailmodel.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/** com.interworldtransport.sailevent.ToolSailMomentsEvents  
 * This class groups the actions associated with the Tools|Show Orbit Moments menu.
 * 
 * @version 0.02, $Date: 2002/02/15 08:55:12 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailgui.SailAway
 * @see com.interworldtransport.sailmodel.RadiationForceModel
 * @see com.interworldtransport.sailmodel
 */
public class ToolSailMomentsEvents implements ActionListener {

    protected	JCheckBoxMenuItem	ControlIt;
    protected	SailAway		ParentGUI;
    protected	ToolEvents		Parent;

/** This is the default constructor.
 */
    public ToolSailMomentsEvents(SailAway pGUI, JCheckBoxMenuItem pSailForcer, ToolEvents pParent) {
	this.ParentGUI=pGUI;
	this.Parent=pParent;
	this.ControlIt=pSailForcer;
	this.ControlIt.addActionListener(this);

	}//end of ToolSailMomentsEvents Menu constructor

/** This is the default action to be performed by the Tools|Show Sail Moments menu.
 *  The Sail Moments frame should be called into being.
 */
    public void actionPerformed(ActionEvent evt) {
/*
	if (!ControlIt.isSelected()) {
		if (this.ParentGUI.SailForce!=null) {
			this.ParentGUI.SailForce.dispose();
			this.ParentGUI.SailForce=null;
			}
		}
	else {
		this.ParentGUI.EventModel.ActParts.f.nullDetailer3();
		this.ParentGUI.EventModel.ActParts.b.nullDetailer3();
		this.ParentGUI.SailForce=new SailMoments("Sail Moments for: "+ParentGUI.TestSail.getSailName(), ParentGUI);
		this.ParentGUI.EventModel.ActParts.f.setDetailers();
		this.ParentGUI.EventModel.ActParts.b.setDetailers();
		this.ParentGUI.SailForce.setVisible(true);
		}
*/
	}//end of action performed method.


    }//end of ToolSailMomentsEvents class

