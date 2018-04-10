public class upload {
    private String name;
    private String uri;

    public upload(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public upload() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
