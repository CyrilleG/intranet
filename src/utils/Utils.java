package utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class Utils {

	// Convert an object to a byte array
	public static byte[] ObjectToByteArray(Object obj) throws IOException
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = new ObjectOutputStream(bos);   
		out.writeObject(obj);
		return bos.toByteArray();
	}
	// Convert a byte array to an Object
	public static Object ByteArrayToObject(byte[] arrBytes) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream bis = new ByteArrayInputStream(arrBytes);
		ObjectInput in = new ObjectInputStream(bis);
		Object o = in.readObject();
		bis.close();
		in.close();
		return o;
	}
	 
}
