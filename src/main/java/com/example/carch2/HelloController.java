package com.example.carch2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;


public class HelloController {
    @FXML
    public Spinner<Integer> shift;
    @FXML
    public ToggleGroup group;
    @FXML
    public RadioButton toLeft, toRight;
    @FXML
    public Label initWordLength, keyWordLength, message, vegenreMessage, pairCipherLabel;
    @FXML
    private TextField encryptArea,
            decryptedTextArea,
            vegenreTextArea,
            vegenreKeyWord,
            vegenreEncrypted,
            pairCipherField,
            pairEncryptedArea,
            gammaWordField,
            gammaField,
            gammingResult;


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
            case 3:
                pairCipherLabel.setText("Inputs are invalid! Try again without any digits.");
        }
    }

    @FXML
    protected void onEncryptButtonClick() {
        encryptArea.setText(encrypt(decryptedTextArea.getText(), shift.getValue(), true));
        decryptedTextArea.setText("");
    }

    @FXML
    protected void onDecryptButtonClick() {
        encryptArea.setText(decrypt(encryptArea.getText(), shift.getValue(), true));
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

    @FXML
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

    @FXML
    public void vegenreEncryptMethod() {
        vegenreEncrypted.setText(vegenreEncrypt(vegenreTextArea.getText(), vegenreKeyWord.getText()));
    }

    @FXML
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
                    while (keyWord.length() <= inputLength) {
                        keyWord += keyWord;
                    }
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
    @FXML
    public String vegenreDecrypt(String message, int shift) {
        return vegenreEncrypt(message, String.valueOf(26 - (shift % 26)));
    }

    @FXML
    public void pairEncryptMethod() {
        pairEncryptedArea.setText(pairEncrypt(pairCipherField.getText()));
        System.out.println("Check");
    }

    @FXML
    public String pairEncrypt(String word) {
        StringBuilder encryptedWord = new StringBuilder();
        int character = 0;

        for (int i = 0; i < word.length(); i++) {
            try {
                character = word.charAt(i);
            } catch (StringIndexOutOfBoundsException s) {
                System.out.println("Error from pair cipher");
            }

            if (Character.isLetter(character)) {
                if (character >= 97 && character < 110) {
                    character += 13;
                } else if (character >= 110 && character <= 122) {
                    character -= 13;
                }
                encryptedWord.append((char) (character));
            } else {
                showMessage(3);
            }
        }
        return encryptedWord.toString();
    }

    public void pairDecryptMethod() {
        pairEncryptedArea.setText(pairEncrypt(pairEncryptedArea.getText()));
    }

    public int toInt(ArrayList<Integer> array) {
        StringBuilder str = new StringBuilder();
        for (int i : array) {
            str.append(i);
        }
        return Integer.parseInt(String.valueOf(str));
    }

    public int toInt(int[] array) {
        StringBuilder str = new StringBuilder();
        for (int i : array) {
            str.append(i);
        }
        return Integer.parseInt(String.valueOf(str));
    }


    int concatenate(long[] array) {
        String str = Arrays.stream(array)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());

        return Integer.parseInt(str);
    }

    public void invert(ArrayList<Integer> array) {
        for (int i = 0; i < array.size() / 2; i++) {
            int tmp = array.get(i);
            array.set(i, array.get(array.size() - i - 1));
            array.set(array.size() - i - 1, tmp);
        }
    }

    public int toBinary(int gamma) {
        int dividingResult = 1;
        ArrayList<Integer> binaryEl = new ArrayList<>();

        while (dividingResult != 0) {
            dividingResult = gamma / 2;
            binaryEl.add(gamma % 2);
            gamma = dividingResult;
        }

        invert(binaryEl);
        return toInt(binaryEl);
    }

    public int[] convertToArray(int number) {
        int i = 0;
        int length = (int) Math.log10(number);
        int divisor = (int) Math.pow(10, length);
        int[] temp = new int[length + 1];

        while (number != 0) {
            temp[i] = number / divisor;
            if (i < length) {
                ++i;
            }
            number = number % divisor;
            if (i != 0) {
                divisor = divisor / 10;
            }
        }
        return temp;
    }

    public int toDecimal(int binary) {
        int[] digits = convertToArray(binary);
        int temp, result = 0;
        int power = convertToArray(binary).length - 1;
        for (int digit : digits) {
            temp = (int) Math.pow(digit * 2, power);
            power--;
            result += temp;
        }
        return result;
    }

    public int findBits(int x, int y) {
        int[] upper = convertToArray(x);
        int[] under = convertToArray(y);

        for (int i = upper.length - 1, j = under.length - 1; j >= 0; i--, j--) {
            upper[i] = upper[i] ^ under[j];
        }
        return toInt(upper);
    }

    public String gammaEncode(String unencoded, int num) {
        StringBuilder encoded = new StringBuilder();
        int[] array = convertToArray(num);

        for (int i = 0; i < unencoded.length(); i++) {
            System.out.println(findBits(toBinary(unencoded.charAt(i)), toBinary(array[i])));
            encoded.append((char) (toDecimal(findBits(toBinary(unencoded.charAt(i)), toBinary(array[i])))));
        }
        System.out.println("\n");
        return encoded.toString();
    }

    public long generateGamma(String word) {
        Random random = new Random();
        long[] newGamma = new long[word.length()];

        for (int i = 0; i < word.length(); i++) {
            newGamma[i] = random.nextInt(1,9);
        }

        return concatenate(newGamma);
    }

    public void generateButtonClick() {
        gammaField.setText(String.valueOf(generateGamma(gammaWordField.getText())));
    }

    public void gammaEncodeButtonClick() {
        gammingResult.setText(gammaEncode(gammaWordField.getText(), Integer.parseInt(gammaField.getText())));
    }

    public void gammaDecodeButtonClick() {
        gammingResult.setText(gammaEncode(gammingResult.getText(), Integer.parseInt(gammaField.getText())));
    }

    public void inputCleaning() {
        gammaWordField.setText("");
        gammaField.setText("");
        gammingResult.setText("");
    }

}