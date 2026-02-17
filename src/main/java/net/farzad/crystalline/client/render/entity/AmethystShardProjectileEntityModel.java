// Made with Blockbench 5.0.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.farzad.crystalline.client.render.entity;

import net.farzad.crystalline.common.Crystalline;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.math.MathHelper;

public class AmethystShardProjectileEntityModel extends EntityModel<AmethystShardProjectileEntityRenderState> {
	public static final EntityModelLayer AMETHYST_SHARD_PROJECTILE = new EntityModelLayer(Crystalline.id("amethyst_shard_projectile"), "main");

	public AmethystShardProjectileEntityModel(ModelPart root) {
		super(root, RenderLayer::getEntityCutout);
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("back", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -2.0F, -2.0F, 0.0F, 4.0F, 4.0F), ModelTransform.of(-10.0F, 0.0F, 0.0F, ((float)Math.PI / 4F), 0.0F, 0.0F));
		ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(0, 0).cuboid(-12.0F, -2.0F, 0.0F, 8.0F, 4.0F, 0.0F, Dilation.NONE, 1.0F, 1F);
		modelPartData.addChild("cross_1", modelPartBuilder, ModelTransform.rotation(((float)Math.PI / 4F), 0.0F, 0.0F));
		modelPartData.addChild("cross_2", modelPartBuilder, ModelTransform.rotation(2.3561945F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	public void setAngles(AmethystShardProjectileEntityRenderState projectileEntityRenderState) {
		super.setAngles(projectileEntityRenderState);
		if (projectileEntityRenderState.shake > 0.0F) {
			float f = -MathHelper.sin(projectileEntityRenderState.shake * 3.0F) * projectileEntityRenderState.shake;
			ModelPart var10000 = this.root;
			var10000.roll += f * ((float)Math.PI / 180F);
		}

	}
}