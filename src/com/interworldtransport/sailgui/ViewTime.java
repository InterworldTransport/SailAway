/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailgui.ViewTime---------------------------------------
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
---com.interworldtransport.sailgui.ViewTime---------------------------------------
 */

package com.interworldtransport.sailgui;

import com.interworldtransport.sailmodel.*;
import com.interworldtransport.sailevent.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
//How many of these imports are really needed?

/**
 * This class is the outward expression of the mission clock on the SailAway GUI.
 * This panel will eventually switch to an active control allowing forward or backward slow
 * and fast iteration.  This panel will eventually ask for the time instead of accepting it.
 * 
 * @version 0.06, 2000/02/15 
 * @author Dr Alfred W Differ
 */
public class ViewTime extends JPanel {

	public	String		Name;
	public	JTextField	stnow;

    public ViewTime(String pName) {
	super(true);
	this.Name = pName;
	this.setOpaque(true);
	this.setBackground(Color.white);
	this.stnow = new JTextField(new Date().toString(), 25);
	this.add(stnow);
	}//end of ViewTime Panel constructor

    public void setNow(String pString) {
	stnow.setText(pString);
	}

    public void setNow() {
	stnow.setText(new Date().toString());
	}

    public String getNow() {
	return stnow.getText();
	}

    }//end of ViewTime class
