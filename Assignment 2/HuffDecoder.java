/* HuffDecoder.java

   Starter code for compressed file decoder. You do not have to use this
   program as a starting point if you don't want to, but your implementation
   must have the same command line interface. Do not modify the HuffFileReader
   or HuffFileWriter classes (provided separately).

   B. Bird - 03/19/2019
   Katherine Jacobs
   V00783178
   Last Modified: July 4/2019
*/

import java.io.*;
import java.util.*;
import java.lang.*;


public class HuffDecoder{

    private HuffFileReader inputReader;
    private BufferedOutputStream outputFile;

    /* This function was taking from StackOverflow to convert List<Integer> to int[]
    *https://stackoverflow.com/questions/718554/how-to-convert-an-arraylist-containing-integers-to-primitive-int-array
    */

    public static int[] convertIntegers(List<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = integers.get(i).intValue();
        }
        return ret;
    } 

    /* Basic constructor to open input and output files. */
    public HuffDecoder(String inputFilename, String outputFilename) throws FileNotFoundException {
        inputReader = new HuffFileReader(inputFilename);
        outputFile = new BufferedOutputStream(new FileOutputStream(outputFilename));
    }

    public int arrayToInt(int[] bin){
        int sum = 0;
        int sum2 = 0;
        for (int i=1; i < bin.length; i++) {
            sum += Math.pow(2, i);
        }
        for (int i=0; i < bin.length; i++){
            sum2 = sum2*2 + bin[i];
        }
        return sum + sum2;
    }

    public void decode() throws IOException{

        int sum = 0;
        int size = 0;
        ArrayList<HuffFileSymbol> symbols = new ArrayList<HuffFileSymbol>();

        // Find the length of the longest symbol we will need
        for (HuffFileSymbol currsymbol = inputReader.readSymbol(); currsymbol != null; currsymbol = inputReader.readSymbol()){
            if (currsymbol.symbolBits.length > size) size = currsymbol.symbolBits.length;
            symbols.add(currsymbol);
        }

        // Initialize the table with lots of null values
        size = (int)Math.pow(2, size+1);
        ArrayList<byte[]> table = new ArrayList<byte[]>(size);
        for (int i=0; i<size; i++) table.add(null);

        // Add the symbols into the table
        for (int i=0; i<symbols.size(); i++){
            table.set(arrayToInt(symbols.get(i).symbolBits), symbols.get(i).symbol);
        }

        // Iterate through the coded text and decode with the table
        sum = 0;
        byte[] value = null;
        int digit = inputReader.readStreamBit();
        ArrayList<Integer> templist = new ArrayList<Integer>();
        while(digit != -1){
            templist.add(digit);
            sum = arrayToInt(convertIntegers(templist));
            value = table.get(sum);
            if (value != null){
                for (int i=0; i < value.length; i++){
                    outputFile.write(value[i]);
                }
                templist.clear();
                sum = 0;
            }
            digit = inputReader.readStreamBit();
        }
    outputFile.close();
    } // end of decode method


    public static void main(String[] args) throws IOException{
        if (args.length != 2){
            System.err.println("Usage: java HuffDecoder <input file> <output file>");
            return;
        }
        String inputFilename = args[0];
        String outputFilename = args[1];

        try {
            HuffDecoder decoder = new HuffDecoder(inputFilename, outputFilename);
            decoder.decode();
        } catch (FileNotFoundException e) {
            System.err.println("Error: "+e.getMessage());
        }
    }
}
