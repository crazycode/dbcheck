package com.google.dbcheck.spring;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.io.Resource;

import com.google.code.dbcheck.DatabaseDiffer;
import com.google.code.dbcheck.DatabaseLoader;
import com.google.code.dbcheck.XmlProcesser;
import com.google.code.dbcheck.diff.DiffResult;
import com.google.code.dbcheck.model.Database;

public class DatabaseChecker implements BeanPostProcessor, BeanFactoryAware {

    /**
     * SLF4j日志记录.
     */
    private static final Logger  LOGGER    = LoggerFactory.getLogger(DatabaseChecker.class);

    private DataSource           dataSource;

    private Resource             storeXml;

    private String               scheme;

    private final DatabaseLoader loader    = new DatabaseLoader();

    private final XmlProcesser   processer = new XmlProcesser();

    private final DatabaseDiffer differ    = new DatabaseDiffer();

    private boolean              checked   = false;

    /**
     * 在构造函数中进行数据库的检查.
     */
    public DatabaseChecker() {

    }

    public void saveDatabaseXml(File file) {
        try {
            Database runDatabase = loader.load(scheme, dataSource.getConnection());

            processer.save(scheme, runDatabase, file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void checkDatabase() {
        try {
            Database runDatabase = loader.load(scheme, dataSource.getConnection());
            Database storedDatabase = processer.load(scheme, storeXml.getInputStream());

            List<DiffResult> results = differ.compare(runDatabase, storedDatabase);
            if (results.size() > 0) {
                for (DiffResult result : results) {
                    System.out.print(result.textShow());
                }
                System.exit(1); // 有差异，退出JVM.
            }
            System.out.println("Check Database Success!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Resource getStoreXml() {
        return storeXml;
    }

    public void setStoreXml(Resource storeXml) {
        this.storeXml = storeXml;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!checked) {
            if (needDbCheck()) {
                checkDatabase();
            } else {
                System.out.println("Skip Database Check.");
            }
        }
        checked = true;
        return bean;
    }

    /**
     * 是否需要执行dbcheck?
     * 
     * @return
     */
    private boolean needDbCheck() {
        // 如果系统环境变量中存在skipDbCheck，即跳过检查
        Properties prop = System.getProperties();

        if (prop.containsKey("dbcheck.dump") || prop.containsKey("dbcheck.skip")) {
            return false;
        }

        try {
            SkipDatabaseCheck skipDbCheck = beanFactory.getBean(SkipDatabaseCheck.class);

            // 如果Spring Context有SkipDatabaseCheck实例，跳过检查. 避免单元测试中执行检查.
            if (skipDbCheck != null) {
                return false;
            }
        } catch (Exception e) {
            // 找不到Bean时会出现异常，不需要处理
        }

        return true;
    }

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

}
