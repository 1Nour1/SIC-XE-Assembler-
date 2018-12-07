package sic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class pass2 extends Main {
 protected static ArrayList <String> objectcode= new ArrayList<String>();
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
 static int ImmCheck(String operand)
 
 {operand=operand.replace("#","");
	 for(int i=0;i<symboltable.size();i++){
		 if(Character.isDigit(operand.charAt(0))){
			 return 2;}
		  else if (operand.compareToIgnoreCase(symboltable.get(i).label)==0)
		 {  
			 return 1;
		 }
		  
		 }
		return -1;
	 
 }
 static String fillzeros(int tA2)
 {
	 StringBuilder a=new StringBuilder();
	 String temp=Integer.toHexString(tA2);
	 for(int i=1;i<6-temp.length();i++)
	 {
		 a.append('0');
	 }
	 a.append(tA2);
	 return a.toString();
 }
 static  int convert(String Base)
 {if(Base.charAt(0)=='#')
 {int type=ImmCheck(Base);
 System.out.println("typ");
 Base=Base.replaceFirst("#","");
 if(type==2)
 {
	 
	 return Integer.parseInt(Base);
	 
 }
 else if(type==1)
 {for(int i=0;i<symboltable.size();i++)
 {
	 if(Base.compareToIgnoreCase(symboltable.get(i).label)==0)
	 {
		 System.out.println("base  found"+symboltable.get(i).address);
		 return(symboltable.get(i).address);
	 }else{
		 System.out.println("base not found"+Base);
	 }
	 
 }
	 
 }
 else if(type==-1)
 {
	 System.exit(0);
 }
 }else
 {
	 for(int i=0;i<symboltable.size();i++)
	 {
		 if(Base.compareToIgnoreCase(symboltable.get(i).label)==0)
		 {
			 System.out.println("base  found"+symboltable.get(i).address);
			 return(symboltable.get(i).address);
		 }else{
			 System.out.println("base not found"+Base);
		 }
		 
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

 static int extcontains(ArrayList<String> exp,int section){
	 for(int indx=0;indx<exp.size();indx+=2)
	 {
	  for(int y=0;y<extref.size();y++){
		  if(extref.get(y).label.equalsIgnoreCase(exp.get(indx)))
		  {
			  if(!extref.get(y).csect.contains(section))
			  {
				  System.out.println("DIFFERENT CONTROL SECTION!");
				  System.exit(0);
				  
			  }
			  
			  return 1;
		  }
	  }
	 }
	 return -1;
}
 static int SymbolSect(String lb){
	  for(int y=0;y<symboltable.size();y++){
		  if(symboltable.get(y).label.equals(lb)){
			  return  y;
		  }
	  }return -1;
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
	    char y=hex.charAt(1);
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
		}else if(hex.charAt(1)=='c'||hex.charAt(1)=='C')
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
 {try{
	  PrintWriter writer=new PrintWriter("LISTFILE.txt","UTF-8");
	  
 for(int i =0;i<LocationCounter.size();i++)
 {
	    writer.println(Integer.toHexString(LocationCounter.get(i)));
 }

  writer.close();
}catch(IOException e)
   {
	 
   }
	 try{
		  PrintWriter writer=new PrintWriter("symbol.txt","UTF-8");
	  
	  for(int i =0;i<symboltable.size();i++)
	  {
		  
		  
		    writer.println(symboltable.get(i).label+" "+HTME.formatZeros(Integer.toHexString(symboltable.get(i).address),4)+" "+symboltable.get(i).AR+" "+symboltable.get(i).csection);
	      
	  }

	   writer.close();
	 }catch(IOException e)
	    {
		 
	    }
  for(int t=0;t<Sourcecode.size();t++)
  {n='0';
  i='0';
  x='0';
  b='0';
  p='0';
  e='0';
  TA=-1;
   form=Sourcecode.get(t).format;
   pc=Sourcecode.get(t).address+form;
   disp=0;
  if(form==1)
  {
   objectcode.add(t,Sourcecode.get(t).hexa);
  }
  //-----------------------------------------------------------------------------------------------------
  else if(form==2)
  {int reg1,reg2;
   obj=Sourcecode.get(t).hexa;
   int commaNum=0;
   int indexComma = 0;
   for(int j=0;j<Sourcecode.get(t).operand.length();j++)
   {
    if(Sourcecode.get(t).operand.charAt(j)== ',')
    {
        indexComma = j;
        commaNum++;
       }
   }
   if (commaNum==0)
   {     reg1 = searchRegister(Sourcecode.get(t).operand.substring(0,Sourcecode.get(t).operand.length()));
         reg2=0;
    }
   else
    { reg1 = searchRegister(Sourcecode.get(t).operand.substring(0,indexComma));
     reg2 = searchRegister(Sourcecode.get(t).operand.substring(commaNum+1,Sourcecode.get(t).operand.length()));
    }  StringBuilder ob=new StringBuilder();
    ob.append(obj);
    ob.append(reg1);
    ob.append(reg2);
    objectcode.add(t,ob.toString());
       
  }
  //----------------------------------------------------------------------------------------------
  else if(form==3)
  {int immlabelcheck=0;
  e='0';
  int calculdispcheck=-1;
  if(Sourcecode.get(t).hexa.compareToIgnoreCase("4c")==0)
  {
	   objectcode.add(t, "4f0000");
  }
  else
      {
       String checki=Sourcecode.get(t).operand;
     //Find 'n'&'i'................................................................
        if(checki.charAt(0)=='#')
        {
         immlabelcheck=ImmCheck(checki);
             if(immlabelcheck==-1)
              {
            System.out.println("label not found");
            System.exit(0);
              }
         i='1';
         Sourcecode.get(t).operand=Sourcecode.get(t).operand.replaceFirst("#","");
        
        }else if(Sourcecode.get(t).operand.charAt(0)=='@')
        {
         calculdispcheck=1;
            n ='1';
            Sourcecode.get(t).operand=Sourcecode.get(t).operand.replaceFirst("@","");
        }else
        {
         calculdispcheck=1;
         n=i='1';
        }
         
        
        //Find 'X'................................................................
        int temp=Sourcecode.get(t).operand.indexOf(',');
        if(temp!=-1&&(Sourcecode.get(t).operand.charAt(temp+1)=='x'||Sourcecode.get(t).operand.charAt(temp+1)=='X'))
        {
         x='1';
         Sourcecode.get(t).operand=Sourcecode.get(t).operand.substring(0,Sourcecode.get(t).operand.length()-2);
        }
        //immediate number.............................................................
        if(immlabelcheck==2)
        {
         disp=Integer.parseInt(Sourcecode.get(t).operand);
        }else if(immlabelcheck==1)
        {
         for(int ind=0;ind<symboltable.size();ind++)
          {
                 if(Sourcecode.get(t).operand.equalsIgnoreCase(symboltable.get(ind).label))
                 {
                  TA=symboltable.get(ind).address;
                 }
          }
        
         if(TA!=-1)
         {
          disp=TA-pc;
          if(disp>-2048&&disp<2047)
          {
        	  p='1';
          }
          else{System.out.println("out of range");
          System.exit(0);
          }
         }else
         {
          System.out.println("Label not found in symboltable!!5");
          System.exit(0);
         }

        }
      if(calculdispcheck!=-1)
        {
       for(int ind=0;ind<symboltable.size();ind++)
      {
    	   String tep=symboltable.get(ind).label.replaceAll(" ","");
    	   //System.out.println("ahpppppp"+TA);
             if(Sourcecode.get(t).operand.equalsIgnoreCase(tep)&&Sourcecode.get(t).csect==symboltable.get(ind).csection)
             {
              TA=symboltable.get(ind).address;
              System.out.println(tep+Sourcecode.get(t).operand+"!!!!!!!!!!!!!!!!!!!!!");
             }
      }
      // System.out.println("ahpppppp"+Sourcecode.get(t).operand+Sourcecode.get(t).hexa);
         if(TA!=-1)
         {
          disp=TA-pc;
           System.out.println("disp="+disp);
           
           if((disp<2047&&disp>-2048)||immlabelcheck==1)
           {
            p='1';
           }else
           {   
               disp=TA-convert(Basereg);
               if(disp>=0&&disp<4096&&base==true)
               {
                b='1';
               }else
               {
                System.out.println("Out of range!!");
                System.exit(0);
               }
           }
          
     
         
         }else
         {
        	 System.out.println("!!!!"+Sourcecode.get(t).operand+Sourcecode.get(t).hexa);
          System.out.println("Label not found in symboltable!! or format 3 with external reference error"+"("+Sourcecode.get(t).operand+")");
           System.exit(0);
         }
        }
      System.out.println("Hexa :"+Sourcecode.get(t).hexa);
      obj=hexToBinary(Sourcecode.get(t).hexa);
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
      System.out.println(t+"___"+tmp);
      objectcode.add(t,tmp);
    }
      
   
     
   }
  //-----------------------------------------------------------------------------------------------
  else if(form==4)
  {int type=0;
	e='1';
  
  if(Sourcecode.get(t).operand.charAt(0)=='#')
  {type=ImmCheck(Sourcecode.get(t).operand);
  i='1';
  n='0';
  Sourcecode.get(t).operand=Sourcecode.get(t).operand.replaceFirst("#","");
  }
  else if(Sourcecode.get(t).operand.charAt(0)=='@')
  {
   n ='1';
   Sourcecode.get(t).operand=Sourcecode.get(t).operand.replaceFirst("@","");

  }
  else
  {
    n=i='1';
  }
  int temp=Sourcecode.get(t).operand.indexOf(',');
  if(temp!=-1&&(Sourcecode.get(t).operand.charAt(temp+1)=='x'||Sourcecode.get(t).operand.charAt(temp+1)=='X'))
  {
    x='1';
    Sourcecode.get(t).operand=Sourcecode.get(t).operand.substring(0,Sourcecode.get(t).operand.length()-2);
  }
   if(type==2)
   {
	   disp=Integer.parseInt(Sourcecode.get(t).operand);
	   
   }
   else if(type==1)
   {for(int ind=0;ind<symboltable.size();ind++)
   {
	   if(symboltable.get(ind).label.compareToIgnoreCase(Sourcecode.get(t).operand)==0&&Sourcecode.get(t).csect==symboltable.get(ind).csection)
	   {
	    TA=symboltable.get(ind).address;  
	   }
	   
	   }
  if(TA==-1){
	  for(int index2=0;index2<extref.size();index2++){
		  if(extref.get(index2).label.equals(Sourcecode.get(t).operand)&&extref.get(index2).csect.contains(Sourcecode.get(t).csect)){
			  TA=0;
			  
		  }
	  }
  }
   
	  if(TA!=-1)
	  {
	   disp=TA;
	  }}
   else if(type==-1)
   {
	   System.out.println("error not found");
	   System.exit(0);
   }
   else{
	   if(Character.isDigit(Sourcecode.get(t).operand.charAt(0))){
		   TA=Integer.parseInt(Sourcecode.get(t).operand);	   }
	   else{ for(int ind=0;ind<symboltable.size();ind++)
  {
   if(symboltable.get(ind).label.compareToIgnoreCase(Sourcecode.get(t).operand)==0&&Sourcecode.get(t).csect==symboltable.get(ind).csection)
   {
    TA=symboltable.get(ind).address;  
   }
  }
 if(TA==-1){
	for(int index2=0;index2<extref.size();index2++)
	{
     	 //System.out.println(extref.get(index2).label+Sourcecode.get(t).operand);
		  if(extref.get(index2).label.equalsIgnoreCase(Sourcecode.get(t).operand)&&extref.get(index2).csect.contains(Sourcecode.get(t).csect))
		    {
		       TA=0;		  
			}
	}
		  }
	   }
  if(TA!=-1)
  {
   disp=TA;
  } else{
	  System.out.println("disp="+Sourcecode.get(t).operand);
	   System.out.println("label not found in symboltable!!!");	
	   System.exit(0);
  }}
  obj=hexToBinary(Sourcecode.get(t).hexa);
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
  
  objectcode.add(t,tmp);  
  }
  //------------------------------------------------------------
  else
  {
   if(Sourcecode.get(t).hexa.compareTo("zz")==0)
   {
    objectcode.add(t,"zz");
   }
   else if(Sourcecode.get(t).hexa.compareTo("gg")==0)
   {  
	   TA=-1;
	   //handle word % expresion hena!!
    String temp=Sourcecode.get(t).operand;
    if(!temp.contains("’")&&!temp.contains("'"))
    {for(int ind=0;ind<symboltable.size();ind++)
    {
 	   if(symboltable.get(ind).label.compareToIgnoreCase(temp)==0&&Sourcecode.get(t).csect==symboltable.get(ind).csection)
 	   {
 	    TA=symboltable.get(ind).address;  
 	   }
 	   
 	  }if(TA!=-1)
 	  {
 		  objectcode.add(t,Integer.toHexString(TA));
 	  }
    if(TA==-1)
    {
    	for(int i=0;i<extref.size();i++)
    	{
    		if(extref.get(i).label.equalsIgnoreCase(temp))
    		{
    			TA=0;
    		}
    	}
    	if(TA==0)
    	{
    		objectcode.add(t,"00");
    	}
    	if(TA==-1)
    	{
    		System.out.println("Label not found!!555555"+Sourcecode.get(t).operand);
    	    System.exit(0);
    	}
    }
    
    	
    }
    else if(temp.charAt(0)=='x'||temp.charAt(0)=='X')
    {temp=temp.replaceFirst("x","");
     temp=temp.replaceFirst("X","");
     temp=temp.replaceAll(Pattern.quote("’") ,"");
     temp=temp.replaceAll(Pattern.quote("'") ,"");
     objectcode.add(t,temp);
   
     
    }
    else if(temp.charAt(0)=='c'||temp.charAt(0)=='C')
    {temp=temp.replaceFirst("c","");
    temp=temp.replaceAll("C","");
    temp=temp.replaceAll(Pattern.quote("’") ,"");
    String o=new String();
    String y;
    byte[] bytes=temp.getBytes();
    for(int in=0;in<bytes.length;in++)
    {
       y=Integer.toHexString(bytes[in]);
       o=o.concat(y);  
    }
    objectcode.add(t,o);
    
    
    
    }
    else
    {int x=Integer.parseInt(temp);
    String y=Integer.toHexString(x);
     objectcode.add(t,y);
    }
    
    
   }
   else if(Sourcecode.get(t).hexa.compareTo("hh")==0)
   {
	   String temp=Sourcecode.get(t).operand;
	   if(!temp.contains("’"))
	    {for(int ind=0;ind<symboltable.size();ind++)
	    {
	 	   if(symboltable.get(ind).label.compareToIgnoreCase(temp)==0&&Sourcecode.get(t).csect==symboltable.get(ind).csection)
	 	   {
	 	    TA=symboltable.get(ind).address;  
	 	   }
	 	   
	 	   
	 	  }
	    if(TA!=-1)
	    {
	    	objectcode.add(fillzeros(TA));
	    }
	    else if(TA==-1)
	    {
	    	for(int i=0;i<extref.size();i++)
	    	{
	    		if(extref.get(i).label.equalsIgnoreCase(temp))
	    		{
	    			TA=0;
	    		}
	    	}
	    	if(TA==0)
	    	{
	    		objectcode.add(t,"000000");
	    	}
	    	if(TA==-1)
	    	{
	    		System.out.println("Label not found!!");
	    	    System.exit(0);
	    	}
	    }
	    
	    }
    
	   else if(temp.charAt(0)=='x'||temp.charAt(0)=='X')
   {String x1="000000";
   temp=temp.replaceFirst("x","");
   temp=temp.replaceAll("X","");
   temp=temp.replaceAll(Pattern.quote("'") ,"");
   String conc=x1.concat(temp);
      conc=conc.substring(conc.length()-6, conc.length());
      objectcode.add(t,conc);
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
    objectcode.add(t, o); 
    }
    else
    {
     objectcode.add(t,o);
    }
          
          }
      else {int x=Integer.parseInt(temp);
   String y=Integer.toHexString(x); 
   String x1="000000"; 
   String conc=x1.concat(temp);
   conc=conc.substring(conc.length()-6, conc.length());
      objectcode.add(t,conc);
   
       
      }
    }else if(Sourcecode.get(t).hexa.equalsIgnoreCase("RR"))
    {
    }
   }
  }
  //---------------------------------------------------------------------------------------------
  HTME g=new HTME();
  try{
	  File file = new File ("HTMERecords.txt");
	  BufferedWriter out = new BufferedWriter(new FileWriter(file));
	  for(int i=0;i<=currentcsect;i++)
   {
		  g.run(i,out);
		  
   }
	  out.close();
	  }catch(IOException e)
       {}

       
  try{
	  PrintWriter writer=new PrintWriter("OBJECTCODES.txt","UTF-8");
  
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
		return objectcode;
	}
	}