package com.cocha.business.calculos;

import java.sql.Connection;

import com.cocha.dao.SmartDao;
import com.cocha.dto.BmcliTO;
import com.cocha.dto.DatosSalidaTO;
import com.cocha.utiles.FechasUtil;

public class GetLineaCredito {
	public DatosSalidaTO obtieneLineaCredito(String nemote, Connection conJdbc) throws Exception {		
		long lineaEquivalenteAutorizada = 0;
		long lineaEquivalenteUtilizada = 0;
		long lineaEquivalenteDisponible = 0;

		FechasUtil fechas = new FechasUtil();
		BmcliTO cliente = new BmcliTO();
		SmartDao sqlSmartDao = new SmartDao();

		cliente = sqlSmartDao.getCliente(nemote, conJdbc);

		int tasaCambio = sqlSmartDao.getTasaCambio(fechas.getFechaActual(), conJdbc);
		long lineaUtilizadaCLP= sqlSmartDao.getLineaUtilizadaCLP(cliente.getRut(), conJdbc);
		Double lineaUtilizadaUSD= sqlSmartDao.getLineaUtilizadaUSD(cliente.getRut(), conJdbc);
		long lineaDisponibleCLP = cliente.getLineaCLP() - lineaUtilizadaCLP;
		double lineaDisponibleUSD = cliente.getLineaUSD() - lineaUtilizadaUSD;
		lineaUtilizadaUSD = fechas.round(lineaUtilizadaUSD, 2);
		lineaDisponibleUSD = fechas.round(lineaDisponibleUSD, 2);

		DatosSalidaTO salida = new DatosSalidaTO();
		salida.setRut(cliente.getRut());
		salida.setDv(cliente.getDv());
		salida.setNemote(cliente.getNemote());
		salida.setNemogr(cliente.getNemogr());
		salida.setRazonSocial(cliente.getRazonSocial());		
		salida.setTasaCambio(tasaCambio);		
		salida.setLineaAutorizadaCLP(cliente.getLineaCLP());
		salida.setLineaAutorizadaUSD(cliente.getLineaUSD());		
		salida.setLineaUtilizadaCLP(lineaUtilizadaCLP);
		salida.setLineaUtilizadaUSD(lineaUtilizadaUSD);	
		salida.setLineaDisponibleCLP(lineaDisponibleCLP);
		salida.setLineaDisponibleUSD(lineaDisponibleUSD);
		salida.setLineaEquivalenteAutorizada(lineaEquivalenteAutorizada);

		if(cliente.getLineaCLP() > 0) 	{
			lineaEquivalenteAutorizada += cliente.getLineaCLP();
		}
		if(cliente.getLineaUSD() > 0) {
			cliente.setLineaUSD(cliente.getLineaUSD() * tasaCambio);
			lineaEquivalenteAutorizada += cliente.getLineaUSD();
		}

		if(lineaUtilizadaCLP > 0) {
			lineaEquivalenteUtilizada += lineaUtilizadaCLP;
		}
		if(lineaUtilizadaUSD > 0) {
			lineaUtilizadaUSD = lineaUtilizadaUSD * tasaCambio;
			lineaEquivalenteUtilizada += lineaUtilizadaUSD;
		}

		salida.setLineaEquivalenteUtilizada(lineaEquivalenteUtilizada);
		salida.setLineaEquivalenteUtilizadaUSD(fechas.round((lineaEquivalenteUtilizada/tasaCambio),2));
		lineaEquivalenteDisponible = lineaEquivalenteAutorizada - lineaEquivalenteUtilizada;
		salida.setLineaEquivalenteDisponible(lineaEquivalenteDisponible);
		salida.setLineaEquivalenteDisponibleUSD(fechas.round((lineaEquivalenteDisponible/tasaCambio),2));
		salida.setTipo(cliente.getTipo());
		salida.setFrecuencia(cliente.getFrecuencia());
		salida.setReqOC(cliente.getReqOC());

		return salida;
	}
}
