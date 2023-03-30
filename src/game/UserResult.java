/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.LinkedHashMap;
import java.util.Map;
import model.WordResult;

/**
 *
 * @author serin
 */
public class UserResult {
    
    private final Map<String, WordResult> wordToResultMap = new LinkedHashMap<>();
    int totalScore = 0;
    
    public int getTotalScore()
    {
        return totalScore;
    }
    
    //adds point score value together
    public void add(String word, WordResult result)
    {
        this.wordToResultMap.put(word, result);
        this.totalScore += result.getScore();
    }
    
    //gets word from list
    public WordResult get(String word)
    {
        return this.wordToResultMap.get(word);
    }
    
    //gives all the word
    public Map<String, WordResult> all()
    {
        return this.wordToResultMap;
    }
    
    //checks to see if a word exists in the list of words we have used
    public boolean exist(String word)
    {
        return this.wordToResultMap.containsKey(word);
    }
    
    //gets total amount of words found
    public int getWordCount()
    {
        return this.wordToResultMap.size();
    }
}
