package net.farzad.crystalline.client;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientWorldEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
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
import net.farzad.crystalline.common.sound.ClientSideAmbientSoundInstance;
import net.farzad.crystalline.common.sound.ModSounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.property.select.SelectProperties;
import net.minecraft.client.render.item.property.select.SelectProperty;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Set;

public class CrystallineClient implements ClientModInitializer {

    ClientSideAmbientSoundInstance spamtonGspamton;
    @Override
    public void onInitializeClient() {

        ClientPlayConnectionEvents.JOIN.register((clientPlayNetworkHandler, packetSender, minecraftClient) -> {
            spamtonGspamton = ClientSideAmbientSoundInstance.createSoundInstance(ModSounds.HEART_BEAT,minecraftClient.player.getSoundCategory(),minecraftClient.player);
        });
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

        ClientTickEvents.END_CLIENT_TICK.register(tick -> {
            if (tick.player != null && spamtonGspamton != null) {
                var sound = spamtonGspamton;
                if (tick.player.getInventory().containsAny(Set.of(ModItems.CRYSTAL_HEART))) {
                    if (!tick.getSoundManager().isPlaying(sound)) {
                        tick.getSoundManager().play(sound);
                    }
                } else {
                    tick.getSoundManager().stop(sound);
                    tick.getSoundManager().stopSounds(sound.getId(),tick.player.getSoundCategory());
                }
            }
        });

    }

    

}
