package com.cocha.business;

import java.sql.Connection;

import com.cocha.business.calculos.ControlFinanzas;
import com.cocha.business.calculos.ExisteNegPed;
public class ControlFacturacionFinanzas {

	public String controlFacturacion(String numNegocio, int numPedido, String nemo, Connection conJdbc) throws Exception{
		java.util.Date fecha = new java.util.Date();	
		ExisteNegPed isExiste = new ExisteNegPed();
		ControlFinanzas ctrlFinanza = new ControlFinanzas();	
		String status = "ERROR al actualizar el pedido";

		status = isExiste.existeNegPed(numNegocio, numPedido, conJdbc);

		if(status.equals("Existe")){
			if(ctrlFinanza.insertControFinanzas(numNegocio, numPedido, conJdbc)){
				status = "OK pedido actualizado";		
			}
		}
		
		java.util.Date fechaFin = new java.util.Date();
		System.out.println("Control Facturación demoro: "+
				(fechaFin.getTime() - fecha.getTime())/1000+" Segundos");

		return status;
	}
}
