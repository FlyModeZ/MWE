package fr.alexdoru.mwe.config;

import fr.alexdoru.mwe.MWE;
import fr.alexdoru.mwe.asm.loader.ASMLoadingPlugin;
import fr.alexdoru.mwe.chat.ChatHandler;
import fr.alexdoru.mwe.chat.LocrawListener;
import fr.alexdoru.mwe.config.lib.*;
import fr.alexdoru.mwe.features.LeatherArmorManager;
import fr.alexdoru.mwe.gui.guiapi.GuiManager;
import fr.alexdoru.mwe.gui.guiapi.GuiPosition;
import fr.alexdoru.mwe.nocheaters.ReportQueue;
import fr.alexdoru.mwe.nocheaters.WarningMessages;
import fr.alexdoru.mwe.scoreboard.ScoreboardTracker;
import fr.alexdoru.mwe.utils.DelayedTask;
import fr.alexdoru.mwe.utils.NameUtil;
import fr.alexdoru.mwe.utils.SoundUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Loader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MWEConfig extends AbstractConfig {

    private static AbstractConfig INSTANCE;

    private MWEConfig(File file) {
        super(MWEConfig.class, file);
    }

    public static void loadConfig(File file) {
        if (INSTANCE != null) {
            throw new IllegalStateException("Config already created!");
        }
        INSTANCE = new MWEConfig(file);
        if (!MWEConfig.modVersion.equals(MWE.version)) {
            if (!MWEConfig.modVersion.isEmpty()) {
                MWEConfig.onModUpdate();
            }
            MWEConfig.modVersion = MWE.version;
            MWEConfig.saveConfig();
        }
    }

    public static void saveConfig() {
        if (INSTANCE == null) {
            throw new NullPointerException("Config didn't load when the game started, this shouldn't happen !");
        }
        INSTANCE.save();
    }

    public static void displayConfigGuiScreen() {
        if (INSTANCE == null) {
            throw new NullPointerException("Config didn't load when the game started, this shouldn't happen !");
        }
        new DelayedTask(() -> Minecraft.getMinecraft().displayGuiScreen(INSTANCE.getConfigGuiScreen()));
    }

    private static void onModUpdate() {
        // code to run on mod version update
    }

    @ConfigCategory(displayname = "§6Vanilla")
    public static final String VANILLA = "Vanilla";

    @ConfigCategory(displayname = "§8Hypixel")
    public static final String HYPIXEL = "Hypixel";

    @ConfigCategory(displayname = "§aMega Walls")
    public static final String MEGA_WALLS = "Mega Walls";

    @ConfigCategory(displayname = "§5PVP Stuff")
    public static final String PVP_STUFF = "PVP Stuff";

    @ConfigCategory(
            displayname = "§bFinal Kill Counter",
            comment = "For Mega Walls")
    public static final String FINAL_KILL_COUNTER = "Final Kill Counter";

    @ConfigCategory(
            displayname = "§2Squad",
            comment = "§fAdd players to your squad using the §e/squad§f command!")
    public static final String SQUAD = "Squad";

    @ConfigCategory(
            displayname = "§cNoCheaters",
            comment = "§fNoCheaters saves players reported via §e/wdr name§f (not /report) and warns you about them ingame."
                    + "§fTo remove a player from your report list use : §e/unwdr name§f or click the name on the warning message."
                    + "§fYou can see all the players you have reported using §e/nocheaters reportlist§f.")
    public static final String NOCHEATERS = "NoCheaters";

    @ConfigCategory(
            displayname = "§4Hacker Detector",
            comment = "§eDisclaimer : §fthis is not 100% accurate and can sometimes flag legit players, "
                    + "it won't flag every cheater either, however players that are regularly flagging are definitely cheating")
    public static final String HACKER_DETECTOR = "Hacker Detector";

    @ConfigCategory(
            displayname = "§9Hitboxes, better F3+b",
            comment = "§7You obviously need to press F3+b to enable hitboxes")
    public static final String HITBOXES = "Hitbox";

    @ConfigCategory(displayname = "§dExternal")
    public static final String EXTERNAL = "External";

    @ConfigProperty(
            category = "General",
            name = "Mod Version",
            comment = "The version of the mod the config was saved with",
            hidden = true)
    public static String modVersion = "";

    @ConfigProperty(
            category = "April Fools",
            name = "April Fun",
            comment = "Haha got u")
    public static boolean aprilFools = true;

    @ConfigPropertyHideOverride(name = "April Fun")
    public static boolean hideAprilFoolsSetting() {
        return !"01/04".equals(new SimpleDateFormat("dd/MM").format(new Date().getTime()));
    }

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.chat"),
            name = "Chat Heads", displayname = I18n.format("mwe.config.category.vanilla.chat.chatHeads"),
            comment = I18n.format("mwe.config.category.vanilla.chat.chatHeads.comment")
    public static boolean chatHeads = true;

    @ConfigPropertyHideOverride(name = "Chat Heads")
    public static boolean hideChatHeadSetting() {
        return ASMLoadingPlugin.isFeatherLoaded();
    }

    @ConfigPropertyEvent(name = "Chat Heads")
    public static void onChatHeadsSetting() {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().refreshChat();
    }

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.chat"),
            name = "Longer chat", displayname = I18n.format("mwe.config.category.vanilla.chat.longerChat"),
            comment = I18n.format("mwe.config.category.vanilla.chat.longerChat.comment")
    public static boolean longerChat = true;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.chat_copy"),
            name = "Left click to copy chat messages", displayname = I18n.format("mwe.config.category.vanilla.chat_copy.leftClickChatCopy"),
            comment = I18n.format("mwe.config.category.vanilla.chat_copy.leftClickChatCopy.comment")
    public static boolean leftClickChatCopy = true;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.chat_copy"),
            name = "Right click to copy chat messages", displayname = I18n.format("mwe.config.category.vanilla.chat_copy.rightClickChatCopy"),
            comment = I18n.format("mwe.config.category.vanilla.chat_copy.rightClickChatCopy.comment")
    public static boolean rightClickChatCopy = true;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.chat_copy"),
            name = "Shift click to copy one chat line", displayname = I18n.format("mwe.config.category.vanilla.chat_copy.shiftClickChatLineCopy"),
            comment = I18n.format("mwe.config.category.vanilla.chat_copy.shiftClickChatLineCopy.comment")
    public static boolean shiftClickChatLineCopy = true;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.chat_search"),
            name = "Search box in chat", displayname = I18n.format("mwe.config.category.vanilla.chat_search.searchBoxChat"),
            comment = I18n.format("mwe.config.category.vanilla.chat_search.searchBoxChat.comment")
    public static boolean searchBoxChat = true;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.chat_search"),
            name = "Always render search box", displayname = I18n.format("mwe.config.category.vanilla.chat_search.showSearchBoxUnfocused"),
            comment = I18n.format("mwe.config.category.vanilla.chat_search.showSearchBoxUnfocused.comment")
    public static boolean showSearchBoxUnfocused = true;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.chat_search"),
            name = "Search box shortcuts", displayname = I18n.format("mwe.config.category.vanilla.chat_search.searchBoxChatShortcuts"),
            comment = I18n.format("mwe.config.category.vanilla.chat_search.searchBoxChatShortcuts.comment")
    public static boolean searchBoxChatShortcuts = true;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.chat_search"),
            name = "Keep previous chat search", displayname = I18n.format("mwe.config.category.vanilla.chat_search.keepPreviousChatSearch"),
            comment = I18n.format("mwe.config.category.vanilla.chat_search.keepPreviousChatSearch.comment")
    public static boolean keepPreviousChatSearch;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.chat_search"),
            name = "Search box X offset", displayname = I18n.format("mwe.config.category.vanilla.chat_search.searchBoxXOffset"),
            comment = I18n.format("mwe.config.category.vanilla.chat_search.searchBoxXOffset.comment")
    public static int searchBoxXOffset = 0;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.chat_search"),
            name = "Search box Y offset", displayname = I18n.format("mwe.config.category.vanilla.chat_search.searchBoxYOffset"),
            comment = I18n.format("mwe.config.category.vanilla.chat_search.searchBoxYOffset.comment")
    public static int searchBoxYOffset = 0;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.hurt_color"),
            name = "Custom Hurt Color", displayname = I18n.format("mwe.config.category.vanilla.hurt_color.hitColor"),
            comment = I18n.format("mwe.config.category.vanilla.hurt_color.hitColor.comment")
    public static int hitColor = 0x4CFF0000;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.hurt_color"),
            name = "Color armor when hurt", displayname = I18n.format("mwe.config.category.vanilla.hurt_color.colorArmorWhenHurt"),
            comment = I18n.format("mwe.config.category.vanilla.hurt_color.colorArmorWhenHurt.comment")
    public static boolean colorArmorWhenHurt = true;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.hurt_color"),
            name = "Team colored hurt color for players", displayname = I18n.format("mwe.config.category.vanilla.hurt_color.teamColoredPlayerHurt"),
            comment = I18n.format("mwe.config.category.vanilla.hurt_color.teamColoredPlayerHurt.comment")
    public static boolean teamColoredPlayerHurt;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.hurt_color"),
            name = "Team colored hurt color for withers", displayname = I18n.format("mwe.config.category.vanilla.hurt_color.teamColoredWitherHurt"),
            comment = I18n.format("mwe.config.category.vanilla.hurt_color.teamColoredWitherHurt.comment")
    public static boolean teamColoredWitherHurt = true;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.performance"),
            name = "Limit dropped item rendered", displayname = I18n.format("mwe.config.category.vanilla.performance.limitDroppedEntityRendered"),
            comment = I18n.format("mwe.config.category.vanilla.performance.limitDroppedEntityRendered.comment")
    public static boolean limitDroppedEntityRendered = true;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.performance"),
            name = "Max amount of dropped item", displayname = I18n.format("mwe.config.category.vanilla.performance.maxDroppedEntityRendered"),
            comment = I18n.format("mwe.config.category.vanilla.performance.maxDroppedEntityRendered.comment")
    public static int maxDroppedEntityRendered = 80;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.render"),
            name = "Cancel Night Vision Effect", displayname = I18n.format("mwe.config.category.vanilla.render.cancelNightVisionEffect"),
            comment = I18n.format("mwe.config.category.vanilla.render.cancelNightVisionEffect.comment")
    public static boolean cancelNightVisionEffect;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.render"),
            name = "Clear View", displayname = I18n.format("mwe.config.category.vanilla.render.clearVision"),
            comment = I18n.format("mwe.config.category.vanilla.render.clearVision.comment")
    public static boolean clearVision = true;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.render"),
            name = "Colored health/scores above head", displayname = I18n.format("mwe.config.category.vanilla.render.coloredScoreAboveHead"),
            comment = I18n.format("mwe.config.category.vanilla.render.coloredScoreAboveHead.comment")
    public static boolean coloredScoreAboveHead = true;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.tablist"),
            name = "Colored health/scores in Tablist", displayname = I18n.format("mwe.config.category.vanilla.tablist.coloredScoresInTablist"),
            comment = I18n.format("mwe.config.category.vanilla.tablist.coloredScoresInTablist.comment")
    public static boolean coloredScoresInTablist = true;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.tablist"),
            name = "Hide Header Footer Tablist", displayname = I18n.format("mwe.config.category.vanilla.tablist.hideTablistHeaderFooter"),
            comment = I18n.format("mwe.config.category.vanilla.tablist.hideTablistHeaderFooter.comment")
    public static boolean hideTablistHeaderFooter;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.tablist"),
            name = "Hide Header Footer only in MW", displayname = I18n.format("mwe.config.category.vanilla.tablist.hideTablistHeaderFooterOnlyInMW"),
            comment = I18n.format("mwe.config.category.vanilla.tablist.hideTablistHeaderFooterOnlyInMW.comment")
    public static boolean hideTablistHeaderFooterOnlyInMW;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.tablist"),
            name = "Show playercount Tablist", displayname = I18n.format("mwe.config.category.vanilla.tablist.showPlayercountTablist"),
            comment = I18n.format("mwe.config.category.vanilla.tablist.showPlayercountTablist.comment")
    public static boolean showPlayercountTablist = true;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.tablist"),
            name = "Tablist size", displayname = I18n.format("mwe.config.category.vanilla.tablist.tablistSize"),
            comment = I18n.format("mwe.config.category.vanilla.tablist.tablistSize.comment")
    public static int tablistSize = 100;

    @ConfigPropertyHideOverride(name = "Tablist size")
    public static boolean hideTabSizeSetting() {
        return ASMLoadingPlugin.isPatcherLoaded();
    }

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.tablist"),
            name = "Hide ping tablist", displayname = I18n.format("mwe.config.category.vanilla.tablist.hidePingTablist"),
            comment = I18n.format("mwe.config.category.vanilla.tablist.hidePingTablist.comment")
    public static boolean hidePingTablist = true;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.tablist"),
            name = "Tablist column separator", displayname = I18n.format("mwe.config.category.vanilla.tablist.tablistColumnSpacing"),
            comment = I18n.format("mwe.config.category.vanilla.tablist.tablistColumnSpacing.comment")
    public static int tablistColumnSpacing = 1;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.tablist"),
            name = "De-obfuscate names in tab", displayname = I18n.format("mwe.config.category.vanilla.tablist.deobfNamesInTab"),
            comment = I18n.format("mwe.config.category.vanilla.tablist.deobfNamesInTab.comment")
    public static boolean deobfNamesInTab;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.tablist"),
            name = "Show fake players in tab", displayname = I18n.format("mwe.config.category.vanilla.tablist.showFakePlayersInTab"),
            comment = I18n.format("mwe.config.category.vanilla.tablist.showFakePlayersInTab.comment")
    public static boolean showFakePlayersInTab;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.bugfix"),
            name = "Fix actionbar text overlap", displayname = I18n.format("mwe.config.category.vanilla.bugfix.fixActionbarTextOverlap"),
            comment = I18n.format("mwe.config.category.vanilla.bugfix.fixActionbarTextOverlap.comment")
    public static boolean fixActionbarTextOverlap = true;

    @ConfigProperty(
            category = VANILLA, subCategory = I18n.format("mwe.config.category.vanilla.logs"),
            name = "Clean chat logs", displayname = I18n.format("mwe.config.category.vanilla.logs.cleanChatLogs"),
            comment = I18n.format("mwe.config.category.vanilla.logs.cleanChatLogs.comment")
    public static boolean cleanChatLogs = true;

    @ConfigProperty(
            category = PVP_STUFF, subCategory = I18n.format("mwe.config.category.pvp_stuff.armor_hud"),
            name = "Armor HUD", displayname = I18n.format("mwe.config.category.pvp_stuff.armor_hud.armorHUDPositon"),
            comment = I18n.format("mwe.config.category.pvp_stuff.armor_hud.armorHUDPositon.comment")
    public static final GuiPosition armorHUDPositon = new GuiPosition(false, 0.25d, 1d);

    @ConfigProperty(
            category = PVP_STUFF, subCategory = I18n.format("mwe.config.category.pvp_stuff.armor_hud"),
            name = "Horizontal Armor HUD", displayname = I18n.format("mwe.config.category.pvp_stuff.armor_hud.horizontalArmorHUD"),
            comment = I18n.format("mwe.config.category.pvp_stuff.armor_hud.horizontalArmorHUD.comment")
    public static boolean horizontalArmorHUD = true;

    @ConfigProperty(
            category = PVP_STUFF, subCategory = I18n.format("mwe.config.category.pvp_stuff.armor_hud"),
            name = "Low Durability Armor HUD", displayname = I18n.format("mwe.config.category.pvp_stuff.armor_hud.lowDuraArmorHUD"),
            comment = I18n.format("mwe.config.category.pvp_stuff.armor_hud.lowDuraArmorHUD.comment")
    public static boolean lowDuraArmorHUD;

    @ConfigProperty(
            category = PVP_STUFF, subCategory = "Armor HUD",
            name = "Low Durability threshold",
            sliderMax = 528)
    public static int lowDuraArmorHUDValue = 50;

    @ConfigProperty(
            category = PVP_STUFF, subCategory = I18n.format("mwe.config.category.pvp_stuff.armor_hud"),
            name = "Show Armor Durability", displayname = I18n.format("mwe.config.category.pvp_stuff.armor_hud.showArmorDurability"),
            comment = I18n.format("mwe.config.category.pvp_stuff.armor_hud.showArmorDurability.comment")
    public static boolean showArmorDurability = true;

    @ConfigProperty(
            category = PVP_STUFF, subCategory = I18n.format("mwe.config.category.pvp_stuff.armor_hud"),
            name = "Show Armor Durability as number", displayname = I18n.format("mwe.config.category.pvp_stuff.armor_hud.showArmorDurabilityAsNumber"),
            comment = I18n.format("mwe.config.category.pvp_stuff.armor_hud.showArmorDurabilityAsNumber.comment")
    public static boolean showArmorDurabilityAsNumber;

    @ConfigProperty(
            category = PVP_STUFF, subCategory = I18n.format("mwe.config.category.pvp_stuff.potion_hud"),
            name = "Mini Potion HUD", displayname = I18n.format("mwe.config.category.pvp_stuff.potion_hud.miniPotionHUDPosition"),
            comment = I18n.format("mwe.config.category.pvp_stuff.potion_hud.miniPotionHUDPosition.comment")
    public static final GuiPosition miniPotionHUDPosition = new GuiPosition(false, 0.5d, 7.5d / 20d);

    @ConfigProperty(
            category = PVP_STUFF, subCategory = I18n.format("mwe.config.category.pvp_stuff.potion_hud"),
            name = "Mini Potion HUD only in MW", displayname = I18n.format("mwe.config.category.pvp_stuff.potion_hud.showMiniPotionHUDOnlyMW"),
            comment = I18n.format("mwe.config.category.pvp_stuff.potion_hud.showMiniPotionHUDOnlyMW.comment")
    public static boolean showMiniPotionHUDOnlyMW;

    @ConfigProperty(
            category = PVP_STUFF, subCategory = I18n.format("mwe.config.category.pvp_stuff.potion_hud"),
            name = "Potion HUD", displayname = I18n.format("mwe.config.category.pvp_stuff.potion_hud.potionHUDPosition"),
            comment = I18n.format("mwe.config.category.pvp_stuff.potion_hud.potionHUDPosition.comment")
    public static final GuiPosition potionHUDPosition = new GuiPosition(false, 0d, 0.5d);

    @ConfigProperty(
            category = PVP_STUFF, subCategory = I18n.format("mwe.config.category.pvp_stuff.potion_hud"),
            name = "Horizontal Potion HUD", displayname = I18n.format("mwe.config.category.pvp_stuff.potion_hud.horizontalPotionHUD"),
            comment = I18n.format("mwe.config.category.pvp_stuff.potion_hud.horizontalPotionHUD.comment")
    public static boolean horizontalPotionHUD;

    @ConfigProperty(
            category = PVP_STUFF, subCategory = I18n.format("mwe.config.category.pvp_stuff.potion_hud"),
            name = "Show Potion names", displayname = I18n.format("mwe.config.category.pvp_stuff.potion_hud.showPotionEffectNames"),
            comment = I18n.format("mwe.config.category.pvp_stuff.potion_hud.showPotionEffectNames.comment")
    public static boolean showPotionEffectNames;

    @ConfigProperty(
            category = PVP_STUFF, subCategory = "Potion HUD",
            name = "Potion HUD Text Color",
            isColor = true)
    public static int potionHUDTextColor = 0xFFFFFF;

    @ConfigProperty(
            category = PVP_STUFF, subCategory = I18n.format("mwe.config.category.pvp_stuff.toggle_sprint"),
            name = "Toggle Sprint", displayname = I18n.format("mwe.config.category.pvp_stuff.toggle_sprint.toggleSprint"),
            comment = I18n.format("mwe.config.category.pvp_stuff.toggle_sprint.toggleSprint.comment")
    public static boolean toggleSprint;

    @ConfigProperty(
            category = PVP_STUFF, subCategory = I18n.format("mwe.config.category.pvp_stuff.health"),
            name = "Sound warning low HP", displayname = I18n.format("mwe.config.category.pvp_stuff.health.playSoundLowHP"),
            comment = I18n.format("mwe.config.category.pvp_stuff.health.playSoundLowHP.comment")
    public static boolean playSoundLowHP;

    @ConfigPropertyEvent(name = "Sound warning low HP")
    public static void onLowHPSoundSetting() {
        if (MWEConfig.playSoundLowHP) {
            SoundUtil.playLowHPSound();
        }
    }

    @ConfigProperty(
            category = PVP_STUFF, subCategory = I18n.format("mwe.config.category.pvp_stuff.health"),
            name = "Health Threshold low HP", displayname = I18n.format("mwe.config.category.pvp_stuff.health.healthThreshold"),
            comment = I18n.format("mwe.config.category.pvp_stuff.health.healthThreshold.comment")
    public static double healthThreshold = 0.5d;

    @ConfigProperty(
            category = PVP_STUFF, subCategory = I18n.format("mwe.config.category.pvp_stuff.inventory"),
            name = "Prevent sword dropping", displayname = I18n.format("mwe.config.category.pvp_stuff.inventory.preventSwordDropping"),
            comment = I18n.format("mwe.config.category.pvp_stuff.inventory.preventSwordDropping.comment")
    public static boolean preventSwordDropping = true;

    @ConfigProperty(
            category = PVP_STUFF, subCategory = I18n.format("mwe.config.category.pvp_stuff.other_hud"),
            name = "Arrow Hit HUD", displayname = I18n.format("mwe.config.category.pvp_stuff.other_hud.arrowHitHUDPosition"),
            comment = I18n.format("mwe.config.category.pvp_stuff.other_hud.arrowHitHUDPosition.comment")
    public static final GuiPosition arrowHitHUDPosition = new GuiPosition(true, 0.5d, 9d / 20d);

    @ConfigProperty(
            category = PVP_STUFF, subCategory = I18n.format("mwe.config.category.pvp_stuff.other_hud"),
            name = "Show head on Arrow Hit HUD", displayname = I18n.format("mwe.config.category.pvp_stuff.other_hud.showHeadOnArrowHitHUD"),
            comment = I18n.format("mwe.config.category.pvp_stuff.other_hud.showHeadOnArrowHitHUD.comment")
    public static boolean showHeadOnArrowHitHUD;

    @ConfigProperty(
            category = PVP_STUFF, subCategory = I18n.format("mwe.config.category.pvp_stuff.other_hud"),
            name = "Speed HUD", displayname = I18n.format("mwe.config.category.pvp_stuff.other_hud.speedHUDPosition"),
            comment = I18n.format("mwe.config.category.pvp_stuff.other_hud.speedHUDPosition.comment")
    public static final GuiPosition speedHUDPosition = new GuiPosition(false, 1d, 1d);

    @ConfigProperty(
            category = PVP_STUFF, subCategory = "Other HUD",
            name = "Speed HUD Color",
            isColor = true)
    public static int speedHUDColor = 0x00AA00;

    @ConfigProperty(
            category = HITBOXES,
            name = "Hitbox enabled on start",
            hidden = true)
    public static boolean isDebugHitboxOn;

    @ConfigProperty(
            category = HITBOXES, subCategory = "Render hitbox for : ",
            name = "Hitbox for players")
    public static boolean drawHitboxForPlayers = true;

    @ConfigProperty(
            category = HITBOXES, subCategory = "Render hitbox for : ",
            name = "Hitbox for grounded arrows")
    public static boolean drawHitboxForGroundedArrows = true;

    @ConfigProperty(
            category = HITBOXES, subCategory = "Render hitbox for : ",
            name = "Hitbox for pinned arrows")
    public static boolean drawHitboxForPinnedArrows = true;

    @ConfigProperty(
            category = HITBOXES, subCategory = "Render hitbox for : ",
            name = "Hitbox for flying arrows")
    public static boolean drawHitboxForFlyingArrows = true;

    @ConfigProperty(
            category = HITBOXES, subCategory = "Render hitbox for : ",
            name = "Hitbox for dropped items")
    public static boolean drawHitboxForDroppedItems = true;

    @ConfigProperty(
            category = HITBOXES, subCategory = "Render hitbox for : ",
            name = "Hitbox for passive mobs")
    public static boolean drawHitboxForPassiveMobs = true;

    @ConfigProperty(
            category = HITBOXES, subCategory = "Render hitbox for : ",
            name = "Hitbox for aggressive mobs")
    public static boolean drawHitboxForAggressiveMobs = true;

    @ConfigProperty(
            category = HITBOXES, subCategory = "Render hitbox for : ",
            name = "Hitbox for withers")
    public static boolean drawHitboxForWithers = true;

    @ConfigProperty(
            category = HITBOXES, subCategory = "Render hitbox for : ",
            name = "Hitbox for item frame")
    public static boolean drawHitboxItemFrame = true;

    @ConfigProperty(
            category = HITBOXES, subCategory = "Render hitbox for : ",
            name = "Hitbox for other entity")
    public static boolean drawHitboxForOtherEntity = true;

    @ConfigProperty(
            category = HITBOXES, subCategory = I18n.format("mwe.config.category.hitboxes.red_box"),
            name = "Red eye square", displayname = I18n.format("mwe.config.category.hitboxes.red_box.drawRedBox"),
            comment = I18n.format("mwe.config.category.hitboxes.red_box.drawRedBox.comment")
    public static boolean drawRedBox = true;

    @ConfigProperty(
            category = HITBOXES, subCategory = I18n.format("mwe.config.category.hitboxes.blue_vector"),
            name = "Render blue vector", displayname = I18n.format("mwe.config.category.hitboxes.blue_vector.drawBlueVect"),
            comment = I18n.format("mwe.config.category.hitboxes.blue_vector.drawBlueVect.comment")
    public static boolean drawBlueVect = true;

    @ConfigProperty(
            category = HITBOXES, subCategory = I18n.format("mwe.config.category.hitboxes.blue_vector"),
            name = "Blue vect for players only", displayname = I18n.format("mwe.config.category.hitboxes.blue_vector.drawBlueVectForPlayersOnly"),
            comment = I18n.format("mwe.config.category.hitboxes.blue_vector.drawBlueVectForPlayersOnly.comment")
    public static boolean drawBlueVectForPlayersOnly;

    @ConfigProperty(
            category = HITBOXES, subCategory = I18n.format("mwe.config.category.hitboxes.blue_vector"),
            name = "Make blue vector 3m long", displayname = I18n.format("mwe.config.category.hitboxes.blue_vector.makeBlueVect3Meters"),
            comment = I18n.format("mwe.config.category.hitboxes.blue_vector.makeBlueVect3Meters.comment")
    public static boolean makeBlueVect3Meters = true;

    @ConfigProperty(
            category = HITBOXES, subCategory = I18n.format("mwe.config.category.hitboxes.color"),
            name = "Hitbox Color", displayname = I18n.format("mwe.config.category.hitboxes.color.hitboxColor"),
            comment = I18n.format("mwe.config.category.hitboxes.color.hitboxColor.comment")
    public static int hitboxColor = 0xFFFFFF;

    @ConfigProperty(
            category = HITBOXES, subCategory = I18n.format("mwe.config.category.hitboxes.color"),
            name = "Team colored arrow hitbox", displayname = I18n.format("mwe.config.category.hitboxes.color.teamColoredArrowHitbox"),
            comment = I18n.format("mwe.config.category.hitboxes.color.teamColoredArrowHitbox.comment")
    public static boolean teamColoredArrowHitbox = true;

    @ConfigProperty(
            category = HITBOXES, subCategory = I18n.format("mwe.config.category.hitboxes.color"),
            name = "Team colored player hitbox", displayname = I18n.format("mwe.config.category.hitboxes.color.teamColoredPlayerHitbox"),
            comment = I18n.format("mwe.config.category.hitboxes.color.teamColoredPlayerHitbox.comment")
    public static boolean teamColoredPlayerHitbox = true;

    @ConfigProperty(
            category = HITBOXES, subCategory = I18n.format("mwe.config.category.hitboxes.color"),
            name = "Team colored wither hitbox", displayname = I18n.format("mwe.config.category.hitboxes.color.teamColoredWitherHitbox"),
            comment = I18n.format("mwe.config.category.hitboxes.color.teamColoredWitherHitbox.comment")
    public static boolean teamColoredWitherHitbox = true;

    @ConfigProperty(
            category = HITBOXES, subCategory = I18n.format("mwe.config.category.hitboxes.other"),
            name = "Real size hitbox", displayname = I18n.format("mwe.config.category.hitboxes.other.realSizeHitbox"),
            comment = I18n.format("mwe.config.category.hitboxes.other.realSizeHitbox.comment")
    public static boolean realSizeHitbox;

    @ConfigProperty(
            category = HITBOXES, subCategory = I18n.format("mwe.config.category.hitboxes.other"),
            name = "Hide close hitbox", displayname = I18n.format("mwe.config.category.hitboxes.other.hideCloseHitbox"),
            comment = I18n.format("mwe.config.category.hitboxes.other.hideCloseHitbox.comment")
    public static boolean hideCloseHitbox;

    @ConfigProperty(
            category = HITBOXES, subCategory = I18n.format("mwe.config.category.hitboxes.other"),
            name = "Hitbox render range", displayname = I18n.format("mwe.config.category.hitboxes.other.hitboxDrawRange"),
            comment = I18n.format("mwe.config.category.hitboxes.other.hitboxDrawRange.comment")
    public static double hitboxDrawRange = 8f;

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = I18n.format("mwe.config.category.mega_walls.general"),
            name = "Colored leather armor", displayname = I18n.format("mwe.config.category.mega_walls.general.coloredLeatherArmor"),
            comment = I18n.format("mwe.config.category.mega_walls.general.coloredLeatherArmor.comment")
    public static boolean coloredLeatherArmor;

    @ConfigPropertyEvent(name = "Colored leather armor")
    public static void onColoredLeatherArmorSetting() {
        LeatherArmorManager.onSettingChange();
    }

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = I18n.format("mwe.config.category.mega_walls.general"),
            name = "AFK sound warning", displayname = I18n.format("mwe.config.category.mega_walls.general.afkSoundWarning"),
            comment = I18n.format("mwe.config.category.mega_walls.general.afkSoundWarning.comment")
    public static boolean afkSoundWarning = true;

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = I18n.format("mwe.config.category.mega_walls.general"),
            name = "Strength particules", displayname = I18n.format("mwe.config.category.mega_walls.general.strengthParticules"),
            comment = I18n.format("mwe.config.category.mega_walls.general.strengthParticules.comment")
    public static boolean strengthParticules = true;

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = I18n.format("mwe.config.category.mega_walls.general"),
            name = "Show pinned arrows as renegade", displayname = I18n.format("mwe.config.category.mega_walls.general.renegadeArrowCount"),
            comment = I18n.format("mwe.config.category.mega_walls.general.renegadeArrowCount.comment")
    public static boolean renegadeArrowCount = true;

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = I18n.format("mwe.config.category.mega_walls.general"),
            name = "Squad add halo player", displayname = I18n.format("mwe.config.category.mega_walls.general.squadHaloPlayer"),
            comment = I18n.format("mwe.config.category.mega_walls.general.squadHaloPlayer.comment")
    public static boolean squadHaloPlayer = true;

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = I18n.format("mwe.config.category.mega_walls.chat"),
            name = "Print deathmatch damage in chat", displayname = I18n.format("mwe.config.category.mega_walls.chat.printDeathmatchDamageMessage"),
            comment = I18n.format("mwe.config.category.mega_walls.chat.printDeathmatchDamageMessage.comment")
    public static boolean printDeathmatchDamageMessage = true;

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = I18n.format("mwe.config.category.mega_walls.chat"),
            name = "Hide repetitive chat messages", displayname = I18n.format("mwe.config.category.mega_walls.chat.hideRepetitiveMWChatMsg"),
            comment = I18n.format("mwe.config.category.mega_walls.chat.hideRepetitiveMWChatMsg.comment")
    public static boolean hideRepetitiveMWChatMsg = true;

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = I18n.format("mwe.config.category.mega_walls.inventory"),
            name = "Safe Inventory", displayname = I18n.format("mwe.config.category.mega_walls.inventory.safeInventory"),
            comment = I18n.format("mwe.config.category.mega_walls.inventory.safeInventory.comment")
    public static boolean safeInventory = true;

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = I18n.format("mwe.config.category.mega_walls.render"),
            name = "Render wither outline", displayname = I18n.format("mwe.config.category.mega_walls.render.renderWitherOutline"),
            comment = I18n.format("mwe.config.category.mega_walls.render.renderWitherOutline.comment")
    public static boolean renderWitherOutline = true;

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = I18n.format("mwe.config.category.mega_walls.screen"),
            name = "Hide hunger title", displayname = I18n.format("mwe.config.category.mega_walls.screen.hideHungerTitleInMW"),
            comment = I18n.format("mwe.config.category.mega_walls.screen.hideHungerTitleInMW.comment")
    public static boolean hideHungerTitleInMW = true;

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = I18n.format("mwe.config.category.mega_walls.hud"),
            name = "Kill cooldown HUD", displayname = I18n.format("mwe.config.category.mega_walls.hud.killCooldownHUDPosition"),
            comment = I18n.format("mwe.config.category.mega_walls.hud.killCooldownHUDPosition.comment")
    public static final GuiPosition killCooldownHUDPosition = new GuiPosition(true, 0d, 0d);

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = "HUD",
            name = "Kill cooldown HUD Color",
            isColor = true)
    public static int killCooldownHUDColor = 0xAA0000;

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = I18n.format("mwe.config.category.mega_walls.hud"),
            name = "Last wither HUD", displayname = I18n.format("mwe.config.category.mega_walls.hud.lastWitherHUDPosition"),
            comment = I18n.format("mwe.config.category.mega_walls.hud.lastWitherHUDPosition.comment")
    public static final GuiPosition lastWitherHUDPosition = new GuiPosition(true, 0.75d, 0d);

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = I18n.format("mwe.config.category.mega_walls.hud"),
            name = "Last wither HUD in sidebar", displayname = I18n.format("mwe.config.category.mega_walls.hud.witherHUDinSidebar"),
            comment = I18n.format("mwe.config.category.mega_walls.hud.witherHUDinSidebar.comment")
    public static boolean witherHUDinSidebar = true;

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = I18n.format("mwe.config.category.mega_walls.hud"),
            name = "Strength HUD", displayname = I18n.format("mwe.config.category.mega_walls.hud.strengthHUDPosition"),
            comment = I18n.format("mwe.config.category.mega_walls.hud.strengthHUDPosition.comment")
    public static final GuiPosition strengthHUDPosition = new GuiPosition(true, 0.5d, 8d / 20d);

    @ConfigPropertyEvent(name = "Strength HUD")
    public static void onStrengthHUDSetting() {
        if (MWEConfig.strengthHUDPosition.isEnabled()) {
            SoundUtil.playStrengthSound();
        }
    }

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = I18n.format("mwe.config.category.mega_walls.hud"),
            name = "Creeper primed TNT HUD", displayname = I18n.format("mwe.config.category.mega_walls.hud.creeperTNTHUDPosition"),
            comment = I18n.format("mwe.config.category.mega_walls.hud.creeperTNTHUDPosition.comment")
    public static final GuiPosition creeperTNTHUDPosition = new GuiPosition(true, 0.5d, 8d / 20d);

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = I18n.format("mwe.config.category.mega_walls.hud"),
            name = "Energy display HUD", displayname = I18n.format("mwe.config.category.mega_walls.hud.energyHUDPosition"),
            comment = I18n.format("mwe.config.category.mega_walls.hud.energyHUDPosition.comment")
    public static final GuiPosition energyHUDPosition = new GuiPosition(true, 0.5d, 10.5 / 20d);

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = "HUD",
            name = "High energy threshold",
            sliderMax = 160)
    public static int highEnergyThreshold = 100;

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = "HUD",
            name = "Low energy color",
            isColor = true)
    public static int lowEnergyHUDColor = 0x55FF55;

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = "HUD",
            name = "High energy color",
            isColor = true)
    public static int highEnergyHUDColor = 0x55FFFF;

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = I18n.format("mwe.config.category.mega_walls.hud"),
            name = "Phoenix Bond HUD", displayname = I18n.format("mwe.config.category.mega_walls.hud.phxBondHUDPosition"),
            comment = I18n.format("mwe.config.category.mega_walls.hud.phxBondHUDPosition.comment")
    public static final GuiPosition phxBondHUDPosition = new GuiPosition(true, 0.5d, 0.75d);

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = I18n.format("mwe.config.category.mega_walls.hud"),
            name = "Base Location HUD", displayname = I18n.format("mwe.config.category.mega_walls.hud.baseLocationHUDPosition"),
            comment = I18n.format("mwe.config.category.mega_walls.hud.baseLocationHUDPosition.comment")
    public static final GuiPosition baseLocationHUDPosition = new GuiPosition(true, 0.90d, 0d);

    @ConfigPropertyEvent(name = "Base Location HUD")
    public static void onBaseLocationSetting() {
        if (MWEConfig.baseLocationHUDPosition.isEnabled() && ScoreboardTracker.isInMwGame()) {
            LocrawListener.setMegaWallsMap();
        }
    }

    @ConfigProperty(
            category = MEGA_WALLS, subCategory = I18n.format("mwe.config.category.mega_walls.hud"),
            name = "Warcry HUD", displayname = I18n.format("mwe.config.category.mega_walls.hud.warcryHUDPosition"),
            comment = I18n.format("mwe.config.category.mega_walls.hud.warcryHUDPosition.comment")
    public static final GuiPosition warcryHUDPosition = new GuiPosition(true, 0.65d, 1d);

    @ConfigProperty(
            category = FINAL_KILL_COUNTER, subCategory = I18n.format("mwe.config.category.final_kill_counter.hud"),
            name = "Final Kill Counter HUD", displayname = I18n.format("mwe.config.category.final_kill_counter.hud.fkcounterHUDPosition"),
            comment = I18n.format("mwe.config.category.final_kill_counter.hud.fkcounterHUDPosition.comment")
    public static final GuiPosition fkcounterHUDPosition = new GuiPosition(false, 0d, 0.1d);

    @ConfigProperty(
            category = FINAL_KILL_COUNTER, subCategory = I18n.format("mwe.config.category.final_kill_counter.hud"),
            name = "Compact mode", displayname = I18n.format("mwe.config.category.final_kill_counter.hud.fkcounterHUDCompact"),
            comment = I18n.format("mwe.config.category.final_kill_counter.hud.fkcounterHUDCompact.comment")
    public static boolean fkcounterHUDCompact = true;

    @ConfigProperty(
            category = FINAL_KILL_COUNTER, subCategory = I18n.format("mwe.config.category.final_kill_counter.hud"),
            name = "Compact HUD in Sidebar", displayname = I18n.format("mwe.config.category.final_kill_counter.hud.fkcounterHUDinSidebar"),
            comment = I18n.format("mwe.config.category.final_kill_counter.hud.fkcounterHUDinSidebar.comment")
    public static boolean fkcounterHUDinSidebar = true;

    @ConfigProperty(
            category = FINAL_KILL_COUNTER, subCategory = I18n.format("mwe.config.category.final_kill_counter.hud"),
            name = "Players mode", displayname = I18n.format("mwe.config.category.final_kill_counter.hud.fkcounterHUDShowPlayers"),
            comment = I18n.format("mwe.config.category.final_kill_counter.hud.fkcounterHUDShowPlayers.comment")
    public static boolean fkcounterHUDShowPlayers;

    @ConfigProperty(
            category = FINAL_KILL_COUNTER, subCategory = I18n.format("mwe.config.category.final_kill_counter.hud"),
            name = "Player amount", displayname = I18n.format("mwe.config.category.final_kill_counter.hud.fkcounterHUDPlayerAmount"),
            comment = I18n.format("mwe.config.category.final_kill_counter.hud.fkcounterHUDPlayerAmount.comment")
    public static int fkcounterHUDPlayerAmount = 3;

    @ConfigPropertyEvent(name = {
            "Final Kill Counter HUD",
            "Compact mode",
            "Compact HUD in Sidebar",
            "Players mode",
            "Player amount"})
    public static void onFKSHUDSetting() {
        GuiManager.fkCounterHUD.updateDisplayText();
    }

    @ConfigProperty(
            category = FINAL_KILL_COUNTER, subCategory = I18n.format("mwe.config.category.final_kill_counter.hud"),
            name = "Render HUD background", displayname = I18n.format("mwe.config.category.final_kill_counter.hud.fkcounterHUDDrawBackground"),
            comment = I18n.format("mwe.config.category.final_kill_counter.hud.fkcounterHUDDrawBackground.comment")
    public static boolean fkcounterHUDDrawBackground;

    @ConfigProperty(
            category = FINAL_KILL_COUNTER, subCategory = I18n.format("mwe.config.category.final_kill_counter.tablist"),
            name = "Finals in tablist", displayname = I18n.format("mwe.config.category.final_kill_counter.tablist.fkcounterHUDTablist"),
            comment = I18n.format("mwe.config.category.final_kill_counter.tablist.fkcounterHUDTablist.comment")
    public static boolean fkcounterHUDTablist = true;

    @ConfigProperty(
            category = FINAL_KILL_COUNTER, subCategory = I18n.format("mwe.config.category.final_kill_counter.chat"),
            name = "Show kill diff in chat", displayname = I18n.format("mwe.config.category.final_kill_counter.chat.showKillDiffInChat"),
            comment = I18n.format("mwe.config.category.final_kill_counter.chat.showKillDiffInChat.comment")
    public static boolean showKillDiffInChat = true;

    @ConfigProperty(
            category = HYPIXEL,
            name = "APIKey",
            comment = "Your Hypixel API Key",
            hidden = true)
    public static String APIKey = "";

    @ConfigProperty(
            category = HYPIXEL,
            name = "Hypixel Nick",
            comment = "Your nick on Hypixel",
            hidden = true)
    public static String hypixelNick = "";

    @ConfigProperty(
            category = HYPIXEL,
            name = "Short coin messages",
            comment = "Makes the §6coins §7and §2tokens§7 messages shorter by removing the network booster info. It also compacts the guild bonus message and coin message into one.\n"
                    + "\n"
                    + "§6+100 coins! (hypixel's Network booster) §bFINAL KILL\n"
                    + "§fWill become : \n"
                    + "§6+100 coins!§b FINAL KILL")
    public static boolean shortCoinMessage;

    @ConfigProperty(
            category = HYPIXEL,
            name = "Warp Protection",
            comment = "Adds confirmation when clicking the \"Play Again\" paper if you have players in your squad")
    public static boolean warpProtection = true;

    @ConfigProperty(
            category = NOCHEATERS, subCategory = I18n.format("mwe.config.category.nocheaters.general"),
            name = "Warning messages in chat", displayname = I18n.format("mwe.config.category.nocheaters.general.warningMessages"),
            comment = I18n.format("mwe.config.category.nocheaters.general.warningMessages.comment")
    public static boolean warningMessages;

    @ConfigPropertyEvent(name = "Warning messages in chat")
    public static void onWarningMessageSetting() {
        if (MWEConfig.warningMessages) {
            WarningMessages.printReportMessagesForWorld(false);
        } else {
            ChatHandler.deleteAllWarningMessages();
        }
    }

    @ConfigProperty(
            category = NOCHEATERS, subCategory = I18n.format("mwe.config.category.nocheaters.general"),
            name = "Show banned players", displayname = I18n.format("mwe.config.category.nocheaters.general.showBannedPlayers"),
            comment = I18n.format("mwe.config.category.nocheaters.general.showBannedPlayers.comment")
    public static boolean showBannedPlayers = true;

    @ConfigProperty(
            category = NOCHEATERS, subCategory = I18n.format("mwe.config.category.nocheaters.general"),
            name = "Delete Old Report", displayname = I18n.format("mwe.config.category.nocheaters.general.deleteOldReports"),
            comment = I18n.format("mwe.config.category.nocheaters.general.deleteOldReports.comment")
    public static boolean deleteOldReports;

    @ConfigProperty(
            category = NOCHEATERS, subCategory = I18n.format("mwe.config.category.nocheaters.general"),
            name = "Time delete reports", displayname = I18n.format("mwe.config.category.nocheaters.general.timeDeleteReport"),
            comment = I18n.format("mwe.config.category.nocheaters.general.timeDeleteReport.comment")
    public static int timeDeleteReport = 365;

    @ConfigProperty(
            category = NOCHEATERS, subCategory = I18n.format("mwe.config.category.nocheaters.icons"),
            name = "Show Warning Icons", displayname = I18n.format("mwe.config.category.nocheaters.icons.warningIconsOnNames"),
            comment = I18n.format("mwe.config.category.nocheaters.icons.warningIconsOnNames.comment")
    public static boolean warningIconsOnNames = true;

    @ConfigProperty(
            category = NOCHEATERS, subCategory = I18n.format("mwe.config.category.nocheaters.icons"),
            name = "Warning Icons In Tab Only", displayname = I18n.format("mwe.config.category.nocheaters.icons.warningIconsTabOnly"),
            comment = I18n.format("mwe.config.category.nocheaters.icons.warningIconsTabOnly.comment")
    public static boolean warningIconsTabOnly;

    @ConfigPropertyEvent(name = {
            "Show fake players in tab",
            "De-obfuscate names in tab",
            "Show Squad Icons",
            "Squad Icons In Tab Only",
            "Show Warning Icons",
            "Warning Icons In Tab Only",
            "Pink squadmates"})
    public static void refreshAllNames() {
        NameUtil.refreshAllNamesInWorld();
    }

    @ConfigProperty(
            category = NOCHEATERS, subCategory = I18n.format("mwe.config.category.nocheaters.chat"),
            name = "Report suggestions", displayname = I18n.format("mwe.config.category.nocheaters.chat.reportSuggestions"),
            comment = I18n.format("mwe.config.category.nocheaters.chat.reportSuggestions.comment")
    public static boolean reportSuggestions = true;

    @ConfigPropertyEvent(name = "Report suggestions")
    public static void onReportSuggestionSetting() {
        if (MWEConfig.reportSuggestions) {
            SoundUtil.playChatNotifSound();
        }
    }

    @ConfigProperty(
            category = NOCHEATERS, subCategory = I18n.format("mwe.config.category.nocheaters.chat"),
            name = "Censor cheaters messages in chat", displayname = I18n.format("mwe.config.category.nocheaters.chat.censorCheaterChatMsg"),
            comment = I18n.format("mwe.config.category.nocheaters.chat.censorCheaterChatMsg.comment")
    public static boolean censorCheaterChatMsg;

    @ConfigProperty(
            category = NOCHEATERS, subCategory = I18n.format("mwe.config.category.nocheaters.chat"),
            name = "Delete cheaters messages in chat", displayname = I18n.format("mwe.config.category.nocheaters.chat.deleteCheaterChatMsg"),
            comment = I18n.format("mwe.config.category.nocheaters.chat.deleteCheaterChatMsg.comment")
    public static boolean deleteCheaterChatMsg;

    @ConfigProperty(
            category = NOCHEATERS,
            name = "List of cheats that give a red icon",
            comment = "Players reported with one of theses cheats will appear with a red icon on their name",
            hidden = true)
    public static final List<String> redIconCheats = new ArrayList<>(Arrays.asList("autoblock", "bhop", "fastbreak", "noslowdown", "scaffold"));

    @ConfigProperty(
            category = NOCHEATERS,
            name = "List of cheats that don't give an icon",
            comment = "Players reported with only theses cheats will have no icon on their name",
            hidden = true)
    public static final List<String> noIconCheats = new ArrayList<>();

    @ConfigProperty(
            category = HACKER_DETECTOR, subCategory = I18n.format("mwe.config.category.hacker_detector.general"),
            name = "Hacker Detector", displayname = I18n.format("mwe.config.category.hacker_detector.general.hackerDetector"),
            comment = I18n.format("mwe.config.category.hacker_detector.general.hackerDetector.comment")
    public static boolean hackerDetector = true;

    @ConfigProperty(
            category = HACKER_DETECTOR, subCategory = I18n.format("mwe.config.category.hacker_detector.general"),
            name = "Add to report list", displayname = I18n.format("mwe.config.category.hacker_detector.general.addToReportList"),
            comment = I18n.format("mwe.config.category.hacker_detector.general.addToReportList.comment")
    public static boolean addToReportList = true;

    @ConfigProperty(
            category = HACKER_DETECTOR, subCategory = I18n.format("mwe.config.category.hacker_detector.flags"),
            name = "Show flag messages", displayname = I18n.format("mwe.config.category.hacker_detector.flags.showFlagMessages"),
            comment = I18n.format("mwe.config.category.hacker_detector.flags.showFlagMessages.comment")
    public static boolean showFlagMessages = true;

    @ConfigProperty(
            category = HACKER_DETECTOR, subCategory = I18n.format("mwe.config.category.hacker_detector.flags"),
            name = "Show flag type", displayname = I18n.format("mwe.config.category.hacker_detector.flags.showFlagMessageType"),
            comment = I18n.format("mwe.config.category.hacker_detector.flags.showFlagMessageType.comment")
    public static boolean showFlagMessageType = true;

    @ConfigProperty(
            category = HACKER_DETECTOR, subCategory = I18n.format("mwe.config.category.hacker_detector.flags"),
            name = "Compact flags in chat", displayname = I18n.format("mwe.config.category.hacker_detector.flags.compactFlagMessages"),
            comment = I18n.format("mwe.config.category.hacker_detector.flags.compactFlagMessages.comment")
    public static boolean compactFlagMessages = true;

    @ConfigProperty(
            category = HACKER_DETECTOR, subCategory = I18n.format("mwe.config.category.hacker_detector.flags"),
            name = "Show single flag message", displayname = I18n.format("mwe.config.category.hacker_detector.flags.oneFlagMessagePerGame"),
            comment = I18n.format("mwe.config.category.hacker_detector.flags.oneFlagMessagePerGame.comment")
    public static boolean oneFlagMessagePerGame;

    @ConfigProperty(
            category = HACKER_DETECTOR, subCategory = I18n.format("mwe.config.category.hacker_detector.flags"),
            name = "Sound when flagging", displayname = I18n.format("mwe.config.category.hacker_detector.flags.soundWhenFlagging"),
            comment = I18n.format("mwe.config.category.hacker_detector.flags.soundWhenFlagging.comment")
    public static boolean soundWhenFlagging;

    @ConfigProperty(
            category = HACKER_DETECTOR, subCategory = I18n.format("mwe.config.category.hacker_detector.flags"),
            name = "Show report button on flags", displayname = I18n.format("mwe.config.category.hacker_detector.flags.showReportButtonOnFlags"),
            comment = I18n.format("mwe.config.category.hacker_detector.flags.showReportButtonOnFlags.comment")
    public static boolean showReportButtonOnFlags = true;

    @ConfigProperty(
            category = HACKER_DETECTOR, subCategory = I18n.format("mwe.config.category.hacker_detector.flags"),
            name = "Flag message prefix", displayname = I18n.format("mwe.config.category.hacker_detector.flags.flagMessagePrefix"),
            comment = I18n.format("mwe.config.category.hacker_detector.flags.flagMessagePrefix.comment")
    public static String flagMessagePrefix = EnumChatFormatting.GOLD + "[" + EnumChatFormatting.DARK_GRAY + "NoCheaters" + EnumChatFormatting.GOLD + "]";

    @ConfigProperty(
            category = HACKER_DETECTOR, subCategory = I18n.format("mwe.config.category.hacker_detector.report"),
            name = "Auto-report cheaters", displayname = I18n.format("mwe.config.category.hacker_detector.report.autoreportFlaggedPlayers"),
            comment = I18n.format("mwe.config.category.hacker_detector.report.autoreportFlaggedPlayers.comment")
    public static boolean autoreportFlaggedPlayers = true;

    @ConfigPropertyEvent(name = "Auto-report cheaters")
    public static void onAutoreportSetting() {
        if (!MWEConfig.autoreportFlaggedPlayers) {
            ReportQueue.INSTANCE.queueList.clear();
        }
    }

    @ConfigProperty(
            category = HACKER_DETECTOR, subCategory = I18n.format("mwe.config.category.hacker_detector.report"),
            name = "Report HUD", displayname = I18n.format("mwe.config.category.hacker_detector.report.reportHUDPosition"),
            comment = I18n.format("mwe.config.category.hacker_detector.report.reportHUDPosition.comment")
    public static final GuiPosition reportHUDPosition = new GuiPosition(true, 0d, 1d);

    @ConfigProperty(
            category = HACKER_DETECTOR, subCategory = I18n.format("mwe.config.category.hacker_detector.report"),
            name = "Report HUD in chat only", displayname = I18n.format("mwe.config.category.hacker_detector.report.showReportHUDonlyInChat"),
            comment = I18n.format("mwe.config.category.hacker_detector.report.showReportHUDonlyInChat.comment")
    public static boolean showReportHUDonlyInChat;

    @ConfigProperty(
            category = HACKER_DETECTOR, subCategory = I18n.format("mwe.config.category.hacker_detector.bypass"),
            name = "Fix autoblock animation bypass", displayname = I18n.format("mwe.config.category.hacker_detector.bypass.fixAutoblockAnimationBypass"),
            comment = I18n.format("mwe.config.category.hacker_detector.bypass.fixAutoblockAnimationBypass.comment")
    public static boolean fixAutoblockAnimationBypass = true;

    public static boolean debugLogging;

    @ConfigProperty(
            category = HACKER_DETECTOR, subCategory = I18n.format("mwe.config.category.hacker_detector.debug"),
            name = "Replay Killaura Flags", displayname = I18n.format("mwe.config.category.hacker_detector.debug.debugKillauraFlags"),
            comment = I18n.format("mwe.config.category.hacker_detector.debug.debugKillauraFlags.comment")
    public static boolean debugKillauraFlags;

    @ConfigProperty(
            category = EXTERNAL,
            name = "Hide Optifine Hats",
            comment = "Hides the hats added by Optifine during Halloween and Christmas\n"
                    + "§eRequires game restart to be fully effective")
    public static boolean hideOptifineHats;

    @ConfigPropertyHideOverride(name = "Hide Optifine Hats")
    public static boolean hideOptifineHatsSetting() {
        return !FMLClientHandler.instance().hasOptifine();
    }

    @ConfigProperty(
            category = EXTERNAL,
            name = "Hide Orange's Toggle Sprint HUD",
            comment = "Hides the Toggle Sprint HUD from Orange's Marshall Simple Mod")
    public static boolean hideToggleSprintText;

    @ConfigPropertyHideOverride(name = "Hide Orange's Toggle Sprint HUD")
    public static boolean hideOrangeToggleSprintSetting() {
        return !Loader.isModLoaded("orangesimplemod");
    }

    @ConfigProperty(
            category = SQUAD, subCategory = I18n.format("mwe.config.category.squad.general"),
            name = "Pink squadmates", displayname = I18n.format("mwe.config.category.squad.general.pinkSquadmates"),
            comment = I18n.format("mwe.config.category.squad.general.pinkSquadmates.comment")
    public static boolean pinkSquadmates = true;

    @ConfigProperty(
            category = SQUAD, subCategory = I18n.format("mwe.config.category.squad.general"),
            name = "Nick Hider", displayname = I18n.format("mwe.config.category.squad.general.nickHider"),
            comment = I18n.format("mwe.config.category.squad.general.nickHider.comment")
    public static boolean nickHider = true;

    @ConfigProperty(
            category = SQUAD, subCategory = I18n.format("mwe.config.category.squad.general"),
            name = "Keep first letter squadname", displayname = I18n.format("mwe.config.category.squad.general.keepFirstLetterSquadnames"),
            comment = I18n.format("mwe.config.category.squad.general.keepFirstLetterSquadnames.comment")
    public static boolean keepFirstLetterSquadnames = true;

    @ConfigProperty(
            category = SQUAD, subCategory = I18n.format("mwe.config.category.squad.icons"),
            name = "Show Squad Icons", displayname = I18n.format("mwe.config.category.squad.icons.squadIconOnNames"),
            comment = I18n.format("mwe.config.category.squad.icons.squadIconOnNames.comment")
    public static boolean squadIconOnNames = true;

    @ConfigProperty(
            category = SQUAD, subCategory = I18n.format("mwe.config.category.squad.icons"),
            name = "Squad Icons In Tab Only", displayname = I18n.format("mwe.config.category.squad.icons.squadIconTabOnly"),
            comment = I18n.format("mwe.config.category.squad.icons.squadIconTabOnly.comment")
    public static boolean squadIconTabOnly;

    @ConfigProperty(
            category = SQUAD, subCategory = I18n.format("mwe.config.category.squad.hud"),
            name = "Squad HUD", displayname = I18n.format("mwe.config.category.squad.hud.squadHUDPosition"),
            comment = I18n.format("mwe.config.category.squad.hud.squadHUDPosition.comment")
    public static final GuiPosition squadHUDPosition = new GuiPosition(true, 0.25d, 0d);

    @ConfigProperty(
            category = "Updates",
            name = "Automatic Update",
            comment = "Updates the mod automatically")
    public static boolean automaticUpdate = true;

}
