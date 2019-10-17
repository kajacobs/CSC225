/* HuffEncoder.java

   Starter code for compressed file encoder. You do not have to use this
   program as a starting point if you don't want to, but your implementation
   must have the same command line interface. Do not modify the HuffFileReader
   or HuffFileWriter classes (provided separately).

   B. Bird - 03/19/2019
   Katherine Jacobs
   V00783178
   Last Modified: July 6/2019
*/

import java.io.*;
import java.util.*;



 // HuffNode class and HuffComparator created in lab session
class HuffNode implements Comparable<HuffNode>{
    byte character;
    int frequency;

    HuffNode left;
    HuffNode right;

    public HuffNode(){
        this.left = null;
        this.right = null;
    }

    public HuffNode(int freq, byte value){
        this.frequency = freq;
        this.character = value;

        this.left = null;
        this.right = null;
    }

    public int compareTo(HuffNode other) {
        return this.frequency - other.frequency;
    }
} // end of HuffNode class



public class HuffEncoder{

    private BufferedInputStream inputFile;
    private HuffFileWriter outputWriter;
    int[][] converter = new int[256][];

    public HuffEncoder(String inputFilename, String outputFilename) throws FileNotFoundException {
        inputFile = new BufferedInputStream(new FileInputStream(inputFilename));
        outputWriter = new HuffFileWriter(outputFilename);
    }


    public void encode() throws IOException{
        LinkedList<Byte> input_bytes = new LinkedList<Byte>();
        for(int nextByte = inputFile.read(); nextByte != -1; nextByte = inputFile.read()){
            input_bytes.add((byte)nextByte);
        }

        //converting each byte into its unsigned int, and incrementing
        //the index for that byte.
        // Found loop on stackoverflow, modified it to fit my code
        //https://stackoverflow.com/questions/46949759/converting-an-array-of-signed-bytes-to-unsigned-bytes
        int[] byte_frequencies = new int[256];
        //creating second linked list for later use
        int inputsize1 = input_bytes.size();
        LinkedList<Byte> input_bytes2 = new LinkedList<Byte>();
        for (int i = 0; i < inputsize1; i++) {
            input_bytes2.add(input_bytes.getFirst());
            byte_frequencies[input_bytes.pollFirst() & 0xFF]++;
        }
        PriorityQueue<HuffNode> pq = new PriorityQueue<HuffNode>();
        //Printinig frequency

        LinkedList<HuffNode> ordered_ints = new LinkedList<HuffNode>();

        for (int i=0; i<256; i++){
            if (byte_frequencies[i] != 0){
                HuffNode node = new HuffNode(byte_frequencies[i], (byte)i);
                pq.add(node);
            }
        }

        HuffNode root = null;
        while (pq.size() > 1){
            root = new HuffNode();
            root.left = pq.poll();
            root.right = pq.poll();
            root.frequency = root.left.frequency + root.right.frequency;
            pq.add(root);
        }

        symbolCreator(root, new LinkedList<Integer>());
        outputWriter.finalizeSymbols();

        int[] bits = null;
        int inputsize2 = input_bytes2.size();
        for (int i=0; i < inputsize2; i++){
            bits = converter[input_bytes2.pollFirst() & 0xFF];
            for (int j=0; j < bits.length; j++){
                outputWriter.writeStreamBit(bits[j]);
            }
        }

        outputWriter.close();

    } // end of encode()

    /* This function was taking from StackOverflow to convert List<Integer> to int[]
    *https://stackoverflow.com/questions/718554/how-to-convert-an-arraylist-containing-integers-to-primitive-int-array
    */
    public int[] convertIntegers(List<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = integers.get(i).intValue();
        }
        return ret;
    } // end of convert integers

    /*
    * Recursive algorithm that traverses through nodes and creates
    * HuffFileSymbol objects, and writing them to the symbol table
    */
    public void symbolCreator(HuffNode node, LinkedList<Integer> list){
        if (node == null) {
            return;
        } else if (node.left == null && node.right == null){
            byte[] chr = new byte[1];
            chr[0] = node.character;
            outputWriter.writeSymbol(new HuffFileSymbol(chr, convertIntegers(list)));
            converter[node.character & 0xFF] = convertIntegers(list);
            return;
        } else {
            list.add(1);
            symbolCreator(node.left, list);
            list.removeLast();
            list.add(0);
            symbolCreator(node.right, list);
            list.removeLast();
        }
    } // end of traversal

    public static void main(String[] args) throws IOException{
        if (args.length != 2){
            System.err.println("Usage: java HuffEncoder <input file> <output file>");
            return;
        }
        String inputFilename = args[0];
        String outputFilename = args[1];

        try{
            HuffEncoder encoder = new HuffEncoder(inputFilename, outputFilename);
            encoder.encode();
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: "+e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException: "+e.getMessage());
        }

    } // end of main
} // end of HuffEncoder
