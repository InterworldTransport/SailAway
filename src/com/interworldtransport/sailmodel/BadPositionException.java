/*
<h2>Copyright</h2>
Copyright (c) 1998-2000 Interworld Transport.  All rights reserved.<br>
---com.interworldtransport.sailmodel.BadPositionException-------------------------
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
---com.interworldtransport.sailmodel.BadPositionException-------------------------
*/

package com.interworldtransport.sailmodel;
import com.interworldtransport.clados.*;

/** com.interworldtransport.sailmodel.BadPositionException
 *  This exception is thrown whenever a valid orbit attempts to iterate to a new
 *  position and fails.  This will happen most often when the next position is not
 *  on the angular momentum plane.  All such problems come here.
 *  The exception is also thrown whenever an orbit attempts to set RHat and the
 *  settor method complains. Such complaints will lead to failures later with the
 *  iterator, so the same exception applies.
 *   
 * @version 0.15, $Date: 2000/05/19 00:54:56 $
 * @author Dr Alfred W Differ
 * @see com.interworldtransport.sailmodel
 * @see com.interworldtransport.clados
 */
public class BadPositionException extends SailException {
    public OrbitEllipse	SourceOrbit;
    public Monad	SourcePosition;

    public BadPositionException(OrbitEllipse pSource, String pMessage, Monad pPosition){
	super(pMessage);
	this.SourceOrbit=pSource;
	this.SourcePosition=new Monad(pPosition);
	}

    public OrbitEllipse	getSourceOrbit() {
	return this.SourceOrbit;
	}

    public Monad	getSourcePosition() {
	return this.SourcePosition;
	}
    } //end of BadPositionException class

