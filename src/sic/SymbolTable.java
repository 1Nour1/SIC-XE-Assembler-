package sic;

public class SymbolTable {
String label;
int address;
int csection;
char AR;
SymbolTable(String label,int add,int csection,char AR)
{this.AR=AR;
 this.label=label;
 this.address=add;
 this.csection=csection;
 
}
}