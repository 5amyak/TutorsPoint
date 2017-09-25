package com.samyak;

public class Subtopic {
    private int subtopicId;
    private String subtopicName;
    private String subtopicDescription;

    public Subtopic(int subtopicId, String subtopicName, String subtopicDescription) {
        this.subtopicId = subtopicId;
        this.subtopicName = subtopicName;
        this.subtopicDescription = subtopicDescription;
    }

    @Override
    public String toString() {
        return subtopicName;
    }

    public int getSubtopicId() {
        return subtopicId;
    }

    public String getSubtopicName() {
        return subtopicName;
    }

    public String getSubtopicDescription() {
        return subtopicDescription;
    }
}
