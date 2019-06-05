import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class SequentialFileManager
{
   private ObjectOutputStream output; // outputs data to file
   private ObjectInputStream input;

   // allow user to specify file name
   public void deleteFile()
   {
	      try // open file
	      {
	         output = new ObjectOutputStream(
	                 new FileOutputStream( "historico.ser" ) );
	      } // end try
	      catch ( IOException ioException )
	      {
	         System.err.println( "Error opening file." );
	      } // end catch
	   } // end method openFile
   
   public void openFileInput()
   {
      try // open file
      {
         input = new ObjectInputStream(
            new FileInputStream( "historico.ser" ) );
      } // end try
      catch ( IOException ioException )
      {
         System.err.println( "Error opening file." );
      } // end catch
   } // end method openFile
   
   public void openFileOutput()
   {
      try // open file
      {
    	  File name = new File("historico.ser");
    	 // System.out.println();
		  if (!name.exists())
          output = new ObjectOutputStream(
            new FileOutputStream( "historico.ser" ) );
      } // end try
      catch (FileNotFoundException e) { // verifica se o arquivo existe, para nao criar um novo
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
      catch ( IOException ioException )
      {
         System.err.println( "Error opening file." );
      } // end catch
   } // end method openFile

   // add records to file
   public void addRecords(ArrayList<ConteudoSerializable> a)
   {
      try // output values to file
      {
    	  
          output.writeObject(a); // output record

      } // end try
      catch ( IOException ioException )
      {
         System.err.println( "Error writing to file." );
         return;
      } // end catch

   } // end method addRecords

   public ArrayList<ConteudoSerializable> readRecords()
   {
	  ArrayList<ConteudoSerializable>  lista = new ArrayList<ConteudoSerializable>();
      //System.out.printf( "%-12s\n", "frase" );
      try // input the values from the file
      { 
         while ( true )
         {
        	lista =  (ArrayList) input.readObject();
            // display record contents
            //System.out.printf( "%-12s\n", record.getFrase() );
         } // end while
      } // end try
      catch ( EOFException endOfFileException )
      {
         return lista; // end of file was reached
         
      } // end catch
      catch ( ClassNotFoundException classNotFoundException )
      {
         System.err.println( "Unable to create object." );
      } // end catch
      catch ( IOException ioException )
      {
         System.err.println( "Error during reading from file." );
      } // end catch
	return lista;
   } // end method readRecords
   
   // close file and terminate application 
   public void closeFileOutput() 
   {
      try // close file
      {
         if ( output != null )
            output.close();
      } // end try
      catch ( IOException ioException )
      {
         System.err.println( "Error closing file." );
         System.exit( 1 );
      } // end catch
   } // end method closeFile
   
   public void closeFileInput()
   {
      try // close file and exit
      {
         if ( input != null )
            input.close();
         System.exit( 0 );
      } // end try
      catch ( IOException ioException )
      {
         System.err.println( "Error closing file." );
         System.exit( 1 );
      } // end catch
   } // end method closeFile
} // end class CreateSequentialFile
