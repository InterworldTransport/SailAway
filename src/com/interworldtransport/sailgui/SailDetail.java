/* 
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailgui.SailDetail-------------------------------------
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
---com.interworldtransport.sailgui.SailDetail-------------------------------------
*/

package com.interworldtransport.sailgui;

import com.interworldtransport.sailmodel.*;
import com.interworldtransport.sailevent.*;
import java.awt.*;
import javax.swing.*;


/** com.interworldtransport.sailgui.SailDetail The parent Panel for all panels that show details
 *  for objects from the physical model. 
 *
 *  The SailDetail is the parent panel for all panels within the user interface that show details
 *  from objects within the physical model.  The SailDetail takes care of features common to these
 *  panels to avoid duplication of code.
 * 
 * @version 0.02, $Date: 2001/11/03 08:43:10 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailgui.SailAway
 * @see com.interworldtransport.sailmodel.Sail
 * @see com.interworldtransport.sailmodel.OrbitEllipse
 * @see com.interworldtransport.sailmodel
 */
abstract	class	SailDetail	extends JFrame {

/**
 * The name of the panel.
 */
    public	String			Name;
/**
 * The name of the parent application.
 */
    public	SailAway		ParentGUI;
/**
 * The Sail to be shown within the panel.
 */
    public	Sail			DemoSail;
/**
 * The OrbitEllipse within the Sail to be shown within the panel.
 */
    public	OrbitEllipse		DemoOrbit;
/**
 * The Perturber within the OrbitEllipse within the Sail to be shown within the panel.
 */
    public	Perturber		DemoPerturber;
/**
 * The RadiationForceModel within the Perturber within the OrbitEllipse within the
 * Sail to be shown within the panel.
 */
    public	RadiationForceModel	DemoRadModel;
/**
 * An object common to all the layout managers for the panels.
 */
    public	GridBagConstraints	cn;

/**
 * This is the 'No Sail Yet' constructor for the panel.
 * It should be used when the SailAway application will set the represented Sail later.
 */
    public	SailDetail(String pName, SailAway pGUI) {

	super(pName);
	this.Name = pName;
	this.ParentGUI=pGUI;
	this.getContentPane().setLayout(new GridBagLayout());
	//this.setOpaque(true);
	this.setBackground(Color.white);
	this.cn = new GridBagConstraints();
	cn.insets = new Insets(2, 2, 2, 2);

	// Center the window on the parent window.
	
	Point parentLocation = this.ParentGUI.getLocation();
	int Xloc = (int) parentLocation.getX(); // + ((this.ParentGUI.getWidth() - 300) / 2);
	int Yloc = (int) parentLocation.getY(); //+ ((mainWindow.getHeight() - 400) / 2);
	setLocation(Xloc, Yloc);

	}//end of SailDetailing Panel constructor

/**
 * This is the 'Sail Known' constructor for the panel.
 * It should be used when the SailAway application is aware of the Sail to be represented
 * at the time of the Panel's creation.
 */
    public 	SailDetail(	String pName,
				SailAway pGUI, 
				Sail pSail) {

	super(pName);
	this.Name = pName;
	this.ParentGUI=pGUI;
	this.setLayout(new GridBagLayout());
	//this.setOpaque(true);
	this.setBackground(Color.white);
	this.cn = new GridBagConstraints();
	cn.insets = new Insets(2, 2, 2, 2);
	this.setDemoSail(pSail);

	// Center the window on the parent window.
	
	Point parentLocation = this.ParentGUI.getLocation();
	int Xloc = (int) parentLocation.getX();// + ((this.ParentGUI.getWidth() - 300) / 2);
	int Yloc = (int) parentLocation.getY();//+ ((ThisParentGUI.getHeight() - 400) / 2);
	setLocation(Xloc, Yloc);

	}//end of SailDetailing Panel constructor

/**
 * This method is called by the SailAway application to set the Sail to be represented.
 * This method will never allow the Panel to alter the Sail or its parts.
 * Details shown by descendants of this panel are to be read-only.
 */
    public	void		setDemoSail(Sail pSail){

	this.DemoSail = pSail;
	this.DemoOrbit = this.DemoSail.getOrbit();
	this.DemoPerturber = this.DemoOrbit.getPerturber();
	this.DemoRadModel = this.DemoPerturber.getRFModel();
	
	}
/**
 * This method is a place holder for descendents to overwrite.
 * It's job is to pull Sail data for display by the panel.
 */
    abstract	void		setParameters() ;

    }//end of SailDetailing class
