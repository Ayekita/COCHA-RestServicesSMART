package com.cocha.business.calculos;

import java.sql.Connection;

import com.cocha.dao.SmartDao;
import com.cocha.dto.VmpelTo;
import com.cocha.utiles.FechasUtil;

public class ControlFinanzas {
	public boolean insertControFinanzas(String numneg, int numPed, Connection conJdbc){
		SmartDao dao = new SmartDao();
		boolean isOk = false;
		try {
			FechasUtil fec = new FechasUtil();			
			boolean isUpdate = false;
			String tipoServ = dao.getTipoServ(numneg, numPed, conJdbc);
			if (tipoServ != null) {
				isUpdate = dao.updateFacPed(tipoServ, numPed, conJdbc);
			}
			if(isUpdate){
				VmpelTo vmpel = new VmpelTo();
				vmpel.setEvento("AUTORIZACION DE FACTURACION");
				vmpel.setFectran(fec.getFechaActual());
				vmpel.setHortran(fec.getHoraActual());
				vmpel.setIspope("ONLINE");
				vmpel.setNumneg(numneg);
				vmpel.setNumped(numPed);
				vmpel.setOpera(tipoServ);
				vmpel.setUsutran("ONLINE");
				isOk = dao.insertVmpel(vmpel, conJdbc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isOk;

	}
}