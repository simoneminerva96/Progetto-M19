package main;

import java.util.GregorianCalendar;

public class Driver
{
    private String carId;
    private GregorianCalendar timeIn;
    private GregorianCalendar timePaid;

    public Driver(String carId)
    {
        this.carId = carId;
        this.timeIn = new GregorianCalendar();
    }

    public void setTimePaid(GregorianCalendar timePaid)
    {
        this.timePaid = timePaid;
    }
}