#version 120

uniform vec3 cameraPosition;
uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;
varying vec3 toCameraVector;
varying vec4 clipSpace;


void main()
{

    float refractiveFactor = dot(toCameraVector, vec3(0,0,1));

    vec2 ndc = (clipSpace.xy / clipSpace.w) / 2.0 + 0.5;

    vec2 reflectionTexCoords = vec2(ndc.x, 1 - ndc.y);
    vec2 refractionTexCoords = vec2(ndc.x, ndc.y);

    vec4 reflectColor = texture2D(reflectionTexture, reflectionTexCoords);
    vec4 refractColor = texture2D(refractionTexture, refractionTexCoords);

    vec4 outColor = mix(reflectColor, refractColor, 1 - refractiveFactor);
    outColor =      mix(outColor, vec4(0.0,0.3,0.5,1), 0.2);

    gl_FragColor = outColor;
}