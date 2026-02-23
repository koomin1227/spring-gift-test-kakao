package gift.cucumber;

import gift.model.Category;
import gift.model.Product;
import gift.model.ProductRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Map;

public class ProductStepDefinitions {

    @LocalServerPort
    int port;

    @Autowired
    ProductRepository productRepository;

    @And("{string} 카테고리에 {string} 상품이 존재한다")
    public void 상품이_존재한다(String categoryName, String productName) {
        Category category = SharedContext.getCategory(categoryName);
        productRepository.save(new Product(productName, 4500, "http://example.com/image.png", category));
    }

    @When("{string} 카테고리에 {int}원짜리 {string} 상품을 생성한다")
    public void 상품을_생성한다(String categoryName, int price, String productName) {
        Category category = SharedContext.getCategory(categoryName);
        var response = RestAssured.given().log().all()
                .port(port)
                .contentType("application/json")
                .body(Map.of(
                        "name", productName,
                        "price", price,
                        "imageUrl", "http://example.com/image.png",
                        "categoryId", category.getId()
                ))
                .when()
                .post("/api/products")
                .then().log().all()
                .extract();
        SharedContext.setResponse(response);
    }

    @When("존재하지 않는 카테고리로 상품을 생성한다")
    public void 존재하지_않는_카테고리로_상품을_생성한다() {
        var response = RestAssured.given().log().all()
                .port(port)
                .contentType("application/json")
                .body(Map.of(
                        "name", "아메리카노",
                        "price", 4500,
                        "imageUrl", "http://example.com/image.png",
                        "categoryId", 999
                ))
                .when()
                .post("/api/products")
                .then().log().all()
                .extract();
        SharedContext.setResponse(response);
    }

    @When("상품을 전체 조회한다")
    public void 상품을_전체_조회한다() {
        var response = RestAssured.given().log().all()
                .port(port)
                .when()
                .get("/api/products")
                .then().log().all()
                .extract();
        SharedContext.setResponse(response);
    }
}
