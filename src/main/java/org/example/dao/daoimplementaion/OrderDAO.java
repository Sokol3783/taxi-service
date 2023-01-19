package org.example.dao.daoimplementaion;

public class OrderDAO {
/*
    private static final Logger log = LoggerFactory.getLogger(OrderDAO.class);
    private static final String CREATE = "INSERT INTO orders(cars_numbers,client_id,address_departure,destination,cost,percent_discount,distance, create_date) VALUES(?, (SELECT user_id FROM users WHERE phone=?), ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE orders SET (cars_numbers=?,client_id=?,address_departure=?,destination=?,cost=?,percent_discount=?,order_number=?) WHERE order_id=?";
    private static final String DELETE = "DELETE FROM orders WHERE id=?";
    private static final String SELECT_ALL = "SELECT * FROM orders";
    private static final String SELECT = SELECT_ALL + " WHERE order_id=?";

    @Override
    public Order create(Order model) throws SQLException {
        con.setAutoCommit(false);
        try (PreparedStatement statement = con.prepareStatement(CREATE)) {
            statement.setArray(1, getCarNumbers(model.getCars(), con));
            statement.setString(2, model.getClient().getPhone());
            statement.setString(3, model.getAddressDeparture());
            statement.setString(4, model.getDestination());
            statement.setLong(5, model.getCost());
            statement.setInt(6, model.getPercentDiscount());
            statement.setLong(7, model.getDistance());
            statement.setDate(8, LocalDateConverter.convertToDatabaseColumn(model.getCreateAt()));
            if (statement.executeUpdate() > 0) {
                con.commit();
                return model;
            }
        } catch (SQLException e) {
            DAOUtil.rollbackCommit(con, log);
            log.error(ORDER_NOT_CREATE, e);
            throw new DAOException(ORDER_NOT_CREATE);
        } finally {
            DAOUtil.connectionClose(con, log);
        }
        return Order.builder().build();
    }

    private Array getCarNumbers(List<Car> cars) throws SQLException {
        if (cars.size() == 0) {
            log.error("There are no checked car");
            throw new IllegalArgumentException();
        }
        String[] numbers = new String[cars.size()];
        for (int i = 0; i < cars.size(); i++) {
            numbers[i] = cars.get(i).getNumber();
        }
        return con.createArrayOf("VARCHAR", numbers);
    }

    @Override
    public void update(Order model) {
        DAOUtil.connectionClose(con, log);
        throw new UnsupportedOperationException();
    }

    @Override
    public Order get(int id) {
        try (PreparedStatement statement = con.prepareStatement(SELECT)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return buildOrder(result, con);
            }
        } catch (SQLException e) {
            log.error(ORDER_NOT_FOUND, e);
            throw new DAOException(ORDER_NOT_FOUND);
        }
        return Order.builder().build();
    }

    @Override
    public List<Order> getAll(Connection con) {
        List<Order> models = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement(SELECT_ALL)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                models.add(buildOrder(result, con));
            }
        } catch (SQLException e) {
            log.error(USER_NOT_FOUND, e);
            throw new DAOException(USER_NOT_FOUND);
        }
        return models;
    }

    private Order buildOrder(ResultSet result) throws SQLException {
        return Order.builder().cars(getCars(result.getArray("cars_numbers"), con))
                .client(getClient(result.getInt("client_id"), con))
                .cost(result.getLong("cost"))
                .addressDeparture(result.getString("address_departure"))
                .destination(result.getString("destination"))
                .percentDiscount(result.getInt("percent_discount"))
                .distance(result.getLong("distance"))
                .orderNumber(result.getLong("order_number"))
                .createAt(LocalDateConverter.convertToEntityAttributeTime(result.getDate("create_date"))).build();
    }

    private User getClient(int client_id) {
        UserDAO dao = new UserDAO();
        return dao.get(client_id, con);
    }

    private List<Car> getCars(Array car_numbers) throws SQLException {
        String[] carNumbers = (String[]) car_numbers.getArray();
        List<Car> cars = new ArrayList<>();
        CarDAO dao = new CarDAO();
        for (String carNumber : carNumbers) {
            cars.add(dao.get(carNumber, con));
        }
        return cars;
    }

    @Override
    public void delete(int id) {
        callQuery(id, con, DELETE, log);
    }

    static void callQuery(int id, String delete, Logger log) {
        try (PreparedStatement statement = con.prepareStatement(delete)) {
            statement.setInt(1, id);
            int i = statement.executeUpdate();
            if (i <= 0) {
                log.error(USER_NOT_DELETE);
                throw new DAOException(USER_NOT_DELETE);
            }
        } catch (SQLException e) {
            log.error(USER_NOT_FOUND, e);
            throw new DAOException(USER_NOT_FOUND);
        } finally {
            DAOUtil.connectionClose(con, log);
        }
    }

 */
}
