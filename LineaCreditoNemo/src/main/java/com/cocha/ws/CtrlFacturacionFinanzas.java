package com.cocha.ws;

import java.sql.Connection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cocha.business.ControlFacturacionFinanzas;
import com.cocha.domain.respuestaLcSg;
import com.cocha.persistance.SmartConnections;

@Path("/ControlFacturacion")
public class CtrlFacturacionFinanzas {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/get/nemo={nemo}&negocio={negocio}&pedido={pedido}")
	public respuestaLcSg getControlFacturacionFinznansas(@PathParam("nemo") String nemo, @PathParam("negocio") String negocio,
			@PathParam("pedido") int pedido){
		SmartConnections conectame;
		respuestaLcSg respuesta = new respuestaLcSg();
		try {
			conectame = SmartConnections.getInstance();
			Connection conJdbc = conectame.getConnSmart();
			ControlFacturacionFinanzas control = new ControlFacturacionFinanzas();
			respuesta = control.controlFacturacion(negocio, pedido, nemo, conJdbc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respuesta;
	}
}
