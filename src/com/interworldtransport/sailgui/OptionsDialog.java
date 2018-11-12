/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailgui.OptionsDialog----------------------------------
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
---com.interworldtransport.sailgui.OptionsDialog----------------------------------
*/

package com.interworldtransport.sailgui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * The OptionsDialog is an interactive dialog that is called from the 
 * "Tools|Options" menu on the main SailAway application window. 
 * It provides configuration information about the application and 
 * collects changes to it for writing a new SailAway.conf file.
 */
public class OptionsDialog extends JDialog implements ActionListener {

    private JButton closeButton;  // The close button
    //private JButton applyButton;  // The apply button
	
/**
 * The constructor sets up the Options dialog box and displays it.
 */	
    public OptionsDialog(SailAway mainWindow) {
	
	super(mainWindow, "SailAway Configuration", true); //Use parent's constructor
	
	JPanel mainPane = new JPanel(new BorderLayout());
	mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(mainPane);
	
	// Create the content string

	StringBuffer content = new StringBuffer();
	
	content.append("SailAway Solar Sail Simulator v0.1\n\n");
	content.append("Web Site: http://SailAway.sourceforge.net\n\n");
	
	content.append("Developers:\n");
	content.append("  Alfred Differ - Physics, Java\n");
	content.append("  Your name could be here! \n\n");
	
	content.append("More will be added to this dialog in the future.\n\n");
	
	content.append("To get a feel for what information will be displayed here ");
	content.append("check out the current SailAway.conf file. ");
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
	int Yloc = (int) parentLocation.getY(); //+ ((mainWindow.getHeight() - 400) / 2);
	setLocation(Xloc, Yloc);
		
	// Display window
		
	setVisible(true);
	}
	
	// Implementing ActionListener method
	
    public void actionPerformed(ActionEvent event) {
		dispose();	
	}
    }//End of AboutDialog class
