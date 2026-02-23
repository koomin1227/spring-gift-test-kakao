package gift.cucumber;

import gift.model.Category;
import gift.model.CategoryRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Map;

public class CategoryStepDefinitions {

    @LocalServerPort
    int port;

    @Autowired
    CategoryRepository categoryRepository;

    @Given("{string} 카테고리가 존재한다")
    public void 카테고리가_존재한다(String name) {
        Category category = categoryRepository.save(new Category(name));
        SharedContext.putCategory(name, category);
    }

    @When("{string} 카테고리를 생성한다")
    public void 카테고리를_생성한다(String name) {
        var response = RestAssured.given().log().all()
                .port(port)
                .contentType(ContentType.JSON)
                .body(Map.of("name", name))
                .when()
                .post("/api/categories")
                .then().log().all()
                .extract();
        SharedContext.setResponse(response);
    }

    @When("카테고리를 전체 조회한다")
    public void 카테고리를_전체_조회한다() {
        var response = RestAssured.given().log().all()
                .port(port)
                .when()
                .get("/api/categories")
                .then().log().all()
                .extract();
        SharedContext.setResponse(response);
    }
}
