package net.farzad.crystalline.client;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.farzad.crystalline.client.item.DividerHeartProperty;
import net.farzad.crystalline.common.Crystalline;
import net.farzad.crystalline.common.entity.ModEntities;
import net.farzad.crystalline.client.render.entity.AmethystShardProjectileEntityModel;
import net.farzad.crystalline.client.render.entity.AmethystShardProjectileEntityRenderer;
import net.farzad.crystalline.common.item.ModItems;
import net.farzad.crystalline.client.particle.AmethystLampParticle;
import net.farzad.crystalline.client.particle.ModParticles;
import net.farzad.crystalline.common.item.custom.DividerItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.property.select.SelectProperties;
import net.minecraft.client.render.item.property.select.SelectProperty;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class CrystallineClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(AmethystShardProjectileEntityModel.AMETHYST_SHARD_PROJECTILE,AmethystShardProjectileEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.AMETHYST_SHARD_PROJECTILE_ENTITY, AmethystShardProjectileEntityRenderer::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.AMETHYST_LAMP_PARTICLE, AmethystLampParticle.Factory::new);
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
            if (itemStack.isOf(ModItems.DIVIDER)) {
                list.add(1,Text.translatable(
                        "tooltip.crystalline.divider_attack",
                        Text.literal("Attack").formatted(Formatting.GOLD)
                ).formatted(Formatting.DARK_GRAY));
                list.add(2,Text.translatable(
                        "tooltip.crystalline.divider_shoot",
                        Text.translatable(MinecraftClient.getInstance().options.useKey.getTranslationKey()).formatted(Formatting.GOLD)

                ).formatted(Formatting.DARK_GRAY));

            }

            if (itemStack.isOf(ModItems.CRUSHER)) {
                list.add(1,Text.translatable(
                        "tooltip.crystalline.crusher_attack",
                        Text.literal("Attack").formatted(Formatting.GOLD)
                ).formatted(Formatting.DARK_GRAY));
            }

        });
        SelectProperties.ID_MAPPER.put(Crystalline.id("divider_heart"),SelectProperty.Type.create(DividerHeartProperty.CODEC,DividerHeartProperty.VALUE_CODEC));


    }

    

}
