package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;


public class Controller {
    @FXML
    private TextField userInput;

    @FXML
    private ImageView A;

    @FXML
    private ImageView B;

    @FXML
    private ImageView C;

    @FXML
    private ImageView U;

    @FXML
    private ImageView R;

    @FXML
    private ImageView T;

    @FXML
    private ImageView S;

    @FXML
    private ImageView Q;

    @FXML
    private ImageView P;

    @FXML
    private ImageView O;

    @FXML
    private ImageView N;

    @FXML
    private ImageView M;

    @FXML
    private ImageView L;

    @FXML
    private ImageView K;

    @FXML
    private ImageView J;

    @FXML
    private ImageView I;

    @FXML
    private ImageView H;

    @FXML
    private ImageView G;

    @FXML
    private ImageView F;

    @FXML
    private ImageView E;

    @FXML
    private ImageView D;

    @FXML
    private ImageView Z;

    @FXML
    private ImageView Y;

    @FXML
    private ImageView X;

    @FXML
    private ImageView W;

    @FXML
    private ImageView V;

    @FXML
    private Label grades;

    @FXML
    private Label errMsg;

    @FXML
    private Label preWords;

    //initialize variables
    private String word;
    private ArrayList<String> words = new ArrayList<String>();
    private int grade = 0;
    private ImageView[] imageViews = new ImageView[]{A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z};
    private ArrayList<Integer> mapBagValues = new ArrayList<Integer>();

    private static final TreeMap<Character,Integer> lettersMap ;
    static {
        lettersMap = new TreeMap<>();
        lettersMap.put('A', 1);
        lettersMap.put('B', 3);
        lettersMap.put('C', 3);
        lettersMap.put('D', 2);
        lettersMap.put('E', 1);
        lettersMap.put('F', 4);
        lettersMap.put('G', 2);
        lettersMap.put('H', 4);
        lettersMap.put('I', 2);
        lettersMap.put('J', 8);
        lettersMap.put('K', 5);
        lettersMap.put('L', 1);
        lettersMap.put('M', 3);
        lettersMap.put('N', 1);
        lettersMap.put('O', 1);
        lettersMap.put('P', 3);
        lettersMap.put('Q', 10);
        lettersMap.put('R', 1);
        lettersMap.put('S', 1);
        lettersMap.put('T', 1);
        lettersMap.put('U', 1);
        lettersMap.put('V', 4);
        lettersMap.put('W', 4);
        lettersMap.put('X', 8);
        lettersMap.put('Y', 4);
        lettersMap.put('Z', 10);
    }

    public TreeMap<Character,Integer> lettersBag;

    {
        lettersBag = new TreeMap<>();
        lettersBag.put('A', 9);
        lettersBag.put('B', 2);
        lettersBag.put('C', 2);
        lettersBag.put('D', 4);
        lettersBag.put('E', 12);
        lettersBag.put('F', 2);
        lettersBag.put('G', 3);
        lettersBag.put('H', 2);
        lettersBag.put('I', 8);
        lettersBag.put('J', 1);
        lettersBag.put('K', 1);
        lettersBag.put('L', 4);
        lettersBag.put('M', 2);
        lettersBag.put('N', 6);
        lettersBag.put('O', 8);
        lettersBag.put('P', 2);
        lettersBag.put('Q', 1);
        lettersBag.put('R', 6);
        lettersBag.put('S', 4);
        lettersBag.put('T', 6);
        lettersBag.put('U', 4);
        lettersBag.put('V', 2);
        lettersBag.put('W', 2);
        lettersBag.put('X', 1);
        lettersBag.put('Y', 2);
        lettersBag.put('Z', 1);
    }
    @FXML
    public void btnSubmit(ActionEvent event) {

        word = userInput.getText().trim().toUpperCase();
        if (!isGameOver()) {
            if (isValidWord()) {
                try {
                    if (isValidWordBag()) {
                        words.add(word);
                        errMsg.setText("");
                        userInput.setText("");
                        grades.setText(Integer.toString(gradeCount()));
                        disappearImg();
                    }
                } catch (IllegalArgumentException e) {
                    userInput.setText("");
                    errMsg.setText(e.getMessage());
                }

            } else {
                userInput.setText("");
                errMsg.setText("Input Word can't duplicate and must have 2 letters for minimum and 8 letters for maximum.");
            }
        } else {
            userInput.setText("");
            errMsg.setText("Game Over!");
        }
    }
    @FXML
    public void btnPrevWords(ActionEvent event) {
        preWords.setText(getPreviousWords());
    }
    private boolean isValidWord() {

            if (word.length() >= 2 && word.length() <= 8 && word.matches("\\w*([AEIOU])\\w*") && word.matches("[A-Z]*") && !words.contains(word)) {
                return true;
            } else
                return false;
    }
    private boolean isValidWordBag() {
        char[] wordToChar = word.toCharArray();
        Set<Character> allKeys = lettersBag.keySet();
        for (char w : wordToChar) {
            for (Character key : allKeys) {
                if (key == w) {
                    if (lettersBag.get(key) > 0) {
                        lettersBag.put(key, lettersBag.get(key) - 1);
                        break;
                    } else {
                        throw new IllegalArgumentException("Character " + key + " is used up");
                    }
                }
            }
        }
        return true;
    }
    public int gradeCount(){

        char[] wordToChar = word.toCharArray();
        Set<Character> allKeys = lettersMap.keySet();
        for (char w : wordToChar){
            for (Character key : allKeys){
                if (key==w){
                    grade += lettersMap.get(key);
                }
            }
        }
        return grade;
    }
    public String getPreviousWords(){
        return String.join(",", words);
    }
    public boolean isGameOver(){
        Set<Character> allKeys = lettersBag.keySet();
        int countBag = 0;
        int countVowels = 0;
        for (Character key : allKeys) {
            String keyString = Character.toString(key);
            countBag += lettersBag.get(key);
            if(keyString.matches("[AEIOU]")){
                countVowels += lettersBag.get(key);
            }
        }
        if (countBag == 1 || countVowels ==0){
            return true;
        }
        else
            return false;
    }
    public void disappearImg(){
        if(lettersBag.get('A') == 0){
            A.setImage(null);
        }
        if (lettersBag.get('B') == 0){
            B.setImage(null);
        }
        if (lettersBag.get('C') == 0){
            C.setImage(null);
        }
        if (lettersBag.get('D') == 0){
            D.setImage(null);
        }
        if (lettersBag.get('E') == 0){
            E.setImage(null);
        }
        if (lettersBag.get('F') == 0){
            F.setImage(null);
        }
        if (lettersBag.get('G') == 0){
            G.setImage(null);
        }
        if (lettersBag.get('H') == 0) {
            H.setImage(null);
        }
        if (lettersBag.get('I') == 0){
            I.setImage(null);
        }
        if (lettersBag.get('J') == 0){
            J.setImage(null);
        }
        if (lettersBag.get('K') == 0){
            K.setImage(null);
        }
        if (lettersBag.get('L') == 0){
            L.setImage(null);
        }
        if (lettersBag.get('M') == 0){
            M.setImage(null);
        }
        if (lettersBag.get('N') == 0){
            N.setImage(null);
        }
        if (lettersBag.get('O') == 0) {
            O.setImage(null);
        }
        if (lettersBag.get('P') == 0){
            P.setImage(null);
        }
        if (lettersBag.get('Q') == 0){
            Q.setImage(null);
        }
        if (lettersBag.get('R') == 0){
            R.setImage(null);
        }
        if (lettersBag.get('S') == 0){
            S.setImage(null);
        }
        if (lettersBag.get('T') == 0){
            T.setImage(null);
        }
        if (lettersBag.get('U') == 0){
            U.setImage(null);
        }
        if (lettersBag.get('V') == 0) {
            V.setImage(null);
        }
        if (lettersBag.get('W') == 0){
            W.setImage(null);
        }
        if (lettersBag.get('X') == 0){
            X.setImage(null);
        }
        if (lettersBag.get('Y') == 0){
            Y.setImage(null);
        }
        if (lettersBag.get('Z') == 0){
            Z.setImage(null);
        }
    }
    private void convertLettersBagToArrayList(){
        Set<Character> allKeys = lettersBag.keySet();
        for (Character key : allKeys) {
            mapBagValues.add(lettersBag.get(key));
        }
    }
    public void disappearImg2(){
        for (int index = 0; index < mapBagValues.size(); index++) {
            if(mapBagValues.get(index) == 0){
                imageViews[index].setImage(null);
            }
        }
    }
}
