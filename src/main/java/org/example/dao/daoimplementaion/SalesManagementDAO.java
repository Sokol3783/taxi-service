package org.example.dao.daoimplementaion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.dao.daoutil.DAOUtil;
import org.example.exceptions.DAOException;
import org.example.models.Car.CarCategory;
import org.example.models.Discount;
import org.example.models.User;
import org.example.models.User.UserRole;
import org.example.util.LocalDateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SalesManagementDAO {

  private static final Logger log = LoggerFactory.getLogger(SalesManagementDAO.class);
  private static final String GET_PRICE_BY_CATEGORY = "SELECT TOP current_price FROM price WHERE car_category=? ORDER BY date ";
  private static final String GET_PRICES = "SELECT * FROM price JOIN (SELECT price_id, max(date_update) maxDate FROM price GROUP BY price_id, car_category) b    ON price.price_id = b.price_id AND price.date_update = b.maxDate";
  private static final String CREATE_PRICE_BY_CATEGORY = "INSERT INTO price (current_price=?, car_category=?";
  private static final String GET_DISCOUNTS = "SELECT * FROM discounts LEFT JOIN users u on u.user_id = discounts.owner_discount";
  private static final String GET_DISCOUNT_BY_USER = "SELECT * FROM discounts LEFT JOIN users u on u.user_id = discounts.owner_discount WHERE u.phone=?";


  public int getPriceByCategory(CarCategory category, Connection con) {
    try (PreparedStatement statement = con.prepareStatement(GET_PRICE_BY_CATEGORY)) {
      statement.setString(1, category.toString());
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return resultSet.getInt("current_price");
      }
    } catch (SQLException e) {
      log.error(DAOException.PRICE_NOT_FOUND, e);
      throw new DAOException(e);
    } finally {
      DAOUtil.connectionClose(con, log);
    }
    return 0;
  }

  public Map<CarCategory, Integer> getPrices(Connection con) {
    Map<CarCategory, Integer> prices = new HashMap();
    try (PreparedStatement statement = con.prepareStatement(GET_PRICES)) {
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        prices.put(CarCategory.getCategory(resultSet.getString("car_category")),
            resultSet.getInt("current_price"));
      }
    } catch (SQLException e) {
      for (CarCategory category : CarCategory.values()) {
        prices.put(category, 1);
      }
    } finally {
      DAOUtil.connectionClose(con, log);
    }
    return prices;
  }

  public Discount getDiscountByUser(User model, Connection con) {
    Discount.DiscountBuilder builder = getDefaultDiscount(model);
    try (PreparedStatement statement = con.prepareStatement(GET_DISCOUNT_BY_USER)) {
      statement.setString(1, model.getPhone());
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        builder.amountSpent(resultSet.getInt("amount_spent"))
            .percent(resultSet.getInt("percent_discount"));
      }
    } catch (SQLException e) {
      log.error(DAOException.DISCOUNT_NOT_FOUND, e);
    } finally {
      DAOUtil.connectionClose(con, log);
    }
    return builder.build();
  }

  //TODO
  private Discount.DiscountBuilder getDefaultDiscount(User model) {
    return Discount.builder().user(model).
        amountSpent(0).
        percent(0);
  }

  public List<Discount> getDiscounts(Connection con) {
    List<Discount> discounts = new ArrayList<>();
    try (PreparedStatement statement = con.prepareStatement(GET_DISCOUNTS)) {
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        Discount.DiscountBuilder builder = getDefaultDiscount(buildUser(resultSet));
        builder.amountSpent(resultSet.getInt("amount_spent"))
            .percent(resultSet.getInt("percent_discount"));
        discounts.add(builder.build());
      }
    } catch (SQLException e) {
      log.error(DAOException.DISCOUNT_NOT_FOUND, e);
    } finally {
      DAOUtil.connectionClose(con, log);
    }
    return discounts;
  }

  public void updatePriceByCategory(int price, CarCategory category, Connection con) {
    try (PreparedStatement statement = con.prepareStatement(CREATE_PRICE_BY_CATEGORY)) {
      statement.setInt(1, price);
      statement.setString(2, category.name());
    } catch (SQLException e) {
      log.error(e.getMessage());
    } finally {
      DAOUtil.connectionClose(con, log);
    }

  }

  //TODO
  private User buildUser(ResultSet resultSet) throws SQLException {
    User user = User.builder().firstName(resultSet.getString("first_name"))
        .secondName(resultSet.getString("last_name"))
        .email(resultSet.getString("email"))
        .phone(resultSet.getString("phone"))
        .role(UserRole.getRole(resultSet.getString("user_role")))
        .birthDate(LocalDateConverter.convertToEntityAttribute(resultSet.getDate("birthday")))
        .build();
    return user;
  }
}
