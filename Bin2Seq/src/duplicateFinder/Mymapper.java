package duplicateFinder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Mymapper extends Mapper<LongWritable,Text,Text,BytesWritable >{
	
		public void map(LongWritable key,Text val,Context context) throws IOException, InterruptedException
		{
			
			String uri =  val.toString();
			Configuration conf =  new Configuration();
			FileSystem fs =  FileSystem.get(URI.create(uri),conf);
			FSDataInputStream in = null;
			
			try
			{
				in = fs.open(new Path(uri));
				java.io.ByteArrayOutputStream bout = new ByteArrayOutputStream();
				byte buffer[]  = new byte[1024*1024];
				while(in.read(buffer, 0, buffer.length)>=0)
				{
				
					bout.write(buffer);
					
				}
				context.write(val, new BytesWritable(bout.toByteArray()));
				
				
			}finally
			{  
				IOUtils.closeStream(in);
			
		

	

			}
			
			
		}

}
