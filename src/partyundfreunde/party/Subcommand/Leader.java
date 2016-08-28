package de.SoonMitte.partyandfriends.party.subcommand;

import de.SoonMitte.partyandfriends.api.party.abstractcommands.LeaderNeededCommand;
import de.SoonMitte.partyandfriends.main.Main;
import de.SoonMitte.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.SoonMitte.partyandfriends.api.pafplayers.PAFPlayer;
import de.SoonMitte.partyandfriends.api.party.PlayerParty;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.regex.Matcher;

import static de.SoonMitte.partyandfriends.main.Main.getPlayerManager;
import static de.SoonMitte.partyandfriends.utilities.PatterCollection.NEW_LEADER_PATTERN;

/**
 * The class which will be executed on /party leader
 *
 * @author SoonMitte
 * @version 1.0.0
 */
public class Leader extends LeaderNeededCommand {

	public Leader(String[] pCommands, int pPriority, String pHelpText) {
		super(pCommands, pPriority, pHelpText);
	}

	/**
	 * Will be executed on /party leader
	 *
	 * @param pPlayer The player
	 * @param args    The arguments
	 */
	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		PlayerParty party = Main.getPartyManager().getParty(pPlayer);
		if (!standardCheck(pPlayer, party, args))
			return;
		PAFPlayer player = getPlayerManager().getPlayer(args[0]);
		if (!checkIsInParty(pPlayer, player, party, args))
			return;
		party.addPlayer(pPlayer);
		party.setLeader((OnlinePAFPlayer) player);
		party.sendMessage(
				new TextComponent(
						Main.getInstance().getPartyPrefix() + NEW_LEADER_PATTERN
								.matcher(Main.getInstance().getMessagesYml()
										.getString("Party.Command.Leader.NewLeaderIs"))
								.replaceAll(Matcher.quoteReplacement(player.getDisplayName()))));
	}
}
