/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailgui.SailGraphic------------------------------------
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
---com.interworldtransport.sailgui.SailGraphic------------------------------------
 */

package com.interworldtransport.sailgui;

import com.interworldtransport.sailmodel.*;
import com.interworldtransport.sailevent.*;
//import com.interworldtransport.clados.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/** 
 * This class acts as the root class of all graphics panels attempting to draw
 * representations of a Sail Object.  Members and Methods common to all such panels 
 * are defined here.  Members and Methods particular to a representation are defined 
 * in subclasses.  Expect that the setParameters and paintComponenet methods will 
 * get overridden in subclasses.
 * 
 * @version 0.03, $Date: 2002/02/15 08:55:12 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailgui.SailAway
 * @see com.interworldtransport.sailmodel.OrbitEllipse
 * @see com.interworldtransport.sailmodel
 * @see com.interworldtransport.clados
 */
public class SailGraphic extends SailDraw implements ActionListener{

/**
 * This image comes from a file used to draw the picture to be used as the 
 * backdrop for the sail attitude drawer.
 */ 
	protected	Image		img;
	private		JButton		closeButton;

/**
 * Construct the Sail Graphic Panel.
 * Give it a name and the location of the image file to use and it will
 * handle the rest.
 */ 
    public SailGraphic(String pName, SailAway pGUI) {

	super(pName, pGUI);

	//this.addWindowListener(new WindowAdapter() {
	//	public void WindowClosing(WindowEvent evt) {
	//		ParentGUI.SailDrawing=null;
	//		ParentGUI.mniSailGraphic.setSelected(false);
	//		dispose();
	//		}
	//	}
	//);

	Container cp=this.getContentPane();

	//The location of the image file should be passed from the properties.
	String SailImageFile=ParentGUI.IniProps.getProperty("SailAway.Desktop.SailReference");
	this.img=new ImageIcon(SailImageFile).getImage();

	//JPanel ButtonPane = new JPanel(new FlowLayout());
	//ButtonPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	//cp.add(ButtonPane);

	//closeButton = new JButton("Close");
	//closeButton.addActionListener(this);
	//ButtonPane.add(closeButton);

	//this.pack();
	setSize(150,150);
	//setVisible(true);
	}//end of SailGraphic Panel constructor

/**
 * Set the scene with the Sail Attitude as the object to be viewed.
 */ 
    public void setScene() {
	if (DemoSail!=null) {
	    this.setViewable(DemoSail.getAttitude(), 1);
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

/**
 * All actions performed within this object lead to is disposal for now.
 */ 
    public void actionPerformed(ActionEvent event) {
	//ParentGUI.SailDrawing=null;
	//ParentGUI.mniSailGraphic.setSelected(false);
	//dispose();
	}

    }//end of SailGraphic class
