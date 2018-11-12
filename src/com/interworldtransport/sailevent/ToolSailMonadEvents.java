/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailevent.ToolSailMonadEvents------------------------
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
---com.interworldtransport.sailevent.ToolSailMonadEvents------------------------
*/

package com.interworldtransport.sailevent;

import com.interworldtransport.sailgui.*;
import com.interworldtransport.sailmodel.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/** com.interworldtransport.sailevent.ToolSailMonadEvents  
 * This class groups the actions associated with the Tools|Show Monad menus.
 * 
 * @version 0.02, $Date: 2001/11/03 08:43:10 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailgui.SailAway
 * @see com.interworldtransport.sailmodel.Sail
 * @see com.interworldtransport.sailmodel
 */
public class ToolSailMonadEvents implements ActionListener {

    protected	Vector			ControlIt;
    protected	SailAway		ParentGUI;
    protected	ToolEvents		Parent;

/** This is the default constructor.
 */
    public ToolSailMonadEvents(	SailAway pGUI, 
    				Vector pMonadDisplayers, 
				ToolEvents pParent) {
	this.ParentGUI=pGUI;
	this.Parent=pParent;
	this.ControlIt=pMonadDisplayers;
	JCheckBoxMenuItem tempmenu = null;
	if (ControlIt.size()>0) {
		for (int i=0; i<ControlIt.size(); i++) {
			tempmenu = (JCheckBoxMenuItem) ControlIt.get(i);
			tempmenu.addActionListener(this);
			}
		tempmenu = null;
		}

	}//end of ToolSailMonadEvents Menu constructor

/**
 * This is the default action to be performed by the Tools|Show Sail Monad menus.
 * The appropriate SailMonad frame should be called into being.
 */
    public void actionPerformed(ActionEvent evt) {
	String command = evt.paramString();
	//System.out.println(command);
	//Figure out which menu created the action.
	JCheckBoxMenuItem sourcemenu= (JCheckBoxMenuItem)evt.getSource();
	if (sourcemenu.isSelected()) { 
		//By the time we get here the menu is selected even though the user saw it as unselected
		//Construct and show the frame related to that menu.
		//and add the Frame to the Vector for MonadDisplays.
		if (command.equals("ACTION_PERFORMED,cmd=Show Sail Position")) {
			SailMonad tempViewer=new SailMonad("Position", ParentGUI, null, ParentGUI.TestSail);
			ParentGUI.MonadDisplays.addElement(tempViewer);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Sail Velocity")) {
			SailMonad tempViewer=new SailMonad("Velocity", ParentGUI, null, ParentGUI.TestSail);
			ParentGUI.MonadDisplays.addElement(tempViewer);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Sail Attitude")) {
			SailMonad tempViewer=new SailMonad("Attitude", ParentGUI, null, ParentGUI.TestSail);
			ParentGUI.MonadDisplays.addElement(tempViewer);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Sail Internal L")) {
			SailMonad tempViewer=new SailMonad("Internal Angular Momentum", ParentGUI, null, ParentGUI.TestSail);
			ParentGUI.MonadDisplays.addElement(tempViewer);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Orbit L")) {
			SailMonad tempViewer=new SailMonad("Angular Momentum", ParentGUI, null, ParentGUI.TestSail.getOrbit());
			ParentGUI.MonadDisplays.addElement(tempViewer);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Orbit RungeLenz")) {
			SailMonad tempViewer=new SailMonad("Runge Lenz", ParentGUI, null, ParentGUI.TestSail.getOrbit());
			ParentGUI.MonadDisplays.addElement(tempViewer);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Orbit RHat")) {
			SailMonad tempViewer=new SailMonad("RHat", ParentGUI, null, ParentGUI.TestSail.getOrbit());
			ParentGUI.MonadDisplays.addElement(tempViewer);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Orbit Node Line")) {
			SailMonad tempViewer=new SailMonad("Node Line", ParentGUI, null, ParentGUI.TestSail.getOrbit());
			ParentGUI.MonadDisplays.addElement(tempViewer);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Orbit NodePlus90")) {
			SailMonad tempViewer=new SailMonad("NodePlus90", ParentGUI, null, ParentGUI.TestSail.getOrbit());
			ParentGUI.MonadDisplays.addElement(tempViewer);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Orbit Latus Rectum")) {
			SailMonad tempViewer=new SailMonad("Latus Rectum", ParentGUI, null, ParentGUI.TestSail.getOrbit());
			ParentGUI.MonadDisplays.addElement(tempViewer);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Orbit ARIES")) {
			SailMonad tempViewer=new SailMonad("ARIES", ParentGUI, null, ParentGUI.TestSail.getOrbit());
			ParentGUI.MonadDisplays.addElement(tempViewer);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Orbit GEMINI")) {
			SailMonad tempViewer=new SailMonad("GEMINI", ParentGUI, null, ParentGUI.TestSail.getOrbit());
			ParentGUI.MonadDisplays.addElement(tempViewer);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Orbit EQUATOR")) {
			SailMonad tempViewer=new SailMonad("EQUATOR", ParentGUI, null, ParentGUI.TestSail.getOrbit());
			ParentGUI.MonadDisplays.addElement(tempViewer);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Perturber DL")) {
			SailMonad tempViewer=new SailMonad("Delta Angular Momentum", ParentGUI, null, ParentGUI.TestSail.getOrbit().getPerturber());
			ParentGUI.MonadDisplays.addElement(tempViewer);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Perturber DRL")) {
			SailMonad tempViewer=new SailMonad("Delta Runge Lenz", ParentGUI, null, ParentGUI.TestSail.getOrbit().getPerturber());
			ParentGUI.MonadDisplays.addElement(tempViewer);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Perturber SumForce")) {
			SailMonad tempViewer=new SailMonad("Summed Perturbing Force", ParentGUI, null, ParentGUI.TestSail.getOrbit().getPerturber());
			ParentGUI.MonadDisplays.addElement(tempViewer);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Perturber RadForce")) {
			SailMonad tempViewer=new SailMonad("Radiation Pressure Force", ParentGUI, null, ParentGUI.TestSail.getOrbit().getPerturber());
			ParentGUI.MonadDisplays.addElement(tempViewer);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Perturber HarmForce")) {
			SailMonad tempViewer=new SailMonad("Gravity Harmonics Force", ParentGUI, null, ParentGUI.TestSail.getOrbit().getPerturber());
			ParentGUI.MonadDisplays.addElement(tempViewer);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Perturber NBodForce")) {
			SailMonad tempViewer=new SailMonad("Gravity N Body Force", ParentGUI, null, ParentGUI.TestSail.getOrbit().getPerturber());
			ParentGUI.MonadDisplays.addElement(tempViewer);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Perturber FluidForce")) {
			SailMonad tempViewer=new SailMonad("Fluid Drag Force", ParentGUI, null, ParentGUI.TestSail.getOrbit().getPerturber());
			ParentGUI.MonadDisplays.addElement(tempViewer);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Perturber MagForce")) {
			SailMonad tempViewer=new SailMonad("Magnetic Drag Force", ParentGUI, null, ParentGUI.TestSail.getOrbit().getPerturber());
			ParentGUI.MonadDisplays.addElement(tempViewer);
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Perturber ImpForce")) {
			SailMonad tempViewer=new SailMonad("Impulsive Force", ParentGUI, null, ParentGUI.TestSail.getOrbit().getPerturber());
			ParentGUI.MonadDisplays.addElement(tempViewer);
			}
		}
	else {
		//Determine which action to take from the MonadDisplayer called.
		if (command.equals("ACTION_PERFORMED,cmd=Show Sail Position")) {
			this.disposeOfMonadDisplay("Position");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Sail Velocity")) {
			this.disposeOfMonadDisplay("Velocity");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Sail Attitude")) {
			this.disposeOfMonadDisplay("Attitude");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Sail Internal L")) {
			this.disposeOfMonadDisplay("Internal Angular Momentum");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Orbit L")) {
			this.disposeOfMonadDisplay("Angular Momentum");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Orbit RungeLenz")) {
			this.disposeOfMonadDisplay("Runge Lenz");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Orbit RHat")) {
			this.disposeOfMonadDisplay("RHat");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Orbit Node Line")) {
			this.disposeOfMonadDisplay("Node Line");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Orbit NodePlus90")) {
			this.disposeOfMonadDisplay("NodePlus90");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Orbit Latus Rectum")) {
			this.disposeOfMonadDisplay("Latus Rectum");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Orbit ARIES")) {
			this.disposeOfMonadDisplay("ARIES");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Orbit GEMINI")) {
			this.disposeOfMonadDisplay("GEMINI");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Orbit EQUATOR")) {
			this.disposeOfMonadDisplay("EQUATOR");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Perturber DL")) {
			this.disposeOfMonadDisplay("Delta Angular Momentum");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Perturber DRL")) {
			this.disposeOfMonadDisplay("Delta Runge Lenz");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Perturber SumForce")) {
			this.disposeOfMonadDisplay("Summed Perturbing Force");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Perturber RadForce")) {
			this.disposeOfMonadDisplay("Radiation Pressure Force");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Perturber HarmForce")) {
			this.disposeOfMonadDisplay("Gravity Harmonics Force");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Perturber NBodForce")) {
			this.disposeOfMonadDisplay("Gravity N Body Force");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Perturber FluidForce")) {
			this.disposeOfMonadDisplay("Fluid Drag Force");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Perturber MagForce")) {
			this.disposeOfMonadDisplay("Magnetic Drag Force");
			}
		if (command.equals("ACTION_PERFORMED,cmd=Show Perturber ImpForce")) {
			this.disposeOfMonadDisplay("Impulsive Force");
			}
		}

	}//end of action performed method.
/**
 * This is the default action to be performed by the Tools|Show Sail Monad menus.
 * The appropriate SailMonad frame should be called into being.
 */
    public void foreignActionPerformed(String command) {

	SailMonad tempViewer=null;	//holds the correct Frame once it is found
	String tempName=null;		//holds the name of the Frame while we hunt
	int displayPlace=-1;		//holds the index of the Frame after it is found.

	//Determine which action to take from the MonadDisplayer called.
	if (command.equals("ACTION_PERFORMED,cmd=Show Sail Position")) {
		this.releaseMonadDisplay("Position");
		}
	if (command.equals("ACTION_PERFORMED,cmd=Show Sail Velocity")) {
		this.releaseMonadDisplay("Velocity");
		}
	if (command.equals("ACTION_PERFORMED,cmd=Show Sail Attitude")) {
		this.releaseMonadDisplay("Attitude");
		}
	if (command.equals("ACTION_PERFORMED,cmd=Show Sail Internal L")) {
		this.releaseMonadDisplay("Internal Angular Momentum");
		}
	if (command.equals("ACTION_PERFORMED,cmd=Show Orbit L")) {
		this.releaseMonadDisplay("Angular Momentum");
		}
	if (command.equals("ACTION_PERFORMED,cmd=Show Orbit RungeLenz")) {
		this.releaseMonadDisplay("Runge Lenz");
		}
	if (command.equals("ACTION_PERFORMED,cmd=Show Orbit RHat")) {
		this.releaseMonadDisplay("RHat");
		}
	if (command.equals("ACTION_PERFORMED,cmd=Show Orbit Node Line")) {
		this.releaseMonadDisplay("Node Line");
		}
	if (command.equals("ACTION_PERFORMED,cmd=Show Orbit NodePlus90")) {
		this.releaseMonadDisplay("NodePlus90");
		}
	if (command.equals("ACTION_PERFORMED,cmd=Show Orbit Latus Rectum")) {
		this.releaseMonadDisplay("Latus Rectum");
		}
	if (command.equals("ACTION_PERFORMED,cmd=Show Orbit ARIES")) {
		this.releaseMonadDisplay("ARIES");
		}
	if (command.equals("ACTION_PERFORMED,cmd=Show Orbit GEMINI")) {
		this.releaseMonadDisplay("GEMINI");
		}
	if (command.equals("ACTION_PERFORMED,cmd=Show Orbit EQUATOR")) {
		this.releaseMonadDisplay("EQUATOR");
		}
	if (command.equals("ACTION_PERFORMED,cmd=Show Perturber DL")) {
		this.releaseMonadDisplay("Delta Angular Momentum");
		}
	if (command.equals("ACTION_PERFORMED,cmd=Show Perturber DRL")) {
		this.releaseMonadDisplay("Delta Runge Lenz");
		}
	if (command.equals("ACTION_PERFORMED,cmd=Show Perturber SumForce")) {
		this.releaseMonadDisplay("Summed Perturbing Force");
		}
	if (command.equals("ACTION_PERFORMED,cmd=Show Perturber RadForce")) {
		this.releaseMonadDisplay("Radiation Pressure Force");
		}
	if (command.equals("ACTION_PERFORMED,cmd=Show Perturber HarmForce")) {
		this.releaseMonadDisplay("Gravity Harmonics Force");
		}
	if (command.equals("ACTION_PERFORMED,cmd=Show Perturber NBodForce")) {
		this.releaseMonadDisplay("Gravity N Body Force");
		}
	if (command.equals("ACTION_PERFORMED,cmd=Show Perturber FluidForce")) {
		this.releaseMonadDisplay("Fluid Drag Force");
		}
	if (command.equals("ACTION_PERFORMED,cmd=Show Perturber MagForce")) {
		this.releaseMonadDisplay("Magnetic Drag Force");
		}
	if (command.equals("ACTION_PERFORMED,cmd=Show Perturber ImpForce")) {
		this.releaseMonadDisplay("Impulsive Force");
		}
	}//end of foreignActionPerformed

    private void disposeOfMonadDisplay(String name) {

	SailMonad tempViewer=null;	//holds the correct Frame once it is found
	String tempName=null;		//holds the name of the Frame while we hunt
	int displayPlace=-1;		//holds the index of the Frame after it is found.

	//Determine which action to take from the MonadDisplayer called.
	//Find the SailMonad in the MonadDisplays Vector.
	if (ParentGUI.MonadDisplays.size()==0) return;
	for (int i=0; i<ParentGUI.MonadDisplays.size()+1; i++) {
		tempViewer = (SailMonad) ParentGUI.MonadDisplays.get(i);
		tempName = tempViewer.Name;
		if (tempName.equals(name)) {
			displayPlace=i;
			break;
			//Found it!
			}
		}
	if (displayPlace>=0) {
		//Remove the entry from the vector.

		ParentGUI.MonadDisplays.removeElementAt(displayPlace);

		//Vector already collapses one unit after this removal
		//so their are no holes the next time around.
		//the menu that called here is already reset to 'not Selected'
		//so no action needs to be taken to make it happen.

		if (tempViewer != null) tempViewer.dispose();

		//Disposed of the frame.
		}
	else {
		System.out.println("Tried to remove Position Displayer but could not find it.");
		}
	}//end of disposeOfMonadDisplay method

    private void releaseMonadDisplay(String name) {

	SailMonad tempViewer=null;	//holds the correct Frame once it is found
	String tempName=null;		//holds the name of the Frame while we hunt
	int displayPlace=-1;		//holds the index of the Frame after it is found.

	//Determine which action to take from the MonadDisplayer called.
	//Find the SailMonad in the MonadDisplays Vector.
	if (ParentGUI.MonadDisplays.size()==0) return;
	for (int i=0; i<ParentGUI.MonadDisplays.size()+1; i++) {
		tempViewer = (SailMonad) ParentGUI.MonadDisplays.get(i);
		tempName = tempViewer.Name;
		if (tempName.equals(name)) {
			displayPlace=i;
			break;
			//Found it!
			}
		}
	if (displayPlace>=0) {
		//Remove the entry from the vector.

		ParentGUI.MonadDisplays.removeElementAt(displayPlace);

		//Vector already collapses one unit after this removal
		//so their are no holes the next time around.
		//the menu that called here is already reset to 'not Selected'
		//so no action needs to be taken to make it happen.

		}
	else {
		System.out.println("Tried to remove Position Displayer but could not find it.");
		}
	}//end of disposeOfMonadDisplay method
    }//end of ToolSailMonadEvents class

