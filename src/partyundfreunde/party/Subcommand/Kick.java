package de.SoonMitte.partyandfriends.party.subcommand;

import de.SoonMitte.partyandfriends.api.party.abstractcommands.LeaderNeededCommand;
import de.SoonMitte.partyandfriends.main.Main;
import de.SoonMitte.partyandfriends.api.pafplayers.OnlinePAFPlayer;
import de.SoonMitte.partyandfriends.api.pafplayers.PAFPlayer;
import de.SoonMitte.partyandfriends.api.party.PlayerParty;

import static de.SoonMitte.partyandfriends.main.Main.getPlayerManager;

/**
 * The /party kick command
 *
 * @author SoonMitte
 * @version 1.0.0
 */
public class Kick extends LeaderNeededCommand {
	public Kick(String[] pCommands, int pPriority, String pHelpText) {
		super(pCommands, pPriority, pHelpText);
	}

	/**
	 * Will be executed on /party kick
	 *
	 * @param pPlayer The player
	 * @param args    The arguments
	 */
	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		PlayerParty party = Main.getPartyManager().getParty(pPlayer);
		if (!standardCheck(pPlayer, party, args))
			return;
		PAFPlayer toKick = getPlayerManager().getPlayer(args[0]);
		if (!checkIsInParty(pPlayer, toKick, party, args))
			return;
		party.kickPlayer((OnlinePAFPlayer) toKick);
	}

}
