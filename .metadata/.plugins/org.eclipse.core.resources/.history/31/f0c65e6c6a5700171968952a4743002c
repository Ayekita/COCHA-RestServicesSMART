package com.cocha.test;

import java.sql.Connection;

import com.cocha.business.ConsultaLineaCredito;
import com.cocha.business.ControlFacturacionFinanzas;
import com.cocha.business.IngresoLineaCredito;
import com.cocha.domain.DatosSalida;
import com.cocha.persistance.SmartConnections;

public class DoTest {
	public static void main(String[] args) {
		try {
			String nemote = "COPEC";
			SmartConnections conectame = SmartConnections.getInstance();
			Connection conJdbc = conectame.getConnSmart();
					

			//CONSULTA LINEA DE CREDITO
			DatosSalida salida;
			ConsultaLineaCredito consultaLineaCredito = new ConsultaLineaCredito();	
			salida = consultaLineaCredito.ejecucion(nemote, conJdbc);			
			System.out.println("CONSULTA LINEA DE CREDITO: "+salida.toString());	

			//CONSULTA CONTROL FACTURACION
			ControlFacturacionFinanzas control = new ControlFacturacionFinanzas();
			String nemo = "DAVEY";
			String negocio = "7-I92-500";
			int pedido = 395914;
			System.out.println("CONSULTA CONTROL FACTURACION: "+
					control.controlFacturacion(negocio, pedido, nemo, conJdbc));	

			//INGRESO LIENA DE CREDITO
			IngresoLineaCredito ingreso = new IngresoLineaCredito();
			nemo = "DAVEY";
			String moneda = "CLP";
			negocio = "7-I92-545";
			double valorServicio = 2000;
			System.out.println("INGRESO LIENA DE CREDITO: "+
					ingreso.ingresoLC( moneda, nemo, negocio, valorServicio, conJdbc));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
