package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;


public class Water {
    
    Vector position;
    int side;
    
    public Water (Vector position, int side) {
    
        this.position = position;
        this.side = (side % 2 == 0) ? side : side + 1;
    
    }
    
    public void draw(GL2 gl, GLU glu, GLUT glut) {
    
        int sideDiv2 = side / 2;
        
        gl.glBegin(GL2.GL_QUADS);
        gl.glNormal3f(0,0,1);
        gl.glTexCoord2i(1, 0); gl.glVertex3d(position.x() + sideDiv2, position.y() - sideDiv2, position.z());
        gl.glTexCoord2i(0, 0); gl.glVertex3d(position.x() - sideDiv2, position.y() - sideDiv2, position.z());
        gl.glTexCoord2i(0, 1); gl.glVertex3d(position.x() - sideDiv2, position.y() + sideDiv2, position.z());
        gl.glTexCoord2i(1, 1); gl.glVertex3d(position.x() + sideDiv2, position.y() + sideDiv2, position.z());
        
        

        gl.glEnd();
    
    }
    
    public float getHeight() {
        return (float)position.z();
    }

}
