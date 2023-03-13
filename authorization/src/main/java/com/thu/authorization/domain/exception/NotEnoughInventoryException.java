//package com.thu.authorization.domain.exception;
//
//class NotEnoughInventoryException extends Exception {
//
//    private int v1;
//    private int v2;
//
//    NotEnoughInventoryException(int v1, int v2) {
//        this.v1 = v1;
//        this.v2 = v2;
//    }
//
//    public void isResultInteger() throws ArithmeticException {
//        if (v2 <= 0) {
//            throw new ArithmeticException("Cannot divided by 0");
//        }
//        else if (v1 % v2 != 0) {
//            throw new ArithmeticException(v1 + " divided by " + v2 + " is not an integer");
//        } else {
//            System.out.println("The result of " + v1 + " divided by " + v2 + " is " + v1 / v2);
//        }
//    }
//
//}