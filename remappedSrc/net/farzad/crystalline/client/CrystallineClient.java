package net.farzad.crystalline.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.impl.client.rendering.EntityRendererRegistryImpl;
import net.farzad.crystalline.client.render.item.DividerHeartProperty;
import net.farzad.crystalline.client.particle.AmethystLampParticle;
import net.farzad.crystalline.common.init.*;
import net.farzad.crystalline.client.render.entity.AmethystShardProjectileEntityModel;
import net.farzad.crystalline.client.render.entity.AmethystShardProjectileEntityRenderer;
import net.farzad.crystalline.client.render.entity.CrystalCoreEntityRenderer;
import net.farzad.crystalline.common.Crystalline;
import net.farzad.crystalline.common.sound.ClientSideAmbientSoundInstance;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperties;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import java.util.Set;

public class CrystallineClient implements ClientModInitializer {

    ClientSideAmbientSoundInstance heartSoundEffect;
    @Override
    public void onInitializeClient() {

        init();
        addTooltips();
        updateSoundInstance();
    }

    private void init() {
        EntityModelLayerRegistry.registerModelLayer(AmethystShardProjectileEntityModel.AMETHYST_SHARD_PROJECTILE,AmethystShardProjectileEntityModel::getTexturedModelData);
        EntityRendererRegistryImpl.register(CrystallineEntities.AMETHYST_SHARD_PROJECTILE_ENTITY, AmethystShardProjectileEntityRenderer::new);
        EntityRendererRegistryImpl.register(CrystallineEntities.CRYSTAL_CORE_ENTITY, CrystalCoreEntityRenderer::new);
        ParticleFactoryRegistry.getInstance().register(CrystallineParticles.AMETHYST_LAMP_PARTICLE, AmethystLampParticle.Factory::new);
        SelectItemModelProperties.ID_MAPPER.put(Crystalline.id("divider_heart"),SelectItemModelProperty.Type.create(DividerHeartProperty.CODEC,DividerHeartProperty.VALUE_CODEC));
        initNetworking();
    }

    private void initNetworking() {
        ClientPlayConnectionEvents.JOIN.register((clientPlayNetworkHandler, packetSender, minecraftClient) -> heartSoundEffect = ClientSideAmbientSoundInstance.createSoundInstance(CrystallineSounds.HEART_BEAT, SoundSource.AMBIENT,minecraftClient.player));
    }

    private void updateSoundInstance() {
        ClientTickEvents.END_CLIENT_TICK.register(tick -> {
            if (tick.player != null && heartSoundEffect != null) {

                if (tick.player.getInventory().hasAnyOf(Set.of(CrystallineItems.CRYSTAL_HEART))) {
                    if (!tick.getSoundManager().isActive(heartSoundEffect)) {
                        tick.getSoundManager().play(heartSoundEffect);
                    }
                } else {
                    tick.getSoundManager().stop(heartSoundEffect);
                    tick.getSoundManager().stop(heartSoundEffect.getIdentifier(),tick.player.getSoundSource());
                }
            }
        });
    }

    private void addTooltips() {
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
            if (itemStack.is(CrystallineItems.DIVIDER)) {
                String textig;
                ItemStack heart = itemStack.getOrDefault(CrystallineDataComponentTypes.DIVIDER_HEART, ItemStack.EMPTY);

                if (heart.is(CrystallineItems.CRYSTAL_CORE)) {
                    textig = "tooltip.crystalline.divider_shoot_cc";
                } else if (heart.is(CrystallineItems.CRYSTAL_HEART)){
                    textig = "tooltip.crystalline.divider_shoot_ch";
                } else if (heart.is(Items.HEART_OF_THE_SEA)) {
                    textig = "tooltip.crystalline.divider_shoot_hots";
                } else {
                    textig = "tooltip.crystalline.divider_shoot";
                }



                list.add(1, Component.translatable(
                        "tooltip.crystalline.divider_attack",
                        Component.literal("Attack").withStyle(ChatFormatting.GOLD)
                ).withStyle(ChatFormatting.DARK_GRAY));
                list.add(2, Component.translatable(
                        textig,
                        Component.translatable(Minecraft.getInstance().options.keyUse.saveString()).withStyle(ChatFormatting.GOLD)

                ).withStyle(ChatFormatting.DARK_GRAY));


            }
            if (itemStack.is(CrystallineItems.CRUSHER)) {
                list.add(1,Component.translatable(
                        "tooltip.crystalline.crusher_attack",
                        Component.literal("Attack").withStyle(ChatFormatting.GOLD)
                ).withStyle(ChatFormatting.DARK_GRAY));
            }
        });
    }

    

}
