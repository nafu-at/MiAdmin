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

import online.pandasoft.miadmin.core.database.DatabaseConnector;
import online.pandasoft.miadmin.core.database.DatabaseTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SystemParametersTable extends DatabaseTable {

    public SystemParametersTable(String prefix, DatabaseConnector connector) {
        super(prefix, "system_settings", connector);
    }

    public void createTable() throws SQLException {
        super.createTable("properties TINYTEXT NOT NULL, property LONGTEXT NULL, PRIMARY KEY(properties(255))");
    }

    @Override
    public void createTableColumn(String name, String type) throws SQLException {
        throw new UnsupportedOperationException("This operation is not allowed in this table.");
    }

    @Override
    public void dropTableColumn(String name) throws SQLException {
        throw new UnsupportedOperationException("This operation is not allowed in this table.");
    }

    public String getProperty(String key) throws SQLException {
        try (Connection connection = getConnector().getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT property FROM " + getTablename() + " WHERE properties = ?")) {
            ps.setString(1, key);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next())
                    return resultSet.getString("property");
                return null;
            }
        }
    }

    public void setProperty(String key, String value) throws SQLException {
        try (Connection connection = getConnector().getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO " + getTablename() + " (properties, property)" +
                     " VALUES (?, ?) ON DUPLICATE KEY UPDATE properties = VALUES (property)")) {
            ps.setString(1, key);
            ps.setString(2, value);
            ps.execute();
        }
    }
}
