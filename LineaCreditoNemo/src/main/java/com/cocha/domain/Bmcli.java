package com.cocha.domain;

public class Bmcli {
	
	private int rut;
	private String dv;
	private String nemote;
	private String nemogr;
	private String razonSocial;
	private long lineaCLP;
	private double lineaUSD;
	private String tipo;
	private String frecuencia;
	private String reqOC;
	public int getRut() {
		return rut;
	}
	public void setRut(int rut) {
		this.rut = rut;
	}
	public String getDv() {
		return dv;
	}
	public void setDv(String dv) {
		this.dv = dv;
	}
	public String getNemote() {
		return nemote;
	}
	public void setNemote(String nemote) {
		this.nemote = nemote;
	}
	public String getNemogr() {
		return nemogr;
	}
	public void setNemogr(String nemogr) {
		this.nemogr = nemogr;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public long getLineaCLP() {
		return lineaCLP;
	}
	public void setLineaCLP(long lineaCLP) {
		this.lineaCLP = lineaCLP;
	}
	public double getLineaUSD() {
		return lineaUSD;
	}
	public void setLineaUSD(double lineaUSD) {
		this.lineaUSD = lineaUSD;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getFrecuencia() {
		return frecuencia;
	}
	public void setFrecuencia(String frecuencia) {
		this.frecuencia = frecuencia;
	}
	public String getReqOC() {
		return reqOC;
	}
	public void setReqOC(String reqOC) {
		this.reqOC = reqOC;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Bmcli [rut=");
		builder.append(rut);
		builder.append(", dv=");
		builder.append(dv);
		builder.append(", nemote=");
		builder.append(nemote);
		builder.append(", nemogr=");
		builder.append(nemogr);
		builder.append(", razonSocial=");
		builder.append(razonSocial);
		builder.append(", lineaCLP=");
		builder.append(lineaCLP);
		builder.append(", lineaUSD=");
		builder.append(lineaUSD);
		builder.append(", tipo=");
		builder.append(tipo);
		builder.append(", frecuencia=");
		builder.append(frecuencia);
		builder.append(", reqOC=");
		builder.append(reqOC);
		builder.append("]");
		return builder.toString();
	}
	
	}