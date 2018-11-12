/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailgui.SailMoments------------------------------------
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
---com.interworldtransport.sailgui.SailMoments------------------------------------
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
 * This class holds the details concerning the accelerations and torques being
 * experienced by the Sail.
 *  
 * @version 0.04, $Date: 2002/02/15 08:55:12 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailgui.SailAway
 * @see com.interworldtransport.sailmodel.OrbitEllipse
 * @see com.interworldtransport.sailmodel
 * @see com.interworldtransport.clados
 */
public class SailMoments extends SailDetail implements ActionListener{

	protected	JTextField	sfrc;
	protected	JTextField	tfrc;
	protected	JTextField	wfrc;
	private		JButton		closeButton;

    public SailMoments(String pName, SailAway pGUI) {
	super(pName, pGUI);
/*
	this.addWindowListener(new WindowAdapter() {
		public void WindowClosing(WindowEvent evt) {
			ParentGUI.SailForce=null;
			ParentGUI.EventModel.ActParts.f.nullDetailer3();
			ParentGUI.mniSailMoments.setSelected(false);
			dispose();
			}
		}
	);
*/
	Container cp=this.getContentPane();
	JLabel frcname = new JLabel("Direction", SwingConstants.RIGHT);
	JLabel frcdir = new JLabel("Value", SwingConstants.RIGHT);
	JLabel sfrctxt = new JLabel("S= ", SwingConstants.RIGHT);
	JLabel tfrctxt = new JLabel("T= ", SwingConstants.RIGHT);
	JLabel wfrctxt = new JLabel("W= ", SwingConstants.RIGHT);
	this.sfrc = new JTextField("0", 15);
	this.tfrc = new JTextField("0", 15);
	this.wfrc = new JTextField("0", 15);

	//cn.insets = new Insets(2, 2, 2, 2);
	cn.gridx = 0;
	cn.gridy = 0;
	cn.gridwidth = 1;
	cn.gridheight = 1;
	cn.weightx = 0.0;
	cn.weighty = 0.0;
	cp.add(frcname, cn);

	cn.gridx++;
	cn.weightx=1.0;
	cp.add(frcdir, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx=0.0;
	cp.add(sfrctxt, cn);

	cn.gridx++;
	cn.weightx=1.0;
	cp.add(sfrc, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx=0.0;
	cp.add(tfrctxt, cn);

	cn.gridx++;
	cn.weightx=1.0;
	cp.add(tfrc, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.weightx=0.0;
	cp.add(wfrctxt, cn);

	cn.gridx++;
	cn.weightx=1.0;
	cp.add(wfrc, cn);

	cn.gridx=0;
	cn.gridy++;
	cn.gridwidth = 2;
	cn.gridheight = 2;
	cn.weightx=1.0;
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

	}//end of SailMoments Panel constructor

    public void setS(){
	this.sfrc.setText(new Double(this.DemoPerturber.getR()).toString());
	}

    public void setT(){
	this.tfrc.setText(new Double(this.DemoPerturber.getS()).toString());
	}

    public void setW(){
	this.wfrc.setText(new Double(this.DemoPerturber.getW()).toString());
	}

    public String getS(){
	return sfrc.toString();
	}

    public String getT(){
	return tfrc.toString();
	}

    public String getW(){
	return wfrc.toString();
	}

    public void setParameters() {
	if (DemoPerturber==null) {
		DemoPerturber=ParentGUI.TestSail.Orbit.Perturb;
		}
	this.setS();
	this.setT();
	this.setW();
	}

    public void actionPerformed(ActionEvent event) {
	//ParentGUI.SailForce=null;
	//ParentGUI.EventModel.ActParts.f.nullDetailer3();
	//ParentGUI.mniSailMoments.setSelected(false);
	//dispose();
	}

    }//end of SailMoments class
