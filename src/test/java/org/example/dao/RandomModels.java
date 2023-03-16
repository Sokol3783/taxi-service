package org.example.dao;

import java.time.LocalDate;
import java.util.Random;
import org.example.models.Car;
import org.example.models.Car.CarCategory;
import org.example.models.User;

public class RandomModels {

  private static final Random RANDOM = new Random();
  private static final String[] UKRAINE_CODES = {"50", "63", "66", "67", "68", "73", "91", "92",
      "93", "94", "95", "96", "97", "98", "99"};
  private static final String[] DOMAINS = {"gmail.com", "yahoo.com", "hotmail.com", "ukr.net",
      "i.ua", "bigmir.net", "meta.ua"};

  public static User generateUser() {
    String firstName = generateRandomString(6);
    String secondName = generateRandomString(8);
    String phone = "+380" + generateRandomNumericString(9);
    String email = firstName + "." + secondName + "@example.com";
    String password = generateRandomString(8);
    User.UserRole role = User.UserRole.values()[RANDOM.nextInt(User.UserRole.values().length)];
    LocalDate birthDate = LocalDate.of(RANDOM.nextInt(60) + 1960, RANDOM.nextInt(12) + 1,
        RANDOM.nextInt(28) + 1);

    return User.builder()
        .firstName(firstName)
        .secondName(secondName)
        .phone(phone)
        .email(email)
        .password(password)
        .role(role)
        .birthDate(birthDate)
        .build();
  }

  public static Car generateCar() {
    User driver = generateUser();
    String number = generateRandomString(3) + " " + generateRandomNumericString(4);
    CarCategory category = CarCategory.values()[RANDOM.nextInt(CarCategory.values().length)];
    int capacity = RANDOM.nextInt(8) + 1;
    String carName = generateRandomString(10);

    return Car.builder()
        .driver(driver)
        .number(number)
        .category(category)
        .capacity(capacity)
        .carName(carName)
        .build();
  }

  public static User[] generateUsers(int count) {
    User[] users = new User[count];
    for (int i = 0; i < count; i++) {
      users[i] = generateUser();
    }
    return users;
  }

  public static Car[] generateCars(int count) {
    Car[] cars = new Car[count];
    for (int i = 0; i < count; i++) {
      cars[i] = generateCar();
    }
    return cars;
  }

  private static String generateRandomString(int length) {
    final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length; i++) {
      int index = RANDOM.nextInt(ALPHA_NUMERIC_STRING.length());
      builder.append(ALPHA_NUMERIC_STRING.charAt(index));
    }
    return builder.toString();
  }

  private static String generateRandomNumericString(int length) {
    final String NUMERIC_STRING = "0123456789";
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < length; i++) {
      int index = RANDOM.nextInt(NUMERIC_STRING.length());
      builder.append(NUMERIC_STRING.charAt(index));
    }
    return builder.toString();
  }

  public static String generatePhoneNumber() {
    Random random = new Random();
    String code = UKRAINE_CODES[random.nextInt(UKRAINE_CODES.length)];
    String number = String.format("%07d", random.nextInt(10_000_000));
    return "+38" + code + number;
  }

  public static String generateEmail() {
    Random random = new Random();
    String name = "user" + random.nextInt(1000);
    String domain = DOMAINS[random.nextInt(DOMAINS.length)];
    return name + "@" + domain;
  }
}
