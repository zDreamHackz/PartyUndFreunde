package de.SoonMitte.partyandfriends.friends.subcommands;

import de.SoonMitte.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.SoonMitte.partyandfriends.main.Main;
import de.SoonMitte.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.SoonMitte.partyandfriends.api.pafplayers.PAFPlayer;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.regex.Matcher;

import static de.SoonMitte.partyandfriends.main.Main.getPlayerManager;
import static de.SoonMitte.partyandfriends.utilities.PatterCollection.PLAYER_PATTERN;

/**
 * The command remove
 *
 * @author SoonMitte
 * @version 1.0.0
 */
public class Remove extends FriendSubCommand {

	public Remove(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp);
	}

	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		if (!isPlayerGiven(pPlayer, args))
			return;
		PAFPlayer playerQuery = getPlayerManager().getPlayer(args[1]);
		if (!isAFriendOf(pPlayer, playerQuery))
			return;
		pPlayer.removeFriend(playerQuery);
		pPlayer.sendMessage(new TextComponent(Main.getInstance().getFriendsPrefix() + PLAYER_PATTERN.matcher(Main.getInstance()
				.getMessagesYml().getString("Friends.Command.Remove.Removed")).replaceAll(Matcher.quoteReplacement(args[1]))));
	}

}
