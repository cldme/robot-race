package robotrace;

import static com.jogamp.opengl.GL2.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import static robotrace.ShaderPrograms.*;
import static robotrace.Textures.*;
import java.util.concurrent.TimeUnit;
import java.awt.Color;

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
    
    // Variables for tracking time spent on track
    private double time;
    // Variable oldTime is used to calculate time from last tick
    private double oldTime;
    
    // Array for tracking number of steps done by robots
    private Double[] steps = {0.0, 0.0, 0.0, 0.0};
    // Array for storing robots speed
    private Double[] speed = {250.0, 450.0, 350.0, 150.0};
    // Total number of steps taken by the robots in one lap (can be tweaked)
    private Double N = 10000d;
    
    private final CelestialBodies sun;
    
    private final Water water;
    
    private final WaterFrameBuffers waterFrameBuffers;
    
    private final DayNightCycle dayNightCycle;
    
    TimeManager timeManager = TimeManager.start();
    
    FloatBuffer viewM = FloatBuffer.allocate(16);
    FloatBuffer projM = FloatBuffer.allocate(16);
    IntBuffer viewP = IntBuffer.allocate(4);
    boolean test = false;

    /**
     * Constructs this robot race by initializing robots,
     * camera, track, and terrain.
     */
    public RobotRace() {
        
        // Create a new array of four robots
        robots = new Robot[4];
        
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
        
        terrain = new Terrain(TerrainUtility.getSubdivisions(), TerrainUtility.getPatches());
        
        sun = new CelestialBodies(new Vector(100,0,25), new Vector(100,0,25), 20);
        
        water = new Water(new Vector(0,0,0.5f), 150);
        
        waterFrameBuffers = new WaterFrameBuffers();
        
        dayNightCycle = new DayNightCycle(20);
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
        
        // Initialize robot 0
        robots[0] = new Robot(Material.GOLD, Material.GOLD, torso, head);
        
        // Initialize robot 1
        robots[1] = new Robot(Material.SILVER, Material.SILVER, torso, head);
        
        // Initialize robot 2
        robots[2] = new Robot(Material.WOOD, Material.WOOD, torso, head);

        // Initialize robot 3
        robots[3] = new Robot(Material.ORANGE, Material.ORANGE, torso, head);

    }
   
    /**
     * Configures the viewing transform.
     */
    @Override
    public void setView() {
        
        gl.glEnable(GL_LIGHTING);
        gl.glEnable(GL_LIGHT0);
        gl.glEnable(GL_LIGHT1);
        // Select part of window.
        gl.glViewport(0, 0, gs.w, gs.h);
        gl.glGetIntegerv(GL_VIEWPORT, viewP);
        // Set projection matrix.
        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();
        
        // Set the perspective.
        glu.gluPerspective(45, (float)gs.w / (float)gs.h, 0.1*gs.vDist, 100*gs.vDist);
        
        gl.glGetFloatv(GL_PROJECTION_MATRIX, projM);

        // Set camera.

        gl.glMatrixMode(GL_MODELVIEW);
        
        gl.glLoadIdentity();
        
        camera.update(gs, robots[1]);
        
        // Update the view according to the camera mode and robot of interest.
        // For camera modes 1 to 4, determine which robot to focus on.
        
        //camera.update(gs, robots[gs.]);
        
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

        float dayTime = timeManager.time(TimeUnit.MILLISECONDS)/1000f;
        gl.glClearColor(0,0,0,0);
        gl.glClear(GL_COLOR_BUFFER_BIT);
        gl.glClear(GL_DEPTH_BUFFER_BIT);
        gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);        
        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);        

        if (!waterFrameBuffers.isInitialized()) {
            waterFrameBuffers.init(gl, gs);
            waterFrameBuffers.setInitialize();
        }
        //=========================================WATER REFLECTION=================================================
        waterFrameBuffers.bindReflectionFrameBuffer(gl, gs);
        
        gl.glClear(GL_COLOR_BUFFER_BIT);

        gl.glClear(GL_DEPTH_BUFFER_BIT);
        
        gl.glEnable(GL_CLIP_DISTANCE0);

        camera.invertPitch(gs, water);
        setView();
        
        
        gl.glUseProgram(terrainShader.getProgramID());
        int clipPlane = gl.glGetUniformLocation(terrainShader.getProgramID(), "plane");
        gl.glUniform4f(clipPlane, 0, 0, 1, -water.getHeight());
        int sunPos = gl.glGetUniformLocation(terrainShader.getProgramID(), "sunPosition");
        gl.glUniform3f(sunPos, (float)sun.positionSun.x(), (float)sun.positionSun.y(), (float)sun.positionSun.z());               
        terrain.draw(gl, glu, glut);
        gl.glActiveTexture(GL_TEXTURE0);
        gl.glUseProgram(trackShader.getProgramID());
        int texSampler = gl.glGetUniformLocation(trackShader.getProgramID(), "tex");
        gl.glUniform1i(texSampler, 0);
        raceTracks[gs.trackNr].draw(gl, glu, glut);
        
        gl.glUseProgram(robotShader.getProgramID());
        robotDraw();
        
        gl.glUseProgram(defaultShader.getProgramID());
        gl.glColor3f(1,1,0.8f);
        sun.moveSun(dayTime);
        sun.moveMoon(dayTime);
        sun.drawSun(gl, glu, glut);
        sun.drawMoon(gl, glu, glut);
        camera.invertPitch(gs, water);
        setView();

        gl.glUseProgram(defaultShader.getProgramID());
        dayNightCycle.drawSky(gl, gs, dayTime);
        
        
        
        //=========================================WATER REFRACTION=================================================
        
        waterFrameBuffers.bindRefractionFrameBuffer(gl, gs);
        
        gl.glClear(GL_COLOR_BUFFER_BIT);

        gl.glClear(GL_DEPTH_BUFFER_BIT);
        
        gl.glUseProgram(terrainShader.getProgramID());
        clipPlane = gl.glGetUniformLocation(terrainShader.getProgramID(), "plane");
        gl.glUniform4f(clipPlane, 0, 0, -1, water.getHeight());
        gl.glUniform3f(sunPos, (float)sun.positionSun.x(), (float)sun.positionSun.y(), (float)sun.positionSun.z());
        
        terrain.draw(gl, glu, glut);
        
        gl.glActiveTexture(GL_TEXTURE0);
        gl.glUseProgram(trackShader.getProgramID());
        texSampler = gl.glGetUniformLocation(trackShader.getProgramID(), "tex");
        gl.glUniform1i(texSampler, 0);
        raceTracks[gs.trackNr].draw(gl, glu, glut);
        
        gl.glDisable(GL_CLIP_DISTANCE0);
        waterFrameBuffers.unbindCurrentFBO(gl, gs);
        //=========================================SCENE=================================================
        
        gl.glClear(GL_COLOR_BUFFER_BIT);
        gl.glClear(GL_DEPTH_BUFFER_BIT);
        
        sun.moveSun(dayTime);
        sun.moveMoon(dayTime);

        gl.glUseProgram(defaultShader.getProgramID());
        
        dayNightCycle.drawSky(gl, gs, dayTime);
        
        gl.glUseProgram(moonShader.getProgramID());
        gl.glActiveTexture(GL_TEXTURE0);
        int moonTex = gl.glGetUniformLocation(moonShader.getProgramID(), "moonTex");
        gl.glUniform1i(moonTex, 0);
        Textures.moon.bind(gl);
        sun.drawMoonQuad(gl, gs);
        
        gl.glUseProgram(terrainShader.getProgramID());
        terrain.draw(gl, glu, glut);

        gl.glActiveTexture(GL_TEXTURE0);
        gl.glUseProgram(trackShader.getProgramID());
        gl.glUniform3f(gl.glGetUniformLocation(trackShader.getProgramID(), "cameraPosition"), (float)camera.eye.x(), (float)camera.eye.y(), (float)camera.eye.z());
        texSampler = gl.glGetUniformLocation(trackShader.getProgramID(), "tex");
        gl.glUniform1i(texSampler, 0);
        raceTracks[gs.trackNr].draw(gl, glu, glut);


        gl.glUseProgram(waterShader.getProgramID());

        gl.glUniform3f(gl.glGetUniformLocation(waterShader.getProgramID(), "cameraPosition"), (float)camera.eye.x(), (float)camera.eye.y(), (float)camera.eye.z());
        int reflection = gl.glGetUniformLocation(waterShader.getProgramID(), "reflectionTexture");
        int refraction = gl.glGetUniformLocation(waterShader.getProgramID(), "refractionTexture");
        sunPos = gl.glGetUniformLocation(waterShader.getProgramID(), "sunPosition");
        gl.glUniform3f(sunPos, (float)sun.positionSun.x(), (float)sun.positionSun.y(), (float)sun.positionSun.z());
        gl.glActiveTexture(GL_TEXTURE1);
        gl.glBindTexture(GL_TEXTURE_2D, waterFrameBuffers.getReflectionTexture());
        gl.glUniform1i(reflection, 1);
        gl.glActiveTexture(GL_TEXTURE2);
        gl.glBindTexture(GL_TEXTURE_2D, waterFrameBuffers.getRefractionTexture());
        gl.glUniform1i(refraction, 2);
        gl.glActiveTexture(GL_TEXTURE3);
        int dudvMap = gl.glGetUniformLocation(waterShader.getProgramID(), "dudvMap");
        gl.glUniform1i(dudvMap, 3);
        Textures.dudv.bind(gl);
        Textures.dudv.setTexParameteri(gl, GL_TEXTURE_WRAP_S, GL_REPEAT);
        Textures.dudv.setTexParameteri(gl, GL_TEXTURE_WRAP_T, GL_REPEAT);
        gl.glActiveTexture(GL_TEXTURE4);
        int normalMap = gl.glGetUniformLocation(waterShader.getProgramID(), "normalMap");
        gl.glUniform1i(normalMap, 4);
        Textures.normal.bind(gl);
        Textures.normal.setTexParameteri(gl, GL_TEXTURE_WRAP_S, GL_REPEAT);
        Textures.normal.setTexParameteri(gl, GL_TEXTURE_WRAP_T, GL_REPEAT);
        int waveTime = gl.glGetUniformLocation(waterShader.getProgramID(), "waveTime");
        gl.glUniform1f(waveTime, water.move());
        
        water.draw(gl, glu, glut);
        
        gl.glUseProgram(robotShader.getProgramID());
        robotDraw();
        
        //DrawTrackBounds();
        
        //=========================================CELESTIAL BODIES=================================================
        
        if (!sun.isInitialized()) {
            sun.init(gl, gs);
            sun.setInitialize();
        }
        sun.bindSunFBO(gl); 
        
        gl.glClearColor(0,0,0,0);
        
        gl.glClear(GL_COLOR_BUFFER_BIT);
        gl.glClear(GL_DEPTH_BUFFER_BIT);
        
        gl.glUseProgram(defaultShader.getProgramID()); 
        
        sun.drawSun(gl, glu, glut);
        
        gl.glUseProgram(blackShader.getProgramID());
        reportError("program:");

        raceTracks[gs.trackNr].draw(gl, glu, glut);

        terrain.draw(gl, glu, glut);
        
        reportError("terrain:");
        
        sun.bindMoonFBO(gl); 
        
        gl.glClearColor(0,0,0,0);
        
        gl.glClear(GL_COLOR_BUFFER_BIT);
        gl.glClear(GL_DEPTH_BUFFER_BIT);
        
        gl.glUseProgram(defaultShader.getProgramID()); 
        
        sun.drawMoon(gl, glu, glut);
        
        gl.glUseProgram(blackShader.getProgramID());
        reportError("program:");

        raceTracks[gs.trackNr].draw(gl, glu, glut);

        terrain.draw(gl, glu, glut);
        
        reportError("terrain:");
        
        sun.unbindCurrentFBO(gl, gs);
        
        
        FloatBuffer screenSun = FloatBuffer.allocate(3);
        
        gl.glUseProgram(sunShader.getProgramID());
        
        gl.glActiveTexture(GL_TEXTURE0);
        
        gl.glBindTexture(GL_TEXTURE_2D, sun.getSunTexture());
        int shaft = gl.glGetUniformLocation(sunShader.getProgramID(), "shaftTexture");
        gl.glUniform1i(shaft, 0);
        int viewPortPix = gl.glGetUniformLocation(sunShader.getProgramID(), "WH");
        gl.glUniform2f(viewPortPix, gs.w, gs.h);
        
        glu.gluProject((float)sun.positionSun.x(), (float)sun.positionSun.y(), (float)sun.positionSun.z(), viewM, projM, viewP, screenSun);
        int sole = gl.glGetUniformLocation(sunShader.getProgramID(), "screenSun");
        gl.glUniform2f(sole, screenSun.get(0), screenSun.get(1));
        
        gl.glBlendFunc(GL_ONE, GL_ONE);
        
        gl.glDisable(GL_DEPTH_TEST);
        
        sun.renderFSQ(gl);

        gl.glBindTexture(GL_TEXTURE_2D, sun.getMoonTexture());

        glu.gluProject((float)sun.positionMoon.x(), (float)sun.positionMoon.y(), (float)sun.positionMoon.z(), viewM, projM, viewP, screenSun);
        int luna = gl.glGetUniformLocation(sunShader.getProgramID(), "screenSun");
        gl.glUniform2f(luna, screenSun.get(0), screenSun.get(1));

        sun.renderFSQ(gl);
        
        gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL_DEPTH_TEST);
    }
    
    /**
     * Draws the x-axis (red), y-axis (green), z-axis (blue),
     * and origin (yellow).
     */
    public void drawAxisFrame() {

        gl.glColor3f(1f, 0f, 0f);
        gl.glPushMatrix();
        gl.glTranslatef((float)camera.center.x(), (float)camera.center.y(), (float)camera.center.z());
        glut.glutSolidSphere(0.15f, 24, 12);
        gl.glPopMatrix();
        
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
        
        gl.glVertex3f(-20, -20, 5);
        gl.glVertex3f(20, -20, 5);
        
        gl.glVertex3f(20, -20, 5);
        gl.glVertex3f(20, 20, 5);
        
        gl.glVertex3f(20, 20, 5);
        gl.glVertex3f(-20, 20, 5);
        
        gl.glVertex3f(-20, 20, 5);
        gl.glVertex3f(-20, -20, 5);
    
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
    
    private static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
    
    private void setClearColor(Color c) {
        float[] comps = new float[4];
        comps = c.getComponents(comps);
        gl.glClearColor(comps[0], comps[1], comps[2], comps[3]);
    }
    
    public Vector getCamera () {
    
        return camera.eye;
    }
    
    /**
     * Main program execution body, delegates to an instance of
     * the RobotRace implementation.
     */
    public static void main(String args[]) {
        RobotRace robotRace = new RobotRace();
        robotRace.run();
    }
    
    public void robotDraw() {
    
    //=========================================DRAWING THE ROBOTS (EVERYTHING IN HERE)=================================================
        
        // -------------------- UNDER CONSTRUCTION --------------------
        gl.glUniform3f(gl.glGetUniformLocation(robotShader.getProgramID(), "cameraPosition"), (float)camera.eye.x(), (float)camera.eye.y(), (float)camera.eye.z());
        // Update time variables
        oldTime = time;
        time = gs.tAnim;
                
        // Calculate the positions of the robots on the track
        for (int i = 0; i < 4; i++) {
            robots[i].position = raceTracks[gs.trackNr].getLanePoint(i, (steps[i]) / N);
            robots[i].direction = raceTracks[gs.trackNr].getLaneTangent(i, steps[i] / N);
            //System.out.println(raceTracks[gs.trackNr].getLanePoint(i, steps[i]/N).x);
            
            steps[i] += (time - oldTime) * speed[i];
            //steps[i] = 0.0;
            
            if(round(steps[i]/speed[i], 1) % round((N/speed[i]), 1) == 0) {
                steps[i] = 0.0;
            }
        }
        
        // Start drawing the robots in the scene
        gl.glPushMatrix();
        
        for (int i = 0; i < 4; i++) {
            gl.glPushMatrix();
            gl.glTranslated(robots[i].position.x, robots[i].position.y, robots[i].position.z);
            //double angle = Math.atan2(robots[i].direction.y, robots[i].direction.x);

            // Calculate the dot product between the tangent and the Y axis.
            double dot = robots[i].direction.dot(Vector.Y);

            //Divide by length of y-axis and direction to get cos
            double cosangle = dot / (robots[i].direction.length() * Vector.Y.length());

            //Check if x is negative of possitive     
            double angle;
            if (robots[i].direction.x() >= 0) {
                angle = -Math.acos(cosangle);
            } else {
                angle = Math.acos(cosangle);
            }
            gl.glRotated(Math.toDegrees(angle), 0, 0, 1);

            // Rotate bender to stand perpendicular to lange tangent
            
//            gl.glActiveTexture(GL_TEXTURE0);
//            Textures.head.bind(gl);

            
            robots[i].draw(gl, glu, glut, gs.tAnim);
            
            gl.glPopMatrix();
        }
        
        gl.glPopMatrix();
        
        // -------------------- UNDER CONSTRUCTION --------------------
    
    }
}
