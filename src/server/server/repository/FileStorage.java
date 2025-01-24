package server.server.repository;

import java.io.FileReader;
import java.io.FileWriter;

import java.io.FileReader;
import java.io.FileWriter;

/**
 * Класс FileStorage реализует интерфейс Repository для работы с текстовыми данными.
 * Сохраняет и загружает текст в/из файла.
 */
public class FileStorage implements Repository<String> {

    // Константа, указывающая путь к файлу для хранения данных.
    private static final String LOG_PATH = "src/server/server/repository/history.txt";

    /**
     * Сохраняет переданный текст в файл.
     * @param text строка, которая будет сохранена.
     */
    @Override
    public void save(String text) {
        try (FileWriter writer = new FileWriter(LOG_PATH, true)) {// Если true содержимое файла не буде тперезаписано
            writer.write(text); // Запись текста в файл.
            writer.write("\n"); // Переход на новую строку.
        } catch (Exception e) {
            e.printStackTrace(); // Вывод информации об ошибке.
        }
    }

    /**
     * Загружает содержимое файла и возвращает его как строку.
     * @return строка с содержимым файла или null, если произошла ошибка.
     */
    @Override
    public String load() {
        StringBuilder stringBuilder = new StringBuilder(); // Для накопления данных из файла.
        try (FileReader reader = new FileReader(LOG_PATH)) {
            int c; // Переменная для хранения текущего символа.
            while ((c = reader.read()) != -1) { // Чтение символов по одному до конца файла.
                stringBuilder.append((char) c); // Добавление символа в StringBuilder.
            }
            // Удаление последнего символа (например, лишнего переноса строки).
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
            return stringBuilder.toString(); // Возврат содержимого файла как строки.
        } catch (Exception e) {
            e.printStackTrace(); // Вывод информации об ошибке.
            return null; // Возврат null в случае ошибки.
        }
    }
}

