package com.cocha.business;

import java.sql.Connection;

import com.cocha.business.calculos.GetLineaCredito;
import com.cocha.business.calculos.SetLineaCredito;
import com.cocha.business.calculos.setSobregiro;
import com.cocha.domain.DatosSalida;
import com.cocha.domain.respuestaLcSg;
import com.cocha.utils.FechasUtil;

public class IngresoLineaCredito {

	public respuestaLcSg ingresoLC(String moneda, String nemo, String negocio, double valorServicio, Connection conJdbc) throws Exception{
		java.util.Date fecha = new java.util.Date();	
		respuestaLcSg respuesta = new respuestaLcSg();
		respuesta.setStatusLc("ERROR ingreso pago con Linea de Credito");

		GetLineaCredito getLinCre = new GetLineaCredito();
		FechasUtil fechas = new FechasUtil();
		SetLineaCredito setlc = new SetLineaCredito();
		setSobregiro sobregiro = new setSobregiro();

		long fechaActual = fechas.getFechaActual();
		long horaActual = fechas.getHoraActual();
		long valorUtilizadoAntes = 0;
		long valorUtilizadoDespues = 0;
		double valorDisponible = 0;
		double valorUtilizadoAntesUSD = 0;
		double valorUtilizadoDespuesUSD = 0;

		DatosSalida salida = getLinCre.obtieneLineaCredito(nemo, conJdbc);	

		int rutCli = salida.getRut();
		String usuario = "ONLINE";
		if("CLP".equalsIgnoreCase(moneda)){
			valorUtilizadoAntes = salida.getLineaEquivalenteUtilizada();
			valorDisponible = salida.getLineaEquivalenteDisponible();
		}
		else if ("USD".equalsIgnoreCase(moneda)){
			valorUtilizadoAntesUSD = salida.getLineaEquivalenteUtilizadaUSD();
			valorDisponible = salida.getLineaEquivalenteDisponibleUSD();
		}

		valorUtilizadoDespues = valorUtilizadoAntes +  Math.round(valorServicio);
		valorUtilizadoDespuesUSD = valorUtilizadoAntesUSD + valorServicio;

		System.out.println("linea utilizada CLP antes:" + valorUtilizadoAntes);		
		System.out.println("linea utilizada CLP despues:" + valorUtilizadoDespues);
		System.out.println("linea utilizada USD antes:" + valorUtilizadoAntesUSD);		
		System.out.println("linea utilizada USD despues:" + valorUtilizadoDespuesUSD);

		if(setlc.ingresoLinCred(rutCli, moneda, valorServicio, fechaActual, horaActual, conJdbc)){
			respuesta.setStatusLc("OK pago con Linea de Credito");			
			if(valorDisponible <= 0 ){
				if(sobregiro.sobregiroLineaCredito(moneda, nemo, rutCli, negocio, valorUtilizadoAntes, valorUtilizadoDespues, valorUtilizadoAntesUSD, 
						valorUtilizadoDespuesUSD, valorServicio, usuario,fechaActual, horaActual ,conJdbc)){
					respuesta.setSobreGiro("OK registro en SMART Sobregiro");
				} else{
					respuesta.setSobreGiro("ERROR registro en SMART Sobregiro");
				}
			}	
		}

		java.util.Date fechaFin = new java.util.Date();
		System.out.println("Ingreso de Linea de Credito demoro: "+
				(fechaFin.getTime() - fecha.getTime())/1000+" Segundos");

		return respuesta;
	}
}
