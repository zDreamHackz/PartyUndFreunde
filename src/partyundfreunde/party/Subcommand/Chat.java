package de.SoonMitte.partyandfriends.party.subcommand;

import de.SoonMitte.partyandfriends.api.party.PartyAPI;
import de.SoonMitte.partyandfriends.api.party.abstractcommands.PartySubCommand;
import de.SoonMitte.partyandfriends.main.Main;
import de.SoonMitte.partyandfriends.api.pafplayers.OnlinePAFPlayer;

/**
 * The /p command
 *
 * @author SoonMitte
 * @version 1.0.0
 */
public class p extends PartySubCommand {

	public p(String[] pCommands, int pPriority, String pHelpText) {
		super(pCommands, pPriority, pHelpText);
	}

	@Override
	public boolean hasAccess(int pPermissionHeight) {
		return PartyAPI.PARTY_MEMBER_PERMISSION_HEIGHT <= pPermissionHeight;
	}

	@Override
	public void onCommand(OnlinePAFPlayer pPlayer, String[] args) {
		Main.getInstance().getPartyChatCommand().send(pPlayer, args);
	}
}
