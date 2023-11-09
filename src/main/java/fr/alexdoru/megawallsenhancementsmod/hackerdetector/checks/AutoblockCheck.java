package fr.alexdoru.megawallsenhancementsmod.hackerdetector.checks;

import fr.alexdoru.megawallsenhancementsmod.config.ConfigHandler;
import fr.alexdoru.megawallsenhancementsmod.hackerdetector.data.PlayerDataSamples;
import fr.alexdoru.megawallsenhancementsmod.hackerdetector.utils.ViolationLevelTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class AutoblockCheck extends AbstractCheck {

    @Override
    public String getCheatName() {
        return "Autoblock";
    }

    @Override
    public String getCheatDescription() {
        return "The player can attack while their sword is blocked";
    }

    @Override
    public boolean canSendReport() {
        return true;
    }

    @Override
    public void performCheck(EntityPlayer player, PlayerDataSamples data) {
        super.checkViolationLevel(player, this.check(player, data), data.autoblockVL);
    }

    @Override
    public boolean check(EntityPlayer player, PlayerDataSamples data) {
        if (data.hasAttacked) {
            final ItemStack itemStack = player.getHeldItem();
            if (itemStack != null && itemStack.getItem() instanceof ItemSword) {
                if (data.useItemTime > 5) {
                    data.autoblockVL.add(1);
                    if (ConfigHandler.debugLogging) {
                        this.log(player, data, data.autoblockVL, "useItemTime " + data.useItemTime + " target " + data.targetedPlayer.getName());
                    }
                    return true;
                } else {
                    data.autoblockVL.substract(1);
                }
            }
        }
        return false;
    }

    public static ViolationLevelTracker newViolationTracker() {
        return new ViolationLevelTracker(4);
    }

}

