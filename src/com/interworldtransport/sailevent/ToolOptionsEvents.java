/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailevent.ToolOptionsEvents--------------------------
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
---com.interworldtransport.sailevent.ToolOptionsEvents--------------------------
*/

package com.interworldtransport.sailevent;

import com.interworldtransport.sailgui.*;
import com.interworldtransport.sailmodel.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/** com.interworldtransport.sailevent.ToolOptionsEvents  
 * This class groups the actions associated with the Tools|Options menu.
 * 
 * @version 0.02, $Date: 2001/11/03 08:43:10 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailgui.SailAway
 * @see com.interworldtransport.sailmodel.OrbitEllipse
 * @see com.interworldtransport.sailmodel
 */
public class ToolOptionsEvents implements ActionListener {

    protected JMenuItem		ControlIt;
    protected SailAway		ParentGUI;
    protected ToolEvents	Parent;

/** This is the default constructor.
 */
    public ToolOptionsEvents(	SailAway pGUI, 
    				JMenuItem pOptions, 
				ToolEvents pParent) {
	this.ParentGUI=pGUI;
	this.Parent=pParent;
	this.ControlIt=pOptions;
	this.ControlIt.addActionListener(this);

	}//end of HelpSupportEvents Menu constructor

/** This is the default action to be performed by the Tools|Options menu.
 *  The Options/Preference Dialog box should be called into being.
 */
    public void actionPerformed(ActionEvent evt) {
	//Show the support custom dialog box
	OptionsDialog opt = new OptionsDialog(this.ParentGUI);
	}//end of action performed method.

    }//end of ToolOptionsEvents class

