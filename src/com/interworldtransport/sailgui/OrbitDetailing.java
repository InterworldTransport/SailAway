/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailgui.OrbitDetailing-------------------------------
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
---com.interworldtransport.sailgui.OrbitDetailing-------------------------------
*/

package com.interworldtransport.sailgui;

import com.interworldtransport.sailmodel.*;
import com.interworldtransport.sailevent.*;
import com.interworldtransport.clados.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/** com.interworldtransport.sailgui.OrbitDetailing
 * This class expresses the details about the Sail's orbit.  Parameters, changes, etc.
 *
 * @version 0.03, $Date: 2001/11/03 08:43:10 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailgui.SailAway
 * @see com.interworldtransport.sailmodel.OrbitEllipse
 * @see com.interworldtransport.sailmodel
 * @see com.interworldtransport.clados
 */
public class OrbitDetailing extends SailDetail implements ActionListener {
    public	JTextField	sma;
    public	JTextField	ecc;
    public	JTextField	inc;
    public	JTextField	nod;
    public	JTextField	per;
    public	JTextField	anm;
    public	JTextField	time;
    public	JTextField	dsma;
    public	JTextField	decc;
    public	JTextField	dinc;
    public	JTextField	dnod;
    public	JTextField	dper;
    public	JTextField	danm;
    public	JTextField	dtime;
    private	JButton		closeButton;  // The close button
    //private	JButton		attachButton;  // The attach button



    public OrbitDetailing(String pName, SailAway pGUI) {
	super(pName, pGUI);

	this.addWindowListener(new WindowAdapter() {
		public void WindowClosing(WindowEvent evt) {
		    ParentGUI.OrbParm=null;
		    ParentGUI.EventModel.ActParts.f.nullDetailer1();
		    ParentGUI.TheMenuBar.mniOrbitDetailing.setSelected(false);
		    dispose();
		    }
		}
	);

	Container cp=this.getContentPane();
	cp.setBackground(Color.white);

	this.sma = new JTextField("0", 15);
	this.ecc = new JTextField("0", 15);
	this.inc = new JTextField("0", 15);
	this.nod = new JTextField("0", 15);
	this.per = new JTextField("0", 15);
	this.anm = new JTextField("0", 15);
	this.time = new JTextField("0", 15);
	this.dsma = new JTextField("0", 15);
	this.decc = new JTextField("0", 15);
	this.dinc = new JTextField("0", 15);
	this.dnod = new JTextField("0", 15);
	this.dper = new JTextField("0", 15);
	this.danm = new JTextField("0", 15);
	this.dtime = new JTextField("0", 15);

	JLabel smatxt = new JLabel("a= ", SwingConstants.RIGHT);
	JLabel ecctxt = new JLabel("e= ", SwingConstants.RIGHT);
	JLabel inctxt = new JLabel("i= ", SwingConstants.RIGHT);
	JLabel nodtxt = new JLabel("N= ", SwingConstants.RIGHT);
	JLabel pertxt = new JLabel("w= ", SwingConstants.RIGHT);
	JLabel anmtxt = new JLabel("v= ", SwingConstants.RIGHT);
	JLabel timetxt = new JLabel("t= ", SwingConstants.RIGHT);
	JLabel dsmatxt = new JLabel("da= ", SwingConstants.RIGHT);
	JLabel decctxt = new JLabel("de= ", SwingConstants.RIGHT);
	JLabel dinctxt = new JLabel("di= ", SwingConstants.RIGHT);
	JLabel dnodtxt = new JLabel("dN= ", SwingConstants.RIGHT);
	JLabel dpertxt = new JLabel("dw= ", SwingConstants.RIGHT);
	JLabel danmtxt = new JLabel("dv= ", SwingConstants.RIGHT);
	JLabel dtimetxt = new JLabel("dt= ", SwingConstants.RIGHT);

	cn.insets = new Insets(2, 2, 2, 2);
	cn.gridx = 0;
	cn.gridy = 0;
	cn.gridwidth = 1;
	cn.gridheight = 1;
	cn.weightx = 0.0;
	cn.weighty = 0.0;
	//cn.fill = GridBagConstraints.BOTH;
	cp.add(smatxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(sma, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(dsmatxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(dsma, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx = 0.0;
	cp.add(ecctxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(ecc, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(decctxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(decc, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx = 0.0;
	cp.add(inctxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(inc, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(dinctxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(dinc, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx = 0.0;
	cp.add(nodtxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(nod, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(dnodtxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(dnod, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx = 0.0;
	cp.add(pertxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(per, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(dpertxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(dper, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx = 0.0;
	cp.add(anmtxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(anm, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(danmtxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(danm, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx = 0.0;
	cp.add(timetxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(time, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(dtimetxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(dtime, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx = 0.0;
	cn.gridwidth = 4;
	cn.gridheight = 2;

				// Create close button panel
	JPanel ButtonPane = new JPanel(new FlowLayout());
	ButtonPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	ButtonPane.setBackground(Color.white);
	cp.add(ButtonPane, cn);
				// Create attach button
	//attachButton = new JButton("Attach");
	//attachButton.addActionListener(this);
	//ButtonPane.add(attachButton);
				// Create close button
	closeButton = new JButton("Close");
	closeButton.addActionListener(this);
	ButtonPane.add(closeButton);
		
	this.setDemoSail(ParentGUI.TestSail);
	this.pack();
	this.setParameters();
	//this.setVisible(true);

	}//end of OrbitDetailing Panel constructor
/**
 * This method reaches out the the orbit within the physical model and gets the
 * SemiMajorAxis in order to change it to text and display it.
 */
    public void setSMA() {
	this.sma.setText(new Double(this.DemoOrbit.SemiMajorAxis).toString());
	}
/**
 * This method reaches out the the perturber within the physical model and gets 
 * the change to the SemiMajorAxis in order to change it to text and display it.
 * It needs to reach out to the Orbit instead, though, because the perturber is
 * no longer maintaining the dparam set.
 */
    public void setdSMA() {
	this.dsma.setText(new Double(this.DemoOrbit.dsma).toString());
	}

    public void setECC() {
	this.ecc.setText(new Double(this.DemoOrbit.Eccentricity).toString());
	}

    public void setdECC() {
	this.decc.setText(new Double(this.DemoOrbit.decc).toString());
	}

    public void setINC() {
	this.inc.setText(new Double(this.DemoOrbit.Inclination).toString());
	}

    public void setdINC() {
	this.dinc.setText(new Double(this.DemoOrbit.dinc).toString());
	}

    public void setNOD() {
	this.nod.setText(new Double(this.DemoOrbit.RAN).toString());
	}

    public void setdNOD() {
	this.dnod.setText(new Double(this.DemoOrbit.dnod).toString());
	}

    public void setPER() {
	this.per.setText(new Double(this.DemoOrbit.PeriAngle).toString());
	}

    public void setdPER() {
	this.dper.setText(new Double(this.DemoOrbit.dper).toString());
	}

    public void setANM() {
	this.anm.setText(new Double(this.DemoOrbit.TrueAnomoly).toString());
	}

    public void setdANM() {
	this.danm.setText(new Double(this.DemoOrbit.danm).toString());
	}

    public void setTIME() {
	this.time.setText(new Double(DemoSail.getMissionClock()).toString());
	}

    public void setdTIME() {
	this.dtime.setText(new Double(DemoOrbit.TimeStep).toString());
	}

    public void setParameters() {

	if (DemoSail==null) {
		DemoSail=ParentGUI.TestSail;
		}

	this.setANM();
	this.setPER();
	this.setNOD();
	this.setINC();
	this.setECC();
	this.setSMA();
	this.setTIME();

	this.setdANM();
	this.setdPER();
	this.setdNOD();
	this.setdINC();
	this.setdECC();
	this.setdSMA();
	this.setdTIME();
	}
	
    public void actionPerformed(ActionEvent event) {
	ParentGUI.OrbParm=null;
	ParentGUI.EventModel.ActParts.f.nullDetailer1();
	ParentGUI.EventModel.ActParts.b.nullDetailer1();
	ParentGUI.TheMenuBar.mniOrbitDetailing.setSelected(false);
	dispose();	
	}

    }//end of OrbitDetailing class
