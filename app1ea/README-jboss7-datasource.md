# JBoss Datasource

[DataSource Configuration in AS7](https://community.jboss.org/wiki/DataSourceConfigurationinAS7)
[How to create and manage datasources in AS7](http://www.javalinux.it/wordpress/2011/07/14/how-to-create-an-manage-datasources-in-as7/)
[JBoss 7 PostgreSQL 9](http://jan.zawodny.pl/blog/2011/07/jboss-7-postgresql-9)

## JDBC Driver

Install a JDBC driver by copying it into `deployments`:

    cp jdbc.jar $JBOSS_HOME/standalone/deployments

or by adding it as a module:

* Create directory `$JBOSS_HOME/modules/com/mysql/main`
* Copy the jar into `$JBOSS_HOME/modules/com/mysql/main`
* Add XML descriptor `module.xml`

`$JBOSS_HOME/modules/com/mysql/main/module.xml` has the following content:

    <?xml version="1.0" encoding="UTF-8"?>
    <module xmlns="urn:jboss:module:1.0" name="com.mysql">
      <resources>
        <resource-root path="mysql-connector-java-5.1.20-bin.jar"/>
      </resources>
      <dependencies>
        <module name="javax.api"/>
      </dependencies>
    </module>

## XML Configuration

Create the datasource in `$JBOSS_HOME/standalone/configuration/standalone.xml` or `$JBOSS_HOME/domain/configuration/domain.xml`:

    <subsystem xmlns="urn:jboss:domain:datasources:1.0">
        <datasources>
            <datasource jndi-name="java:jboss/datasources/app1Datasource" pool-name="app1DatasourcePool" enabled="true" jta="true" use-java-context="true" use-ccm="true">
                <connection-url>jdbc:mysql://localhost:3306/app1</connection-url>
                <driver>com.mysql</driver>
                <transaction-isolation>TRANSACTION_READ_COMMITTED</transaction-isolation>
                <pool>
                    <min-pool-size>10</min-pool-size>
                    <max-pool-size>100</max-pool-size>
                    <prefill>true</prefill>
                    <flush-strategy>FailingConnectionOnly</flush-strategy>
                </pool>
                <security>
                    <user-name>app1</user-name>
                    <password>app1</password>
                </security>
                <statement>
                    <prepared-statement-cache-size>32</prepared-statement-cache-size>
                    <share-prepared-statements/>
                </statement>
                <validation>
                    <check-valid-connection-sql>SELECT 1</check-valid-connection-sql>
                    <validate-on-match>false</validate-on-match>
                    <background-validation>false</background-validation>
                    <use-fast-fail>false</use-fast-fail>
                </validation>
            </datasource>
            <drivers>
                <driver name="com.mysql" module="com.mysql">
                    <xa-datasource-class>com.mysql.jdbc.jdbc2.optional.MysqlXADataSource</xa-datasource-class>
                </driver>
            </drivers>
        </datasources>
    </subsystem>
