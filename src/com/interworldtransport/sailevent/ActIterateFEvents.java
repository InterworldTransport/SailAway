/*  
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailevent.ActIterateFEvents--------------------------
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
---com.interworldtransport.sailevent.ActIterateFEvents--------------------------
*/

package com.interworldtransport.sailevent;

import com.interworldtransport.sailgui.*;
import com.interworldtransport.sailmodel.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/** com.interworldtransport.sailevent.ActIterateFEvents  
 *  This class manages all events relating to the forward iteration
 *  of the Sail's path along its orbit.  The Sail is ordered to step back
 *  first, then all detailers and drawers are ordered to refresh their data.
 *  
 * @version 0.03, $Date: 2000/12/21 07:12:02 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailgui.SailAway
 * @see com.interworldtransport.sailmodel.OrbitEllipse
 * @see com.interworldtransport.sailmodel
 */
public class ActIterateFEvents implements ActionListener {
    protected SailAway			ParentGUI;
    protected Sail			theSail;
    protected JMenuItem 		ControlIt;
    protected OrbitDetailing		Detailer1;
    protected SailDetailing		Detailer2;
    protected ActEvents 		Parent;
    private	boolean			DEBUG=true;

/** This is the default constructor.
 */
    public ActIterateFEvents(	SailAway pGUI, 
    				JMenuItem ActF, 
				ActEvents pParent) {

	this.ParentGUI=pGUI;
	this.ControlIt=ActF;
	this.ControlIt.addActionListener(this);
	this.Detailer1=ParentGUI.OrbParm;
	this.Detailer2=ParentGUI.SailParm;
	this.theSail=ParentGUI.TestSail;
	this.Parent=pParent;

	}//end of ActIterateFEvents Menu constructor

/** 
 *  This is the actual action to be performed by this member of the Action menu.
 */
    public void actionPerformed(ActionEvent evt) {
	if (DEBUG) {
		System.out.println("-----------------------------");
		System.out.println("Stepping forward once.");
		}
	double pDPhase = 0.0;
	if (theSail!=null) {
		theSail.stepForward(pDPhase);
		}
	else	{
		theSail=ParentGUI.TestSail;
		theSail.stepForward(pDPhase);
		}
	this.setDetailers();
	}//end of action performed method.

/** 
 *  This is an action to be performed when the Orbit Detailing window is closed.
 */
    public void nullDetailer1() {
	this.Detailer1=null;
	}

/** 
 *  This is the action to be performed when the Sail Detailing window is closed.
 */
    public void nullDetailer2() {
	this.Detailer2=null;
	}

/** 
 *  This is the action to be performed when the Detailing windows are created.
 */
    public void setDetailers() {

	if (this.Detailer1!=null) {
		this.Detailer1.setParameters();
		}
	else {
		if (ParentGUI.OrbParm!=null) {
			this.Detailer1=ParentGUI.OrbParm;
			this.Detailer1.setParameters();
			}
		}

	if (this.Detailer2!=null) {
		this.Detailer2.setParameters();
		}
	else {
		if (ParentGUI.SailParm!=null) {
			this.Detailer2=ParentGUI.SailParm;
			this.Detailer2.setParameters();
			}
		}

	}//end of setDetailers method.


    }//end of ActIterateFEvents class
