package com.cocha.business;

import java.sql.Connection;

import com.cocha.business.calculos.GetLineaCredito;
import com.cocha.dto.DatosSalidaTO;

public class ConsultaLineaCredito {

	public DatosSalidaTO ejecucion (String nemote, Connection con) throws Exception {
		java.util.Date fecha = new java.util.Date();	

		GetLineaCredito getLineaCredito = new GetLineaCredito();
		DatosSalidaTO salida = getLineaCredito.obtieneLineaCredito(nemote, con);

		java.util.Date fechaFin = new java.util.Date();
		System.out.println("Consulta Linea de Credito demoro: "+
				(fechaFin.getTime() - fecha.getTime())/1000+" Segundos");

		return salida;
	}

}
