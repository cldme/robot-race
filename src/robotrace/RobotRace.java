package robotrace;

import static com.jogamp.opengl.GL2.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import static robotrace.ShaderPrograms.*;
import static robotrace.Textures.*;

/**
 * Handles all of the RobotRace graphics functionality,
 * which should be extended per the assignment.
 * 
 * OpenGL functionality:
 * - Basic commands are called via the gl object;
 * - Utility commands are called via the glu and
 *   glut objects;
 * 
 * GlobalState:
 * The gs object contains the GlobalState as described
 * in the assignment:
 * - The camera viewpoint angles, phi and theta, are
 *   changed interactively by holding the left mouse
 *   button and dragging;
 * - The camera view width, vWidth, is changed
 *   interactively by holding the right mouse button
 *   and dragging upwards or downwards; (Not required in this assignment)
 * - The center point can be moved up and down by
 *   pressing the 'q' and 'z' keys, forwards and
 *   backwards with the 'w' and 's' keys, and
 *   left and right with the 'a' and 'd' keys;
 * - Other settings are changed via the menus
 *   at the top of the screen.
 * 
 * Textures:
 * Place your "track.jpg", "brick.jpg", "head.jpg",
 * and "torso.jpg" files in the folder textures. 
 * These will then be loaded as the texture
 * objects track, bricks, head, and torso respectively.
 * Be aware, these objects are already defined and
 * cannot be used for other purposes. The texture
 * objects can be used as follows:
 * 
 * gl.glColor3f(1f, 1f, 1f);
 * Textures.track.bind(gl);
 * gl.glBegin(GL_QUADS);
 * gl.glTexCoord2d(0, 0);
 * gl.glVertex3d(0, 0, 0);
 * gl.glTexCoord2d(1, 0);
 * gl.glVertex3d(1, 0, 0);
 * gl.glTexCoord2d(1, 1);
 * gl.glVertex3d(1, 1, 0);
 * gl.glTexCoord2d(0, 1);
 * gl.glVertex3d(0, 1, 0);
 * gl.glEnd(); 
 * 
 * Note that it is hard or impossible to texture
 * objects drawn with GLUT. Either define the
 * primitives of the object yourself (as seen
 * above) or add additional textured primitives
 * to the GLUT object.
 */
public class RobotRace extends Base {
    
    /** Array of the four robots. */
    private final Robot[] robots;
    
    /** Instance of the camera. */
    private final Camera camera;
    
    /** Instance of the race track. */
    private final RaceTrack[] raceTracks;
    
    /** Instance of the terrain. */
    private final Terrain terrain;
    
    private final Sun sun;
    FloatBuffer viewM = FloatBuffer.allocate(16);
    FloatBuffer projM = FloatBuffer.allocate(16);
    IntBuffer viewP = IntBuffer.allocate(4);

    /**
     * Constructs this robot race by initializing robots,
     * camera, track, and terrain.
     */
    public RobotRace() {
        
        // Create a new array of four robots
        robots = new Robot[4];
        
        // Initialize robot 0
        robots[0] = new Robot(Material.GOLD, Material.GOLD, torso, head);
        
//        // Initialize robot 1
//        robots[1] = new Robot(Material.SILVER, Material.SILVER, torso, head);
//        
//        // Initialize robot 2
//        robots[2] = new Robot(Material.WOOD, Material.WOOD, torso, head);
//
//        // Initialize robot 3
//        robots[3] = new Robot(Material.ORANGE, Material.ORANGE, torso, head);
        
        // Initialize the camera
        camera = new Camera();
        
        // Initialize the race tracks
        raceTracks = new RaceTrack[2];
        
        // Track 1
        raceTracks[0] = new ParametricTrack();
        
        // Track 2
        float g = 3.5f;
        raceTracks[1] = new BezierTrack(
                
            new Vector[] { new Vector(16f, -2.379993f, 0f),
                           new Vector(17.03568f,6.612997f,-0.4572847f),
                           new Vector(15.39956f,14.51239f,0f),
                           new Vector(10.0101f,16.87626f,0f),
                           new Vector(5.178312f,18.99553f,0f),
                           new Vector(0.7413831f,15.13234f,0f),
                           new Vector(-1.365651f,12.43286f,0f),
                           new Vector(-4.521659f,8.38945f,0f),
                           new Vector(-9.067448f,0.2013298f,0f),
                           new Vector(0.4023554f,-1.109114f,0f),
                           new Vector(4.620424f,-1.692816f,0f),
                           new Vector(9.506009f,2.844918f,0f),
                           new Vector(6.446103f,5.99296f,0f),
                           new Vector(4.939413f,7.543048f,0f),
                           new Vector(1.486183f,9.50538f,-4.580544f),
                           new Vector(-2.880064f,12.11383f,-3.134924f),
                           new Vector(-12.34857f,17.77042f,0f),
                           new Vector(-21.53945f,13.99727f,0f),
                           new Vector(-13.84152f,5.419401f,0f),
                           new Vector(-10.30898f,1.483055f,0f),
                           new Vector(-5.282493f,-3.16907f,4.840358f),
                           new Vector(-11.78699f,-8.081102f,2.397926f),
                           new Vector(-14.41224f,-10.06362f,1.412148f),
                           new Vector(-16.80755f,-16.73213f,2.397926f),
                           new Vector(-10.1092f,-16.73213f,2.397926f),
                           new Vector(-9.10943f,-16.73213f,2.397926f),
                           new Vector(-8.61202f,-16.73213f,3.641369f),
                           new Vector(-5.448234f,-16.73213f,7.430427f),
                           new Vector(-4.807303f,-16.73213f,8.198026f),
                           new Vector(0.1789123f,-16.73213f,8.577466f),
                           new Vector(1.178912f,-16.73213f,7.430427f),
                           new Vector(1.836054f,-16.73213f,6.67666f),
                           new Vector(2.342921f,-16.73213f,0.2505693f),
                           new Vector(6.382141f,-16.73213f,0.2505693f),
                           new Vector(9.877653f,-16.73213f,0.2505693f),
                           new Vector(12.81498f,-15.94569f,0.2505693f),
                           new Vector(14.32221f,-12.64111f,0.2505693f),
                           new Vector(15.11011f,-10.91366f,0.2505693f),
                           new Vector(15.4325f,-7.307707f,0.2505693f),
                           new Vector(16f,-2.379993f,0f)
            }              
        );
        
        // Initialize the terrain
        
        terrain = new Terrain(TerrainUtility.getSubdivisions(), TerrainUtility.getPatches());
        
        sun = new Sun(new Vector(10,0,0));
        
        
    }
    
    /**
     * Called upon the start of the application.
     * Primarily used to configure OpenGL.
     */
    @Override
    public void initialize() {
        
        // Enable blending.
        gl.glEnable(GL_BLEND);
        gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                
        // Enable depth testing.
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LESS);

        // Enable face culling for improved performance
        //gl.glCullFace(GL_BACK);
        //gl.glEnable(GL_CULL_FACE);
        
	// Normalize normals.
        gl.glEnable(GL_NORMALIZE);
        
	// Try to load four textures, add more if you like in the Textures class         
        Textures.loadTextures();
        reportError("reading textures");
        
        // Try to load and set up shader programs
        ShaderPrograms.setupShaders(gl, glu);
        reportError("shaderProgram");

    }
   
    /**
     * Configures the viewing transform.
     */
    @Override
    public void setView() {
        
        gl.glEnable(GL_LIGHTING);
        gl.glEnable(GL_LIGHT0);
        
        // Select part of window.
        gl.glViewport(0, 0, gs.w, gs.h);
        gl.glGetIntegerv(GL_VIEWPORT, viewP);
        // Set projection matrix.
        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();
        
        // Set the perspective.
        glu.gluPerspective(45, (float)gs.w / (float)gs.h, 0.1*gs.vDist, 10*gs.vDist);
        
        gl.glGetFloatv(GL_PROJECTION_MATRIX, projM);

        // Set camera.

        gl.glMatrixMode(GL_MODELVIEW);
        
        gl.glLoadIdentity();
        
        //Add light source
        gl.glLightfv(GL_LIGHT0, GL_POSITION, new float[]{0f, 6f, 0f, 0f}, 0);
        gl.glLightfv(GL_LIGHT0, GL_AMBIENT, new float[]{0.2f,0f,0.4f,1f}, 0);
        gl.glLightfv(GL_LIGHT0, GL_DIFFUSE, new float[]{0.6f,0f,0f,1f}, 0);
        gl.glLightfv(GL_LIGHT0, GL_SPECULAR, new float[]{1f,1f,1f,1f}, 0);
        
        // Update the view according to the camera mode and robot of interest.
        // For camera modes 1 to 4, determine which robot to focus on.
        camera.update(gs, robots[0]);
        
        
        glu.gluLookAt(camera.eye.x(),    camera.eye.y(),    camera.eye.z(),
                      camera.center.x(), camera.center.y(), camera.center.z(),
                      camera.up.x(),     camera.up.y(),     camera.up.z());
        gl.glGetFloatv(GL_MODELVIEW_MATRIX, viewM);
    }
    
    /**
     * Draws the entire scene.
     */
    @Override
    public void drawScene() {

        gl.glClear(GL_COLOR_BUFFER_BIT);

        gl.glClear(GL_DEPTH_BUFFER_BIT);
        
        gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        gl.glClearColor(0, 0, 0, 1);
        
        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        
        if (!sun.isInitialized()) {
            sun.init(gl, gs);
            sun.setInitialize();
        }

        sun.bindSunFBO(gl); 
        
        gl.glClear(GL_COLOR_BUFFER_BIT);

        gl.glClear(GL_DEPTH_BUFFER_BIT);
        
        gl.glUseProgram(blackShader.getProgramID());

        reportError("program");

        raceTracks[gs.trackNr].draw(gl, glu, glut);

        terrain.draw(gl, glu, glut);
        reportError("terrain:");

        gl.glUseProgram(defaultShader.getProgramID()); 
        gl.glColor3f(1,1,1);
        
        sun.move();
        sun.draw(gl, glu, glut);
    
        FloatBuffer screen = FloatBuffer.allocate(3);
        
        sun.unbindCurrentFBO(gl, gs);
        
                
        gl.glActiveTexture(GL_TEXTURE0);
        gl.glUseProgram(trackShader.getProgramID());
        int texSampler = gl.glGetUniformLocation(trackShader.getProgramID(), "tex");
        gl.glUniform1i(texSampler, 0);
        raceTracks[gs.trackNr].draw(gl, glu, glut);
        
        
        gl.glUseProgram(terrainShader.getProgramID());
        terrain.draw(gl, glu, glut);
        reportError("terrain:");
        
        
        gl.glUseProgram(sunShader.getProgramID());

        gl.glActiveTexture(GL_TEXTURE1);
        glu.gluProject((float)sun.position.x(), (float)sun.position.y(), (float)sun.position.z(), viewM, projM, viewP, screen);
        int shaft = gl.glGetUniformLocation(sunShader.getProgramID(), "shaftTexture");
        gl.glUniform1i(shaft, 1);
        int sole = gl.glGetUniformLocation(sunShader.getProgramID(), "screenSun");
        gl.glUniform2f(sole, screen.get(0), screen.get(1));
        
        gl.glBindTexture(GL_TEXTURE_2D, sun.getSunTexture());
        
        sun.renderFSQ(gl);
    }
    
    /**
     * Draws the x-axis (red), y-axis (green), z-axis (blue),
     * and origin (yellow).
     */
    public void drawAxisFrame() {

        gl.glColor3f(1f, 0f, 0f);
//        gl.glPushMatrix();
//        gl.glTranslatef((float)camera.center.x(), (float)camera.center.y(), (float)camera.center.z());
//        glut.glutSolidSphere(0.15f, 24, 12);
//        gl.glPopMatrix();
        
        gl.glColor3f(1f, 1f, 0f);
        glut.glutSolidSphere(0.3f, 24, 12);
        gl.glColor3f(1f, 0f, 0f);
        gl.glPushMatrix();
        gl.glRotatef(90f, 0f, 1f, 0f);
        drawArrow();
        gl.glPopMatrix();
        gl.glColor3f(0f, 1f, 0f);
        gl.glPushMatrix();
        gl.glRotatef(-90f, 1f, 0f, 0f);
        drawArrow();
        gl.glPopMatrix();
        gl.glColor3f(0f, 0f, 1f);
        drawArrow();
        
    }
    
    void DrawTrackBounds () {
    
        gl.glBegin(GL_LINES);
        
        gl.glVertex3f(-20, -20, 0);
        gl.glVertex3f(20, -20, 0);
        
        gl.glVertex3f(20, -20, 0);
        gl.glVertex3f(20, 20, 0);
        
        gl.glVertex3f(20, 20, 0);
        gl.glVertex3f(-20, 20, 0);
        
        gl.glVertex3f(-20, 20, 0);
        gl.glVertex3f(-20, -20, 0);
    
        gl.glEnd();
        
    }
    
    /**
     * Draws a single arrow
     */
    public void drawArrow() {
        gl.glPushMatrix();
        glut.glutSolidCylinder(0.05f, 1f, 12, 6);
        gl.glTranslatef(0f, 0f, 1f);
        glut.glutSolidCone(0.2f, 0.5f, 12, 6);
        gl.glPopMatrix();
    }
 
    /**
     * Drawing hierarchy example.
     * 
     * This method draws an "arm" which can be animated using the sliders in the
     * RobotRace interface. The A and B sliders rotate the different joints of
     * the arm, while the C, D and E sliders set the R, G and B components of
     * the color of the arm respectively. 
     * 
     * The way that the "arm" is drawn (by calling {@link #drawSecond()}, which 
     * in turn calls {@link #drawThird()} imposes the following hierarchy:
     * 
     * {@link #drawHierarchy()} -> {@link #drawSecond()} -> {@link #drawThird()}
     */
    private void drawHierarchy() {
        gl.glColor3d(gs.sliderC, gs.sliderD, gs.sliderE);
        gl.glPushMatrix(); 
            gl.glScaled(1, 0.5, 0.5);
            glut.glutSolidCube(1);
            gl.glScaled(1, 2, 2);
            gl.glTranslated(0.5, 0, 0);
            gl.glRotated(gs.sliderA * -90.0, 0, 1, 0);
            drawSecond();
        gl.glPopMatrix();
    }
    
    private void drawSecond() {
        gl.glTranslated(0.5, 0, 0);
        gl.glScaled(1, 0.5, 0.5);
        glut.glutSolidCube(1);
        gl.glScaled(1, 2, 2);
        gl.glTranslated(0.5, 0, 0);
        gl.glRotated(gs.sliderB * -90.0, 0, 1, 0);
        drawThird();
    }
    
    private void drawThird() {
        gl.glTranslated(0.5, 0, 0);
        gl.glScaled(1, 0.5, 0.5);
        glut.glutSolidCube(1);
    }
    
    
    /**
     * Main program execution body, delegates to an instance of
     * the RobotRace implementation.
     */
    public static void main(String args[]) {
        RobotRace robotRace = new RobotRace();
        robotRace.run();
    }
}
