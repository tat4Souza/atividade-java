package com.pratica4.application.utils;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class FieldsFormmaters {
    public String applyCPFMask(String s) {
        String m = s;
        if (m.length() > 3) m = m.substring(0, 3) + "." + m.substring(3);
        if (m.length() > 7) m = m.substring(0, 7) + "." + m.substring(7);
        if (m.length() > 11) m = m.substring(0, 11) + "-" + m.substring(11);
        return m;
    }

    public String applyCEPMask(String s) {
        String m = s;
        if (m.length() > 5) m = m.substring(0, 5) + "-" + m.substring(5);
        return m;
    }

    public String applyPhoneMask(String s) {
        int len = s.length();
        if (len <= 2) { return s; }
        if (len <= 6) { return "(" + s.substring(0, 2) + ") " + s.substring(2); }
        return "(" + s.substring(0, 2) + ") " + s.substring(2, 7) + "-" + s.substring(7);
    }

    public void textFormatter(TextField field, int characters, UnaryOperator<String> maskFunc) {
        field.textProperty().addListener((observable, oldVal, newVal) -> {
            String onlyNum = newVal.replaceAll("\\D", "");

            if (onlyNum.length() > characters) {
                onlyNum = onlyNum.substring(0, characters);
            }

            String mask = maskFunc.apply(onlyNum);

            if (!newVal.equals(mask)) {
                field.setText(mask);
                field.positionCaret(mask.length());
            }

        });
    }

    public void onlyText(TextField field) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getControlNewText();

            if(text.isEmpty() || !text.matches(".*\\d.*")) {
                return change;
            }

            return null;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        field.setTextFormatter(textFormatter);
    }

    public void stateFormmater(TextField field) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getControlNewText();

            if(text.length() <= 2 && text.matches("[a-zA-Z\\s]*")) {
                return change;
            }

            return null;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        field.setTextFormatter(textFormatter);
    }


}
