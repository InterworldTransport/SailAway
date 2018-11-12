/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailevent.SailEventModel-------------------------------
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
---com.interworldtransport.sailevent.SailEventModel-------------------------------
 */

package com.interworldtransport.sailevent;

import com.interworldtransport.sailgui.*;
import com.interworldtransport.sailmodel.*;
import java.awt.*;
import javax.swing.*;


/** com.interworldtransport.sailevent.SailEventModel
 *
 *
 * @version 0.02, $Date: 2001/11/03 08:43:10 $
 * @author Dr Alfred W Differ
 * @see 	com.interworldtransport.sailgui.SailAway
 * @see 	com.interworldtransport.sailgui
 * @see 	com.interworldtransport.sailmodel
 */  
public class SailEventModel {
/** 
 * The GUIParent object maintains a reference to the GUI to which this EventModel applies
 */
    protected SailAway 		GUIParent;
/** The FileEvents object collects all File Event Handlers in one place in case there
 *  are actions they all share.  This object is responsible for constructing all File
 *  Event Handlers.
 */
    protected FileEvents	FileParts;
/** The EditEvents object collects all Edit Event Handlers in one place in case there
 *  are actions they all share.  This object is responsible for constructing all Edit
 *  Event Handlers.
 */
    //protected EditEvents	EditParts;
/** The ToolEvents object collects all Tool Event Handlers in one place in case there
 *  are actions they all share.  This object is responsible for constructing all Tool
 *  Event Handlers.
 */
    public	ToolEvents	ToolParts;
/** The ActEvents object collects all Action Event Handlers in one place in case there
 *  are actions they all share.  This object is responsible for constructing all Action
 *  Event Handlers.
 */
    public	ActEvents	ActParts;
/** The WinEvents object collects all Windowing Event Handlers in one place in case there
 *  are actions they all share.  This object is responsible for constructing all Windowing
 *  Event Handlers.
 */
    //protected WinEvents	WinParts;
/** The HelpEvents object collects all Help Event Handlers in one place in case there
 *  are actions they all share.  This object is responsible for constructing all Help
 *  Event Handlers.
 */
    protected HelpEvents	HelpParts;


/** This is the default constructor.  The event structure of the SailAway 
 *  application starts here and finishes with the child components that affect the
 *  physical model.
 */
    public SailEventModel(SailAway pGUIParent) {

	this.GUIParent=pGUIParent;

	this.FileParts = new FileEvents(this.GUIParent);
	//this.EditParts = new EditEvents(this.GUIParent);
	this.ToolParts = new ToolEvents(this.GUIParent);
	this.ActParts = new ActEvents(this.GUIParent);
	//this.WinParts = new WinEvents(this.GUIParent);
	this.HelpParts = new HelpEvents(this.GUIParent);

	}//end of SailEventModel constructor

    public void setDemoSail(Sail pSail) {
	FileParts.setDemoSail(pSail);
	//EditParts.setDemoSail(pSail);
	ToolParts.setDemoSail(pSail);
	ActParts.setDemoSail(pSail);
	//WinParts.setDemoSail(pSail);
	HelpParts.setDemoSail(pSail);
	}


    }//end of SailEventModel class
