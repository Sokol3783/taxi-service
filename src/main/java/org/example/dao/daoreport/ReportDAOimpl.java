package org.example.dao.daoreport;

import org.example.dao.ReportDAO;
import org.example.dao.SimpleConnectionPool;
import org.example.dao.connectionpool.BasicConnectionPool;
import org.example.exceptions.DAOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReportDAOimpl implements ReportDAO {

    SimpleConnectionPool pool = BasicConnectionPool.getInstance();
    private static final Logger log = LoggerFactory.getLogger(ReportDAOimpl.class);

    @Override
    public JSONArray getReport(String query, Map<Integer, String> conditions) {
        try (Connection con = pool.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            setConditions(statement, conditions);
            ResultSet resultSet = statement.executeQuery();
            return createArrayString(resultSet);
        } catch (SQLException e) {
            log.error(DAOException.REPORT_MISTAKE);
            throw new DAOException(DAOException.REPORT_MISTAKE);
        }
    }

    private PreparedStatement setConditions(PreparedStatement statement, Map<Integer, String> conditions) {
        conditions.forEach((key, value) -> {
            try {
                statement.setString(key, value);
            } catch (SQLException e) {
                log.error(DAOException.REPORT_SET_CONDITION, e);
                throw new DAOException(e);
            }
        });
        return statement;
    }

    private JSONArray createArrayString(ResultSet resultSet) {
        try {
            List<String> colNames = getColumns(resultSet);
            return rowsInJson(resultSet, colNames);
        } catch (SQLException e) {
            log.error(DAOException.REPORT_BUILD_RESULT_ARRAY, e);
            throw new DAOException(e);
        }
    }

    private JSONArray rowsInJson(ResultSet resultSet, List<String> colNames) throws SQLException {
        JSONArray result = new JSONArray();
        while (resultSet.next()) {
            JSONObject row = new JSONObject();
            colNames.forEach(cn -> {
                try {
                    row.put(cn, resultSet.getObject(cn));
                } catch (JSONException | SQLException e) {
                    e.printStackTrace();
                }
            });
            result.put(row);
        }
        return result;
    }

    private List<String> getColumns(ResultSet resultSet) throws SQLException {
        ResultSetMetaData md = resultSet.getMetaData();
        int numCols = md.getColumnCount();
        return IntStream.range(0, numCols)
                .mapToObj(i -> {
                    try {
                        return md.getColumnName(i + 1);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return "?";
                    }
                })
                .collect(Collectors.toList());
    }
}
