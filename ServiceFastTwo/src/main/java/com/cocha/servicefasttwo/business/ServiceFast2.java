package com.cocha.servicefasttwo.business;

import java.sql.Connection;

import com.cocha.servicefasttwo.domain.Response;
import com.cocha.servicefasttwo.persistance.SmartDao;
import com.cocha.servicefasttwo.utils.FechasUtil;

public class ServiceFast2 {	
	public Response procesoPrincipal(String negocio, String moneda, double valor, String spnr, Connection conJdbc) throws Exception  {
		FechasUtil funciones = new FechasUtil();
		System.out.println("## Inicio FEE FASTII " + funciones.getFechaHora() + " ##");
		Response respuesta = new Response();
		try {
			int numped = 0;
			SmartDao smartDao = new SmartDao();
			String idpedido = smartDao.obtenerIdPedido(negocio, spnr, conJdbc);
			if(idpedido != null && idpedido.length() > 0){
				numped = smartDao.pedidoWss04(idpedido, negocio, conJdbc);
			}
			if(!smartDao.existeFee(negocio, spnr, moneda, numped, conJdbc)) {
				if(smartDao.insertarFee(negocio, numped, moneda, valor, spnr, conJdbc)) {
					respuesta.setStatus("FEE OK!");
				}
			} else {
				respuesta.setStatus("FEE YA EXISTE!");
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("## Fin FEE FASTII " + funciones.getFechaHora() + " ##");
		return respuesta;
	}
}
