package com.cocha.business.calculos;

import java.sql.Connection;

import com.cocha.persistance.SmartDao;

public class ExisteNegPed {
	public String existeNegPed(String numneg, int numped, Connection con){
		SmartDao dao = new SmartDao();
		String respuesta = "";
		try {
			respuesta = dao.existeNeg(numneg, numped, con);
			if (respuesta.equals("Existe")){
				respuesta = dao.existePedVmosk(numneg, numped, con);
				if (!respuesta.equals("Existe")){
					respuesta = dao.existePedVmosn(numneg, numped, con);
				} else if (respuesta.equals("Existe")){
					respuesta = dao.existePedVmoso(numneg, numped, con);
				} else if (respuesta.equals("Existe")){
					respuesta = dao.existePedVmcaj(numneg, numped, con);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respuesta;
	}
}
