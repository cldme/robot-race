package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

/**
 * Represents the terrain, to be implemented according to the Assignments.
 */
class Terrain {

    private static int subdivisions; // relatively large value.. will be lowered for final terrain
    private static int patches;
    
    public Terrain() {
        this.subdivisions = TerrainUtility.subdivisions;
        this.patches = TerrainUtility.p;
    }

    /**
     * Draws the terrain.
     */
    public void draw(GL2 gl, GLU glu, GLUT glut) {
        
        for (int surface = 0; surface < patches * patches; surface++) {
        
            for (int subsY = 0; subsY <= subdivisions; subsY++) {

                for (int subsX = 0; subsX <= subdivisions; subsX++) {
                    gl.glPushMatrix();
                    Vector pos = TerrainUtility.getPointSurface(subsX/(float)subdivisions, subsY/(float)subdivisions, surface, TerrainUtility.bezierSurfaces);
                    gl.glTranslatef((float)pos.x, (float)pos.y, (float)pos.z);
                    if (pos.z < -3f) {
                        gl.glColor3f(0,0,1);
                    } else if (pos.z < -1f) {

                        gl.glColor3f(1,1,0);
                    } else {

                        gl.glColor3f(0,1,0);
                    }
                    glut.glutSolidSphere(0.2d, 8, 4);
                    gl.glPopMatrix();;

                }

            }
        
        }

        for (int surf = 0; surf < patches * patches; surf++) {
            for (int i = 0; i < 16; i++) {

                Vector pos = TerrainUtility.bezierSurfaces[surf][i];
                gl.glPushMatrix();
                gl.glColor3f(1,0,0);
                gl.glTranslatef((float)pos.x, (float)pos.y, (float)pos.z);
                glut.glutSolidSphere(0.3d, 8, 4);
                gl.glPopMatrix();;

            }
        }
        
        gl.glBegin(gl.GL_LINES);
        gl.glColor3f(0,0,1);
        Vector[] points;
        
        for (int surf = 0; surf < patches * patches; surf++) {
            
            for (int j = 0; j < 16; j++) {
                points = TerrainUtility.bezierSurfaces[surf];

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
    
}
