/*
 * Copyright 2020 PandaSoft.Dev Social Networks.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package online.pandasoft.miadmin.core.database.tables;

import lombok.extern.slf4j.Slf4j;
import online.pandasoft.miadmin.core.database.DatabaseConnector;
import online.pandasoft.miadmin.core.database.DatabaseTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class OperationLogTable extends DatabaseTable {

    public OperationLogTable(String prefix, DatabaseConnector connector) {
        super(prefix, "operation_log", connector);
    }

    public void createTable() throws SQLException {
        super.createTable("operation_time TIMESTAMP NOT NULL, operation_user TINYTEXT NOT NULL, execute_class TINYTEXT NULL, " +
                "description LONGTEXT NULL");
    }

    @Override
    public void createTableColumn(String name, String type) throws SQLException {
        throw new UnsupportedOperationException("This operation is not allowed in this table.");
    }

    @Override
    public void dropTableColumn(String name) throws SQLException {
        throw new UnsupportedOperationException("This operation is not allowed in this table.");
    }

    /**
     * Gets the log recorded in the specified range.
     *
     * @param from
     * @param to
     * @return The log recorded in the specified range
     * @throws SQLException It will be thrown if the log fails to be retrieved.
     */
    public List<OperationLog> searchOperationLogBetween(Date from, Date to) throws SQLException {
        List<OperationLog> operationLogs = new ArrayList<>();
        try (Connection connection = getConnector().getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + getTablename() + " WHERE operation_time BETWEEN ? AND ?")) {
            ps.setDate(1, new java.sql.Date(from.getTime()));
            ps.setDate(2, new java.sql.Date(to.getTime()));
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next())
                    operationLogs.add(resultToLog(resultSet));
            }
        }
        return operationLogs;
    }

    /**
     * Gets the log recorded since the specified date.
     *
     * @param since
     * @return The log recorded in the specified range
     * @throws SQLException It will be thrown if the log fails to be retrieved.
     */
    public List<OperationLog> searchOperationLogFrom(Date since) throws SQLException {
        List<OperationLog> operationLogs = new ArrayList<>();
        try (Connection connection = getConnector().getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + getTablename() + " WHERE operation_time > ?")) {
            ps.setDate(1, new java.sql.Date(since.getTime()));
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next())
                    operationLogs.add(resultToLog(resultSet));
            }
        }
        return operationLogs;
    }

    /**
     * Gets the log recorded up to the specified date.
     *
     * @param to
     * @return The log recorded in the specified range
     * @throws SQLException It will be thrown if the log fails to be retrieved.
     */
    public List<OperationLog> searchOperationLogTo(Date to) throws SQLException {
        List<OperationLog> operationLogs = new ArrayList<>();
        try (Connection connection = getConnector().getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + getTablename() + " WHERE operation_time < ?")) {
            ps.setDate(1, new java.sql.Date(to.getTime()));
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next())
                    operationLogs.add(resultToLog(resultSet));
            }
        }
        return operationLogs;
    }

    /**
     * Gets the operation log of the specified user.
     *
     * @param operationUser
     * @return Log of operations performed by the specified user
     * @throws SQLException It will be thrown if the log fails to be retrieved.
     */
    public List<OperationLog> searchOperationUser(String operationUser) throws SQLException {
        List<OperationLog> operationLogs = new ArrayList<>();
        try (Connection connection = getConnector().getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + getTablename() + " WHERE operation_user = ?")) {
            ps.setString(1, operationUser);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next())
                    operationLogs.add(resultToLog(resultSet));
            }
        }
        return operationLogs;
    }

    /**
     * Get the operation log of the specified class.
     *
     * @param executeClass
     * @return Log of operations performed by the specified class
     * @throws SQLException It will be thrown if the log fails to be retrieved.
     */
    public List<OperationLog> searchOperationClass(String executeClass) throws SQLException {
        List<OperationLog> operationLogs = new ArrayList<>();
        try (Connection connection = getConnector().getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + getTablename() + " WHERE execute_class = ?")) {
            ps.setString(1, executeClass);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next())
                    operationLogs.add(resultToLog(resultSet));
            }
        }
        return operationLogs;
    }

    public void saveOperationLog(OperationLog operationLog) throws SQLException {
        try (Connection connection = getConnector().getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO " + getTablename() +
                     " (operation_time, operation_user, execute_class, description) VALUES (?, ?, ?, ?)")) {
            ps.setTimestamp(1, operationLog.operationTime);
            ps.setString(2, operationLog.operationUser);
            ps.setString(3, operationLog.executeClass);
            ps.setString(4, operationLog.description);
            ps.execute();
        }
    }

    private OperationLog resultToLog(ResultSet resultSet) throws SQLException {
        Timestamp date = resultSet.getTimestamp("operation_time");
        String user = resultSet.getString("operation_user");
        String executeClass = resultSet.getString("execute_class");
        String description = resultSet.getString("description");
        return (new OperationLog(date, user, executeClass, description));
    }


    public static class OperationLog {
        private final Timestamp operationTime;
        private final String operationUser;
        private final String executeClass;
        private final String description;

        public OperationLog(Timestamp operationTime, String operationUser, String executeClass, String description) {
            this.operationTime = operationTime;
            this.operationUser = operationUser;
            this.executeClass = executeClass;
            this.description = description;
        }

        public Timestamp getOperationTime() {
            return operationTime;
        }

        public String getOperationUser() {
            return operationUser;
        }

        public String getExecuteClass() {
            return executeClass;
        }

        public String getDescription() {
            return description;
        }
    }
}
