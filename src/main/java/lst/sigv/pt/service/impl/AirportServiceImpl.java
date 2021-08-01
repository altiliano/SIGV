package lst.sigv.pt.service.impl;

import lombok.extern.slf4j.Slf4j;
import lst.sigv.pt.exception.AirportAlreadyExistException;
import lst.sigv.pt.model.AirportEntity;
import lst.sigv.pt.model.api.RestAirport;
import lst.sigv.pt.model.api.RestPageRequest;
import lst.sigv.pt.model.api.RestPageResult;
import lst.sigv.pt.repository.AirportRepository;
import lst.sigv.pt.service.AirportService;
import lst.sigv.pt.service.mapper.AirportMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public RestAirport updateAirport(RestAirport airport) {
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
    public RestPageResult<RestAirport> getAllAirports(RestPageRequest request) {
        List<RestAirport> result = new ArrayList<>();
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        //airportRepository.findAll(page).forEach(airportEntity -> result.add(airportMapper.airportEntityToRestAirport(airportEntity)));
        Page<RestAirport> page = airportRepository.findAll(pageable).map(airportMapper::airportEntityToRestAirport);
        RestPageResult<RestAirport> restPageResult = new RestPageResult<>(page.getContent());


        return restPageResult;
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
