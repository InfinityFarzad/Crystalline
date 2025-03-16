package net.farzad.crystalline.entity.client;

import net.farzad.crystalline.Crystalline;
import net.farzad.crystalline.entity.custom.AmethystShardProjectileEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;


public class AmethystShardProjectileEntityModel extends EntityModel<AmethystShardProjectileEntity> {

	private final ModelPart root;
	public static final EntityModelLayer AMETHYST_SHARD_PROJECTILE = new EntityModelLayer(Identifier.of(Crystalline.MOD_ID, "amethyst_shard_projectile2"), "main");

	public AmethystShardProjectileEntityModel(ModelPart root) {
		this.root = root.getChild("root");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -6.0F, 2.0F, 8.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 22.0F, -2.0F));

		ModelPartData face2_r1 = root.addChild("face2_r1", ModelPartBuilder.create().uv(0, 4).cuboid(-4.0F, -4.0F, 0.0F, 8.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -4.0F, 0.0F, -1.5708F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 16, 16);
	}

	@Override
	public void setAngles(AmethystShardProjectileEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}


	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		/*root.render(matrices, vertices, light, overlay,color);*/
	}
}