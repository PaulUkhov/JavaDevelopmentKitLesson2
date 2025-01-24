package server.server.ui;

import server.server.domain.ServerController;
import server.server.repository.FileStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Класс ServerWindow представляет GUI для управления сервером.
 * Реализует интерфейс ServerView, чтобы интегрироваться с серверным контроллером.
 */
public class ServerWindow extends JFrame implements ServerView {

    // Константы ширины и высоты окна.
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;

    // Кнопки для управления сервером.
    private JButton btnStart, btnStop;

    // Текстовое поле для вывода логов.
    private JTextArea log;

    // Контроллер для управления серверной логикой.
    private ServerController serverController;

    /**
     * Конструктор ServerWindow.
     * Инициализирует настройки окна и компоненты.
     */
    public ServerWindow() {
        setting(); // Настройка основного окна.
        createPanel(); // Создание и добавление панелей и кнопок.

        setVisible(true); // Отображение окна.
    }

    /**
     * Устанавливает объект ServerController для управления сервером.
     * @param serverController объект контроллера сервера.
     */
    @Override
    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }

    /**
     * Возвращает текущий объект ServerController.
     * @return объект ServerController.
     */
    public ServerController getConnection() {
        return serverController;
    }

    /**
     * Метод для настройки основных параметров окна (размер, заголовок, поведение при закрытии).
     */
    private void setting() {
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Завершение приложения при закрытии окна.
        setSize(WIDTH, HEIGHT); // Установка размера окна.
        setResizable(false); // Запрет изменения размера окна.
        setTitle("Chat server"); // Установка заголовка окна.
        setLocationRelativeTo(null); // Центрирование окна на экране.
    }

    /**
     * Создаёт основные панели и добавляет их в окно.
     */
    private void createPanel() {
        log = new JTextArea(); // Поле для отображения логов.
        add(log); // Добавление текстовой области в основное окно.
        add(createButtons(), BorderLayout.SOUTH); // Добавление панели с кнопками.
    }

    /**
     * Создаёт панель с кнопками "Start" и "Stop".
     * @return компонент JPanel с кнопками.
     */
    private Component createButtons() {
        JPanel panel = new JPanel(new GridLayout(1, 2)); // Панель с кнопками в одну строку.
        btnStart = new JButton("Start"); // Кнопка для запуска сервера.
        btnStop = new JButton("Stop"); // Кнопка для остановки сервера.
//СВЯЗЬ МЕЖДУ КЛАССАМИ
        // Добавление действия для кнопки "Start".
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverController.start(); // Запуск сервера через контроллер.
            }
        });

        // Добавление действия для кнопки "Stop".
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverController.stop(); // Остановка сервера через контроллер.
            }
        });

        // Добавление кнопок на панель.
        panel.add(btnStart);
        panel.add(btnStop);
        return panel; // Возврат панели.
    }

    /**
     * Отображает сообщение в текстовой области (логах).
     * @param msg сообщение для отображения.
     */
    @Override
    public void showMessage(String msg) {
        log.append(msg); // Добавление текста в лог.
    }
}

