/* 
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailgui.SailAway------------------------------------
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
---com.interworldtransport.sailgui.SailAway-------------------------------------
 */

package com.interworldtransport.sailgui ;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;

/** com.interworldtransport.sailgui.StatusBar
 * The StatusBar class is intended to be the status bar of the SailAway
 * application.  There is nothing really special about this class.  It can and
 * used to be defined and built within the SailAway application.  For the sake 
 * of maintenance, it has been moved to its own class and file.
 * <p>
 * @version 0.11, $Date: 2001/11/03 09:02:54 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailevent.SailEventModel
 */

 public class StatusBar extends JPanel {
	 
    public	SailAway	TheGUI;
    public	Messaging	StatusMessage;
    public	ViewLabel	StatusView;
    public	ViewTime	StatusTimestamp;
    public	boolean		StatusInUseFlag;
/**
 * The StatusBar class is intended to be the status bar of the SailAway
 * application.  There is nothing really special about this class.  It can and
 * used to be defined and built within the SailAway application.  For the sake 
 * of maintenance, it has been moved to its own class and file.
 */    
    public StatusBar(SailAway pGUI) {
	super();
	if (pGUI!=null) {
		this.TheGUI=pGUI;
		}
	else {
		System.out.println("A GUI must be passed to the StatusLine");
		System.exit(0);
		}
	constructStatusBar();
	}
/**
 * The StatusBar class is mostly constructed in this method.
 */ 
    protected void constructStatusBar() {
	String tStatus="null";
	if (TheGUI.IniProps!=null) {
		tStatus=TheGUI.IniProps.getProperty(
					"SailAway.Desktop.ShowStatusBar");
		}
	if (tStatus.equals("1")) {
		this.StatusInUseFlag=true;
		this.StatusMessage = new Messaging("Status", 
						"Initializing GUI and Sail");
		this.add(StatusMessage);

		this.StatusView = new ViewLabel("First Sail", "Helios");
		this.add(StatusView);

		this.StatusTimestamp = new ViewTime("First Sail");
		this.add(StatusTimestamp);
		}
	else {
		this.StatusInUseFlag=false;
		this.StatusMessage=null;
		this.StatusView=null;
		this.StatusTimestamp=null;
		}
	}

    }
