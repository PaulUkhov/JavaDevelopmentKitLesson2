package server;

import server.client.domain.ClientController;
import server.client.ui.ClientGUI;
import server.server.domain.ServerController;
import server.server.repository.FileStorage;
import server.server.ui.ServerWindow;
//На семинаре мы разделили класс отвечающий за клиентское приложение на класс,
// отвечающий за логику приложения и за графическую часть приложения. А также создали слабую связь между ними с помощью интерфейса.
//Аналогичным образом вам надо преобразовать серверную часть приложения.
// Схема, которую требуется реализовать, также есть в материалах к уроку.
//Вы можете работать со своим проектом из первой домашки, а можете работать с проектом с семинара (ссылка в материалах к уроку).
//Требуется разделить класс серверного приложения на контроллер, GUI и репозиторий.
//Если вы работаете со своим проектом, то клиентскую часть также надо разделить на контроллер и GUI.
//Связь между составляющими проекта реализовать с помощью интерфейсов

public class Main {
    public static void main(String[] args) {
        ServerController serverController = new ServerController(new ServerWindow(), new FileStorage());

        new ClientController(new ClientGUI(), serverController);
        new ClientController(new ClientGUI(), serverController);
    }
}
