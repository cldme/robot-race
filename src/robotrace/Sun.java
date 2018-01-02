package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import java.nio.FloatBuffer;

public class Sun {
    
    int[] FBO;
    int[] sunTexture;
    int[] depthBuffer;
    int[] depthTexture;
    private boolean initialized;
    Vector position;
    float time;
    
    public Sun (Vector position) {
        
        this.position = position;
    
    }
    
    void init(GL2 gl, GlobalState gs) {
          initializeSunFBO(gl, gs);
        
    }
    
    void move() {
        time+=0.04f;
        position.x = 1000 * Math.cos(time);
        position.y = 1000 * Math.sin(time);
        position.z = 50;
    }
    
    public void draw(GL2 gl, GLU glu, GLUT glut) {
        gl.glPushMatrix();
        
        gl.glTranslatef((float)position.x(), (float)position.y(), (float)position.z());
        //gl.glRotatef(-23, 0, 1, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, new float[] {(float)position.x(), (float)position.y(), (float)position.z()}, 0);
        
        glut.glutSolidSphere(10,24,12);
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
    
        FBO = createFBO(gl);
        sunTexture = createTextureAttachment(gl, gs);
        depthTexture = createDepthTextureAttachment(gl, gs);
        depthBuffer = createDepthTextureAttachment(gl, gs);
        unbindCurrentFBO(gl, gs);
    }
    
    public void unbindCurrentFBO (GL2 gl, GlobalState gs) {
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, 0);
        gl.glViewport(0, 0, gs.w, gs.h);
    } // done
    
    public void bindSunFBO (GL2 gl) {
        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, FBO[0]);
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
    
    public boolean setInitialize () {
    
        return initialized = !initialized;
    }
    
    public boolean isInitialized () {
    
        return initialized;
    }
    
}
