package org.txlcn.demo.servicea;

import javax.sql.DataSource;
import javax.sql.XADataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * Description:
 * Date: 19-2-25 上午11:00
 *
 * @author ujued
 */
public class DataSourceBean implements DataSource {
    private XADataSource xaDataSource;

    public DataSourceBean(XADataSource xaDataSource) {
        this.xaDataSource = xaDataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.xaDataSource.getXAConnection().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return this.xaDataSource.getXAConnection().getConnection();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return xaDataSource.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        xaDataSource.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        xaDataSource.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return xaDataSource.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return xaDataSource.getParentLogger();
    }
}
