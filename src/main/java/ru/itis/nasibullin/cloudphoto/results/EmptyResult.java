package ru.itis.nasibullin.cloudphoto.results;

public class EmptyResult implements Result<Object> {
    @Override
    public Object getResult() {
        return null;
    }
}
