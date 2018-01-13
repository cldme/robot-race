#version 120

uniform sampler2D moonTex;


void main(void)
{

    gl_FragColor = texture2D(moonTex, gl_TexCoord[0].st);

}
