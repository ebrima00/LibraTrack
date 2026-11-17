package com.libratrack.model;

public class Student extends Member {

    public Student(int memberId, String name) {
        super(memberId, name);
    }

    @Override
    public int getBorrowLimit() {
        return 3;
    }

    @Override
    public String toString() {
        return "com.libratrack.model.Student - " + super.toString();
    }
}