/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailgui.SailDetailing--------------------------------
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
---com.interworldtransport.sailgui.SailDetailing----------------------------------
 */

package com.interworldtransport.sailgui;

import com.interworldtransport.sailmodel.*;
import com.interworldtransport.sailevent.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/** 
 * This class holds the detailed numbers concerning the sail and presents them to
 * the User Interface.
 * 
 * @version 0.02, $Date: 2001/11/03 08:43:10 $
 * @author Dr Alfred W Differ
 */
public class SailDetailing extends SailDetail implements ActionListener {
    public	JTextField		rf;
    public	JTextField		rb;
    public	JTextField		sf;
    public	JTextField		sb;
    public	JTextField		ef;
    public	JTextField		eb;
    public	JTextField		lf;
    public	JTextField		lb;
    public	JTextField		temperature;
    public	JTextField		luminosity;
    private 	JButton			closeButton;
    //private	JButton			attachButton;

    public SailDetailing(String pName, SailAway pGUI) {
	super(pName, pGUI);

	this.addWindowListener(new WindowAdapter() {
		public void WindowClosing(WindowEvent evt) {
		    ParentGUI.SailParm=null;
		    ParentGUI.EventModel.ActParts.f.nullDetailer2();
		    ParentGUI.TheMenuBar.mniSailDetailing.setSelected(false);
		    dispose();
		    }
		}
	);

	Container cp=this.getContentPane();
	JLabel rftxt = new JLabel("Rf= ", SwingConstants.RIGHT);
	JLabel rbtxt = new JLabel("Rb= ", SwingConstants.RIGHT);
	JLabel sftxt = new JLabel("Sf= ", SwingConstants.RIGHT);
	JLabel sbtxt = new JLabel("Sb= ", SwingConstants.RIGHT);
	JLabel eftxt = new JLabel("Ef= ", SwingConstants.RIGHT);
	JLabel ebtxt = new JLabel("Eb= ", SwingConstants.RIGHT);
	JLabel lftxt = new JLabel("Lf= ", SwingConstants.RIGHT);
	JLabel lbtxt = new JLabel("Lb= ", SwingConstants.RIGHT);
	JLabel temperaturetxt = new JLabel("T= ", SwingConstants.RIGHT);
	JLabel luminositytxt = new JLabel("W= ", SwingConstants.RIGHT);

	this.rf = new JTextField("0", 15);
	this.rb = new JTextField("0", 15);
	this.sf = new JTextField("0", 15);
	this.sb = new JTextField("0", 15);
	this.ef = new JTextField("0", 15);
	this.eb = new JTextField("0", 15);
	this.lf = new JTextField("0", 15);
	this.lb = new JTextField("0", 15);
	this.temperature = new JTextField("0", 15);
	this.luminosity = new JTextField("0", 15);

	cn.insets = new Insets(2, 2, 2, 2);
	cn.gridx = 0;
	cn.gridy = 0;
	cn.gridwidth = 1;
	cn.gridheight = 1;
	cn.weightx = 0.0;
	cn.weighty = 0.0;
	//cn.fill = GridBagConstraints.BOTH;
	cp.add(rftxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(rf, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(rbtxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(rb, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx = 0.0;
	cp.add(sftxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(sf, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(sbtxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(sb, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx = 0.0;
	cp.add(eftxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(ef, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(ebtxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(eb, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx = 0.0;
	cp.add(lftxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(lf, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(lbtxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(lb, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx = 0.0;
	cp.add(temperaturetxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(temperature, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(luminositytxt, cn);

	cn.gridx++;
	cn.weightx = 0.0;
	cp.add(luminosity, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx = 0.0;
	cn.gridwidth=4;
	cn.gridheight=2;

	JPanel ButtonPane = new JPanel(new FlowLayout());
	ButtonPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	cp.add(ButtonPane, cn);

	closeButton = new JButton("Close");
	closeButton.addActionListener(this);
	ButtonPane.add(closeButton);

	this.setDemoSail(ParentGUI.TestSail);
	this.pack();
	this.setParameters();
	//this.setVisible(true);
	}//end of SailDetailing Panel constructor

    public void setRf(){
	rf.setText(DemoSail.getRf());
	}

    public void setRb(){
	this.rb.setText(DemoSail.getRb());
	}

    public void setSf(){
	this.sf.setText(DemoSail.getSf());
	}

    public void setSb(){
	this.sb.setText(DemoSail.getSb());
	}

    public void setEf(){
	this.ef.setText(DemoSail.getEf());
	}

    public void setEb(){
	this.eb.setText(DemoSail.getEb());
	}

    public void setLf(){
	this.lf.setText(DemoSail.getLf());
	}

    public void setLb(){
	this.lb.setText(DemoSail.getLb());
	}

    public void setTemperature(){
	this.temperature.setText(new Double(this.DemoRadModel.getTemperature()).toString());
	}

    public void setLuminosity(){
	this.luminosity.setText(new Double(this.DemoRadModel.getLuminosity()).toString());
	}

    public void setParameters() {

	if (DemoSail==null) {
		DemoSail=ParentGUI.TestSail;
		}

	this.setRf();
	this.setRb();
	this.setSf();
	this.setSb();
	this.setEf();
	this.setEb();
	this.setLf();
	this.setLb();
	this.setTemperature();
	this.setLuminosity();
	}

    public void actionPerformed(ActionEvent event) {
	ParentGUI.SailParm=null;
	ParentGUI.EventModel.ActParts.f.nullDetailer2();
	ParentGUI.TheMenuBar.mniSailDetailing.setSelected(false);
	dispose();
	}

    }//end of SailDetailing class

