package com.omaru.wedding;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.sql.Connection;

import org.flywaydb.core.Flyway;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayForceMigrate {

    @Bean
    ApplicationRunner forceFlyway(DataSource dataSource) {
        return args -> {
            try (Connection con = dataSource.getConnection()) {
                System.out.println("[DBG] dbUrl=" + con.getMetaData().getURL());
                System.out.println("[DBG] dbUser=" + con.getMetaData().getUserName());
                // catalog=DB名, schema=public になってるのが普通
                System.out.println("[DBG] catalog=" + con.getCatalog());
                System.out.println("[DBG] schema=" + con.getSchema());
            }

            String loc = "filesystem:" + Path.of("target/classes/db/migration")
                    .toAbsolutePath()
                    .normalize();

            System.out.println("[Flyway-FORCE] using location=" + loc);

            Flyway flyway = Flyway.configure()
                    .dataSource(dataSource)
                    .locations(loc)
                    .defaultSchema("public")
                    .schemas("public")
                    .baselineOnMigrate(true)
                    .load();

            var r = flyway.migrate();
            System.out.println("[Flyway-FORCE] migrationsExecuted=" + r.migrationsExecuted);

            // 同じ接続先DBに本当にテーブルができたか確認（psql不要）
            try (Connection con = dataSource.getConnection();
                 var st = con.createStatement();
                 var rs = st.executeQuery("""
                     select tablename
                     from pg_tables
                     where schemaname = 'public'
                     order by tablename
                 """)) {
                System.out.println("[DBG] public tables:");
                while (rs.next()) {
                    System.out.println(" - " + rs.getString(1));
                }
            }
        };
    }
}
