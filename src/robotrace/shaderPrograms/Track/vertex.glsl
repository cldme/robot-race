#version 120

varying vec3 N;
varying vec3 P;

void main()
{
    N = normalize(gl_NormalMatrix * gl_Normal);
    vec3 P = vec3(gl_ModelViewMatrix * gl_Vertex);
    gl_Position    = gl_ModelViewProjectionMatrix * gl_Vertex;      // model view transform
    gl_FrontColor = gl_Color;
}
