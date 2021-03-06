package TestJunit;
import main.Parking.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class entrySubscriptionTest {

    Parking m = new Parking();
    Entrance em = new Entrance(m);
    splitString split = new splitString();

    @Test
    public void cardIDTest(){
        m.makeFloors(2,200);
        m.setSpacesSubdivision(200);
        assertEquals("entryNo--Targa non valida.",em.entrySub("000000000","MM") );
    }

    @Test
    public void notSubYet()
    {
        m.makeFloors(2,200);
        m.setSpacesSubdivision(200);
        assertEquals("entryNo--Non hai ancora l'abbonamento.",
                em.entrySub("0000000", "XX"));
    }

    @Test
    public void soldOutSub()
    {
        m.makeFloors(1,10);
        m.setSpacesSubdivision(1);
        //Ingresso valido con abbonamento
        em.entrySub("0000000","Mensile");
        //Ingresso che dovebbre generare l'errore
        assertEquals("entryNo--Abbonamenti finiti.",
                em.entrySub("1111111","Mensile"));
    }

    @Test
    public void validEntrySubMensile(){
        String s;
        m.makeFloors(1,10);
        m.setSpacesSubdivision(10);
        s = split.split(em.entrySub("1111111","Mensile"));
        assertEquals("entryOk",s);
    }

    @Test
    public void validEntrySubSemestrale(){
        String s;
        m.makeFloors(1,10);
        m.setSpacesSubdivision(10);
        s = split.split(em.entrySub("2222222","Semestrale"));
        assertEquals("entryOk",s);
    }

    @Test
    public void validEntrySunAnnuale(){
        String s;
        m.makeFloors(1,10);
        m.setSpacesSubdivision(10);
        s = split.split(em.entrySub("3333333","Annuale"));
        assertEquals("entryOk",s);
    }

    @Test
    public void carInParkYetTicket(){
        m.makeFloors(1,10);
        m.setSpacesSubdivision(5);
        //Ingresso valido
        em.entryTicket("0000000");
        //Ingresso che da errore
        assertEquals("entryNo--Ingresso non riuscito, la targa risulta già all'interno con un ticket.",
                em.entrySub("0000000","Mensile") );
    }

    @Test
    public void carInParkYey(){
        m.makeFloors(1,10);
        m.setSpacesSubdivision(5);
        em.entrySub("0000000","Mensile");

        assertEquals("entryNo--Ingresso non riuscito, targa: 0000000 già all'interno del parcheggio.",
                em.entrySub("0000000","Mensile"));
    }

    @Test
    void entryBuySubYet(){
        m.makeFloors(1,10);
        m.setSpacesSubdivision(5);
        //Ingresso
        em.entrySub("0000000","Mensile");
        //Simulo l'uscita
        m.getDriver("0000000").setInPark(false);
        System.out.println("InPark" + m.getDriver("0000000").getInPark());
        //Rientra
        assertEquals("entryOk--Hai già un abbonamento, puoi entrare.",
                em.entrySub("0000000","Mensile"));
    }

}
