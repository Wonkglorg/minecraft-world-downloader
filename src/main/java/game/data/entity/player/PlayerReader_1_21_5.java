package game.data.entity.player;

import game.data.entity.PlayerEntity;
import packets.DataTypeProvider;
import packets.UUID;

import java.util.Map;

public class PlayerReader_1_21_5 extends PlayerReader{
	//reference player Actions player_info_update
	@Override
	public void updatePlayerAction(Map<UUID, PlayerEntity> players, DataTypeProvider provider) {
		byte actions = provider.readNext();
		int playerCnt = provider.readVarInt();
		
		for(int i = 0; i < playerCnt; i++){
			packets.UUID uuid = provider.readUUID();
			
			if((actions & 0x01) > 0){
				PlayerEntity player = PlayerEntity.fromUUID(uuid);
				players.put(uuid, player);
				

				
				String name = provider.readString();
				int properties = provider.readVarInt();
				for(int j = 0; j < properties; j++){
					String texturesKey = provider.readString();
					String texturesBase64Value = provider.readString();
					boolean signed = provider.readBoolean();
					if(signed){
						provider.readString();
					}
				}
			}
			
			if((actions & 0x02) > 0){
				boolean signature = provider.readBoolean();
				if(signature){
					UUID val = provider.readUUID();
					long val2 = provider.readLong();
					int encKeySz = provider.readVarInt();
					byte[] bytes = provider.readByteArray(encKeySz);
					int pubKeySz = provider.readVarInt();
					byte[] array = provider.readByteArray(pubKeySz);
					System.out.println();
				}
			}
			
			if((actions & 0x04) > 0){
				//gamemode
				provider.readVarInt();
			}
			
			if((actions & 0x08) > 0){
				//if player is listed on player list
				provider.readBoolean();
			}
			
			if((actions & 0x10) > 0){
				//ping in ms
				provider.readVarInt();
			}
			
			if((actions & 0x20) > 0){
				boolean displayName = provider.readBoolean();
				if(displayName){
					//display name only sent if it hasDisplay
					provider.readChat();
				}
			}
		}
	}
}
