package com.cocha.servicefasttwo.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cocha.servicefasttwo.utils.FechasUtil;

public class SmartDao {

	public int obtenerCorrelativo(Connection conJdbc) throws Exception	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		int correlativo = 0;
		try	{			
			StringBuilder select = new StringBuilder();
			select.append("SELECT VMCAF_NUMMOV FROM P_VMCAF04 WHERE VMCAF_NUMMOV < 99999999 ");
			ps = conJdbc.prepareStatement(select.toString());
			rs = ps.executeQuery();				
			while(rs.next()) { 
				correlativo = rs.getInt("VMCAF_NUMMOV") + 1;
				break;
			}		
		} 
		catch (Exception e) {
			System.out.println("<error>SmartDao -obtenerCorrelativo: "+e+"</error>");
		}
		finally	{
			if(rs!=null)
				rs.close();			
			if(ps!= null)
				ps.close();			
		}	
		return correlativo;
	}

	public String obtenerIdPedido(String negocio, String spnr, Connection conJdbc) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String pedido = "";
		try	{			
			StringBuilder select = new StringBuilder();
			select.append("SELECT WSS01_IDSPNRH FROM P_WSS0101 ");
			select.append("WHERE WSS01_SPNR='").append(spnr).append("' AND WSS01_NUMNEG='").append(negocio).append("' ");
			select.append("ORDER BY WSS01_IDSPNRH DESC ");
			ps = conJdbc.prepareStatement(select.toString());
			rs = ps.executeQuery();				
			while(rs.next()) { 
				pedido = rs.getString("WSS01_IDSPNRH");
				break;
			}		
		} 
		catch (Exception e) {
			System.out.println("<error>SmartDao -obtenerPedido: "+e+"</error>");
		}
		finally {
			if(rs!=null)
				rs.close();			
			if(ps!= null)
				ps.close();
		}	
		return pedido;
	}

	public int pedidoWss04(String idspnr, String negocio, Connection conJdbc) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int pedido = 0;
		try	{			
			StringBuilder select = new StringBuilder();
			select.append("SELECT WSS04_NUMSOL FROM P_WSS0401 ");
			select.append("WHERE WSS04_IDSPNR='").append(idspnr).append("' ");
			select.append("AND WSS04_NUMNEG='").append(negocio).append("' ");
			ps = conJdbc.prepareStatement(select.toString());
			rs = ps.executeQuery();				
			while(rs.next()) { 
				pedido = rs.getInt("WSS04_NUMSOL");				
				break;
			}		
		} 
		catch (Exception e) {
			System.out.println("<error>SmartDao -pedidoWss04: "+e+"</error>");
		}
		finally	{
			if(rs!=null)
				rs.close();
			if(ps!= null)
				ps.close();
		}	
		return pedido;
	}

	public boolean existeFee(String negocio, String spnr, String moneda, int numped, Connection conJdbc) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean existe = false;
		try	{			
			StringBuilder select = new StringBuilder();
			select.append("SELECT VMCAF_NUMNEG FROM P_VMCAF01 WHERE VMCAF_NUMNEG='").append(negocio).append("' ");
			select.append("AND VMCAF_CODPPC='FAST' AND VMCAF_CODMON='").append(moneda).append("' ");
			select.append("AND VMCAF_SPNR='").append(spnr).append("' AND VMCAF_NUMPED=").append(numped);
			ps = conJdbc.prepareStatement(select.toString());
			rs = ps.executeQuery();				
			while(rs.next()) { 
					existe = true;
			}		
		} 
		catch (Exception e) {
			System.out.println("<error>SmartDao -existeFee: "+e+"</error>");
		}
		finally	{
			if(rs!=null)
				rs.close();
			if(ps!= null)
				ps.close();
		}	
		return existe;
	}

	public boolean insertarFee(String negocio, int numped, String moneda, double valor, String spnr,Connection conJdbc) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean respuesta = false;
		try	{
			FechasUtil funciones = new FechasUtil();
			SmartDao smartDao = new SmartDao();
			int correlativo = smartDao.obtenerCorrelativo(conJdbc);
			StringBuilder insert = new StringBuilder();
			insert.append("INSERT INTO VMCAF (VMCAF_AREA, VMCAF_AREAT, VMCAF_AREA2, VMCAF_CANCAR, VMCAF_CODUSU, VMCAF_CODMON,");
			insert.append("VMCAF_CODPPC, VMCAF_FECCAR, VMCAF_HORCAR, VMCAF_NUMNEG, VMCAF_NUMMOV, VMCAF_NUMPED, VMCAF_VALCAR, VMCAF_SPNR) ");
			insert.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			ps = conJdbc.prepareStatement(insert.toString());
			ps.setString(1, "X");
			ps.setString(2, "F");
			ps.setString(3, "XTK");
			ps.setInt(4, 1);
			ps.setString(5, "ONLINE");
			ps.setString(6, moneda);
			ps.setString(7, "FAST");
			ps.setLong(8, funciones.getFechaActual());
			ps.setLong(9, funciones.getHoraAtual());
			ps.setString(10, negocio);
			ps.setInt(11, correlativo);
			ps.setLong(12, numped);
			ps.setDouble(13, valor);
			ps.setString(14, spnr);
			ps.addBatch();
			ps.executeBatch();
			ps.clearBatch();
			respuesta = true;
		} 
		catch (Exception e) {
			System.out.println("<error>SmartDao -insertarFee: "+e+"</error>");
			return false;
		}
		finally {
			if(rs!=null)
				rs.close();
			if(ps!= null)
				ps.close();
		}	
		return respuesta;
	}
}

