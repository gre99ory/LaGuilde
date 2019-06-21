package com.ggpi.laguilde.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GGEventModel {

    /*
    ArrayList<Date> date=new ArrayList<>();
    SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
    Date date1=format.parse("02-12-2001");
    Date date2=format.parse("12-09-1999");
    Date date3=format.parse("13-11-2016");
  date.add(date1);
  date.add(date2);
  date.add(date3);
  Collections.sort(date);
  date.forEach(action-> System.out.println(format.format(action)));
  */

    /* Event sample
    {
        "id": 71,
            "start": "2019-05-31 20:00:00",
            "backgroundColor": "#82d5e2",
            "borderColor": "#000000",
            "textColor": "#000000",
            "title": "Draft Guerre des Planeswalkers",
            "description": "",
            "image": "logomagic.png",
            "gamename": "Magic",
            "typename": "FNM",
            "eventname": "Draft Guerre des Planeswalkers",
            "formatname": "Draft",
            "price": "14",
            "winner": "Yohann B."
    },
    */

    public class Params {
        public static final String ID = "id";
        public static final String START = "start";
        public static final String BACKGROUND = "backgroundColor";
        public static final String BORDER = "borderColor";
        public static final String TEXT = "textColor";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String IMAGE = "image";
        public static final String GAME = "gamename";
        public static final String TYPE = "typename";
        public static final String EVENT = "eventname";
        public static final String FORMAT = "formatname";
        public static final String PRICE = "price";
        public static final String WINNER = "winner";

    }

    private String id,
            start,
            backgroundColor,
            borderColor,
            textColor,
            title,
            description,
            image,
            gameName,
            typeName,
            eventName,
            formatName,
            winnerName;

    private int price;

    private Date date;
    private Calendar calendar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(String price) {
        try {
            this.price = Integer.parseInt(price);
        }
        catch ( Exception e ) {
            this.price = 0;
        }
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getWinnerName() { return this.winnerName; }

    public void setWinnerName(String winnerName ) { this.winnerName = winnerName; }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
        this.setDate(start.substring(0,13));
    }

    /* Private setter */
    private void setDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        try {
            this.date = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar = Calendar.getInstance();
        calendar.setTime(this.date);
    }

    public Date getDate() { return date; }

    public Calendar getCalendar() { return calendar; }

    /* Utility */
    public int getMonth() {
        return date.getMonth();
    }


    /* Text Converted Data */
    public String getPriceText() {
        if ( price > 0 ) return price + " €";
        return "Gratuit";
    }

    public String getMonthText() {
        SimpleDateFormat format = new SimpleDateFormat("MMMM");
        return format.format(date);
    }

    public String getDateText() {
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM");
        return format.format(date);
    }

    public String getStartText() {
        SimpleDateFormat format = new SimpleDateFormat("EEEE dd MMMM '-' HH'h'");
        return format.format(date);
    }

}