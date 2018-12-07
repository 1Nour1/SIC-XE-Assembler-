package sic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class HTME extends Main {
int lineCount = 0;
boolean flag=true;
ArrayList<String> Mrecord=new ArrayList<String>();
public static ArrayList<String> newt = new ArrayList<String>();
public String getstartcode(String str)
{
 StringBuilder obj =new StringBuilder();
 for(int i=1;i<str.length();i++)
 {
  if(str.charAt(i)!='^')
  {
   obj.append(str.charAt(i));
   
  }else
  {
   
   return obj.toString();
  }
 }
 return str;
 
}
public int indexsearch(String str)
{
 pass2  P=new pass2();
 for(int i=0;i<P.getobjectcodes().size();i++)
 {
 if(str.equalsIgnoreCase(P.getobjectcodes().get(i)))
           {
          return i;
         }

 }
 return -1;
}
public long Tlength(String x){
	int countspace=0; 
	for(int i=0;i<x.length();i++)
     {
      if(x.charAt(i)=='^')
      {
       countspace++;
      }
      }
	return (x.length()-countspace)/2;
	
}

public static void UpdateDrec(){
	for(int i=0;i<extdef.size();i++){
		for(int y=0;y< symboltable.size();y++){
			if(extdef.get(i).label.equals(symboltable.get(y).label)){
				extdef.get(i).add=formatZeros(Long.toHexString(symboltable.get(y).address),6);
			}
		}
	}
}

public static String Drecord(int csect)
{
	
	String drec="D";
	System.out.println("size====="+extdef.size());
	for(int i=0;i<extdef.size();i++)
	{
		System.out.println(extdef.get(i).label+" "+extdef.get(i).controlsect);
		if(csect==extdef.get(i).controlsect)
		{
			drec+="^"+extdef.get(i).label+"^"+extdef.get(i).add;			
		}
	}
	if(drec.equalsIgnoreCase("D")){
		return null;
	}
	return drec;
	}

public static String Rrecord(int csect)
{
	String Rrec="R";
	for(int i=0;i<extref.size();i++)
	{
		if(extref.get(i).csect.contains(csect))
		{
			Rrec+="^"+extref.get(i).label;
			
		}
	}
	if(Rrec.equals("R"))
	{
	return null;
	}
	else{
		return Rrec;
	}
}
public void run(int csect,BufferedWriter buff) throws IOException
{  
     pass2 p=new pass2();
     String H= HeaderRecord(prognames.get(csect),startAddress , startcsect.get(csect));
     Modificationrecord(csect);
     UpdateDrec();
     String Drec=Drecord(csect);
     String Rrec=Rrecord(csect);
     String E = EndRecord(startAddress);
     BufferedWriter out = buff;
     out.write(H);
     out.newLine();
     if(Drec!=null)
     {
     out.write(Drec);
     out.newLine();
     }
     if(Rrec!=null)
     {
     out.write(Rrec);
     out.newLine();
     }
     getTrec(csect);
     for(int i=0;i<newt.size();i++)
  {
     String t=getstartcode(newt.get(i));
     int index=indexsearch(t);
     if(index!=-1)
     {
    out.write("T^"+formatZeros(Long.toHexString(Sourcecode.get(index).address),6)+"^"+formatZeros(Long.toHexString(Tlength(newt.get(i))),2)+newt.get(i));
       out.newLine();

     }
  }
     newt.clear();
     
     
     for(int i=0;i<Mrecord.size();i++)
     {
     out.write(Mrecord.get(i));
        out.newLine();
     }  
     Mrecord.clear();
     out.write(E);
     out.newLine();
     out.newLine();
    

}
public String HeaderRecord (String progName, String startAddress , int progLength)
{System.out.println("proglength="+progLength);
    return "H"+"^"+formatSpaces(progName,6)+"^"+ formatZeros(startAddress,6)+"^"+ formatZeros(Long.toHexString(progLength),6) + "\n";     
}


 public void Modificationrecord(int csect)
 {
  String Mrec = "";
  for(instructionsinf i:Sourcecode)
  {
   if(i.format==4&&i.csect==csect)
   {
    if(i.operand.charAt(0)=='#'&&Character.isLetter(i.operand.charAt(1)))
    {
    
      Mrec = "M" +"^"+ formatZeros(Integer.toHexString(i.address+1),6) +"^"+ formatZeros("5",2)+"^"+"+"+i.operand;
      Mrecord.add(Mrec); 
    }else if(Character.isLetter(i.operand.charAt(0)))
    {
     Mrec = "M" +"^"+ formatZeros(Integer.toHexString(i.address+1),6) +"^"+ "05"+"^"+"+"+i.operand;
     Mrecord.add(Mrec); 
    }
   }
   

  }

 }

public String EndRecord (String startAdress)
{
    return "E"+ "^"+formatZeros(startAdress,6);
}
static String formatZeros(String string , int reqDigits)
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
public static void getTrec(int csect)
{
    int length=0;
    int x=0;
    int j;
    pass2 p=new pass2();
    for(int i=0;i<p.getobjectcodes().size();i++)
    {
     String test="";
     for( j=x;j<p.getobjectcodes().size();j++)
     {
    	 if(csect==Sourcecode.get(j).csect){
      length+=p.getobjectcodes().get(j).length();
      if(p.getobjectcodes().get(j).equalsIgnoreCase("zz"))
      {
       length=0;
       x=j+1;
       break;
      }else if(length>60)
      {
       length=0;
       x=j;
       break;
      }else
      {
      
       test=test+"^"+p.getobjectcodes().get(j);   
      
      }
     }
     }
     i=j;
     if(!test.equalsIgnoreCase(""))
     {
     newt.add(test);
     }
               
    }
    
   
 
}

}