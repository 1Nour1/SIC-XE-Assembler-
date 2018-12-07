package sic;

import java.util.ArrayList;

import javax.swing.plaf.synth.SynthSpinnerUI;

public class RegisterTable {
    String register;
    int number;
	protected static ArrayList<refList> extref=new ArrayList<refList>();
    
    RegisterTable(String register,int number){
     this.register = register;
     this.number = number;
    }

	
    public static void main(String[]args)
    {
    }
}
 