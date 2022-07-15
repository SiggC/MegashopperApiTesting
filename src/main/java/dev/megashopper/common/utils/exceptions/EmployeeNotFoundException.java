package dev.megashopper.common.utils.exceptions;


public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException() {
    }

    public EmployeeNotFoundException(String s) {
        super(s);
    }

    public EmployeeNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public EmployeeNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public EmployeeNotFoundException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
