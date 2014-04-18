package com.guti16.network;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.Icon;

import com.application.model.GeneralAttribute;

public class SendInformationObject implements Serializable {
	private static final long serialVersionUID = 1L;

	public Image playerImage = GeneralAttribute.getPlayerImage(1);

	transient public  String playerName;
	public int timeOut;
	public int playerId;

	public SendInformationObject(int timeout, int id) {
		this.timeOut = timeout;
		this.playerId = id;
	}
}
