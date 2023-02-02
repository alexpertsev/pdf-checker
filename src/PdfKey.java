public class PdfKey {
   private String filename;
   private int numberOfPages;
   
   public PdfKey(String filename, int numberOfPages) {
    this.filename = filename;
    this.numberOfPages = numberOfPages;
   }

   public String getFilename() {
    return filename;
   }

   public int getNumberOfPages() {
    return numberOfPages;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((filename == null) ? 0 : filename.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      PdfKey other = (PdfKey) obj;
      if (filename == null) {
         if (other.filename != null)
            return false;
      } else if (!filename.equals(other.filename))
         return false;
      return true;
   }


}
