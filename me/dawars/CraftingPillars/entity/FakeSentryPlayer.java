package me.dawars.CraftingPillars.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class FakeSentryPlayer extends EntityPlayer
{

	public FakeSentryPlayer(World world, String name) {
		super(world, name);
	}

	@Override
	public void sendChatToPlayer(ChatMessageComponent chatmessagecomponent) {
		
	}

	@Override
	public boolean canCommandSenderUseCommand(int i, String s) {
		return false;
	}

	@Override
	public ChunkCoordinates getPlayerCoordinates() {
		return new ChunkCoordinates(0, 0, 0);
	}

}
