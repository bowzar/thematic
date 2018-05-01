package com.yulintu.thematic.data.hibernate.oracle;

import java.lang.reflect.Method;
import java.sql.Connection;

import com.yulintu.thematic.data.DataException;
import oracle.jdbc.OracleConnection;

import com.mchange.v2.c3p0.C3P0ProxyConnection;
import org.geolatte.geom.codec.db.oracle.ConnectionFinder;

public class OracleC3P0ConnectionFinder implements ConnectionFinder {
    public static Connection getRawConnection(Connection con) {
        return con;
    }

    public OracleConnection find( Connection con )  {
        if (con instanceof OracleConnection) {
            return (OracleConnection) con;
        }
        if (con instanceof C3P0ProxyConnection) {
            C3P0ProxyConnection cpCon = (C3P0ProxyConnection) con;
            Connection unwrappedCon = null;
            try {
                Method rawConnectionMethod = getClass().getMethod("getRawConnection",
                        new Class[] {Connection.class});
                unwrappedCon = (Connection) cpCon.rawConnectionOperation(
                        rawConnectionMethod, null, new Object[] {C3P0ProxyConnection.RAW_CONNECTION});
            }
            catch (Exception ex) {
                throw new DataException( ex.getMessage() );
            }
            if( unwrappedCon != null && unwrappedCon instanceof OracleConnection ){
                return (OracleConnection) unwrappedCon;
            }
        }
        throw new DataException("Couldn't get Oracle Connection in OracleConnectionFinder");
    }
}