package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL2ES3.GL_QUADS;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.*;

/**
* Represents a Robot, to be implemented according to the Assignments.
*/
class Robot {
    
    /** The position of the robot. */
    public Vector position = new Vector(0, 0, 0);
    
    /** The direction in which the robot is running. */
    public Vector direction = new Vector(1, 0, 0);

    /** The material from which this robot is built. */
    private final Material material;
    
    

    /**
     * Constructs the robot with initial parameters.
     */
    public Robot(Material material
            
    ) {
        this.material = material;

        
    }

    /**
     * Draws this robot (as a {@code stickfigure} if specified).
     */
    public void draw(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        float scale = 0.5f;
        gl.glPushMatrix();
        Textures.head.bind(gl);
        gl.glScalef(scale,scale,scale);
        
        
        gl.glBegin(GL2.GL_QUADS);
        
        // Front Face (up)
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.5f, -0.5f, 0.5f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 0.5f, -0.5f, 0.5f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 0.5f, 0.5f, 0.5f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.5f, 0.5f, 0.5f);

        // Back Face (down)
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 0.5f, -0.5f, -0.5f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-0.5f, -0.5f, -0.5f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-0.5f, 0.5f, -0.5f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 0.5f, 0.5f, -0.5f);

        // Top Face (front)
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.5f, 0.5f, 0.5f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 0.5f, 0.5f, 0.5f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 0.5f, 0.5f, -0.5f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.5f, 0.5f, -0.5f);

        // Bottom Face (back)
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 0.5f, -0.5f, 0.5f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-0.5f, -0.5f, 0.5f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-0.5f, -0.5f, -0.5f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 0.5f, -0.5f, -0.5f);

        // Right face (right)
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 0.5f, -0.5f, 0.5f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 0.5f, -0.5f, -0.5f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 0.5f, 0.5f, -0.5f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 0.5f, 0.5f, 0.5f);

        // Left Face (left)
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.5f, -0.5f, -0.5f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-0.5f, -0.5f, 0.5f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-0.5f, 0.5f, 0.5f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.5f, 0.5f, -0.5f);
        
        gl.glEnd();
        
        gl.glPopMatrix();
        
        
    }
    
    
}
