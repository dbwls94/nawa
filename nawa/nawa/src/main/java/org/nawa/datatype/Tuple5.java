package org.nawa.datatype;

public class Tuple5<V1, V2, V3, V4, V5> extends Tuple4<V1, V2, V3, V4>{
	private V5 value5=null;
	
	public Tuple5(V1 value1, V2 value2, V3 value3, V4 value4, V5 value5) {
		super(value1, value2, value3, value4);
		this.value5=value5;
	} //INIT

	public V5 getV5(){
		return this.value5;
	} //getV5
} //class