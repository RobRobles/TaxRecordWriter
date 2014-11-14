import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class TaxRecordWriter {

	static DataOutputStream out = null; 
	public int bSize = 0; 
	public int recordSize = 0; 

	public TaxRecordWriter(String fileName, int blockSize) throws IOException 
	{	 
		out = new DataOutputStream(new FileOutputStream(fileName));
		bSize = blockSize; 
	}

	public void WriteRecord(int ssn, String name, int income, String state) throws IOException
	{

		int tempSSN = ssn; 
		String tempName = name; 
		int tempIncome = income; 
		String tempState = state; 

		//size of the string name 
		int nameSize = 0;
		byte[] ssnByteSize = tempName.getBytes("UTF-8");
		nameSize = ssnByteSize.length;

		//size of the string state 
		int stateSize = 0;
		byte [] stateByteSize = tempState.getBytes("UTF-8");
		stateSize = stateByteSize.length;


		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		//getting the size of the idividual record(adding size of ssn income name and state)
		recordSize = nameSize + stateSize + Integer.bitCount(tempSSN) + Integer.bitCount(tempIncome);
		//System.out.println("recordSize: " + recordSize + " bSize: " + bSize);

		//block handling, checking for fits, first we need to consider writing it to the block

		//checking to see if it will actually fit in the remaining space. 
		if((bSize - recordSize) >= 4)
		{
			int tempBuffer = 0; 
			//adjusting the blockSize
			bSize = bSize - recordSize; 
			//System.out.println("bSize: " + (bSize));

			//write the record to the block 
			out.writeInt(tempBuffer);
			out.writeInt(tempSSN);
			out.writeInt(nameSize);
			out.writeChars(tempName);
			out.writeInt(tempIncome);
			out.writeInt(stateSize);
			out.writeChars(tempState);
			//writing a tempBuffer to help me read them all in later on
			out.writeInt(tempBuffer);
			
			System.out.println("ssn: " + tempSSN + " name: " + tempName + " income: " + tempIncome + " state: " + tempState);
			
		}
		//find the offset of bites and fill them with dummy datato reach the next block 
		else 
		{
			//this is how much garbage text we will write 
			int buffer = Math.abs(bSize - recordSize);
			//I subtract 4 because when I write an Int to out it will take up that much space. I made sure to always have 4 spaces 
			//left for this buffer by checking to see if any given record that is added will leave at least 4 spaces for this buffer. 
			buffer = buffer - 4; 
			out.writeInt(buffer);
			for(int i = 0; i < (buffer); i++)
			{
				out.writeByte(0);
			}

			//now we can write to the next block
			out.writeInt(tempSSN);
			out.writeInt(nameSize);
			out.writeChars(tempName);
			out.writeInt(tempIncome);
			out.writeInt(stateSize);
			out.writeChars(tempState);
			out.writeInt(0);
			//System.out.println("You are off by: " + Math.abs(bSize - recordSize) + " ---> " + buffer);
		}



		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	}

	public void Close() throws IOException
	{
		out.close();
	}

	public static void main(String[] args) throws IOException {

		TaxRecordWriter reader = new TaxRecordWriter("OutPut.db", 240); 
		reader.WriteRecord(233455968, "John Smith", 2000000000, "IL");
		reader.WriteRecord(549854234, "Alex David", 65000, "AL");
		reader.WriteRecord(995544332, "Tony Stark", 250000, "CA"); 
		reader.WriteRecord(955586643, "Thor Odinson", 1000000, "AG"); 
		reader.WriteRecord(558699332, "Nick Furry", 2550000, "NM"); 
		reader.WriteRecord(666849439, "Jason Shepherd", 66000, "IA"); 
		reader.WriteRecord(339595849, "Nathan Backman", 65000, "IA");
		reader.Close();

	}
}
