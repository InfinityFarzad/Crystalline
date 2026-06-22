package net.farzad.crystalline.common.item;

import net.farzad.crystalline.common.init.CrystallineDataComponentTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SingleSlotAbilityItem extends Item {

    public SingleSlotAbilityItem(Properties settings) {
        super(settings.component(CrystallineDataComponentTypes.DIVIDER_HEART,ItemStack.EMPTY));
    }

    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack otherStack, Slot slot, ClickAction clickType, Player player, SlotAccess cursorStackReference) {
        ItemStack stored = stack.getOrDefault(CrystallineDataComponentTypes.DIVIDER_HEART, ItemStack.EMPTY);
        if (slot.getItem().get(CrystallineDataComponentTypes.DIVIDER_HEART) == null || !slot.allowModification(player)) {
            return false;
        }
        else {
            if (!player.getCooldowns().isOnCooldown(stack) && stored.isEmpty() && !cursorStackReference.get().isEmpty() && clickType.equals(ClickAction.PRIMARY) && shouldITakeAndShoveThisItemInMyItemSlot(cursorStackReference.get())) {
                stack.set(CrystallineDataComponentTypes.DIVIDER_HEART, cursorStackReference.get());
                cursorStackReference.set(ItemStack.EMPTY);
                onContentChanged(player);
                playSlotEmptySound(player);
                player.getCooldowns().addCooldown(stack, 10);
                return true;
            } else if (!player.getCooldowns().isOnCooldown(stack) && !stored.isEmpty() && cursorStackReference.get().isEmpty() && clickType.equals(ClickAction.SECONDARY)) {
                cursorStackReference.set(stored);
                stack.set(CrystallineDataComponentTypes.DIVIDER_HEART, ItemStack.EMPTY);
                onContentChanged(player);
                playSlotingSound(player);
                player.getCooldowns().addCooldown(stack, 10);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction clickType, Player player) {
        ItemStack stored = stack.getOrDefault(CrystallineDataComponentTypes.DIVIDER_HEART, ItemStack.EMPTY);
        if (slot.getItem().get(CrystallineDataComponentTypes.DIVIDER_HEART) == null) {
            return false;
        }
        else {
            if (stored.isEmpty() && clickType.equals(ClickAction.SECONDARY)) {
                return true;
            } else if (!stored.isEmpty() && clickType.equals(ClickAction.PRIMARY)) {
                return true;
            } else {
                return false;
            }
        }
    }

    private static void playSlotEmptySound(Entity entity) {
        entity.playSound(SoundEvents.AMETHYST_BLOCK_BREAK, 1.0F, 1.0F);
        entity.playSound(SoundEvents.SCULK_CATALYST_BLOOM, 1.0F, 1.0F);
    }

    private static void playSlotingSound(Entity entity) {
        entity.playSound(SoundEvents.AMETHYST_BLOCK_BREAK, 1.0F, -2.0F);
        entity.playSound(SoundEvents.SCULK_CATALYST_BLOOM, 1.0F, -2.0F);
    }

    private void onContentChanged(Player user) {
        AbstractContainerMenu screenHandler = user.containerMenu;
        if (screenHandler != null) {
            screenHandler.slotsChanged(user.getInventory());
        }

    }

    public static boolean hasHeart(DividerItem.DividerAbilityTypes ability, ItemStack stack) {
        ItemStack storedStack = stack.getOrDefault(CrystallineDataComponentTypes.DIVIDER_HEART,ItemStack.EMPTY);
        var storedStackType = storedStack.getOrDefault(CrystallineDataComponentTypes.HEART_DATA, DividerItem.DividerAbilityTypes.NONE);
        boolean bool = storedStackType.equals(ability);

        return bool;
    }

    public boolean shouldITakeAndShoveThisItemInMyItemSlot(ItemStack stack) {
        return stack.has(CrystallineDataComponentTypes.HEART_DATA);
    }
}