package assembler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class Pass2 extends Main {
 private static ArrayList <String> objectcode= new ArrayList<String>();
 private static ArrayList <String>       t   = new ArrayList<String>();
 private static String obj;
 static char n='0';
 static char i='0';
 static char x='0';
 static char b='0';
 static char p='0';
 static char e='0';
 static int pc,disp,form;
 static int TA=-1;
 static int BaseR;
 static int Tsize=0;
 static int labelimm(String operand)
 
 {operand=operand.replace("#","");
	 for(int i=0;i<symboltab.size();i++){
		 if(Character.isDigit(operand.charAt(0))){
			 return 2;}
		  else if (operand.compareToIgnoreCase(symboltab.get(i).label)==0)
		 {  
			 return 1;
		 }
		  
		 }
		return -1;
	 
 }
 static  int convert(String Base)
 {if(Base.charAt(0)=='#')
 {int z=labelimm(Base);
 Base=Base.replaceFirst("#","");
 if(z==2)
 {return Integer.parseInt(Base);
	 
 }
 else if(z==1)
 {for(int i=0;i<symboltab.size();i++)
 {
	 if(Base.compareToIgnoreCase(symboltab.get(i).label)==0)
	 {
		 return(symboltab.get(i).address);
	 }
	 
 }
	 
 }
 else if(z==-1)
 {
	 System.exit(0);
 }
 }
return -1;
 }
 static String form3BintoHexa(String bin)
 {
	 StringBuilder a1=new StringBuilder();
	 for(int i=0;i<6;i++)
	 {
		 String temp=bin.substring(i*4, 4+(i*4));
		 int decimal = Integer.parseInt(temp,2);
		 String hexStr = Integer.toString(decimal,16);
		 a1.append(hexStr);
	 }
	 return a1.toString();
 }
 static String form4BintoHexa(String bin)
 {
	 StringBuilder a1=new StringBuilder();
	 for(int i=0;i<8;i++)
	 {
		 String temp=bin.substring(i*4, 4+(i*4));
		 int decimal = Integer.parseInt(temp,2);
		 String hexStr = Integer.toString(decimal,16);
		 a1.append(hexStr);
	 }
	 return a1.toString();
 }
 static String dispform3(int disp)
 {
	 String temp=Integer.toBinaryString(disp);
	 if(disp>=0)
	 {
	 StringBuilder a=new StringBuilder();
	 for(int i=0;i<12-temp.length();i++)
	 {
		 a.append('0');
	 }
	 a.append(temp);
	return a.toString();
	 }else
	 {
		 return temp.substring(temp.length()-12, temp.length());
	 }
 }
 static String dispform4(int disp)
 {
	 String temp=Integer.toBinaryString(disp);
	 if(disp>=0)
	 {
	 StringBuilder a=new StringBuilder();
	 for(int i=0;i<20-temp.length();i++)
	 {
		 a.append('0');
	 }
	 a.append(temp);
	return a.toString();
	 }else
	 {
		 return temp.substring(temp.length()-20, temp.length());
	 }
 }
 static String hexToBinary(String hex) {
		String x="000000";
		StringBuilder a1=new StringBuilder();
	    String t=hex.substring(0,1);
	    int t1=Integer.parseInt(t, 16);
		String x1 =Integer.toBinaryString(t1);
		String x2=new String();
		if(hex.charAt(1)=='0')
		{
			x2="00";
		}else if(hex.charAt(1)=='4')
		{
			x2="01";
		}else if(hex.charAt(1)=='8')
		{
			x2="10";
		}else if(hex.charAt(1)=='c')
		{
			x2="11";
		}
		a1.append(x);
		a1.append(x1);
		a1.append(x2);
		String temp=a1.toString();

	    return temp.substring(temp.length()-6, temp.length());
	}
 
 public static void pass2test()
 {
  for(int indx=0;indx<sourcecode.size();indx++)
  {n='0';
  i='0';
  x='0';
  b='0';
  p='0';
  e='0';
  TA=-1;
   form=sourcecode.get(indx).format;
   pc=sourcecode.get(indx).address+form;
  if(form==1)
  {
   objectcode.add(indx,sourcecode.get(indx).hexa);
  }
  //-----------------------------------------------------------------------------------------------------
  else if(form==2)
  {int reg1,reg2;
   obj=sourcecode.get(indx).hexa;
   int commaNum=0;
   int indexComma = 0;
   for(int j=0;j<sourcecode.get(indx).operand.length();j++)
   {
    if(sourcecode.get(indx).operand.charAt(j)== ',')
    {
        indexComma = j;
        commaNum++;
       }
   }
   if (commaNum==0)
   {     reg1 = searchRegister(sourcecode.get(indx).operand.substring(0,sourcecode.get(indx).operand.length()));
         reg2=0;
    }
   else
    { reg1 = searchRegister(sourcecode.get(indx).operand.substring(0,indexComma));
     reg2 = searchRegister(sourcecode.get(indx).operand.substring(commaNum+1,sourcecode.get(indx).operand.length()));
    }  StringBuilder ob=new StringBuilder();
    ob.append(obj);
    ob.append(reg1);
    ob.append(reg2);
    objectcode.add(indx,ob.toString());
       
  }
  //----------------------------------------------------------------------------------------------
  else if(form==3)
  {int immlabelcheck=0;
   e='0';
   int calculdispcheck=-1;
   if(sourcecode.get(indx).hexa.compareToIgnoreCase("4c")==0)
   {
	   objectcode.add(indx, "4f0000");
   }else{
   String checki=sourcecode.get(indx).operand;
   
   if(checki.charAt(0)=='#')
   {immlabelcheck=labelimm(checki);
   if(immlabelcheck==-1)
   {
	   System.out.println("label not found");
	   System.exit(0);
   }
   
    i='1';
    sourcecode.get(indx).operand=sourcecode.get(indx).operand.replaceFirst("#","");
    
    
   }
   else if(sourcecode.get(indx).operand.charAt(0)=='@')
   {calculdispcheck=1;
    n ='1';
    sourcecode.get(indx).operand=sourcecode.get(indx).operand.replaceFirst("@","");
   }
   else
   {calculdispcheck=1;
    n=i='1';
   }
   int temp=sourcecode.get(indx).operand.indexOf(',');
   if(temp!=-1&&(sourcecode.get(indx).operand.charAt(temp+1)=='x'||sourcecode.get(indx).operand.charAt(temp+1)=='X'))
   {
    x='1';
    sourcecode.get(indx).operand=sourcecode.get(indx).operand.substring(0,sourcecode.get(indx).operand.length()-2);
   }
   if(immlabelcheck==2)
   {
   	disp=Integer.parseInt(sourcecode.get(indx).operand);
   }
   else if(calculdispcheck!=-1)
   {
	   if(Character.isDigit(sourcecode.get(indx).operand.charAt(0))){
	   TA=Integer.parseInt(sourcecode.get(indx).operand);
	   if(TA!=-1)
	   {
	    disp=TA-pc;
	    System.out.println("disp="+disp);
	    
	    if((disp<2047&&disp>-2048)||immlabelcheck==1)
	    {
	     p='1';
	    }else if(disp>0&&disp<4096&&base==true)
	    {
	     b='1';
	     disp=TA-convert(Basereg);
	    }else{System.out.println("Out of range!!");}
	   }
   }
	   else{
   for(int ind=0;ind<symboltab.size();ind++)
   {
	   String check=symboltab.get(ind).label;
    if(check.compareToIgnoreCase(sourcecode.get(indx).operand)==0)
    {
     TA=symboltab.get(ind).address;  
    }
   }}
   if(TA!=-1)
   {
    disp=TA-pc;
    System.out.println("disp="+disp);
    
    if((disp<2047&&disp>-2048)||immlabelcheck==1)
    {
     p='1';
    }else if(disp>0&&disp<4096&&base==true)
    {
     b='1';
     disp=TA-convert(Basereg);
    }else{System.out.println("Out of range!!");}
   }
   else{
	   System.out.println("label not found in symboltable!!!");	
	   System.exit(0);
   }
   }
   else{
	   disp=sourcecode.get(indx).address;
	   }
   System.out.println("Hexa :"+sourcecode.get(indx).hexa);
   obj=hexToBinary(sourcecode.get(indx).hexa);
   StringBuilder a3=new StringBuilder();
   a3.append(obj);
   a3.append(n);
   a3.append(i);
   a3.append(x);
   a3.append(b);
   a3.append(p);
   a3.append(e);
   a3.append(dispform3(disp));
   String tmp=form3BintoHexa(a3.toString());
   objectcode.add(indx,tmp);
     
   }
  }
  //-----------------------------------------------------------------------------------------------
  else if(form==4)
  {int immlabelcheck=0;
	e='1';
  
  if(sourcecode.get(indx).operand.charAt(0)=='#')
  {
	  immlabelcheck=labelimm(sourcecode.get(indx).operand);
      i='1';
      n='0';
  sourcecode.get(indx).operand=sourcecode.get(indx).operand.replaceFirst("#","");
  }
  else if(sourcecode.get(indx).operand.charAt(0)=='@')
  {
   n ='1';
   sourcecode.get(indx).operand=sourcecode.get(indx).operand.replaceFirst("@","");

  }
  else
  {
    n=i='1';
  }
  int temp=sourcecode.get(indx).operand.indexOf(',');
  if(temp!=-1&&(sourcecode.get(indx).operand.charAt(temp+1)=='x'||sourcecode.get(indx).operand.charAt(temp+1)=='X'))
  {
    x='1';
    sourcecode.get(indx).operand=sourcecode.get(indx).operand.substring(0,sourcecode.get(indx).operand.length()-2);
  }
   if(immlabelcheck==2)
   {
	   disp=Integer.parseInt(sourcecode.get(indx).operand);
	   
   }
   else if(immlabelcheck==1)
   {for(int ind=0;ind<symboltab.size();ind++)
   {
	   if(symboltab.get(ind).label.compareToIgnoreCase(sourcecode.get(indx).operand)==0)
	   {
	    TA=symboltab.get(ind).address;  
	   }
	  }
	  if(TA!=-1)
	  {
	   disp=TA;
	  }}
   else if(immlabelcheck==-1)
   {
	   System.out.println("error not found");
	   System.exit(0);
   }
   else{
	   if(Character.isDigit(sourcecode.get(indx).operand.charAt(0))){
		   TA=Integer.parseInt(sourcecode.get(indx).operand);	   }
	   else{ for(int ind=0;ind<symboltab.size();ind++)
  {
   if(symboltab.get(ind).label.compareToIgnoreCase(sourcecode.get(indx).operand)==0)
   {
    TA=symboltab.get(ind).address;  
   }
  }
	   }
  if(TA!=-1)
  {
   disp=TA;
  } else{
	   System.out.println("label not found in symboltable!!!");	
  }}
  obj=hexToBinary(sourcecode.get(indx).hexa);
  StringBuilder a3=new StringBuilder();
  a3.append(obj);
  a3.append(n);
  a3.append(i);
  a3.append(x);
  a3.append(b);
  a3.append(p);
  a3.append(e);
  a3.append(dispform4(disp));
  String tmp=form4BintoHexa(a3.toString());
  
  objectcode.add(indx,tmp);  
  }
  //------------------------------------------------------------
  else
  {
   if(sourcecode.get(indx).hexa.compareTo("ii")==0)
   {
    objectcode.add(indx,"zz");
   }
   else if(sourcecode.get(indx).hexa.compareTo("gg")==0)
   {
	   
    String temp=sourcecode.get(indx).operand;
    if(temp.charAt(0)=='x'||temp.charAt(0)=='X')
    {temp=temp.replaceFirst("x","");
     temp=temp.replaceFirst("X","");
     temp=temp.replaceAll(Pattern.quote("'") ,"");
     objectcode.add(indx,temp);
   
     
    }
    else if(temp.charAt(0)=='c'||temp.charAt(0)=='C')
    {temp=temp.replaceFirst("c","");
    temp=temp.replaceAll("C","");
    temp=temp.replaceAll(Pattern.quote("'") ,"");
    String o=new String();
    String y;
    byte[] bytes=temp.getBytes();
    for(int in=0;in<bytes.length;in++)
    {
       y=Integer.toHexString(bytes[in]);
       o=o.concat(y);  
    }
    objectcode.add(indx,o);
    
    
    
    }
    else
    {int x=Integer.parseInt(temp);
    String y=Integer.toHexString(x);
     objectcode.add(indx,y);
    }
    
    
   }
   else if(sourcecode.get(indx).hexa.compareTo("hh")==0)
   {
	   String temp=sourcecode.get(indx).operand;
   
    if(temp.charAt(0)=='x'||temp.charAt(0)=='X')
   {String x1="000000";
   temp=temp.replaceFirst("x","");
   temp=temp.replaceAll("X","");
   temp=temp.replaceAll(Pattern.quote("'") ,"");
   String conc=x1.concat(temp);
      conc=conc.substring(conc.length()-6, conc.length());
      objectcode.add(indx,conc);
   }
      else if(temp.charAt(0)=='c'||temp.charAt(0)=='C')
      {temp=temp.replaceFirst("c","");
    temp=temp.replaceAll("C","");
    temp=temp.replaceAll(Pattern.quote("'") ,"");
          String o=new String();
    String y;
    byte[] bytes=temp.getBytes();
    for(i=0;i<bytes.length;i++)
    {y=Integer.toHexString(bytes[i]);
   o=o.concat(y);  
    }
    if(o.length()<=6)
    {String x1="000000";
        String conc=x1.concat(temp);
        conc=conc.substring(conc.length()-6, conc.length());
    objectcode.add(indx, o); 
    }
    else
    {
     objectcode.add(indx,o);
    }
          
          }
      else {int x=Integer.parseInt(temp);
   String y=Integer.toHexString(x); 
   String x1="000000";
   String conc=x1.concat(y);
   conc=conc.substring(conc.length()-6, conc.length());
      objectcode.add(indx,conc);
   
       
      }
    }
   }
  }
  //---------------------------------------------------------------------------------------------
  Tfile t=new Tfile();
  try{
       t.run();
     }catch(IOException e)
       {
    	 
       }

       
  try{
	  PrintWriter writer=new PrintWriter("objectcodes.txt","UTF-8");
  
  for(int i =0;i<objectcode.size();i++)
  {
	  if(objectcode.get(i).compareToIgnoreCase("zz")!=0)
	  {
	    writer.println(objectcode.get(i));
      }
  }

   writer.close();
 }catch(IOException e)
    {
	 
    }
}


	public ArrayList<String> getobjectcodes() {
		// TODO Auto-generated method stub
		return objectcode;
	}
	}