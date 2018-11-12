
/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailgui.BadViewPlaneException--------------------------
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
---com.interworldtransport.sailgui.BadViewPlaneException--------------------------
 */

package com.interworldtransport.sailgui;

import com.interworldtransport.clados.*;

/** 
 * This class is thrown when a graphical presenter gets a ViewPlane that is malformed. 
 * 
 * @version 0.10, 2000/02/15
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailmodel
 * @see com.interworldtransport.clados
 *   
 */
public class BadViewPlaneException extends SailGUIException {
    public Monad	PlaneTried;

    public BadViewPlaneException(Monad pPlane, String pMessage){
	super(pMessage);
	this.PlaneTried=pPlane;
	}

    public Monad	getPlaneTried() {
	return this.PlaneTried;
	}

    } //end of BadViewPlaneException class

