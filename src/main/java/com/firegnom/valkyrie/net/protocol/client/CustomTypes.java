/*******************************************************************************
 * Copyright (c) 2010 Maciej Kaniewski (mk@firegnom.com).
 * 
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 3 of the License, or
 *    (at your option) any later version.
 * 
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 * 
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software Foundation,
 *    Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 * 
 *    Contributors:
 *     Maciej Kaniewski (mk@firegnom.com) - initial API and implementation
 ******************************************************************************/
/** 
 * MooDS Encoder/Decoder for Flash.
 * Encoding version: 1200
 * @author Generated by MooDS Generator v2.1.0 - 2006 CNAM, INT, Filao 
 */

package com.firegnom.valkyrie.net.protocol.client;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.Hashtable;

import com.firegnom.valkyrie.net.protocol.ChangeGameMode;
import com.firegnom.valkyrie.net.protocol.ChatMessage;
import com.firegnom.valkyrie.net.protocol.ChatUserJoined;
import com.firegnom.valkyrie.net.protocol.ChatUserLeft;
import com.firegnom.valkyrie.net.protocol.ConfirmMove;
import com.firegnom.valkyrie.net.protocol.CreateUserMessage;
import com.firegnom.valkyrie.net.protocol.Path;
import com.firegnom.valkyrie.net.protocol.PlayerDisconnected;
import com.firegnom.valkyrie.net.protocol.PlayerInfoMessage;
import com.firegnom.valkyrie.net.protocol.PlayerMove;
import com.firegnom.valkyrie.net.protocol.PlayerPositionMessage;
import com.firegnom.valkyrie.net.protocol.PlayerPositionsMessage;
import com.firegnom.valkyrie.net.protocol.PlayerStats;
import com.firegnom.valkyrie.net.protocol.RequestPlayerInfoMessage;
import com.firegnom.valkyrie.net.protocol.RequestPlayersPositionMessage;
import com.firegnom.valkyrie.net.protocol.Step;

// TODO: Auto-generated Javadoc
//This class has been generated by MooDS Generator to handle objects
// for binarisation input/output.
/**
 * The Class CustomTypes.
 */
public class CustomTypes {

	// types
	/** The Constant TYPE_PLAYERMOVE. */
	public final static byte TYPE_PLAYERMOVE = 1;

	/** The Constant TYPE_CONFIRMMOVE. */
	public final static byte TYPE_CONFIRMMOVE = 2;

	/** The Constant TYPE_PLAYERDISCONNECTED. */
	public final static byte TYPE_PLAYERDISCONNECTED = 3;

	/** The Constant TYPE_CHATMESSAGE. */
	public final static byte TYPE_CHATMESSAGE = 4;

	/** The Constant TYPE_CHATUSERJOINED. */
	public final static byte TYPE_CHATUSERJOINED = 5;

	/** The Constant TYPE_CHATUSERLEFT. */
	public final static byte TYPE_CHATUSERLEFT = 6;

	/** The Constant TYPE_CHANGEGAMEMODE. */
	public final static byte TYPE_CHANGEGAMEMODE = 7;

	/** The Constant TYPE_REQUESTPLAYERINFOMESSAGE. */
	public final static byte TYPE_REQUESTPLAYERINFOMESSAGE = 8;

	/** The Constant TYPE_CREATEUSERMESSAGE. */
	public final static byte TYPE_CREATEUSERMESSAGE = 9;

	/** The Constant TYPE_PLAYERINFOMESSAGE. */
	public final static byte TYPE_PLAYERINFOMESSAGE = 10;

	/** The Constant TYPE_PLAYERPOSITIONSMESSAGE. */
	public final static byte TYPE_PLAYERPOSITIONSMESSAGE = 11;

	/** The Constant TYPE_PLAYERPOSITIONMESSAGE. */
	public final static byte TYPE_PLAYERPOSITIONMESSAGE = 12;

	/** The Constant TYPE_REQUESTPLAYERSPOSITIONMESSAGE. */
	public final static byte TYPE_REQUESTPLAYERSPOSITIONMESSAGE = 13;

	/**
	 * Decode change game mode.
	 * 
	 * @param dis
	 *            the dis
	 * @return the change game mode
	 * @throws Exception
	 *             the exception
	 */
	public static ChangeGameMode decodeChangeGameMode(final DataInput dis)
			throws Exception {
		final ChangeGameMode customType = new ChangeGameMode();

		customType.setType(dis.readInt());
		return customType;
	}

	/**
	 * Decode chat message.
	 * 
	 * @param dis
	 *            the dis
	 * @return the chat message
	 * @throws Exception
	 *             the exception
	 */
	public static ChatMessage decodeChatMessage(final DataInput dis)
			throws Exception {
		final ChatMessage customType = new ChatMessage();

		customType.setUsername(dis.readUTF());
		customType.setMessage(dis.readUTF());
		return customType;
	}

	/**
	 * Decode chat user joined.
	 * 
	 * @param dis
	 *            the dis
	 * @return the chat user joined
	 * @throws Exception
	 *             the exception
	 */
	public static ChatUserJoined decodeChatUserJoined(final DataInput dis)
			throws Exception {
		final ChatUserJoined customType = new ChatUserJoined();

		customType.setUsername(dis.readUTF());
		return customType;
	}

	/**
	 * Decode chat user left.
	 * 
	 * @param dis
	 *            the dis
	 * @return the chat user left
	 * @throws Exception
	 *             the exception
	 */
	public static ChatUserLeft decodeChatUserLeft(final DataInput dis)
			throws Exception {
		final ChatUserLeft customType = new ChatUserLeft();

		customType.setUsername(dis.readUTF());
		return customType;
	}

	/**
	 * Decode confirm move.
	 * 
	 * @param dis
	 *            the dis
	 * @return the confirm move
	 * @throws Exception
	 *             the exception
	 */
	public static ConfirmMove decodeConfirmMove(final DataInput dis)
			throws Exception {
		final ConfirmMove customType = new ConfirmMove();

		customType.setId(dis.readShort());
		return customType;
	}

	/**
	 * Decode create user message.
	 * 
	 * @param dis
	 *            the dis
	 * @return the creates the user message
	 * @throws Exception
	 *             the exception
	 */
	public static CreateUserMessage decodeCreateUserMessage(final DataInput dis)
			throws Exception {
		final CreateUserMessage customType = new CreateUserMessage();

		customType.setPlayerClass(dis.readInt());
		return customType;
	}

	// decodeData rooy method
	/**
	 * Decode data.
	 * 
	 * @param dis
	 *            the dis
	 * @return the hashtable
	 * @throws Exception
	 *             the exception
	 */
	public static Hashtable decodeData(final DataInput dis) throws Exception {
		dis.readShort();
		final Hashtable h = new Hashtable();
		short nb = dis.readShort();
		final int nbObj = nb;
		while (nb != 0) {
			final String key = "" + (nbObj - nb);
			final byte type = dis.readByte();
			if (type == 1) {
				final PlayerMove customType = decodePlayerMove(dis);
				h.put(key, customType);
			}
			if (type == 2) {
				final ConfirmMove customType = decodeConfirmMove(dis);
				h.put(key, customType);
			}
			if (type == 3) {
				final PlayerDisconnected customType = decodePlayerDisconnected(dis);
				h.put(key, customType);
			}
			if (type == 4) {
				final ChatMessage customType = decodeChatMessage(dis);
				h.put(key, customType);
			}
			if (type == 5) {
				final ChatUserJoined customType = decodeChatUserJoined(dis);
				h.put(key, customType);
			}
			if (type == 6) {
				final ChatUserLeft customType = decodeChatUserLeft(dis);
				h.put(key, customType);
			}
			if (type == 7) {
				final ChangeGameMode customType = decodeChangeGameMode(dis);
				h.put(key, customType);
			}
			if (type == 8) {
				final RequestPlayerInfoMessage customType = decodeRequestPlayerInfoMessage(dis);
				h.put(key, customType);
			}
			if (type == 9) {
				final CreateUserMessage customType = decodeCreateUserMessage(dis);
				h.put(key, customType);
			}
			if (type == 10) {
				final PlayerInfoMessage customType = decodePlayerInfoMessage(dis);
				h.put(key, customType);
			}
			if (type == 11) {
				final PlayerPositionsMessage customType = decodePlayerPositionsMessage(dis);
				h.put(key, customType);
			}
			if (type == 12) {
				final PlayerPositionMessage customType = decodePlayerPositionMessage(dis);
				h.put(key, customType);
			}
			if (type == 13) {
				final RequestPlayersPositionMessage customType = decodeRequestPlayersPositionMessage(dis);
				h.put(key, customType);
			}
			nb--;
		}
		return h;
	}

	/**
	 * Decode path.
	 * 
	 * @param dis
	 *            the dis
	 * @return the path
	 * @throws Exception
	 *             the exception
	 */
	public static Path decodePath(final DataInput dis) throws Exception {
		final Path customType = new Path();

		final int table1_l0 = dis.readInt();
		if (table1_l0 > 0) {
			final Step[] table1 = new Step[table1_l0];
			for (int i0 = 0; i0 < table1_l0; i0++) {
				table1[i0] = decodeStep(dis);
			}
			customType.setStep(table1);
		}
		return customType;
	}

	/**
	 * Decode player disconnected.
	 * 
	 * @param dis
	 *            the dis
	 * @return the player disconnected
	 * @throws Exception
	 *             the exception
	 */
	public static PlayerDisconnected decodePlayerDisconnected(
			final DataInput dis) throws Exception {
		final PlayerDisconnected customType = new PlayerDisconnected();

		customType.setPlayerName(dis.readUTF());
		return customType;
	}

	/**
	 * Decode player info message.
	 * 
	 * @param dis
	 *            the dis
	 * @return the player info message
	 * @throws Exception
	 *             the exception
	 */
	public static PlayerInfoMessage decodePlayerInfoMessage(final DataInput dis)
			throws Exception {
		final PlayerInfoMessage customType = new PlayerInfoMessage();

		customType.setPlayerClass(dis.readInt());
		customType.setZoneName(dis.readUTF());
		customType.setPosition(decodePlayerPositionMessage(dis));
		customType.setStats(decodePlayerStats(dis));
		return customType;
	}

	/**
	 * Decode player move.
	 * 
	 * @param dis
	 *            the dis
	 * @return the player move
	 * @throws Exception
	 *             the exception
	 */
	public static PlayerMove decodePlayerMove(final DataInput dis)
			throws Exception {
		final PlayerMove customType = new PlayerMove();

		customType.setPlayerName(dis.readUTF());
		customType.setPlayerClass(dis.readInt());
		customType.setPath(decodePath(dis));
		return customType;
	}

	/**
	 * Decode player position message.
	 * 
	 * @param dis
	 *            the dis
	 * @return the player position message
	 * @throws Exception
	 *             the exception
	 */
	public static PlayerPositionMessage decodePlayerPositionMessage(
			final DataInput dis) throws Exception {
		final PlayerPositionMessage customType = new PlayerPositionMessage();

		customType.setUserName(dis.readUTF());
		customType.setPlayerClass(dis.readInt());
		customType.setX(dis.readShort());
		customType.setY(dis.readShort());
		return customType;
	}

	/**
	 * Decode player positions message.
	 * 
	 * @param dis
	 *            the dis
	 * @return the player positions message
	 * @throws Exception
	 *             the exception
	 */
	public static PlayerPositionsMessage decodePlayerPositionsMessage(
			final DataInput dis) throws Exception {
		final PlayerPositionsMessage customType = new PlayerPositionsMessage();

		final int table1_l0 = dis.readInt();
		if (table1_l0 > 0) {
			final PlayerPositionMessage[] table1 = new PlayerPositionMessage[table1_l0];
			for (int i0 = 0; i0 < table1_l0; i0++) {
				table1[i0] = decodePlayerPositionMessage(dis);
			}
			customType.setPlayerPositionMessage(table1);
		}
		return customType;
	}

	/**
	 * Decode player stats.
	 * 
	 * @param dis
	 *            the dis
	 * @return the player stats
	 * @throws Exception
	 *             the exception
	 */
	public static PlayerStats decodePlayerStats(final DataInput dis)
			throws Exception {
		final PlayerStats customType = new PlayerStats();

		customType.setHp(dis.readInt());
		customType.setMaxHp(dis.readInt());
		customType.setMp(dis.readInt());
		customType.setMaxMp(dis.readInt());
		return customType;
	}

	/**
	 * Decode request player info message.
	 * 
	 * @param dis
	 *            the dis
	 * @return the request player info message
	 * @throws Exception
	 *             the exception
	 */
	public static RequestPlayerInfoMessage decodeRequestPlayerInfoMessage(
			final DataInput dis) throws Exception {
		final RequestPlayerInfoMessage customType = new RequestPlayerInfoMessage();

		return customType;
	}

	/**
	 * Decode request players position message.
	 * 
	 * @param dis
	 *            the dis
	 * @return the request players position message
	 * @throws Exception
	 *             the exception
	 */
	public static RequestPlayersPositionMessage decodeRequestPlayersPositionMessage(
			final DataInput dis) throws Exception {
		final RequestPlayersPositionMessage customType = new RequestPlayersPositionMessage();

		return customType;
	}

	// decoding method for each custom type
	/**
	 * Decode step.
	 * 
	 * @param dis
	 *            the dis
	 * @return the step
	 * @throws Exception
	 *             the exception
	 */
	public static Step decodeStep(final DataInput dis) throws Exception {
		final Step customType = new Step();

		customType.setX(dis.readShort());
		customType.setY(dis.readShort());
		return customType;
	}

	/**
	 * Encode change game mode.
	 * 
	 * @param dos
	 *            the dos
	 * @param customType
	 *            the custom type
	 * @throws Exception
	 *             the exception
	 */
	public static void encodeChangeGameMode(final DataOutput dos,
			final ChangeGameMode customType) throws Exception {
		dos.writeInt(customType.getType());
	}

	/**
	 * Encode chat message.
	 * 
	 * @param dos
	 *            the dos
	 * @param customType
	 *            the custom type
	 * @throws Exception
	 *             the exception
	 */
	public static void encodeChatMessage(final DataOutput dos,
			final ChatMessage customType) throws Exception {
		dos.writeUTF(customType.getUsername());
		dos.writeUTF(customType.getMessage());
	}

	/**
	 * Encode chat user joined.
	 * 
	 * @param dos
	 *            the dos
	 * @param customType
	 *            the custom type
	 * @throws Exception
	 *             the exception
	 */
	public static void encodeChatUserJoined(final DataOutput dos,
			final ChatUserJoined customType) throws Exception {
		dos.writeUTF(customType.getUsername());
	}

	/**
	 * Encode chat user left.
	 * 
	 * @param dos
	 *            the dos
	 * @param customType
	 *            the custom type
	 * @throws Exception
	 *             the exception
	 */
	public static void encodeChatUserLeft(final DataOutput dos,
			final ChatUserLeft customType) throws Exception {
		dos.writeUTF(customType.getUsername());
	}

	/**
	 * Encode confirm move.
	 * 
	 * @param dos
	 *            the dos
	 * @param customType
	 *            the custom type
	 * @throws Exception
	 *             the exception
	 */
	public static void encodeConfirmMove(final DataOutput dos,
			final ConfirmMove customType) throws Exception {
		dos.writeShort(customType.getId());
	}

	/**
	 * Encode create user message.
	 * 
	 * @param dos
	 *            the dos
	 * @param customType
	 *            the custom type
	 * @throws Exception
	 *             the exception
	 */
	public static void encodeCreateUserMessage(final DataOutput dos,
			final CreateUserMessage customType) throws Exception {
		dos.writeInt(customType.getPlayerClass());
	}

	// encodeData root method
	/**
	 * Encode data.
	 * 
	 * @param h
	 *            the h
	 * @param dos
	 *            the dos
	 * @throws Exception
	 *             the exception
	 */
	public static void encodeData(final Hashtable h, final DataOutput dos)
			throws Exception {
		dos.writeShort(1200);
		dos.writeShort(h.size());
		final int nbSend = h.size();
		for (int i = 0; i < nbSend; i++) {
			final Object o = h.get("" + i);
			if (o instanceof PlayerMove) {
				dos.writeByte(TYPE_PLAYERMOVE);
				encodePlayerMove(dos, ((PlayerMove) o));
			}
			if (o instanceof ConfirmMove) {
				dos.writeByte(TYPE_CONFIRMMOVE);
				encodeConfirmMove(dos, ((ConfirmMove) o));
			}
			if (o instanceof PlayerDisconnected) {
				dos.writeByte(TYPE_PLAYERDISCONNECTED);
				encodePlayerDisconnected(dos, ((PlayerDisconnected) o));
			}
			if (o instanceof ChatMessage) {
				dos.writeByte(TYPE_CHATMESSAGE);
				encodeChatMessage(dos, ((ChatMessage) o));
			}
			if (o instanceof ChatUserJoined) {
				dos.writeByte(TYPE_CHATUSERJOINED);
				encodeChatUserJoined(dos, ((ChatUserJoined) o));
			}
			if (o instanceof ChatUserLeft) {
				dos.writeByte(TYPE_CHATUSERLEFT);
				encodeChatUserLeft(dos, ((ChatUserLeft) o));
			}
			if (o instanceof ChangeGameMode) {
				dos.writeByte(TYPE_CHANGEGAMEMODE);
				encodeChangeGameMode(dos, ((ChangeGameMode) o));
			}
			if (o instanceof RequestPlayerInfoMessage) {
				dos.writeByte(TYPE_REQUESTPLAYERINFOMESSAGE);
				encodeRequestPlayerInfoMessage(dos,
						((RequestPlayerInfoMessage) o));
			}
			if (o instanceof CreateUserMessage) {
				dos.writeByte(TYPE_CREATEUSERMESSAGE);
				encodeCreateUserMessage(dos, ((CreateUserMessage) o));
			}
			if (o instanceof PlayerInfoMessage) {
				dos.writeByte(TYPE_PLAYERINFOMESSAGE);
				encodePlayerInfoMessage(dos, ((PlayerInfoMessage) o));
			}
			if (o instanceof PlayerPositionsMessage) {
				dos.writeByte(TYPE_PLAYERPOSITIONSMESSAGE);
				encodePlayerPositionsMessage(dos, ((PlayerPositionsMessage) o));
			}
			if (o instanceof PlayerPositionMessage) {
				dos.writeByte(TYPE_PLAYERPOSITIONMESSAGE);
				encodePlayerPositionMessage(dos, ((PlayerPositionMessage) o));
			}
			if (o instanceof RequestPlayersPositionMessage) {
				dos.writeByte(TYPE_REQUESTPLAYERSPOSITIONMESSAGE);
				encodeRequestPlayersPositionMessage(dos,
						((RequestPlayersPositionMessage) o));
			}
		}
	}

	/**
	 * Encode path.
	 * 
	 * @param dos
	 *            the dos
	 * @param customType
	 *            the custom type
	 * @throws Exception
	 *             the exception
	 */
	public static void encodePath(final DataOutput dos, final Path customType)
			throws Exception {
		final Step[] table1 = customType.getStep();
		final int table1_l0 = table1.length;
		dos.writeInt(table1_l0);
		if (table1_l0 > 0) {
			for (int i0 = 0; i0 < table1_l0; i0++) {
				encodeStep(dos, table1[i0]);
			}
		}

	}

	/**
	 * Encode player disconnected.
	 * 
	 * @param dos
	 *            the dos
	 * @param customType
	 *            the custom type
	 * @throws Exception
	 *             the exception
	 */
	public static void encodePlayerDisconnected(final DataOutput dos,
			final PlayerDisconnected customType) throws Exception {
		dos.writeUTF(customType.getPlayerName());
	}

	/**
	 * Encode player info message.
	 * 
	 * @param dos
	 *            the dos
	 * @param customType
	 *            the custom type
	 * @throws Exception
	 *             the exception
	 */
	public static void encodePlayerInfoMessage(final DataOutput dos,
			final PlayerInfoMessage customType) throws Exception {
		dos.writeInt(customType.getPlayerClass());
		dos.writeUTF(customType.getZoneName());
		encodePlayerPositionMessage(dos, customType.getPosition());
		encodePlayerStats(dos, customType.getStats());
	}

	/**
	 * Encode player move.
	 * 
	 * @param dos
	 *            the dos
	 * @param customType
	 *            the custom type
	 * @throws Exception
	 *             the exception
	 */
	public static void encodePlayerMove(final DataOutput dos,
			final PlayerMove customType) throws Exception {
		dos.writeUTF(customType.getPlayerName());
		dos.writeInt(customType.getPlayerClass());
		encodePath(dos, customType.getPath());
	}

	/**
	 * Encode player position message.
	 * 
	 * @param dos
	 *            the dos
	 * @param customType
	 *            the custom type
	 * @throws Exception
	 *             the exception
	 */
	public static void encodePlayerPositionMessage(final DataOutput dos,
			final PlayerPositionMessage customType) throws Exception {
		dos.writeUTF(customType.getUserName());
		dos.writeInt(customType.getPlayerClass());
		dos.writeShort(customType.getX());
		dos.writeShort(customType.getY());
	}

	/**
	 * Encode player positions message.
	 * 
	 * @param dos
	 *            the dos
	 * @param customType
	 *            the custom type
	 * @throws Exception
	 *             the exception
	 */
	public static void encodePlayerPositionsMessage(final DataOutput dos,
			final PlayerPositionsMessage customType) throws Exception {
		final PlayerPositionMessage[] table1 = customType
				.getPlayerPositionMessage();
		final int table1_l0 = table1.length;
		dos.writeInt(table1_l0);
		if (table1_l0 > 0) {
			for (int i0 = 0; i0 < table1_l0; i0++) {
				encodePlayerPositionMessage(dos, table1[i0]);
			}
		}

	}

	/**
	 * Encode player stats.
	 * 
	 * @param dos
	 *            the dos
	 * @param customType
	 *            the custom type
	 * @throws Exception
	 *             the exception
	 */
	public static void encodePlayerStats(final DataOutput dos,
			final PlayerStats customType) throws Exception {
		dos.writeInt(customType.getHp());
		dos.writeInt(customType.getMaxHp());
		dos.writeInt(customType.getMp());
		dos.writeInt(customType.getMaxMp());
	}

	/**
	 * Encode request player info message.
	 * 
	 * @param dos
	 *            the dos
	 * @param customType
	 *            the custom type
	 * @throws Exception
	 *             the exception
	 */
	public static void encodeRequestPlayerInfoMessage(final DataOutput dos,
			final RequestPlayerInfoMessage customType) throws Exception {
	}

	/**
	 * Encode request players position message.
	 * 
	 * @param dos
	 *            the dos
	 * @param customType
	 *            the custom type
	 * @throws Exception
	 *             the exception
	 */
	public static void encodeRequestPlayersPositionMessage(
			final DataOutput dos, final RequestPlayersPositionMessage customType)
			throws Exception {
	}

	// encoding method for each custom type
	/**
	 * Encode step.
	 * 
	 * @param dos
	 *            the dos
	 * @param customType
	 *            the custom type
	 * @throws Exception
	 *             the exception
	 */
	public static void encodeStep(final DataOutput dos, final Step customType)
			throws Exception {
		dos.writeShort(customType.getX());
		dos.writeShort(customType.getY());
	}

}
