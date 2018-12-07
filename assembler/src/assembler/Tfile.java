package assembler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Tfile extends Main {
int lineCount = 0;
boolean flag=true;
ArrayList<String> Mrecord=new ArrayList<String>();
public void run() throws IOException
{
	 Pass2 p=new Pass2();
     String H= HeaderRecord(progName,startAddress , progLength);
     String T = TextRecord (sourcecode.get(0).address, progLength, p.getobjectcodes());
     Modificationrecord();
     String E = EndRecord(startAddress);
     File file = new File ("Records.txt");
     BufferedWriter out = new BufferedWriter(new FileWriter(file));
     out.write(H);
     out.newLine();
     out.write(T);
     out.newLine();
     for(int i=0;i<Mrecord.size();i++)
     {
    	out.write(Mrecord.get(i));
        out.newLine();
     }     
     out.write(E);
     out.close();
}
public String HeaderRecord (String progName, String startAddress , int progLength)
{
    return "H"+"^"+formatSpaces(progName,6)+"^"+ formatZeros(startAddress,6)+"^"+ formatZeros(Long.toHexString(progLength),6) + "\n";     
}

public String TextRecord (Integer startAdress, int progLength,ArrayList<String> objectcode)
{
   
    int startT = startAdress;
    
    String textRec =  "";
    while (flag==true)
    {
       String codeLine ="";
       int count;
        count=0;
       for (count= 0; count< 60 && lineCount<objectcode.size();  )
       {  
           if(objectcode.get(lineCount).equalsIgnoreCase("zz") )
           {
        	   //flag=false;
               //break;
           }
           int length=objectcode.get(lineCount).length();
         
           count=count+length;
           if (count>=60)
           {   count=count-length;
               flag=false;
               break;
           }
           else {codeLine+= "^";
           if(!objectcode.get(lineCount).equalsIgnoreCase("zz")){
           codeLine += objectcode.get(lineCount);
           }
           }
           
           lineCount++;
       } 
       
       
      
       String lineLen = Integer.toHexString(count/2 ); 
   
       textRec += "T" +"^"+ formatZeros(Integer.toHexString(startT),6) +"^"+ formatZeros(lineLen,2) + codeLine+"^";
       
       
       if (lineCount >= objectcode.size())
       {
    	   flag=false;
           break;
       }
       while(lineCount < objectcode.size() && objectcode.get(lineCount)==null)
           lineCount++;
       
       if (lineCount >= objectcode.size())
       {
    	   flag=false;
           break;
       }
       startT =sourcecode.get(lineCount).address;
    }  
    return textRec;
}


 public void Modificationrecord()
 {
	 String Mrec = "";
	 for(Instruction i:sourcecode)
	 {
		 if(i.format==4)
		 {
			 if(i.operand.charAt(0)=='#'&&Character.isLetter(i.operand.charAt(1)))
			 {
				
					 Mrec = "M" +"^"+ formatZeros(Integer.toHexString(i.address+1),6) +"^"+ formatZeros("5",2);
					 Mrecord.add(Mrec); 
			 }else if(Character.isLetter(i.operand.charAt(0)))
			 {
				 Mrec = "M" +"^"+ formatZeros(Integer.toHexString(i.address+1),6) +"^"+ "05";
				 Mrecord.add(Mrec); 
			 }
		 }
	 }

 }

public String EndRecord (String startAdress)
{
    return "E"+ "^"+formatZeros(startAdress,6);
}
String formatZeros(String string , int reqDigits)
{
    while (string.length() < reqDigits)
    {
        string = "0" + string;
    }
    return string.toUpperCase();
}

String formatSpaces(String string , int reqDigits)
{
    while (string.length() < reqDigits)
    {
        string += " ";
    }
    return string;
 }
}