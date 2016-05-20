package com.google.engedu.ghost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {
    int ind=0,len=s.length();
        TrieNode t=this;
        while(ind<len)
        {
            HashMap h=t.children;
            String key=String.valueOf(s.charAt(ind));
            if(h.containsKey(key))
            {
                t=(TrieNode)h.get(key);
            }
            else
            {
                TrieNode ch=new TrieNode();
                h.put(key,ch);
                t=ch;       //check for key contained or not
                if(ind==len-1)
                {
                    ch.children.put(s,null);
                }
            }
            ind++;

        }



    }

    public boolean isWord(String s) {

        TrieNode t=this;
        HashMap h;
        int len=s.length();
        int ind=0;
        while (ind<len)
        {
            h=t.children;
            String key=String.valueOf(s.charAt(ind));
            if(h.containsKey(key))
            {
                t=(TrieNode)h.get(key);
            }
            else
            return false;
            ind++;
        }
        h=t.children;
        if(h.containsKey(s))
            return true;
        return false;
    }

    public String getAnyWordStartingWith(String s) {

        TrieNode t=this;
        HashMap h;
        int len=s.length();
        int ind=0;
        while (ind<len)
        {
            h=t.children;
            String key=String.valueOf(s.charAt(ind));
            if(h.containsKey(key))
            {
                t=(TrieNode)h.get(key);
            }
            else
                return null;
            ind++;
        }
        String randomKey=null;
        while(t!=null)
        {
            h=t.children;
    //        HashMap<String, String> x;

            Random       random    = new Random();
            ArrayList<String> keys      = new ArrayList<String>(h.keySet());
                randomKey = keys.get( random.nextInt(keys.size()) );
            t = (TrieNode)h.get(randomKey);
        }
      //  if(t==null)
            return randomKey;
    }
    public String rand_alpha()
    {
        String com;
        final String alphabet = "abcdefghijklmnopqrstuvwxyz";
        final int N = alphabet.length();

        Random r = new Random();
        char ch= alphabet.charAt(r.nextInt(N));
        com=String.valueOf(ch);
        return com;
    }


    public String getGoodWordStartingWith(String s) {
        return null;
    }
}
