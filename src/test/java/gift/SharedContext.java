package gift;

import gift.model.Category;
import gift.model.Member;
import gift.model.Option;
import io.cucumber.spring.ScenarioScope;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ScenarioScope
public class SharedContext {

    private ExtractableResponse<Response> response;
    private final Map<String, Category> categories = new HashMap<>();
    private final Map<String, Member> members = new HashMap<>();
    private final Map<String, Option> options = new HashMap<>();

    public ExtractableResponse<Response> getResponse() {
        return response;
    }

    public void setResponse(ExtractableResponse<Response> response) {
        this.response = response;
    }

    public void putCategory(String name, Category category) {
        categories.put(name, category);
    }

    public Category getCategory(String name) {
        return categories.get(name);
    }

    public void putMember(String name, Member member) {
        members.put(name, member);
    }

    public Member getMember(String name) {
        return members.get(name);
    }

    public void putOption(String name, Option option) {
        options.put(name, option);
    }

    public Option getOption(String name) {
        return options.get(name);
    }
}
