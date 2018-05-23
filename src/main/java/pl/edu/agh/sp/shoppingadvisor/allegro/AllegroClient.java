package pl.edu.agh.sp.shoppingadvisor.allegro;

import api.allegro.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

@Component
@PropertySource("classpath:secrets.properties")
public class AllegroClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(AllegroClient.class);

    private Environment environment;

    @Autowired
    public AllegroClient(Jaxb2Marshaller marshaller, Environment environment) {
        this.setDefaultUri("https://webapi.allegro.pl/service.php");
        this.setMarshaller(marshaller);
        this.setUnmarshaller(marshaller);
        this.environment = environment;
    }

    public DoGetItemsListResponse getItems(String searchPhrase, int offset) {
        DoGetItemsListRequest request = new DoGetItemsListRequest();
        request.setCountryId(1);
        request.setWebapiKey(environment.getProperty("allegro.api.key"));
        request.setResultOffset(offset);

        ArrayOfFilteroptionstype filters = new ArrayOfFilteroptionstype();
        FilterOptionsType filter = new FilterOptionsType();
        filter.setFilterId("search");
        ArrayOfString arrayOfString = new ArrayOfString();
        arrayOfString.getItem().add(searchPhrase);
        filter.setFilterValueId(arrayOfString);
        filters.getItem().add(filter);

        request.setFilterOptions(filters);

        log.info("Requesting for items list");

        return (DoGetItemsListResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }
}
