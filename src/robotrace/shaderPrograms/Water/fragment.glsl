#version 120

uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;
uniform sampler2D normalMap;
uniform sampler2D dudvMap;
uniform float waveTime;
varying vec3 toCameraVector;
varying vec4 clipSpace;
varying vec3 fromLightVector;

const float tiling = 10.0;
const float waveStrength = 0.015;

const float shineDamper = 100.0;
const float reflectivity = 2;

void main()
{
    vec2 texCoords = gl_TexCoord[0].st * tiling;

    vec3 viewVector = normalize(toCameraVector);

    float refractiveFactor = dot(viewVector, vec3(0,0,1));
    refractiveFactor = pow(refractiveFactor, 1);

    vec2 ndc = (clipSpace.xy / clipSpace.w) / 2.0 + 0.5;

    vec2 reflectionTexCoords = vec2(ndc.x, -ndc.y);
    vec2 refractionTexCoords = vec2(ndc.x, ndc.y);

    vec2 distortedTexCoords = texture2D(dudvMap, vec2(texCoords.x + waveTime, texCoords.y)).rg * 0.1;
    distortedTexCoords = texCoords + vec2(distortedTexCoords.x, distortedTexCoords.y + waveTime);
    vec2 totalDistortion = (texture2D(dudvMap, distortedTexCoords).rg * 2.0 - 1.0) * waveStrength;

    reflectionTexCoords += totalDistortion;
    reflectionTexCoords.x = clamp(reflectionTexCoords.x, 0.001, 0.999);
    reflectionTexCoords.y = clamp(reflectionTexCoords.y, -0.999, -0.001);

    refractionTexCoords += totalDistortion;
    refractionTexCoords = clamp(refractionTexCoords, 0.001, 0.999);

    vec4 reflectColor = texture2D(reflectionTexture, reflectionTexCoords);
    vec4 refractColor = texture2D(refractionTexture, refractionTexCoords);

    vec4 normalMapColor = texture2D(normalMap, distortedTexCoords);
    vec3 normal = vec3(normalMapColor.r * 2.0 - 1.0, normalMapColor.b, normalMapColor.g * 2.0 - 1.0);
    normal = normalize(normal);

    vec3 reflectedLight = reflect(normalize(-fromLightVector), normal);
    float specular = max(dot(reflectedLight, viewVector), 0.0);
    specular = pow(specular, shineDamper);
    vec3 specularHighlights = vec3(1,1,1) * specular * reflectivity;

    vec4 outColor = mix(reflectColor, refractColor, refractiveFactor);

    outColor =      mix(outColor, vec4(0.0,0.3,0.5,1), 0.2) + vec4(specularHighlights, 0.0);

    gl_FragColor = outColor;
}