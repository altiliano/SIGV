package lst.sigv.pt.service;

import lst.sigv.pt.model.api.RestAirport;
import lst.sigv.pt.model.api.RestPageResult;
import lst.sigv.pt.model.api.RestPageRequest;

/**
 * Created by Afonseca on 12/02/21
 */
public interface AirportService {

    RestAirport addAirport(RestAirport airport);
    RestAirport updateAirport(RestAirport airport);
    RestPageResult<RestAirport> getAllAirports(RestPageRequest request);
    void  deleteAirport(String id);
    RestAirport findAirportById(String id);



}
