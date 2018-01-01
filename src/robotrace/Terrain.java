package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import java.awt.Color;

/**
 * Represents the terrain, to be implemented according to the Assignments.
 */
class Terrain {
    private int subdivisions;
    private int patches;
    private double stepVert;
    private int vertexCount;
    private double[][] heights;
    private Color[] colors;
    private Vector[] vertices;
    private Vector[] normals;
    
    public Terrain(int subdivisions, int patches) {

        this.subdivisions = subdivisions;
        this.patches = patches;
        this.vertexCount = subdivisions * patches + 1;
        this.stepVert = 40d / subdivisions * patches;
        this.heights = TerrainUtility.generateHeights();
        this.vertices = TerrainUtility.storeVertexPositions();
        this.colors = TerrainUtility.generateColors(vertices, 10f);
        //this.normals = TerrainUtility.generateNormals();
    }

    /**
     * Draws the terrain.
     */
    public void draw(GL2 gl, GLU glu, GLUT glut) {

        //drawSubdivisions(gl, glut);
        //drawControlPoints(gl, glut);
        //drawCPConnections(gl, glut);
        gl.glBegin(GL2.GL_TRIANGLES);
        
        for (int idx = 0, stride = 0; idx < vertexCount * (vertexCount - 1); idx++, stride++) {
            
            if (stride == patches * subdivisions) {
                stride = -1;
                continue;
            }
            
            int row = idx / vertexCount;
            int col = idx % vertexCount;
            
            int topLeft = idx;
            int topRight = topLeft + 1;
            int bottomLeft = idx + vertexCount;
            int bottomRight = bottomLeft + 1;
            
            addQuad(topLeft, topRight, bottomLeft, bottomRight, col % 2 == row % 2, gl);
        }
        gl.glEnd();
    }
    
    
    
    private void drawSubdivisions (GL2 gl, GLUT glut) {
    
        for (int surface = 0; surface < patches * patches; surface++) {
        
            for (int subsY = 0; subsY <= subdivisions; subsY++) {

                for (int subsX = 0; subsX <= subdivisions; subsX++) {
                    gl.glPushMatrix();
                    Vector pos = TerrainUtility.getPointSurface(subsX/(float)subdivisions, subsY/(float)subdivisions, surface, TerrainUtility.getSurface());
                    gl.glTranslatef((float)pos.x, (float)pos.y, (float)pos.z);
                    gl.glColor3f(0,0,0);
//                    if (pos.z < -3f) {
//                        gl.glColor3f(0,0,1);
//                    } else if (pos.z < -1f) {
//
//                        gl.glColor3f(1,1,0);
//                    } else {
//
//                        gl.glColor3f(0,1,0);
//                    }
                    glut.glutSolidSphere(0.2d, 8, 4);
                    gl.glPopMatrix();;

                }

            }
        
        }    
    
    }
    
    private void drawControlPoints (GL2 gl, GLUT glut) {
    
        for (int surf = 0; surf < patches * patches; surf++) {
            for (int i = 0; i < 16; i++) {

                Vector pos = TerrainUtility.getVectorInPatch(surf, i);
                gl.glPushMatrix();
                gl.glTranslatef((float)pos.x, (float)pos.y, (float)pos.z);
                if (i == 0 || i == 3 || i == 12 || i == 15) {
                    gl.glColor3f(0,0,1);
                    glut.glutSolidSphere(0.5d, 8, 4);
                } else {
                    gl.glColor3f(1,0,0);
                    glut.glutSolidSphere(0.3d, 8, 4);
                }
                
                gl.glPopMatrix();

            }
        }    
    
    }
    
    private void drawCPConnections (GL2 gl, GLUT glut) {
    
        gl.glBegin(gl.GL_LINES);
        gl.glColor3f(0,0,1);
        Vector[] points;
        
        for (int surf = 0; surf < patches * patches; surf++) {
            
            for (int j = 0; j < 16; j++) {
                points = TerrainUtility.getPatch(surf);

                for (int i = 0; i < 4; i++) {

                    gl.glVertex3d(points[i * 4].x, points[i * 4].y, points[i * 4].z);
                    gl.glVertex3d(points[1 + i * 4].x, points[1 + i * 4].y, points[1 + i * 4].z);

                    gl.glVertex3d(points[1 + i * 4].x, points[1 + i * 4].y, points[1 + i * 4].z);
                    gl.glVertex3d(points[2 + i * 4].x, points[2 + i * 4].y, points[2 + i * 4].z);

                    gl.glVertex3d(points[2 + i * 4].x, points[2 + i * 4].y, points[2 + i * 4].z);
                    gl.glVertex3d(points[3 + i * 4].x, points[3 + i * 4].y, points[3 + i * 4].z);
                }

                for (int i = 0; i < 4; i++) {

                    gl.glVertex3d(points[i].x, points[i].y, points[i].z);
                    gl.glVertex3d(points[4 + i].x, points[4 + i].y, points[4 + i].z);

                    gl.glVertex3d(points[4 + i].x, points[4 + i].y, points[4 + i].z);
                    gl.glVertex3d(points[8 + i].x, points[8 + i].y, points[8 + i].z);

                    gl.glVertex3d(points[8 + i].x, points[8 + i].y, points[8 + i].z);
                    gl.glVertex3d(points[12 + i].x, points[12 + i].y, points[12 + i].z);
                }

            }
            
        }
        gl.glEnd();    
    
    }
    
    public void init(GL2 gl) { 
    }
    
    public void addVertex(Vector v, GL2 gl) {
    
        gl.glVertex3d(v.x(), v.y(),v.z());
    
    }
    
    public void addNormal(Vector v, GL2 gl) {
    
        gl.glNormal3d(v.x(), v.y(),v.z());
    
    }
    
    public void addColor(Color c, GL2 gl) {

        gl.glColor3f((float)c.getRed()/255f, (float)c.getGreen()/255f, (float)c.getBlue()/255f);
    
    }
    
    public void addQuad (int topLeft, int topRight, int bottomLeft, int bottomRight, boolean rightHanded, GL2 gl) {
    
        addLeftTriangle(topLeft, topRight, bottomLeft, bottomRight, rightHanded, gl);
        addRightTriangle(topLeft, topRight, bottomLeft, bottomRight, rightHanded, gl);
    }
    
    public void addLeftTriangle(int topLeft, int topRight, int bottomLeft, int bottomRight, boolean rightHanded, GL2 gl) {
        addNormal(calcNormalLeft(topLeft, topRight, bottomLeft, bottomRight, rightHanded, gl), gl);
        addColor(colors[topLeft], gl);
        addVertex(vertices[topLeft], gl);
        addVertex(vertices[bottomLeft], gl);
        if (rightHanded)
            addVertex(vertices[bottomRight], gl); 
        else
            addVertex(vertices[topRight], gl);
    }
    
    public void addRightTriangle(int topLeft, int topRight, int bottomLeft, int bottomRight, boolean rightHanded, GL2 gl) {
        addNormal(calcNormalRight(topLeft, topRight, bottomLeft, bottomRight, rightHanded, gl), gl);
        addColor(colors[topRight], gl);
        addVertex(vertices[topRight], gl);
        if (rightHanded)
            addVertex(vertices[topLeft], gl); 
        else
            addVertex(vertices[bottomLeft], gl);
        addVertex(vertices[bottomRight], gl);
    
    }
    public Vector calcNormalLeft (int topLeft, int topRight, int bottomLeft, int bottomRight, boolean rightHanded, GL2 gl) {

        Vector v0 = vertices[topLeft];
        Vector v1 = vertices[bottomLeft];
        Vector v2;
        if (rightHanded) {
            v2 = vertices[bottomRight];
        } else {
            v2 = vertices[topRight];
        }
        
        Vector tangentA = v1.subtract(v0);
        Vector tangentB = v2.subtract(v0);
        Vector normal = tangentA.cross(tangentB).normalized();
    
        return normal;
    }
    
        public Vector calcNormalRight (int topLeft, int topRight, int bottomLeft, int bottomRight, boolean rightHanded, GL2 gl) {
    
        Vector v0 = vertices[topRight];
        Vector v1;
        if (rightHanded) {
            v1 = vertices[topLeft];}
        else {
            v1 = vertices[bottomLeft];
        }

        Vector v2 = vertices[bottomRight];
        
        Vector tangentA = v1.subtract(v0);
        Vector tangentB = v2.subtract(v0);
        Vector normal = tangentA.cross(tangentB).normalized();
    
        return normal;
    }
}
