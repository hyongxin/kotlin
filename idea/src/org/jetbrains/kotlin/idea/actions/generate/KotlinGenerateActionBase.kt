/*
 * Copyright 2010-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.idea.actions.generate

import com.intellij.codeInsight.actions.CodeInsightAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import org.jetbrains.kotlin.idea.core.refactoring.canRefactor
import org.jetbrains.kotlin.psi.JetClassOrObject
import org.jetbrains.kotlin.psi.JetFile
import org.jetbrains.kotlin.psi.psiUtil.getNonStrictParentOfType

abstract class KotlinGenerateActionBase() : CodeInsightAction() {
    override fun isValidForFile(project: Project, editor: Editor, file: PsiFile): Boolean {
        if (file !is JetFile || file.isCompiled) return false

        val targetClass = getTargetClass(editor, file) ?: return false
        return targetClass.canRefactor() && isValidForClass(targetClass)
    }

    protected open fun getTargetClass(editor: Editor, file: PsiFile): JetClassOrObject? {
        return file.findElementAt(editor.caretModel.offset)?.getNonStrictParentOfType<JetClassOrObject>()
    }

    protected abstract fun isValidForClass(targetClass: JetClassOrObject): Boolean
}