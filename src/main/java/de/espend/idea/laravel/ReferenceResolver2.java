package de.espend.idea.laravel;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.containers.ContainerUtil;
import com.jetbrains.php.lang.psi.PhpMultipleDeclarationFilter;
import com.jetbrains.php.lang.psi.elements.PhpNamedElement;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ReferenceResolver2 implements PhpMultipleDeclarationFilter {

    private static final Logger logger = Logger.getInstance(ReferenceResolver2.class);

    @Override
    public <E extends PhpNamedElement> Collection<E> filter(@NotNull PsiElement psiElement, Collection<E> candidates) {
        if (candidates.size() > 1) {
            return ContainerUtil.filter(candidates, (PhpNamedElement candidate) -> {
                final PsiFile file = candidate.getContainingFile();
                if (file == null) {
                    return false;
                }
                VirtualFile virtualFile = file.getVirtualFile();
                if (virtualFile == null) {
                    return false;
                }

                String name = virtualFile.getName();
                return virtualFile.isValid() && name.startsWith("_ide_helper") && name.endsWith(".php");
            });
        }
        return candidates;
    }
}
