package gift.cucumber;

import gift.model.CategoryRepository;
import gift.model.MemberRepository;
import gift.model.OptionRepository;
import gift.model.ProductRepository;
import gift.model.WishRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonStepDefinitions {

    @Autowired
    WishRepository wishRepository;

    @Autowired
    OptionRepository optionRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MemberRepository memberRepository;

    @Before
    public void setUp() {
        wishRepository.deleteAll();
        optionRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        memberRepository.deleteAll();
        SharedContext.clear();
    }

    @Then("응답 상태코드는 {int}이다")
    public void 응답_상태코드_확인(int statusCode) {
        assertThat(SharedContext.getResponse().statusCode()).isEqualTo(statusCode);
    }

    @And("응답의 {string}은 {string}이다")
    public void 응답_문자열_필드_확인(String field, String expected) {
        assertThat(SharedContext.getResponse().jsonPath().getString(field)).isEqualTo(expected);
    }

    @And("응답의 정수 {string}는 {int}이다")
    public void 응답_정수_필드_확인(String field, int expected) {
        assertThat(SharedContext.getResponse().jsonPath().getInt(field)).isEqualTo(expected);
    }

    @And("응답 목록에 {string}이 {string}, {string}을 포함한다")
    public void 응답_목록_포함_확인(String field, String value1, String value2) {
        assertThat(SharedContext.getResponse().jsonPath().getList(field)).contains(value1, value2);
    }

    @And("응답 목록이 비어있다")
    public void 응답_목록_비어있음() {
        assertThat(SharedContext.getResponse().jsonPath().getList("$")).isEmpty();
    }
}
