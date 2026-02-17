package net.farzad.crystalline.common.item.custom;

import net.farzad.crystalline.common.dataComponents.ModDataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ClickType;

public class SingleSlotAbilityItem extends Item {

    public SingleSlotAbilityItem(Settings settings) {
        super(settings.component(ModDataComponentTypes.DIVIDER_HEART,ItemStack.EMPTY));
    }

    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        ItemStack stored = stack.getOrDefault(ModDataComponentTypes.DIVIDER_HEART, ItemStack.EMPTY);
        if (slot.getStack().get(ModDataComponentTypes.DIVIDER_HEART) == null || !slot.canTakePartial(player)) {
            return false;
        }
        else {
            if (!player.getItemCooldownManager().isCoolingDown(stack) && stored.isEmpty() && !cursorStackReference.get().isEmpty() && clickType.equals(ClickType.LEFT) && shouldITakeAndShoveThisItemInMyItemSlot(cursorStackReference.get())) {
                stack.set(ModDataComponentTypes.DIVIDER_HEART, cursorStackReference.get());
                cursorStackReference.set(ItemStack.EMPTY);
                onContentChanged(player);
                playSlotEmptySound(player);
                player.getItemCooldownManager().set(stack, 10);
                return true;
            } else if (!player.getItemCooldownManager().isCoolingDown(stack) && !stored.isEmpty() && cursorStackReference.get().isEmpty() && clickType.equals(ClickType.RIGHT)) {
                cursorStackReference.set(stored);
                stack.set(ModDataComponentTypes.DIVIDER_HEART, ItemStack.EMPTY);
                onContentChanged(player);
                playSlotingSound(player);
                player.getItemCooldownManager().set(stack, 10);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        ItemStack stored = stack.getOrDefault(ModDataComponentTypes.DIVIDER_HEART, ItemStack.EMPTY);
        if (slot.getStack().get(ModDataComponentTypes.DIVIDER_HEART) == null) {
            return false;
        }
        else {
            if (stored.isEmpty() && clickType.equals(ClickType.RIGHT)) {
                return true;
            } else if (!stored.isEmpty() && clickType.equals(ClickType.LEFT)) {
                return true;
            } else {
                return false;
            }
        }
    }

    private static void playSlotEmptySound(Entity entity) {
        entity.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, 1.0F, 1.0F);
        entity.playSound(SoundEvents.BLOCK_SCULK_CATALYST_BLOOM, 1.0F, 1.0F);
    }

    private static void playSlotingSound(Entity entity) {
        entity.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, 1.0F, -2.0F);
        entity.playSound(SoundEvents.BLOCK_SCULK_CATALYST_BLOOM, 1.0F, -2.0F);
    }

    private void onContentChanged(PlayerEntity user) {
        ScreenHandler screenHandler = user.currentScreenHandler;
        if (screenHandler != null) {
            screenHandler.onContentChanged(user.getInventory());
        }

    }

    public static boolean hasHeart(DividerItem.DividerAbilityTypes ability, ItemStack stack) {
        ItemStack storedStack = stack.getOrDefault(ModDataComponentTypes.DIVIDER_HEART,ItemStack.EMPTY);
        var storedStackType = storedStack.getOrDefault(ModDataComponentTypes.HEART_DATA, DividerItem.DividerAbilityTypes.NONE);
        boolean bool = storedStackType.equals(ability);

        String massage = bool ? "1" : "2";
        //Crystalline.LOGGER.info(massage);
        return bool;
    }

    public boolean shouldITakeAndShoveThisItemInMyItemSlot(ItemStack stack) {
        return stack.contains(ModDataComponentTypes.HEART_DATA);
    }
}