package assembler;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.regex.Pattern;

public class Main {
	static int progLength;
	static int startFlag=0;
	static String progName;
	static int count=0;
	static int symindex=0;
	static String label;
	static String opcode;
	static String Basereg,Xreg;
	static int currentFormat,dispfrom5;
	static String strInstruction;
	static String startAddress = null;
	static int startAddressDecimal;
	static String operand;
	static int currentOperandMode = 0;
	static boolean base=false;
	static boolean startendcheck=false;
	private static ArrayList <Integer> LocationCounter= new ArrayList<Integer>();
	private static ArrayList<InstructionSet> InstrSet= new ArrayList<InstructionSet>();
	protected static ArrayList<SymTable> symboltab= new ArrayList<SymTable>();
	static ArrayList<RegisterTable> Registers= new ArrayList<RegisterTable>();
	protected static ArrayList<Instruction> sourcecode= new ArrayList<Instruction>();
	
	private static int isValidInstruction(String mnemonic){
		if(mnemonic.charAt(0)== '+'){
			currentFormat = 4;
			mnemonic = mnemonic.substring(1, mnemonic.length());
		}
		for(int i = 0 ; i < InstrSet.size() ; i++){
			if(mnemonic.compareToIgnoreCase(InstrSet.get(i).name)== 0 ){
				return i;
			}
		}
		return -1;
	}
	
	private static void addToList(int i,int loc){
		
		sourcecode.add(new Instruction(InstrSet.get(isValidInstruction(opcode)).hexa,operand,label,currentFormat,LocationCounter.get(loc-1)));
		
	}
	
	private static void checkStart(){
		String start="START";
		//if this is the start of the program
		int comp=start.compareToIgnoreCase(opcode);
		if(comp==0)
		{
		  startAddress = operand;
		  startFlag+=1;
		  progName = label;
	    }
		
		if(startFlag == 0){
			//exit
			System.out.println( "Start is not the first instruction");
			System.exit(0);
		}
		if(startFlag == 2){
			//exit
			System.out.println( "Start is repeated");
			System.exit(0);
		}
	}
	private static void setOperandMode(String str){
		//check @ # 
		if(str.charAt(16) == '#'){
			currentOperandMode = 1;
			System.out.println( "# flag" + " "+ currentOperandMode);
		}
		if(str.charAt(16) == '@'){
			currentOperandMode = 2;
			System.out.println( "@ flag" + " "+ currentOperandMode);
		}
	}
	private static void operandError( String operand,int format){
		checkFormat1( operand, format);
		checkFormat2( operand, format);
		checkFormat3Or4(operand, format);
	}
	 protected static int searchRegister(String reg){
	  	   for(int i = 0;i<(Registers.size());i++){
	  		   if((Registers.get(i).reg).compareToIgnoreCase(reg) == 0){
	  			   System.out.println("found register");
	  			   return Registers.get(i).number;
	  		   }
	  	   }
	  	   return -1;
	     }
	private static void checkFormat1(String operand,int format){
		if((format == 1 && (operand != null ))){
			//exit
			operand.replaceAll(" ","");
			
			if(operand==null)
			{
				System.out.println( "format 1 error!!");
				System.exit(0);
			}
		}
		
	}
	
	private static void checkFormat2(String operand,int format){
		//more than one ','
		int indexComma = 0;
		int found =0;
		int found2 = 0;
		if(format==2 && operand != null)
		{
			spaceErrorInLabel(operand);
			operand = operand.replace(" " , "");
		    int commaNum=0;
		    
			for(int i=0;i<operand.length();i++){
				if(operand.charAt(i)== ','){
	                indexComma = i;
					commaNum++;
			    }
			}
		if(commaNum>1){
			System.out.println( "format 2 error");
			System.exit(0);
			//exit
		}
		else if(commaNum == 0){
			 found = searchRegister(operand);
			}
		
	    else {
	    	
			String reg1,reg2;
			reg1 = operand.substring(0,indexComma);
			reg2 = operand.substring(commaNum+1,operand.length());
			//missing : add them here to the struct in the list
		    found = searchRegister(reg1);
		    found2 = searchRegister(reg2);
		    
		    System.out.println(commaNum);
		    System.out.println( indexComma);
		    System.out.println( reg1 +","+ reg2);
		    System.out.println( found +","+ found2);
		    
		}
		}
				
		
		
	}
		
	
	private static void checkFormat3Or4(String operand, int format){
		String reg1,reg2;
		if(format==3 && operand != null){
			if(operand.charAt(0)=='#'){
				operand = operand.substring(1, operand.length());
			}
			operand = operand.replace(" " , "");
			
			int commaNum=0;
		    int indexComma=0;
		    
			for(int i=0;i<operand.length();i++){
				if(operand.charAt(i)== ','){
	                indexComma = i;
					commaNum++;
			    }
			}
			if(commaNum>1){
			System.out.println( "format 3 error, more than 1 comma");
			System.exit(0);
			//exit
			}
			if(commaNum == 0){
				reg1 = operand;
			}
			if(commaNum == 1)
		          	{
				
				reg1 = operand.substring(0,indexComma);
				String X = operand.substring(indexComma+1, operand.length());
				if(X.compareToIgnoreCase("x") != 0){
					//exit
					System.out.println( "index error");
					System.exit(0);
				}
				reg2 = "x";
				//missing : add them here to the struct in the list
			    System.out.println( reg1 +","+ reg2);

			}
		}
	}
	

	private static void getOperand(String str)
	{
		
		int flagBound=0;
		label = null;
		opcode = null;
		operand = null;

		label = strInstruction.substring(0,8);
		spaceErrorInLabel(label);
		label = label.replaceAll(" " , "");
		if(label.compareToIgnoreCase("")==0)
		{
			label=null;
		}
		if(str.length() < 14){
			flagBound = 1;
			opcode=str.substring(9,str.length());
			System.out.println( opcode);
			if(str.length()-9!=0){
				opcode = opcode.replace(" " , "");
			}
		}
		else {
			opcode = strInstruction.substring(9, 14);
			opcode = opcode.replaceAll(" " , "");
		}
		
		checkStart();
		
		if(flagBound == 0){
			setOperandMode( str);
			if(str.length() < 36){
				operand=str.substring(17,str.length());
				if(str.length()-17==0){
					operand = null;
				}
			}
			else {
				operand=str.substring(17,35);
				operand = operand.replace(" " , "");
			}
		}
		
		int i = isValidInstruction(opcode.replace("+",""));
		if(i==-1){
			//exit
			System.out.println( "inValid opcode");
		}
		  currentFormat=getformat(opcode);
		  operandError(operand,InstrSet.get(i).format);
		  if(opcode.compareToIgnoreCase("LDB")==0)
		  {
			  Basereg=operand;
		  }else if(opcode.compareToIgnoreCase("LDX")==0)
		  {
			  Xreg=operand;
		  }
}
	
	
	
	
	private static String getLength(String lastAddress, String startAddress){
		
		int lastAddressInt = Integer.parseInt(lastAddress,16);
		int startAddressInt = Integer.parseInt(startAddress,16);
		int length = lastAddressInt - startAddressInt;
		return ( Integer.toHexString(length));
	}
	
	

	private static int ASCIItoDEC(String str)
	{
		int sum=0;
		String temp=str.replaceAll(" ","");		
		sum=Integer.parseInt(temp);
		return sum;
	}
	private static boolean spaceErrorInLabel(String label){
		int flag = 0;
		char c = label.charAt(0);
		if(!(Character.isLetter(c)) &&  c != ' '){
			
			System.out.println("Error in first char of label");
			System.exit(0);
		}
			
			for(int i =1 ; i < label.length() ; i ++){
				if((label.charAt(i)) == ' '){
					flag = 1;
				}
				if(flag == 1 && label.charAt(i)!= ' '){
					
					  System.out.println("Error in Label!!!");
					  System.exit(0);
					  return false;
				}
		}
		return true;
	}
	
	private static int getformat(String stopcode)
    { 
    	int comp;
    	String temp;
    	stopcode=stopcode.replaceAll(" ", "");
    	if(stopcode.charAt(0)=='+')
    	{
    		return 4;
    	}
    	else{
    	for(int i =0;i<InstrSet.size();i++)
    	{
    		comp=stopcode.compareToIgnoreCase(InstrSet.get(i).name);
    		if(comp==0)
    		{
    			if(InstrSet.get(i).format==5)
    			{
    				
    				if("BYTE".compareToIgnoreCase(InstrSet.get(i).name)==0)
    				{
    					temp=operand;
    					temp=temp.replaceFirst("C","");
    					temp=temp.replaceFirst("c","");
    					temp=temp.replaceAll(Pattern.quote("’") ,"");
    					temp=temp.replaceAll(" ", "");
						char c=operand.charAt(0);
    					if(operand.charAt(0)=='c' || operand.charAt(0)=='C' )
    					{
    						dispfrom5= temp.length();    						
    					}else if(operand.charAt(0)=='x' || operand.charAt(0)=='X' )
    					{
    						if(temp.length()%2==1){
    							dispfrom5= (temp.length()/2)+1;
    						}else{dispfrom5= temp.length()/2;}
    					}
    					//possible error if number>255 decimal
    					else if(Character.isDigit(c)==true)
    					{
    						dispfrom5= 1;
    					}
    				}else if("WORD".compareToIgnoreCase(InstrSet.get(i).name)==0)
    				{
    					dispfrom5= 3;
    				}else if("RESB".compareToIgnoreCase(InstrSet.get(i).name)==0)
    				{
    					int sum=ASCIItoDEC(operand); 					
    					dispfrom5= sum;
    				}else if("RESW".compareToIgnoreCase(InstrSet.get(i).name)==0)
    				{
    					int sum=ASCIItoDEC(operand); 					
    					dispfrom5= sum*3;
    				}
    				return 5;
    			}else{
    			      return InstrSet.get(i).format;
    		         }
    		}
    	}
    	}
    	return -1;
    }
	
	public static void main(String[] args)
	{
		
		InstrSet.add(new InstructionSet("ADD",3,"18"));
		InstrSet.add(new InstructionSet("ADDR",2,"90"));
		InstrSet.add(new InstructionSet("AND",3,"40"));
		InstrSet.add(new InstructionSet("CLEAR",2,"B4"));
		InstrSet.add(new InstructionSet("COMP",3,"28"));
		InstrSet.add(new InstructionSet("COMPR",2,"A0"));
		InstrSet.add(new InstructionSet("DIV",3,"24"));
		InstrSet.add(new InstructionSet("FIX",1,"C4"));
		InstrSet.add(new InstructionSet("J",3,"3C"));
		InstrSet.add(new InstructionSet("JEQ",3,"30"));
		InstrSet.add(new InstructionSet("JSUB",3,"48"));
		InstrSet.add(new InstructionSet("LDA",3,"00"));
		InstrSet.add(new InstructionSet("LDX",3,"04"));
		InstrSet.add(new InstructionSet("LDB",3,"68"));
		InstrSet.add(new InstructionSet("LDCH",3,"50"));
		InstrSet.add(new InstructionSet("LDT",3,"74"));
		InstrSet.add(new InstructionSet("RSUB",3,"4C"));
		InstrSet.add(new InstructionSet("STA",3,"0C"));
		InstrSet.add(new InstructionSet("STCH",3,"54"));
		InstrSet.add(new InstructionSet("STL",3,"14"));
		InstrSet.add(new InstructionSet("STX",3,"10"));
		InstrSet.add(new InstructionSet("SUB",3,"1C"));
		InstrSet.add(new InstructionSet("SUBR",2,"94"));
		InstrSet.add(new InstructionSet("TD",3,"E0"));
		InstrSet.add(new InstructionSet("TIX",3,"2C"));
		InstrSet.add(new InstructionSet("TIXR",2,"B8"));
		InstrSet.add(new InstructionSet("ADD",3,"18"));
		InstrSet.add(new InstructionSet("RD",3,"D8"));
		InstrSet.add(new InstructionSet("START",0,"000000"));
		InstrSet.add(new InstructionSet("BASE",0,"000000"));
		InstrSet.add(new InstructionSet("END",0,"000000"));
		InstrSet.add(new InstructionSet("BYTE",5,"gg"));
		InstrSet.add(new InstructionSet("WORD",5,"hh"));
		InstrSet.add(new InstructionSet("RESB",5,"ii"));
		InstrSet.add(new InstructionSet("RESW",5,"ii"));
		Registers.add(new RegisterTable("A",0));
		Registers.add(new RegisterTable("X",1));
		Registers.add(new RegisterTable("L",2));
		Registers.add(new RegisterTable("PC",8));
		Registers.add(new RegisterTable("SW",9));
		Registers.add(new RegisterTable("B",3));
		Registers.add(new RegisterTable("S",4));
		Registers.add(new RegisterTable("T",5));
		Registers.add(new RegisterTable("F",6));
		int y=0;

		try(BufferedReader br = new BufferedReader (new FileReader("C:\\test\\Document.txt"))){
			
			while((strInstruction = br.readLine())!=null)
			{
				 int x = 0;
				 
				if(strInstruction.charAt(0)!='.')
				{
				getOperand(strInstruction);
				if(opcode.compareToIgnoreCase("BASE")==0)
			     {
			    	 base=true;
			     }else{base=false;}
				if(opcode.compareToIgnoreCase("START")==0||opcode.compareToIgnoreCase("END")==0)
				{
					startendcheck=true;
				}else{startendcheck=false;}
				     if(count==0)
				        {
				    	  startAddress=operand;
				    	  startAddress=startAddress.replaceAll(" ", "");
					      LocationCounter.add(0,Integer.parseInt(startAddress,16));
					      count++;
					      LocationCounter.add(1,Integer.parseInt(startAddress,16));
					      progName=label;
				         }
				     else{if(currentFormat!=5)
				     {
			        	 x=currentFormat;        	 
				     }else if(currentFormat==5)
				     {
				    	 x=dispfrom5;
				     }
				        	  if(x!=-1)
				            {
				        		  
				                LocationCounter.add(count ,LocationCounter.get(count-1)+x );
				           
				             }
				         }
				     if(startendcheck==false&&base==false)
				     {opcode=opcode.replace("+","");
				     addToList(count-1,count);
				     }
				     if(label!=null)
				     {
				    	symboltab.add(symindex,new SymTable(label,LocationCounter.get(count-1)));
				    	symindex++;
				     }
				     
						count++;
						y++;
				
			    }
				else{System.out.println("this is a comment");}
			}
	//---------------------------------------------------------------------------------------------------------
			progLength=LocationCounter.get(count-1)-LocationCounter.get(0);
			for(int i=0;i<symboltab.size();i++)
			{
				System.out.println("label:"+symboltab.get(i).label+"   sym address:"+symboltab.get(i).address);
			}
			for(int i=0;i<sourcecode.size();i++)
			{
				System.out.println(" opcode: "+sourcecode.get(i).hexa+" operand "+sourcecode.get(i).operand+" label "+sourcecode.get(i).label+" loc "+sourcecode.get(i).address);
			}
			Pass2 a = new Pass2();
			a.pass2test();
			System.out.println("program name :"+progName+"    Program length:"+progLength);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		 


	}


	}