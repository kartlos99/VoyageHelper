package diakonidze.kartlos.voiage.models;

import java.util.Calendar;

/**
 * Created by k.diakonidze on 7/13/2015.
 */
public class PassangerStatement {
        long id, userID, placeX, placeY;
        int freeSpace, price, kondencioneri, sigareti, sabarguli, cxovelebi, atHome;
        String cityFrom, cityTo, time, comment, number, name, surname;
        Calendar date;

        public PassangerStatement(long userID, int freeSpace, int price, String cityFrom, String cityTo, Calendar date) {
                this.userID = userID;
                this.freeSpace = freeSpace;
                this.price = price;
                this.cityFrom = cityFrom;
                this.cityTo = cityTo;
                this.date = date;

                id=0;
                placeX=0;
                placeY=0;
                kondencioneri=2;
                sigareti=2;
                sabarguli=2;
                cxovelebi=2;
                atHome=2;
                time="";
                comment="";
                number=""; name=""; surname="";
        }

        @Override
        public String toString() {
                return "PassangerStatement{" +
                        "id=" + id +
                        ", userID=" + userID +
                        ", placeX=" + placeX +
                        ", placeY=" + placeY +
                        ", freeSpace=" + freeSpace +
                        ", price=" + price +
                        ", kondencioneri=" + kondencioneri +
                        ", sigareti=" + sigareti +
                        ", sabarguli=" + sabarguli +
                        ", cxovelebi=" + cxovelebi +
                        ", atHome=" + atHome +
                        ", cityFrom='" + cityFrom + '\'' +
                        ", cityTo='" + cityTo + '\'' +
                        ", time='" + time + '\'' +
                        ", comment='" + comment + '\'' +
                        ", date=" + date +
                        '}';
        }

        public String getNumber() {
                return number;
        }

        public void setNumber(String number) {
                this.number = number;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getSurname() {
                return surname;
        }

        public void setSurname(String surname) {
                this.surname = surname;
        }

        public long getId() {
                return id;
        }

        public void setId(long id) {
                this.id = id;
        }

        public long getUserID() {
                return userID;
        }

        public void setUserID(long userID) {
                this.userID = userID;
        }

        public long getPlaceX() {
                return placeX;
        }

        public void setPlaceX(long placeX) {
                this.placeX = placeX;
        }

        public long getPlaceY() {
                return placeY;
        }

        public void setPlaceY(long placeY) {
                this.placeY = placeY;
        }

        public int getFreeSpace() {
                return freeSpace;
        }

        public void setFreeSpace(int freeSpace) {
                this.freeSpace = freeSpace;
        }

        public int getPrice() {
                return price;
        }

        public void setPrice(int price) {
                this.price = price;
        }

        public int getKondencioneri() {
                return kondencioneri;
        }

        public void setKondencioneri(int kondencioneri) {
                this.kondencioneri = kondencioneri;
        }

        public int getSigareti() {
                return sigareti;
        }

        public void setSigareti(int sigareti) {
                this.sigareti = sigareti;
        }

        public int getSabarguli() {
                return sabarguli;
        }

        public void setSabarguli(int sabarguli) {
                this.sabarguli = sabarguli;
        }

        public int getCxovelebi() {
                return cxovelebi;
        }

        public void setCxovelebi(int cxovelebi) {
                this.cxovelebi = cxovelebi;
        }

        public int getAtHome() {
                return atHome;
        }

        public void setAtHome(int atHome) {
                this.atHome = atHome;
        }

        public String getCityFrom() {
                return cityFrom;
        }

        public void setCityFrom(String cityFrom) {
                this.cityFrom = cityFrom;
        }

        public String getCityTo() {
                return cityTo;
        }

        public void setCityTo(String cityTo) {
                this.cityTo = cityTo;
        }

        public String getTime() {
                return time;
        }

        public void setTime(String time) {
                this.time = time;
        }

        public String getComment() {
                return comment;
        }

        public void setComment(String comment) {
                this.comment = comment;
        }

        public Calendar getDate() {
                return date;
        }

        public void setDate(Calendar date) {
                this.date = date;
        }
}
