#version 120

uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;
uniform cameraPosition
varying vec4 clipSpace;

void main()
{
    vec2 ndc = (clipSpace.xy / clipSpace.w) / 2.0 + 0.5;

    vec2 reflectionTexCoords = vec2(ndc.x, 1 - ndc.y);
    vec2 refractionTexCoords = vec2(ndc.x, 1 - ndc.y);

    vec4 reflectColor = texture2D(reflectionTexture, reflectionTexCoords);
    vec4 refractColor = texture2D(refractionTexture, refractionTexCoords);

    gl_FragColor = mix(reflectColor, refractColor, 0.5);
}