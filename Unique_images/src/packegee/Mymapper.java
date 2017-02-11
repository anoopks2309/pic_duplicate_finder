package packegee;



import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Mymapper extends Mapper<Text, BytesWritable, Text, Text> {

	public void map(Text key, BytesWritable value, Context context) throws IOException, InterruptedException {

		String md5Str;
		try {
			
			md5Str = calculateMd5(value.getBytes());
			
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
			return;

		}
		Text md5Text = new Text(md5Str);
		context.write(md5Text, key);
	}

	static String calculateMd5(byte[] imageData) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(imageData);
		byte[] hash = md.digest();
		String hexString = new String();
		for (int i = 0; i < hash.length; i++){
			
			hexString += Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1);
			
		}
		return hexString;
	}

}
