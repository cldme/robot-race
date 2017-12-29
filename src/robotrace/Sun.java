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
//        gl.glEnable(GL2.GL_TEXTURE_2D); // Enable texturing so we can bind our frame buffer texture  
//        gl.glEnable(GL2.GL_DEPTH_TEST);
//        
//        gl.glGenTextures(1, sunTexture, 0);
//        gl.glBindTexture(GL2.GL_TEXTURE_2D, sunTexture[0]);
//        gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGB, gs.w, gs.h, 0, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, 0);
//        //texture = new Texture();
//        gl.glTexParameterf(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);  
//        gl.glTexParameterf(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);  
//        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);  
//        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);  
//    
//        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
//        
//        gl.glGenFramebuffers(1, FBO, 0);
//        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, FBO[0]);
//        gl.glDrawBuffer(GL2.GL_COLOR_ATTACHMENT0);
//        gl.glFramebufferTexture2D(GL2.GL_FRAMEBUFFER, GL2.GL_COLOR_ATTACHMENT0, GL2.GL_TEXTURE_2D, sunTexture[0], 0);
        
    }
    
    void finish(GL2 gl) {
    
    
    
    }
    
    void move() {
        time+=0.006f;
        position.x = 15 * Math.cos(time);
        position.y = 15 * Math.sin(time);
    }
    
    public void draw(GL2 gl, GLU glu, GLUT glut) {
        
        gl.glPushMatrix();

        gl.glColor3f(1,1,1);
        
        gl.glRotatef(-22, 0, 1, 0);
        
        gl.glTranslatef((float)position.x, (float)position.y, (float)position.z);
        
        glut.glutSolidSphere(10,24,12);
        
        gl.glPopMatrix();
        
        //gl.glEnable(GL2.GL_BLEND);
        
        //gl.glBlendFunc(GL2.GL_ONE, GL2.GL_ONE);
        //gl.glClearColor(1.0f, 0.0f, 0.0f, 1.0f); // Clear the background of our window to red  
        //gl.glClear(GL2.GL_COLOR_BUFFER_BIT); //Clear the colour buffer (more buffers later on)  
        //gl.glLoadIdentity(); // Load the Identity Matrix to reset our drawing locations  

        //gl.glTranslatef(0.0f, 0.0f, -10.0f);  
        //gl.glBindTexture(GL2.GL_TEXTURE_2D, sunTexture[0]);
//        gl.glBegin(GL2.GL_QUADS);  
//
//        gl.glTexCoord2f(0.0f, 0.0f);  
//        gl.glVertex3f(-1.0f, -1.0f, 0.0f); // The bottom left corner  
//
//        gl.glTexCoord2f(0.0f, 1.0f);  
//        gl.glVertex3f(-1.0f, 1.0f, 0.0f); // The top left corner  
//
//        gl.glTexCoord2f(1.0f, 1.0f);  
//        gl.glVertex3f(1.0f, 1.0f, 0.0f); // The top right corner  
//
//        gl.glTexCoord2f(1.0f, 0.0f);  
//        gl.glVertex3f(1.0f, -1.0f, 0.0f); // The bottom right corner  
//
//        gl.glEnd();  

//        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
//        
//        
//        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, FBO[0]); // Bind our frame buffer for rendering  
//        gl.glPushAttrib(GL2.GL_VIEWPORT_BIT | GL2.GL_ENABLE_BIT);
//        
//        gl.glViewport(0, 0, gs.w, gs.h); // Set the size of the frame buffer view port  
//  
//        //gl.glClearColor (0.0f, 0.0f, 0.0f, 0.0f); // Set the clear colour  
//        //gl.glClear (GL2.GL_COLOR_BUFFER_BIT); // Clear the depth and colour buffers  
//  
//        //gl.glLoadIdentity();  // Reset the modelview matrix  
//        gl.glColor3f(1,1,1);
//        gl.glPushMatrix();
//        gl.glTranslatef((float)position.x(), (float)position.y(), (float)position.z());
//        
//        glut.glutSolidSphere(5, 24, 12);
//        gl.glPopMatrix();
//        
//        gl.glPopAttrib();
//        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, 0); // Unbind our texture
        

    }
    
    
    private void createFBO (GL2 gl) {
    
        FBO = new int[1];
        gl.glGenFramebuffers(1, FBO, 0);
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, FBO[0]);
        gl.glDrawBuffer(GL2.GL_COLOR_ATTACHMENT0);
    
    } // done
    
    private void createTextureAttachment (GL2 gl, GlobalState gs) {
    
        sunTexture = new int[1];
        gl.glGenTextures(1, sunTexture, 0);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, sunTexture[0]);
        gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, gs.w, gs.h, 0, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, null);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);  
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
        gl.glFramebufferTexture2D(GL2.GL_FRAMEBUFFER, GL2.GL_COLOR_ATTACHMENT0, GL2.GL_TEXTURE_2D, sunTexture[0], 0);
    } // done
    
    private void createDepthTextureAttachment (GL2 gl, GlobalState gs) {
    
        depthTexture = new int[1];
        gl.glGenTextures(1, depthTexture, 0);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, depthTexture[0]);
        gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_DEPTH_COMPONENT32, gs.w, gs.h, 0, GL2.GL_DEPTH_COMPONENT, GL2.GL_FLOAT, null);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);  
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glFramebufferTexture2D(GL2.GL_FRAMEBUFFER, GL2.GL_DEPTH_ATTACHMENT, GL2.GL_TEXTURE_2D, depthTexture[0], 0);
    } // done
    
    private void createDepthBufferAttachment (GL2 gl, GlobalState gs) {
    
        depthBuffer = new int[1];
        gl.glGenRenderbuffers(1, depthBuffer, 0);
        gl.glBindRenderbuffer(GL2.GL_RENDERBUFFER, depthBuffer[0]);
        gl.glRenderbufferStorage(GL2.GL_RENDERBUFFER, GL2.GL_DEPTH_COMPONENT, gs.w, gs.h);
        gl.glFramebufferRenderbuffer(GL2.GL_FRAMEBUFFER, GL2.GL_DEPTH_ATTACHMENT, GL2.GL_RENDERBUFFER, depthBuffer[0]);
    } // done
    
    public void initializeSunFBO (GL2 gl, GlobalState gs) {
    
        createFBO(gl);
        createDepthBufferAttachment(gl, gs);
        createTextureAttachment(gl, gs);
        createDepthTextureAttachment(gl, gs);
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
    
    public FloatBuffer modelMatrix () {
    
        FloatBuffer buffer = FloatBuffer.allocate(16);
        buffer.put(1).put(0).put(0).put(0);
        buffer.put(0).put(1).put(0).put(0);
        buffer.put(0).put(0).put(1).put(0);
        buffer.put((float)position.x()).put((float)position.x()).put((float)position.x()).put(1);
        
        buffer.flip();
        return buffer;
    }
    
    public boolean setInitialize () {
    
        return initialized = !initialized;
    }
    
    public boolean isInitialized () {
    
        return initialized;
    }
    
}
