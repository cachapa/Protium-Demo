package net.cachapa.data.model;

public class Gif {
    private static final String GIF_URL = "http://media2.giphy.com/media/%s/giphy.gif";
    private static final String PREVIEW_URL = "http://media2.giphy.com/media/%s/giphy_s.gif";
    
    private String id;
    
    public String getGifUrl() {
        return String.format(GIF_URL, id);
    }
    
    public String getPreviewUrl() {
        return String.format(PREVIEW_URL, id);
    }
}
