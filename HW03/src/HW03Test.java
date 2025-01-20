import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

public class HW03Test {

  @Test
  @DisplayName("evaluate() method should work properly")
  void testEvaluate() {
    assertEquals(60, HW03.evaluate("(15+5)*3"));
    assertEquals(80, HW03.evaluate("(15+5)*(2+2)"));
    assertEquals(9, HW03.evaluate("(15/5)*3"));
    assertEquals(30, HW03.evaluate("(15-5)*3"));
    assertEquals(3, HW03.evaluate("(15-5)/3"));
    assertEquals(-9, HW03.evaluate("3 + 2 + 1 - 5 * 3"));
    assertEquals(-10, HW03.evaluate("3 + 2 / 1 - 5 * 3"));
    assertEquals(2, HW03.evaluate("0 + 6 / 2-1 + 3 * 0"));

  }

  @Test
  @DisplayName("isHTMLMatching() method should work properly")
  void testIsHTMLMatching() {
    String htmlCode = "<html>" +
        "<head>\n" +
        "    <title>Starter Page</title>\n" +
        "</head>\n" +
        "<body>\n" +
        "    <header>\n" +
        "        <h1>Welcome to My Web Page</h1>\n" +
        "    </header>\n" +
        "    <main>\n" +
        "        <p>This is a starter HTML page.</p>\n" +
        "    </main>\n" +
        "    <footer>\n" +
        "        <p>Copyright © 2024</p>\n" +
        "    </footer>\n" +
        "</body>\n" +
        "</html>";

    assertEquals(true, HW03.isHTMLMatching(htmlCode));
         assertEquals(false, HW03.isHTMLMatching("<meta />"));
    assertEquals(false, HW03.isHTMLMatching("<p></pa>"));
  }

  @Test
  @DisplayName("isMatching() method should work properly")
  void testIsMatching() {
    assertEquals(true, HW03.isMatching("(getClass(123, true))"));
    assertEquals(false, HW03.isMatching("())"));
    assertEquals(false, HW03.isMatching("}}{{"));
    assertEquals(true, HW03.isMatching("([{}])"));
    assertEquals(true, HW03.isMatching(""));
    assertEquals(false, HW03.isMatching("([{()()()()()()()()()()()()(})()}])"));
    assertEquals(true, HW03.isMatching(""));

    String code = "public static void main(String[] args) {\n" +
        "    /* File f = new File(\"./test.html\");\n" +
        "    try (Scanner reader = new Scanner(f)) {\n" +
        "        StringBuilder content = new StringBuilder();\n" +
        "        while (reader.hasNextLine()) {\n" +
        "            content.append(reader.nextLine()).append(System.lineSeparator());\n" +
        "        }\n" +
        "        String fileContent = content.toString();\n" +
        "        System.out.println(isHTMLMatching(fileContent));\n" +
        "    } catch (Exception e) {\n" +
        "        System.out.println(e);\n" +
        "    } */\n" +
        "\n" +
        "    //System.out.println(evaluate(\"(15+5)*3\"));\n" +
        "    //System.out.println(reverse(\"abcd\"));\n" +
        "}";
    assertEquals(true, HW03.isMatching(code));
  }

  @Test
  @DisplayName("reverse() method should work properly")
  void testReverse() {
    assertEquals("", HW03.reverse(""));
    assertEquals("dcba", HW03.reverse("abcd"));
    assertEquals("Dcba", HW03.reverse("abcD"));
    assertEquals("}{,-DCBAdcba987654321", HW03.reverse("123456789abcdABCD-,{}"));
  }
}
