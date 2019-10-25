package com.example.smsapp;


import java.util.Scanner;

public class KeysArray {
    private String[] keys;

    public KeysArray(){
        keys = new String[3];
        keys[0]="0a";
        keys[1]="0b";
        keys[2]="0c";
    }

    public Boolean hasKey(int i, String Message){
        Scanner mexScanner = new Scanner(Message);
        if(mexScanner.next().equals(keys[i])){
            return true;
        }
        else {
            return false;
        }
    }
}
