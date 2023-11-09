package com.pluralsight;

public class SalesContract extends Contract{
    private static final double SALES_TAX_PERCENTAGE = 0.05;
    private static final double RECORDING_FEE = 100.0;
    private static final double PROCESSING_FEE_UNDER_10000 = 295.0;
    private static final double PROCESSING_FEE_ABOVE_10000 = 495.0;

    private boolean isFinanced;
    private double loanInterestRate;
    private int loanDurationMonths;

    public SalesContract(String date, String customerName, String customerEmail, boolean sold, double totalPrice,  boolean isFinanced) {
        super(date, customerName, customerEmail, totalPrice, sold);
        this.isFinanced = isFinanced;

        if (getTotalPrice() >= 10000) {
            this.loanInterestRate = 0.0425; // 4.25% for loans of 48 months
            this.loanDurationMonths = 48;
        } else {
            this.loanInterestRate = 0.0525; // 5.25% for loans of 24 months
            this.loanDurationMonths = 24;
        }
    }

    @Override
    public double getTotalPrice() {
        double totalPrice = getTotalPrice();

        double salesTax = totalPrice * SALES_TAX_PERCENTAGE;
        double processingFee = (totalPrice >= 10000) ? PROCESSING_FEE_ABOVE_10000 : PROCESSING_FEE_UNDER_10000;

        totalPrice += salesTax + RECORDING_FEE + processingFee;

        if (!isFinanced) {
            return totalPrice;
        }

        return totalPrice;
    }

    @Override
    public double getMonthlyPayment() {
        if (!isFinanced) {
            return 0;
        }

        double totalPrice = getTotalPrice();
        double monthlyPayment;

        if (totalPrice >= 10000) {
            monthlyPayment = (this.loanInterestRate / 12) * totalPrice / (1 - Math.pow(1 + this.loanInterestRate / 12, -this.loanDurationMonths));
        } else {
            monthlyPayment = (this.loanInterestRate / 12) * totalPrice / (1 - Math.pow(1 + this.loanInterestRate / 12, -this.loanDurationMonths));
        }

        return monthlyPayment;
    }
}
