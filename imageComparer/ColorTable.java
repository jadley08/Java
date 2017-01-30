package p1;

import java.awt.Color;
import java.util.Arrays;
//import java.util.Random;

/**
 * @author Jacob Adley
 * 
 * A ColorTable represents a dictionary of frequency counts, keyed on Color.
 * It is a simplification of Map<Color, Integer>. The size of the key space
 * can be reduced by limiting each Color to a certain number of bits per channel.
 */

/**
 * TODO
 * 
 * Implement this class, including whatever data members you need and all of the
 * public methods below. You may create any number of private methods if you find
 * them to be helpful. Replace all TODO comments with appropriate javadoc style 
 * comments. Be sure to document all data fields and helper methods you define.
 */

public class ColorTable {
  /**
   * Counts the number of collisions during an operation.
   */
  private static int numCollisions = 0;
  
  private int bitsPerChannel;
  
  private int collisionStrategy;
  
  private double rehashThreshold;
  
  private ColorMap[] colorMap; //this is the array that holds all of the colors and their frequencies
  
  private int colorCount = 0; //this is a counter for the number of elements in the array
  
  /**
   * Returns the number of collisions that occurred during the most recent get or
   * put operation.
   */
  public static int getNumCollisions() {
    return numCollisions;
  }
  
  /**
   * This class is a class containing a pair of int and long
   * int corresponds to the code for a color obtained from Util.pack
   * long corresponds to the frequency of that paired color
   */
  class ColorMap {
	  int color;
	  long frequency;
	  
	  ColorMap(int color, long frequency) {
		  if (color < 0) {
			  throw new RuntimeException("color cannot be a negative number");
		  }
		  if (frequency < 0) {
			  throw new RuntimeException("frequency cannot be a negative number");
		  }
		  this.color = color;
		  this.frequency = frequency;
	  }
	  
	  /**
	   * returns the int color code for the ColorMap object
	   */
	  int getColor() {
		  return this.color;
	  }
	  
	  /**
	   * returns the long frequency for the ColorMap object
	   */
	  long getFrequency() {
		  return this.frequency;
	  }
	  
	  /**
	   * when called, increases the frequency variable pertianing to
	   * the ColorMap object by 1
	   */
	  void increment() {
		  this.frequency += 1;
	  }
  }

  /**
   * TODO
   * 
   * Constructs a color table with a starting capacity of initialCapacity. Keys in
   * the color key space are truncated to bitsPerChannel bits. The collision resolution
   * strategy is specified by passing either Constants.LINEAR or Constants.QUADRATIC for
   * the collisionStrategy parameter. The rehashThrehold specifies the maximum tolerable load 
   * factor before triggering a rehash.
   * 
   * @throws RuntimeException if initialCapacity is not in the range [1..Constants.MAX_CAPACITY]
   * @throws RuntimeException if bitsPerChannel is not in the range [1..8]
   * @throws RuntimeException if collisionStrategy is not one of Constants.LINEAR or Constants.QUADRATIC
   * @throws RuntimeException if rehashThreshold is not in the range (0.0..1.0] for a
   *                             linear strategy or (0.0..0.5) for a quadratic strategy
   */
  public ColorTable(int initialCapacity, int bitsPerChannel, int collisionStrategy, double rehashThreshold) { 
	  //checking errors:
	  if (initialCapacity < 1 || initialCapacity > Constants.MAX_CAPACITY) {
		  throw new RuntimeException("initialCapacity not within bounds [1..Constants.MAX_CAPACITY : " + initialCapacity);
	  }
	  if (bitsPerChannel < 1 || bitsPerChannel > 8) {
		  throw new RuntimeException("bitsPerChannel is not within bounds [1..8] : " + bitsPerChannel);
	  }
	  if (collisionStrategy != Constants.LINEAR && collisionStrategy != Constants.QUADRATIC) {
		  throw new RuntimeException("collision strategy is neither Coonstants.LINEAR or QUADRATIC : " + collisionStrategy);
	  }
	  if ((collisionStrategy == Constants.LINEAR) && (rehashThreshold <= 0.0 || rehashThreshold > 1.0)) {
		  throw new RuntimeException("collisionStrategy is LINEAR but rehashThreshold is not withing range (0.0..1.0] : " + rehashThreshold);
	  }
	  if ((collisionStrategy == Constants.QUADRATIC) && (rehashThreshold <= 0.0 || rehashThreshold >= 0.5)) {
		  throw new RuntimeException("collisionStrategy is QUADRATIC but rehashThreshold is not withing range (0.0..0.5) : " + rehashThreshold);
	  }
	  
	  //setting variables:
	  this.colorMap = new ColorMap[initialCapacity]; //sets the starting size of the array equal to initialCapacity
	  this.bitsPerChannel = bitsPerChannel;
	  this.collisionStrategy = collisionStrategy;
	  this.rehashThreshold = rehashThreshold;
  }

  /**
   * TODO
   * 
   * Returns the number of bits per channel used by the colors in this table.
   */
  public int getBitsPerChannel() {
    return bitsPerChannel;
  }
  
  /**
   * According to either collisionStrategy LINEAR or QUADRATIC
   * the method will take a given int (read @param) and will
   * find either the first place that is empty according to that strategy
   * or it will find where that particular color is already present
   * that location is the @return index.
   * 
   * @param int key - color code for a given color obtained from Util.pack
   * @return int - returns the index at which the color belongs
   */
  
  private int lookup(int key) {
	  if (key < 0) {
		  throw new RuntimeException("colorKeyCode is < 0 : " + key);
	  }
	  numCollisions = 0; //sets numCollisions to 0 so it can count in this function
	  
	  int index = key % getCapacity(); //take the given color code and modulus it by the size to get the hashCode
	  int originIndex = index; //keeping track of what the original index was
	  int n = 1; //this is the initialization of the counter for the search algorithms
	  
	  while (true) { //runs continuously until broken
		  while (index >= getCapacity()) { //checks to see if the index ever gets larger than possible given the size of the array
			  index = index - getCapacity();// - 1; //NOT SUPPOSED TO SUBTRACT 1?
		  }
		  if (colorMap[index] == null) { //if the space is null then that index is returned
			  break;
		  }
		  else if (colorMap[index].getColor() == key) {//if the given color is the same as the current position that index is returned
			  break;
		  }
		  else { //if the correct spot is not found:
			  if (collisionStrategy == Constants.LINEAR) {
				  index = originIndex + n; //linear probing algorithm
			  }
			  else if (collisionStrategy == Constants.QUADRATIC) {
				  index = originIndex + (n * n); //quadratic probing algorithm
			  }
			  //if the correct index is not found then must increment:
			  n++;
			  numCollisions++;
		  }
	  }
	  return index;
  }
  
  /**
   * TODO
   * 
   * Returns the frequency count associated with color. Note that colors not
   * explicitly represented in the table are assumed to be present with a
   * count of zero. Uses Util.pack() as the hash function.
   */
  public long get(Color color) {
	  int colorCode = Util.pack(color, bitsPerChannel); //get the color code for the given color
	  int index = lookup(colorCode); //find the index for the color code
	  if (colorMap[index] != null) { //only returns frequency if colorMap exists, else 0
		  return colorMap[index].getFrequency();
	  }
	  else {
		  return 0;
	  }
  }

  /**
   * TODO
   * 
   * Associates the count with the color in this table. Do nothing if count is less than
   * or equal to zero. Uses Util.pack() as the hash function.
   */
  public void put(Color color, long count) {
	  if (count < 0) {
		  throw new RuntimeException("frequency of a color cannot be set as < 0 : " + count);
	  }
	  else if (count > 0) {
		  //colorCount++;
		  /*
		   * this should be how it works, it shouldn't rehash if the thing being put in is just replacing
		   * something that isn't null, but tests fail when I switched it.
		   */
		  int colorCode = Util.pack(color, getBitsPerChannel()); //gets the numerical code for the color
		  int index = lookup(colorCode);
		  if (colorMap[index] == null) {
			  colorCount++; //only increment the count if you are not replacing a colorMap
		  }
		  
		  //having this as a while loop could be slow but much simpler than other solutions
		  while (getLoadFactor() >= rehashThreshold) { //while the load factor exceeds the threshold
			  colorCount = 1; //set to 1 because all are re-put in and also the current one (1)
			  rehash();
		  }
		  
		  index = lookup(colorCode); //finds the location to put the ColorMap in colorMap
		  /*
		  if (colorMap[index] != null) {
			  colorCount--;
		  }*/
		  colorMap[index] = new ColorMap(colorCode, count); //sets the ColorMap in colorMap
	  }
  }
		  
  /**
   * TODO
   * 
   * Increments the frequency count associated with color. Note that colors not
   * explicitly represented in the table are assumed to be present with a
   * count of zero.
   */
  public void increment(Color color) {
	  int packedColor = Util.pack(color, getBitsPerChannel()); //set the color code
	  int index = lookup(packedColor); //find the location for the color in the array
	  if (colorMap[index] == null) { //if there is nothing at the index then put it there with frequency = 1
		  put(color, 1);
	  }
	  else { //else it must already be there so call ColorMap's increment function on the object at the index
		  colorMap[index].increment();
	  }
  }

  /**
   * TODO
   * 
   * Returns the load factor for this table.
   */
  public double getLoadFactor() {
	  return (double)getSize() / (double)getCapacity();
  }

  /**
   * TODO
   * 
   * Returns the size of the internal array representing this table.
   */
  public int getCapacity() {
    return colorMap.length;
  }

  /**
   * TODO
   * 
   * Returns the number of key/value associations in this table.
   */
  public int getSize() {
    return colorCount;
  }

  /**
   * TODO
   * 
   * Returns true iff this table is empty.
   */
  public boolean isEmpty() {
    if (getSize() == 0) { return true; }
    else { return false; }
  }

  /**
   * TODO
   * 
   * Increases the size of the array to the smallest prime greater than double the 
   * current size that is of the form 4j + 3, and then moves all the key/value 
   * associations into the new array. 
   * 
   * Hints: 
   * -- Make use of Util.isPrime().
   * -- Multiplying a positive integer n by 2 could result in a negative number,
   *    corresponding to integer overflow. You should detect this possibility and
   *    crop the new size to Constants.MAX_CAPACITY.
   * 
   * @throws RuntimeException if the table is already at maximum capacity.
   */
  private void rehash() {
	  ColorMap[] tempArr = Arrays.copyOf(colorMap, colorMap.length); //create a copy of colorMap array
	  int tempLength = getCapacity() * 2; //int variable set to double the current size of colorMap
	  while ((!Util.isPrime(tempLength)) || !(((tempLength - 3) % 4) == 0)) { //make sure the tableSize is valid
		  tempLength += 1;
	  }
	  if (tempLength > Constants.MAX_CAPACITY) { //make sure the length is within bounds
		  tempLength = Constants.MAX_CAPACITY;
	  }
	  colorMap = new ColorMap[tempLength]; //set colorMap to the new correct size
	  for (ColorMap arr : tempArr) { //go through every element in the temporary array and if they are not null put them in colorMap
		  if (arr != null) {
			  put(Util.unpack((int)arr.getColor(), getBitsPerChannel()), (int)arr.getFrequency());
		  }
	  }
  }

  /**
   * TODO
   * 
   * Returns an Iterator that marches through each color in the key color space and
   * returns the sequence of frequency counts.
   */
  public Iterator iterator() { //NOT SUPPOSED TO BE STATIC?
	  class FrequencyIterator<ColorMap> implements Iterator {
		  /*
		  private int index = 0;
		  
		  @Override
		  public boolean hasNext() {
			  return index < getCapacity();
		  }
		  
		  @Override
		  public long next() {
			  if (colorMap[index] != null) {
				  return colorMap[index++].getFrequency();
			  }
			  else {
				  index++;
				  return 0;
			  }
			  
		  }*/
		  //new Color(r, g, b);
		  //int index = 0;
		  int bitSize = getBitsPerChannel();
		  int incrementer = (int)Math.pow(2.0, (8.0 - bitSize));
		  int size = 256;
		  
		  int redCounter = 0;
		  int greenCounter = 0;
		  int blueCounter = 0;
		  
		  @Override
		  public boolean hasNext() {
			  if (redCounter < size) {// && greenCounter < size && blueCounter < size) {
				  return true;
			  }
			  else {
				  return false;
			  }
		  }
		  
		  @Override
		  public long next() {
			  Color color = new Color(redCounter, greenCounter, blueCounter);
			  int index = lookup(Util.pack(color, bitSize));
			  
			  blueCounter += incrementer;
			  if (blueCounter >= size) {
				  blueCounter = 0;
				  greenCounter += incrementer;
				  if (greenCounter >= size) {
					  greenCounter = 0;
					  redCounter += incrementer;
				  }
			  }
			  
			  if (colorMap[index] != null) {
				  return colorMap[index].getFrequency();
			  }
			  else {
				  return 0;
			  }
		  }
		  
	  }
    return new FrequencyIterator();
  }

  /**
   * TODO
   * 
   * Returns a String representation of this table.
   * just a simple toString method, returning index: colorCode, frequency
   */
  public String toString() {
    String str = "[ ";
    for (int i = 0; i < colorMap.length; i++) {
    	if (colorMap[i] != null) {
    		if (!(str.length() <= 3)) {
    			str += ", ";
    		}
    		str += "(" + i + ": " + colorMap[i].getColor() + ", " + colorMap[i].getFrequency() + ")";
    	}
    }
    str += " ]";
    return str;
  }

  /**
   * TODO
   * 
   * Returns the count in the table at index i in the array representing the table.
   * The sole purpose of this function is to aid in writing the unit tests.
   */
  public long getCountAt(int i) {
	  if (colorMap[i] != null) { //checks to make sure the colorMap at the index exists
		  return colorMap[i].getFrequency();
	  }
	  else {
		  return 0;
	  }
  }

  /**
   * Simple testing.
   */
  public static void main(String[] args) {
	  //System.out.println(Util.pack(Color.RED, 6));
    ColorTable table = new ColorTable(3, 6, Constants.QUADRATIC, .49);
    int[] data = new int[] { 32960, 4293315, 99011, 296390 };
    for (int i = 0; i < data.length; i++) {
      table.increment(new Color(data[i]));
    }
    System.out.println("capacity: " + table.getCapacity()); // Expected: 7
    System.out.println("size: " + table.getSize());         // Expected: 3
    
    /* The following automatically calls table.toString().
       Notice that we only include non-zero counts in the String representation.
       
       Expected: [3:2096,2, 5:67632,1, 6:6257,1]
       
       This shows that there are 3 keys in the table. They are at positions 3, 5, and 6.
       Their color codes are 2096, 67632, and 6257. The code 2096 was incremented twice.
       You do not have to mimic this format exactly, but strive for something compact
       and readable.
       */
    System.out.println(table);
    /**
     * result:
     * capacity: 7
	 * size: 3
	 * [ (3: 2096, 2), (5: 67632, 1), (6: 6257, 1) ]
	 * this is consistent with expected result
     */
    //table.increment(Color.GRAY);
    System.out.println("capacity: " + table.getCapacity()); // Expected: 7
    System.out.println("size: " + table.getSize()); 
    System.out.println(table);
    
    table.increment(Color.BLACK);
    table.increment(Color.BLUE);
    table.increment(Color.WHITE);
    table.increment(Color.CYAN);
    table.increment(Color.DARK_GRAY);
    table.increment(Color.GREEN);
    System.out.println("capacity: " + table.getCapacity()); // Expected: 7
    System.out.println("size: " + table.getSize()); 
    System.out.println(table);
    /*
    Iterator freqIter = iterator();
    while (freqIter.hasNext()) {
    	System.out.println(freqIter.next());
    }*/
  }
}
