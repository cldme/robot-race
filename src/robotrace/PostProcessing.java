package robotrace;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

public class PostProcessing {
    
    int[] FBO;
    int[] toBePostProcessed;
    int[] postProcessDepthTexture;
    int[] postProcessDepthBuffer;
    private boolean initialized;

    
    public PostProcessing () {
    }
    
    void init(GL2 gl, GlobalState gs) {
          initializeSunFBO(gl, gs);
    }
    
    private int[] createFBO (GL2 gl) {
    
        int[] FBO = new int[1];
        gl.glGenFramebuffers(1, FBO, 0);
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, FBO[0]);
        gl.glDrawBuffer(GL2.GL_COLOR_ATTACHMENT0);
    
        return FBO;
        
    }
    
    private int[] createTextureAttachment (GL2 gl, GlobalState gs) {
    
        int[] tobePostProcessed = new int[1];
        gl.glGenTextures(1, tobePostProcessed, 0);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, tobePostProcessed[0]);
        gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, gs.w, gs.h, 0, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, null);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);  
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
        gl.glFramebufferTexture2D(GL2.GL_FRAMEBUFFER, GL2.GL_COLOR_ATTACHMENT0, GL2.GL_TEXTURE_2D, tobePostProcessed[0], 0);
        
        return tobePostProcessed;
    }
    
        private int[] createDepthTextureAttachment (GL2 gl, GlobalState gs) {
    
        int[] depthTexture = new int[1];
        gl.glGenTextures(1, depthTexture, 0);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, depthTexture[0]);
        gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_DEPTH_COMPONENT32, gs.w, gs.h, 0, GL2.GL_DEPTH_COMPONENT, GL2.GL_FLOAT, null);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);  
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glFramebufferTexture2D(GL2.GL_FRAMEBUFFER, GL2.GL_DEPTH_ATTACHMENT, GL2.GL_TEXTURE_2D, depthTexture[0], 0);
        
        return depthTexture;
    }
    
    private int[] createDepthBufferAttachment (GL2 gl, GlobalState gs) {
    
        int[] depthBuffer = new int[1];
        gl.glGenRenderbuffers(1, depthBuffer, 0);
        gl.glBindRenderbuffer(GL2.GL_RENDERBUFFER, depthBuffer[0]);
        gl.glRenderbufferStorage(GL2.GL_RENDERBUFFER, GL2.GL_DEPTH_COMPONENT, gs.w, gs.h);
        gl.glFramebufferRenderbuffer(GL2.GL_FRAMEBUFFER, GL2.GL_DEPTH_ATTACHMENT, GL2.GL_RENDERBUFFER, depthBuffer[0]);
        
        return depthBuffer;
    }

    
    public void initializeSunFBO (GL2 gl, GlobalState gs) {
    
        FBO = createFBO(gl);
        toBePostProcessed = createTextureAttachment(gl, gs);
        postProcessDepthTexture = createDepthTextureAttachment(gl, gs);
        postProcessDepthBuffer = createDepthBufferAttachment(gl, gs);
        unbindCurrentFBO(gl, gs);
    }
    
    public void unbindCurrentFBO (GL2 gl, GlobalState gs) {
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, 0);
        gl.glViewport(0, 0, gs.w, gs.h);
    } // done
    
    public void bindPostProcessFBO (GL2 gl) {
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
    
    public int getToBeProcessed () {
    
        return toBePostProcessed[0];
    }
    
    public boolean setInitialize () {
    
        return initialized = !initialized;
    }
    
    public boolean isInitialized () {
    
        return initialized;
    }
    
    
    
}
