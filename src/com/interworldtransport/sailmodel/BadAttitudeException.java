/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailmodel.BadAttitudeException-------------------------
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
---com.interworldtransport.sailmodel.BadAttitudeException-------------------------
*/

package com.interworldtransport.sailmodel;
import com.interworldtransport.clados.*;

/** com.interworldtransport.sailmodel.BadAttitudeException
 *  This exception is thrown whenever an orbit attempts to create or reset its 
 *  attitude monad and fails.  This will happen most often when the attitude
 *  monad fails to be a plane.  All such problems come here.
 *   
 * @version 0.15, $Date: 2000/05/19 00:54:56 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailmodel
 * @see com.interworldtransport.clados
 */
public class BadAttitudeException extends SailException {
    public Sail		SourceSail;
    public Monad	SourceAtt;

    public BadAttitudeException(Sail pSource, String pMessage, Monad pAtt){
	super(pMessage);
	this.SourceSail=pSource;
	this.SourceAtt=new Monad(pAtt);
	}

    public Sail		getSourceSail() {
	return this.SourceSail;
	}

    public Monad	getSourceAtt() {
	return this.SourceAtt;
	}
    } //end of BadAttitudeException class

