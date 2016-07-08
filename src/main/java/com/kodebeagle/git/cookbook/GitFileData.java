package com.kodebeagle.git.cookbook;

import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;

import java.io.IOException;

public class GitFileData {

	public static byte[] getFileContent(FileInfo fileInfo) throws IOException {
		Repository repository = fileInfo.getRepository();
		ObjectLoader loader = repository.open(fileInfo.getObjectId());
		return loader.getBytes();

	}
}
