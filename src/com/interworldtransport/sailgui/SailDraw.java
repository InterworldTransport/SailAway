/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailgui.SailDraw---------------------------------------
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
---com.interworldtransport.sailgui.SailDraw---------------------------------------
 */

package com.interworldtransport.sailgui;

import com.interworldtransport.sailmodel.*;
import com.interworldtransport.sailevent.*;
import com.interworldtransport.clados.*;
import java.awt.*;
import javax.swing.*;


/** com.interworldtransport.sailgui.SailDraw  Root class of all SailAway graphics panels.
 * Purpose:  This class acts as the root class of all graphics panels attempting to draw
 * representations of a Sail Object.  Members and Methods common to all such panels are defined
 * here.  Members and Methods particular to a representation are defined in subclasses.
 * Expect that the setParameters and paintComponenet methods will get overridden in subclasses.
 *
 * @version 0.03, $Date: 2002/02/14 04:55:03 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailgui.SailAway
 * @see com.interworldtransport.sailmodel.OrbitEllipse
 * @see com.interworldtransport.sailmodel
 * @see com.interworldtransport.clados
 */
public class SailDraw extends JFrame {
    public 	String 			Name;
    public 	Monad 			ViewPlane;
    public 	Monad[]			ToBeViewed;
    public 	int[]			viewGrade;
    protected 	SailAway		ParentGUI;
    protected 	Sail			DemoSail;
    protected 	OrbitEllipse		DemoOrbit;
    protected 	Perturber		DemoPerturber;
    protected 	RadiationForceModel	DemoRFModel;

/**
 * Construct and parent information for the presenter.
 */
    public SailDraw(String pName, SailAway pGUI) {
	super(pName);
	this.Name = pName;
	this.ParentGUI=pGUI;
	this.getContentPane().setLayout(new BorderLayout());
	//this.getContentPane().setOpaque(true);
	this.setBackground(Color.white);	

	//Construct a default viewing plane
	double[] tempC=new double[9];
	for (int j=0; j<9; j++) tempC[j]=0.0;
	tempC[7]=1.0;
	this.ViewPlane=new Monad("ViewPlane", "Helios0", "HeliosCenter", 3, 0, tempC);

	ToBeViewed=new Monad[8];
	viewGrade=new int[8];

	for (int i=0; i<8; i++) {
		ToBeViewed[i]=new Monad("LookAt"+i, "Helios0", "HeliosCenter", 3, 0, tempC);
		viewGrade[i]=2;
		}

	if (this.ParentGUI.TestSail!=null){
		this.setDemoSail(this.ParentGUI.TestSail);
		}
	else {
		System.out.println("No test sail exists to draw parts for yet.");
		}

	}//end of SailDraw Panel constructor

/**
 * All graphical presenters will have a setScene method and override this one.
 */
    public void setScene() {
	;
	}

/**
 * All graphical presenters need to keep track of the Sail they are representing.  
 * Set the Sail here.
 */
    public void setDemoSail(Sail pSail){
	this.DemoSail = pSail;
	this.DemoOrbit = DemoSail.getOrbit();
	this.DemoPerturber = DemoOrbit.getPerturber();
	this.DemoRFModel = DemoPerturber.getRFModel();
	}

/**
 * All graphical presenters will have a repaintScene method and override this one.
 */
    public void repaintScene() {
	//Loops through the monads to be viewed and draw them to the canvas
	
	}

/**
 * All graphical presenters will have an array of viewable monads.  Set a monad
 * to be viewed here by inserting it into the array at location ploc.
 */
    public void setViewable(Monad pLookAt, int ploc) {
	//ploc will eventually be used as an array index for multiple viewables.
	if (ploc<8) {
	    this.ToBeViewed[ploc]=new Monad(pLookAt);
	    }
	else {
	    System.out.println("Can't view that many things in a SailDraw Panel.");
	    }
	}

/**
 * All graphical presenters will have a monad representing the view plane.  
 * Set the view plane monad here.
 */
    public void changeViewPlane(Monad pPlane) throws BadViewPlaneException{
	try {
	    if (pPlane.isGrade()==2) {
		this.ViewPlane=new Monad("Sail View Plane", pPlane);
		this.repaintScene();
		}
	    }
	catch (CladosException e) {
	    throw new BadViewPlaneException(pPlane, "Badly formed viewing plane");
	    }
	}

    }//end of SailDraw class
