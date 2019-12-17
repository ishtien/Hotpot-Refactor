package hotpot;

public class GameStatus {
	public static int roomInHotpot = 10;
	public static int playerMaxCount = 4;
	private long cookTimeArray[] = new long[roomInHotpot];
	private int cookItemIDArray[] = new int[roomInHotpot];
	private boolean isCooking[] = new boolean[roomInHotpot];
	private String otherPlayersName[] = new String[playerMaxCount];
	private Hotpot hotpot;
	
	public GameStatus(Hotpot hotpot)
	{
		this.hotpot = hotpot;
	}
	
	public boolean checkIsCooking(int index)
	{
		if (index < 0)
			return false;
		else if (index >= roomInHotpot)
			return false;
		return isCooking[index];
	}
	
	synchronized public int getFreeRoomInHotpot()
	{
		for (int i = 0; i < roomInHotpot; ++i)
			if (!isCooking[i])
				return i;
		return -1;
	}
	
	synchronized public int getHotpotItemID(int index)
	{
		if (checkIsCooking(index))
			return cookItemIDArray[index];
		else
			return -1;
	}
	
	public long getHotpotItemCookTime(int index)
	{
		if (checkIsCooking(index))
			return System.currentTimeMillis() - cookTimeArray[index];
		else
			return -1;
	}
	
	synchronized public void freeHotpotRoom(int index)
	{
		if (!checkIsCooking(index))
			return;
		isCooking[index] = false;
		cookItemIDArray[index] = 0;
		cookTimeArray[index] = 0;
	}
	
	synchronized public void takeFoodAway(int index, int sender)
	{
		if (!checkIsCooking(index))
			return;

		if (hotpot != null)
			hotpot.foodTakeAwayFromHotpot(index, getHotpotItemID(index), sender, (int)getHotpotItemCookTime(index));
		freeHotpotRoom(index);
	}
	
	synchronized public void putItemIntoHotpot(int index, int foodID)
	{
		if (checkIsCooking(index))
			return;
		isCooking[index] = true;
		cookItemIDArray[index] = foodID;
		cookTimeArray[index] = System.currentTimeMillis();
		if (hotpot != null)
			hotpot.addFoodIntoHotpot(index, foodID);
	}
	
	synchronized public void addPlayerName(int userID, String name)
	{
		otherPlayersName[userID] = new String(name);
		if (hotpot != null)
			hotpot.addNewPlayer(userID, name);
	}
	
	public String getPlayerName(int userID)
	{
		return otherPlayersName[userID];
	}
}
