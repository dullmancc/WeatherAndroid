package Model;

/**
 * Created by Administrator on 2018/3/14.
 */

public class Suggestion {
    private String SuggtextSample;
    private String Suggtext;
    private int Imgid;

    public Suggestion(String suggtextSample,String suggtext,int imgid){
        this.SuggtextSample = suggtextSample;
        this.Suggtext = suggtext;
        this.Imgid = imgid;
    }

    public String getSuggtextSample() {
        return SuggtextSample;
    }

    public void setSuggtextSample(String suggtextSample) {
        SuggtextSample = suggtextSample;
    }

    public String getSuggtext() {
        return Suggtext;
    }

    public void setSuggtext(String suggtext) {
        Suggtext = suggtext;
    }

    public int getImgid() {
        return Imgid;
    }

    public void setImgid(int imgid) {
        Imgid = imgid;
    }
}
