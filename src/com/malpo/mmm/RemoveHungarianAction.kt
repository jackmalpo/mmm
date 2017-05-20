package com.malpo.mmm


import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiField
import com.intellij.refactoring.RefactoringFactory
import java.util.*

class RemoveHungarianAction : AnAction() {

    lateinit var mProject: Project

    companion object {
        val HUNGARIAN = Regex("m[A-Z][A-Za-z]*")
    }

    override fun actionPerformed(event: AnActionEvent) {
        mProject = event.getData(PlatformDataKeys.PROJECT)!!
        WriteCommandAction.runWriteCommandAction(mProject) {
            Util.traverseFiles(mProject, HashSet(Arrays.asList(".java"))) { psiFile ->
                for (psiElement in psiFile.children) {
                    if (psiElement is PsiField) {
                        processField(psiElement)
                    }
                }
            }
        }
    }

    private fun processField(psiField: PsiField) {
        var name = psiField.name
        if (HUNGARIAN.matches(name as CharSequence)) {
            name = name.subSequence(1, name.length)
                    .forEachIndexed{index, c ->
                        run {
                            when (index) {
                                0 -> c.toLowerCase()
                                1 -> c.toUpperCase()
                                else -> {}
                            }

                        }
                    }
                    .toString()

            RefactoringFactory.getInstance(mProject).createRename(psiField, name)
            return
        }
    }

}