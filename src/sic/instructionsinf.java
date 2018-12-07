package sic;

public class instructionsinf {

	   int address;
	   String hexa;
	   String operand;
	   String label;
	   int csect;
	 int format;
	 public instructionsinf(String hexa, String operand, String label, int format,int loc,int csect) {
	  this.hexa=hexa;
	  this.operand=operand;
	  this.label =label;
	  this.format=format;
	  this.address=loc;
	  this.csect=csect;
	 }
	 

	}