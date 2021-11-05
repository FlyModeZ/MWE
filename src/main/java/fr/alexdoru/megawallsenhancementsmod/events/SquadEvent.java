package fr.alexdoru.megawallsenhancementsmod.events;

import fr.alexdoru.fkcountermod.utils.MinecraftUtils;
import fr.alexdoru.fkcountermod.utils.ScoreboardUtils;
import fr.alexdoru.megawallsenhancementsmod.misc.NameModifier;
import fr.alexdoru.nocheatersmod.NoCheatersMod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.event.entity.player.PlayerEvent.NameFormat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.List;

public class SquadEvent {

    // TODO se rajouter soit meme et mettre une commande pour mettre son propre nick

    private static final HashMap<String, String> squadmap = new HashMap<>();
    private static final IChatComponent isquadprefix = new ChatComponentText(EnumChatFormatting.GOLD + "[" + EnumChatFormatting.DARK_GREEN + "S" + EnumChatFormatting.GOLD + "] ");
    private static final String squadprefix = isquadprefix.getFormattedText();

    @SubscribeEvent
    public void onNameFormat(NameFormat event) {

        if (!NoCheatersMod.areIconsToggled()) {
            return;
        }

        String squadname = squadmap.get(event.username);
        if (squadname != null) { // TODO ca met multiples [S]
            event.displayname = squadname;
            EntityPlayer player = (EntityPlayer) event.entity;
            player.addPrefix(isquadprefix);
        }

    }

    public static void addPlayer(String playername) {
        squadmap.put(playername, playername);
        NameModifier.transformDisplayName(playername);
    }

    public static void addPlayer(String playername, String newname) {
        squadmap.put(playername, newname);
        NameModifier.transformDisplayName(playername);
    }

    public static boolean removePlayer(String playername) {
        boolean success = squadmap.remove(playername) != null;
        if (success) {
            NameModifier.transformDisplayName(playername);
        }
        return success;
    }

    public static void clearSquad() {
        squadmap.clear();
    }

    public static HashMap<String, String> getSquad() {
        return squadmap;
    }

    public static IChatComponent getIprefix() {
        return isquadprefix;
    }

    public static String getprefix() {
        return squadprefix;
    }

    /**
     * At the start of any game it checks the scoreboard for teamates and adds them to the team
     * if you have the same teamates it keeps the nicks you gave them
     */
    public static void formSquad() {

        Minecraft mc = Minecraft.getMinecraft();

        if (mc.theWorld == null || !MinecraftUtils.isHypixel()) {
            return;
        }

        Scoreboard scoreboard = mc.theWorld.getScoreboard();
        if (scoreboard == null) {
            return;
        }

        boolean isinMW = ScoreboardUtils.getUnformattedSidebarTitle(scoreboard).contains("MEGA WALLS");

        if (!isinMW) {
            return;
        }

        List<String> scoresRaw = ScoreboardUtils.getUnformattedSidebarText();
        boolean found_teammates = false;

        HashMap<String, String> newsquad = new HashMap<>();

        for (String line : scoresRaw) {

            if (found_teammates) {

                if (line.contains("www.hypixel.net") || line.contains("HAPPY HOUR!") || line.equals("")) {
                    break;
                }

                String nameonscoreboard = line.replace(" ", "");
                String squadmate = squadmap.get(nameonscoreboard);
                /*
                 * the player was already in the squad before, reuse the same name transformation
                 */
                if (squadmate == null) {
                    newsquad.put(nameonscoreboard, nameonscoreboard);
                } else {
                    newsquad.put(nameonscoreboard, squadmate);
                }

            }

            if (line.contains("Teammates:")) {
                found_teammates = true;
            }

        }

        squadmap.clear();
        squadmap.putAll(newsquad);

    }

}
