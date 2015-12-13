#version 330 core

layout (location = 0) out vec4 color;

in DATA {
	vec2 tc;
} fs_in;

uniform sampler2D tex;
uniform int click = 0;

void main() 
{
	color = texture(tex, fs_in.tc);
	if(color.w < 1.0)
		discard;
		
	if(click == 1)
		color *= 20.0;
	color.w = 1.0;
}