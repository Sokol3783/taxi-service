package org.example.dao;

import org.json.JSONArray;

import java.util.Map;

@FunctionalInterface
public interface DAOReport {
    JSONArray getReport(String query, Map<Integer, String> conditions);
}
