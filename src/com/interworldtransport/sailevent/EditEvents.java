/** <pre> com.interworldtransport.sailevent.EditEvents  
 * 1999/08/16
 * Copyright (c) 1999 InterWorld Transport.  All rights reserved.
 * @version 0.02
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailgui.SailAway
 * @see com.interworldtransport.sailmodel.OrbitEllipse
 * @see com.interworldtransport.sailmodel
 *   
 * _______________________________________________________________________________________________
 * Interworld Transport grants you ("Licensee") a license to this software under the terms of the 
 * GNU General Public License.  A full copy of the license can be found bundled with this package.
 * or code file.  
 * If the license file has become separated from this code file, the Licensee is still expected 
 * to read about the license at
 * 			http://www.opensource.org/gpl-license.html
 * before accepting this code file or related executables.  Use of this code or executable objects 
 * derived from it by the Licensee states their willingness to accept the terms of the license.  
 * A prospective Licensee unable to find a copy of the license terms should contact 
 * Interworld Transport for a free copy.
 * _______________________________________________________________________________________________
 *
 * Purpose:
 * 
 * </pre>
 */

package com.interworldtransport.sailevent;

import com.interworldtransport.sailgui.*;
import com.interworldtransport.sailmodel.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class EditEvents implements ActionListener {
    //protected EditSPropsEvents	sp;
   // protected EditOPropsEvents	op;
   // protected EditViewPEvents	vp;

    protected SailAway 		ParentGUI;
    protected Sail		ParentSail;

/** This is the default constructor.  The event structure of the Edit
 *  menu starts here and finishes with the child menu items.
 */
    public EditEvents(SailAway pTheGUI) {
	this.ParentGUI=pTheGUI;
	this.ParentSail=pTheGUI.TestSail;

	//this.sp = new EditSPropsEvents(pTheGUI.mniSailProps, ParentSail, this);
	//this.op = new EditOPropsEvents(pTheGUI.mniOrbitProps, ParentSail, this);
	//this.vp = new EditViewPEvents(pTheGUI.mniViewPoint, ParentSail, this);

	}//end of EditEvents Menu constructor

/** This is the default action to be performed by all members of the Edit menu.
 *  It will be overridden by specific members of the menu.
 */
    public void actionPerformed(ActionEvent evt) {
	;
	}//end of action performed method.


    }//end of EditEvents class
