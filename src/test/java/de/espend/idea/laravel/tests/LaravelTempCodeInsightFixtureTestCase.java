package de.espend.idea.laravel.tests;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.UsefulTestCase;
import com.intellij.testFramework.fixtures.IdeaProjectTestFixture;
import com.intellij.testFramework.fixtures.IdeaTestFixtureFactory;
import de.espend.idea.laravel.LaravelSettings;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
abstract public class LaravelTempCodeInsightFixtureTestCase extends UsefulTestCase {
    private Project project;

    private IdeaProjectTestFixture myFixture;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        myFixture = IdeaTestFixtureFactory.getFixtureFactory()
            .createFixtureBuilder(RandomStringUtils.randomAlphanumeric(20))
            .getFixture();

        myFixture.setUp();

        project = myFixture.getProject();
        LaravelSettings.getInstance(myFixture.getProject()).pluginEnabled = true;
    }

    protected void tearDown() throws Exception {
        this.project = null;

        try {
            this.myFixture.tearDown();
        } finally {
            this.myFixture = null;
            super.tearDown();
        }
    }

    @NotNull
    protected Project getProject() {
        return project;
    }

    @NotNull
    protected VirtualFile createFile(@NotNull String file) {
        return createFile(file, null);
    }

    @NotNull
    protected VirtualFile[] createFiles(@NotNull String... files) {
        List<VirtualFile> virtualFiles = new ArrayList<>();

        for (String file : files) {
            virtualFiles.add(createFile(file));
        }

        return virtualFiles.toArray(new VirtualFile[virtualFiles.size()]);
    }

    protected VirtualFile createFile(@NotNull String file, @Nullable String content) {
        final VirtualFile[] childData = new VirtualFile[1];

        ApplicationManager.getApplication().runWriteAction(new Runnable() {
            @Override
            public void run() {
                try {
                    String[] paths = file.split("/");

                    if(paths.length == 0) {
                        childData[0] = getProject().getBaseDir().createChildData(this, file);
                    } else {
                        childData[0] = VfsUtil.createDirectoryIfMissing(
                            getProject().getBaseDir(),
                            StringUtils.join(Arrays.copyOf(paths, paths.length - 1), "/")
                        ).createChildData(this, paths[paths.length - 1]);
                    }

                    if(content != null) {
                        childData[0].setBinaryContent(content.getBytes());
                    }
                } catch (IOException ignored) {
                }
            }
        });

        return childData[0];
    }
}
