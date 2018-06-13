package com.demo.main.Utils;

import javafx.scene.control.Alert;

public class AlertMessage extends Alert {

	public AlertMessage(AlertType alertType, String title, String headerText, String contentText) {
		super(alertType);
		setTitle(title);
		setHeaderText(headerText);
		setContentText(contentText);
	}


}
