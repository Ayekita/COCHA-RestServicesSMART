package com.cocha.domain;

import javax.ws.rs.Path;

@Path("repuesta")
public class respuestaLcSg {
	private String sobreGiro;
	private String statusLc;

	public String getSobreGiro() {
		return sobreGiro;
	}
	public void setSobreGiro(String sobreGiro) {
		this.sobreGiro = sobreGiro;
	}
	public String getStatusLc() {
		return statusLc;
	}
	public void setStatusLc(String statusLc) {
		this.statusLc = statusLc;
	}


}
