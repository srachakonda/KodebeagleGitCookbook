package com.kodebeagle.git.cookbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;

public class GitRepoFileInfo {

	private static final String GIT_PATH = "/home/sampathr/packfiles/.git";

	public static void main(String[] args) throws IOException {

		List<String> fileTypes = new ArrayList<>();
		fileTypes.add("java");
		fileTypes.add("scala");
		fileTypes.add("js");

		List<FileInfo> fileInfoList = listRepositoryContents(GIT_PATH, fileTypes);

		for (FileInfo fileInfo : fileInfoList) {
			System.out.println("--------------");
			System.out.println(fileInfo.getFileName());
			System.out.println(new String(GitFileData.getFileContent(fileInfo), "UTF-8"));
		}

	}

	private static List<FileInfo> listRepositoryContents(String dotGitFilePath, List<String> fileTypes)
			throws IOException {
		List<FileInfo> repoFileInfoList = new ArrayList<FileInfo>();

		FileRepositoryBuilder builder = new FileRepositoryBuilder();

		Repository repository = builder.setGitDir(new File(dotGitFilePath)).readEnvironment().findGitDir().build();

		Ref head = repository.getRef("HEAD");

		// a RevWalk allows to walk over commits based on some filtering that is
		// defined
		RevWalk walk = new RevWalk(repository);

		RevCommit commit = walk.parseCommit(head.getObjectId());
		RevTree tree = commit.getTree();

		System.out.println("Having tree: " + tree);

		// Now use a TreeWalk to iterate over all files in the Tree recursively
		// We can set Filters to narrow down the results if needed
		TreeWalk treeWalk = new TreeWalk(repository);
		treeWalk.addTree(tree);
		treeWalk.setRecursive(true);
		FileInfo fileInfo = null;
		while (treeWalk.next()) {
			if (fileTypes.size() > 0) {
				String[] types = treeWalk.getPathString().split("\\.");
				if (types.length > 1 && fileTypes.contains(types[1])) {
					System.out.println("found: " + treeWalk.getPathString());
					fileInfo = new FileInfo();
					fileInfo.setRepository(repository);
					fileInfo.setFileName(treeWalk.getPathString());
					fileInfo.setObjectId(treeWalk.getObjectId(0));
					// fileInfo.setContent(repository.open(fileInfo.getObjectId()).getBytes());
					repoFileInfoList.add(fileInfo);
				}
			}
		}
		treeWalk.close();
		walk.close();
		repository.close();
		return repoFileInfoList;

	}
}
