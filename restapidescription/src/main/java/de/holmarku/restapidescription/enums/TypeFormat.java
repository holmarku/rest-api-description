package de.holmarku.restapidescription.enums;

public enum TypeFormat {

	Integer_short,	//signed 32 bits
	Integer_long,	//signed 64 bits (a.k.a long)
	Number_float,	
	Number_double,	
	String,		
	String_byte,	//base64 encoded characters
	String_binary,	//any sequence of octets
	Boolean_value,		
	String_date,	//As defined by full-date - RFC3339
	String_date_time,	//As defined by date-time - RFC3339
	String_password
}
