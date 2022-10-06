package com.example.carch2;

import javafx.fxml.FXML;
import javafx.scene.Camera;
import javafx.scene.control.*;


public class HelloController {
    @FXML
    public Spinner<Integer> shift;
    @FXML
    public ToggleGroup group;
    @FXML
    public RadioButton toLeft, toRight;
    @FXML
    public Label initWordLength, keyWordLength, message, vegenreMessage;
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


    public void showMessage(int fromWhichCipher) {
        switch (fromWhichCipher) {
            case 1:
                message.setText("INVALID INPUT!");
            case 2:
                vegenreMessage.setText("Inputs are invalid! Try again without any digits.");
        }
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
                showMessage(1);
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
        int inputLength = inputWord.length();
        int keyLength = keyWord.length();

        int input = 0;
        char key = 0;

        for (int i = 0; i < inputWord.length(); i++) {
            try {
                input = inputWord.charAt(i);
                key = (char) (keyWord.charAt(i) - 'a');
                if (keyLength < inputLength) {
                    keyWord = keyWord.repeat(inputLength);
                }
            } catch (StringIndexOutOfBoundsException r) {
                System.out.println("Error!");
            }
                if (Character.isLetter(inputWord.charAt(i)) && Character.isLetter(keyWord.charAt(i))) {
                    input += key;
                    input = (input % 122) + 'a';
                        if (input > 122) {
                            input -= 96;
                    }
                encryptedWord.append((char) (input));
            } else {
                showMessage(2);
            }
        }
        return encryptedWord.toString();
    }
}