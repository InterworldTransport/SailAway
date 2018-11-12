/*
<h2>Copyright</h2>
Copyright (c) 1998-20001Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailevent.ToolSailImageEvents------------------------
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
---com.interworldtransport.sailevent.ToolSailImageEvents------------------------
*/

package com.interworldtransport.sailevent;

import com.interworldtransport.sailgui.*;
import com.interworldtransport.sailmodel.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/** com.interworldtransport.sailevent.ToolOrbitGraphicEvents  
 * This class groups the actions associated with the Tools|Show Orbit Graphic
 * menu.
 * 
 * @version 0.10, $Date: 2001/11/03 09:02:54 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailgui.SailAway
 * @see com.interworldtransport.sailmodel.OrbitEllipse
 * @see com.interworldtransport.sailmodel
 */
public class ToolSailImageEvents 
					implements ActionListener {

    //protected	JCheckBoxMenuItem	ControlIt;
/**
 * This is a vector of menu items to be handled via this event handling class.
 */
    public	Vector			ControlIt;
/**
 * This is a reference to the GUI to which this part of the event model is
 * attached.
 */
    protected	SailAway		ParentGUI;
/**
 * This is a reference to the parent menu within the event model.
 */
    protected	ToolEvents		Parent;

/** 
 * This is the default constructor.
 */
    public ToolSailImageEvents(	SailAway pGUI, 
    				//JCheckBoxMenuItem pOrbitShower,
				Vector pGraphicDisplayers,
				ToolEvents pParent) {
	//Switch the JCheckBoxMenuItem to the vector of Graphic Displays when we
	//are ready to make the switch.  the ControlIt data member will have to
	// be switched too
	this.ParentGUI=pGUI;
	this.Parent=pParent;
	this.ControlIt=pGraphicDisplayers;
	//this.ControlIt.addActionListener(this);
	JCheckBoxMenuItem tempmenu = null;
	if (ControlIt.size()>0) {
		for (int i=0; i<ControlIt.size(); i++) {
			tempmenu = (JCheckBoxMenuItem) ControlIt.get(i);
			tempmenu.addActionListener(this);
			}
		tempmenu = null;
		}
	else {
		System.out.println("No menu was available for controlling");
		}
		
	}//end of ToolOrbitGraphicEvents Menu constructor

/**
 * This is the default action to be performed by the Tools|Show Orbit Graphic 
 * menu.  This is the new version of the method after it was converted to 
 * support a vector of displayers.
 */
    public void actionPerformed(ActionEvent evt) {
	    
	String command = evt.paramString();
	//System.out.println("Command issued: "+command);
	//Figure out which menu created the action.
	JCheckBoxMenuItem sourcemenu= (JCheckBoxMenuItem)evt.getSource();
	if (sourcemenu.isSelected()) { 
		//By the time we get here the menu is selected even though the 
		//user saw it as unselected.  Construct and show the frame 
		//related to that menu.  Add the Frame to the Vector for 
		//GraphicDisplays.
		if (command.equals("ACTION_PERFORMED,cmd=Show Sail")) {
			SailDraw3D tempViewer=new SailDraw3D(
						"Sail", 
						ParentGUI);
			//tempViewer.setGraphicOwner(ParentGUI.TestSail);
			ParentGUI.GraphicDisplays.addElement(tempViewer);
			tempViewer.setVisible(true);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Orbit")) {
			OrbitDraw3D tempViewer=new OrbitDraw3D(
						"Orbit", 
						ParentGUI);
			//tempViewer.setGraphicOwner(ParentGUI.TestSail);
			ParentGUI.GraphicDisplays.addElement(tempViewer);
			tempViewer.setVisible(true);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Perturber")) {
			SailDraw3D tempViewer=new SailDraw3D(
						"Perturber", 
						ParentGUI);
			//tempViewer.setGraphicOwner(ParentGUI.TestSail);
			ParentGUI.GraphicDisplays.addElement(tempViewer);
			tempViewer.setVisible(true);
			}
		}
	else {
		//Determine which action to take from the GraphicDisplayer called.
		if (command.equals("ACTION_PERFORMED,cmd=Show Sail")) {
			this.disposeOfGraphicDisplay("Sail");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Orbit")) {
			this.disposeOfGraphicDisplay("Orbit");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Perturber")) {
			this.disposeOfGraphicDisplay("Perturber");
			}
		}

	}//end of action performed method.

    private void disposeOfGraphicDisplay(String name) {

	GraphicDisplayer3D tempViewer=null;
	
	//holds the correct Frame once it is found
	String tempName=null;		
	//holds the name of the Frame while we hunt
	int displayPlace=-1;		
	//holds the index of the Frame after it is found.

	//Determine which action to take from the GraphicDisplayer called.
	//Find the graphic window in the GraphicDisplays Vector.
	if (ParentGUI.GraphicDisplays.size()==0) {
		System.out.println("No Graphics are being displayed.");
		return;
		}
	else {
	    for (int i=0; i<ParentGUI.GraphicDisplays.size()+1; i++) {
		tempViewer = (GraphicDisplayer3D) ParentGUI.GraphicDisplays.get(i);
		tempName = tempViewer.Name;
		if (tempName.equals(name)) {
			displayPlace=i;
			break;
			//Found it!
			}
		    }
    		}
	if (displayPlace>=0) {
		//Remove the entry from the vector.

		ParentGUI.GraphicDisplays.removeElementAt(displayPlace);

		//Vector already collapses one unit after this removal
		//so their are no holes the next time around.
		//The menu that called here is already reset to 'not Selected'
		//so no action needs to be taken to make it happen.

		if (tempViewer != null) tempViewer.dispose();

		//Disposed of the frame.
		}
	else {
		System.out.println(
		    "Tried to remove Graphic Displayer but couldn't.");
		System.out.println("Try: "+name);
		}
	}//end of disposeOfMonadDisplay method

    }//end of ToolSailImageEvents class
