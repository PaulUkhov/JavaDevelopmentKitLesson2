package server.server.domain;

import server.client.domain.ClientController;
import server.server.repository.Repository;
import server.server.ui.ServerView;

import java.util.ArrayList;
import java.util.List;

import server.client.domain.ClientController;
import server.server.repository.Repository;
import server.server.ui.ServerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс ServerController отвечает за управление сервером.
 * Управляет подключением/отключением клиентов, отправкой сообщений и работой сервера.
 */
public class ServerController {
    private boolean work; // Флаг, указывающий на текущее состояние сервера (работает или нет).
    private ServerView serverView; // Интерфейс для взаимодействия с пользователем.
    private List<ClientController> clientControllerList; // Список подключенных клиентов.
    private Repository<String> repository; // Репозиторий для сохранения сообщений в историю.

    /**
     * Конструктор, инициализирующий представление сервера, репозиторий и список клиентов.
     * @param serverView представление сервера для отображения сообщений.
     * @param repository репозиторий для сохранения истории сообщений.
     */
    public ServerController(ServerView serverView, Repository<String> repository) {
        this.serverView = serverView;
        this.repository = repository;
        clientControllerList = new ArrayList<>();
        serverView.setServerController(this); // Установка контроллера для представления.
    }

    /**
     * Запускает сервер, если он ещё не работает.
     */
    public void start() {
        if (work) {
            showOnWindow("Сервер уже был запущен");
        } else {
            work = true;
            showOnWindow("Сервер запущен!");
        }
    }

    /**
     * Останавливает сервер и отключает всех подключённых клиентов.
     */
    public void stop() {
        if (!work) {
            showOnWindow("Сервер уже был остановлен");
        } else {
            work = false;
            while (!clientControllerList.isEmpty()) {
                disconnectUser(clientControllerList.get(clientControllerList.size() - 1));
            }
            showOnWindow("Сервер остановлен!");
        }
    }

    /**
     * Отключает указанного клиента от сервера.
     * @param clientController клиент, которого необходимо отключить.
     */
    public void disconnectUser(ClientController clientController) {
        clientControllerList.remove(clientController);
        if (clientController != null) {
            clientController.disconnectedFromServer(); // Оповещение клиента об отключении.
            showOnWindow(clientController.getName() + " отключился от беседы");
        }
    }

    /**
     * Подключает клиента к серверу, если сервер работает.
     * @param clientController клиент, которого необходимо подключить.
     * @return true, если клиент успешно подключен; false, если сервер не работает.
     */
    public boolean connectUser(ClientController clientController) {
        if (!work) {
            return false;
        }
        clientControllerList.add(clientController);
        showOnWindow(clientController.getName() + " подключился к беседе");
        return true;
    }

    /**
     * Обрабатывает входящее сообщение, рассылает его клиентам и сохраняет в истории.
     * @param text текст сообщения.
     */
    public void message(String text) {
        if (!work) {
            return;
        }
        showOnWindow(text);
        answerAll(text);
        saveInHistory(text);
    }

    /**
     * Загружает историю сообщений из репозитория.
     * @return строка с историей сообщений.
     */
    public String getHistory() {
        return repository.load();
    }

    /**
     * Отправляет сообщение всем подключённым клиентам.
     * @param text текст сообщения.
     */
    private void answerAll(String text) {
        for (ClientController clientController : clientControllerList) {
            clientController.answerFromServer(text);
        }
    }

    /**
     * Отображает сообщение в интерфейсе сервера.
     * @param text текст сообщения.
     */
    private void showOnWindow(String text) {
        serverView.showMessage(text + "\n");
    }

    /**
     * Сохраняет сообщение в историю с использованием репозитория.
     * @param text текст сообщения.
     */
    private void saveInHistory(String text) {
        repository.save(text);
    }
}

