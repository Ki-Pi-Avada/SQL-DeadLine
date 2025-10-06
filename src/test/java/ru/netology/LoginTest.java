package ru.netology;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import java.sql.DriverManager;
import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class LoginTest {

    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:9999");
    }

    @Test
    void shouldLoginWithValidCodeFromDB() throws Exception {

        $("[data-test-id=login] input").setValue("vasya");
        $("[data-test-id=password] input").setValue("qwerty123");
        $("[data-test-id=action-login]").click();


        $("[data-test-id=code] input").shouldBe(visible, Duration.ofSeconds(10));


        String code = getAuthCode();
        System.out.println("Получен код из БД: " + code);


        $("[data-test-id=code] input").setValue(code);
        $("[data-test-id=action-verify]").click();


         $("h2.heading").shouldHave(text("Интернет Банк"), Duration.ofSeconds(10));


    }
    
    private String getAuthCode() throws Exception {
        var runner = new QueryRunner();
        var conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass"
        );



        Thread.sleep(2000);
        return runner.query(conn,
                "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1",
                new ScalarHandler<>()
        );
    }
}