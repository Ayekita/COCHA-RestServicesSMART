package com.cocha.utiles;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.ss.usermodel.DateUtil;


public class FechasUtil {
	public long getFechaActual() {		
		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("ddMMyyyy");
		String fechaNormal = formateador.format(ahora);
		long fechaSmart = calcularFechaNormalSmart(fechaNormal);
		return fechaSmart;
	}

	public long getHoraActual() {		
		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("HHmmss");
		long hora = Long.parseLong(formateador.format(ahora));
		System.out.println(hora);
		return hora;
	}

	public long calcularFechaNormalSmart(int iDia, int iMes, int iAno) {
		long fechaSmart;

		// Crear 2 instancias de Calendar
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		// Establecer las fechas
		cal1.set(1957, 1, 1);
		cal2.set(iAno, iMes, iDia);
		//cal2.set(2014, 6, 20);

		// conseguir la representacion de la fecha en milisegundos
		long milis1 = cal1.getTimeInMillis();
		long milis2 = cal2.getTimeInMillis();

		long fechainicial = milis1 / 86400000; //(24 * 60 * 60 * 1000);
		long fechaactual = milis2 / 86400000; //(24 * 60 * 60 * 1000);

		// calcular la diferencia en milisengundos
		long diff = fechaactual - fechainicial;

		// calcular la diferencia en dias
		long diffDays = diff; // / 86400000; //(24 * 60 * 60 * 1000);

		fechaSmart = (diffDays + 1);

		switch ( iMes ) {
		case 2:
			fechaSmart = fechaSmart + 2;
			break;
		case 4:
			fechaSmart = fechaSmart + 1;
			break;
		case 6:
			fechaSmart = fechaSmart + 1;
			break;
		case 9: //
			fechaSmart = fechaSmart + 1;
			break;
		case 11:
			fechaSmart = fechaSmart + 1;
			break;     
		default:	          
			break;
		}

		return fechaSmart;
	}

	public long calcularFechaNormalSmart(String fecha) {
		int iDia = 0;
		int iMes = 0;
		int iAno = 0; 
		if(fecha.length()>6) {
			iDia = Integer.parseInt(fecha.substring(0, 2));
			iMes = Integer.parseInt(fecha.substring(2, 4));
			iAno = Integer.parseInt(fecha.substring(4, 8));

			return calcularFechaNormalSmart(iDia, iMes, iAno);
		}else {
			return 0;
		}
	}

	public String calcularFechaSmartNormal(double val) {	
		String fecha = "";
		Date fechaSmart = new Date();		
		fechaSmart = DateUtil.getJavaDate(val + 20821);	

		SimpleDateFormat newformatter = new SimpleDateFormat("dd-MM-yyyy");
		fecha = newformatter.format(fechaSmart);
		return fecha;
	}

	public double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
}