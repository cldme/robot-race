package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL2ES3.GL_QUADS;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.*;

import com.jogamp.opengl.util.texture.Texture;

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
    private final Material accent;
    
    private Texture torso;
    private Texture face;
    
    private GL2 gl;
    private GLUT glut;
    
    // Variables for setting up the robots
    private final double scale = 0.05;
    private final double bodyWidth = 9;
    private final double bodyWidthRadius = this.bodyWidth / 2;
    private final double bodyHeight = 11;
    public double headWidth  = (this.bodyWidth / 11) * 5;
    public double faceHeight = this.headWidth * 1.5;
    public double foreheadRadius  = this.headWidth / 2;
    public double antennaBaseRadius  = this.foreheadRadius / 5;
    public double limbRadius  = this.bodyWidthRadius / 6;
    public double armLength = (this.bodyHeight * 0.75) / 2;
    
    // Variables for model parametrization (update is easier)
    private final Head head = new Head();
    private final Body body = new Body();
    private final Arm armL = new Arm(1);
    private final Arm armR = new Arm(-1);
    private final Leg legL = new Leg(1);
    private final Leg legR = new Leg(-1);
    
    // Declare array for all body parts
    BodyPart[] parts = new BodyPart[] {head, body, armL, armR, legL, legR};

    /**
     * Constructs the robot with initial parameters.
     */
    public Robot(Material material, Material accent, Texture torso, Texture face) {
        
        this.material = material;
        this.accent = accent;
        this.torso = torso;
        this.face = face;
        
        // Link values to body parts attributes (using the hierarchy example)
        for(BodyPart p : parts) {
            p.length = this.bodyHeight;
            p.width = this.bodyWidth;
            p.bodyWidthRadius = this.bodyWidthRadius;
            p.material = this.material;
            p.accent = this.accent;           
            p.headWidth = this.headWidth;
            p.faceHeight = this.faceHeight;
            p.foreheadRadius = this.foreheadRadius;
            p.antennaBaseRadius = this.antennaBaseRadius;
            p.limbRadius = this.limbRadius;
            p.armLength = this.armLength; 
            p.texture = this.torso;
        }
    }

    /**
     * Draws this robot (as a {@code stickfigure} if specified).
     */
    public void draw(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        
        this.gl = gl;
        this.glut = glut;
        
        // Link values to body parts attributes (using the hierarchy example)
        for (BodyPart p : parts) {
            p.gl = gl;
            p.glu = glu;
            p.glut = glut;
            p.tAnim = tAnim;
        }
        
        // Set the robot material
        setMaterial(material);
        
        gl.glPushMatrix();
        
        // Translate robot to the correct position in the XOY plane
        gl.glTranslated(0, 0, 0.4f);
        // Apply the universal scaling for the robot
        gl.glScaled(scale, scale, scale);

        // Draw the body parts, results in drawing the robot (using the hierarchy example)
        for(BodyPart p : parts) {
            p.Draw();
        }
        
        gl.glPopMatrix();
    }
    
    // Function for setting the correct material for a given robot type
    private void setMaterial(Material material) {
        //Set material determined by given robot type
        gl.glMaterialf(GL_FRONT, GL_SHININESS, material.shininess);
        gl.glMaterialfv(GL_FRONT, GL_DIFFUSE, material.diffuse, 0);
        gl.glMaterialfv(GL_FRONT, GL_SPECULAR, material.specular, 0);
    }
    
    // Function for setting the correct texture (torso and head) for a given robot type
    public void setTorso(Texture torso, Texture head) {
        this.torso = torso;
        this.face = head;
        this.body.texture = this.torso;
        this.head.texture = this.face;
    }
}
