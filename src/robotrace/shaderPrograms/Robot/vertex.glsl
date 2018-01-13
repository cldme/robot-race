#version 120

varying vec3 N;
varying vec3 P;
varying vec3 V;
uniform vec3 cameraPosition;

void main()
{
    
    N = gl_Normal;
    P = gl_Vertex.xyz;
    V = cameraPosition - P;
    gl_Position    = gl_ModelViewProjectionMatrix * gl_Vertex;      // model view transform
    gl_FrontColor = gl_Color;
    gl_TexCoord[0] = gl_MultiTexCoord0;
}
