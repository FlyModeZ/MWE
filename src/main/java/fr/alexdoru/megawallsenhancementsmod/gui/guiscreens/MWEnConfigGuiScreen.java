package fr.alexdoru.megawallsenhancementsmod.gui.guiscreens;

import fr.alexdoru.megawallsenhancementsmod.MegaWallsEnhancementsMod;
import fr.alexdoru.megawallsenhancementsmod.api.apikey.HypixelApiKeyUtil;
import fr.alexdoru.megawallsenhancementsmod.chat.ChatUtil;
import fr.alexdoru.megawallsenhancementsmod.config.ConfigHandler;
import fr.alexdoru.megawallsenhancementsmod.utils.NameUtil;
import fr.alexdoru.megawallsenhancementsmod.utils.SoundUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.client.config.GuiSlider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MWEnConfigGuiScreen extends MyGuiScreen implements GuiSlider.ISlider {

    public MWEnConfigGuiScreen(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        super.initGui();
        /*
         * Defines the button list
         */
        final int buttonsWidth = 210;
        final int XposLeftButton = getxCenter() - buttonsWidth - 10;
        final int XposRightButton = getxCenter() + 10;

        buttonList.add(new GuiButton(18, XposLeftButton, getButtonYPos(1), buttonsWidth, ButtonsHeight, getButtonDisplayString(18)));
        buttonList.add(new GuiButton(26, XposLeftButton, getButtonYPos(2), buttonsWidth, ButtonsHeight, getButtonDisplayString(26)));
        buttonList.add(new GuiButton(19, XposLeftButton, getButtonYPos(3), buttonsWidth, ButtonsHeight, getButtonDisplayString(19)));
        buttonList.add(new GuiButton(25, XposLeftButton, getButtonYPos(4), buttonsWidth, ButtonsHeight, getButtonDisplayString(25)));
        buttonList.add(new GuiButton(15, XposLeftButton, getButtonYPos(5), buttonsWidth, ButtonsHeight, getButtonDisplayString(15)));
        buttonList.add(new GuiButton(27, XposLeftButton, getButtonYPos(6), buttonsWidth, ButtonsHeight, getButtonDisplayString(27)));
        buttonList.add(new GuiButton(24, XposLeftButton, getButtonYPos(7), buttonsWidth, ButtonsHeight, getButtonDisplayString(24)));

        buttonList.add(new GuiButton(21, XposRightButton, getButtonYPos(1), buttonsWidth, ButtonsHeight, getButtonDisplayString(21)));
        buttonList.add(new GuiButton(0, XposRightButton, getButtonYPos(2), buttonsWidth, ButtonsHeight, getButtonDisplayString(0)));
        buttonList.add(new GuiButton(16, XposRightButton, getButtonYPos(3), buttonsWidth, ButtonsHeight, getButtonDisplayString(16)));
        buttonList.add(new GuiButton(17, XposRightButton, getButtonYPos(4), buttonsWidth, ButtonsHeight, getButtonDisplayString(17)));
        buttonList.add(new GuiSlider(20, XposRightButton, getButtonYPos(5), buttonsWidth, ButtonsHeight, "Health threshold : ", " %", 0d, 100d, ConfigHandler.healthThreshold * 100d, false, true, this));
        buttonList.add(new GuiButton(22, XposRightButton, getButtonYPos(6), buttonsWidth, ButtonsHeight, getButtonDisplayString(22)));
        buttonList.add(new GuiSlider(23, XposRightButton, getButtonYPos(7), buttonsWidth, ButtonsHeight, "Maximum dropped item entities : ", "", 40d, 400d, ConfigHandler.maxDroppedEntityRendered, false, true, this));

        /* Exit button */
        buttonList.add(new GuiButton(4, getxCenter() - 150 / 2, getButtonYPos(9), 150, ButtonsHeight, getButtonDisplayString(4)));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawCenteredTitle(EnumChatFormatting.GREEN + "Mega Walls Enhancements v" + MegaWallsEnhancementsMod.version, 2, getxCenter(), getButtonYPos(-1), 0);
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawTooltips(mouseX, mouseY);
    }

    private String getButtonDisplayString(int id) {
        switch (id) {
            case 21:
                return "Safe inventory : " + getSuffix(ConfigHandler.safeInventory);
            case 16:
                return "Strength particule HBR DRE : " + getSuffix(ConfigHandler.strengthParticules);
            case 15:
                return "Icons on names : " + getSuffix(ConfigHandler.iconsOnNames);
            case 0:
                return "Short coin messages : " + getSuffix(ConfigHandler.shortCoinMessage);
            case 24:
                return "Prestige V tags : " + getSuffix(ConfigHandler.prestigeV);
            case 25:
                return "Hide repetitive MW msg : " + getSuffix(ConfigHandler.hideRepetitiveMWChatMsg);
            case 26:
                return "Clear view : " + getSuffix(ConfigHandler.clearVision);
            case 27:
                return "Nick Hider : " + getSuffix(ConfigHandler.nickHider);
            case 18:
                return "Cancel night vision effect : " + getSuffix(!ConfigHandler.keepNightVisionEffect);
            case 19:
                return "Colored tablist health : " + getSuffix(ConfigHandler.useColoredScores);
            case 17:
                return "Sound warning when low HP : " + getSuffix(ConfigHandler.playSoundLowHP);
            case 22:
                return "Limit dropped item rendered : " + getSuffix(ConfigHandler.limitDroppedEntityRendered);
            case 4:
                return "Done";
            default:
                return "no display text for this button id";
        }
    }

    @Override
    public List<String> getTooltipText(int id) {
        final List<String> textLines = new ArrayList<>();
        switch (id) {
            case 21:
                textLines.add(EnumChatFormatting.GREEN + "Prevents dropping the sword you are holding in your hotbar");
                textLines.add(EnumChatFormatting.GREEN + "Prevents hotkeying important kit items out of your inventory");
                textLines.add(EnumChatFormatting.GRAY + "The later only works in Mega Walls");
                break;
            case 16:
                textLines.add(EnumChatFormatting.GREEN + "Spawns angry villager particles when the player gets a final kill");
                break;
            case 15:
                textLines.add(EnumChatFormatting.GREEN + "Toggles all icons on nametags and in the tablist");
                textLines.add("");
                textLines.add(NameUtil.squadprefix + EnumChatFormatting.YELLOW + "Players in your squad");
                textLines.add(NameUtil.prefix_bhop + EnumChatFormatting.YELLOW + "Players reported for bhop");
                textLines.add(NameUtil.prefix + EnumChatFormatting.YELLOW + "Players reported for other cheats");
                textLines.add(NameUtil.prefix_scan + EnumChatFormatting.YELLOW + "Players flagged by the /scangame command");
                textLines.add(NameUtil.prefix_old_report + EnumChatFormatting.YELLOW + "Players no longer getting auto-reported");
                break;
            case 0:
                textLines.add(EnumChatFormatting.GREEN + "Makes the coin messages shorter by removing the network booster info");
                textLines.add(EnumChatFormatting.GREEN + "It makes the assists messages in mega walls fit on one line instead of two");
                textLines.add("");
                textLines.add(EnumChatFormatting.GOLD + "+100 coins! (hypixel's Network booster)" + EnumChatFormatting.AQUA + " FINAL KILL");
                textLines.add("Will become : ");
                textLines.add(EnumChatFormatting.GOLD + "+100 coins!" + EnumChatFormatting.AQUA + " FINAL KILL");
                break;
            case 24:
                textLines.add(EnumChatFormatting.GREEN + "Adds the prestige V colored tags in mega walls");
                textLines.add(EnumChatFormatting.GREEN + "You need at least" + EnumChatFormatting.GOLD + " 1 000 000 coins" + EnumChatFormatting.GREEN + ", " + EnumChatFormatting.GOLD + " 10 000 classpoints" + EnumChatFormatting.GREEN + ",");
                textLines.add(EnumChatFormatting.GREEN + "and a " + EnumChatFormatting.DARK_RED + "working API Key" + EnumChatFormatting.GREEN + ". This will send api requests and store the data");
                textLines.add(EnumChatFormatting.GREEN + "in a cache until you close your game.");
                textLines.add(EnumChatFormatting.GREEN + "Type " + EnumChatFormatting.YELLOW + "/mwenhancements clearcache" + EnumChatFormatting.GREEN + " to force update the data");
                textLines.add("");
                textLines.add(EnumChatFormatting.GOLD + "Prestige Colors :");
                textLines.add(EnumChatFormatting.GOLD + "10000 classpoints : " + EnumChatFormatting.DARK_PURPLE + "[TAG]");
                textLines.add(EnumChatFormatting.GOLD + "13000 classpoints : " + EnumChatFormatting.DARK_BLUE + "[TAG]");
                textLines.add(EnumChatFormatting.GOLD + "19000 classpoints : " + EnumChatFormatting.DARK_AQUA + "[TAG]");
                textLines.add(EnumChatFormatting.GOLD + "28000 classpoints : " + EnumChatFormatting.DARK_GREEN + "[TAG]");
                textLines.add(EnumChatFormatting.GOLD + "40000 classpoints : " + EnumChatFormatting.DARK_RED + "[TAG]");
                break;
            case 25:
                textLines.add(EnumChatFormatting.GREEN + "Hides the following messages in mega walls");
                textLines.add("");
                textLines.add(EnumChatFormatting.RED + "Get to the middle to stop the hunger!");
                textLines.add(EnumChatFormatting.GREEN + "You broke your protected chest");
                textLines.add(EnumChatFormatting.GREEN + "You broke your protected trapped chest");
                textLines.add(EnumChatFormatting.YELLOW + "Your Salvaging skill returned your arrow to you!");
                textLines.add(EnumChatFormatting.YELLOW + "Your Efficiency skill got you an extra drop!");
                textLines.add(EnumChatFormatting.GREEN + "Your " + EnumChatFormatting.AQUA + "Ability name " + EnumChatFormatting.GREEN + "skill is ready!");
                textLines.add(EnumChatFormatting.GREEN + "Click your sword or bow to activate your skill!");
                break;
            case 26:
                textLines.add(EnumChatFormatting.GREEN + "Stops rendering particles that are too close (75cm)");
                textLines.add(EnumChatFormatting.GREEN + "to the camera for a better visibility");
                break;
            case 18:
                textLines.add(EnumChatFormatting.GREEN + "Removes the visual effect of night vision");
                break;
            case 17:
                textLines.add(EnumChatFormatting.GREEN + "Plays a sound when your health drops below the threshold defined below");
                textLines.add(EnumChatFormatting.GRAY + "The sound used is \"note.pling\" check your sound settings to see if it's enabled !");
                break;
            case 19:
                textLines.add(EnumChatFormatting.GREEN + "Adds colors to the scores/health in the tablist depending on the value");
                textLines.add("");
                textLines.add(EnumChatFormatting.RED + "OrangeMarshall " + EnumChatFormatting.GRAY + "[ZOM] " + EnumChatFormatting.DARK_GREEN + "44");
                textLines.add(EnumChatFormatting.RED + "OrangeMarshall " + EnumChatFormatting.GRAY + "[ZOM] " + EnumChatFormatting.GREEN + "35");
                textLines.add(EnumChatFormatting.RED + "OrangeMarshall " + EnumChatFormatting.GRAY + "[ZOM] " + EnumChatFormatting.YELLOW + "25");
                textLines.add(EnumChatFormatting.RED + "OrangeMarshall " + EnumChatFormatting.GRAY + "[ZOM] " + EnumChatFormatting.RED + "15");
                textLines.add(EnumChatFormatting.RED + "OrangeMarshall " + EnumChatFormatting.GRAY + "[ZOM]  " + EnumChatFormatting.DARK_RED + "5");
                break;
            case 22:
                textLines.add(EnumChatFormatting.GREEN + "Dynamically modifies the render distance for dropped items entities to preserve performance");
                textLines.add(EnumChatFormatting.GREEN + "It starts reducing the render distance when exceeding the threshold set below");
                textLines.add(EnumChatFormatting.GRAY + "There is a keybind (ESC -> options -> controls -> MegaWallsEnhancements) to toggle it on the fly");
                break;
            case 27:
                textLines.add(EnumChatFormatting.GREEN + "Shows your real name instead of your nick when forming the squad in Mega Walls");
                break;
        }
        return textLines;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 21:
                ConfigHandler.safeInventory = !ConfigHandler.safeInventory;
                break;
            case 16:
                ConfigHandler.strengthParticules = !ConfigHandler.strengthParticules;
                break;
            case 15:
                ConfigHandler.iconsOnNames = !ConfigHandler.iconsOnNames;
                NameUtil.refreshAllNamesInWorld();
                break;
            case 24:
                if (ConfigHandler.prestigeV) {
                    ConfigHandler.prestigeV = false;
                    NameUtil.refreshAllNamesInWorld();
                } else {
                    if (HypixelApiKeyUtil.apiKeyIsNotSetup()) {
                        ChatUtil.printApikeySetupInfo();
                    } else {
                        ConfigHandler.prestigeV = true;
                        NameUtil.refreshAllNamesInWorld();
                    }
                }
                break;
            case 25:
                ConfigHandler.hideRepetitiveMWChatMsg = !ConfigHandler.hideRepetitiveMWChatMsg;
                break;
            case 26:
                ConfigHandler.clearVision = !ConfigHandler.clearVision;
                break;
            case 27:
                ConfigHandler.nickHider = !ConfigHandler.nickHider;
                break;
            case 0:
                ConfigHandler.shortCoinMessage = !ConfigHandler.shortCoinMessage;
                break;
            case 18:
                ConfigHandler.keepNightVisionEffect = !ConfigHandler.keepNightVisionEffect;
                break;
            case 19:
                ConfigHandler.useColoredScores = !ConfigHandler.useColoredScores;
                break;
            case 22:
                ConfigHandler.limitDroppedEntityRendered = !ConfigHandler.limitDroppedEntityRendered;
                break;
            case 17:
                ConfigHandler.playSoundLowHP = !ConfigHandler.playSoundLowHP;
                if (ConfigHandler.playSoundLowHP) {
                    SoundUtil.playLowHPSound();
                }
                break;
            case 4:
                mc.displayGuiScreen(parent);
                break;
            default:
                break;
        }
        button.displayString = getButtonDisplayString(button.id);
        super.actionPerformed(button);
    }

    @Override
    public void onChangeSliderValue(GuiSlider slider) {
        switch (slider.id) {
            case 20:
                ConfigHandler.healthThreshold = Math.floor(slider.getValue()) / 100d;
                break;
            case 23:
                ConfigHandler.maxDroppedEntityRendered = (int) slider.getValue();
                break;
        }
    }

}
