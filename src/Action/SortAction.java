package Action;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import Sorter.Sorter;
/**
 * Created by armand on 3/1/15.
 */
public class SortAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {


        PsiClass psiClass = getPsiClassFromContext(e);

        fortmatText(psiClass);
        return;
    }



    private void fortmatText(final PsiClass psiClass) {
        new WriteCommandAction.Simple(psiClass.getProject(), psiClass.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                new Sorter(psiClass).sort();

            }
        }.execute();


    }



    private PsiClass getPsiClassFromContext(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);

        if (psiFile == null || editor == null) {
            return null;
        }

        int offSet = editor.getCaretModel().getOffset();
        PsiElement elementAt = psiFile.findElementAt(offSet);
        PsiClass psiClass = PsiTreeUtil.getParentOfType(elementAt, PsiClass.class);

        return psiClass;
    }
}
