package com.cocha.business.calculos;

import java.sql.Connection;

import com.cocha.domain.Bmcli;
import com.cocha.domain.DatosSalida;
import com.cocha.persistance.SmartDao;
import com.cocha.utils.FechasUtil;

public class GetLineaCredito {
	public DatosSalida obtieneLineaCredito(String nemote, Connection conJdbc) throws Exception {		
		long lineaEquivalenteAutorizada = 0;
		long lineaEquivalenteUtilizada = 0;
		long lineaEquivalenteDisponible = 0;

		FechasUtil fechas = new FechasUtil();
		Bmcli cliente = new Bmcli();
		SmartDao sqlSmartDao = new SmartDao();

		cliente = sqlSmartDao.getCliente(nemote, conJdbc);

		int tasaCambio = sqlSmartDao.getTasaCambio(fechas.getFechaActual(), conJdbc);
		long lineaUtilizadaCLP= sqlSmartDao.getLineaUtilizadaCLP(cliente.getRut(), conJdbc);
		Double lineaUtilizadaUSD= sqlSmartDao.getLineaUtilizadaUSD(cliente.getRut(), conJdbc);
		long lineaDisponibleCLP = cliente.getLineaCLP() - lineaUtilizadaCLP;
		double lineaDisponibleUSD = cliente.getLineaUSD() - lineaUtilizadaUSD;
		lineaUtilizadaUSD = fechas.round(lineaUtilizadaUSD, 2);
		lineaDisponibleUSD = fechas.round(lineaDisponibleUSD, 2);

		DatosSalida salida = new DatosSalida();
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
