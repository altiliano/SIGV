package lst.sigv.pt.service.impl;

import lombok.extern.slf4j.Slf4j;
import lst.sigv.pt.exception.AirportAlreadyExistException;
import lst.sigv.pt.model.AirportEntity;
import lst.sigv.pt.model.api.RestAirport;
import lst.sigv.pt.repository.AirportRepository;
import lst.sigv.pt.service.AirportService;
import lst.sigv.pt.service.mapper.AirportMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Afonseca on 12/02/21
 */
@Service
@Slf4j
public class AirportServiceImpl implements AirportService {
    private final AirportMapper airportMapper;
    private final AirportRepository airportRepository;

    public AirportServiceImpl(AirportMapper airportMapper, AirportRepository airportRepository) {
        this.airportMapper = airportMapper;
        this.airportRepository = airportRepository;
    }

    @Override
    public RestAirport editAirport(RestAirport airport) {
        return saveAirport(airport);
    }

    @Override
    public RestAirport addAirport(RestAirport airport) {
        AirportEntity existingAirport = airportRepository.findByIcaoCode(airport.getIcaoCode());

        if (existingAirport != null) {
            throw new AirportAlreadyExistException("Airport with ICAO: " + airport.getIcaoCode() + " already exist");
        }
        return saveAirport(airport);
    }

    @Override
    public List<RestAirport> getAirports() {
        List<RestAirport> airports = new ArrayList<>();
        airportRepository.findAll().forEach(airportEntity -> airports.add(airportMapper.airportEntityToRestAirport(airportEntity)));
        return airports;
    }

    @Override
    public void deleteAirport(String id) {
        airportRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public RestAirport findAirportById(String id) {
        return airportMapper.airportEntityToRestAirport(airportRepository.findAirportEntityById(Long.parseLong(id)));
    }


    private RestAirport saveAirport(RestAirport airport) {
        AirportEntity airportEntity = airportMapper.restAirportToAirportEntity(airport);
        return airportMapper.airportEntityToRestAirport(airportRepository.save(airportEntity));
    }
}
