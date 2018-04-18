package pl.edu.agh.sp.shoppingadvisor.scraper;

import java.io.IOException;
import java.util.Collection;

public class ScrapExample {

    public static void main(String[] args) throws IOException {
        Scraper scraper = new AllegroScraper();
        Collection<String> description = scraper.scrapDescription(
                "http://allegro.pl/zasilacz-lenovo-b590-thinkpad-t430-t430s-x201-x230-i6718933792.html");

        description.forEach(System.out::println);

        System.out.println("---------------------------------------");

        description = scraper.scrapDescription(
                "http://allegro.pl/super-audi-a4-w-tdi-swiezy-import-oplacone-i7288776166.html");

        description.forEach(System.out::println);
    }

}
