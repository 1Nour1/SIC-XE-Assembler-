package assembler;

public class Instruction {

	  int address;
	  String hexa;
	  String operand;
	  String label;
	  int format;
	public Instruction(String hexa, String operand, String label, int format,int loc) {
		this.hexa=hexa;
		this.operand=operand;
		this.label =label;
		this.format=format;
		this.address=loc;
	}
	

}
