package network;

import hotpot.GameStatus;

public interface Packet {
	boolean isValid(GameStatus status);
	void doOperation(GameStatus status);
	byte[] toArray();
}
