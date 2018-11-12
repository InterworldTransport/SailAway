/**
 * ThreeSpace (c) Copyright 2001 Adam Chodorowski, John Nilsson
 * Created during spring of 2001 for the PROST01 course
 */

package threespace.scene;

import javax.media.j3d.IndexedTriangleArray;
import javax.media.j3d.IndexedGeometryArray;
import javax.media.j3d.GeometryArray;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector3d;

import java.io.*;

import threespace.util.DEBUG;

/**
 * The Geometry class is the wrapper around the Java3D Geometry
 * data. The Java3D data is in the form of IndexedTriangleArray
 * and thus all constructors and access is on the form of this
 * structure. The arrays that are included in the structure are:
 * <ul>
 * <li>Vertices - all vertices in the 3D object on the form of
 *                {x1,y1,z1,x2,y2,z2,x3,y3,z3}
 * <li>Colors - the color of the vertices specified in the
 *              Vertices array. {red1,green1,blue1,r2,g2,b2}
 * <li>Triangles - Defines the connection between vertices
 *                {triangle1vertex1,triangle1vertex2,triangle1vertex3,
 *                 triangle2vertex1,triangle2vertex2,triangle2vertex3}
 * <li>Normals - automatically created. Creates a normal for each triangle.
 * </ul>
 *
 * @version 1.0,2001-04-28
 */

public class Geometry implements Serializable {
    private transient IndexedTriangleArray g_TriangleArray;
    private double[]  g_Vertices;
    private transient float[]   g_Normals;
    private byte[]    g_Colors;
    private int[]     g_Triangles;
    private double[]  g_Max = new double[3];
    private double[]  g_Min = new double[3];

    private transient Vector3d A,B,C,nA,AB,AC;
    private transient Vector3f normer;

    /**
     * Creates a new Geometry and populates the Normal-array.
     *
     * @param vertices The verticesarray - {x1,y1,z1,x2,y2,z2,...}
     * @param colors The colorarray - {r1,g1,b1,r2,g2,b2,...}
     * @param triangles {triangle1vertex1,t1v2,t1v3,t2v1,t2v2,t2v3,...}
     */
    public Geometry(double[] vertices, byte[] colors, int[] triangles){
	g_Vertices  = vertices;
	g_Colors    = colors;
	g_Triangles = triangles;

        DEBUG.printLine( "Created geometry with " + (g_Triangles.length/3) + " polygons.");

        initialize();
    }
    
    private void initialize() {
	// Create the vectors used
	A=null;
	A=new Vector3d();
	B=null;
	B=new Vector3d();
	C=null;
	C=new Vector3d();
	nA=null;
	nA=new Vector3d();
	AB=null;
	AB=new Vector3d();
	AC=null;
	AC=new Vector3d();
	normer=null;
	normer=new Vector3f();;

	// Create the normal-array
	g_Normals=null;
	g_Normals=new float[(int)g_Vertices.length];

        createGeometry();
        updateAll();
    }
    
    /**
     * Returns the colorsarray.
     */
    public byte[] getColors(){
	return g_Colors;
    }

    /**
     * Returns the verticesarray.
     */
    public double[] getVertices(){
	return g_Vertices;
    }

    /**
     * Returns the trianglesarray.
     */
    public int[] getTriangles(){
	return g_Triangles;
    }

    /**
     * Returns the normalsarray.
     */
    public float[] getNormals(){
	return g_Normals;
    }

    /**
     * Returns the size needed for the internal Java3D vertex-,color-,
     * and normal-arrays
     */
    private int getVertexCount(){
	return (int)(g_Vertices.length/3);
    }

    /**
     * Returns the size needed for the internal Java3D index-array
     */
    private int getTriangleCount(){
	return g_Triangles.length;
    }

    /**
     * Returns the maximum x,y,z values returned in an double-array
     */
    public double[] getMax(){
	return g_Max;
    }

    /**
     * Returns the minimum x,y,z values returned in an double-array
     */
    public double[] getMin(){
	return g_Min;
    }

    /**
     * Updates both the colors and the vertices of the Java3D geometry
     * @see javax.media.j3d.IndexedTriangleArray
     */
    public void updateAll(){
	updateColors();
	updateVertices();
    }

    /**
     * Updates the colors of the Java3D geometry
     * @see javax.media.j3d.IndexedTriangleArray
     */
    public void updateColors(){
	g_TriangleArray.setColorIndices(0, g_Triangles);
    }

    /**
     * Updates the vertices of the Java3D geometry and then
     * creates the normals and updates the geometry with those.
     * @see javax.media.j3d.IndexedTriangleArray
     */
    public void updateVertices(){
	createNormals();
	for(int i=0;i<3;i++){
	    if(Double.isNaN(g_Vertices[i])){
		DEBUG.printLine("Vertice#"+i+" is NaN");
		g_Vertices[i]=0;
	    }
	    g_Min[i]=g_Vertices[i];
	    g_Max[i]=g_Vertices[i];
	}
	for(int i=3;i<g_Vertices.length;i++){
	    if(Double.isNaN(g_Vertices[i])){
		DEBUG.printLine("Vertice#"+i+" is NaN");
		g_Vertices[i]=0;
	    }
	    if(g_Vertices[i]>g_Max[i%3]){
		g_Max[i%3]=g_Vertices[i];
	    }else if(g_Vertices[i]<g_Min[i%3]){
		g_Min[i%3]=g_Vertices[i];
	    }
	}

	g_TriangleArray.setCoordinateIndices(0,g_Triangles);
	g_TriangleArray.setNormalIndices(0,g_Triangles);

	DEBUG.printLine("updating vertices on j3d object");
    }

    /**
     * Creates the IndexedTriangleArray object, and sets the indexes
     * for colors,vertices and normals.
     * @see javax.media.j3d.IndexedTriangleArray
     */
    private void createGeometry(){
	g_TriangleArray=null;
	g_TriangleArray=new IndexedTriangleArray(getVertexCount(),
						 GeometryArray.COORDINATES |
						 GeometryArray.COLOR_3 |
						 GeometryArray.NORMALS |
						 GeometryArray.BY_REFERENCE,
						 getTriangleCount());
	g_TriangleArray.setCapability(IndexedGeometryArray.ALLOW_COORDINATE_INDEX_WRITE);
	g_TriangleArray.setCapability(IndexedGeometryArray.ALLOW_COLOR_INDEX_WRITE);
	g_TriangleArray.setCapability(IndexedGeometryArray.ALLOW_NORMAL_INDEX_WRITE);
	g_TriangleArray.setCapability(GeometryArray.ALLOW_COLOR_WRITE);
	g_TriangleArray.setCapability(GeometryArray.ALLOW_COORDINATE_WRITE);
	g_TriangleArray.setCapability(GeometryArray.ALLOW_NORMAL_WRITE);

	g_TriangleArray.setCoordRefDouble(g_Vertices);
	g_TriangleArray.setColorRefByte(g_Colors);
	g_TriangleArray.setNormalRefFloat(g_Normals);
    }

    /**
     * Gets the IndexedGeometryArray repressentation as the Java3D
     * structure of the Geometry.
     * @see javax.media.j3d.IndexedGeometryArray
     */
    IndexedGeometryArray getIndexedGeometryArray(){
	return g_TriangleArray;
    }
    
    /**
     * Clones the Geometry.
     */
    protected Object clone(){
	double[] vertices=(double[])g_Vertices.clone();
	byte[] colors=(byte[])g_Colors.clone();
	int[] triangles=(int[])g_Triangles.clone();
	Geometry g=new Geometry(vertices,colors,triangles);
	vertices=null;
	colors=null;
	triangles=null;
	return g;
    }

    /**
     * Creates the normals for all triangles. 
     */
    private void createNormals(){
	for(int i=0;i<g_Triangles.length;i+=3){
	    A.x=g_Vertices[g_Triangles[i+0]*3+0];
	    A.y=g_Vertices[g_Triangles[i+0]*3+1];
	    A.z=g_Vertices[g_Triangles[i+0]*3+2];
	    B.x=g_Vertices[g_Triangles[i+1]*3+0];
	    B.y=g_Vertices[g_Triangles[i+1]*3+1];
	    B.z=g_Vertices[g_Triangles[i+1]*3+2];
	    C.x=g_Vertices[g_Triangles[i+2]*3+0];
	    C.y=g_Vertices[g_Triangles[i+2]*3+1];
	    C.z=g_Vertices[g_Triangles[i+2]*3+2];
	    AB.set(A);
	    AC.set(A);
	    AB.sub(B);
	    AC.sub(C);
	    nA.cross(AB,AC);
	    nA.normalize();
	    
	    if(!Double.isNaN(nA.x) &&
	       !Double.isNaN(nA.y) &&
	       !Double.isNaN(nA.z)){
		for(int j=0;j<3;j++){
		    g_Normals[g_Triangles[i+j]*3+0]+=nA.x;
		    g_Normals[g_Triangles[i+j]*3+1]+=nA.y;
		    g_Normals[g_Triangles[i+j]*3+2]+=nA.z;
		}
	    }
	}
	for(int i=0;i<g_Normals.length;i+=3){
	    normer.x=g_Normals[i+0];
	    normer.y=g_Normals[i+1];
	    normer.z=g_Normals[i+2];
	    if(!(normer.x==0 && normer.y==0 && normer.z==0))
		normer.normalize();
	    if(!Double.isNaN(normer.x) &&
	       !Double.isNaN(normer.y) &&
	       !Double.isNaN(normer.z)){
		g_Normals[i+0]=normer.x;
		g_Normals[i+1]=normer.y;
		g_Normals[i+2]=normer.z;
	    }
	}
    }

    /**
     * Special serialization support.
     */
    private void readObject( ObjectInputStream input ) throws IOException {
        try {
            input.defaultReadObject();
        } catch( ClassNotFoundException e ) {
            throw new IOException();
        }
        
        initialize();
    }
}

