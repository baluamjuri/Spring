package org.balu.batchpoc.misc;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class PersonIdGenerator implements IdentifierGenerator{
	private final static String FIRST_ID = "A001";
	private final static String ID_COLUMN_ALIAS = "max_id";
	private final static String EXCEPTION = "ID overflow exception";
	private final static String QUERY =  "select MAX(person_id) as "+ID_COLUMN_ALIAS +" from person";

	public String newId(String oldId) {
		
		if(oldId==null){
			return FIRST_ID;
		}
		
		String newId;
		
		char firstPart = oldId.charAt(0);		
		int secondPart = Integer.parseInt(oldId.split(String.valueOf(firstPart))[1]);
		
		char nextFirstPart;
		
		if(secondPart == 999){
			if(firstPart == 'Z')
				throw new HibernateException(EXCEPTION);
			 nextFirstPart = (char)(firstPart+1);
			 newId = nextFirstPart+"001";
		}
		else{
			nextFirstPart = firstPart;
			newId = new Integer((secondPart+1)+1000)
			.toString()
			.replace('1', nextFirstPart);
		}
		
		return newId;
	}

	@Override
	public Serializable generate(SharedSessionContractImplementor session,
			Object arg1) throws HibernateException {
		Connection con = session.connection();
		String oldId=null;
		try(PreparedStatement ps = con.prepareStatement(QUERY);
				ResultSet rs = ps.executeQuery();) {
			
			while(rs.next()){
				oldId = rs.getString(ID_COLUMN_ALIAS);
			}
			
			return newId(oldId);
		} catch (SQLException e) {
			System.out.println("Exception came!!!");
			System.out.println(e.getMessage());
		}
		return null;
	}
}