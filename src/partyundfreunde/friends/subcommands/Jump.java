package de.SoonMitte.partyandfriends.friends.subcommands;

import de.SoonMitte.partyandfriends.api.friends.ServerConnector;
import de.SoonMitte.partyandfriends.api.friends.abstractcommands.FriendSubCommand;
import de.SoonMitte.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.SoonMitte.partyandfriends.api.pafplayers.PAFPlayer;
import de.SoonMitte.partyandfriends.utilities.PatterCollection;
import de.SoonMitte.partyandfriends.utilities.StandardConnector;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.regex.Matcher;

import static de.SoonMitte.partyandfriends.main.Main.getInstance;
import static de.SoonMitte.partyandfriends.main.Main.getPlayerManager;

/***
 * The command jump
 *
 * @author SoonMitte
 * @version 1.0.0
 */
public class Jump extends FriendSubCommand {
	private static ServerConnector connector = new StandardConnector();

	public Jump(String[] pCommands, int pPriority, String pHelp) {
		super(pCommands, pPriority, pHelp);
	}

	/**
	 * Sets the server connector, which will be used to join a server.
	 *
	 * @param pConnector The connector
	 */
	public static void setServerConnector(ServerConnector pConnector) {
		connector = pConnector;
	}

	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		if (!isPlayerGiven(pPlayer, args))
			return;
		PAFPlayer playerQuery = getPlayerManager().getPlayer(args[1]);
		if (!isPlayerOnline(pPlayer, playerQuery))
			return;
		OnlinePAFPlayer friend = (OnlinePAFPlayer) playerQuery;
		if (!isAFriendOf(pPlayer, friend))
			return;
		ServerInfo toJoin = friend.getServer();
		if (!serverExists(pPlayer, toJoin))
			return;
		if (isAlreadyOnServer(pPlayer.getPlayer(), toJoin))
			return;
		if (!allowsJumps(pPlayer, friend))
			return;
		connector.connect(pPlayer.getPlayer(), toJoin);
		pPlayer.sendMessage(
				new TextComponent(
						getInstance().getFriendsPrefix() + PatterCollection.PLAYER_PATTERN
								.matcher(getInstance().getMessagesYml()
										.getString("Friends.Command.Jump.JoinedTheServer"))
								.replaceAll(Matcher.quoteReplacement(friend.getDisplayName()))));
	}

	private boolean serverExists(OnlinePAFPlayer pPlayer, ServerInfo toJoin) {
		if (toJoin != null)
			return true;
		pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix()
				+ getInstance().getMessagesYml().getString("Friends.Command.Jump.CanNotJump")));
		pPlayer.sendMessage(HELP);
		return false;
	}

	private boolean allowsJumps(OnlinePAFPlayer pPlayer, OnlinePAFPlayer pQueryPlayer) {
		if (pQueryPlayer.getSettingsWorth(4) == 1) {
			pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix()
					+ getInstance().getMessagesYml().getString("Friends.Command.Jump.CanNotJump")));
			pPlayer.sendMessage(new TextComponent(HELP));
			return false;
		}
		return true;
	}

	private boolean isAlreadyOnServer(ProxiedPlayer pPlayer, ServerInfo pToJoin) {
		if (pToJoin.equals(pPlayer.getServer().getInfo())) {
			pPlayer.sendMessage(new TextComponent(getInstance().getFriendsPrefix()
					+ getInstance().getMessagesYml().getString("Friends.Command.Jump.AlreadyOnTheServer")));
			pPlayer.sendMessage(new TextComponent(HELP));
			return true;
		}
		return false;
	}

	private boolean isPlayerOnline(OnlinePAFPlayer pSender, PAFPlayer pQueryPlayer) {
		if (!pQueryPlayer.isOnline()) {
			pSender.sendMessage(new TextComponent(getInstance().getFriendsPrefix() + PatterCollection.PLAYER_PATTERN
					.matcher(getInstance().getMessagesYml().getString("Friends.General.PlayerIsOffline"))
					.replaceAll(Matcher.quoteReplacement(pQueryPlayer.getName()))));
			pSender.sendMessage(new TextComponent(HELP));
			return false;
		}
		return true;
	}
}
