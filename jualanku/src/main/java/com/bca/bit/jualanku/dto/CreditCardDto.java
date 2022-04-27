package com.bca.bit.jualanku.dto;

public class CreditCardDto {
    private String name;
    private Long balance;
    private String cardNumber;
    private String month;
    private String year;
    private String cvv;

    public CreditCardDto() {

    }

    public CreditCardDto(String name, Long balance, String cardNumber, String month, String year, String cvv) {
        this.name = name;
        this.balance = balance;
        this.cardNumber = cardNumber;
        this.month = month;
        this.year = year;
        this.cvv = cvv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
