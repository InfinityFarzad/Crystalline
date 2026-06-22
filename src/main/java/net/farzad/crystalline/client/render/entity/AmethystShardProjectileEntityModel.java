// Made with Blockbench 5.0.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.farzad.crystalline.client.render.entity;

import net.farzad.crystalline.common.Crystalline;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Mth;

public class AmethystShardProjectileEntityModel extends EntityModel<ArrowRenderState> {
    public static final ModelLayerLocation AMETHYST_SHARD_PROJECTILE = new ModelLayerLocation(Crystalline.id("amethyst_shard_projectile"), "main");

    public AmethystShardProjectileEntityModel(ModelPart root) {
        super(root, RenderTypes::entityCutoutNoCull);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild("back", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 0.0F, 4.0F, 4.0F), PartPose.offsetAndRotation(-10.0F, 0.0F, 0.0F, ((float) Math.PI / 4F), 0.0F, 0.0F));
        CubeListBuilder modelPartBuilder = CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -2.0F, 0.0F, 8.0F, 4.0F, 0.0F, CubeDeformation.NONE, 1.0F, 1F);
        modelPartData.addOrReplaceChild("cross_1", modelPartBuilder, PartPose.rotation(((float) Math.PI / 4F), 0.0F, 0.0F));
        modelPartData.addOrReplaceChild("cross_2", modelPartBuilder, PartPose.rotation(2.3561945F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 32, 32);
    }

    public void setAngles(ArrowRenderState projectileEntityRenderState) {
        super.setupAnim(projectileEntityRenderState);
        if (projectileEntityRenderState.shake > 0.0F) {
            float f = -Mth.sin(projectileEntityRenderState.shake * 3.0F) * projectileEntityRenderState.shake;
            this.root.zRot += f * ((float) Math.PI / 180F);
        }

    }
}