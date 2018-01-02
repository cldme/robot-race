#version 120

uniform sampler2D toBeProcessed;
uniform sampler2D shaftTexture;   // scene texture


void main(void)
{
    vec2 texCoords = gl_TexCoord[0].st;

    gl_FragColor = texture2D(toBeProcessed, texCoords);
}
