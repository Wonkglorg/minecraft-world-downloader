package game.data.entity.player;

import config.Config;
import config.Option;
import config.Version;
import game.data.entity.PlayerEntity;
import packets.DataTypeProvider;
import packets.UUID;

import java.util.Map;

public abstract class PlayerReader{
	
	// https://minecraft.wiki/w/Java_Edition_protocol/Packets#Spawn_Entity#PlayerInfoUpdate
	public abstract void updatePlayerAction(Map<UUID, PlayerEntity> players, DataTypeProvider provider);
	
	public static PlayerReader getVersioned() {
		return Config.versionReporter().select(PlayerReader.class,
				Option.of(Version.V1_21_5, PlayerReader_1_21_5::new),
				Option.of(Version.ANY, PlayerReader_1_20::new));
	}
	
}
