/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailgui.MonadPanel-------------------------------------
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
---com.interworldtransport.sailgui.MonadPanel-------------------------------------
*/

package com.interworldtransport.sailgui;

import com.interworldtransport.clados.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.*;

/** 
 * This class graphically expresses the details about a monad.
 *
 * @version 0.03, $Date: 2002/02/14 04:55:03 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.clados
 */
public class MonadPanel extends JPanel {

    private	double[]		CoeffList;
    private	double[]		scale;
    private	Ellipse2D.Double	scl;
    private	Line2D.Double		ln;
    private	Rectangle2D.Double	rct;
    private	int			displaywidth=200;
    private	int			displayheight=100;
    private	int			displaydepth=100;

/**
 * This class helps to display the parts of a monad graphically.
 */
    public MonadPanel() {	//main constructor

	super();			//call parent JPanel
	this.setBackground(Color.white);
	this.setForeground(Color.blue);
	this.setPreferredSize(new Dimension(200, 200));
	scl = new Ellipse2D.Double();
	ln = new Line2D.Double();
	rct = new Rectangle2D.Double();
	//vol=???
	}

/**
 * This method takes an array of doubles to be the list of coefficients
 * to be displayed in the panel.
 */
    public void setCoeffList(Monad pM) {
	this.CoeffList=pM.getCoeff();
	this.scale = new double[4];
	scale[0]=2.0*CoeffList[1];
	scale[1]=2.0*Math.sqrt(CoeffList[2]*CoeffList[2]+CoeffList[3]*CoeffList[3]+CoeffList[4]*CoeffList[4]);
	scale[2]=2.0*Math.sqrt(CoeffList[5]*CoeffList[5]+CoeffList[6]*CoeffList[6]+CoeffList[7]*CoeffList[7]);
	scale[3]=2.0*CoeffList[8];
	this.repaint();
	}

/**
 * This method paints the Display panels within this MonadDisplayer.
 */
    public void paintComponent(Graphics g) {
	Graphics2D g2 = (Graphics2D)g;
	g2.drawLine(0, 0, 0, displayheight);
	g2.drawLine(0, 0, displaywidth, 0);
	g2.drawLine(0, 0, displaydepth, displayheight);
	if (CoeffList!=null) {
		scl.setFrame(	-0.5*CoeffList[1]*displaywidth/scale[0],
				-0.5*CoeffList[1]*displayheight/scale[0], 
				0.5*CoeffList[1]*displaywidth/scale[0], 
				0.5*CoeffList[1]*displayheight/scale[0]);
		g2.draw(scl);	//scalar portion is drawn

		ln.setLine(	0.0, 
				0.0, 
				displaywidth*(CoeffList[2]+0.5*CoeffList[4])/scale[1],
				displayheight*(CoeffList[3]+0.5*CoeffList[4])/scale[1]);
		//this line should be skewed to make up for the z coordinate
		g2.draw(ln) ;	//vector portion is drawn

		rct.setRect(	-0.5*displaywidth, -0.5*displayheight, 
				0.5*displaywidth, 0.5*displayheight);

		//This rectangle has the right magnitude but needs to be
		//skewed for the xy and yz components.
		//It also needs to be altered to give a sense of positive	
		//and negative.  A stroke or double sided rectangle might work.
		g2.draw(rct);	//bivector portion is drawn

		//g2.fillMethodHere(*PScalar Part*);

		}
	}

    }//end of MonadPanel class
