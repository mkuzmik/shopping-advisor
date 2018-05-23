package pl.edu.agh.sp.shoppingadvisor.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Scraper {

    private String[] tags = { "p", "td", "li" };

    protected abstract String descriptionSelector();

    public Collection<String> scrapDescription(String offerAddress) throws IOException {
        Document doc = Jsoup.connect(offerAddress).get();

        Collection<Element> paragraphs = Stream.of(tags)
                .map(tag -> doc.select(descriptionSelector() + tag))
                .flatMap(Elements::stream)
                .collect(Collectors.toSet());

        Elements iframes = doc.select(descriptionSelector() + "iframe");

        for (Element i : iframes) {
            String path = i.absUrl("src");
            Document iframeDoc = Jsoup.connect(path).get();

            paragraphs.addAll(
                    Stream.of(tags)
                            .map(tag -> iframeDoc.select(tag))
                            .flatMap(Elements::stream)
                            .collect(Collectors.toSet())
            );
        }

        return paragraphs.stream()
                .map(Element::text)
                .filter(t -> !t.isEmpty())
                .collect(Collectors.toSet());
    }
}
