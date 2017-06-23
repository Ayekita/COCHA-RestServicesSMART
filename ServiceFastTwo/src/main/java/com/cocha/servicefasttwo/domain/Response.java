package com.cocha.servicefasttwo.domain;


import java.io.Serializable;

import javax.ws.rs.Path;

@Path("respuesta")
public class Response implements Serializable{
	static final long serialVersionUID = 3734232549150405918L;
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status)  {
		this.status = status;
	}

}
