package com.guti16.model;

/**
 * This class used to control time management of game
 * 
 * @author Md. Ashiquzzaman & Rashik Hasnat
 * @version 1.00
 */
public class Timer implements Runnable {
	private long timeSlice = 200;
	private GameLogic gameLogic;
	private long startTime;

	/**
	 * Constructor method
	 * 
	 * @param gameLogic
	 */
	public Timer(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
	}

	@Override
	public void run() {

		startTime = System.currentTimeMillis();
		while (!gameLogic.isClosed()) {
			try {
				Thread.sleep(timeSlice);
				gameLogic.setDecreaseRemainingTime(timeSlice);
			} catch (InterruptedException e) {
				System.err.println("shutdown");
			}
			if (!((System.currentTimeMillis() - startTime) <= GameAttribute.TIME_EACH_TURN)) {
				gameLogic.timeUp();
			}
		}
	}

	public void resetTimer() {
		startTime = System.currentTimeMillis();
	}

}
