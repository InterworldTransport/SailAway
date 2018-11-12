/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailgui.AboutDialog------------------------------------
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
---com.interworldtransport.sailgui.AboutDialog------------------------------------
*/

package com.interworldtransport.sailgui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;



/**
 * The SupportDialog is an information window that is called from the 
 * "Help|Support" menu on the main SailAway application window. 
 * It provides information about support for the application.
 */
public class SupportDialog extends JDialog implements ActionListener {


    private JButton closeButton;  // The close button
	
/**
 * The constructor sets up the about dialog box and displays it.
 */	
    public SupportDialog(SailAway mainWindow) {

	super(mainWindow, "Support for SailAway", true); //Use parent's constructor

	JPanel mainPane = new JPanel(new BorderLayout());
	mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(mainPane);

	// Create the content string

	StringBuffer content = new StringBuffer();

	content.append("SailAway Solar Sail Simulator v0.1\n\n");
	content.append("Web Site: http://SailAway.sourceforge.net\n\n");

	content.append("For support issues that would help us make a better simulator please visit ");
	content.append("the project home page.  From this page you should be able to find the Sourceforge ");
	content.append("project site with its associated mailing lists and bulletin boards.  ");
	content.append("Please list your support issues on the appropriate bulletin board and sign up for ");
	content.append("any mailing lists that interest you.\n\n");
	content.append("For complex support or licensing issues, please contact Dr Alfred Differ from the ");
	content.append("message link on the Sourceforge project page or at the address listed for him on the ");
	content.append("Interworld Transport site.\n   http://www.interworldtransport.com\n\n");

	// Create content text area
		
	JTextArea contentArea = new JTextArea(new String(content));
	contentArea.setBackground(Color.lightGray);
	contentArea.setBorder(new EmptyBorder(2, 2, 2, 2));
	contentArea.setLineWrap(true);
	contentArea.setWrapStyleWord(true);
	contentArea.setEditable(false);
	mainPane.add(new JScrollPane(contentArea), "Center");
		
	// Create close button panel
	
	JPanel closeButtonPane = new JPanel(new FlowLayout());
	closeButtonPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	mainPane.add(closeButtonPane, "South");
		
	// Create close button
		
	closeButton = new JButton("Close");
	closeButton.addActionListener(this);
	closeButtonPane.add(closeButton);
		
	// Set the size of the window
		
	setSize(300, 400);
		
	// Center the window on the parent window.
	
	Point parentLocation = mainWindow.getLocation();
	int Xloc = (int) parentLocation.getX() + ((mainWindow.getWidth() - 300) / 2);
	int Yloc = (int) parentLocation.getY();// + ((mainWindow.getHeight() - 400) / 2);
	setLocation(Xloc, Yloc);
		
	// Display window
		
	setVisible(true);
	}
	
	// Implementing ActionListener method
	
    public void actionPerformed(ActionEvent event) {
		dispose();	
	}
    }//End of SupportDialog class
