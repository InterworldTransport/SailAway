/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailgui.SailMonad------------------------------------
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
---com.interworldtransport.sailgui.SailMonad------------------------------------
*/

package com.interworldtransport.sailgui;

import com.interworldtransport.sailmodel.*;
import com.interworldtransport.sailevent.*;
import com.interworldtransport.clados.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/** 
 * This class expresses the details about one of the Sail's monads.
 *
 * @version 0.03, $Date: 2001/11/03 08:43:10 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailgui.SailAway
 * @see com.interworldtransport.clados
 */
public class SailMonad 	extends JFrame 
				implements ActionListener {
/**
 * This String is the name of the monad displayer.  This name
 * is used to help identify the monad to be requested when
 * refreshes are needed.
 */
    public	String		Name;
/**
 * A reference to the parent GUI.
 */
    public	SailAway	SailGUI;
/**
 * The MonadOwner is the object to whom ShowPiece belongs.
 * Reqeusts for coefficients are made to this owner.
 */
    public	Object		MonadOwner;
/**
 * The ShowPiece is the Monad to be shown in the frame.
 * Reqeusts for coefficients are made to the owner.
 */
    public	Monad		ShowPiece;
/**
 * Show the e (scalar) coefficient of the monad.
 */
    public	JTextField	spos;	
/**
 * Show the e1 coefficient of the monad.
 */
    public	JTextField	xpos;
/**
 * Show the e2 coefficient of the monad.
 */
    public	JTextField	ypos;
/**
 * Show the e3 coefficient of the monad.
 */
    public	JTextField	zpos;
/**
 * Show the e12 coefficient of the monad.
 */
    public	JTextField	xypos;
/**
 * Show the e13 coefficient of the monad.
 */
    public	JTextField	xzpos;
/**
 * Show the e23 coefficient of the monad.
 */
    public	JTextField	yzpos;
/**
 * Show the e123 coefficient of the monad.
 */
    public	JTextField	xyzpos;
/**
 * Show the Name of the monad.
 */
    public	JTextField	MonadName;
/**
 * Show the Frame Name of the monad.
 */
    public	JTextField	FrameName;
/**
 * Show the Foot Name of the monad.
 */
    public	JTextField	FootName;
/**
 * Graphically display the scalar portion of the monad.
 */
    //public	MonadPanel	DisplayIt;
/**
 * Show the label for the e (scalar) coefficient of the monad.
 */
    private	JLabel		spostxt;
/**
 * Show the label for the e1 coefficient of the monad.
 */
    private	JLabel		xpostxt;
/**
 * Show the label for the e2 coefficient of the monad.
 */
    private	JLabel		ypostxt;
/**
 * Show the label for the e3 coefficient of the monad.
 */
    private	JLabel		zpostxt;
/**
 * Show the label for the e12 coefficient of the monad.
 */
    private	JLabel		xypostxt;
/**
 * Show the label for the e13 coefficient of the monad.
 */
    private	JLabel		xzpostxt;
/**
 * Show the label for the e23 coefficient of the monad.
 */
    private	JLabel		yzpostxt;
/**
 * Show the label for the e123 coefficient of the monad.
 */
    private	JLabel		xyzpostxt;
/**
 * Show the label for the name of the monad.
 */
    private	JLabel		MonadNametxt;
/**
 * Show the label for the Frame Name of the monad.
 */
    private	JLabel		FrameNametxt;
/**
 * Show the label for the Foot Name of the monad.
 */
    private	JLabel		FootNametxt;

    public 	GridBagConstraints	cn;
/**
 * This is the button that closes the SailMonad frame.
 * The one event found within SailMonad is registered with this button.
 */
    private	JButton		closeButton;  // The close button

/**
 * This class helps to display a monad assigned to it in a frame viewable
 * by the application user when and only when they want to see it.
 * The Name of the SailMonad is used to identify which monad from the
 * sail model is to be shown.  The MonadOwner object points to the 
 * actual owner of the monad to be shown as ShowPiece.
 */
    public SailMonad(	String pName, 
			SailAway pGUI,
			Monad pMonad, 
			Object pOwner) {	//main constructor

	super(pName);			//call parent JFrame with titlebar
	this.SailGUI=pGUI;
	this.Name=pName;
	ShowPiece=pMonad;		//set the ShowPiece if given.
	MonadOwner=pOwner;		//set the MonadOwner if given.
	this.constructLayout();		//Layout the frame for user.

	if (MonadOwner != null) {
		this.setMonadDisplay();
		}
	this.pack();
	this.setVisible(true);
	}

/**
 * This method lays out the panel showing the monad to be shown.
 * It does not actually set the values that appear in the text fields
 * for the coefficients or various monads strings.
 */
    public void constructLayout() {

	Container cp = this.getContentPane();
	cp.setLayout(new GridBagLayout());
	cp.setBackground(Color.white);
	this.cn = new GridBagConstraints();
	cn.insets = new Insets (2, 2, 2, 2);

	this.spos = new JTextField("0", 15);
	spos.setHorizontalAlignment(JTextField.RIGHT);
	spos.setFont(new Font("SansSerif", Font.PLAIN, 10));

	this.xpos = new JTextField("0", 15);
	xpos.setHorizontalAlignment(JTextField.RIGHT);
	xpos.setFont(new Font("SansSerif", Font.PLAIN, 10));

	this.ypos = new JTextField("0", 15);
	ypos.setHorizontalAlignment(JTextField.RIGHT);
	ypos.setFont(new Font("SansSerif", Font.PLAIN, 10));

	this.zpos = new JTextField("0", 15);
	zpos.setHorizontalAlignment(JTextField.RIGHT);
	zpos.setFont(new Font("SansSerif", Font.PLAIN, 10));

	this.xypos = new JTextField("0", 15);
	xypos.setHorizontalAlignment(JTextField.RIGHT);
	xypos.setFont(new Font("SansSerif", Font.PLAIN, 10));

	this.xzpos = new JTextField("0", 15);
	xzpos.setHorizontalAlignment(JTextField.RIGHT);
	xzpos.setFont(new Font("SansSerif", Font.PLAIN, 10));

	this.yzpos = new JTextField("0", 15);
	yzpos.setHorizontalAlignment(JTextField.RIGHT);
	yzpos.setFont(new Font("SansSerif", Font.PLAIN, 10));

	this.xyzpos = new JTextField("0", 15);
	xyzpos.setHorizontalAlignment(JTextField.RIGHT);
	xyzpos.setFont(new Font("SansSerif", Font.PLAIN, 10));

	this.MonadName = new JTextField("0", 15);
	MonadName.setHorizontalAlignment(JTextField.RIGHT);
	MonadName.setFont(new Font("SansSerif", Font.PLAIN, 10));

	this.FrameName = new JTextField("0", 15);
	FrameName.setHorizontalAlignment(JTextField.RIGHT);
	FrameName.setFont(new Font("SansSerif", Font.PLAIN, 10));

	this.FootName = new JTextField("0", 15);
	FootName.setHorizontalAlignment(JTextField.RIGHT);
	FootName.setFont(new Font("SansSerif", Font.PLAIN, 10));


	//this.DisplayIt = new MonadPanel();

	spostxt = new JLabel("s= ", SwingConstants.RIGHT);
	spostxt.setHorizontalAlignment(JTextField.RIGHT);
	spostxt.setFont(new Font("SansSerif", Font.PLAIN, 10));

	xpostxt = new JLabel("x= ", SwingConstants.RIGHT);
	xpostxt.setHorizontalAlignment(JTextField.RIGHT);
	xpostxt.setFont(new Font("SansSerif", Font.PLAIN, 10));

	ypostxt = new JLabel("y= ", SwingConstants.RIGHT);
	ypostxt.setHorizontalAlignment(JTextField.RIGHT);
	ypostxt.setFont(new Font("SansSerif", Font.PLAIN, 10));

	zpostxt = new JLabel("z= ", SwingConstants.RIGHT);
	zpostxt.setHorizontalAlignment(JTextField.RIGHT);
	zpostxt.setFont(new Font("SansSerif", Font.PLAIN, 10));

	xypostxt = new JLabel("xy= ", SwingConstants.RIGHT);
	xypostxt.setHorizontalAlignment(JTextField.RIGHT);
	xypostxt.setFont(new Font("SansSerif", Font.PLAIN, 10));

	xzpostxt = new JLabel("xz= ", SwingConstants.RIGHT);
	xzpostxt.setHorizontalAlignment(JTextField.RIGHT);
	xzpostxt.setFont(new Font("SansSerif", Font.PLAIN, 10));

	yzpostxt = new JLabel("yz= ", SwingConstants.RIGHT);
	yzpostxt.setHorizontalAlignment(JTextField.RIGHT);
	yzpostxt.setFont(new Font("SansSerif", Font.PLAIN, 10));

	xyzpostxt = new JLabel("xyz= ", SwingConstants.RIGHT);
	xyzpostxt.setHorizontalAlignment(JTextField.RIGHT);
	xyzpostxt.setFont(new Font("SansSerif", Font.PLAIN, 10));

	MonadNametxt = new JLabel("Name= ", SwingConstants.RIGHT);
	MonadNametxt.setHorizontalAlignment(JTextField.RIGHT);
	MonadNametxt.setFont(new Font("SansSerif", Font.PLAIN, 10));

	FrameNametxt = new JLabel("Frame= ", SwingConstants.RIGHT);
	FrameNametxt.setHorizontalAlignment(JTextField.RIGHT);
	FrameNametxt.setFont(new Font("SansSerif", Font.PLAIN, 10));

	FootNametxt = new JLabel("Foot= ", SwingConstants.RIGHT);
	FootNametxt.setHorizontalAlignment(JTextField.RIGHT);
	FootNametxt.setFont(new Font("SansSerif", Font.PLAIN, 10));

	cn.gridx = 0;
	cn.gridy = 0;
	cn.gridwidth = 1;
	cn.gridheight = 1;
	cn.weightx = 0.0;
	cn.weighty = 0.0;
	cn.fill = GridBagConstraints.HORIZONTAL;
	cp.add(spostxt, cn);

	cn.gridx++;
	cn.weightx = 1.0;
	cp.add(spos, cn);

// 	cn.gridx++;
// 	cn.weightx = 1.0;
// 	cn.weighty = 1.0;
// 	cn.gridheight = 8;
// 	cn.fill = GridBagConstraints.BOTH;
// 	cp.add(DisplayIt, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.gridheight = 1;
	cn.weightx = 0.0;
	cn.fill = GridBagConstraints.HORIZONTAL;
	cp.add(xpostxt, cn);

	cn.gridx++;
	cn.weightx = 1.0;
	cp.add(xpos, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.gridheight = 1;
	cn.weightx = 0.0;
	cp.add(ypostxt, cn);

	cn.gridx++;
	cn.weightx = 1.0;
	cp.add(ypos, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx = 0.0;
	cp.add(zpostxt, cn);

	cn.gridx++;
	cn.weightx = 1.0;
	cp.add(zpos, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx = 0.0;
	cp.add(xypostxt, cn);

	cn.gridx++;
	cn.weightx = 1.0;
	cp.add(xypos, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.gridheight = 1;
	cn.weightx = 0.0;
	cp.add(xzpostxt, cn);

	cn.gridx++;
	cn.weightx = 1.0;
	cp.add(xzpos, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx = 0.0;
	cp.add(yzpostxt, cn);

	cn.gridx++;
	cn.weightx = 1.0;
	cp.add(yzpos, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx = 0.0;
	cp.add(xyzpostxt, cn);

	cn.gridx++;
	cn.weightx = 1.0;
	cp.add(xyzpos, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx = 0.0;
	cp.add(MonadNametxt, cn);

	cn.gridx++;
	cn.weightx = 1.0;
	cp.add(MonadName, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx = 0.0;
	cp.add(FrameNametxt, cn);

	cn.gridx++;
	cn.weightx = 1.0;
	cp.add(FrameName, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx = 0.0;
	cp.add(FootNametxt, cn);

	cn.gridx++;
	cn.weightx = 1.0;
	cp.add(FootName, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx = 0.0;
	cn.fill = GridBagConstraints.HORIZONTAL;
	cn.gridwidth = 3;
	cn.gridheight = 1;

				// Create close button panel
	JPanel ButtonPane = new JPanel(new FlowLayout());
	ButtonPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	ButtonPane.setBackground(Color.white);
	cp.add(ButtonPane, cn);
				// Create close button
	closeButton = new JButton("Close");
	closeButton.addActionListener(this);
	ButtonPane.add(closeButton);
		
	}//end of constructLayout

/**
 * This method is used by the EventModel to set the Owner object for
 * the ShowPiece right after the Frame is instantiated.
 */
    public void setMonadOwner(Object pOwner) {
	MonadOwner=pOwner;
	}

/**
 * This method is used by the EventModel to set the Monad for
 * the ShowPiece and then show it.
 */
    public void setMonadDisplay() {
	if (MonadOwner != null) {
		this.getMonad();
		if (ShowPiece==null) {
			this.spos.setText("null");
			this.xpos.setText("null");
			this.ypos.setText("null");
			this.zpos.setText("null");
			this.xypos.setText("null");
			this.xzpos.setText("null");
			this.yzpos.setText("null");
			this.xyzpos.setText("null");
			this.MonadName.setText("null");
			this.FrameName.setText("null");
			this.FootName.setText("null");
			}
		else {
			double[] tc=ShowPiece.getCoeff();
			this.spos.setText(new Double(tc[1]).toString());
			this.xpos.setText(new Double(tc[2]).toString());
			this.ypos.setText(new Double(tc[3]).toString());
			this.zpos.setText(new Double(tc[4]).toString());
			this.xypos.setText(new Double(tc[5]).toString());
			this.xzpos.setText(new Double(tc[6]).toString());
			this.yzpos.setText(new Double(tc[7]).toString());
			this.xyzpos.setText(new Double(tc[8]).toString());
			this.MonadName.setText(ShowPiece.getMonadName());
			this.FrameName.setText(ShowPiece.getFrameName());
			this.FootName.setText(ShowPiece.getFootName());
			//this.DisplayIt.setCoeffList(ShowPiece);
			}
		}
	else {
		System.out.println("No known owner for "+Name+" so nothing is set.");
		}
	}

/**
 * This method is used by the setMonadDisplay method to actually get
 * the Monad for the ShowPiece.
 */
    private void getMonad() {
	if (MonadOwner instanceof Sail) {
		if (Name.equals("Position")) {
			ShowPiece = ((Sail)MonadOwner).getPosition();
			}
		if (Name.equals("Velocity")) {
			ShowPiece = ((Sail)MonadOwner).getVelocity();
			}
		if (Name.equals("Attitude")) {
			ShowPiece = ((Sail)MonadOwner).getAttitude();
			}
		if (Name.equals("Internal Angular Momentum")) {
			ShowPiece = ((Sail)MonadOwner).getintAngMomentum();
			}
		}
	if (MonadOwner instanceof OrbitEllipse) {
		if (Name.equals("Angular Momentum")) {
			ShowPiece = ((OrbitEllipse)MonadOwner).getL();
			}
		if (Name.equals("Runge Lenz")) {
			ShowPiece = ((OrbitEllipse)MonadOwner).getRungeLenz();
			}
		if (Name.equals("R Hat")) {
			ShowPiece = ((OrbitEllipse)MonadOwner).getRHat();
			}
		if (Name.equals("Node Line")) {
			ShowPiece = ((OrbitEllipse)MonadOwner).getNodeLine();
			}
		if (Name.equals("NodePlus90")) {
			ShowPiece = ((OrbitEllipse)MonadOwner).getNodePlus90();
			}
		if (Name.equals("Latus Rectum")) {
			ShowPiece = ((OrbitEllipse)MonadOwner).getLatusRectum();
			}
		if (Name.equals("ARIES")) {
			ShowPiece = ((OrbitEllipse)MonadOwner).getARIES();
			}
		if (Name.equals("GEMINI")) {
			ShowPiece = ((OrbitEllipse)MonadOwner).getGEMINI();
			}
		if (Name.equals("EQUATOR")) {
			ShowPiece = ((OrbitEllipse)MonadOwner).getEQUATOR();
			}
		}
	if (MonadOwner instanceof Perturber) {
		if (Name.equals("Delta Angular Momentum")) {
			ShowPiece = ((Perturber)MonadOwner).getDL();
			}
		if (Name.equals("Delta Runge Lenz")) {
			ShowPiece = ((Perturber)MonadOwner).getDRL();
			}
		if (Name.equals("Summed Perturbing Force")) {
			ShowPiece = ((Perturber)MonadOwner).getSumForce();
			}
		if (Name.equals("Radiation Pressure Force")) {
			ShowPiece = ((Perturber)MonadOwner).getRadForce();
			}
		if (Name.equals("Gravity Harmonics Force")) {
			ShowPiece = ((Perturber)MonadOwner).getHarmForce();
			}
		if (Name.equals("Gravity N Body Force")) {
			ShowPiece = ((Perturber)MonadOwner).getNBodForce();
			}
		if (Name.equals("Fluid Drag Force")) {
			ShowPiece = ((Perturber)MonadOwner).getFluidForce();
			}
		if (Name.equals("Magnetic Drag Force")) {
			ShowPiece = ((Perturber)MonadOwner).getMagForce();
			}
		if (Name.equals("Impulsive Force")) {
			ShowPiece = ((Perturber)MonadOwner).getImpForce();
			}
		}
	}
/**
 * This method disposes of the frame on any action that makes it here.
 */
    public void actionPerformed(ActionEvent event) {
	//Determine which monad is being displayed from the name.
	String commandpart=null;
	if (Name.equals("Position")) commandpart="Show Sail Position";
	if (Name.equals("Velocity")) commandpart="Show Sail Velocity";
	if (Name.equals("Attitude")) commandpart="Show Sail Attitude";
	if (Name.equals("Internal Angular Momentum")) commandpart="Show Sail Internal L";
	if (Name.equals("Angular Momentum")) commandpart="Show Orbit Angular Momentum";
	if (Name.equals("Runge Lenz")) commandpart="Show Orbit Runge Lenz";
	if (Name.equals("RHat")) commandpart="Show Orbit RHat";
	if (Name.equals("Node Line")) commandpart="Show Orbit Node Line";
	if (Name.equals("NodePlus90")) commandpart="Show Orbit NodePlus90";
	if (Name.equals("Latus Rectum")) commandpart="Show Orbit Latus Rectum";
	if (Name.equals("ARIES")) commandpart="Show Orbit ARIES";
	if (Name.equals("GEMINI")) commandpart="Show Orbit GEMINI";
	if (Name.equals("EQUATOR")) commandpart="Show Orbit EQUATOR";
	if (Name.equals("Delta Angular Momentum")) commandpart="Show Perturber DL";
	if (Name.equals("Delta Runge Lenz")) commandpart="Show Perturber DRL";
	if (Name.equals("Radiation Pressure Force")) commandpart="Show Perturber RadForce";
	if (Name.equals("Gravity Harmonics Force")) commandpart="Show Perturber HarmForce";
	if (Name.equals("Gravity N Body Force")) commandpart="Show Perturber NBodForce";
	if (Name.equals("Fluid Drag Force")) commandpart="Show Perturber FluidForce";
	if (Name.equals("Magnetic Drag Force")) commandpart="Show Perturber MagForce";
	if (Name.equals("Impulsive Force")) commandpart="Show Perturber ImpForce";

	//Tell the SailMonad Event handler to clear this Frame from the 
	//MonadDisplays vector.

	String command="ACTION_PERFORMED,cmd="+commandpart;
	SailGUI.EventModel.ToolParts.sailmonad.foreignActionPerformed(command);

	//Dispose of the display
	dispose();	
	}

    }//end of SailMonad class
