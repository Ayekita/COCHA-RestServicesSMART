package com.cocha.business;

import java.sql.Connection;

import com.cocha.business.calculos.GetLineaCredito;
import com.cocha.business.calculos.SetLineaCredito;
import com.cocha.business.calculos.setSobregiro;
import com.cocha.domain.DatosSalida;
import com.cocha.utils.FechasUtil;

public class IngresoLineaCredito {

	public String ingresoLC(String moneda, String nemo, String negocio, double valorServicio, Connection conJdbc) throws Exception{
		java.util.Date fecha = new java.util.Date();	
		String status = "ERROR ingreso pago con Linea de Credito";

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
		if(moneda.equals("CLP")){
			valorUtilizadoAntes = salida.getLineaEquivalenteUtilizada();
			valorDisponible = salida.getLineaEquivalenteDisponible();
		}
		else if (moneda.equals("USD")){
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
			status = "OK pago con Linea de Credito";			
			if(valorDisponible <= 0 ){
				if(sobregiro.sobregiroLineaCredito(moneda, nemo, rutCli, negocio, valorUtilizadoAntes, valorUtilizadoDespues, valorUtilizadoAntesUSD, 
						valorUtilizadoDespuesUSD, valorServicio, usuario,fechaActual, horaActual ,conJdbc)){
					status += "\nOK registro en SMART Sobregiro";
				} else{
					status += "\nERROR registro en SMART Sobregiro";
				}
			}	
		}

		java.util.Date fechaFin = new java.util.Date();
		System.out.println("Ingreso de Linea de Credito demoro: "+
				(fechaFin.getTime() - fecha.getTime())/1000+" Segundos");

		return status;
	}
}
