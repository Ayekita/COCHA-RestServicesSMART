package com.cocha.servicefasttwo.test;

import java.sql.Connection;

import com.cocha.servicefasttwo.business.ServiceFast2;
import com.cocha.servicefasttwo.domain.Response;

public class TestService {
	public static void main(String[] args) {
		try	{
			Connection conSmart = null;
			// Parametros de entrada
			String negocio = "7-I92-703";
			String moneda = "CLP";
			double valor = 21200.2;
			String spnr = "P001428";

			//Proceso
			ServiceFast2 ejecutar = new ServiceFast2();
			Response respuesta = ejecutar.procesoPrincipal(negocio, moneda, valor, spnr, conSmart);
			System.out.println(respuesta);
		} 
		catch (Throwable e) {
			System.err.println(e);
		}	
	}
}
