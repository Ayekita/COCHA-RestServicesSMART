package com.cocha.business.calculos;

import java.sql.Connection;

import com.cocha.dao.SmartDao;
import com.cocha.dto.VmlcdTO;

public class setSobregiro {
	public boolean sobregiroLineaCredito(String moneda, String nemo, int rut, String numneg, 
			long vallca, long vallcd, double vallcaUSD, double vallcdUSD, double monto, String usuario, 
			long fechaActual, long horaActual, Connection con) 
					throws ClassNotFoundException{    	    	
		boolean status = false;
		VmlcdTO datos = new VmlcdTO();
		datos.setAutoriza(usuario);
		datos.setEstado(0);
		datos.setMonlca(moneda);
		datos.setMonlcd(moneda);
		datos.setMonval(moneda);
		datos.setNemo(nemo);
		datos.setRut(rut);
		datos.setNumneg(numneg);		
		datos.setUsucre(usuario);
		datos.setFeccre(fechaActual);		
		datos.setHorcre(horaActual);		
		datos.setUsumod("");
		datos.setFecmod(0);
		datos.setHormod(0);
		datos.setVallca(vallca);
		datos.setVallcd(vallcd);
		datos.setValtot(monto);
		datos.setVallcaUSD(vallcaUSD);
		datos.setVallcdUSD(vallcdUSD);
		try {
			SmartDao sqlSmart = new SmartDao();
			status = sqlSmart.ingresoSobregiro(datos,con);
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		return status;
	}
}