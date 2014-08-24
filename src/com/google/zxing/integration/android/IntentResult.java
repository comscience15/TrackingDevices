package com.google.zxing.integration.android;

public class IntentResult {
	private final String contents;
	private final String formatName;
	
	public IntentResult(String contents, String formatName, byte[] rawBytes,Integer orientation, String errorCorrectionLevel) {
		// TODO Auto-generated constructor stub
		this.contents = contents;
		this.formatName = formatName;
	}

	public String getContents() {
		// TODO Auto-generated method stub
		return contents;
	}

	public String getFormatName() {
		// TODO Auto-generated method stub
		return formatName;
	}
	
}
