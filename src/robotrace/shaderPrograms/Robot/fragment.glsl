#version 120

uniform sampler2D robot;

void main()
{
    gl_FragColor = texture2D(robot, gl_TexCoord[0].st);
}