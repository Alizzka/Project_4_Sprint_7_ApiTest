package CourierTest;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CourierHelper {

    // Метод для получения ID курьера по логину и паролю
    @Step("get courier Id")
    public int getCourierId(String login, String password) {
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body("{ \"login\": \"" + login + "\", \"password\": \"" + password + "\" }")
                .when()
                .post("/api/v1/courier/login");

        if (response.getStatusCode() == 200) {
            return response.jsonPath().getInt("id");
        } else {
            return -1; // Если авторизация не удалась
        }
    }

    // Метод для удаления курьера по его ID
    @Step("delete courier")
    public void deleteCourier(int courierId) {
        RestAssured.given()
                .header("Content-Type", "application/json")
                .when()
                .delete("/api/v1/courier/" + courierId)
                .then()
                .statusCode(200); // Ожидаем успешное удаление курьера
        System.out.println("Курьер с ID " + courierId + " был удален.");
    }

    // Метод для вывода кода ответа и тела ответа
    @Step("print response")
    public void printResponse(Response response, Gson gson) {
        String responseBody = response.getBody().asString();

        // Форматируем JSON для вывода
        String formattedJson = gson.toJson(gson.fromJson(responseBody, Object.class));

        // Выводим код ответа и форматированное тело ответа
        System.out.println("Код ответа: " + response.getStatusCode());
        System.out.println("Тело ответа: " + formattedJson);
    }
}

