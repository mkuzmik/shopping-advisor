package pl.edu.agh.sp.shoppingadvisor.scraper;

public class AllegroScraper extends Scraper {

    @Override
    protected String descriptionSelector() {
        return "#showitem-main .opbox-fragment .opbox-sheet-wrapper .opbox-sheet .app-container ";
    }

}
