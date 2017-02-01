package util;

import java.util.ArrayList;
import java.util.Set;
import java.util.Vector;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;

import character.AdventureGirl;
import character.BasePlayer;
import character.Gorilla;
import character.Octopus;
import character.Robot;
import packet.BossTargetsInfo;
import packet.DamageInfo;
import packet.DeathInfo;
import packet.DisconnectionInfo;
import packet.EnemyInfo;
import packet.Packet;
import packet.PlayerInfo;
import packet.PositionInfo;

public class KryoInitializer {
	//register the classes in kyro. 
	public static void register(Kryo kryo){
		kryo.register(PlayerInfo.class);
		kryo.register(Packet.class);
		kryo.register(DisconnectionInfo.class);
		kryo.register(EnemyInfo.class);
		kryo.register(ArrayList.class);
		kryo.register(Vector.class);
		kryo.register(Gorilla.class);
		kryo.register(Octopus.class);
		kryo.register(AdventureGirl.class);
		kryo.register(Robot.class);
		kryo.register(BasePlayer.class);
		kryo.register(sprites.GorillaLoader.class);
		kryo.register(sprites.OctopusLoader.class);
		kryo.register(sprites.AdventureGirlLoader.class);
		kryo.register(sprites.RobotLoader.class);
		kryo.register(sprites.BossLoader.class);
		kryo.register(packet.UserInfo.class);
		kryo.register(packet.UpdateDatabase.class);
		kryo.register(packet.UserAuthenticationError.class);
		kryo.register(packet.UpdateUserInfo.class);
		kryo.register(packet.SwitchToAvatarScreenMessage.class);
		kryo.register(packet.UpdateGamesPlayedStats.class);
		kryo.register(com.badlogic.gdx.graphics.Color.class);
		kryo.register(com.badlogic.gdx.graphics.Texture.class);
		kryo.register(com.badlogic.gdx.physics.box2d.World.class);
		kryo.register(com.badlogic.gdx.physics.box2d.Body.class);
		kryo.register(com.badlogic.gdx.graphics.glutils.FileTextureData.class);
		kryo.register(com.badlogic.gdx.backends.lwjgl.LwjglFileHandle.class);
		kryo.register(java.io.File.class);
		kryo.register(com.badlogic.gdx.Files.FileType.class);
		kryo.register(com.badlogic.gdx.graphics.Pixmap.Format.class);
		kryo.register(com.badlogic.gdx.graphics.Texture.TextureFilter.class);
		kryo.register(com.badlogic.gdx.graphics.Texture.TextureWrap.class);
		kryo.register(float[].class);
		kryo.register(Set.class);
		kryo.register(character.Character.AttackState.class);
		kryo.register(character.Character.Direction.class);
		kryo.register(character.Character.Type.class);
		kryo.register(com.badlogic.gdx.utils.Array.class);
		kryo.register(Object[].class);
		kryo.register(com.badlogic.gdx.physics.box2d.Fixture.class);
		kryo.register(Integer.class);
		kryo.register(String.class);
		kryo.register(com.badlogic.gdx.math.Rectangle.class);
		kryo.register(int.class);
		kryo.register(float.class);
		kryo.register(boolean.class);
		kryo.register(Boolean.class);
		kryo.register(packet.InputInfo.class);
		kryo.register(Vector2.class);
		kryo.register(PositionInfo.class);
		kryo.register(packet.UserInfo.class);
		kryo.register(DeathInfo.class);
		kryo.register(DamageInfo.class);
		kryo.register(BossTargetsInfo.class);
		kryo.register(java.util.Stack.class);
	}
}
