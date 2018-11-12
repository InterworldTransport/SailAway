/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailmodel.SailException--------------------------------
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
---com.interworldtransport.sailmodel.SailException--------------------------------
*/

package com.interworldtransport.sailmodel;

/** com.interworldtransport.sailmodel.SailException
 *  This exception is the parent of all sailmodel exceptions.  It is written to 
 *  be able to distinguish sailmodel exceptions from all other exceptions thrown
 *  by the application.
 *  
 * @version 0.50, 2000/02/07
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailmodel
 * @see com.interworldtransport.clados
 */
public class SailException extends Exception {
    public String	SourceMessage;

    public SailException(String pMessage){
	super();
	this.SourceMessage=pMessage;
	}

    public String	getSourceMessage() {
	return this.SourceMessage;
	}

    } //end of SailException class

