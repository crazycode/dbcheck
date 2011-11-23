package com.google.dbcheck;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.dbcheck.spring.DatabaseChecker;

/**
 * Goal which dump Database Struct to XML File.
 * 
 * @goal dump
 * @execute phase="test-compile"
 * @phase test-compile
 * @requiresDependencyResolution test
 */
public class DumpMojo extends AbstractMojo {
    /**
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * @parameter default-value="classpath:spring-dbcheck.xml"
     * @required
     */
    private String       resource;

    /**
     * Returns the an isolated classloader.
     *
     * @return ClassLoader
     * @noinspection unchecked
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
    private ClassLoader getClassLoader() {
        try {
            List<String> classpathElements = project.getCompileClasspathElements();
            classpathElements.add(project.getBuild().getOutputDirectory());
            classpathElements.add(project.getBuild().getTestOutputDirectory());
            URL urls[] = new URL[classpathElements.size()];
            for (int i = 0; i < classpathElements.size(); ++i) {
                urls[i] = new File(classpathElements.get(i)).toURL();
            }
            return new URLClassLoader(urls, this.getClass().getClassLoader());
        } catch (Exception e) {
            getLog().debug("Couldn't get the classloader.");
            return this.getClass().getClassLoader();
        }
    }

    public void execute()
            throws MojoExecutionException
    {
        System.out.println("Hello, world!");

        Properties prop = System.getProperties();
        if (prop.containsKey("dbcheck.dump")) {
            doDatabaseDump();
        } else {
            System.out.println("Skip Database Dump.");
        }
    }

    /**
     * 数据库导出.
     */
    protected void doDatabaseDump() {
        Thread currentThread = Thread.currentThread();
        ClassLoader oldClassLoader = currentThread.getContextClassLoader();

        try {
            currentThread.setContextClassLoader(getClassLoader());

            ApplicationContext ctx = new ClassPathXmlApplicationContext(resource);

            Map<String, DatabaseChecker> map = ctx.getBeansOfType(DatabaseChecker.class);


            for (String name : map.keySet()) {
                DatabaseChecker c = map.get(name);

                try {
                    Resource storeXml = c.getStoreXml();
                    if (storeXml instanceof ClassPathResource) {
                        ClassPathResource res = (ClassPathResource) storeXml;

                        String pathname = "src/main/resources/" + res.getPath();
                        System.out.println("Dump " + c.getScheme() + " to " + pathname + "...");
                        File file = getRelationFileOfPom(pathname);
                        c.saveDatabaseXml(file);
                        System.out.println("Dump " + c.getScheme() + " to " + pathname + ": Success!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } finally {
            currentThread.setContextClassLoader(oldClassLoader);
        }
    }

    /**
     * 得到相对当前pom.xml文件目录相对的pathname文件。
     * 
     * @param pathname
     * @return
     */
    protected File getRelationFileOfPom(String pathname) {
        if (project.getBasedir() != null) {
            // 返回pom.xml目录所在目录的文件名
            return new File(project.getBasedir(), pathname);
        }
        // 返回当前目录相对的文件路径.
        return new File(pathname);
    }
}
