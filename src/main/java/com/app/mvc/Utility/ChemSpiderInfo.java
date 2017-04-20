package com.app.mvc.Utility;

import java.math.BigInteger;

public class ChemSpiderInfo {
	private String chemSpiderUri;
	private String chemSpiderURL;
	private String pictureURL;
	private String systematicName;
	private String formula;
	private BigInteger chemSpiderId;
	
	
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getSystematicName() {
		return systematicName;
	}
	public void setSystematicName(String systematicName) {
		this.systematicName = systematicName;
	}
	
	public String getChemSpiderUri() {
		return chemSpiderUri;
	}
	public void setChemSpiderUri(String chemSpiderUri) {
		this.chemSpiderUri = chemSpiderUri;
	}

	public String getChemSpiderURL() {
		return chemSpiderURL;
	}
	public void setChemSpiderURL(String chemSpiderURL) {
		this.chemSpiderURL = chemSpiderURL;
	}
	public String getPictureURL() {
		return pictureURL;
	}
	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}
	public BigInteger getChemSpiderId() {
		return chemSpiderId;
	}
	public void setChemSpiderId(BigInteger chemSpiderId) {
		this.chemSpiderId = chemSpiderId;
	}
}
