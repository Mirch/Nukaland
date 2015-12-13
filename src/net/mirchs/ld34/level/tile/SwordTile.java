package net.mirchs.ld34.level.tile;

import net.mirchs.dm8.graphics.Texture;
import net.mirchs.dm8.math.Matrix4f;
import net.mirchs.dm8.math.Vector3f;
import net.mirchs.ld34.ShaderContainer;

public class SwordTile extends Tile {

	public SwordTile(Texture texture) {
		super(texture);
	}
	
	public boolean isSolid() {
		return true;
	}
	
	public void render(float x, float y) {
		texture.bind();
		ShaderContainer.SWORDTILE.enable();
		ShaderContainer.SWORDTILE.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(x, y, 0.0f)));
		ShaderContainer.SWORDTILE.setUniform1i("click", 1);
		mesh.render();
		ShaderContainer.SWORDTILE.disable();
		texture.unbind();
	}

}
