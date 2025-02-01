package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.Config;

public class ApiClient {
    public static String getAuthToken(){
        RestAssured.baseURI= Config.BASE_URL;
        String requestBody="{ \"email\": \"" + Config.EMAIL + "\", \"password\": \"" + Config.PASSWORD + "\" }";
        Response response=RestAssured.given()
                .header("Content-Type","application/json")
                .body(requestBody)
                .when()
                .post("/users/login")
                .then()
                .extract()
                .response();
        return response.jsonPath().getString("token");
    }

    public static Response addContact(String firstName, String lastName, String email, String phone, String token) {
        String requestBody = "{ \"firstName\": \"" + firstName + "\", \"lastName\": \"" + lastName + "\", \"email\": \"" + email + "\", \"phone\": \"" + phone + "\" }";

        return RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
                .when()
                .post("/contacts");
    }
}
