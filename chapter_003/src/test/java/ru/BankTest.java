package ru.bank;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест Основной. Реализовать коллекцию Map для банка. Тестовое задание [#10038].
 * Created by Алексей on 11.10.2017.
 */
public class BankTest {
    /**
     * Добавление user.
     */
    @Test
    public void addTest() {
        Bank bank = new Bank();
        User user1 = new User("Вася", 2210);
        bank.addUser(user1);
        boolean result = bank.getClients().containsKey(user1);
        assertThat(result, is(true));
    }

    /**
     * Удаление user.
     */
    @Test
    public void deleteTest() {
        Bank bank = new Bank();
        User user1 = new User("Вася", 2210);
        User user2 = new User("Уася", 2200);
        bank.addUser(user1);
        bank.addUser(user2);
        boolean added = bank.getClients().containsKey(user1) && bank.getClients().containsKey(user2);
        bank.deleteUser(user1);
        boolean result = !bank.getClients().containsKey(user1) && bank.getClients().containsKey(user2) && added;
        assertThat(result, is(true));
    }

    /**
     * ДОбавление счета.
     */
    @Test
    public void addAccountTest() {
        Bank bank = new Bank();
        User user1 = new User("Вася", 2210);
        bank.addUser(user1);
        Account account1 = new Account(500);
        bank.addAccountToUser(user1, account1);
        boolean result = bank.getClients().get(user1).contains(account1);
        assertThat(result, is(true));
    }

    /**
     * Удаление счета.
     */
    @Test
    public void deleteAccountTest() {
        Bank bank = new Bank();
        User user1 = new User("Вася", 2210);
        bank.addUser(user1);
        Account account1 = new Account(500);
        Account account2 = new Account(500);
        bank.addAccountToUser(user1, account1);
        bank.addAccountToUser(user1, account2);
        boolean added = bank.getClients().get(user1).contains(account1) && bank.getClients().get(user1).contains(account2);
        bank.deleteAccountFromUser(user1, account1);
        boolean result = !bank.getClients().get(user1).contains(account1) && bank.getClients().get(user1).contains(account2) && added;
        assertThat(result, is(true));
    }

    /**
     * Получение счетов.
     */
    @Test
    public void getAllAccountsTest() {
        Bank bank = new Bank();
        User user1 = new User("Вася", 2210);
        bank.addUser(user1);
        Account account1 = new Account(500);
        Account account2 = new Account(500);
        Account account3 = new Account(500);
        bank.addAccountToUser(user1, account1);
        bank.addAccountToUser(user1, account2);
        bank.addAccountToUser(user1, account3);
        boolean added = bank.getClients().get(user1).contains(account1) && bank.getClients().get(user1).contains(account2) && bank.getClients().get(user1).contains(account3);
        List<Account> result = bank.getUserAccounts(user1);
        List<Account> expected = new ArrayList<>(Arrays.asList(account1, account2, account3));
        assertThat(result, is(expected));
    }

    /**
     * Перевод денег.
     */
    @Test
    public void transferMoneyTest() {
        Bank bank = new Bank();
        User user1 = new User("Вася", 2210);
        User user2 = new User("Уася", 2200);
        bank.addUser(user1);
        bank.addUser(user2);
        Account account1 = new Account(500);
        Account account2 = new Account(500);
        Account account3 = new Account(500);
        bank.addAccountToUser(user1, account1);
        bank.addAccountToUser(user2, account2);
        boolean result1 = bank.transferMoney(user1, account1, user2, account2, 100);
        boolean result1TransferSuccessful = bank.getClients().get(user1).get(0).getValue() == 400
                && bank.getClients().get(user2).get(0).getValue() == 600;
        boolean result2 = bank.transferMoney(user1, account1, user2, account2, 1000);
        boolean result3 = bank.transferMoney(user1, account1, user2, account3, 1000);
        assertThat(result1, is(true));
        assertThat(result1TransferSuccessful, is(true));
        assertThat(result2, is(false));
        assertThat(result3, is(false));

    }
}
