package assembler;

import java.util.regex.Pattern;

import javax.xml.bind.DatatypeConverter;

public class SymTable {
String label;
int address;
SymTable(String label,int add)
{
	this.label=label;
	this.address=add;
}
}