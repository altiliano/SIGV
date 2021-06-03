package lst.sigv.pt.service;

import lst.sigv.pt.model.AirportEntity;
import lst.sigv.pt.model.api.RestAirport;

import java.util.List;

/**
 * Created by Afonseca on 12/02/21
 */
public interface AirportService {

    RestAirport addAirport(RestAirport airport);
    RestAirport editAirport(RestAirport airport);
    List<RestAirport> getAirports();
    void  deleteAirport(String id);
    RestAirport findAirportById(String id);



}
