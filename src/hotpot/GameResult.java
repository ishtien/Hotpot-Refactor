package hotpot;

import java.util.Dictionary;

public class GameResult {
	private int sumCal;
	private int sumPrice;
	private int []sumChicken = new int[3];
	private int []sumBeef = new int[3];
	private int []sumEggdump = new int[3];
	private int []sumFish = new int[3];
	private int []sumFishegg = new int[3];
	private int []sumLettuce = new int[3];
	private int []sumMushroom = new int[3];
	private int []sumPig = new int[3];
	private int []sumRicecake = new int[3];
	private int []sumVeg = new int[3];
	private int []sumWurst = new int[3];
	int [] CookedMost = new int [11];
	int [] RawMost = new int [11];
	int [] FuckMost = new int [11];
	public void setInfo(int cal,int price,String food,int state) {
		sumCal += cal;
		sumPrice += price;
		switch(food) {
			case "牛肉":sumBeef[state]++;break;
			case "雞肉":sumChicken[state]++;break;
			case "蛋餃":sumEggdump[state]++;break;
			case "魚肉":sumFish[state]++;break;
			case "魚蛋":sumFishegg[state]++;break;
			case "高麗菜":sumLettuce[state]++;break;
			case "香菇":sumMushroom[state]++;break;
			case "豬肉":sumPig[state]++;break;
			case "年糕":sumRicecake[state]++;break;
			case "青菜":sumVeg[state]++;break;
			case "鑫鑫腸":sumWurst[state]++;break;
			default: break;
		}
			
	}
	
	public int getSumCal() {
		return sumCal;
	}
	
	public int getSumPrice() {
		return sumPrice;
	}
	
	public String getFoodMost (int state) {
		String Max="beef";
		if(sumBeef[state]<sumChicken[state]) {
			Max="chicken";
		}else if(sumChicken[state]<sumEggdump[state]) {
			Max="eggdump";
		}else if(sumEggdump[state]<sumFish[state]) {
			Max="fish";
		}else if(sumFish[state]<sumFishegg[state]) {
			Max="fishegg";
		}else if(sumFishegg[state]<sumLettuce[state]) {
			Max="lettuce";
		}else if(sumLettuce[state]<sumMushroom[state]) {
			Max="mushroom";
		}else if(sumMushroom[state]<sumRicecake[state]) {
			Max="ricecake";
		}else if(sumRicecake[state]<sumVeg[state]) {
			Max="veg";
		}else if(sumVeg[state]<sumWurst[state]) {
			Max="wurst";
		}else {
			if(Max.contentEquals("beef") && sumBeef[state]==0) {
				return "404.jpg";
			}
		}
		if(state == 0) {
			return "raw_"+Max+".png";
		}else if(state ==1) {
			return "cooked_"+Max+".png";
		}else {
			return "fucked_up_"+Max+".png";
		}
	}

	public String getHunger() {
		if(sumCal > 1500) {
			return "飽飽的";
		}else if(sumCal <=1500 && sumCal > 800) {
			return "有點飽";
		}else {
			return "你有沒有認真吃啊！";
		}
	}
	
	public String getNickname() {
		if(sumCal>5000) {
			return "超級食怪";
		}else if(sumPrice > 5000) {
			return "花錢大大";
		}else if(sumBeef[1]+sumChicken[1]+sumFish[1]>30) {
			return "愛吃肉肉";
		}else if(sumVeg[1]+sumMushroom[1]+sumLettuce[1]>40) {
			return "速食主義";
		}
		return "火鍋愛好者";
	}
}
