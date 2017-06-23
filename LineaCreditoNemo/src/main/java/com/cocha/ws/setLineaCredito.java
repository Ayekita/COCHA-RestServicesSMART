package com.cocha.ws;

import java.sql.Connection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cocha.business.IngresoLineaCredito;
import com.cocha.domain.respuestaLcSg;
import com.cocha.persistance.SmartConnections;

@Path("/IngresoLineaCredito")
public class setLineaCredito {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/get/nemo={nemo}&negocio={negocio}&pedido={pedido}&moneda={moneda}&valorservicio={valorServicio}")
	public respuestaLcSg setLineaCreidto(@PathParam("nemo") String nemo, @PathParam("negocio") String negocio,
			@PathParam("pedido") int pedido, @PathParam("moneda") String moneda,@PathParam("valorServicio") double valorServicio ){
		SmartConnections conectame;
		respuestaLcSg status = null;
		try {
			conectame = SmartConnections.getInstance();
			Connection conJdbc = conectame.getConnSmart();
			IngresoLineaCredito ingreso = new IngresoLineaCredito();

			status = ingreso.ingresoLC( moneda, nemo, negocio, valorServicio, conJdbc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
}
