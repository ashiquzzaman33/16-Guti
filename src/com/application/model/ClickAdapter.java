package com.application.model;

import com.guti16.model.GameLogic;
import com.guti16.network.Client;
import com.guti16.network.Server;

public class ClickAdapter {
	private GameLogic gameLogic;
	private static Server server;
	private Client client;
	private static ClickAdapter clickAdapter;
	private ClickAdapter(Server server, Client client) {
		ClickAdapter.server = server;
		this.client  = client;
		this.gameLogic = null;
	}
	public void setGameLogic(GameLogic gameLogic){
			clickAdapter.gameLogic = gameLogic;
	}
	
	
	
	public static ClickAdapter getInstance(Server server){
		if(clickAdapter!=null)
			return clickAdapter;
		else{
			return clickAdapter = new ClickAdapter(server, null);
		}
			
	}
	public static ClickAdapter getInstance(Client client){
		if(clickAdapter!=null)
			return clickAdapter;
		else
			return clickAdapter=new ClickAdapter(null, client);
	}
	public static ClickAdapter getInstance(){
		if(clickAdapter!=null){
			return clickAdapter;
		}
		else
			return clickAdapter=new ClickAdapter(null, null);
	}
	public static void createFreshInstance(Server server){
		clickAdapter = null;
		clickAdapter = getInstance(server);
	}
	public static void createFreshInstance(Client client){
		clickAdapter = null;
		clickAdapter=getInstance(client);
	}
	public static void createFreshInstance(Server server2, Client client2) {
		clickAdapter  = null;
		clickAdapter = new ClickAdapter(server2, client2);
	}
	public void changeTurnNativeCall() {
		if(server!=null){
			server.sendData("ChangeTurn:");
		}else if(client!=null)
			client.sendData("ChangeTurn:");
		gameLogic.changeTurn();
	}
	public void changeTurnRemoteCall(){
		gameLogic.changeTurn();
	}
	public void clickedNativeCall(int x, int y){
		if(server!=null){
			server.sendData("Clicked: "+x+" "+y);
		}else if(client!=null){
			client.sendData("Clicked: "+x+" "+y);
		}
		gameLogic.processClick(x, y);
	}
	public void clickedRemoteCall(int x, int y){
		gameLogic.processClick(x, y);
	}
}
