package robotrace;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import java.nio.IntBuffer;

public class WaterFrameBuffers {

    public GlobalState gs;
    
    protected static final int REFLECTION_WIDTH = 320;
    private static final int REFLECTION_HEIGHT = 180;
     
    protected static final int REFRACTION_WIDTH = 1280;
    private static final int REFRACTION_HEIGHT = 720;
 
    IntBuffer reflectionFrameBuffer = IntBuffer.allocate(1);
    private int reflectionTexture;
//    private int reflectionDepthBuffer;
     
//    private int refractionFrameBuffer;
//    private int refractionTexture;
//    private int refractionDepthTexture;

    public void init() {
    
    
    }
    
    public WaterFrameBuffers(GlobalState gs, GL2 gl) {//call when loading the game
        this.gs = gs;
        //initialiseReflectionFrameBuffer();
        
        unbindCurrentFrameBuffer(gs, gl);
        //initialiseRefractionFrameBuffer();
    }
 
    public void cleanUp(GL2 gl) {//call when closing the game
        gl.glDeleteFramebuffers(1, reflectionFrameBuffer);
//        GL11.glDeleteTextures(reflectionTexture);
//        GL30.glDeleteRenderbuffers(reflectionDepthBuffer);
//        GL30.glDeleteFramebuffers(refractionFrameBuffer);
//        GL11.glDeleteTextures(refractionTexture);
//        GL11.glDeleteTextures(refractionDepthTexture);

    }
 
    public void bindReflectionFrameBuffer(GL2 gl) {//call before rendering to this FBO
        bindFrameBuffer(reflectionFrameBuffer,REFLECTION_WIDTH,REFLECTION_HEIGHT, gl);
    }
     
//    public void bindRefractionFrameBuffer() {//call before rendering to this FBO
//        bindFrameBuffer(refractionFrameBuffer,REFRACTION_WIDTH,REFRACTION_HEIGHT);
//    }
     
    public void unbindCurrentFrameBuffer(GlobalState gs, GL2 gl) {//call to switch to default frame buffer
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, 0);
        gl.glViewport(0, 0, gs.w, gs.h);
    }
 
    public int getReflectionTexture() {//get the resulting texture
        return reflectionTexture;
    }
     
//    public int getRefractionTexture() {//get the resulting texture
//        return refractionTexture;
//    }
     
//    public int getRefractionDepthTexture(){//get the resulting depth texture
//        return refractionDepthTexture;
//    }
 
    private void initialiseReflectionFrameBuffer(GL2 gl) {
        createFrameBuffer(gl);
        reflectionTexture = createTextureAttachment(REFLECTION_WIDTH,REFLECTION_HEIGHT, gl);
        //reflectionDepthBuffer = createDepthBufferAttachment(REFLECTION_WIDTH,REFLECTION_HEIGHT);
        unbindCurrentFrameBuffer(gs, gl);
    }
     
//    private void initialiseRefractionFrameBuffer() {
//        refractionFrameBuffer = createFrameBuffer();
//        refractionTexture = createTextureAttachment(REFRACTION_WIDTH,REFRACTION_HEIGHT);
//        //refractionDepthTexture = createDepthTextureAttachment(REFRACTION_WIDTH,REFRACTION_HEIGHT);
//        unbindCurrentFrameBuffer();
//    }
     
    private void bindFrameBuffer(IntBuffer frameBuffer, int width, int height, GL2 gl){
        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);//To make sure the texture isn't bound
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, frameBuffer.get(0));
        gl.glViewport(0, 0, width, height);
    }
 
    private void createFrameBuffer(GL2 gl) {
        
        IntBuffer frameBuffer = IntBuffer.allocate(1);
        gl.glGenBuffers(1, frameBuffer);
        int frameBufferName = frameBuffer.get(0);
        //generate name for frame buffer
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, frameBufferName);
        
        //create the framebuffer
        gl.glDrawBuffer(GL2.GL_COLOR_ATTACHMENT0);
        //indicate that we will always render to color attachment 0
    }
 
    private int createTextureAttachment(int width, int height, GL2 gl) {
        int[] texture = new int[1];
        gl.glGenTextures(1, texture, 0);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, texture[0]);
        gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGB, width, height,0, GL2.GL_RGB, GL2.GL_UNSIGNED_BYTE, null);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glFramebufferTexture2D(GL2.GL_FRAMEBUFFER, GL2.GL_COLOR_ATTACHMENT0, GL2.GL_TEXTURE_2D, texture[0], 0);
        return texture[0];
    }
     
//    private int createDepthTextureAttachment(int width, int height){
//        int texture = GL11.glGenTextures();
//        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
//        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL14.GL_DEPTH_COMPONENT32, width, height,
//                0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (ByteBuffer) null);
//        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
//        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
//        GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT,
//                texture, 0);
//        return texture;
//    }
// 
//    private int createDepthBufferAttachment(int width, int height) {
//        int depthBuffer = GL30.glGenRenderbuffers();
//        GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthBuffer);
//        GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL11.GL_DEPTH_COMPONENT, width,
//                height);
//        GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT,
//                GL30.GL_RENDERBUFFER, depthBuffer);
//        return depthBuffer;
//    }
    
}
