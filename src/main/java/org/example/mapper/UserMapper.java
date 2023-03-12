package org.example.mapper;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.models.User;
import org.example.models.User.UserRole;
import org.example.util.LocalDateConverter;

public class UserMapper {

  public User mapResultToUser(ResultSet result) throws SQLException {
    return User.builder().firstName(result.getString("first_name"))
        .secondName(result.getString("last_name"))
        .email(result.getString("email"))
        .phone(result.getString("phone"))
        .role(UserRole.getRole(result.getString("user_role")))
        .birthDate(LocalDateConverter.convertToEntityAttribute(result.getDate("birthday")))
        .id(result.getLong("user_id"))
        .build();
  }

  public void mapUserToPreparedStatement(User model, PreparedStatement statement)
      throws SQLException {
    statement.setString(1, model.getFirstName());
    statement.setString(2, model.getSecondName());
    statement.setString(3, model.getPhone());
    statement.setString(4, String.valueOf(model.getRole()));
    statement.setString(5, model.getEmail());
    statement.setDate(6, Date.valueOf(model.getBirthDate()));
  }

}
