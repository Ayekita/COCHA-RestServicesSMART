package com.cocha.business.calculos;

import java.sql.Connection;

import com.cocha.dao.SmartDao;
import com.cocha.dto.FmsctTo;

public class SetLineaCredito {
	public boolean ingresoLinCred(int rutCli,String moneda, Double valor, 
			long fechaActual, long horaActual, Connection conJdbc){
		boolean isOk = false;
		SmartDao dao = new SmartDao();
		try {
			FmsctTo fmsct = dao.existeFmsct(rutCli, conJdbc);
			if (fmsct != null){
				if("CLP".equalsIgnoreCase(moneda)){
					fmsct.setTrClp(valor + fmsct.getTrClp());
				}else if("USD".equalsIgnoreCase(moneda)){
					fmsct.setTrUsd(valor + fmsct.getTrUsd());
				}
				isOk = dao.updateFmcst(fmsct, conJdbc);
			}else{
				fmsct = new FmsctTo();
				fmsct.setCodUsuar("ONLINE");
				fmsct.setFecAct(fechaActual);
				fmsct.setHrUltran(horaActual);
				fmsct.setFecUltran(fechaActual);
				fmsct.setNumRut(rutCli);
				fmsct.setSalClp(0);
				fmsct.setSalUsd(0);				
				if("CLP".equalsIgnoreCase(moneda)){
					fmsct.setTrClp(valor);
					fmsct.setTrUsd(0);
				}else if("USD".equalsIgnoreCase(moneda)){
					fmsct.setTrUsd(valor);
					fmsct.setTrClp(0);
				}
				isOk = dao.insertFmsct(fmsct, conJdbc);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return isOk;
	}
}
