package p1;
/**
 * TODO
 * @author Jacob Adley
 */

public class Driver {
  
  private static int numCollisions;
  
  /**
   * TODO
   * 
   * Return the ColorTable associated with this image, assuming the color key space
   * is restricted to bitsPerChannel. Increment numCollisions after each increment.
   */
  public static ColorTable vectorize(Image image, int bitsPerChannel) {
	  //rehashThreshold could be greater because 4j-3 stipulation is in play -- taken out of play
	  //what to initialize these as?
	  ColorTable table = new ColorTable(3, bitsPerChannel, Constants.QUADRATIC, .49); //creates the ColorTable
	  image = image.quantize(bitsPerChannel); //IS THIS NEEDED?
	  //passing bitsPerChannel into ColorTable already???
	  int width = image.getWidth();
	  int height = image.getHeight();
	  for (int i = 0; i < height; i++) {
		  for (int j = 0; j < width; j++) {
			  table.increment(image.getColor(j, i)); //increments the color at that position to the ColorTable
			  numCollisions++;
		  }
	  }
	  return table;
  }

  /**
   * TODO
   * 
   * Return the result of running Util.cosineSimilarity() on the vectorized images.
   * 
   * Note: If you compute the similarity of an image with itself, it should be close to 1.0.
   */
  public static double similarity(Image image1, Image image2, int bitsPerChannel) {
	  //turn both images into color tables
	  ColorTable ct1 = vectorize(image1, bitsPerChannel);
	  ColorTable ct2 = vectorize(image2, bitsPerChannel);
	  //compare the two color tables
	  double similarity = Util.cosineSimilarity(ct1, ct2);
	  return similarity;
  }

  /**
   * Uses the Painting images and all 8 bitsPerChannel values to compute and print 
   * out a table of collision counts.
   */
  public static void allPairsTest() {
    Painting[] paintings = Painting.values();
    int n = paintings.length;
    for (int y = 0; y < n; y++) {
      for (int x = y + 1; x < n; x++) {
        System.out.println(paintings[y].get().getName() + 
            " and " + 
            paintings[x].get().getName() + ":");
        for (int bitsPerChannel = 1; bitsPerChannel <= 8; bitsPerChannel++) {
          numCollisions = 0;
          System.out.println(String.format("   %d: %.2f %d", 
              bitsPerChannel,
              similarity(paintings[x].get(), paintings[y].get(), bitsPerChannel),
              numCollisions));
        }
        System.out.println();
      }
    }
  }

  /**
   * Simple testing
   */  
  public static void main(String[] args) {
    System.out.println(Constants.TITLE);
    Image mona = Painting.MONA_LISA.get();//davinci
    Image starry = Painting.STARRY_NIGHT.get(); //vangogh
    Image christina = Painting.CHRISTINAS_WORLD.get();
    System.out.println("It looks like all three test images were successfully loaded.");
    System.out.println("mona's dimensions are " + 
        mona.getWidth() + " x " + mona.getHeight());
    System.out.println("starry's dimenstions are " + 
        starry.getWidth() + " x " + starry.getHeight());
    System.out.println("christina's dimensions are " + 
        christina.getWidth() + " x " + christina.getHeight());
    allPairsTest();
    
    //System.out.println("davinci:");
    //System.out.println(vectorize(mona, 2));
    //System.out.println("vangogh:");
    //System.out.println(vectorize(starry, 2));
    
    //System.out.println("similarity between Mona_Lisa and STARRY_NIGHT");
    //System.out.println(similarity(mona, starry, 2));
  }
}
