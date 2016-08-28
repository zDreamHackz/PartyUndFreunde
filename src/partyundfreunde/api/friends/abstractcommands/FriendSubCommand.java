package de.SoonMitte.partyandfriends.api.friends.abstractcommands;

import de.SoonMitte.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.SoonMitte.partyandfriends.api.pafplayers.PAFPlayer;
import de.SoonMitte.partyandfriends.utilities.SubCommand;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.regex.Matcher;

import static de.SoonMitte.partyandfriends.main.Main.getInstance;
import static de.SoonMitte.partyandfriends.utilities.PatterCollection.PLAYER_PATTERN;

public abstract class FriendSubCommand extends SubCommand implements Comparable<SubCommand> {

	protected FriendSubCommand(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, new TextComponent(pHelp), getInstance().getFriendsPrefix());
	}

	protected boolean isPlayerGiven(OnlinePAFPlayer pPlayer, String[] args) {
		if (args.length < 2) {
			pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix()
					+ getInstance().getMessagesYml().getString("Friends.General.NoPlayerGiven")));
			pPlayer.sendMessage(new TextComponent(HELP));
			return false;
		}
		return true;
	}

	protected boolean isAFriendOf(OnlinePAFPlayer pPlayer, PAFPlayer pGivenPlayer) {
		if (!pPlayer.isAFriendOf(pGivenPlayer)) {
			pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix() + PLAYER_PATTERN.matcher(getInstance()
					.getMessagesYml().getString("Friends.General.PlayerIsOffline")).replaceAll(Matcher.quoteReplacement(pGivenPlayer.getName()))));
			pPlayer.sendMessage(new TextComponent(HELP));
			return false;
		}
		return true;
	}


}
