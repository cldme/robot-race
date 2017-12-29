#version 120

varying vec4 clipSpace;
uniform mat4 projMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
void main()
{
    clipSpace = projMatrix * viewMatrix * gl_Vertex;
    gl_FrontColor   = gl_Color;
    gl_TexCoord[0] = gl_MultiTexCoord0;
    gl_Position    = gl_ModelViewProjectionMatrix * gl_Vertex;      // model view transform
}
