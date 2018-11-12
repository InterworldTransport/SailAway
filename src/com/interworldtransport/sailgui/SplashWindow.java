/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailgui.SplashWindow-----------------------------------
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
---com.interworldtransport.sailgui.SplashWindow-----------------------------------
*/

/*
 * Significant parts of this class and others within sailgui are copies with
 * minor alterations of code written by Scott Davis for the Mars Simulation
 * Project.  Information regarding the Mars Simulation Project can be found
 * below.
 *
 * Mars Simulation Project
 * Copyright (C) 1999 Scott Davis
 *
 * home page http://mars-sim.sourceforge.net
 *
 * For questions or comments on this project, contact:
 *
 * Scott Davis
 * 1725 W. Timber Ridge Ln. #6206
 * Oak Creek, WI  53154
 * scud1@execpc.com
 * http://www.execpc.com/~scud1/
 */

package com.interworldtransport.sailgui;

import java.awt.*;
import javax.swing.*;

/** 
 * The SplashWindow class is a splash screen shown when the application is loading.
 */
public class SplashWindow extends JWindow {

/** 
 * The default constructor may not be used for this class.
 */
    private SplashWindow() {
	}

/** 
 * The constructor loads an image and displays the splash window. 
 * The image must be in the ImageIcon passed to the constructor at present.
 */
    public SplashWindow(ImageIcon pSplashIcon) {
	setVisible(false);
	setBackground(Color.black);
	//ImageIcon splashIcon = new ImageIcon("SplashImage.jpg");
	JLabel splashLabel = new JLabel(pSplashIcon);
	getContentPane().add(splashLabel);
	pack();
	getRootPane().setDoubleBuffered(true);
	
	// Center the splash window on the screen.
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension windowSize = getSize();
	setLocation(((screenSize.width - windowSize.width) / 2), ((screenSize.height - windowSize.height) / 2));
	
	// Display the splash window.
	setVisible(true);
	}
    }//end of SplashWindow class
