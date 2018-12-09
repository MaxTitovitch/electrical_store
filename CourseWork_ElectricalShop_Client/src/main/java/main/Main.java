package main;

import dialog_construction.MainJobPreparer;

public class Main {
    public static void main(String[] args) {
        //Работа строиться на шаблоне проектирования "Фасад". Вся функциональность проекта сводится к вызову 1-го метода
        MainJobPreparer controller = new MainJobPreparer();
        controller.getStarted();
    }
}
