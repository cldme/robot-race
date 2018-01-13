package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class CelestialBodies {
    
    int[] sunFBO;
    int[] sunTexture;
    int[] sunDepthBuffer;
    int[] sunDepthTexture;
    
    int[] moonFBO;
    int[] moonTexture;
    int[] moonDepthBuffer;
    int[] moonDepthTexture;
    
    private boolean initialized;
    Vector positionSun;
    Vector positionMoon;
    float time;
    int dayTimeLength;
    Vector rotationAxis = new Vector(0, 0.30901699437, -0.95105651629);
    Vector a = new Vector(0, 0.95105651629, 0.30901699437).normalized();
    Vector b = a.cross(rotationAxis).normalized();
    
    public CelestialBodies (Vector posSun, Vector posMoon, int dayTimeLength) {
        
        this.positionSun = posSun;
        this.positionMoon = posMoon;
        this.dayTimeLength = dayTimeLength;
    }
    
    void init(GL2 gl, GlobalState gs) {
          initializeSunFBO(gl, gs);
          initializeMoonFBO(gl, gs);
    }
    
    void moveSun(float time) {
        float timeScaled =((time*2*(float)Math.PI)/dayTimeLength)-(float)Math.PI/2;
        positionSun.x = 100 * (Math.sin(timeScaled) * a.x() - Math.cos(timeScaled) * b.x());
        positionSun.y = 100 * (Math.sin(timeScaled) * a.y() - Math.cos(timeScaled) * b.y());
        positionSun.z = 100 * (Math.sin(timeScaled) * a.z() - Math.cos(timeScaled) * b.z());
    }
    
    void moveMoon(float time) {
        float timeScaled =((time*2*(float)Math.PI)/dayTimeLength)-(float)Math.PI/2;
        positionMoon.x = 100 * (-Math.sin(timeScaled) * a.x() + Math.cos(timeScaled) * b.x());
        positionMoon.y = 100 * (-Math.sin(timeScaled) * a.y() + Math.cos(timeScaled) * b.y());
        positionMoon.z = 100 * (-Math.sin(timeScaled) * a.z() + Math.cos(timeScaled) * b.z());
    }
    
    void drawMoonQuad (GL2 gl, GlobalState gs) {
    
        float modelview[] = new float[16];

        gl.glPushMatrix();
        
        gl.glTranslatef((float)positionMoon.x(), (float)positionMoon.y(), (float)positionMoon.z());
        
        gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX , modelview, 0);

        modelview[0] = 1.0f;
        modelview[1] = 0;     
        modelview[2] = 0;
        modelview[4] = 0;
        modelview[5] = 1.0f;     
        modelview[6] = 0;
        modelview[8] = 0;
        modelview[9] = 0;   
        modelview[10] = 1.0f;
        
        gl.glLoadMatrixf(modelview, 0);
        
        gl.glBegin(GL2.GL_QUADS);
    
        gl.glTexCoord2i(1, 1);
        gl.glVertex3f(3, 3, 0);
        gl.glTexCoord2i(1, 0);
        gl.glVertex3f(3, -3, 0);
        gl.glTexCoord2i(0, 0);
        gl.glVertex3f(-3, -3, 0);
        gl.glTexCoord2i(0, 1);
        gl.glVertex3f(-3, 3, 0);
        
        gl.glEnd();
    
        gl.glPopMatrix();
    }
    
    public void drawSun(GL2 gl, GLU glu, GLUT glut) {
        
        
        gl.glPushMatrix();

        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, new float[] {(float)positionSun.x(), (float)positionSun.y(), (float)positionSun.z()}, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, new float[]{1f,1f,1f,1f}, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, new float[]{1f,1f,0.6f,1f}, 0);
        gl.glPopMatrix();

        gl.glTranslatef((float)positionSun.x(), (float)positionSun.y(), (float)positionSun.z());

        
        
        gl.glColor3f(0.7f,0.7f,0.7f);
        glut.glutSolidSphere(4,24,12);
        gl.glPopMatrix();

    }
    
    public void drawMoon(GL2 gl, GLU glu, GLUT glut) {
        gl.glPushMatrix();

        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, new float[] {(float)positionMoon.x(), (float)positionMoon.y(), (float)positionMoon.z()}, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, new float[]{0f,0f,1f,1f}, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, new float[]{0.6f,0.6f,1f,1f}, 0);
        
        
        gl.glPopMatrix();
        
        
        gl.glTranslatef((float)positionMoon.x(), (float)positionMoon.y(), (float)positionMoon.z());

        //gl.glColor3f(0.5f, 0.5f ,1);
        gl.glColor3f(0.4f,0.4f,0.4f);
        glut.glutSolidSphere(2,24,12);
        gl.glPopMatrix();

    }
    
    
    private int[] createFBO (GL2 gl) {
    
        int[] FBO = new int[1];
        gl.glGenFramebuffers(1, FBO, 0);
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, FBO[0]);
        gl.glDrawBuffer(GL2.GL_COLOR_ATTACHMENT0);
        
        return FBO;
    
    }
    
    private int[] createTextureAttachment (GL2 gl, GlobalState gs) {
    
        int[] sunTex = new int[1];
        gl.glGenTextures(1, sunTex, 0);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, sunTex[0]);
        gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, gs.w, gs.h, 0, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, null);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);  
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
        gl.glFramebufferTexture2D(GL2.GL_FRAMEBUFFER, GL2.GL_COLOR_ATTACHMENT0, GL2.GL_TEXTURE_2D, sunTex[0], 0);
        
        return sunTex;
    }
    
    private int[] createDepthTextureAttachment (GL2 gl, GlobalState gs) {
    
        int[] depthTex = new int[1];
        gl.glGenTextures(1, depthTex, 0);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, depthTex[0]);
        gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_DEPTH_COMPONENT32, gs.w, gs.h, 0, GL2.GL_DEPTH_COMPONENT, GL2.GL_FLOAT, null);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);  
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glFramebufferTexture2D(GL2.GL_FRAMEBUFFER, GL2.GL_DEPTH_ATTACHMENT, GL2.GL_TEXTURE_2D, depthTex[0], 0);
        
        return depthTex;
    }
    
    private int[] createDepthBufferAttachment (GL2 gl, GlobalState gs) {
    
        int[] depthBuf = new int[1];
        gl.glGenRenderbuffers(1, depthBuf, 0);
        gl.glBindRenderbuffer(GL2.GL_RENDERBUFFER, depthBuf[0]);
        gl.glRenderbufferStorage(GL2.GL_RENDERBUFFER, GL2.GL_DEPTH_COMPONENT, gs.w, gs.h);
        gl.glFramebufferRenderbuffer(GL2.GL_FRAMEBUFFER, GL2.GL_DEPTH_ATTACHMENT, GL2.GL_RENDERBUFFER, depthBuf[0]);
        
        return depthBuf;
    }
    
    public void initializeSunFBO (GL2 gl, GlobalState gs) {
    
        sunFBO = createFBO(gl);
        sunTexture = createTextureAttachment(gl, gs);
        sunDepthTexture = createDepthTextureAttachment(gl, gs);
        sunDepthBuffer = createDepthTextureAttachment(gl, gs);
        unbindCurrentFBO(gl, gs);
    }
    
    public void initializeMoonFBO (GL2 gl, GlobalState gs) {
    
        moonFBO = createFBO(gl);
        moonTexture = createTextureAttachment(gl, gs);
        moonDepthTexture = createDepthTextureAttachment(gl, gs);
        moonDepthBuffer = createDepthTextureAttachment(gl, gs);
        unbindCurrentFBO(gl, gs);
    }
    
    
    
    public void unbindCurrentFBO (GL2 gl, GlobalState gs) {
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, 0);
        gl.glViewport(0, 0, gs.w, gs.h);
    }
    
    public void bindSunFBO (GL2 gl) {
        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, sunFBO[0]);
        gl.glDrawBuffer(GL2.GL_COLOR_ATTACHMENT0);
    }
    
    public void bindMoonFBO (GL2 gl) {
        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, moonFBO[0]);
        gl.glDrawBuffer(GL2.GL_COLOR_ATTACHMENT0);
    }
    
    public void renderFSQ (GL2 gl) {
        gl.glMatrixMode (GL2.GL_MODELVIEW);
        gl.glPushMatrix ();
        gl.glLoadIdentity ();
        gl.glMatrixMode (GL2.GL_PROJECTION);
        gl.glPushMatrix ();
        gl.glLoadIdentity ();
        gl.glBegin (GL2.GL_QUADS);
        gl.glTexCoord2i(0, 0);
        gl.glVertex3i (-1, -1, -1);
        gl.glTexCoord2i(1, 0);
        gl.glVertex3i (1, -1, -1);
        gl.glTexCoord2i(1, 1);
        gl.glVertex3i (1, 1, -1);
        gl.glTexCoord2i(0, 1);
        gl.glVertex3i (-1, 1, -1);
        gl.glEnd ();
        gl.glPopMatrix ();
        gl.glMatrixMode (GL2.GL_MODELVIEW);
        gl.glPopMatrix ();
    }
    
    public int getSunTexture () {
    
        return sunTexture[0];
    }
    
    public int getMoonTexture () {
    
        return moonTexture[0];
    }
    
    public boolean setInitialize () {
    
        return initialized = !initialized;
    }
    
    public boolean isInitialized () {
    
        return initialized;
    }
    
}
