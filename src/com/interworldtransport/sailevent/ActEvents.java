/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailevent.ActEvents----------------------------------
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
---com.interworldtransport.sailevent.ActEvents----------------------------------
*/
 
package com.interworldtransport.sailevent;

import com.interworldtransport.sailgui.*;
import com.interworldtransport.sailmodel.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/** com.interworldtransport.sailevent.ActEvents  
 * This class groups the event listeners associated with the Actions menu.
 * It may be used in the future to act on events associated with the entire Actions menu
 * by having it register as a Listeners with all of its controlled listeners.  The controlled
 * listeners will create an event or call their parent.  It could also register will all the 
 * components to which its listeners register....maybe....
 * 
 * @version 0.02, $Date: 2001/11/03 08:43:10 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailgui.SailAway
 * @see com.interworldtransport.sailmodel.OrbitEllipse
 * @see com.interworldtransport.sailmodel
 */
public class ActEvents implements ActionListener {
    public	ActIterateFEvents	f;
    public	ActIterateBEvents	b;
    protected 	SailAway 		ParentGUI;
    protected 	Sail 		ParentSail;

/** 
 * This is the default constructor.  The event structure of the Actions menu starts here and 
 * finishes with the child menu items.
 */
    public ActEvents(SailAway pTheGUI) {
	this.ParentGUI=pTheGUI;
	this.f = new ActIterateFEvents(	ParentGUI, 
					ParentGUI.TheMenuBar.mniIterateFor, 
					this);
	this.b = new ActIterateBEvents(	ParentGUI, 
					ParentGUI.TheMenuBar.mniIterateBak, 
					this);
	//this.setDemoSail(pTheGUI.TestSail);
	}//end of ActEvents Menu constructor
/**
 * This method sets the Sail for all Action Menu handlers to influence.
 */
    public void setDemoSail(Sail pSail) {
	this.ParentSail=pSail;
	}
/**
 * This is the default action to be performed by all members of the Action menu.
 * It will be overridden by specific members of the menu.
 */
    public void actionPerformed(ActionEvent evt) {
	;
	}//end of action performed method.

    }//end of ActEvents class
