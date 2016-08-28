package de.SoonMitte.partyandfriends.api.friends.abstractcommands;

import de.SoonMitte.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.SoonMitte.partyandfriends.api.pafplayers.PAFPlayer;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.regex.Matcher;

import static de.SoonMitte.partyandfriends.main.Main.getInstance;
import static de.SoonMitte.partyandfriends.utilities.PatterCollection.PLAYER_PATTERN;

public abstract class RequestReactionsCommands extends FriendSubCommand {

	protected RequestReactionsCommands(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp);
	}

	protected boolean hasNoRequest(OnlinePAFPlayer pPlayer, PAFPlayer pQueryPlayer) {
		if ((!pPlayer.hasRequestFrom(pQueryPlayer))) {
			pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix() + PLAYER_PATTERN.matcher(getInstance()
					.getMessagesYml().getString("Friends.Command.Accept.ErrorNoFriendShipInvitation")).replaceAll(Matcher.quoteReplacement(pQueryPlayer.getName()))));
			pPlayer.sendMessage(new TextComponent(HELP));
			return true;
		}
		return false;
	}

}
