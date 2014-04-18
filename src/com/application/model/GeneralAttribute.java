package com.application.model;

import java.awt.Image;
import java.util.Observable;

import javax.swing.ImageIcon;

public class GeneralAttribute extends Observable {
	private static String PLAYER_NAME[] = { "", "Ashiq", "Rashik" };
	private static Image PLAYER_IMAGE[] = {
			null,
			new ImageIcon(
					GeneralAttribute.class
							.getResource("/resource/images/p2.jpg"))
					.getImage(),
			new ImageIcon(
					GeneralAttribute.class
							.getResource("/resource/images/p1.jpg"))
					.getImage() };
	private static int TIME_OUT;
	private boolean changePlayerImage[] = { false, false, false };
	private static boolean changePlayerName = false;

	// ***********************************
	public static String getPlayerName(int playerId) {
		return PLAYER_NAME[playerId];
	}
	public static void setChangePlayerName(boolean status){
		changePlayerName = status;
	}
	public boolean hasChangedPlayerName(){
		boolean flag = changePlayerName;
		setChangePlayerName(false);
		return flag;
	}
	public void setName(String name, int playerId){
		setPlayerName(name, playerId);
		setChangePlayerName(true);
		setChanged();
		notifyObservers();
	}
	public synchronized static void setPlayerName(String name, int playerId) {
		if (name != "") {
			PLAYER_NAME[playerId] = name;
			setChangePlayerName(true);
		}
	}

	public static Image getPlayerImage(int playerId) {
		return PLAYER_IMAGE[playerId];
	}

	public static void setPlayerImage(Image img, int playerId) {
		if (img != null)
			PLAYER_IMAGE[playerId] = img;
	}

	public static int getTimeOut() {
		return TIME_OUT;
	}

	public synchronized static void setTimeOut(int timeOut) {
		TIME_OUT = timeOut;
	}

	public void setImage(Image img, int playerId) {
		setPlayerImage(img, playerId);
		setChangePlayerImage(playerId);
		setChanged();
		notifyObservers();
		clearChangePlayerImage(playerId);
	}

	public void setChangePlayerImage(int playerId) {
		changePlayerImage[playerId] = true;
	}

	public void clearChangePlayerImage(int playerId) {
		changePlayerImage[playerId] = false;
	}

	public boolean hasChangePlayerImage(int playerId) {
		return changePlayerImage[playerId];
	}
}
