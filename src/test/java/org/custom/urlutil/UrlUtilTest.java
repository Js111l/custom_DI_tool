package org.custom.urlutil;

import static org.custom.web.util.UrlUtil.getPathVariableNameValueMapFromUrl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.custom.web.util.UrlUtil;
import org.junit.jupiter.api.Test;

import org.assertj.core.api.Assertions;

public class UrlUtilTest {

  @Test
  public void testMatchingUrlWithComplexPattern1() {
    Assertions.assertThat(UrlUtil.isMatching("users/123/james/abc", "users/{id}/{name}/abc"))
        .isTrue();

    Assertions.assertThat(UrlUtil.isMatching("products/name", "products/{id}"))
        .isTrue();
  }

  @Test
  public void testMatchingUrlWithComplexPattern2() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "articles/2023/09/04/my-article", "articles/{year}/{month}/{day}/my-article"))
        .isTrue();
  }

  @Test
  public void testMatchingUrlWithComplexPattern3() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "category/products/product123/details", "category/products/{productId}/details"))
        .isTrue();
  }

  @Test
  public void testMatchingUrlWithComplexPattern4() {
    Assertions.assertThat(
            UrlUtil.isMatching("users/123/posts/456", "users/{userId}/posts/{postId}"))
        .isTrue();
  }

  @Test
  public void testMatchingUrlWithComplexPattern5() {
    Assertions.assertThat(
            UrlUtil.isMatching("blog/posts/2023/09/04", "blog/posts/{year}/{month}/{day}"))
        .isTrue();
  }

  @Test
  public void testMatchingUrlWithComplexPattern6() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "articles/2023/09/04/my-article", "articles/{year}/{month}/{day}/{slug}"))
        .isTrue();
  }

  @Test
  public void testMatchingUrlWithComplexPattern7() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "users/123/posts/456/comments/789",
                "users/{userId}/posts/{postId}/comments/{commentId}"))
        .isTrue();
  }

  @Test
  public void testMatchingUrlWithComplexPattern8() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "categories/c1/c2/c3/c4/c5", "categories/{cat1}/{cat2}/{cat3}/{cat4}/{cat5}"))
        .isTrue();
  }

  @Test
  public void testMatchingUrlWithComplexPattern9() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "store/products/123/details?color=blue",
                "store/products/{productId}/details?color={color}"))
        .isTrue();
  }

  @Test
  public void testMatchingUrlWithComplexPattern10() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "app/users/123/settings/notifications",
                "app/users/{userId}/settings/notifications"))
        .isTrue();
  }

  @Test
  public void testMatchingUrlWithComplexPattern11() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "articles/2023/09/04/my-article", "articles/{year}/{month}/{day}/{slug}"))
        .isTrue();
  }

  @Test
  public void testMatchingUrlWithComplexPattern12() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "users/123/posts/456/comments/789/likes/123",
                "users/{userId}/posts/{postId}/comments/{commentId}/likes/{likeId}"))
        .isTrue();
  }

  @Test
  public void testMatchingUrlWithComplexPattern13() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "categories/c1/c2/c3/c4/c5/c6",
                "categories/{cat1}/{cat2}/{cat3}/{cat4}/{cat5}/{cat6}"))
        .isTrue();
  }

  @Test
  public void testMatchingUrlWithComplexPattern14() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "store/products/123/details?color=blue&size=large",
                "store/products/{productId}/details?color={color}&size={size}"))
        .isTrue();
  }

  @Test
  public void testMatchingUrlWithComplexPattern15() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "app/users/123/settings/notifications/mode/silent",
                "app/users/{userId}/settings/notifications/mode/{mode}"))
        .isTrue();
  }

  @Test
  public void testMatchingUrlWithComplexPattern16() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "users/123/posts/12999999999999999999999999999999999999/comments/789/likes/123111111111111111111111111111111111",
                "users/{userId}/posts/{postId}/comments/{commentId}/likes/{likeId}"))
        .isTrue();
  }

  @Test
  public void testMatchingUrlWithComplexPattern17() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "products/category/electronics/subcategory/smartphones/item/123",
                "products/category/{cat1}/subcategory/{cat2}/item/{itemId}"))
        .isTrue();
  }

  @Test
  public void testMatchingUrlWithComplexPattern18() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "company/departments/hr/employees/123/profile",
                "company/departments/{dept}/employees/{empId}/profile"))
        .isTrue();
  }

  @Test
  public void testMatchingUrlWithComplexPattern19() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "store/products/123/details?color=blue&size=large&discount=10",
                "store/products/{productId}/details?color={color}&size={size}&discount={discount}"))
        .isTrue();
  }

  @Test
  public void testMatchingUrlWithComplexPattern20() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "app/users/123/settings/notifications/mode/silent/language/en",
                "app/users/{userId}/settings/notifications/mode/{mode}/language/{lang}"))
        .isTrue();
  }

  @Test
  public void testMatchingUrlWithNegativePattern1() {
    Assertions.assertThat(UrlUtil.isMatching("users/abc/james", "users/{id}/{name}/abc")).isFalse();
  }

  @Test
  public void testMatchingUrlWithNegativePattern2() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "articles/2023/09/04/my-article", "articles/{year}/{month}/my-article"))
        .isFalse();
  }

  @Test
  public void testMatchingUrlWithNegativePattern3() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "category/prodcts/product123/detail",
                "category/{categoryName}/{productId}/details"))
        .isFalse();
  }

  @Test
  public void testMatchingUrlWithNegativePattern4() {
    Assertions.assertThat(
            UrlUtil.isMatching("users/123/posts/456", "users/{userId}/posts/{postId}/{extra}"))
        .isFalse();
  }

  @Test
  public void testMatchingUrlWithNegativePattern5() {
    Assertions.assertThat(UrlUtil.isMatching("blog/posts/2023/09/04", "blog/posts/{year}/{month}"))
        .isFalse();
  }

  @Test
  public void testMatchingUrlWithNegativePattern6() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "articles/2023/09/my-article", "articles/{year}/{month}/{day}/{slug}"))
        .isFalse();
  }

  @Test
  public void testMatchingUrlWithNegativePattern7() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "users/123/posts/456/comments/789",
                "users/{userId}/posts/{postId}/comments/{commentId}/likes/{likeId}"))
        .isFalse();
  }

  @Test
  public void testMatchingUrlWithNegativePattern8() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "categories/c1/c2/c3/c4/c5",
                "categories/{cat1}/{cat2}/{cat3}/{cat4}/{cat5}/{cat6}"))
        .isFalse();
  }

  @Test
  public void testMatchingUrlWithNegativePattern9() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "store/products/123/details?size=large",
                "store/products/{productId}/details?color={color}"))
        .isFalse();
  }

  @Test
  public void testMatchingUrlWithNegativePattern10() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "app/users/123/settings/preferences",
                "app/users/{userId}/settings/notifications/mode/{mode}"))
        .isFalse();
  }

  @Test
  public void testMatchingUrlWithNegativePattern11() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "articles/2023/09/04/my-article", "articles/{year}/{month}/{day}/{slug}/{extra}"))
        .isFalse();
  }

  @Test
  public void testMatchingUrlWithNegativePattern12() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "users/123/posts/comments/789/likes",
                "users/{userId}/posts/{postId}/comments/{commentId}/likes/{likeId}"))
        .isFalse();
  }

  @Test
  public void testMatchingUrlWithNegativePattern13() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "products/category/electronics/subcategory/smartphones",
                "products/category/{cat1}/subcategory/{cat2}/item/{itemId}"))
        .isFalse();
  }

  @Test
  public void testMatchingUrlWithNegativePattern14() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "company/departments/hr/employees/123",
                "company/departments/{dept}/employees/{empId}/profile/{extra}"))
        .isFalse();
  }

  @Test
  public void testMatchingUrlWithNegativePattern15() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "store/products/123/details?size=large&discount=10",
                "store/products/{productId}/details?color={color}&size={size}"))
        .isFalse();
  }

  @Test
  public void testMatchingUrlWithNegativePattern16() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "app/users/123/settings/preferences",
                "app/users/{userId}/settings/notifications/mode/{mode}/language/{lang}"))
        .isFalse();
  }

  @Test
  public void testMatchingUrlWithNegativePattern17() {
    Assertions.assertThat(UrlUtil.isMatching("invalid/url", "users/{userId}/posts/{postId}"))
        .isFalse();
  }

  @Test
  public void testMatchingUrlWithNegativePattern18() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "users/123/posts/456/comments",
                "users/{userId}/posts/{postId}/comments/{commentId}"))
        .isFalse();
  }

  @Test
  public void testMatchingUrlWithNegativePattern19() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "blog/posts/2023/09/04/extra-segment", "blog/posts/{year}/{month}/{day}"))
        .isFalse();
  }

  @Test
  public void testMatchingUrlWithNegativePattern20() {
    Assertions.assertThat(
            UrlUtil.isMatching(
                "articles/2023/09/04/my-article/extra-segment",
                "articles/{year}/{month}/{day}/{slug}"))
        .isFalse();
  }

  @Test
  public void testGetPathVarValuesFromUrl1() {
    String url = "app/users/123/settings/notifications";
    String pattern = "app/users/{userId}/settings/notifications";

    Map<String, String> result = getPathVariableNameValueMapFromUrl(url, pattern);

    assertEquals("123", result.get("userId"));
  }

  @Test
  public void testGetPathVarValuesFromUrl2() {
    String url = "articles/2023/09/04/my-article";
    String pattern = "articles/{year}/{month}/{day}/{slug}";

    Map<String, String> result = getPathVariableNameValueMapFromUrl(url, pattern);

    assertEquals("2023", result.get("year"));
    assertEquals("09", result.get("month"));
    assertEquals("04", result.get("day"));
    assertEquals("my-article", result.get("slug"));
  }

  @Test
  public void testGetPathVarValuesFromUrl3() {
    String url = "users/123/posts/456/comments/789/likes/123";
    String pattern = "users/{userId}/posts/{postId}/comments/{commentId}/likes/{likeId}";

    Map<String, String> result = getPathVariableNameValueMapFromUrl(url, pattern);

    assertEquals("123", result.get("userId"));
    assertEquals("456", result.get("postId"));
    assertEquals("789", result.get("commentId"));
    assertEquals("123", result.get("likeId"));
  }

  @Test
  public void testGetPathVarValuesFromUrl4() {
    String url = "categories/c1/c2/c3/c4/c5/c6";
    String pattern = "categories/{cat1}/{cat2}/{cat3}/{cat4}/{cat5}/{cat6}";

    Map<String, String> result = getPathVariableNameValueMapFromUrl(url, pattern);

    assertEquals("c1", result.get("cat1"));
    assertEquals("c2", result.get("cat2"));
    assertEquals("c3", result.get("cat3"));
    assertEquals("c4", result.get("cat4"));
    assertEquals("c5", result.get("cat5"));
    assertEquals("c6", result.get("cat6"));
  }

  @Test
  public void testGetPathVarValuesFromUrl5() {
    String url = "store/products/123/details?color=blue&size=large";
    String pattern = "store/products/{productId}/details?color={color}&size={size}";

    Map<String, String> result = getPathVariableNameValueMapFromUrl(url, pattern);

    assertEquals("123", result.get("productId"));
    assertEquals("blue", result.get("color"));
    assertEquals("large", result.get("size"));
  }

  @Test
  public void testGetPathVarValuesFromUrl6() {
    String url = "app/users/123/settings/notifications/mode/silent";
    String pattern = "app/users/{userId}/settings/notifications/mode/{mode}";

    Map<String, String> result = getPathVariableNameValueMapFromUrl(url, pattern);

    assertEquals("123", result.get("userId"));
    assertEquals("silent", result.get("mode"));
  }

  @Test
  public void testGetPathVarValuesFromUrl7() {
    String url = "example/a/b/c/d/e/f/g/h/i/j";
    String pattern =
        "example/{var1}/{var2}/{var3}/{var4}/{var5}/{var6}/{var7}/{var8}/{var9}/{var10}";

    Map<String, String> result = getPathVariableNameValueMapFromUrl(url, pattern);

    assertEquals("a", result.get("var1"));
    assertEquals("b", result.get("var2"));
    assertEquals("c", result.get("var3"));
    assertEquals("d", result.get("var4"));
    assertEquals("e", result.get("var5"));
    assertEquals("f", result.get("var6"));
    assertEquals("g", result.get("var7"));
    assertEquals("h", result.get("var8"));
    assertEquals("i", result.get("var9"));
    assertEquals("j", result.get("var10"));
  }

  @Test
  public void testGetPathVarValuesFromUrl8() {
    String url = "example/one/two/three/four/five/six/seven/eight/nine/ten";
    String pattern =
        "example/{var1}/{var2}/{var3}/{var4}/{var5}/{var6}/{var7}/{var8}/{var9}/{var10}";

    Map<String, String> result = getPathVariableNameValueMapFromUrl(url, pattern);

    assertEquals("one", result.get("var1"));
    assertEquals("two", result.get("var2"));
    assertEquals("three", result.get("var3"));
    assertEquals("four", result.get("var4"));
    assertEquals("five", result.get("var5"));
    assertEquals("six", result.get("var6"));
    assertEquals("seven", result.get("var7"));
    assertEquals("eight", result.get("var8"));
    assertEquals("nine", result.get("var9"));
    assertEquals("ten", result.get("var10"));
  }

  @Test
  public void testGetPathVarValuesFromUrl9() {
    String url = "app/users/123/settings/notifications";
    String pattern = "app/users/{userId}/settings/notifications";

    Map<String, String> result = getPathVariableNameValueMapFromUrl(url, pattern);

    assertEquals("123", result.get("userId"));
  }

  @Test
  public void testGetPathVarValuesFromUrl10() {
    String url = "articles/2023/09/04/my-article";
    String pattern = "articles/{year}/{month}/{day}/{slug}";

    Map<String, String> result = getPathVariableNameValueMapFromUrl(url, pattern);

    assertEquals("2023", result.get("year"));
    assertEquals("09", result.get("month"));
    assertEquals("04", result.get("day"));
    assertEquals("my-article", result.get("slug"));
  }

  @Test
  public void testGetPathVarValuesFromUrl11() {
    String url = "users/123/posts/456/comments/789/likes/123";
    String pattern = "users/{userId}/posts/{postId}/comments/{commentId}/likes/{likeId}";

    Map<String, String> result = getPathVariableNameValueMapFromUrl(url, pattern);

    assertEquals("123", result.get("userId"));
    assertEquals("456", result.get("postId"));
    assertEquals("789", result.get("commentId"));
    assertEquals("123", result.get("likeId"));
  }

  @Test
  public void testGetPathVarValuesFromUrl12() {
    String url = "categories/c1/c2/c3/c4/c5/c6";
    String pattern = "categories/{cat1}/{cat2}/{cat3}/{cat4}/{cat5}/{cat6}";

    Map<String, String> result = getPathVariableNameValueMapFromUrl(url, pattern);

    assertEquals("c1", result.get("cat1"));
    assertEquals("c2", result.get("cat2"));
    assertEquals("c3", result.get("cat3"));
    assertEquals("c4", result.get("cat4"));
    assertEquals("c5", result.get("cat5"));
    assertEquals("c6", result.get("cat6"));
  }

  @Test
  public void testGetPathVarValuesFromUrl13() {
    String url = "store/products/123/details?color=blue&size=large";
    String pattern = "store/products/{productId}/details?color={color}&size={size}";

    Map<String, String> result = getPathVariableNameValueMapFromUrl(url, pattern);

    assertEquals("123", result.get("productId"));
    assertEquals("blue", result.get("color"));
    assertEquals("large", result.get("size"));
  }

  @Test
  public void testGetPathVarValuesFromUrl14() {
    String url = "app/users/123/settings/notifications/mode/silent";
    String pattern = "app/users/{userId}/settings/notifications/mode/{mode}";

    Map<String, String> result = getPathVariableNameValueMapFromUrl(url, pattern);

    assertEquals("123", result.get("userId"));
    assertEquals("silent", result.get("mode"));
  }

  @Test
  public void testGetPathVarValuesFromUrlNegative1() {
    String url = "app/settings/notifications";
    String pattern = "app/users/{userId}/settings/notifications";

    assertTrue(getPathVariableNameValueMapFromUrl(url, pattern).isEmpty());
  }

  @Test
  public void testGetPathVarValuesFromUrlNegative2() {
    String url = "articles/2023/my-article";
    String pattern = "articles/{year}/{month}/{day}/{slug}";

    assertTrue(getPathVariableNameValueMapFromUrl(url, pattern).isEmpty());
  }

  @Test
  public void testGetPathVarValuesFromUrlNegative3() {
    String url = "users/123/comments/789/likes/123";
    String pattern = "users/{userId}/posts/{postId}/comments/{commentId}/likes/{likeId}";

    assertTrue(getPathVariableNameValueMapFromUrl(url, pattern).isEmpty());
  }

  @Test
  public void testGetPathVarValuesFromUrlNegative4() {
    String url = "categories/c1/c2/c3/c4";
    String pattern = "categories/{cat1}/{cat2}/{cat3}/{cat4}/{cat5}/{cat6}";

    assertTrue(getPathVariableNameValueMapFromUrl(url, pattern).isEmpty());
  }

  @Test
  public void testGetPathVarValuesFromUrlNegative5() {
    String url = "store/products/123/details?color=blue";
    String pattern = "store/products/{productId}/details?color={color}&size={size}";

    assertTrue(getPathVariableNameValueMapFromUrl(url, pattern).isEmpty());
  }

  @Test
  public void testGetPathVarValuesFromUrlNegative6() {
    String url = "app/users/settings/notifications/mode/silent";
    String pattern = "app/users/{userId}/settings/notifications/mode/{mode}";

    assertTrue(getPathVariableNameValueMapFromUrl(url, pattern).isEmpty());
  }

  @Test
  public void testGetPathVarValuesFromUrlNegative7() {
    String url = "example/a/b/c/d/e/f/g/h/i";
    String pattern =
        "example/{var1}/{var2}/{var3}/{var4}/{var5}/{var6}/{var7}/{var8}/{var9}/{var10}";

    assertTrue(getPathVariableNameValueMapFromUrl(url, pattern).isEmpty());
  }

  @Test
  public void testGetPathVarValuesFromUrlNegative8() {
    String url = "example/one/two/three/four/five/six/seven/eight/nine";
    String pattern =
        "example/{var1}/{var2}/{var3}/{var4}/{var5}/{var6}/{var7}/{var8}/{var9}/{var10}";

    assertTrue(getPathVariableNameValueMapFromUrl(url, pattern).isEmpty());
  }

  @Test
  public void testGetPathVarValuesFromUrlNegative9() {
    String url = "products/abc123/details?color=red&size=medium&category=electronics";
    String pattern = "products/{productId}/details?color={color}&size={size}";

    assertTrue(UrlUtil.getPathVariableNameValueMapFromUrl(url, pattern).isEmpty());
  }

  @Test
  public void testGetPathVarValuesFromUrlNegative10() {
    String url = "users/123/posts/456";
    String pattern = "users/{userId}/posts/{postId}/comments/{commentId}";

    // Ensure that the URL does not match the pattern
    assertTrue(getPathVariableNameValueMapFromUrl(url, pattern).isEmpty());
  }
}
