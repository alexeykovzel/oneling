import com.alexeykovzel.dictionary.api.GoogleTranslateAPI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoogleTranslateAPITest {
    private GoogleTranslateAPI dictionary;

    @BeforeEach
    public void setUp() {
        dictionary = new GoogleTranslateAPI();
    }

    @Test
    public void translateWord() {
        List<String> translations = dictionary.translate("word", "ru");
        assertEquals(1, translations.size());
        assertEquals("слово", translations.get(0));
    }

    @Test
    public void translatePhrase() {
        List<String> translations = dictionary.translate("Hello, World!", "ru");
        assertEquals(1, translations.size());
        assertEquals("Привет, мир!", translations.get(0));
    }
}