package com.cocha.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cocha.dto.BmcliTO;
import com.cocha.dto.FmsctTo;
import com.cocha.dto.VmlcdTO;
import com.cocha.dto.VmpelTo;

public class SmartDao {

	public BmcliTO getCliente(String nemote, Connection conJdbc)throws SQLException, ClassNotFoundException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		BmcliTO cliente = new BmcliTO();
		try {	
			String query = "";	
			query =  "SELECT BMCLI_NUMRUT, BMCLI_DIGVER, BMCLI_NEMOTE, BMCLI_NEMOGR, BMCLI_RAZOSOC, BMCLI_LIMCRE, BMCLI_LIMCREUSD,";
			query += "BMCLI_TIPOCL, BMCLI_FRECFAC, BMCLI_REQOC FROM P_BMCLI02 WHERE BMCLI_NEMOTE = '" + nemote +"'";
			ps = conJdbc.prepareStatement(query);
			rs = ps.executeQuery();	
			while(rs.next()) {			
				cliente.setRut(rs.getInt("BMCLI_NUMRUT"));
				cliente.setDv(rs.getString("BMCLI_DIGVER"));
				cliente.setNemote(rs.getString("BMCLI_NEMOTE"));
				cliente.setNemogr(rs.getString("BMCLI_NEMOGR"));
				cliente.setRazonSocial(rs.getString("BMCLI_RAZOSOC"));
				cliente.setLineaCLP(rs.getLong("BMCLI_LIMCRE"));
				cliente.setLineaUSD(rs.getDouble("BMCLI_LIMCREUSD"));
				cliente.setTipo(rs.getString("BMCLI_TIPOCL"));
				cliente.setFrecuencia(rs.getString("BMCLI_FRECFAC"));
				if(rs.getString("BMCLI_FRECFAC").equals("")){
					cliente.setFrecuencia("D");
				}				
				cliente.setReqOC(rs.getString("BMCLI_REQOC"));
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
		}	
		return cliente;
	}

	public int getTasaCambio(long fecha, Connection con) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "";
		int tasaCambio = 0;
		try {
			query =  "SELECT BMLAT_VALTAS FROM P_BMLAT01 WHERE BMLAT_CODLAE = 45 AND BMLAT_FECTAS = " + fecha;
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();		
			while(rs.next()) {
				tasaCambio = rs.getInt("BMLAT_VALTAS");
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
		}	
		return tasaCambio;	
	}

	public long getLineaUtilizadaCLP(int numRut, Connection con)throws SQLException, ClassNotFoundException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		long lineaUtilizadaCLP = 0;
		String query = "";	
		try {	
			query =  "SELECT FMSCT_SALCLP, FMSCT_TRCLP FROM P_FMSCT03 WHERE FMSCT_NUMRUT = " + numRut;
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();	
			while(rs.next()) {
				lineaUtilizadaCLP = rs.getLong("FMSCT_SALCLP");	
				lineaUtilizadaCLP += rs.getLong("FMSCT_TRCLP");	
			}	
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
		}		
		return lineaUtilizadaCLP;
	}

	public double getLineaUtilizadaUSD(int numRut, Connection con)throws SQLException, ClassNotFoundException {
		double lineaUtilizada = 0;
		String query = "";	
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {	
			query =  "SELECT FMSCT_SALUSD, FMSCT_TRUSD FROM P_FMSCT03 WHERE FMSCT_NUMRUT = " + numRut;
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();	
			while(rs.next()) {
				lineaUtilizada = rs.getDouble("FMSCT_SALUSD");
				lineaUtilizada += rs.getDouble("FMSCT_TRUSD");
			}	
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
		}		
		return lineaUtilizada;
	}

	public FmsctTo existeFmsct(int rutCli, Connection con) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		FmsctTo fmsct = null;
		try {
			String query =  "SELECT FMSCT_TRUSD, FMSCT_TRCLP  FROM P_FMSCT03 WHERE FMSCT_NUMRUT = " + rutCli;
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();	
			while(rs.next()) {	
				fmsct = new FmsctTo();
				fmsct.setNumRut(rutCli);
				fmsct.setTrClp(rs.getDouble("FMSCT_TRCLP"));
				fmsct.setTrUsd(rs.getDouble("FMSCT_TRUSD"));
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
		}	
		return fmsct;
	}

	public boolean ingresoSobregiro(VmlcdTO datos, Connection con) throws SQLException, ClassNotFoundException {
		boolean status = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder insert = new StringBuilder();
			insert.append("INSERT INTO VMLCD(VMLCD_AUTORIZA,VMLCD_ESTADO,VMLCD_FECCRE,VMLCD_FECMOD,VMLCD_HORCRE,VMLCD_HORMOD");
			insert.append(",VMLCD_MONLCA,VMLCD_MONLCD,VMLCD_MONVAL,VMLCD_NEMO,VMLCD_NUMNEG,VMLCD_USUCRE,VMLCD_USUMOD,VMLCD_VALLCA");
			insert.append(",VMLCD_VALLCD,VMLCD_VALTOT, VMLCD_RUT)");
			insert.append("values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			ps = con.prepareStatement(insert.toString());

			ps.setString(1,datos.getAutoriza());
			ps.setInt(2,datos.getEstado());
			ps.setLong(3,datos.getFeccre());
			ps.setLong(4,datos.getFecmod());
			ps.setLong(5,datos.getHorcre());
			ps.setLong(6,datos.getHormod());
			ps.setString(7,datos.getMonlca());
			ps.setString(8,datos.getMonlcd());
			ps.setString(9,datos.getMonval());
			ps.setString(10,datos.getNemo());
			ps.setString(11,datos.getNumneg());
			ps.setString(12,datos.getUsucre());
			ps.setString(13,datos.getUsumod());
			if(datos.getMonval().equals("CLP")) {
				ps.setLong(14,datos.getVallca());
				ps.setLong(15,datos.getVallcd());
			}
			else if(datos.getMonval().equals("USD")) {
				ps.setDouble(14,datos.getVallcaUSD());
				ps.setDouble(15,datos.getVallcdUSD());

			}
			ps.setDouble(16,datos.getValtot());
			ps.setDouble(17,datos.getRut());

			ps.execute();

			status = true;
		} 
		catch (Exception e) {
			System.out.println(e);
			return false;
		}finally{
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
		}	
		return status;
	}

	public String existeNeg(String numneg, int numped, Connection con) throws SQLException, ClassNotFoundException {
		String  existe = "El negocio " + numneg + " no existe en SMART";
		String query = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {	
			query = "SELECT VMNEG_NUMNEG FROM P_VMNEG01 WHERE VMNEG_NUMNEG = '" + numneg + "'";
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();	
			while(rs.next()) {
				existe = "Existe";		
				break;
			} 
		}catch (Exception e)  {
			e.printStackTrace();
		}finally{
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
		}		
		return existe;		
	}

	public String existePedVmosn(String numneg, int numped, Connection con) throws SQLException, ClassNotFoundException {
		String  existe ="El pedido " + numped + " no existe en SMART";
		String query = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {	
			query = "SELECT VMOSN_NUMNEG FROM P_VMOSN02 WHERE VMOSN_NUMNEG = '" + numneg + "' AND VMOSN_NUMSOL = " + numped;
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();	
			while(rs.next()) {
				existe = "Existe";		
				break;
			} 
		}catch (Exception e)  {
			e.printStackTrace();
		}finally{
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
		}		
		return existe;		
	}

	public String existePedVmoso(String numneg, int numped, Connection con) throws SQLException, ClassNotFoundException {
		String  existe ="El pedido " + numped + " no existe en SMART";
		String query = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {	
			query = "SELECT VMOSO_NUMNEG FROM P_VMOSO02 WHERE VMOSO_NUMNEG = '" + numneg + "' AND VMOSO_NUMSOL = " + numped;
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();	
			while(rs.next()) {
				existe = "Existe";		
				break;
			} 
		}catch (Exception e)  {
			e.printStackTrace();
		}finally{
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
		}		
		return existe;		
	}

	public String existePedVmcaj(String numneg, int numped, Connection con) throws SQLException, ClassNotFoundException {
		String  existe ="El pedido " + numped + " no existe en SMART";
		String query = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {	
			query = "SELECT VMACJ_NUMNEG FROM P_VMACJ01 WHERE VMACJ_NUMNEG = '" + numneg + "' AND VMACJ_NUMSOL = " + numped;
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();	
			while(rs.next()) {
				existe = "Existe";		
				break;
			} 
		}catch (Exception e)  {
			e.printStackTrace();
		}finally{
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
		}		
		return existe;		
	}

	public String existePedVmosk(String numneg, int numped, Connection con) throws SQLException, ClassNotFoundException {
		String  existe ="El pedido " + numped + " no existe en SMART";
		String query = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {	
			query = "SELECT VMOSK_NUMNEG FROM P_VMOSK02 WHERE VMOSK_NUMNEG = '" + numneg + "' AND VMOSK_NUMSOL = " + numped;
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();	
			while(rs.next()) {
				existe = "Existe";		
				break;
			} 
		}catch (Exception e)  {
			e.printStackTrace();
		}finally{
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
		}		
		return existe;		
	}

	public String getTipoServ(String numneg, int numped, Connection con) throws Exception {
		String query = "";	
		PreparedStatement ps = null;
		ResultSet rs = null;
		String indServ = null;
		try {	
			query =  "SELECT FMSFI_FLAGTR, FMSFI_INDSER FROM P_FMSFI04 WHERE FMSFI_NUMNEG = '" + numneg + "' AND FMSFI_NUMPED = " + numped;
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();	
			while(rs.next()) {
				if(rs.getInt("FMSFI_FLAGTR") != 2) {	
					indServ = rs.getString("FMSFI_INDSER");
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
		}		
		return indServ;
	}

	public  void controlFinanzasNegocio(String numneg, long fechaFac, long horaFac, Connection con) throws SQLException, ClassNotFoundException {
		int existe = 0;
		String query = "";	
		PreparedStatement ps = null;
		ResultSet rs = null;
		try  {	
			query =  "SELECT FMSFI_FLAGFAC, FMSFI_FLAGORC, FMSFI_NUMPED, FMSFI_INDSER ";
			query += "FROM P_FMSFI04 WHERE FMSFI_NUMNEG = '" + numneg + "'";
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();	

			while(rs.next())  {
				if(rs.getInt("FMSFI_FLAGFAC") == 1 || rs.getInt("FMSFI_FLAGFAC") == 3 || rs.getInt("FMSFI_FLAGORC") == 1 || rs.getInt("FMSFI_FLAGORC") == 3) {
					PreparedStatement ps2 = null;	
					ResultSet rs2 = null;	
					if(rs.getString("FMSFI_INDSER").equals("K")) {									
						query =  "SELECT COUNT (*) AS EXISTE ";
						query += "FROM P_VMOSK01 WHERE VMOSK_NUMSOL = " + rs.getInt("FMSFI_NUMPED") + " AND VMOSK_STATSA = 'KK'";
						ps2 = con.prepareStatement(query);
						rs2 = ps2.executeQuery();
						while(rs2.next())  {
							if(rs2.getInt("EXISTE") > 0) {
								existe = 1;	
							}
						}
					}
					else if(rs.getString("FMSFI_INDSER").equals("N")) {									
						query =  "SELECT COUNT (*) AS EXISTE ";
						query += "FROM P_VMOSN01 WHERE VMOSN_NUMSOL = " + rs.getInt("FMSFI_NUMPED") + " AND VMOSN_STATSA = 'KK'";
						ps2 = con.prepareStatement(query);
						rs2 = ps2.executeQuery();	
						while(rs2.next()){
							if(rs2.getInt("EXISTE") > 0) {
								existe = 1;	
							}
						}
					}
					else if(rs.getString("FMSFI_INDSER").equals("J")) {									
						query =  "SELECT COUNT (*) AS EXISTE ";
						query += "FROM P_VMACJ01 WHERE VMACJ_NUMSOL = " + rs.getInt("FMSFI_NUMPED") + " AND VMACJ_STATSA = 'KK'";
						ps2 = con.prepareStatement(query);
						rs2 = ps2.executeQuery();	
						while(rs2.next())  {
							if(rs2.getInt("EXISTE") > 0) {
								existe = 1;	
							}
						}
					}
					else if(rs.getString("FMSFI_INDSER").equals("O")) {									
						query =  "SELECT COUNT (*) AS EXISTE ";
						query += "FROM P_VMOSO01 WHERE VMOSO_NUMSOL = " + rs.getInt("FMSFI_NUMPED") + " AND VMOSO_STATSA = 'GK'";
						ps2 = con.prepareStatement(query);
						rs2 = ps2.executeQuery();	
						while(rs2.next())  {
							if(rs2.getInt("EXISTE") > 0) {
								existe = 1;	
							}
						}
					}
				}
				if(existe == 0) {
					PreparedStatement ps3 = null;	
					query =  "UPDATE VMNEG SET VMNEG_FECAFAC = " + fechaFac + ", VMNEG_HRAAFAC = " + horaFac + ", VMNEG_SUPFACT = 'ONL', VMNEG_FLAGFAC = 2 ";
					query += "WHERE VMNEG_NUMNEG = '" + numneg +"'";
					ps3 = con.prepareStatement(query);
					ps3.execute();	
				}
			}
		} 
		catch (SQLException e)  {
			e.printStackTrace();
		}finally{
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
		}	
	}

	public long getRutCliente(String nemote, Connection conJdbc)throws SQLException, ClassNotFoundException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		long rut = 0;
		try {	
			String query = "";	
			query =  "SELECT BMCLI_NUMRUT FROM P_BMCLI02 WHERE BMCLI_NEMOTE = '" + nemote +"'";
			ps = conJdbc.prepareStatement(query);
			rs = ps.executeQuery();	
			while(rs.next()) {			
				rut = rs.getLong("BMCLI_NUMRUT");
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				rs.close();
			}
			if(ps != null){
				ps.close();
			}
		}	
		return rut;
	}

	public boolean insertVmpel(VmpelTo vmpel, Connection conJdbc)throws SQLException, ClassNotFoundException {
		PreparedStatement ps = null;
		StringBuilder insert = new StringBuilder();
		boolean isOk = false;
		try {	
			insert.append("INSERT INTO VMPEL (VMPEL_FECTRAN, VMPEL_HORTRAN, VMPEL_NUMNEG");	
			insert.append(",VMPEL_NUMPED, VMPEL_OPERA, VMPEL_USUTRAN, VMPEL_EVENTO, VMPEL_ISPOPE) ");	
			insert.append("VALUES(?,?,?,?,?,?,?,?)");
			ps = conJdbc.prepareStatement(insert.toString());
			ps.setLong(1, vmpel.getFectran());
			ps.setLong(2, vmpel.getHortran());
			ps.setString(3, vmpel.getNumneg()); 
			ps.setInt(4, vmpel.getNumped());
			ps.setString(5, vmpel.getOpera());
			ps.setString(6, vmpel.getUsutran());
			ps.setString(7, vmpel.getEvento());
			ps.setString(8, vmpel.getIspope());			
			ps.execute();
			isOk = true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(ps != null){
				ps.close();
			}
		}	
		return isOk;
	}

	public boolean updateFacPed(String tipoServ,int numPed , Connection conJdbc)throws SQLException, ClassNotFoundException {
		PreparedStatement ps = null;
		boolean isOk = false;
		String query = "";
		try {	
			if(tipoServ.equals("K")) {				
				query =  "UPDATE P_VMOSK01 SET VMOSK_FLAGFAC = 2 ";											
				query += "WHERE VMOSK_NUMSOL = " + numPed;

			}else  if(tipoServ.equals("N")) {
				query =  "UPDATE P_VMOSN01 SET VMOSN_FLAGFAC = 2 ";										
				query += "WHERE VMOSN_NUMSOL = " + numPed;

			}else  if(tipoServ.equals("J")) {						
				query =  "UPDATE P_VMACJ01 SET VMACJ_FLAGFAC = 2 ";							
				query += "WHERE VMACJ_NUMSOL = " + numPed;

			}else  if(tipoServ.equals("O")) {					
				query =  "UPDATE P_VMOSO01 SET VMOSO_FLAGFAC = 2 ";							
				query += "WHERE VMOSO_NUMSOL = " + numPed;
			}
			ps = conJdbc.prepareStatement(query);
			ps.execute();
			isOk = true;
		} 
		catch (Exception e) {
			return false;
		}finally{
			if(ps != null){
				ps.close();
			}
		}	
		return isOk;
	}

	public boolean insertFmsct(FmsctTo fmsctTo, Connection conJdbc)throws SQLException, ClassNotFoundException {
		PreparedStatement ps = null;
		StringBuilder insert = new StringBuilder();
		boolean isOk = false;
		try {	
			insert.append("INSERT INTO FMSCT (FMSCT_CODUSUAR, FMSCT_FECACT, FMSCT_FEULTRAN, FMSCT_HRULTRAN, FMSCT_NUMRUT");	
			insert.append(",FMSCT_TRCLP,FMSCT_TRUSD, FMSCT_SALCLP, FMSCT_SALUSD) ");	
			insert.append("VALUES(?,?,?,?,?,?,?,?,?)");
			ps = conJdbc.prepareStatement(insert.toString());
			ps.setString(1, fmsctTo.getCodUsuar());
			ps.setLong(2, fmsctTo.getFecAct());
			ps.setLong(3, fmsctTo.getFecUltran()); 
			ps.setLong(4, fmsctTo.getHrUltran());
			ps.setLong(5, fmsctTo.getNumRut());
			ps.setDouble(6, fmsctTo.getTrClp());
			ps.setDouble(7, fmsctTo.getTrUsd());
			ps.setDouble(8, fmsctTo.getSalClp());
			ps.setDouble(9, fmsctTo.getSalUsd());	
			ps.execute();
			isOk = true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(ps != null){
				ps.close();
			}
		}	
		return isOk;
	}

	public boolean updateFmcst(FmsctTo fmsct ,Connection conJdbc)throws SQLException, ClassNotFoundException {
		PreparedStatement ps = null;
		boolean isOk = false;
		String query = "";
		try {			
			query =  "UPDATE FMSCT SET FMSCT_TRCLP = " + fmsct.getTrClp();	
			query +=  ", FMSCT_TRUSD = " + fmsct.getTrUsd();		
			query += " WHERE FMSCT_NUMRUT = " + fmsct.getNumRut();
			ps = conJdbc.prepareStatement(query);
			ps.execute();
			isOk = true;
		} 
		catch (Exception e) {
			return false;
		}finally{
			if(ps != null){
				ps.close();
			}
		}	
		return isOk;
	}
}
