package de.SoonMitte.partyandfriends.party.subcommand;

import de.SoonMitte.partyandfriends.api.party.PartyAPI;
import de.SoonMitte.partyandfriends.api.party.abstractcommands.PartySubCommand;
import de.SoonMitte.partyandfriends.main.Main;
import de.SoonMitte.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.SoonMitte.partyandfriends.api.pafplayers.PAFPlayer;
import de.SoonMitte.partyandfriends.api.party.PlayerParty;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.regex.Matcher;

import static de.SoonMitte.partyandfriends.main.Main.getPlayerManager;
import static de.SoonMitte.partyandfriends.utilities.PatterCollection.PLAYER_PATTERN;

/**
 * The class which will be executed on /party join
 *
 * @author SoonMitte
 * @version 1.0.0
 */
public class Join extends PartySubCommand {

	public Join(String[] pCommands, int pPriority, String pHelpText) {
		super(pCommands, pPriority, pHelpText);
	}

	/**
	 * Will be executed on /party join
	 *
	 * @param pPlayer The player
	 * @param args    The arguments
	 */
	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		if (!isPlayerGiven(pPlayer, args))
			return;
		if (isInParty(pPlayer))
			return;
		PAFPlayer pl = getPlayerManager().getPlayer(args[0]);
		if (!pl.isOnline()) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.Join.PlayerHasNoParty")));
			return;
		}
		OnlinePAFPlayer onlinePAFPlayer = (OnlinePAFPlayer) pl;
		PlayerParty party = Main.getPartyManager().getParty(onlinePAFPlayer);
		if (hasNoParty(pPlayer, party))
			return;
		if (party.addPlayer(pPlayer))
			party.sendMessage(
					new TextComponent(
							Main.getInstance().getPartyPrefix() + PLAYER_PATTERN
									.matcher(Main.getInstance().getMessagesYml()
											.getString("Party.Command.Join.PlayerHasJoined"))
									.replaceAll(Matcher.quoteReplacement(pPlayer.getDisplayName()))));
		else
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.Join.ErrorNoInvitation")));
	}

	@Override
	public boolean hasAccess(int pPermissionHeight) {
		return PartyAPI.NO_PARTY_PERMISSION_HEIGHT == pPermissionHeight;
	}

	private boolean hasNoParty(OnlinePAFPlayer pPlayer, PlayerParty pParty) {
		if (pParty == null) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.Join.PlayerHasNoParty")));
			return true;
		}
		return false;
	}

	private boolean isInParty(OnlinePAFPlayer pPlayer) {
		if (Main.getPartyManager().getParty(pPlayer) != null) {
			pPlayer.sendMessage(new TextComponent(Main.getInstance().getPartyPrefix()
					+ Main.getInstance().getMessagesYml().getString("Party.Command.Join.AlreadyInAPartyError")));
			return true;
		}
		return false;
	}
}
