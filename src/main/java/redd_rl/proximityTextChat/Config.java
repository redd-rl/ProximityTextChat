package redd_rl.proximityTextChat;

import com.moandjiezana.toml.Toml;
import java.io.File;

public class Config {

    public Settings settings = new Settings();
    public Messages messages = new Messages();

    public static class Settings {
        public int maximumReceivingDistance = 30; // Maximum distance before other players lose the ability to hear you.
        public String globalPrefix = "!"; // Prefix to add before the message in order to make it visible for all players.
        public boolean cancelMessage = true; // If the player's message should be canceled for them if nobody can hear them.
    }

    public static class Messages {
        // Available placeholders are:
        // {player} - the player's name
        // {message} - the player's message
        public String noReader = "&cNobody is around to hear you."; // The returned message when nobody is around to hear a player's message.
        public String spyFormat = "&d&lSPY&r &7» &a{player}: &7{message}"; // The format for spy messages, requires permission: proximitytextchat.spy
        public String globalFormat = "&6&lGlobal: {player}&r &7» &r{message}"; // The format for global messages, requires permission: proximitytextchat.global
    }

    public static Config load(File configFile) {
        return new Toml().read(configFile).to(Config.class);
    }
}