#version 120

varying vec4 clipSpace;
uniform vec3 sunPosition;
uniform sampler2D shaftTexture;   // scene texture
uniform vec2 screenSun;

const float SAMPLE_COUNT = 100.0;
const float INV_SAMPLE_COUNT = 0.01;
const float SPREAD = 1.0;
const float DECAY = 0.0001;

void main(void)
{
    vec2 sun = screenSun/800;

    vec2 TexCoord = vec2(gl_TexCoord[0].st);

    vec2 dir = sun - TexCoord;

    dir *= (SPREAD * INV_SAMPLE_COUNT);

    vec4 shaft = vec4(0.0, 0.0, 0.0, 0.0);

    if (length(TexCoord - sun) < 0.005) {

    gl_FragColor = vec4(1,0,0,1);

    } else {

    for (int i = 0; i < SAMPLE_COUNT; i++) {
        float weight = 1.0 - i * DECAY;
    
        shaft += texture2D(shaftTexture, TexCoord) * weight;
        TexCoord += dir;
    }

    gl_FragColor = vec4(vec3(shaft * INV_SAMPLE_COUNT),0.6);
    }
}
