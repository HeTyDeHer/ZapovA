package ru.bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Основной. Реализовать коллекцию Map для банка. Тестовое задание [#10038].
 * Created by Алексей on 10.10.2017.
 */
public class Bank {
    /** Map, где key - user, value - List счетов клиентов. */
    private Map<User, List<Account>> clients;

    /**
     * Конструктор.
     */
    public Bank() {
        this.clients = new HashMap<>();

    }

    /**
     * Добавление пользователя.
     * @param user Пользователь.
     */
    public void addUser(User user) {
        if (clients.containsKey(user)) {
            System.out.println("Пользователь уже существует!");
        } else {
            List<Account> list = new ArrayList<>();
            clients.put(user, list);
            System.out.println("Полльзователь " + user + " добавлен.");
        }
    }

    /**
     * удаление пользователя.
     * @param user Пользователь.
     */
    public void deleteUser(User user) {
        if (!clients.containsKey(user)) {
            System.out.println("Пользователь не существует!");
        } else {
            clients.remove(user);
            System.out.println("Полльзователь " + user + " удален.");
        }

    }

    /**
     * добавить счёт пользователю.
     * @param user Пользователь.
     * @param account счет.
     */
    public void addAccountToUser(User user, Account account) {
        if (!clients.containsKey(user)) {
            System.out.println("Пользователь не существует!");
        } else {
            clients.get(user).add(account);
            System.out.println("Счет " + account + " добавлен пользователю " + user + ".");
        }
    }

    /**
     * удалить один счёт пользователя.
     * @param user Пользователь.
     * @param account счет.
     */
    public void deleteAccountFromUser(User user, Account account) {
        if (!clients.containsKey(user)) {
            System.out.println("Пользователь не существует!");
        } else if (!clients.get(user).contains(account)) {
            System.out.printf("У пользователя нет такого счета.");
        } else {
            clients.get(user).remove(account);
            System.out.println("Счет " + account + " удален пользователю " + user + ".");
        }
    }

    /**
     * получить список счетов для пользователя.
     * @param user Пользователь.
     * @return List счетов пользователя.
     */
    public List<Account> getUserAccounts(User user) {
        List<Account> accounts = null;
        if (!clients.containsKey(user)) {
            System.out.println("Пользователь не существует!");
        } else {
            accounts = clients.get(user);
        }
        return accounts;
    }

    /**
     * метод для перечисления денег с одного счёта на другой счёт:
     если счёт не найден или не хватает денег на счёте srcAccount (с которого переводят) должен вернуть false.
     * @param srcUser Пользователь, который переводит.
     * @param srcAccount Счет пользователя, который переводит.
     * @param dstUser Пользователь получатель.
     * @param dstAccount Счет получателя.
     * @param amount сумма для перевода.
     * @return перевод возможен?
     */
    public boolean transferMoney(User srcUser, Account srcAccount, User dstUser, Account dstAccount, double amount) {
        boolean possible = false;
        int srcAccountIndex = clients.get(srcUser).indexOf(srcAccount);
        int dstAccountIndex = clients.get(dstUser).indexOf(dstAccount);
        if (clients.containsKey(srcUser)
                && clients.containsKey(dstUser)
                && srcAccountIndex != -1
                && dstAccountIndex != -1
                && clients.get(srcUser).get(srcAccountIndex).getValue() >= amount) {
            clients.get(srcUser).get(srcAccountIndex).addValue(-amount);
            clients.get(dstUser).get(dstAccountIndex).addValue(amount);
            possible = true;
        }
        return possible;
    }
}


