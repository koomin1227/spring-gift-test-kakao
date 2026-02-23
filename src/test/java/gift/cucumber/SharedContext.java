package gift.cucumber;

import gift.model.Category;
import gift.model.Member;
import gift.model.Option;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class SharedContext {

    private static ExtractableResponse<Response> response;
    private static final Map<String, Category> categories = new HashMap<>();
    private static final Map<String, Member> members = new HashMap<>();
    private static final Map<String, Option> options = new HashMap<>();

    public static void clear() {
        response = null;
        categories.clear();
        members.clear();
        options.clear();
    }

    public static ExtractableResponse<Response> getResponse() {
        return response;
    }

    public static void setResponse(ExtractableResponse<Response> response) {
        SharedContext.response = response;
    }

    public static void putCategory(String name, Category category) {
        categories.put(name, category);
    }

    public static Category getCategory(String name) {
        return categories.get(name);
    }

    public static void putMember(String name, Member member) {
        members.put(name, member);
    }

    public static Member getMember(String name) {
        return members.get(name);
    }

    public static void putOption(String name, Option option) {
        options.put(name, option);
    }

    public static Option getOption(String name) {
        return options.get(name);
    }
}
