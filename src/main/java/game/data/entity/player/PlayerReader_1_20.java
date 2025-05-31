package game.data.entity.player;

import game.data.entity.PlayerEntity;
import packets.DataTypeProvider;
import packets.UUID;

import java.util.Map;

public class PlayerReader_1_20 extends PlayerReader{
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
					provider.readUUID();
					provider.readLong();
					int encKeySz = provider.readVarInt();
					provider.readByteArray(encKeySz);
					int pubKeySz = provider.readVarInt();
					provider.readByteArray(pubKeySz);
				}
			}
			
			if((actions & 0x04) > 0){
				provider.readVarInt();
			}
			
			if((actions & 0x08) > 0){
				provider.readBoolean();
			}
			
			if((actions & 0x10) > 0){
				provider.readVarInt();
			}
			
			if((actions & 0x20) > 0){
				boolean displayName = provider.readBoolean();
				if(displayName){
					provider.readChat();
				}
			}
		}
	}
}
