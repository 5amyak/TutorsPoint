package com.samyak;

public class Subtopic {
    private int subtopicId;
    private String subtopicName;

    public Subtopic(int subtopicId, String subtopicName) {
        this.subtopicId = subtopicId;
        this.subtopicName = subtopicName;
    }

    @Override
    public String toString() {
        return subtopicName;
    }

    public int getSubtopicId() {
        return subtopicId;
    }
}
