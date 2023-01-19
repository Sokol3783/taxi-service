package org.example.dao.daoimplementaion;

public class CarDAOimpl {

    /*
    private static final Logger log = LoggerFactory.getLogger(CarDAO.class);
    private static final String CREATE = "INSERT INTO cars(car_number, car_name,category, capacity) VALUES(?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE cars SET (car_number=?,car_name=?,category=?,capacity=?) WHERE car_number=?";
    private static final String DELETE = "DELETE FROM users WHERE id=?";
    private static final String SELECT_ALL = "SELECT * FROM cars";
    private static final String SELECT = SELECT_ALL + " WHERE car_number=?";

    @Override
    public Car create(Car model, Connection con) throws SQLException {
        con.setAutoCommit(false);
        try (PreparedStatement statement = con.prepareStatement(CREATE)) {
            statement.setString(1, model.getNumber());
            statement.setString(2, model.getCarName());
            statement.setString(3, model.getCategory().toString());
            statement.setInt(4, model.getCapacity());
            boolean execute = statement.execute();
            con.commit();
            if (execute) {
                return model;
            }
        } catch (SQLException e) {
            DAOUtil.rollbackCommit(con, log);
            log.error(USER_NOT_CREATE, e);
            throw new DAOException(USER_NOT_CREATE);
        } finally {
            DAOUtil.connectionClose(con, log);
        }
        return Car.builder().build();
    }

    @Override
    public void update(Car model, Connection con) {
        try (PreparedStatement statement = con.prepareStatement(UPDATE)) {
            statement.setString(1, model.getNumber());
            statement.setString(2, model.getCarName());
            statement.setString(3, model.getCategory().toString());
            statement.setInt(4, model.getCapacity());
            boolean execute = statement.execute();
            con.commit();
        } catch (SQLException e) {
            DAOUtil.rollbackCommit(con, log);
            log.error(CAR_NOT_UPDATE, e);
            throw new DAOException(CAR_NOT_UPDATE);
        } finally {
            DAOUtil.connectionClose(con, log);
        }
    }

    @Override
    public Car get(int id, Connection con) {
        throw new UnsupportedOperationException();
    }

    public Car get(String number, Connection con) {
        try (PreparedStatement statement = con.prepareStatement(SELECT)) {
            statement.setString(1, number);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return Car.builder().build();
            }
        } catch (SQLException e) {
            log.error(CAR_NOT_FOUND, e);
            throw new DAOException(CAR_NOT_FOUND);
        }
        return Car.builder().build();
    }


    @Override
    public List<Car> getAll(Connection con) {
        List<Car> models = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement(SELECT_ALL)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                models.add(buildCar(result));
            }
        } catch (SQLException e) {
            log.error(CAR_NOT_FOUND, e);
            throw new DAOException(CAR_NOT_FOUND);
        }
        return models;
    }

    private Car buildCar(ResultSet result) throws SQLException {
        return Car.builder().carName(result.getString("car_name"))
                .number(result.getString("car_number")).capacity(result.getInt("capacity"))
                .category(CarCategory.getCategory(result.getString("category"))).
                build();
    }

    @Override
    public void delete(int id, Connection con) {
        throw new IllegalArgumentException();
    }

 */
}
