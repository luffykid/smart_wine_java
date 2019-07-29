package com.changfa.frame.core.exception;

class ExceptionUtils {
    static String buildMessage(String message, Throwable cause) {
        if (cause != null) {
            StringBuilder buf = new StringBuilder();
            if (message != null) {
                buf.append(message).append("; ");
            }
            buf.append("nested exception is ").append(cause);
            return buf.toString();
        } else {
            return message;
        }
    }

    static Throwable getRootCause(Throwable ex, boolean nullable) {
        Throwable rootCause = nullable ? null : ex;
        Throwable cause = ex.getCause();
        while (cause != null && cause != rootCause) {
            rootCause = cause;
            cause = cause.getCause();
        }
        return rootCause;
    }

    static boolean contains(Throwable ex, Class<? extends Throwable> exType) {
        if (exType == null) {
            return false;
        }
        if (exType.isInstance(ex)) {
            return true;
        }
        Throwable cause = ex.getCause();
        if (cause == ex) {
            return false;
        }
        if (cause instanceof AppRuntimeException) {
            return ((AppRuntimeException) cause).contains(exType);
        } else if (cause instanceof AppCheckedException) {
            return ((AppCheckedException) cause).contains(exType);
        } else {
            while (cause != null) {
                if (exType.isInstance(cause)) {
                    return true;
                }
                if (cause.getCause() == cause) {
                    break;
                }
                cause = cause.getCause();
            }
            return false;
        }
    }
}
