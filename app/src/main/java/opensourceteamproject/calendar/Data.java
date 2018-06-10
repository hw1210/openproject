package opensourceteamproject.calendar;

public class Data {
    private String dDay;
    private String title;
    private String date;

    public Data(String dDay,String title, String date){
        this.dDay=dDay;
        this.title = title;
        this.date = date;
    }

    String getDDay() { return dDay; }

    String getTitle(){
        return title;
    }

    String getDate(){
        return date;
    }

}
