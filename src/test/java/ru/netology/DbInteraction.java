package ru.netology;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;
import java.util.Random;

public class DbInteraction {

  private static Faker faker = new Faker(new Locale("en"));

  private DbInteraction() {
  }

  @Value
  public static class AuthInfo {
    private String login;
    private String password;
  }

  public static AuthInfo getAuthInfoFromTestData() {
    return new AuthInfo("vasya", "qwerty123");
  }

  private static String generateLogin() {
    return faker.name().username();
  }

  private static String generatePassword() {
    return faker.internet().password();
  }

  public static AuthInfo generateUser() {
    return new AuthInfo(generateLogin(), generatePassword());
  }


  @Value
  public static class VerificationCode {
    private String code;
  }

  public static VerificationCode getRandomVerificationCode() {

    return new VerificationCode(faker.numerify("######"));
  }
}