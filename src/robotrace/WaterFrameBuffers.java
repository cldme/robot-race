package robotrace;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class WaterFrameBuffers {

    protected static final int REFLECTION_WIDTH = 800;
    private static final int REFLECTION_HEIGHT = 800;
    
    protected static final int REFRACTION_WIDTH = 800;
    private static final int REFRACTION_HEIGHT = 800;
    
    private boolean initialized;
    
    int[] reflectionFBO;
    int[] reflectionTexture;
    int[] reflectionDepthBuffer;
    int[] refractionFBO;
    int[] refractionTexture;
    int[] refractionDepthBuffer;
            
    
    
    public WaterFrameBuffers () {
    }
    
    public void init(GL2 gl, GlobalState gs) {
        initializeReflectionFBO(gl, gs);
        initializeRefractionFBO(gl, gs);
    }
    
//    public void draw(GL2 gl, GLU glu, GLUT glut) {
//    
//    
//    
//    
//    
//    }
    
    public int getReflectionTexture() {
        return reflectionTexture[0];
    }
    
    public int getRefractionTexture() {
        return refractionTexture[0];
    }
    
    public void unbindCurrentFBO (GL2 gl, GlobalState gs) {
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, 0);
        gl.glViewport(0, 0, gs.w, gs.h);
    }
    
    private int[] createTextureAttachment (GL2 gl, GlobalState gs) {
    
        int[] texture = new int[1];
        gl.glGenTextures(1, texture, 0);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, texture[0]);
        gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, gs.w, gs.h, 0, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, null);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);  
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
        gl.glFramebufferTexture2D(GL2.GL_FRAMEBUFFER, GL2.GL_COLOR_ATTACHMENT0, GL2.GL_TEXTURE_2D, texture[0], 0);
        
        return texture;
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
    
    private int[] createFBO (GL2 gl) {
    
        int[] FBO = new int[1];
        gl.glGenFramebuffers(1, FBO, 0);
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, FBO[0]);
        gl.glDrawBuffer(GL2.GL_COLOR_ATTACHMENT0);
    
        return FBO;
    }
    
    public void bindReflectionFrameBuffer (GL2 gl, GlobalState gs) {
        bindFBO(gl, gs, reflectionFBO, REFLECTION_WIDTH, REFLECTION_HEIGHT);
    }
    
    public void bindRefractionFrameBuffer (GL2 gl, GlobalState gs) {
        bindFBO(gl, gs, refractionFBO, REFRACTION_WIDTH, REFRACTION_HEIGHT);
    }
    
    private void bindFBO (GL2 gl, GlobalState gs, int[] FBO, int width, int height) {
        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, FBO[0]);
        gl.glViewport(0, 0, width, height);
    }
    
    private void initializeReflectionFBO (GL2 gl, GlobalState gs) {
        reflectionFBO = createFBO(gl);
        reflectionTexture = createTextureAttachment(gl, gs);
        reflectionDepthBuffer = createDepthBufferAttachment(gl, gs);
        unbindCurrentFBO(gl, gs);
    }
    
    private void initializeRefractionFBO (GL2 gl, GlobalState gs) {
        refractionFBO = createFBO(gl);
        refractionTexture = createTextureAttachment(gl, gs);
        refractionDepthBuffer = createDepthBufferAttachment(gl, gs);
        unbindCurrentFBO(gl, gs);
    }
    
    public boolean setInitialize () {
        return initialized = !initialized;
    }
    
    public boolean isInitialized () {
        return initialized;
    }
}
