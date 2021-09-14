package derfl007.roads.common.commands;

import derfl007.roads.common.items.ItemTrafficRemote;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.server.command.CommandTreeBase;

public abstract class CommandTrafficLightsTreeBase extends CommandTreeBase {

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args != null && args.length == 1 && "help".equals(args[0])) {
			sender.sendMessage(new TextComponentTranslation(getHelp()));
			return;
		}
		EntityPlayer player;
		try {
			player = getCommandSenderAsPlayer(sender);
		} catch (PlayerNotFoundException ex) {
			sender.sendMessage(new TextComponentTranslation("command.trafficlights.invalidsender"));
			return;
		}
		if (!(player.getHeldItemMainhand().getItem() instanceof ItemTrafficRemote)) {
			sender.sendMessage(new TextComponentTranslation("command.trafficlights.invalidhelditem"));
			return;
		}

		super.execute(server, sender, args);
	}

	public abstract String getHelp();

}
