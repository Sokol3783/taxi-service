package org.example.dao.daoimplementaion;

public class QueryReportDAO {
    public final static String reportByPeriod = "SELECT orders.order_number,orders.cost,orders.percent_discount,orders.distance,format('%s %s', u.first_name, u.last_name) as full_name,orders.client_id as user_id FROM orders Left Outer Join users u on u.user_id = orders.client_id WHERE orders.create_date between TO_TIMESTAMP(?, 'YYYY/MM/DD HH:MI:SS')  and TO_TIMESTAMP(?, 'YYYY/MM/DD HH:MI:SS')";
    public final static String reportByPeriodAndClient = reportByPeriod + " AND user_id = ?";
}
