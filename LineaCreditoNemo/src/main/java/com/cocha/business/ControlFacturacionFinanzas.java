package com.cocha.business;

import java.sql.Connection;

import com.cocha.business.calculos.ControlFinanzas;
import com.cocha.business.calculos.ExisteNegPed;
import com.cocha.domain.respuestaLcSg;
public class ControlFacturacionFinanzas {

	public respuestaLcSg controlFacturacion(String numNegocio, int numPedido, String nemo, Connection conJdbc) throws Exception{
		java.util.Date fecha = new java.util.Date();	
		ExisteNegPed isExiste = new ExisteNegPed();
		ControlFinanzas ctrlFinanza = new ControlFinanzas();	
		respuestaLcSg respuesta = new respuestaLcSg();
		respuesta.setStatusLc("ERROR al actualizar el pedido");

		respuesta.setStatusLc(isExiste.existeNegPed(numNegocio, numPedido, conJdbc));

		if(respuesta.getStatusLc().equals("Existe")){
			if(ctrlFinanza.insertControFinanzas(numNegocio, numPedido, conJdbc)){
				respuesta.setStatusLc("OK pedido actualizado");		
			}
		}		
		java.util.Date fechaFin = new java.util.Date();
		System.out.println("Control Facturación demoro: "+
				(fechaFin.getTime() - fecha.getTime())/1000+" Segundos");

		return respuesta;
	}
}
