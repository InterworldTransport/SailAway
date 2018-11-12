/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailgui.OrbitGraphic-----------------------------------
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
---com.interworldtransport.sailgui.OrbitGraphic-----------------------------------
 */

package com.interworldtransport.sailgui;

import com.interworldtransport.sailmodel.*;
import com.interworldtransport.sailevent.*;
//import com.interworldtransport.clados.*;
import java.awt.*;
import javax.swing.*;

/** 
 * This class holds the graphical representation of the Orbit associated with
 * the Sail in the simulation.
 * 
 * @version 0.03, $Date: 2002/02/14 04:55:03 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailgui.SailAway
 * @see com.interworldtransport.sailmodel.OrbitEllipse
 * @see com.interworldtransport.sailmodel
 * @see com.interworldtransport.clados
 */
public class OrbitGraphic extends SailDraw {

/**
 * This image comes from a file used to draw the picture to be used as the 
 * backdrop for the orbit drawer.
 */ 
    protected	Image		img;

/**
 * Construct the Orbit Graphic Panel.
 * Give it a name and the location of the image file to use and it will
 * handle the rest.
 */ 
    public OrbitGraphic(String pName, SailAway pGUI) {
	super(pName, pGUI);
	//The location of the image file should be passed from the properties.

	String OrbitImageFile = ParentGUI.IniProps.getProperty("SailAway.Desktop.OrbitReference");
	this.img = new ImageIcon(OrbitImageFile).getImage();
	setSize(350,250);
	//setVisible(true);
	}//end of OrbitGraphic Panel constructor

/**
 * Set the scene with the Angular momentum as the object to be viewed.
 */ 
    public void setScene() {
	if (DemoOrbit!=null) {
	    this.setViewable(DemoOrbit.getL(), 1);
	    this.repaintScene();
	    }
	}

/**
 * Paint the scene by drawing the backdrop and then adding the viewables.  
 * No viewables are being drawn yet.
 */ 
    public void paint(Graphics g) {
	Graphics2D g2 = (Graphics2D)g;
	g2.drawImage(img, 10, 10, null);
	}	

    }//end of OrbitGraphic class
