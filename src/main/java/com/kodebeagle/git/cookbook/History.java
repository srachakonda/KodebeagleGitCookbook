package com.kodebeagle.git.cookbook;

import org.eclipse.jgit.lib.ObjectId;

public class History {
    private String commitId;
    private ObjectId objectId;

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }
}
