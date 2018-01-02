package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL2ES3.GL_QUADS;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.*;

import com.jogamp.opengl.util.texture.Texture;
import static java.lang.Math.abs;

/**
 *
 * @author s168309
 */
public abstract class BodyPart {
    
    // Variable for the bodypart material
    public Material material;
    // Variable for the bodypart accent
    public Material accent;
    
    // Variables for the body part dimensions
    public double length;
    public double width;
    public double bodyWidthRadius;
    public double headWidth;
    public double faceHeight;
    public double foreheadRadius;
    public double antennaBaseRadius;
    public double limbRadius;
    public double armLength;
    
    // Variable for number of stacks when drawing (resolution)
    public final int stacks = 30;
    
    // Variable for number of slices when drawing (resolution)
    public final int slices = 30;
    
    // Variables for necessary OpenGL libraries
    public GL2 gl;
    public GLU glu;
    public GLUT glut;
    
    public Texture texture;
    
    // Variable for the animation ticker
    public float tAnim;
    
    /**
     * Draw function for the body part
     * Needs to be overridden inside the various body parts
     */
    public void Draw() {}
    
    /**
     * Draw function for the body part (in stick mode)
     * Needs to be overridden inside the various body parts
     */
    public void DrawStick() {}
    
    /**
     * Function for drawing a solid cube
     * 
     * @param dim   The size of the cube (length of one side)
     * @param dispZ The displacement on the Z-axis
     */
    public void SolidCube(float dim, float dispZ) {
        gl.glPushMatrix();
        gl.glBegin(GL2.GL_QUADS);
        
        // Front Face (up)
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-dim, -dim, dim);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( dim, -dim, dim);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( dim, dim, dim);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-dim, dim, dim);

        // Back Face (down)
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( dim, -dim, -dim);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-dim, -dim, -dim);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-dim, dim, -dim);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( dim, dim, -dim);

        // Top Face (front)
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-dim, dim, dim);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( dim, dim, dim);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( dim, dim, -dim - dispZ);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-dim, dim, -dim - dispZ);

        // Bottom Face (back)
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( dim, -dim, dim);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-dim, -dim, dim);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-dim, -dim, -dim - dispZ);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( dim, -dim, -dim - dispZ);

        // Right face (right)
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( dim, -dim, dim);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( dim, -dim, -dim -dispZ);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( dim, dim, -dim - dispZ);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( dim, dim, dim);

        // Left Face (left)
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-dim, -dim, -dim - dispZ);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-dim, -dim, dim);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-dim, dim, dim);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-dim, dim, -dim - dispZ);
        
        gl.glEnd();
        gl.glPopMatrix();
    }
    
    /**
     * Function for drawing a solid cylinder
     * 
     * @param radius The radius of the cylinder
     * @param height The height of the cylinder
     */
    public void SolidCylinder(double radius, double height) {
        glut.glutSolidCylinder(radius, height, slices, stacks);
    }
    
    /**
     * Function for drawing a solid circle
     * 
     * @param radius Radius of the circle
     */
    public void SolidCircle(double radius) {
        this.SolidCylinder(radius, 0.0);
    }
    
    /**
     * Function for drawing a solid sphere
     * 
     * @param radius The radius of the sphere
     */
    public void SolidSphere(double radius) {
        glut.glutSolidSphere(radius, slices, stacks);
    }
    
    /**
     * Function for drawing a cone
     * 
     * @param radius The cone base radius
     * @param height The cone height
     */
    public void SolidCone(double radius, double height) {
        glut.glutSolidCone(radius, height, slices, stacks);
    }
    
    /**
     * Function that sets the parts material
     * 
     * @param material Set material determined by given robot type
     */
    public void setMaterial(Material material) {
        gl.glMaterialf(GL_FRONT, GL_SHININESS, material.shininess);
        gl.glMaterialfv(GL_FRONT, GL_DIFFUSE, material.diffuse, 0);
        gl.glMaterialfv(GL_FRONT, GL_SPECULAR, material.specular, 0);
    }
}

class Head extends BodyPart {
    
    @Override
    public void DrawStick() {
        // Push current view matrix on top of the stack
        gl.glPushMatrix();
        
        gl.glTranslated(0, 0, this.length + (this.faceHeight / 2));
        // Draw the robot head
        this.SolidSphere(foreheadRadius);
        
        // Set the material to the original one
        this.setMaterial(this.material);
        
        // Pop view matrix from the top of the stack
        gl.glPopMatrix();
    }
    
    @Override
    public void Draw() {
        // Push current view matrix on top of the stack
        gl.glPushMatrix();
        
        // Enable and bind texture
//        texture.enable(gl);
//        texture.bind(gl);
        
        // Translate head to correct position
        gl.glTranslated(0, 0, this.length + 7f);
        
        // -------------------- DRAW ROBOT HEAD (textured cube) ----------
        this.SolidCube(3, 0);
        // -------------------- DRAW ROBOT HEAD (textured cube) ----------
        
        
        // Disable OpenGL texture
        texture.disable(gl);
        // Pop view matrix from the top of the stack
        gl.glPopMatrix();
    }
}

class Body extends BodyPart {
    
    @Override
    public void DrawStick() {
        // Push current view matrix on top of the stack
        gl.glPushMatrix();

        // Draw the body
        this.SolidCylinder(this.bodyWidthRadius / 20, this.length);

        // Translate for neck to be ontop of body
        gl.glTranslated(0, 0, this.length);

        // Draw the neck
        this.SolidCylinder(this.bodyWidthRadius / 20, this.length * 0.3);

        // Pop view matrix from the top of the stack
        gl.glPopMatrix();
    }
    
    @Override
    public void Draw() {
        // Push current view matrix on top of the stack
        gl.glPushMatrix();
        
        // Enable and bind texture
        texture.enable(gl);
        texture.bind(gl);
        
        //Translate body to correct position
        gl.glTranslated(0, 0, this.length);
        
        // -------------------- DRAW ROBOT HEAD (textured cube) ----------
        this.SolidCube(3, 6.5f);
        // -------------------- DRAW ROBOT HEAD (textured cube) ----------
        
        
        // Translate for neck to be ontop of body
        gl.glTranslated(0, 0, 1f);

        // Draw the "neck"
        this.SolidCylinder((double) 3 * 0.75, this.length * 0.60);
        
        // Disable OpenGL texture
        texture.disable(gl);
        // Pop view matrix from the top of the stack
        gl.glPopMatrix();
    }
}

class Arm extends BodyPart {
    
    // Variables for the arms
    // Left = 1; Right = -1
    public int side;
    
    // Angle of the upper arm with respect to shoulder joint
    private int upperAnim = 0;
    // Angle of lowerarm with respect to upper arm
    private int lowerAnim = 0;

    // Animation step (speed?) for te rotations
    private int upperstep;
    private int lowerstep;
    
    public Arm(int tempSide) {
        this.side = tempSide;
        
        // The two arms rotate in different directions (to simulate human movement)
        upperstep = (-tempSide) * 8;
        lowerstep = 5;
    }
    
    @Override
    public void DrawStick() {
        // Update the animation tickers
        upperAnim += upperstep;
        lowerAnim += lowerstep;
        
        // switch arm sway direction when needed
        if (abs(upperAnim) >= 80 && upperAnim >= 0 || upperAnim < 0 && abs(upperAnim) >= 45) {
            upperstep = upperstep * -1;
        }

        // switch arm sway direction when needed
        if (lowerAnim == 80 || lowerAnim == 0) {
            lowerstep = lowerstep * -1;
        }
        
        gl.glPushMatrix();

        //Translate for correct position on body
        gl.glTranslated(0, 0, this.length * 0.9);
        
        gl.glPushMatrix();

        //Set accent color
        this.setMaterial(this.accent);

        //Translate to correct postition next to body
        gl.glTranslated((this.width * 0.5) * this.side, 0, 0);

        //Rotate over y-axis to offset from the body
        gl.glRotated(170 * this.side, 0, 1, 0);

        // Animate swaying of uppe arm
        gl.glRotated(-this.upperAnim, 1, 0, 0);

        //Draw shoulder
        this.SolidSphere(this.limbRadius / 2);

        // Draw upper arm
        this.SolidCylinder(this.limbRadius / 5, this.armLength);

        // Translate for joint to be at end of upper arm
        gl.glTranslated(0, 0, this.armLength);

        // Draw the joint
        this.SolidSphere(this.limbRadius / 2);

        // Set rotation of lower arm
        gl.glRotated(-lowerAnim, 1, 0, 0);

        // Draw the lower arm
        this.SolidCylinder(this.limbRadius / 5, this.armLength);

        //Set original color
        this.setMaterial(this.material);

        gl.glPopMatrix();
        gl.glPopMatrix();
    }
    
    @Override
    public void Draw() {
        // Step up the animation tickers
        upperAnim += upperstep;
        lowerAnim += lowerstep;

        // switch arm sway direction when needed
        if (abs(upperAnim) >= 80 && upperAnim >= 0 || upperAnim < 0 && abs(upperAnim) >= 45) {
            upperstep = upperstep * -1;
        }

        // switch arm sway direction when needed
        if (lowerAnim == 80 || lowerAnim == 0) {
            lowerstep = lowerstep * -1;
        }

        gl.glPushMatrix();

        //Translate for correct position on body
        gl.glTranslated(0, 0, this.length * 0.9);

        gl.glPushMatrix();

        //Set accent color
        this.setMaterial(this.accent);

        //Translate to correct postition next to body
        gl.glTranslated((this.width * .5 - 0.75) * this.side, 0, 0);

        //Rotate over y-axis to offset from the body
        gl.glRotated(170 * this.side, 0, 1, 0);

        // Animate swaying of uppe arm
        gl.glRotated(-this.upperAnim, 1, 0, 0);

        //Draw shoulder
        this.SolidSphere(this.limbRadius);

        // Draw upper arm
        this.SolidCylinder(this.limbRadius, this.armLength);

        // Translate for joint to be at end of upper arm
        gl.glTranslated(0, 0, this.armLength);

        // Draw the joint
        this.SolidSphere(this.limbRadius);

        // Set rotation of lower arm
        gl.glRotated(-lowerAnim, 1, 0, 0);

        // Draw the lower arm
        this.SolidCylinder(this.limbRadius, this.armLength);

        // Translate for hand position
        gl.glTranslated(0, 0, (this.length * .80) / 2);

        //Set original color
        this.setMaterial(this.material);

        // Draw hand (cone)
        this.SolidCone(this.bodyWidthRadius / 3, -this.length * .3);
        // Close the hand cone | create palm 
        this.SolidCircle(this.bodyWidthRadius / 3);

        gl.glPopMatrix();
        gl.glPopMatrix();
    }
}

class Leg extends BodyPart {
    
    int side;
    
    int step = 5;
    int angle = 0;
    
    public Leg(int tempSide) {
        // The two legs rotate in different directions (to simulate human movement)
        this.side = tempSide;
        this.step = this.step * tempSide;
    }
    
    @Override
    public void DrawStick() {
        this.angle += this.step;

        if (abs(this.angle) == 40) {
            this.step = this.step * -1;
        }

        gl.glPushMatrix();

        //Set accent color
        this.setMaterial(this.accent);

        //Translate legs for correct position to body
        gl.glTranslated((this.width * 0.2) * this.side, 0, this.limbRadius);
        gl.glPushMatrix();

        //Draw rotated leg
        gl.glRotated(-7 * this.side, 0, 1, 0);

        // Animate the leg swaying
        gl.glRotated(this.angle, 1, 0, 0);

        // Draw the leg
        this.SolidCylinder(this.limbRadius / 5, -this.length * 0.8);

        //Set original color
        this.setMaterial(this.material);

        gl.glPopMatrix();
        gl.glPopMatrix();
    }
    
    @Override
    public void Draw() {
        this.angle += this.step;

        if (abs(this.angle) == 40) {
            this.step = this.step * -1;
        }

        // Push me, and then just touch me, till I can't get my..satisfaction
        gl.glPushMatrix();

        //Set accent color
        this.setMaterial(this.accent);

        //Translate legs for correct position to body
        gl.glTranslated((this.width * .2) * this.side, 0, this.limbRadius);

        // Push me, and then just touch me, till I can't get my..satisfaction
        gl.glPushMatrix();

        //Draw rotated leg
        gl.glRotated(-7 * this.side, 0, 1, 0);

        // Animate the leg swaying
        gl.glRotated(this.angle, 1, 0, 0);

        // Draw the leg
        this.SolidCylinder(this.limbRadius, -this.length * 0.8);
        //gl.glPopMatrix();

        gl.glTranslated(0, 0, -this.length * 0.80);

        //Set original color
        this.setMaterial(this.material);

        //Create clipping plane for hemisphere foot
        double[] eqn = {0.0, 0.0, 1.0, 0.0};
        gl.glClipPlane(GL2.GL_CLIP_PLANE0, eqn, 0);
        gl.glEnable(GL2.GL_CLIP_PLANE0);

        //Draw foot
        this.SolidSphere(this.bodyWidthRadius * .4);
        gl.glDisable(GL2.GL_CLIP_PLANE0);

        // Draw foot sole
        this.SolidCircle(this.bodyWidthRadius * .4);

        gl.glPopMatrix();

        gl.glPopMatrix();
    }
}