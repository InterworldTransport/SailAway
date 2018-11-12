/*
 * <h2>Copyright</h2>
 * Copyright (c) 1998-2001 Interworld Transport.  All rights reserved.<br>
 * ---com.interworldtransport.sailgui.OrbitDraw3D----------------------------------
 * <p>
 * Interworld Transport grants you ("Licensee") a license to this software
 * under the terms of the GNU General Public License.<br>
 * A full copy of the license can be found bundled with this package or code file.
 * <p>
 * If the license file has become separated from the package, code file, or binary
 * executable, the Licensee is still expected to read about the license at the
 * following URL before a-ccepting this material.
 * <blockquote><code>http://www.opensource.org/gpl-license.html</code></blockquote>
 * <p>
 * Use of this code or executable objects derived from it by the Licensee states
 * their willingness to accept the terms of the license.
 * <p>
 * A prospective Licensee unable to find a copy of the license terms should contact
 * Interworld Transport for a free copy.
 * <p>
 * ---com.interworldtransport.sailgui.OrbitDraw3D *---------------------------------
 */

package com.interworldtransport.sailgui;

import com.interworldtransport.sailmodel.*;
import com.interworldtransport.sailevent.*;
import com.interworldtransport.clados.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.*;
import com.sun.j3d.utils.behaviors.mouse.*;
import javax.media.j3d.*;
import javax.vecmath.*;

/**
 *  com.interworldtransport.sailgui.OrbitDraw3D Root class of all SailAway graphics3D
 *  Orbit panels. Purpose: This class acts as the root class of all graphics3D Orbit 
 *  panels that draw representations of an Orbit Object. Members and Methods common to
 *  all such panels are defined here. Members and Methods particular to a representation
 *  are defined in subclasses.
 *
 * @author     Dr Alfred W Differ
 * @version    0.1, $Date: 2001/11/04 09:46:59 $
 * @see        com.interworldtransport.sailgui.SailAway
 * @see        com.interworldtransport.sailmodel.OrbitEllipse
 * @see        com.interworldtransport.sailmodel
 * @see        com.interworldtransport.clados
 */
public class OrbitDraw3D extends GraphicDisplayer3D {

/**
 *  This data member holds coordinates for the orbit ellipse to be
 *  drawn for L
 * @since SailAway 0.1.0
 */
    private float[] ellipsequad;
    //private float[] ellipsecolorquad;
/**
 *  This data member holds coordinates for the RA of the Ascending Node drawn
 *  for L
 * @since SailAway 0.1.0
 */
    private float[] RANLine;
/**
 *  This data member holds coordinates for Periapsis drawn for L.
 * @since SailAway 0.1.0
 */
    private float[] PeriLine;
/**
 *  This data member holds coordinates for the ecliptic to be drawn for L
 * @since SailAway 0.1.0
 */
    private float[] eclipticquad;
    //private float[] eclipticcolorquad;

/**
 *  This field describes how many strips make up the ellipse to be drawn
 * @since SailAway 0.1.0
 */
    private final static int[] 	perstrip = {360};
    private final static int[] 	perstrip2 = {2};
/**
 *  This data member holds a definition of the orbit plane as a set of line
 *  strips
 * @since SailAway 0.1.0
 */
    private	LineStripArray	orbitplane;
/**
 *  This data member holds a definition of the ecliptic plane as a set of line
 *  strips
 * @since SailAway 0.1.0
 
 */
    private	LineStripArray	eclipticplane;
/**
 *  This data member holds a definition of the orbit RAAN as a set of line
 *  strips
 * @since SailAway 0.1.0
 */
    private	LineStripArray	orbitRAN;
/**
 *  This data member holds a definition of the orbit Periapsis as a set of
 *  line strips
 * @since SailAway 0.1.0
 */
    private	LineStripArray	orbitPeri;
    private	double		sma, ecc, inc, node, peri;

/**
 *  Construct and parent information for the presenter.
 *
 * @param  pName  Name the graphical frame so it may be found on a Vector
 * @param  pGUI   Name the GUI that owns this frame
 * @since SailAway 0.1.0
 */
	public OrbitDraw3D( String pName, SailAway pGUI ) {
		super( pName, pGUI );
		
		this.ShowPiece = new Vector();
		ShowPiece.addElement( ParentGUI.TestSail.getOrbit());
		ShowPiece.addElement( ParentGUI.TestSail);
		
		ellipsequad = new float[1080];
		//ellipsecolorquad = new float[1080];
		eclipticquad = new float[1080];
		//eclipticcolorquad = new float[1080];
		RANLine = new float[6];
		PeriLine = new float[6];
		//RungeLenzquad = new float[4];
		
		GraphicsConfiguration config =
				SimpleUniverse.getPreferredConfiguration();
		
		Canvas3D c = new Canvas3D( config );
		cp.add( "Center", c );
		
	// Call for the references to all the items that show in the scene.
		this.setGraphicOwner( pGUI.TestSail );
		
	// Create the scene and attach it to the virtual universe
		BranchGroup scene = createSceneGraph();
		
		SimpleUniverse u = new SimpleUniverse( c );
		
		u.getViewingPlatform().setNominalViewingTransform();
		u.addBranchGraph( scene );
		//u.addBranchGraph( scene2 );
		
	}

/**
 *  This method lays out the panel showing the graphic to be shown. It does
 *  not actually set the values that appear in any fields.
 *
 * @return    A BranchGroup to be included in the rendered impage.
 * @since SailAway 0.1.0
 */
	public BranchGroup createSceneGraph() {
	// Set up the scene to be used to show the contents of the ShowPiece
	// array.  Make sure the scene makes it clear which object is the
	// Graphic Owner.

	// Create the root of the branch graph
		BranchGroup fooRoot = new BranchGroup();

	// Create the transform groups to hold various parts of the Branch.
		TransformGroup tranParent = new TransformGroup();
		Transform3D t3parent = new Transform3D();
		tranParent.setTransform( t3parent );
		fooRoot.addChild( tranParent );
	// Create the TransformGroup node and initialize it to the
	// identity. Enable the TRANSFORM_WRITE capability so that
	// our behavior code can modify it at run time. Add it to
	// the root of the subgraph.
		TransformGroup tranAlter = new TransformGroup();
		tranAlter.setCapability( TransformGroup.ALLOW_TRANSFORM_READ );
		tranAlter.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );
		tranParent.addChild( tranAlter );	
		
		BoundingSphere bounds =
		    new BoundingSphere( new Point3d( 0.0, 0.0, 0.0 ), 100.0 );
		
	// Create a new Behavior object that will perform the desired operation on
	//the specified transform and add it into the scene graph.
	// Create the rotate behavior node
		MouseRotate behavior = new MouseRotate( tranAlter );
		tranAlter.addChild( behavior );
		behavior.setSchedulingBounds( bounds );
	// Create the zoom behavior node
		MouseZoom behavior2 = new MouseZoom( tranAlter );
		tranAlter.addChild( behavior2 );
		behavior2.setSchedulingBounds( bounds );
	// Create the translate behavior node
		MouseTranslate behavior3 = new MouseTranslate( tranAlter );
		tranAlter.addChild( behavior3 );
		behavior3.setSchedulingBounds( bounds );

	
	
		TransformGroup tranOrbitFoo = new TransformGroup();
		Transform3D t3orbit = new Transform3D();
		t3orbit.setScale( 0.5 );
		//Hold off adding the t3orbit transform to the Group yet because it
		// is not complete without the orbit information.
		//When it is ready, add it to transAlter.
		
		TransformGroup tranRefFoo = new TransformGroup();
		Transform3D t3ref = new Transform3D();
		t3ref.setScale( 0.5 );
		//When it is ready, add it to transAlter.
		
	// Ready to construct an ellipse now.
		this.orbitplane = new LineStripArray( 360,
		    LineStripArray.COORDINATES,
		    perstrip );
		    
		OrbitEllipse orbit = (OrbitEllipse) ShowPiece.get(0);
		
		this.sma = 1.0;
		this.ecc = orbit.Eccentricity;
		this.inc = orbit.Inclination;
		this.node = orbit.RAN;
		this.peri = orbit.PeriAngle;
		
		this.draftOrbitEllipse();
		
		this.orbitRAN = new LineStripArray (2,
		    LineStripArray.COORDINATES,
		    perstrip2 );
		this.draftOrbitRAN();
		
		this.orbitPeri = new LineStripArray (2,
		    LineStripArray.COORDINATES,
		    perstrip2 );
		this.draftOrbitPeri();
		
	// Create a Transform3D node and initialize it to the identity.
	// Then Rotate the Transform3D to show the nodal line of the orbit.
	// Then Rotate the Transform3D to show the inclination of hte oribt.
	// Then Rotate the Transform3D to show the periapsis of the orbit.
	// This Transform3D is to be applied on the end of the earlier one
	// that set the scale for the whole TransformGroup.
		Transform3D tperi = new Transform3D();
		tperi.rotZ(peri);
		
		Transform3D tinc = new Transform3D();
		tinc.rotY(inc);
		
		Transform3D t3L = new Transform3D();
		t3L.rotZ(node);
		t3L.mul(tinc);
		t3L.mul(tperi);
		
		t3orbit.mul(t3L);
		
	//Now we are ready to add the t3orbit transform to the TransformGroup
	// and attach the orbit to it.
		tranOrbitFoo.setTransform( t3orbit );
		tranAlter.addChild( tranOrbitFoo );
	//Now add the orbit plane to the scene by attaching it to the
	// TransformGroup that allows all our behviors and alterations.
		Shape3D OrbitShape = new Shape3D( orbitplane );
		Shape3D OrbitShapeRAN = new Shape3D( orbitRAN );
		Shape3D OrbitShapePeri = new Shape3D( orbitPeri );
		tranOrbitFoo.addChild( OrbitShape );
		tranOrbitFoo.addChild( OrbitShapeRAN );
		tranOrbitFoo.addChild( OrbitShapePeri );
		//Shape3D OrbitRAN = new Shape3D( orbitRAN);
		//transOrbitFoo.addChild( orbitRAN );
		//Shape3D OrbitPeri = new Shape3D( orbitPeri);
		//transOrbitFoo.addChild( orbitPeri );
		
	// Ready to construct reference frame now.
		this.eclipticplane = new LineStripArray( 360,
		    LineStripArray.COORDINATES,
		    perstrip );
		this.draftEcliptic();
		
	//Now add the reference frame to the scene by attaching it to the
	// TransformGroup that allows our basic behaviors.
		Shape3D ReferenceFrame = new Shape3D( eclipticplane );
		tranRefFoo.addChild( ReferenceFrame );
		tranRefFoo.setTransform( t3ref );
		tranAlter.addChild( tranRefFoo );
	
	
	
	
	//Shine it with a light.
		//AmbientLight lgt1 = new AmbientLight(new Color3f(0.0f, 1.0f, 1.0f));
		//Color3f lColor2 = new Color3f( 1.0f, 1.0f, 1.0f );
		//Vector3f lDir2 = new Vector3f( 0.0f, 0.0f, -1.0f );
		//DirectionalLight lgt2 = new DirectionalLight( lColor2, lDir2 );
		//lgt1.setInfluencingBounds( bounds );
		//lgt2.setInfluencingBounds( bounds );
		//tranOrbitFoo.addChild( lgt1 );
		//tranOrbitFoo.addChild( lgt2 );

	
	// Have Java 3D perform optimizations on the root scene graph and call
	// it done.
		fooRoot.compile();
		return fooRoot;
	}
	
/**
 * The purpose of this method is to build the orbit ellipse that matches
 * the angular momentum of the sail's orbit.
 * @since SailAway 0.1.0
 */
	public	void	draftOrbitEllipse() {
				
		float conv = (float) Math.PI/180.0f;
				
		for (int in=0; in < 1080; in=in+3) {
		    
		    float temp = (float) (sma*(1.0f-ecc*ecc)/
		    			(1+ecc*Math.cos((in)*conv)));
					
		    this.ellipsequad[in] = (float) (Math.cos(in*conv)*temp);
		    this.ellipsequad[in+1] = (float) (Math.sin(in*conv)*temp);
		    this.ellipsequad[in+2] = 0.0f;
		    
		    //this.ellipsecolorquad[in] = 1.0f;
		    //this.ellipsecolorquad[in+1] = 0.0f;
		    //this.ellipsecolorquad[in+2] = 1.0f;
		    }
		    
		orbitplane.setCoordinates( 0, ellipsequad );
		//orbitplane.setColor( 0, ellipsecolorquad );
		}
/**
* The purpose of this method is to draw the line showing the direction of the
* ascending node of the orbit drated from the angular momentum.
* @since SailAway 0.1.0
*/
	public	void		draftOrbitRAN() {
		float conv = (float) Math.PI/180.0f;
		this.RANLine[0] = 0.0f;
		this.RANLine[1] = 0.0f;
		this.RANLine[2] = 0.0f;
		this.RANLine[3] = (float) (sma * Math.cos( node * conv ));
		this.RANLine[4] = (float) (sma * Math.sin( node * conv ));
		this.RANLine[5] = 0.0f;
		
		orbitRAN.setCoordinates( 0, RANLine );
		}

/**
* The purpose of this method is to draw the line showing the direction of the
* periapsis of the orbit drated from the angular momentum.
* @since SailAway 0.1.0
*/
	public	void		draftOrbitPeri() {
		this.PeriLine[0] = 0.0f;
		this.PeriLine[1] = 0.0f;
		this.PeriLine[2] = 0.0f;
		this.PeriLine[3] = (float) sma;
		this.PeriLine[4] = 0.0f;
		this.PeriLine[5] = 0.0f;
		
		orbitPeri.setCoordinates( 0, PeriLine );
		}

/**
 * The purpose of this method is to build the orbit ellipse that matches
 * the angular momentum of the sail's orbit.
 * @since SailAway 0.1.0
 */
	public	void	draftEcliptic() {
				
		float conv = (float) Math.PI/180.0f;
		
		for (int in=0; in < 1080; in=in+3) {
		    
		    this.eclipticquad[in] = (float) (Math.cos(in*conv)*sma);
		    this.eclipticquad[in+1] = (float) (Math.sin(in*conv)*sma);
		    this.eclipticquad[in+2] = 0.0f;
		    
		    //this.eclipticcolorquad[in] = 1.0f;
		    //this.eclipticcolorquad[in+1] = 1.0f;
		    //this.eclipticcolorquad[in+2] = 1.0f;
		    
		    }
		eclipticplane.setCoordinates( 0, eclipticquad );
		//eclipticplane.setColor( 0, eclipticcolorquad );
		
		}

/**
 *  This method is used by the EventModel to set the Owner object for the ShowPiece
 *  right after the Frame is instantiated.
 *
 * @param  pOwner  The new GraphicOwner value
 * @since SailAway 0.1.0
 */
	public void setGraphicOwner( Object pOwner ) {
		GraphicOwner = pOwner;
		//System.out.println("Graphic Owner is; "+pOwner.toString());
		this.getGraphic();
	}


/**
 *  This method is used by the EventModel to reset the Graphic for the ShowPiece
 *  and then show it.
 *
 * @since SailAway 0.1.0
 */
	public void setGraphicDisplay() {
		if ( GraphicOwner != null ) {
			this.getGraphic();
			if ( ShowPiece.size() == 0 ) {
				// Alter the contents of the scene to show there is
				// nothing to show.
			}
			else {
				// Alter the contents of the scene to show there is
				// something to show.
			}
		}
		else {
			System.out.println(
					"No known owner for " + Name + " so nothing is set." );
		}
	}

	//end of the settor for the Graphic Display
/**
 *  This method is used by the setGraphicDisplay method to actually get the
 *  Graphics for the ShowPiece.
 *
 * @since 0.1
 */
	private void getGraphic() {
		if ( GraphicOwner instanceof Sail ) {
			if ( Name.equals( "Sail" ) ) {
				ShowPiece.addElement( ( ( Sail ) GraphicOwner ).getAttitude() );
				ShowPiece.addElement( ( ( Sail ) GraphicOwner ).getintAngMomentum() );
				ShowPiece.addElement( ( ( Sail ) GraphicOwner ).getRFModel().getSailNormal() );
				ShowPiece.addElement( ( ( Sail ) GraphicOwner ).getRFModel().getSailReflect() );
				ShowPiece.addElement( ( ( Sail ) GraphicOwner ).getRFModel().getSailSunLine() );
				//ShowPiece.addElement(((Sail)GraphicOwner).getintlStructure());
			}
			if ( Name.equals( "Orbit" ) ) {
				ShowPiece.addElement( ( ( Sail ) GraphicOwner ).getOrbit().getRHat() );
				ShowPiece.addElement( ( ( Sail ) GraphicOwner ).getOrbit().getL() );
				ShowPiece.addElement( ( ( Sail ) GraphicOwner ).getOrbit().getRungeLenz() );
				ShowPiece.addElement( ( ( Sail ) GraphicOwner ).getOrbit().getARIES() );
				ShowPiece.addElement( ( ( Sail ) GraphicOwner ).getOrbit().getGEMINI() );
				ShowPiece.addElement( ( ( Sail ) GraphicOwner ).getOrbit().getEQUATOR() );
				ShowPiece.addElement( ( ( Sail ) GraphicOwner ).getOrbit().getNodeLine() );
				ShowPiece.addElement( ( ( Sail ) GraphicOwner ).getOrbit().getLatusRectum() );
				ShowPiece.addElement( ( ( Sail ) GraphicOwner ).getOrbit().getNodePlus90() );
				//ShowPiece.addElement(((Sail)GraphicOwner).getOrbit().getnewRHat());
			}
			if ( Name.equals( "Perturber" ) ) {
				ShowPiece.addElement( ( ( Sail ) GraphicOwner ).getOrbit().getPerturber().getDSailAtt() );
				ShowPiece.addElement( ( ( Sail ) GraphicOwner ).getOrbit().getPerturber().getDL() );
				ShowPiece.addElement( ( ( Sail ) GraphicOwner ).getOrbit().getPerturber().getDRL() );
				ShowPiece.addElement( ( ( Sail ) GraphicOwner ).getOrbit().getPerturber().getSumForce() );
				ShowPiece.addElement( ( ( Sail ) GraphicOwner ).getOrbit().getPerturber().getRadForce() );
				//ShowPiece.addElement(((Sail)GraphicOwner).getOrbit().getPerturber().getHarmForce());
				//ShowPiece.addElement(((Sail)GraphicOwner).getOrbit().getPerturber().getNBodForce());
				//ShowPiece.addElement(((Sail)GraphicOwner).getOrbit().getPerturber().getFluidForce());
				//ShowPiece.addElement(((Sail)GraphicOwner).getOrbit().getPerturber().getMagDragForce());
				//ShowPiece.addElement(((Sail)GraphicOwner).getOrbit().getPerturber().getImpForce());
			}
		}
		else {
			System.out.println( "Graphic Owner only knows how to show things" );
			System.out.println( "for Sails right now.  Owner wasn't a Sail." );
		}
	}


}
