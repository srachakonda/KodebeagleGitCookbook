package com.kodebeagle.git.cookbook;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GitFileData {

	public static byte[] getFileContent(FileInfo fileInfo) throws IOException {
		Repository repository = fileInfo.getRepository();
		ObjectLoader loader = repository.open(fileInfo.getObjectId());
		return loader.getBytes();

	}

	public static List<History> getFileHistory(FileInfo fileInfo) throws GitAPIException {

		List<History> historyList = new ArrayList<History>();
		History history = new History();
		Git git = new Git(fileInfo.getRepository());
		String fileName = fileInfo.getFileName();

		Iterable<RevCommit> logs = git.log()
				.addPath(fileName)
				.call();
		//int count = 0;  No of commits
		for (RevCommit rev : logs) {
			//System.out.println("Commit: " + rev  + ", name: " + rev.getName() + ", id: " + rev.getId().getName() );
			//count++;
			history.setObjectId(rev.getId());
			history.setCommitId(rev.getId().getName());
			historyList.add(history);

		}

		return historyList;
	}
}
