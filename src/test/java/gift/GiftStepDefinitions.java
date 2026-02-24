package gift;

import gift.model.Category;
import gift.model.CategoryRepository;
import gift.model.Member;
import gift.model.MemberRepository;
import gift.model.Option;
import gift.model.OptionRepository;
import gift.model.Product;
import gift.model.ProductRepository;
import io.cucumber.java.ko.그리고;
import io.cucumber.java.ko.먼저;
import io.cucumber.java.ko.만일;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Map;

public class GiftStepDefinitions {

    @LocalServerPort
    int port;

    @Autowired
    SharedContext context;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OptionRepository optionRepository;

    @먼저("회원 {string}과 {string}이 존재한다")
    public void 회원이_존재한다(String senderName, String receiverName) {
        context.putMember(senderName, memberRepository.save(new Member(senderName, senderName + "@test.com")));
        context.putMember(receiverName, memberRepository.save(new Member(receiverName, receiverName + "@test.com")));
    }

    @그리고("{string} 카테고리에 {int}원짜리 {string} 상품이 존재한다")
    public void 카테고리에_상품이_존재한다(String categoryName, int price, String productName) {
        Category category = categoryRepository.save(new Category(categoryName));
        context.putCategory(categoryName, category);
        productRepository.save(new Product(productName, price, "http://example.com/image.png", category));
    }

    @그리고("{string}에 재고 {int}개인 {string} 옵션이 존재한다")
    public void 옵션이_존재한다(String productName, int quantity, String optionName) {
        Product product = productRepository.findAll().stream()
                .filter(p -> p.getName().equals(productName))
                .findFirst()
                .orElseThrow();
        Option option = optionRepository.save(new Option(optionName, quantity, product));
        context.putOption(optionName, option);
    }

    @만일("{string}이 {string}에게 {string} 옵션 {int}개를 선물한다")
    public void 선물한다(String senderName, String receiverName, String optionName, int quantity) {
        Member sender = context.getMember(senderName);
        Member receiver = context.getMember(receiverName);
        Option option = context.getOption(optionName);
        var response = RestAssured.given().log().all()
                .port(port)
                .contentType("application/json")
                .header("Member-Id", sender.getId())
                .body(Map.of(
                        "optionId", option.getId(),
                        "quantity", quantity,
                        "receiverId", receiver.getId(),
                        "message", "선물"
                ))
                .when()
                .post("/api/gifts")
                .then().log().all()
                .extract();
        context.setResponse(response);
    }

    @만일("존재하지 않는 옵션으로 선물한다")
    public void 존재하지_않는_옵션으로_선물한다() {
        Member sender = context.getMember("보내는사람");
        Member receiver = context.getMember("받는사람");
        var response = RestAssured.given().log().all()
                .port(port)
                .contentType("application/json")
                .header("Member-Id", sender.getId())
                .body(Map.of(
                        "optionId", 999,
                        "quantity", 1,
                        "receiverId", receiver.getId(),
                        "message", "선물"
                ))
                .when()
                .post("/api/gifts")
                .then().log().all()
                .extract();
        context.setResponse(response);
    }
}
