/*  
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailevent.FileExitEvents-------------------------------
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
---com.interworldtransport.sailevent.FileExitEvents-------------------------------
*/

package com.interworldtransport.sailevent;

import com.interworldtransport.sailgui.*;
import com.interworldtransport.sailmodel.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** com.interworldtransport.sailevent.FileExitEvents  
 *  This class manages all events relating to the exiting of the applicaiton.
 *  
 * @version 0.02, $Date: 2002/02/15 08:55:12 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailgui.SailAway
 * @see com.interworldtransport.sailmodel
 * @see com.interworldtransport.sailevent
 */
public class FileExitEvents implements ActionListener {
    protected SailAway			ParentGUI;
    protected JMenuItem 		ControlIt;
    protected FileEvents 		Parent;

/** This is the default constructor.
 */
    public FileExitEvents(SailAway pGUI, JMenuItem pmniExit, FileEvents pParent) {
	this.ParentGUI=pGUI;
	this.ControlIt=pmniExit;
	this.ControlIt.addActionListener(this);
	this.Parent=pParent;

	}//end of FileExitEvents Menu constructor

/** This is the actual action to be performed by this member of the File menu.
 */
    public void actionPerformed(ActionEvent evt) {
	if (ParentGUI.TestSail != null) {
		ParentGUI.TestSail.terminateSail();
		System.exit(0);
		}
	else {
		System.exit(0);
		}
	}//end of action performed method.

    }//end of FileExitEvents class
