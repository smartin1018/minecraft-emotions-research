package me.sagemartin.minecraftresearch;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinecraftResearch extends JavaPlugin {

    private final String[] emotions = new String[] {
            "joy", "sadness", "anger", "disgust", "neutral"
    };
    private final long DURATION_MILLIS = 6000;
    private final long PERIOD_MILLIS = 10000;
    private final int TRIALS = 3;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equals("startproject")) {
            start();
            return true;
        }

        return false;
    }

    public void start() {
        long delay = 0;
        sendMessage(ChatColor.BLUE + "Welcome actors!", delay);
        sendMessage(ChatColor.BLUE + "You will be asked to perform different emotions using your Minecraft person " + String.join(", ", emotions), delay += 2000);
        sendMessage(ChatColor.BLUE + "Each act will take " + DURATION_MILLIS / 1000 + " seconds to perform, and " + PERIOD_MILLIS / 1000 + " seconds in between each act.", delay += 6000);
        sendMessage(ChatColor.BLUE + "You will perform " + TRIALS + " act" + (TRIALS != 1 ? "s" : "") + " of each emotion.", delay += 5000);
        sendMessage(ChatColor.BLUE + "When the act starts, you will hear a sound. It sounds like this...", delay += 3000);
        playStartNoise(delay += 4000);
        sendMessage(ChatColor.BLUE + "When the act stops, you will hear a sound. It sounds like this...", delay += 2000);
        playEndNoise(delay += 4000);
        sendMessage(ChatColor.BLUE + "You will have 60 seconds to ask questions, then we will proceed!", delay += 2000);
        sendMessage(ChatColor.RED + "30 seconds remaining", delay += 30000);
        sendMessage(ChatColor.RED + "15 seconds remaining", delay += 15000);
        sendMessage(ChatColor.RED + "5 seconds remaining", delay += 10000);
        sendMessage(ChatColor.BLUE + "Starting!", delay += 5000);
        delay += 2000;
        for (String emotion : emotions) {
            sendMessage(ChatColor.BLUE + "Emotion: " + ChatColor.GREEN + emotion, delay);
            delay += PERIOD_MILLIS;
            for (int i = 0; i < TRIALS; i++) {
                sendMessage(ChatColor.BLUE + "Act " + (i + 1), delay);
                sendMessage(ChatColor.GREEN + "Start!", delay += 2000);
                playStartNoise(delay);
                sendMessage(ChatColor.GREEN + "Stop!", delay += DURATION_MILLIS);
                playEndNoise(delay);
                delay += PERIOD_MILLIS;
            }
        }
        delay -= PERIOD_MILLIS;
        sendMessage(ChatColor.BLUE + "That wasn't so hard, was it? Thanks for coming!", delay + 2000);
    }

    // Utility Methods:
    public void sendMessage(String message, long delayMillis) {
        Bukkit.getScheduler().runTaskLater(this, () -> Bukkit.broadcastMessage(message), delayMillis / 50); // convert ms to ticks
    }

    public void playStartNoise(long delayMillis) {
        Bukkit.getScheduler().runTaskLater(this, () -> Bukkit.getOnlinePlayers().forEach(player -> player.playNote(player.getLocation(), Instrument.PLING, Note.natural(1, Note.Tone.F))), delayMillis / 50);
    }

    public void playEndNoise(long delayMillis) {
        Bukkit.getScheduler().runTaskLater(this, () -> Bukkit.getOnlinePlayers().forEach(player -> player.playNote(player.getLocation(), Instrument.PLING, Note.natural(0, Note.Tone.G))), delayMillis / 50);
    }
}
