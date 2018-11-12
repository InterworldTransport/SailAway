/*
 * <h2>Copyright</h2>
 * Copyright (c) 1998-2001 Interworld Transport.  All rights reserved.<br>
 * ---com.interworldtransport.sailgui.SailDraw3D-----------------------------------
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
 * ---com.interworldtransport.sailgui.SailDraw3D-----------------------------------
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
 *  com.interworldtransport.sailgui.SailDraw3D Root class of all SailAway graphics3D
 *  panels. Purpose: This class acts as the root class of all graphics3D panels
 *  that draw representations of a Sail Object. Members and Methods common to
 *  all such panels are defined here. Members and Methods particular to a representation
 *  are defined in subclasses.
 *
 * @author     Dr Alfred W Differ
 * @version    0.03, $Date: 2001/11/03 09:02:54 $
 * @see        com.interworldtransport.sailgui.SailAway
 * @see        com.interworldtransport.sailmodel.OrbitEllipse
 * @see        com.interworldtransport.sailmodel
 * @see        com.interworldtransport.clados
 */
public class SailDraw3D extends JFrame {
	/**
	 *  This String is the name of the graphic displayer. This name is used to
	 *  help identify the graphic to be requested when refreshes are needed.
	 *
	 * @since
	 */
	public String Name;
	/**
	 *  Description of the Field
	 *
	 * @since
	 */
	public Container cp;
	/**
	 *  The GraphicOwner is the object to which ShowPiece belongs. Reqeusts for
	 *  coefficients are made to this owner.
	 *
	 * @since
	 */
	public Object GraphicOwner;
	/**
	 *  ShowPiece is a Vector of Objects to be shown in the graphic frame. Reqeusts
	 *  for information to be translated to an image are made to the owner.
	 *
	 * @since
	 */
	public Vector ShowPiece;
	/**
	 *  A reference to the parent GUI.
	 *
	 * @since
	 */
	protected SailAway ParentGUI;
	/**
	 *  Description of the Field
	 *
	 * @since
	 */
	private java.net.URL texImage = null;
	/**
	 *  Description of the Field
	 *
	 * @since
	 */
	private java.net.URL texImage2 = null;
	/**
	 *  Description of the Field
	 *
	 * @since
	 */
	private final static float[] ellipsequad = {
			0.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			0.707f, 0.707f, 0.0f,
			0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
			0.707f, 0.0f, 0.707f,
			0.0f, 0.0f, 1.0f,
			};
	/**
	 *  Description of the Field
	 *
	 * @since
	 */
	private final static int[] perstrip = {4, 4};


	/**
	 *  Construct and parent information for the presenter.
	 *
	 * @param  pName  Name the graphical frame so it may be found on a Vector
	 * @param  pGUI   Name the GUI that owns this frame
	 * @since
	 */
	public SailDraw3D( String pName, SailAway pGUI ) {
		super( pName );
		this.Name = pName;
		this.ParentGUI = pGUI;
		this.cp = this.getContentPane();
		cp.setLayout( new BorderLayout() );
		//this.getContentPane().setOpaque(true);
		//this.setBackground(Color.white);
		setSize( 350, 350 );

		this.ShowPiece = new Vector();
		ShowPiece.addElement( ParentGUI.TestSail.getOrbit().getL() );
		ShowPiece.addElement( ParentGUI.TestSail.getOrbit().getRungeLenz() );

		if ( texImage == null ) {
			try {
				texImage = new java.net.URL(
						"file:./images/sun.jpg" );
			}
			catch ( java.net.MalformedURLException ex ) {
				System.out.println( ex.getMessage() );
				System.exit( 1 );
			}
		}

		GraphicsConfiguration config =
				SimpleUniverse.getPreferredConfiguration();

		Canvas3D c = new Canvas3D( config );
		cp.add( "Center", c );

		// Call for the references to all the items that show in the scene.
		this.setGraphicOwner( pGUI.TestSail );

		// Create the scene and attach it to the virtual universe
		BranchGroup scene = createSceneGraph();
		SimpleUniverse u = new SimpleUniverse( c );

		// This will move the ViewPlatform back a bit so the
		// objects in the scene can be viewed.
		u.getViewingPlatform().setNominalViewingTransform();
		u.addBranchGraph( scene );

	}


	/**
	 *  This method is used by the EventModel to set the Owner object for the ShowPiece
	 *  right after the Frame is instantiated.
	 *
	 * @param  pOwner  The new GraphicOwner value
	 * @since
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
	 * @since
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

	//end of SailDraw Panel constructor


	/**
	 *  This method lays out the panel showing the graphic to be shown. It does
	 *  not actually set the values that appear in any fields.
	 *
	 * @return    Description of the Returned Value
	 * @since
	 */
	public BranchGroup createSceneGraph() {
		//Set up the scene to be used to show the contents of the ShowPiece
		// array.  Make sure the scene makes it clear which object is the
		// Graphic Owner.

		// Create the root of the branch graph
		BranchGroup objRoot = new BranchGroup();

		// Create a Transformgroup to scale all objects so they
		// appear in the scene.
		TransformGroup objScale = new TransformGroup();
		Transform3D t3d = new Transform3D();
		t3d.setScale( 0.4 );
		objScale.setTransform( t3d );
		objRoot.addChild( objScale );

		// Create the TransformGroup node and initialize it to the
		// identity. Enable the TRANSFORM_WRITE capability so that
		// our behavior code can modify it at run time. Add it to
		// the root of the subgraph.
		TransformGroup objTrans = new TransformGroup();
		objTrans.setCapability( TransformGroup.ALLOW_TRANSFORM_READ );
		objTrans.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );
		objScale.addChild( objTrans );

		// Create a simple Shape3D node; add it to the scene graph.
		//Sphere SphereObj = new Sphere(0.5f, Sphere.GENERATE_NORMALS |
		//			  Sphere.GENERATE_TEXTURE_COORDS, 45);
		//Appearance ap = SphereObj.getAppearance();
		//objTrans.addChild(SphereObj);

		//TextureLoader tex = new TextureLoader(texImage, "RGB", this);
		//ap.setTexture(tex.getTexture());

		TriangleFanArray orbitplane = new TriangleFanArray( 8,
				TriangleFanArray.COORDINATES, perstrip );
		orbitplane.setCoordinates( 0, ellipsequad );
		Shape3D testShape = new Shape3D( orbitplane );
		objTrans.addChild( testShape );

		BoundingSphere bounds =
				new BoundingSphere( new Point3d( 0.0, 0.0, 0.0 ), 100.0 );
		// Create a new Behavior object that will perform the
		// desired operation on the specified transform and add
		// it into the scene graph.
		// Create the rotate behavior node
		MouseRotate behavior = new MouseRotate( objTrans );
		objTrans.addChild( behavior );
		behavior.setSchedulingBounds( bounds );
		// Create the zoom behavior node
		MouseZoom behavior2 = new MouseZoom( objTrans );
		objTrans.addChild( behavior2 );
		behavior2.setSchedulingBounds( bounds );
		// Create the translate behavior node
		MouseTranslate behavior3 = new MouseTranslate( objTrans );
		objTrans.addChild( behavior3 );
		behavior3.setSchedulingBounds( bounds );

		//Shine it with two colored lights.
		Color3f lColor1 = new Color3f( 0.7f, 0.0f, 0.7f );
		Color3f lColor2 = new Color3f( 0.7f, 0.7f, 0.0f );
		Vector3f lDir1 = new Vector3f( -1.0f, -1.0f, -1.0f );
		Vector3f lDir2 = new Vector3f( 0.0f, 0.0f, -1.0f );
		DirectionalLight lgt1 = new DirectionalLight( lColor1, lDir1 );
		DirectionalLight lgt2 = new DirectionalLight( lColor2, lDir2 );
		lgt1.setInfluencingBounds( bounds );
		lgt2.setInfluencingBounds( bounds );
		objScale.addChild( lgt1 );
		objScale.addChild( lgt2 );

		// Have Java 3D perform optimizations on this scene graph.
		//objRoot.compile();
		objRoot.compile();
		return objRoot;
	}


	/**
	 *  This method is used by the setGraphicDisplay method to actually get the
	 *  Graphics for the ShowPiece.
	 *
	 * @since
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

