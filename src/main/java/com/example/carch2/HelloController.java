package com.example.carch2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.TextFlow;


public class HelloController {
    @FXML
    public Spinner<Integer> shift;
    @FXML
    public ToggleGroup group;
    @FXML
    public RadioButton toLeft, toRight;
    @FXML
    public Label initWordLength, keyWordLength, message;
    @FXML
    private TextArea encryptArea, decryptedTextArea, vegenreTextArea, vegenreKeyWord, vegenreEncrypted;

    public void initialize() {
        initWordLength.textProperty().bind(vegenreTextArea.textProperty()
                .length()
                .asString("%d"));

        keyWordLength.textProperty().bind(vegenreKeyWord.textProperty()
                .length()
                .asString("%d"));
    }


    public void showMessage() {
        message.setText("INVALID INPUT!");
    }

    @FXML
    protected void onEncryptButtonClick() {
        encryptArea.setText(encrypt(decryptedTextArea.getText(), shift.getValue(), toRight.isSelected()));
        decryptedTextArea.setText("");
    }
    @FXML
    protected void onDecryptButtonClick() {
        encryptArea.setText(decrypt(encryptArea.getText(), shift.getValue(), toRight.isSelected()));
    }

    @FXML
    public String encrypt(String str, int shift, boolean rightIsSelected) {
        StringBuilder strBuilder = new StringBuilder();
        char c;

        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (Character.isLetter(c)) {
                if (rightIsSelected) {
                    c = (char) (str.charAt(i) + shift);
                } else {
                    c = (char) (str.charAt(i) - shift);
                }
                if ((Character.isLowerCase(str.charAt(i)) && c > 'z')
                        || (Character.isUpperCase(str.charAt(i)) && c > 'Z'))
                    if (rightIsSelected) {
                        c = (char) (str.charAt(i) + shift);
                    } else {
                        c = (char) (str.charAt(i) - shift);
                    }

                if (c + 0 > 122) {
                    c -= 26;
                } else if (c + 0 < 97) {
                    c += 26;
                }
            } else {
                showMessage();
            }
            strBuilder.append(c);

        }
        return strBuilder.toString();
    }


    public String decrypt(String str, int shift, boolean rightIsSelected) {
        StringBuilder strBuilder = new StringBuilder();
        char c;

        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (Character.isLetter(c)) {
                if (rightIsSelected) {
                    c = (char) (str.charAt(i) - shift);
                } else {
                    c = (char) (str.charAt(i) + shift);
                }

                if ((Character.isLowerCase(str.charAt(i)) && c > 'z')
                        || (Character.isUpperCase(str.charAt(i)) && c > 'Z'))
                    if (rightIsSelected) {
                        c = (char) (str.charAt(i) - shift);
                    } else {
                        c = (char) (str.charAt(i) + shift);
                    }

                if (c + 0 < 97) {
                    c += 26;
                } else if (c + 0 > 122) {
                    c -= 26;
                }
                System.out.println(str.charAt(i) - shift);
            }
            strBuilder.append(c);
        }
        return strBuilder.toString();
    }

    public void vegenreEncryptMethod() {
        vegenreEncrypted.setText(vegenreEncrypt(vegenreTextArea.getText(), vegenreKeyWord.getText()));
    }

    public String vegenreEncrypt(String inputWord, String keyWord) {
        StringBuilder encryptedWord = new StringBuilder();

        char input;
        int key;

        for (int i = 0; i < inputWord.length(); i++) {
            input = inputWord.charAt(i);
            key = keyWord.charAt(i);

            System.out.println(key);

            input += key % 26;
            System.out.println(input);
            encryptedWord.append(input + 26);
        }
        return encryptedWord.toString();
    }


}