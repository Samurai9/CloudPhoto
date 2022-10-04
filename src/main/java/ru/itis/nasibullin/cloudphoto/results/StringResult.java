package ru.itis.nasibullin.cloudphoto.results;

public class StringResult implements Result<String> {
    private final String result;

    public StringResult(String result) {
        this.result = result;
    }
    @Override
    public String getResult() {
        return result;
    }
}
