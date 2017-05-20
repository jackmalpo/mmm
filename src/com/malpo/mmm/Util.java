package com.malpo.mmm;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;

import java.util.Set;


public class Util {

    interface JavaFileIterator {
        void processFile(PsiFile psiFile);
    }

    public static void traverseFiles(Project project, Set<String> extensions, JavaFileIterator iterator) {
        ProjectFileIndex.SERVICE.getInstance(project).iterateContent(fileOrDir -> {
            String n = fileOrDir.toString();
            int i = n.lastIndexOf('.');
            if (i < 0) {
                return true;
            }
            String ext = n.substring(i);
            if (extensions.contains(ext)) {
                PsiFile psiFile = PsiManager.getInstance(project).findFile(fileOrDir);
                if (psiFile != null) {
                    iterator.processFile(psiFile);
                } else {
                    System.err.println("could not find psiFile for: " + n);
                }
            }
            return true;
        });
    }
}
