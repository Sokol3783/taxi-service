package org.example.controllers.managers;

import org.example.dao.DAOReport;
import org.example.dao.daoreport.QueryReportTexts;
import org.example.dao.daoreport.SimpleReportDAO;
import org.example.models.User;
import org.json.JSONArray;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ReportManager {

    private static DAOReport dao;
    private static ReportManager report;

    private ReportManager() {
        dao = new SimpleReportDAO();
    }

    public static ReportManager getInstance() {
        synchronized (ReportManager.class) {
            if (report == null) {
                report = new ReportManager();
            }
        }
        return report;
    }

    public JSONArray getOrderByPeriod(LocalDate start, LocalDate end) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, start.toString());
        map.put(2, end.toString());
        return dao.getReport(QueryReportTexts.reportByPeriod, map);
    }

    public JSONArray getOrderByPeriodAndClient(LocalDate start, LocalDate end, User user) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, start.toString());
        map.put(2, end.toString());
        map.put(3, user.getPhone());
        return dao.getReport(QueryReportTexts.reportByPeriod, map);
    }
}
