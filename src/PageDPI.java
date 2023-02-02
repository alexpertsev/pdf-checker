public class PageDPI {
   private int pageNumber;
   private double dpiX;
   private double dpiY;
   
   public PageDPI(int pageNumber, double dpiX, double dpiY) {
     this.pageNumber = pageNumber;
     this.dpiX = dpiX;
     this.dpiY = dpiY;
   }

   public int getPageNumber() {
     return pageNumber;
   }

   public double getDpiX() {
     return dpiX;
   }

   public double getDpiY() {
    return dpiY;
  }
}
