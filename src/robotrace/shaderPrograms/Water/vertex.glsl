#version 120

varying vec4 clipSpace;

void main()
{
    clipSpace = gl_ModelViewProjectionMatrix * gl_Vertex;
    gl_TexCoord[0] = gl_MultiTexCoord0;
    gl_Position    = clipSpace;
    gl_FrontColor = gl_Color;
}
