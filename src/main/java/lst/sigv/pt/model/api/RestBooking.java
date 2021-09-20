package lst.sigv.pt.model.api;

import lombok.Data;
import lst.sigv.pt.model.BookingStatus;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Afonseca on 18/11/20
 */

public class RestBooking implements Serializable {
    private long id;
    private RestUser user;
    private BookingStatus status;
    private Date createdDate;
    private Date statusDate;
    private RestAirport departure;
    private RestAirport arrive;
    private RestPlane aircraft;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RestUser getUser() {
        return user;
    }

    public void setUser(RestUser user) {
        this.user = user;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public RestAirport getDeparture() {
        return departure;
    }

    public void setDeparture(RestAirport departure) {
        this.departure = departure;
    }

    public RestAirport getArrive() {
        return arrive;
    }

    public void setArrive(RestAirport arrive) {
        this.arrive = arrive;
    }

    public RestPlane getAircraft() {
        return aircraft;
    }

    public void setAircraft(RestPlane aircraft) {
        this.aircraft = aircraft;
    }
}
