/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
/**
 *
 * @author serin
 */
public class Dictionary {
    
    private static final String WORDS_FILE = "words.txt";
    private final Trie trie;
    
    public Dictionary()
    {
        
        Scanner inputFile = null;
        String word;
        
        try
        {
            //reads in word.txt file
            this.trie = new Trie();
            URL url = getClass().getResource(WORDS_FILE);
            File file = new File(url.toURI());
            
            inputFile = new Scanner(file);
            
            //if text file is empty
            if(inputFile == null)
            {
                throw new IOException("Invalid URL specified");
            }
            
            //inputs words into a trie
            while(inputFile.hasNext())
            {
                word = inputFile.next();
                word = word.trim().toLowerCase();
                trie.insert(word);
            }
            
            System.out.println("Loaded all words into the trie");
        }
        //catches any exceptions that might be thrown
        catch(IOException | URISyntaxException e)
        {
            System.out.println("Error while loading words into the trie");
            throw new RuntimeException(e);
        }
  
    }
    
    //method that searches for a word and gives score
     public int search(String word)
     {
        return this.trie.search(word);
     }
        
     //method that states whether a word exists with a specific prefix
     public boolean prefix(String word)
     {
        return this.trie.prefix(word);
     }  
}
