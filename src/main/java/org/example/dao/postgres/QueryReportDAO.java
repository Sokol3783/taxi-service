package org.example.dao.postgres;

public class QueryReportDAO {
    public final static String reportByPeriod = "SELECT orders.order_number,orders.cost,orders.percent_discount,orders.distance,u.first_name + ' ' + u.last_name as full_name,orders.client_id as user_id FROM ordersLeft Outer Join users u on u.user_id = orders.client_id WHERE orders.create_date between ? and ?";
    public final static String reportByPeriodAndClient = reportByPeriod + " AND user_id = ?";
}
